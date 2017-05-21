(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider
			.state('competition-notification', {
				parent: 'competitions',
				url: '/competition-notification',
				data: {
					authorities: ['ROLE_ADMIN'],
					pageTitle: 'danceFormApp.competitionNotification.home.title'
				},
				views: {
					'content@': {
						templateUrl: 'app/competitions/competition-notification/competition-notifications.html',
						controller: 'CompetitionNotificationController',
						controllerAs: 'vm'
					}
				},
				resolve: {
					translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
						$translatePartialLoader.addPart('competitionNotification');
						$translatePartialLoader.addPart('global');
						return $translate.refresh();
					}]
				}
			})
			.state('competition-notification-detail', {
				parent: 'competitions',
				url: '/competition-notification/{id}',
				data: {
					authorities: ['ROLE_ADMIN'],
					pageTitle: 'danceFormApp.competitionNotification.detail.title'
				},
				views: {
					'content@': {
						templateUrl: 'app/competitions/competition-notification/competition-notification-detail.html',
						controller: 'CompetitionNotificationDetailController',
						controllerAs: 'vm'
					}
				},
				resolve: {
					translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
						$translatePartialLoader.addPart('competitionNotification');
						return $translate.refresh();
					}],
					entity: ['$stateParams', 'CompetitionNotification', function($stateParams, CompetitionNotification) {
						return CompetitionNotification.get({id: $stateParams.id}).$promise;
					}],
					previousState: ["$state", function($state) {
						var currentStateData = {
							name: $state.current.name || 'competition-notification',
							params: $state.params,
							url: $state.href($state.current.name, $state.params)
						};
						return currentStateData;
					}]
				}
			})
			.state('competition-notification-detail.edit', {
				parent: 'competition-notification-detail',
				url: '/detail/edit',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/competitions/competition-notification/competition-notification-dialog.html',
						controller: 'CompetitionNotificationDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: ['CompetitionNotification', function(CompetitionNotification) {
								return CompetitionNotification.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('^', {}, {reload: false});
					}, function() {
						$state.go('^');
					});
				}]
			})
			.state('competition-notification.new', {
				parent: 'competition-notification',
				url: '/new',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/competitions/competition-notification/competition-notification-dialog.html',
						controller: 'CompetitionNotificationDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: function() {
								return {
									competitionId: null,
									message: null,
									isActive: null,
									id: null
								};
							}
						}
					}).result.then(function() {
						$state.go('competition-notification', null, {reload: 'competition-notification'});
					}, function() {
						$state.go('competition-notification');
					});
				}]
			})
			.state('competition-notification.edit', {
				parent: 'competition-notification',
				url: '/{id}/edit',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/competitions/competition-notification/competition-notification-dialog.html',
						controller: 'CompetitionNotificationDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: ['CompetitionNotification', function(CompetitionNotification) {
								return CompetitionNotification.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('competition-notification', null, {reload: 'competition-notification'});
					}, function() {
						$state.go('^');
					});
				}]
			})
			.state('competition-notification.delete', {
				parent: 'competition-notification',
				url: '/{id}/delete',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/competitions/competition-notification/competition-notification-delete-dialog.html',
						controller: 'CompetitionNotificationDeleteController',
						controllerAs: 'vm',
						size: 'md',
						resolve: {
							entity: ['CompetitionNotification', function(CompetitionNotification) {
								return CompetitionNotification.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('competition-notification', null, {reload: 'competition-notification'});
					}, function() {
						$state.go('^');
					});
				}]
			});
	}

})();
