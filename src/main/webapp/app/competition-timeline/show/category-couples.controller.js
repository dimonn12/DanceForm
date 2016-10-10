(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CategoryRegisterController', CategoryRegisterController);

    CategoryRegisterController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Competition', 'CompetitionCategory', 'RegisteredCouple'];

    function CategoryRegisterController($scope, $rootScope, $stateParams, previousState, entity, Competition, CompetitionCategory, RegisteredCouple) {
        var vm = this;

        vm.currentCompentition = entity;
        vm.previousState = previousState.name;

        vm.category = null;
        
        vm.couples = [];

        load();
        
        function load() {
            CompetitionCategory.get({competitionId: vm.currentCompentition.id, id: $stateParams.categoryId}, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.category = data;
                loadCouples();
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadCouples() {
            RegisteredCouple.query({categoryId: vm.category.id}, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.couples = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
    }
})();
