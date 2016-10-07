(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('competition-timeline', {
            abstract: true,
            parent: 'app'
        });
    }
})();
