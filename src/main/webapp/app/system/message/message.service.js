(function() {
	'use strict';
	angular
		.module('danceFormApp')
		.factory('Message', Message);

	Message.$inject = ['$resource', 'DateUtils'];

	function Message($resource, DateUtils) {
		var resourceUrl = 'api/system/messages/:id';

		return $resource(resourceUrl, {}, {
			'query': {method: 'GET', isArray: true},
			'get': {
				method: 'GET',
				transformResponse: function(data) {
					if(data) {
						data = angular.fromJson(data);
						data.dateCreated = DateUtils.convertDateTimeFromServer(data.dateCreated);
						data.dateSent = DateUtils.convertDateTimeFromServer(data.dateSent);
					}
					return data;
				}
			},
			'update': {method: 'PUT'}
		});
	}
})();
