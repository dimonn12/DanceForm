(function () {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CategoryRegisterController', CategoryRegisterController);

    CategoryRegisterController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Competition', 'CompetitionCategory', 'DanceClass', 'RegisteredCouple', 'AlertService'];

    function CategoryRegisterController($scope, $rootScope, $stateParams, previousState, entity, Competition, CompetitionCategory, DanceClass, RegisteredCouple, AlertService) {
        var vm = this;

        vm.currentCompetition = entity;
        vm.previousState = previousState.name;

        vm.availableCategories = [];

        vm.danceClasses = [];

        vm.registerCouple = {};
        
        vm.save = save;

        load();
        loadClasses();

        function load() {
            CompetitionCategory.query({competitionId: vm.currentCompetition.id}, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.availableCategories = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadClasses() {
            DanceClass.query({}, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.danceClasses = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function update() {
            console.log('update');
        }

        function save() {
            RegisteredCouple.save(vm.registerCouple, onSaveSuccess, onSaveError);
            function onSaveSuccess (result) {
                $scope.$emit('danceFormApp:registeredCoupleUpdate', result);
                vm.isSaving = false;
            }

            function onSaveError () {
                vm.isSaving = false;
                AlertService.error(error.data.message);
            }

        }
    }
})();
