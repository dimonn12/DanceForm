(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CompetitionCategoryController', CompetitionCategoryController);

    CompetitionCategoryController.$inject = ['$scope', '$state', '$stateParams', 'CompetitionCategory'];

    function CompetitionCategoryController ($scope, $state, $stateParams, CompetitionCategory) {
        var vm = this;
        
        vm.competitionCategories = [];

        loadAll();

        function loadAll() {
            CompetitionCategory.query({competitionId : $stateParams.competitionId}, function(result) {
                vm.competitionCategories = result;
            });
        }
    }
})();
