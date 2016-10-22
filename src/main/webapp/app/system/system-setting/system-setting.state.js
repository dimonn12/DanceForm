(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider
			.state('system-setting', {
				parent: 'system',
				url: '/system-setting?page&sort&search',
				data: {
					authorities: ['ROLE_ADMIN'],
					pageTitle: 'danceFormApp.systemSetting.home.title'
				},
				views: {
					'content@': {
						templateUrl: 'app/system/system-setting/system-settings.html',
						controller: 'SystemSettingController',
						controllerAs: 'vm'
					}
				},
				params: {
					page: {
						value: '1',
						squash: true
					},
					sort: {
						value: 'id,asc',
						squash: true
					},
					search: null
				},
				resolve: {
					pagingParams: ['$stateParams', 'PaginationUtil', function($stateParams, PaginationUtil) {
						return {
							page: PaginationUtil.parsePage($stateParams.page),
							sort: $stateParams.sort,
							predicate: PaginationUtil.parsePredicate($stateParams.sort),
							ascending: PaginationUtil.parseAscending($stateParams.sort),
							search: $stateParams.search
						};
					}],
					translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
						$translatePartialLoader.addPart('systemSetting');
						$translatePartialLoader.addPart('global');
						return $translate.refresh();
					}]
				}
			})
			.state('system-setting-detail', {
				parent: 'system',
				url: '/system-setting/{id}',
				data: {
					authorities: ['ROLE_ADMIN'],
					pageTitle: 'danceFormApp.systemSetting.detail.title'
				},
				views: {
					'content@': {
						templateUrl: 'app/system/system-setting/system-setting-detail.html',
						controller: 'SystemSettingDetailController',
						controllerAs: 'vm'
					}
				},
				resolve: {
					translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
						$translatePartialLoader.addPart('systemSetting');
						return $translate.refresh();
					}],
					entity: ['$stateParams', 'SystemSetting', function($stateParams, SystemSetting) {
						return SystemSetting.get({id: $stateParams.id}).$promise;
					}],
					previousState: ["$state", function($state) {
						var currentStateData = {
							name: $state.current.name || 'system-setting',
							params: $state.params,
							url: $state.href($state.current.name, $state.params)
						};
						return currentStateData;
					}]
				}
			})
			.state('system-setting-detail.edit', {
				parent: 'system-setting-detail',
				url: '/detail/edit',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/system/system-setting/system-setting-dialog.html',
						controller: 'SystemSettingDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: ['SystemSetting', function(SystemSetting) {
								return SystemSetting.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('^', {}, {reload: false});
					}, function() {
						$state.go('^');
					});
				}]
			})
			.state('system-setting.new', {
				parent: 'system-setting',
				url: '/new',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/system/system-setting/system-setting-dialog.html',
						controller: 'SystemSettingDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: function() {
								return {
									name: null,
									value: null,
									id: null
								};
							}
						}
					}).result.then(function() {
						$state.go('system-setting', null, {reload: 'system-setting'});
					}, function() {
						$state.go('system-setting');
					});
				}]
			})
			.state('system-setting.edit', {
				parent: 'system-setting',
				url: '/{id}/edit',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/system/system-setting/system-setting-dialog.html',
						controller: 'SystemSettingDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: ['SystemSetting', function(SystemSetting) {
								return SystemSetting.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('system-setting', null, {reload: 'system-setting'});
					}, function() {
						$state.go('^');
					});
				}]
			})
			.state('system-setting.delete', {
				parent: 'system-setting',
				url: '/{id}/delete',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/system/system-setting/system-setting-delete-dialog.html',
						controller: 'SystemSettingDeleteController',
						controllerAs: 'vm',
						size: 'md',
						resolve: {
							entity: ['SystemSetting', function(SystemSetting) {
								return SystemSetting.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('system-setting', null, {reload: 'system-setting'});
					}, function() {
						$state.go('^');
					});
				}]
			});
	}

})();
