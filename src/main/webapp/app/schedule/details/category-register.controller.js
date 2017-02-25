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
		vm.organizationNames = [];
		vm.organizations = Organization.query(organizationOnLoad);
		vm.trainerNames = [];
		vm.trainers = Trainer.query(trainerOnLoad);

		vm.registerCouple = {};
		vm.registerSolo = {};

		vm.registerCouple.competitionId = null;

		vm.save = save;
		vm.update = update;

		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;

		vm.isCategorySelected = false;
		vm.updateSelectedCategory = updateSelectedCategory;

		vm.soloCouple = false;
		vm.updateSolo = updateSolo;

		vm.isValidForm = isValidForm;

		vm.isReadOnlyForm = isReadOnlyForm;

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

		vm.dateOptions3 = {
			maxDate: new Date(),
			minDate: new Date(1930, 1, 1),
			datepickerMode: 'year',
			showWeeks: false
		};

		load();

		function load() {
			vm.registerCouple.competitionId = vm.currentCompetition.id;
			setReadOnlyForm();
		}

		function setReadOnlyForm() {
			if(vm.isReadOnlyForm()) {
				$('form input').attr('disabled', true);
				$('form select').attr('disabled', true);
				$('form button').attr('disabled', true);
				$('form input').attr('readonly', true);
				$('form select').attr('readonly', true);
				$('form oi-select').attr('ng-disabled', true);
			}
		}

		function isReadOnlyForm() {
			return !vm.currentCompetition.registrationOpen || vm.currentCompetition.registrationClosed;
		}

		function update() {
			if(vm.isReadOnlyForm()) {
				return;
			}
			if((null != vm.registerCouple.partner2DateOfBirth &&
				null != vm.registerCouple.partner2DanceClassST &&
				null != vm.registerCouple.partner2DanceClassLA &&
				null != vm.registerCouple.partner2DanceClassST.id &&
				null != vm.registerCouple.partner2DanceClassLA.id &&
				null != vm.registerCouple.partner1DateOfBirth &&
				null != vm.registerCouple.partner1DanceClassST &&
				null != vm.registerCouple.partner1DanceClassST.id &&
				null != vm.registerCouple.partner1DanceClassLA &&
				null != vm.registerCouple.partner1DanceClassLA.id) ||
			   (vm.soloCouple &&
				null != vm.registerSolo.dateOfBirth &&
				null != vm.registerSolo.danceClassST &&
				null != vm.registerSolo.danceClassLA &&
				null != vm.registerSolo.danceClassST.id &&
				null != vm.registerSolo.danceClassLA.id)) {
				vm.registerCouple.isSoloCouple = vm.soloCouple;
				vm.registerCouple.competitionId = vm.currentCompetition.id;
				CompetitionCategory.available({
					competitionId: vm.currentCompetition.id,
					soloCouple: vm.soloCouple
				}, vm.soloCouple ? convertSoloToCouple(vm.registerSolo) : vm.registerCouple, onSuccess, onError);
			}
			else {
				vm.availableCategories = [];
			}
			function onSuccess(data) {
				if(vm.soloCouple) {
					vm.registerCouple.partner2DateOfBirth = null;
					vm.registerCouple.partner2Surname = null;
					vm.registerCouple.partner2Name = null;
					vm.registerCouple.partner2DanceClassLA = null;
					vm.registerCouple.partner2DanceClassST = null;

					vm.registerCouple.partner1DateOfBirth = null;
					vm.registerCouple.partner1Surname = null;
					vm.registerCouple.partner1Name = null;
					vm.registerCouple.partner1DanceClassLA = null;
					vm.registerCouple.partner1DanceClassST = null;
				} else {
					vm.registerSolo.dateOfBirth = null;
					vm.registerSolo.surname = null;
					vm.registerSolo.name = null;
					vm.registerSolo.danceClassLA = null;
					vm.registerSolo.danceClassST = null;
					vm.registerSolo.location = null;
					vm.registerSolo.organization = null;
					vm.registerSolo.trainer1 = null;
					vm.registerSolo.trainer2 = null;
				}
				vm.availableCategories = data;
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
			if(vm.soloCouple) {
				vm.registerCouple = convertSoloToCouple(vm.registerSolo);
			}
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

		function convertSoloToCouple(solo) {
			var couple = {};
			couple.partner1DateOfBirth = solo.dateOfBirth;
			couple.partner1Surname = solo.surname;
			couple.partner1Name = solo.name;
			couple.partner1DanceClassLA = solo.danceClassLA;
			couple.partner1DanceClassST = solo.danceClassST;
			couple.location = solo.location;
			couple.organization = solo.organization;
			couple.trainer1 = solo.trainer1;
			couple.trainer2 = solo.trainer2;
			couple.soloCouple = vm.soloCouple;
			couple.competitionId = vm.currentCompetition.id;
			return couple;
		}

		function updateSolo() {
			if(vm.soloCouple) {
				vm.registerSolo.trainer1 = vm.registerCouple.trainer1;
				vm.registerSolo.trainer2 = vm.registerCouple.trainer2;
				vm.registerSolo.location = vm.registerCouple.location;
				vm.registerSolo.organization = vm.registerCouple.organization;
				vm.registerCouple = {};
			} else {
				vm.registerCouple.trainer1 = vm.registerSolo.trainer1;
				vm.registerCouple.trainer2 = vm.registerSolo.trainer2;
				vm.registerCouple.location = vm.registerSolo.location;
				vm.registerCouple.organization = vm.registerSolo.organization;
				vm.registerSolo = {}
			}
			update();
			updateSelectedCategory();
		}

		function isValidForm() {
			return !vm.isReadOnlyForm() &&
				   ((null != vm.registerCouple.partner2Surname &&
					 null != vm.registerCouple.partner2Name &&
					 vm.registerCouple.partner2Surname.trim().length > 0 &&
					 vm.registerCouple.partner2Name.trim().length > 0 &&
					 null != vm.registerCouple.trainer1 &&
					 null != vm.registerCouple.location &&
					 null != vm.registerCouple.organization &&
					 vm.registerCouple.trainer1.trim().length > 0 &&
					 vm.registerCouple.location.trim().length > 0 &&
					 vm.registerCouple.organization.trim().length > 0 &&
					 null != vm.registerCouple.partner2DateOfBirth &&
					 null != vm.registerCouple.partner2DanceClassST &&
					 null != vm.registerCouple.partner2DanceClassST.id &&
					 null != vm.registerCouple.partner2DanceClassLA &&
					 null != vm.registerCouple.partner2DanceClassLA.id &&
					 null != vm.registerCouple.partner1Name &&
					 null != vm.registerCouple.partner1Surname &&
					 vm.registerCouple.partner1Name.trim().length > 0 &&
					 vm.registerCouple.partner1Surname.trim().length > 0 &&
					 null != vm.registerCouple.partner1DateOfBirth &&
					 null != vm.registerCouple.partner1DanceClassST &&
					 null != vm.registerCouple.partner1DanceClassLA &&
					 null != vm.registerCouple.partner1DanceClassST.id &&
					 null != vm.registerCouple.partner1DanceClassLA.id) ||
					(vm.soloCouple &&
					 null != vm.registerSolo.name &&
					 null != vm.registerSolo.surname &&
					 vm.registerSolo.name.trim().length > 0 &&
					 vm.registerSolo.surname.trim().length > 0 &&
					 null != vm.registerSolo.dateOfBirth &&
					 null != vm.registerSolo.danceClassST &&
					 null != vm.registerSolo.danceClassLA &&
					 null != vm.registerSolo.danceClassST.id &&
					 null != vm.registerSolo.danceClassLA.id &&
					 null != vm.registerSolo.trainer1 &&
					 null != vm.registerSolo.location &&
					 null != vm.registerSolo.organization));
		}

		function trainerOnLoad() {
			vm.trainers.unshift({
				id: -1,
				name: $translate.instant('danceFormApp.schedule.registry.emptyTrainer'),
				surname: ''
			});
			for(var i = 0; i < vm.trainers.length; i++) {
				vm.trainerNames.push(vm.trainers[i].surname + ' ' + vm.trainers[i].name);
			}
		}

		function organizationOnLoad() {
			for(var i = 0; i < vm.organizations.length; i++) {
				vm.organizationNames.push(vm.organizations[i].name);
			}
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
			if(vm.registerSolo.dateOfBirth == null) {
				vm.dateOptions3.datepickerMode = 'year';
			}
		}

		function onError(error) {
			AlertService.error(error.data.message);
		}
	}
})();
