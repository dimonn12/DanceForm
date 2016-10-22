(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider
			.state('organization', {
				parent: 'config',
				url: '/config/organization?page&sort&search',
				data: {
					authorities: ['ROLE_ADMIN'],
					pageTitle: 'danceFormApp.organization.home.title'
				},
				views: {
					'content@': {
						templateUrl: 'app/config/organization/organizations.html',
						controller: 'OrganizationController',
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
						$translatePartialLoader.addPart('organization');
						$translatePartialLoader.addPart('global');
						return $translate.refresh();
					}]
				}
			})
			.state('organization-detail', {
				parent: 'config',
				url: '/config/organization/{id}',
				data: {
					authorities: ['ROLE_ADMIN'],
					pageTitle: 'danceFormApp.organization.detail.title'
				},
				views: {
					'content@': {
						templateUrl: 'app/config/organization/organization-detail.html',
						controller: 'OrganizationDetailController',
						controllerAs: 'vm'
					}
				},
				resolve: {
					translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
						$translatePartialLoader.addPart('organization');
						return $translate.refresh();
					}],
					entity: ['$stateParams', 'Organization', function($stateParams, Organization) {
						return Organization.get({id: $stateParams.id}).$promise;
					}],
					previousState: ["$state", function($state) {
						var currentStateData = {
							name: $state.current.name || 'organization',
							params: $state.params,
							url: $state.href($state.current.name, $state.params)
						};
						return currentStateData;
					}]
				}
			})
			.state('organization-detail.edit', {
				parent: 'organization-detail',
				url: '/detail/edit',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/config/organization/organization-dialog.html',
						controller: 'OrganizationDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: ['Organization', function(Organization) {
								return Organization.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('^', {}, {reload: false});
					}, function() {
						$state.go('^');
					});
				}]
			})
			.state('organization.new', {
				parent: 'organization',
				url: '/new',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/config/organization/organization-dialog.html',
						controller: 'OrganizationDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: function() {
								return {
									name: null,
									visible: null,
									id: null
								};
							}
						}
					}).result.then(function() {
						$state.go('organization', null, {reload: 'organization'});
					}, function() {
						$state.go('organization');
					});
				}]
			})
			.state('organization.edit', {
				parent: 'organization',
				url: '/{id}/edit',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/config/organization/organization-dialog.html',
						controller: 'OrganizationDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: ['Organization', function(Organization) {
								return Organization.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('organization', null, {reload: 'organization'});
					}, function() {
						$state.go('^');
					});
				}]
			})
			.state('organization.delete', {
				parent: 'organization',
				url: '/{id}/delete',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/config/organization/organization-delete-dialog.html',
						controller: 'OrganizationDeleteController',
						controllerAs: 'vm',
						size: 'md',
						resolve: {
							entity: ['Organization', function(Organization) {
								return Organization.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('organization', null, {reload: 'organization'});
					}, function() {
						$state.go('^');
					});
				}]
			});
	}

})();
