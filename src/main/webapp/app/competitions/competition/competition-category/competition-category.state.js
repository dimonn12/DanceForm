(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('competition-category', {
            parent: 'competition',
            url: '/{competitionId}/competition-category',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'danceFormApp.competitionCategory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/competitions/competition/competition-category/competition-categories.html',
                    controller: 'CompetitionCategoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('competitionCategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'competition',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('competition-category-detail', {
            parent: 'competition-category',
            url: '/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'danceFormApp.competitionCategory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/competitions/competition/competition-category/competition-category-detail.html',
                    controller: 'CompetitionCategoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('competitionCategory');
                    return $translate.refresh();
                }],
                entity: ['$state', '$stateParams', 'CompetitionCategory', function($state, $stateParams, CompetitionCategory) {
                    return CompetitionCategory.get({id : $stateParams.id, competitionId : $state.params.competitionId}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'competition-category',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('competition-category-detail.edit', {
            parent: 'competition-category-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/competitions/competition/competition-category/competition-category-dialog.html',
                    controller: 'CompetitionCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CompetitionCategory', function(CompetitionCategory) {
                            return CompetitionCategory.get({id : $stateParams.id, competitionId : $state.params.competitionId}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('competition-category.new', {
            parent: 'competition-category',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/competitions/competition/competition-category/competition-category-dialog.html',
                    controller: 'CompetitionCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                active: null,
                                checkMinAge: null,
                                checkMaxAge: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('competition-category', null, { reload: 'competition-category' });
                }, function() {
                    $state.go('competition-category');
                });
            }]
        })
        .state('competition-category.edit', {
            parent: 'competition-category',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/competitions/competition/competition-category/competition-category-dialog.html',
                    controller: 'CompetitionCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CompetitionCategory', function(CompetitionCategory) {
                            return CompetitionCategory.get({id : $stateParams.id, competitionId : $state.params.competitionId}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('competition-category', null, { reload: 'competition-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('competition-category.delete', {
            parent: 'competition-category',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/competitions/competition/competition-category/competition-category-delete-dialog.html',
                    controller: 'CompetitionCategoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CompetitionCategory', function(CompetitionCategory) {
                            return CompetitionCategory.get({id : $stateParams.id, competitionId : $state.params.competitionId}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('competition-category', null, { reload: 'competition-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
