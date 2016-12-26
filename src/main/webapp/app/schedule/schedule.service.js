(function() {
	'use strict';
	angular
		.module('danceFormApp')
		.factory('CompetitionSchedule', CompetitionSchedule);

	CompetitionSchedule.$inject = ['$resource', 'DateUtils'];

	function CompetitionSchedule($resource, DateUtils) {
		var resourceUrl = 'api/schedule/competitions/:id';

		return $resource(resourceUrl, {}, {
			'query': {
				method: 'GET', isArray: true,
				transformResponse: function(data) {
					if(data) {
						data = angular.fromJson(data);
						for(var i = 0; i < data.length; i++) {
							data[i].startDate = DateUtils.convertLocalDateFromServer(data[i].startDate);
							data[i].endDate = DateUtils.convertLocalDateFromServer(data[i].endDate);
							data[i].registrationClosesTime = DateUtils.convertDateTimeFromServer(data[i].registrationClosesTime);
						}
					}
					return data;
				}
			},
			'get': {
				method: 'GET',
				transformResponse: function(data) {
					if(data) {
						data = angular.fromJson(data);
						data.startDate = DateUtils.convertLocalDateFromServer(data.startDate);
						data.endDate = DateUtils.convertLocalDateFromServer(data.endDate);
						data.registrationClosesTime = DateUtils.convertDateTimeFromServer(data.registrationClosesTime);
					}
					return data;
				}
			},
			'update': {
				method: 'PUT',
				transformRequest: function(data) {
					var copy = angular.copy(data);
					copy.startDate = DateUtils.convertLocalDateToServer(copy.startDate);
					copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
					copy.registrationClosesTime = DateUtils.convertDateTimeFromServer(copy.registrationClosesTime);
					return angular.toJson(copy);
				}
			},
			'save': {
				method: 'POST',
				transformRequest: function(data) {
					var copy = angular.copy(data);
					copy.startDate = DateUtils.convertLocalDateToServer(copy.startDate);
					copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
					copy.registrationClosesTime = DateUtils.convertDateTimeFromServer(copy.registrationClosesTime);
					return angular.toJson(copy);
				}
			}
		});
	}
})();
