(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CompetitionShowDetailController', CompetitionShowDetailController);

    CompetitionShowDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Competition', 'CompetitionCategory'];

    function CompetitionShowDetailController($scope, $rootScope, $stateParams, previousState, entity, Competition, CompetitionCategory) {
        var vm = this;

        vm.currentCompentition = entity;
        vm.previousState = previousState.name;
        
        vm.categories = [];

        var unsubscribe = $rootScope.$on('danceFormApp:competitionUpdate', function(event, result) {
            vm.competition = result;
        });
        $scope.$on('$destroy', unsubscribe);

        loadCategories();

        function loadCategories() {
            CompetitionCategory.query({competitionId: vm.currentCompentition.id}, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.categories = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
