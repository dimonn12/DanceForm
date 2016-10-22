(function() {
	'use strict';
	angular
		.module('danceFormApp')
		.factory('RegisteredCouple', RegisteredCouple);

	RegisteredCouple.$inject = ['$resource', 'DateUtils'];

	function RegisteredCouple($resource, DateUtils) {
		var resourceUrl = 'api/registered-couples/:id';

		return $resource(resourceUrl, {}, {
			'query': {method: 'GET', isArray: true},
			'get': {
				method: 'GET',
				transformResponse: function(data) {
					if(data) {
						data = angular.fromJson(data);
						data.partner1DateOfBirth = DateUtils.convertLocalDateFromServer(data.partner1DateOfBirth);
						data.partner2DateOfBirth = DateUtils.convertLocalDateFromServer(data.partner2DateOfBirth);
					}
					return data;
				}
			},
			'update': {
				method: 'PUT',
				transformRequest: function(data) {
					var copy = angular.copy(data);
					copy.partner1DateOfBirth = DateUtils.convertLocalDateToServer(copy.partner1DateOfBirth);
					copy.partner2DateOfBirth = DateUtils.convertLocalDateToServer(copy.partner2DateOfBirth);
					return angular.toJson(copy);
				}
			},
			'save': {
				method: 'POST',
				transformRequest: function(data) {
					var copy = angular.copy(data);
					copy.partner1DateOfBirth = DateUtils.convertLocalDateToServer(copy.partner1DateOfBirth);
					copy.partner2DateOfBirth = DateUtils.convertLocalDateToServer(copy.partner2DateOfBirth);
					return angular.toJson(copy);
				}
			}
		});
	}
})();
