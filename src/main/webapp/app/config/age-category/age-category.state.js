(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('age-category', {
            parent: 'config',
            url: '/age-category',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'danceFormApp.ageCategory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/config/age-category/age-categories.html',
                    controller: 'AgeCategoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ageCategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('age-category-detail', {
            parent: 'config',
            url: '/age-category/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'danceFormApp.ageCategory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/config/age-category/age-category-detail.html',
                    controller: 'AgeCategoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ageCategory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AgeCategory', function($stateParams, AgeCategory) {
                    return AgeCategory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'age-category',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('age-category-detail.edit', {
            parent: 'age-category-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/config/age-category/age-category-dialog.html',
                    controller: 'AgeCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AgeCategory', function(AgeCategory) {
                            return AgeCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('age-category.new', {
            parent: 'age-category',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/config/age-category/age-category-dialog.html',
                    controller: 'AgeCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                minAge: null,
                                maxAge: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('age-category', null, { reload: 'age-category' });
                }, function() {
                    $state.go('age-category');
                });
            }]
        })
        .state('age-category.edit', {
            parent: 'age-category',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/config/age-category/age-category-dialog.html',
                    controller: 'AgeCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AgeCategory', function(AgeCategory) {
                            return AgeCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('age-category', null, { reload: 'age-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('age-category.delete', {
            parent: 'age-category',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/config/age-category/age-category-delete-dialog.html',
                    controller: 'AgeCategoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AgeCategory', function(AgeCategory) {
                            return AgeCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('age-category', null, { reload: 'age-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
