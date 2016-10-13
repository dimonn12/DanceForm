(function () {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CategoryRegisterController', CategoryRegisterController);

    CategoryRegisterController.$inject = ['$scope', '$rootScope', '$stateParams', '$state', 'previousState', 'entity', 'Competition', 'CompetitionCategory', 'DanceClass', 'RegisteredCouple', 'AlertService'];

    function CategoryRegisterController($scope, $rootScope, $stateParams, $state, previousState, entity, Competition, CompetitionCategory, DanceClass, RegisteredCouple, AlertService) {
        var vm = this;

        vm.currentCompetition = entity;
        vm.previousState = previousState.name;

        vm.availableCategories = [];

        vm.danceClasses = [];

        vm.registerCouple = {};

        vm.save = save;
        vm.update = update;

        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;

        loadClasses();

        function load() {


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
            if (null != vm.registerCouple.partner1DateOfBirth && null != vm.registerCouple.partner2DateOfBirth &&
                null != vm.registerCouple.partner1DanceClassSTId && null != vm.registerCouple.partner1DanceClassLAId &&
                null != vm.registerCouple.partner2DanceClassSTId && null != vm.registerCouple.partner2DanceClassLAId) {
                CompetitionCategory.query({competitionId: vm.currentCompetition.id}, onSuccess, onError);
            }
            function onSuccess(data, headers) {
                vm.availableCategories = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function save() {
            vm.registerCouple.competitionCategoryIds = [];
            for (var i = 0; i < vm.availableCategories.length; i++) {
                var availableCategory = vm.availableCategories[i];
                if (availableCategory.selected) {
                    vm.registerCouple.competitionCategoryIds.push(availableCategory.id);
                }
            }
            RegisteredCouple.save(vm.registerCouple, onSaveSuccess, onSaveError);
            function onSaveSuccess(result) {
                $scope.$emit('danceFormApp:registeredCoupleUpdate', result);
                vm.isSaving = false;
                $state.go(previousState);
            }

            function onSaveError() {
                vm.isSaving = false;
                AlertService.error(error.data.message);
            }

        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
