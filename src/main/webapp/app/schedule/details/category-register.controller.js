(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CategoryRegisterController', CategoryRegisterController);

	CategoryRegisterController.$inject = ['$scope', '$q', '$translate', '$state', '$timeout', 'previousState', 'entity', 'CompetitionCategory', 'DanceClass', 'RegisteredCouple', 'AlertService', 'Location', 'Organization', 'Trainer'];

	function CategoryRegisterController($scope, $q, $translate, $state, $timeout, previousState, entity, CompetitionCategory, DanceClass, RegisteredCouple, AlertService, Location, Organization, Trainer) {
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

		vm.registerCouple.competitionId = vm.currentCompetition.id;

		vm.save = save;
		vm.update = update;

		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;

		vm.isCategorySelected = false;
		vm.updateSelectedCategory = updateSelectedCategory;

		vm.soloCouple = false;
		vm.updateSolo = updateSolo;

		vm.isValidForm = isValidForm;

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
			if(null != vm.registerCouple.partner1DateOfBirth && null != vm.registerCouple.partner1DanceClassST &&
			   null != vm.registerCouple.partner1DanceClassST.id && null != vm.registerCouple.partner1DanceClassLA &&
			   null != vm.registerCouple.partner1DanceClassLA.id &&
			   (vm.soloCouple || (null != vm.registerCouple.partner2DateOfBirth && null != vm.registerCouple.partner2DanceClassST &&
								  null != vm.registerCouple.partner2DanceClassLA && null != vm.registerCouple.partner2DanceClassST.id &&
								  null != vm.registerCouple.partner2DanceClassLA.id))) {
				vm.registerCouple.isSoloCouple = vm.soloCouple;
				CompetitionCategory.available({
					competitionId: vm.currentCompetition.id,
					soloCouple: vm.soloCouple
				}, vm.registerCouple, onSuccess, onError);
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
			vm.registerCouple.soloCouple = vm.soloCouple;
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

		function updateSolo() {
			if(vm.soloCouple) {
				vm.registerCouple.partner2DateOfBirth = null;
				vm.registerCouple.partner2Surname = null;
				vm.registerCouple.partner2Name = null;
				vm.registerCouple.partner2DanceClassLA = null;
				vm.registerCouple.partner2DanceClassST = null;

				$('#field_partner2surname').attr('required', true);
				$('#field_partner2surname').attr('disabled', true);

				$('#field_partner2name').attr('required', true);
				$('#field_partner2name').attr('disabled', true);

				$('#field_partner2DateOfBirth').attr('required', true);
				$('#field_partner2DateOfBirth').attr('disabled', true);

				$('#field_partner2DanceClassLA').attr('required', true);
				$('#field_partner2DanceClassLA').attr('disabled', true);

				$('#field_partner2DanceClassST').attr('required', true);
				$('#field_partner2DanceClassST').attr('disabled', true);
			} else {
				$('#field_partner2surname').removeAttr('required');
				$('#field_partner2surname').removeAttr('disabled');

				$('#field_partner2name').removeAttr('required');
				$('#field_partner2name').removeAttr('disabled');

				$('#field_partner2DateOfBirth').removeAttr('required');
				$('#field_partner2DateOfBirth').removeAttr('disabled');

				$('#field_partner2DanceClassLA').removeAttr('required');
				$('#field_partner2DanceClassLA').removeAttr('disabled');

				$('#field_partner2DanceClassST').removeAttr('required');
				$('#field_partner2DanceClassST').removeAttr('disabled');
			}
			update();
			updateSelectedCategory();
		}

		function isValidForm() {
			return null != vm.registerCouple.partner1Surname && null != vm.registerCouple.partner1Name &&
				   vm.registerCouple.partner1Surname.trim().length > 0 && vm.registerCouple.partner1Name.trim().length > 0 &&
				   null != vm.registerCouple.trainer1 && null != vm.registerCouple.location &&
				   null != vm.registerCouple.organization &&
				   vm.registerCouple.trainer1.trim().length > 0 && vm.registerCouple.location.trim().length > 0 &&
				   vm.registerCouple.organization.trim().length > 0 &&
				   null != vm.registerCouple.partner1DateOfBirth && null != vm.registerCouple.partner1DanceClassST &&
				   null != vm.registerCouple.partner1DanceClassST.id && null != vm.registerCouple.partner1DanceClassLA &&
				   null != vm.registerCouple.partner1DanceClassLA.id &&
				   (vm.soloCouple || (null != vm.registerCouple.partner2Name && null != vm.registerCouple.partner2Surname &&
									  vm.registerCouple.partner2Name.trim().length > 0 && vm.registerCouple.partner2Surname.trim().length > 0 &&
									  null != vm.registerCouple.partner2DateOfBirth && null != vm.registerCouple.partner2DanceClassST &&
									  null != vm.registerCouple.partner2DanceClassLA && null != vm.registerCouple.partner2DanceClassST.id &&
									  null != vm.registerCouple.partner2DanceClassLA.id))
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
		}
	}
})();
