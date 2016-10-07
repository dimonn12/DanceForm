(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dance-class', {
            parent: 'entity',
            url: '/dance-class',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'danceFormApp.danceClass.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dance-class/dance-classes.html',
                    controller: 'DanceClassController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('danceClass');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('dance-class-detail', {
            parent: 'entity',
            url: '/dance-class/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'danceFormApp.danceClass.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dance-class/dance-class-detail.html',
                    controller: 'DanceClassDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('danceClass');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DanceClass', function($stateParams, DanceClass) {
                    return DanceClass.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dance-class',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dance-class-detail.edit', {
            parent: 'dance-class-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dance-class/dance-class-dialog.html',
                    controller: 'DanceClassDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DanceClass', function(DanceClass) {
                            return DanceClass.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dance-class.new', {
            parent: 'dance-class',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dance-class/dance-class-dialog.html',
                    controller: 'DanceClassDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                symbol: null,
                                weight: null,
                                transferScore: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dance-class', null, { reload: 'dance-class' });
                }, function() {
                    $state.go('dance-class');
                });
            }]
        })
        .state('dance-class.edit', {
            parent: 'dance-class',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dance-class/dance-class-dialog.html',
                    controller: 'DanceClassDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DanceClass', function(DanceClass) {
                            return DanceClass.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dance-class', null, { reload: 'dance-class' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dance-class.delete', {
            parent: 'dance-class',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dance-class/dance-class-delete-dialog.html',
                    controller: 'DanceClassDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DanceClass', function(DanceClass) {
                            return DanceClass.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dance-class', null, { reload: 'dance-class' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
