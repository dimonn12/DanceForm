(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('registered-couple', {
            parent: 'entity',
            url: '/registered-couple',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'danceFormApp.registeredCouple.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/registered-couple/registered-couples.html',
                    controller: 'RegisteredCoupleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('registeredCouple');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('registered-couple-detail', {
            parent: 'entity',
            url: '/registered-couple/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'danceFormApp.registeredCouple.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/registered-couple/registered-couple-detail.html',
                    controller: 'RegisteredCoupleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('registeredCouple');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RegisteredCouple', function($stateParams, RegisteredCouple) {
                    return RegisteredCouple.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'registered-couple',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('registered-couple-detail.edit', {
            parent: 'registered-couple-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registered-couple/registered-couple-dialog.html',
                    controller: 'RegisteredCoupleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RegisteredCouple', function(RegisteredCouple) {
                            return RegisteredCouple.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('registered-couple.new', {
            parent: 'registered-couple',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registered-couple/registered-couple-dialog.html',
                    controller: 'RegisteredCoupleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                partner1Name: null,
                                partner1Surname: null,
                                partner2Name: null,
                                partner2Surname: null,
                                partner1DateOfBirth: null,
                                partner2DateOfBirth: null,
                                partner1Organization: null,
                                partner1Location: null,
                                trainer1: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('registered-couple', null, { reload: 'registered-couple' });
                }, function() {
                    $state.go('registered-couple');
                });
            }]
        })
        .state('registered-couple.edit', {
            parent: 'registered-couple',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registered-couple/registered-couple-dialog.html',
                    controller: 'RegisteredCoupleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RegisteredCouple', function(RegisteredCouple) {
                            return RegisteredCouple.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('registered-couple', null, { reload: 'registered-couple' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('registered-couple.delete', {
            parent: 'registered-couple',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/registered-couple/registered-couple-delete-dialog.html',
                    controller: 'RegisteredCoupleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RegisteredCouple', function(RegisteredCouple) {
                            return RegisteredCouple.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('registered-couple', null, { reload: 'registered-couple' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
