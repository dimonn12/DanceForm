(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider.state('feedback', {
			parent: 'app',
			url: '/feedback',
			data: {
				authorities: [],
				pageTitle: 'danceFormApp.feedback.home.title'
			},
			views: {
				'content@': {
					templateUrl: 'app/feedback/feedback.html',
					controller: 'FeedbackController',
					controllerAs: 'vm'
				}
			},
			resolve: {
				mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('feedback');
					return $translate.refresh();
				}]
			}
		});
	}
})();
