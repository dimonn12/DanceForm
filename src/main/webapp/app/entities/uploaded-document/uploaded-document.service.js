(function() {
	'use strict';
	angular
		.module('danceFormApp')
		.factory('UploadedDocument', UploadedDocument);

	UploadedDocument.$inject = ['$resource', 'DateUtils'];

	function UploadedDocument($resource, DateUtils) {
		var resourceUrl = 'api/uploaded-documents/:id';

		return $resource(resourceUrl, {}, {
			'query': {method: 'GET', isArray: true},
			'get': {
				method: 'GET',
				transformResponse: function(data) {
					if(data) {
						data = angular.fromJson(data);
						data.uploadedDate = DateUtils.convertDateTimeFromServer(data.uploadedDate);
					}
					return data;
				}
			},
			'update': {method: 'PUT'}
		});
	}
})();
