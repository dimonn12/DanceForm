(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider.state('login', {
			parent: 'account',
			url: '/login',
			data: {
				pageTitle: 'global.menu.account.login'
			},
			views: {
				'content@': {
					templateUrl: 'app/components/login/login.html',
					controller: 'LoginController',
					controllerAs: 'vm'
				}
			},
			resolve: {
				translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('login');
					return $translate.refresh();
				}]
			}
		});
	}
})();
