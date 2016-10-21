(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CompetitionCategoryController', CompetitionCategoryController);

    CompetitionCategoryController.$inject = ['$scope', '$state', '$stateParams', 'previousState', 'CompetitionCategory'];

    function CompetitionCategoryController ($scope, $state, $stateParams, previousState, CompetitionCategory) {
        var vm = this;

        vm.previousState = previousState;

        vm.competitionCategories = [];

        loadAll();

        function loadAll() {
            CompetitionCategory.query({competitionId : $stateParams.competitionId}, function(result) {
                vm.competitionCategories = result;
            });
        }
    }
})();
