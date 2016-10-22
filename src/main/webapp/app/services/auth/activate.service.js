(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.factory('Activate', Activate);

	Activate.$inject = ['$resource'];

	function Activate($resource) {
		var service = $resource('api/activate', {}, {
			'get': {method: 'GET', params: {}, isArray: false}
		});

		return service;
	}
})();
