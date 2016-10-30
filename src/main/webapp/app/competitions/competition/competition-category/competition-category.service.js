(function() {
	'use strict';
	angular
		.module('danceFormApp')
		.factory('CompetitionCategory', CompetitionCategory);

	CompetitionCategory.$inject = ['$resource', 'DateUtils'];

	function CompetitionCategory($resource, DateUtils) {
		var resourceUrl = 'api/competition/:competitionId/competition-categories/:id';

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
			},
			'update': {
				method: 'PUT',
				transformRequest: function(data) {
					var copy = angular.copy(data);
					copy.date = DateUtils.convertLocalDateToServer(copy.date);
					return angular.toJson(copy);
				}
			},
			'save': {
				method: 'POST',
				transformRequest: function(data) {
					var copy = angular.copy(data);
					copy.date = DateUtils.convertLocalDateToServer(copy.date);
					return angular.toJson(copy);
				}
			},
			'available': {
				method: 'POST',
				isArray: true,
				url: resourceUrl + '/available'
			}
		});
	}
})();
