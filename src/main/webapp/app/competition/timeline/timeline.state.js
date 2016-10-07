(function() {
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
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/competition/timeline/timeline.html',
                    controller: 'TimelineController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('competition-timeline');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
