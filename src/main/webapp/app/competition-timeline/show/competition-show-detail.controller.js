(function () {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CompetitionShowDetailController', CompetitionShowDetailController);

    CompetitionShowDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Competition', 'CompetitionCategory'];

    function CompetitionShowDetailController($scope, $rootScope, $stateParams, previousState, entity, Competition, CompetitionCategory) {
        var vm = this;

        vm.currentCompetition = entity;
        vm.previousState = previousState.name;

        vm.categories = entity.competitionCategoryDTOs;

        vm.totalRegisteredCount = 0;

        for (var i = 0; i < vm.categories.length; i++) {
            vm.totalRegisteredCount += vm.categories[i].registeredCouplesCount;
        }

        var unsubscribe = $rootScope.$on('danceFormApp:competitionUpdate', function (event, result) {
            vm.competition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
