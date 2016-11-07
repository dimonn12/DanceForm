(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider.state('system', {
			url: '/system',
			abstract: true,
			parent: 'app'
		});
	}
})();
