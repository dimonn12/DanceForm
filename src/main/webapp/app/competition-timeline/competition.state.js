(function () {
    'use strict';

    angular
        .module('danceFormApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('competition-timeline', {
            parent: 'app',
            url: '/timeline',
            data: {
                authorities: [],
                pageTitle: 'global.menu.timeline'
            },
            views: {
                'content@': {
                    templateUrl: 'app/competition-timeline/timeline/timeline.html',
                    controller: 'TimelineController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('competition-timeline');
                    return $translate.refresh();
                }]
            }
        }).state('competition-timeline-show', {
            parent: 'competition-timeline',
            url: '/{id}',
            data: {
                authorities: [],
                pageTitle: 'global.menu.timeline'
            },
            views: {
                'content@': {
                    templateUrl: 'app/competition-timeline/show/details.html',
                    controller: 'CompetitionShowDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('competition-timeline');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CompetitionTimeline', function ($stateParams, CompetitionTimeline) {
                    return CompetitionTimeline.get({id: $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'competition-timeline',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        }).state('competition-timeline-category-register', {
            parent: 'competition-timeline-show',
            url: '/registry',
            data: {
                authorities: [],
                pageTitle: 'global.menu.timeline'
            },
            views: {
                'content@': {
                    templateUrl: 'app/competition-timeline/show/registry.html',
                    controller: 'CategoryRegisterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('competition-timeline');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CompetitionTimeline', function ($stateParams, CompetitionTimeline) {
                    return CompetitionTimeline.get({id: $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'competition-timeline',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        }).state('competition-timeline-category-details', {
            parent: 'competition-timeline-show',
            url: '/category/{categoryId}/couples',
            data: {
                authorities: [],
                pageTitle: 'global.menu.timeline'
            },
            views: {
                'content@': {
                    templateUrl: 'app/competition-timeline/show/registeredCouples.html',
                    controller: 'CategoryCouplesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('competition-timeline');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CompetitionTimeline', function ($stateParams, CompetitionTimeline) {
                    return CompetitionTimeline.get({id: $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'competition-timeline',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        });
    }
})();
