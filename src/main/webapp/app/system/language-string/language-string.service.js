(function() {
	'use strict';
	angular
		.module('danceFormApp')
		.factory('LanguageString', LanguageString);

	LanguageString.$inject = ['$resource'];

	function LanguageString($resource) {
		var resourceUrl = 'api/system/language-strings/:id';

		return $resource(resourceUrl, {}, {
			'query': {method: 'GET', isArray: true},
			'get': {
				method: 'GET',
				transformResponse: function(data) {
					if(data) {
						data = angular.fromJson(data);
					}
					return data;
				}
			},
			'update': {method: 'PUT'}
		});
	}
})();
