(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CategoryRegisterController', CategoryRegisterController);

	CategoryRegisterController.$inject = ['$scope', '$translate', '$state', 'previousState', 'entity', 'CompetitionCategory', 'DanceClass', 'RegisteredCouple', 'AlertService', 'Location', 'Organization', 'Trainer'];

	function CategoryRegisterController($scope, $translate, $state, previousState, entity, CompetitionCategory, DanceClass, RegisteredCouple, AlertService, Location, Organization, Trainer) {
		var vm = this;

		vm.currentCompetition = entity;
		vm.previousState = previousState.name;

		vm.availableCategories = [];

		vm.danceClasses = DanceClass.query();
		vm.locations = Location.query();
		vm.organizations = Organization.query();
		vm.trainers = Trainer.query(unshiftTrainer);

		vm.registerCouple = {};

		vm.registerCouple.competitionId = vm.currentCompetition.id;

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

		function update() {
			if(null != vm.registerCouple.partner1DateOfBirth && null != vm.registerCouple.partner2DateOfBirth &&
			   null != vm.registerCouple.partner1DanceClassST && null != vm.registerCouple.partner1DanceClassLA &&
			   null != vm.registerCouple.partner2DanceClassST && null != vm.registerCouple.partner2DanceClassLA &&
			   null != vm.registerCouple.partner1DanceClassST.id && null != vm.registerCouple.partner1DanceClassLA.id &&
			   null != vm.registerCouple.partner2DanceClassST.id && null != vm.registerCouple.partner2DanceClassLA.id) {
				CompetitionCategory.available({competitionId: vm.currentCompetition.id}, vm.registerCouple, onSuccess, onError);
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
			for(var i = 0; i < vm.availableCategories.length; i++) {
				var availableCategory = vm.availableCategories[i];
				if(availableCategory.selected) {
					vm.isCategorySelected = true;
					break;
				}
			}
		}

		function save() {
			vm.registerCouple.competitionCategoryIds = [];
			for(var i = 0; i < vm.availableCategories.length; i++) {
				var availableCategory = vm.availableCategories[i];
				if(availableCategory.selected) {
					vm.registerCouple.competitionCategoryIds.push(availableCategory.id);
				}
			}
			RegisteredCouple.save(vm.registerCouple, onSaveSuccess, onSaveError);
			function onSaveSuccess(result) {
				$scope.$emit('danceFormApp:competitionUpdate', result);
				vm.isSaving = false;
				$state.go('schedule-details', {id: vm.currentCompetition.id});
			}

			function onSaveError() {
				vm.isSaving = false;
				AlertService.error(error.data.message);
			}

		}

		function isCategorySelected() {
			for(var i = 0; i < vm.availableCategories.length; i++) {
				var availableCategory = vm.availableCategories[i];
				if(availableCategory.selected) {
					return true;
				}
			}
			return false;
		}

		function unshiftTrainer() {
			vm.trainers.unshift({id: -1, name: $translate.instant('danceFormApp.schedule.registry.emptyTrainer'), surname:''});
		}

		vm.datePickerOpenStatus.date = false;

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
			if(vm.registerCouple.partner1DateOfBirth == null) {
				vm.dateOptions1.datepickerMode = 'year';
			}
			if(vm.registerCouple.partner2DateOfBirth == null) {
				vm.dateOptions2.datepickerMode = 'year';
			}
		}
	}
})();
