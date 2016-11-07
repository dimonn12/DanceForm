(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.config(stateConfig);

	stateConfig.$inject = ['$stateProvider'];

	function stateConfig($stateProvider) {
		$stateProvider
			.state('message', {
				parent: 'system',
				url: '/message?page&sort&search',
				data: {
					authorities: ['ROLE_ADMIN'],
					pageTitle: 'danceFormApp.message.home.title'
				},
				views: {
					'content@': {
						templateUrl: 'app/system/message/messages.html',
						controller: 'MessageController',
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
						$translatePartialLoader.addPart('message');
						$translatePartialLoader.addPart('global');
						return $translate.refresh();
					}]
				}
			})
			.state('message-detail', {
				parent: 'system',
				url: '/message/{id}',
				data: {
					authorities: ['ROLE_ADMIN'],
					pageTitle: 'danceFormApp.message.detail.title'
				},
				views: {
					'content@': {
						templateUrl: 'app/system/message/message-detail.html',
						controller: 'MessageDetailController',
						controllerAs: 'vm'
					}
				},
				resolve: {
					translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
						$translatePartialLoader.addPart('message');
						return $translate.refresh();
					}],
					entity: ['$stateParams', 'Message', function($stateParams, Message) {
						return Message.get({id: $stateParams.id}).$promise;
					}],
					previousState: ["$state", function($state) {
						var currentStateData = {
							name: $state.current.name || 'message',
							params: $state.params,
							url: $state.href($state.current.name, $state.params)
						};
						return currentStateData;
					}]
				}
			})
			.state('message-detail.edit', {
				parent: 'message-detail',
				url: '/detail/edit',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/system/message/message-dialog.html',
						controller: 'MessageDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: ['Message', function(Message) {
								return Message.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('^', {}, {reload: false});
					}, function() {
						$state.go('^');
					});
				}]
			})
			.state('message.new', {
				parent: 'message',
				url: '/new',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/system/message/message-dialog.html',
						controller: 'MessageDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: function() {
								return {
									fromEmail: null,
									subject: null,
									content: null,
									dateCreated: null,
									sent: null,
									dateSent: null,
									id: null
								};
							}
						}
					}).result.then(function() {
						$state.go('message', null, {reload: 'message'});
					}, function() {
						$state.go('message');
					});
				}]
			})
			.state('message.edit', {
				parent: 'message',
				url: '/{id}/edit',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/system/message/message-dialog.html',
						controller: 'MessageDialogController',
						controllerAs: 'vm',
						backdrop: 'static',
						size: 'lg',
						resolve: {
							entity: ['Message', function(Message) {
								return Message.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('message', null, {reload: 'message'});
					}, function() {
						$state.go('^');
					});
				}]
			})
			.state('message.delete', {
				parent: 'message',
				url: '/{id}/delete',
				data: {
					authorities: ['ROLE_ADMIN']
				},
				onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
					$uibModal.open({
						templateUrl: 'app/system/message/message-delete-dialog.html',
						controller: 'MessageDeleteController',
						controllerAs: 'vm',
						size: 'md',
						resolve: {
							entity: ['Message', function(Message) {
								return Message.get({id: $stateParams.id}).$promise;
							}]
						}
					}).result.then(function() {
						$state.go('message', null, {reload: 'message'});
					}, function() {
						$state.go('^');
					});
				}]
			});
	}

})();
