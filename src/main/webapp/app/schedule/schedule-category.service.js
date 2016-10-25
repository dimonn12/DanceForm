(function() {
	'use strict';
	angular
		.module('danceFormApp')
		.factory('CompetitionScheduleCategory', CompetitionScheduleCategory);

	CompetitionScheduleCategory.$inject = ['$resource', 'DateUtils'];

	function CompetitionScheduleCategory($resource, DateUtils) {
		var resourceUrl = '/api/schedule/competitions/:competitionId/category/:id';

		return $resource(resourceUrl, {}, {
			'query': {method: 'GET', isArray: true},
			'get': {
				method: 'GET',
				transformResponse: function(data) {
					if(data) {
						data = angular.fromJson(data);
						data.date = DateUtils.convertLocalDateFromServer(data.date);
					}
					return data;
				}
			}
		});
	}
})();
