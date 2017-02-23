(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider
			.state('language-string', {
				parent: 'system',
				url: '/language-string?page&sort&search',
				data: {
					authorities: ['ROLE_ADMIN'],
					pageTitle: 'danceFormApp.languageString.home.title'
				},
				views: {
					'content@': {
						templateUrl: 'app/system/language-string/language-strings.html',
						controller: 'LanguageStringController',
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
						$translatePartialLoader.addPart('languageString');
						$translatePartialLoader.addPart('global');
						return $translate.refresh();
					}]
				}
			})
			.state('language-string-detail', {
				parent: 'system',
				url: '/language-string/{id}',
				data: {
					authorities: ['ROLE_ADMIN'],
					pageTitle: 'danceFormApp.languageString.detail.title'
				},
				views: {
					'content@': {
						templateUrl: 'app/system/language-string/language-string-detail.html',
						controller: 'LanguageStringDetailController',
						controllerAs: 'vm'
					}
				},
				resolve: {
					translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
						$translatePartialLoader.addPart('languageString');
						return $translate.refresh();
					}],
					entity: ['$stateParams', 'LanguageString', function($stateParams, LanguageString) {
						return LanguageString.get({id: $stateParams.id}).$promise;
					}],
					previousState: ["$state", function($state) {
						var currentStateData = {
							name: $state.current.name || 'language-string',
							params: $state.params,
							url: $state.href($state.current.name, $state.params)
						};
						return currentStateData;
					}]
				}
			})
			.state('language-string-detail.edit', {
				parent: 'language-string-detail',
				url: '/detail/edit',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/system/language-string/language-string-dialog.html',
						controller: 'LanguageStringDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: ['LanguageString', function(LanguageString) {
								return LanguageString.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('^', {}, {reload: false});
					}, function() {
						$state.go('^');
					});
				}]
			})
			.state('language-string.new', {
				parent: 'language-string',
				url: '/new',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/system/language-string/language-string-dialog.html',
						controller: 'LanguageStringDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: function() {
								return {
									lang: null,
									name: null,
									value: null,
									id: null
								};
							}
						}
					}).result.then(function() {
						$state.go('language-string', null, {reload: 'language-string'});
					}, function() {
						$state.go('language-string');
					});
				}]
			})
			.state('language-string.edit', {
				parent: 'language-string',
				url: '/{id}/edit',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/system/language-string/language-string-dialog.html',
						controller: 'LanguageStringDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: ['LanguageString', function(LanguageString) {
								return LanguageString.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('language-string', null, {reload: 'language-string'});
					}, function() {
						$state.go('^');
					});
				}]
			})
			.state('language-string.delete', {
				parent: 'language-string',
				url: '/{id}/delete',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/system/language-string/language-string-delete-dialog.html',
						controller: 'LanguageStringDeleteController',
						controllerAs: 'vm',
						size: 'md',
						resolve: {
							entity: ['LanguageString', function(LanguageString) {
								return LanguageString.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('language-string', null, {reload: 'language-string'});
					}, function() {
						$state.go('^');
					});
				}]
			});
	}

})();
