(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dance-category', {
            parent: 'entity',
            url: '/dance-category',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'danceFormApp.danceCategory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dance-category/dance-categories.html',
                    controller: 'DanceCategoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('danceCategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('dance-category-detail', {
            parent: 'entity',
            url: '/dance-category/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'danceFormApp.danceCategory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dance-category/dance-category-detail.html',
                    controller: 'DanceCategoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('danceCategory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DanceCategory', function($stateParams, DanceCategory) {
                    return DanceCategory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dance-category',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dance-category-detail.edit', {
            parent: 'dance-category-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dance-category/dance-category-dialog.html',
                    controller: 'DanceCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DanceCategory', function(DanceCategory) {
                            return DanceCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dance-category.new', {
            parent: 'dance-category',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dance-category/dance-category-dialog.html',
                    controller: 'DanceCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dance-category', null, { reload: 'dance-category' });
                }, function() {
                    $state.go('dance-category');
                });
            }]
        })
        .state('dance-category.edit', {
            parent: 'dance-category',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dance-category/dance-category-dialog.html',
                    controller: 'DanceCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DanceCategory', function(DanceCategory) {
                            return DanceCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dance-category', null, { reload: 'dance-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dance-category.delete', {
            parent: 'dance-category',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dance-category/dance-category-delete-dialog.html',
                    controller: 'DanceCategoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DanceCategory', function(DanceCategory) {
                            return DanceCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dance-category', null, { reload: 'dance-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
