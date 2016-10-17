(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trainer', {
            parent: 'config',
            url: '/config/trainer?page&sort&search',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'danceFormApp.trainer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/config/trainer/trainers.html',
                    controller: 'TrainerController',
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
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trainer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('trainer-detail', {
            parent: 'config',
            url: '/config/trainer/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'danceFormApp.trainer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/config/trainer/trainer-detail.html',
                    controller: 'TrainerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trainer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Trainer', function($stateParams, Trainer) {
                    return Trainer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trainer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trainer-detail.edit', {
            parent: 'trainer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/config/trainer/trainer-dialog.html',
                    controller: 'TrainerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trainer', function(Trainer) {
                            return Trainer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trainer.new', {
            parent: 'trainer',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/config/trainer/trainer-dialog.html',
                    controller: 'TrainerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                surname: null,
                                visible: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('trainer', null, { reload: 'trainer' });
                }, function() {
                    $state.go('trainer');
                });
            }]
        })
        .state('trainer.edit', {
            parent: 'trainer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/config/trainer/trainer-dialog.html',
                    controller: 'TrainerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trainer', function(Trainer) {
                            return Trainer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trainer', null, { reload: 'trainer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trainer.delete', {
            parent: 'trainer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/config/trainer/trainer-delete-dialog.html',
                    controller: 'TrainerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Trainer', function(Trainer) {
                            return Trainer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trainer', null, { reload: 'trainer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
