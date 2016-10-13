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

        vm.isCategorySelected = false;
        vm.updateSelectedCategory = updateSelectedCategory;

        vm.dateOptions1 = {
            maxDate: new Date(),
            minDate: new Date(1930, 1, 1),
            datepickerMode: 'year',
            showWeeks: false
        };

        vm.dateOptions2 = {
                    maxDate: new Date(),
                    minDate: new Date(1930, 1, 1),
                    datepickerMode: 'year',
                    showWeeks: false
                };

        loadClasses();

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
            } else {
                vm.availableCategories = [];
            }
            function onSuccess(data, headers) {
                vm.availableCategories = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function updateSelectedCategory() {
            vm.isCategorySelected = false;
            for (var i = 0; i < vm.availableCategories.length; i++) {
                var availableCategory = vm.availableCategories[i];
                if (availableCategory.selected) {
                    vm.isCategorySelected = true;
                    break;
                }
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
                $scope.$emit('danceFormApp:competitionUpdate', result);
                vm.isSaving = false;
                $state.go('competition-timeline-show', {id: vm.currentCompetition.id});
            }

            function onSaveError() {
                vm.isSaving = false;
                AlertService.error(error.data.message);
            }

        }

        function isCategorySelected() {
            for (var i = 0; i < vm.availableCategories.length; i++) {
                var availableCategory = vm.availableCategories[i];
                if (availableCategory.selected) {
                    return true;
                }
            }
            return false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
            if (vm.registerCouple.partner1DateOfBirth == null) {
                vm.dateOptions1.datepickerMode = 'year';
            }
            if (vm.registerCouple.partner2DateOfBirth == null) {
                            vm.dateOptions2.datepickerMode = 'year';
                        }
        }
    }
})();
