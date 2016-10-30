(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider.state('schedule', {
			parent: 'app',
			url: '/schedule',
			data: {
				authorities: [],
				pageTitle: 'global.menu.schedule'
			},
			views: {
				'content@': {
					templateUrl: 'app/schedule/schedule.html',
					controller: 'ScheduleController',
					controllerAs: 'vm'
				}
			},
			resolve: {
				mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('schedule');
					return $translate.refresh();
				}]
			}
		}).state('schedule-details', {
			parent: 'schedule',
			url: '/{id}',
			data: {
				authorities: [],
				pageTitle: 'global.title'
			},
			views: {
				'content@': {
					templateUrl: 'app/schedule/details/details.html',
					controller: 'CompetitionScheduleDetailsController',
					controllerAs: 'vm'
				}
			},
			resolve: {
				translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('schedule');
					return $translate.refresh();
				}],
				entity: ['$stateParams', 'CompetitionSchedule', function($stateParams, CompetitionSchedule) {
					return CompetitionSchedule.get({id: $stateParams.id}).$promise;
				}],
				previousState: ["$state", function($state) {
					var currentStateData = {
						name: $state.current.name || 'schedule',
						params: $state.params,
						url: $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				}]
			}
		}).state('schedule-category-register', {
			parent: 'schedule-details',
			url: '/registry',
			data: {
				authorities: [],
				pageTitle: 'global.title'
			},
			views: {
				'content@': {
					templateUrl: 'app/schedule/details/registry.html',
					controller: 'CategoryRegisterController',
					controllerAs: 'vm'
				}
			},
			resolve: {
				translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('schedule');
					return $translate.refresh();
				}],
				entity: ['$stateParams', 'CompetitionSchedule', function($stateParams, CompetitionSchedule) {
					return CompetitionSchedule.get({id: $stateParams.id}).$promise;
				}],
				previousState: ["$state", function($state) {
					var currentStateData = {
						name: $state.current.name || 'schedule-details',
						params: $state.params,
						url: $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				}]
			}
		}).state('schedule-category-details', {
			parent: 'schedule-details',
			url: '/category/{categoryId}/couples',
			data: {
				authorities: [],
				pageTitle: 'global.title'
			},
			views: {
				'content@': {
					templateUrl: 'app/schedule/details/registeredCouples.html',
					controller: 'CategoryCouplesController',
					controllerAs: 'vm'
				}
			},
			resolve: {
				translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('schedule');
					return $translate.refresh();
				}],
				previousState: ["$state", function($state) {
					var currentStateData = {
						name: $state.current.name || 'schedule-details',
						params: $state.params,
						url: $state.href($state.current.name, $state.params)
					};
					return currentStateData;
				}]
			}
		});
	}
})();
