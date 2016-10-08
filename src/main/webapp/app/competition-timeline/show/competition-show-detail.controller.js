(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CompetitionShowDetailController', CompetitionShowDetailController);

    CompetitionShowDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Competition'];

    function CompetitionShowDetailController($scope, $rootScope, $stateParams, previousState, entity, Competition) {
        var vm = this;

        vm.currentCompentition = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('danceFormApp:competitionUpdate', function(event, result) {
            vm.competition = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
