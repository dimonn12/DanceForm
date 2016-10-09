(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CategoryRegisterController', CategoryRegisterController);

    CategoryRegisterController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Competition', 'CompetitionCategory'];

    function CategoryRegisterController($scope, $rootScope, $stateParams, previousState, entity, Competition, CompetitionCategory) {
        var vm = this;

        vm.currentCompentition = entity;
        vm.previousState = previousState.name;

        vm.categories = [];

        var unsubscribe = $rootScope.$on('danceFormApp:competitionUpdate', function(event, result) {
            vm.competition = result;
        });
        $scope.$on('$destroy', unsubscribe);

        load();

        function load() {
            CompetitionCategory.get({competitionId: vm.currentCompentition.id, id: $stateParams.id}, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.categories = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
