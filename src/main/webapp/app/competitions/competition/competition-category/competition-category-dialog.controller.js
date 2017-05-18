(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionCategoryDialogController', CompetitionCategoryDialogController);

	CompetitionCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CompetitionCategory', 'Competition', 'DanceClass', 'AgeCategory', 'DanceCategory'];

	function CompetitionCategoryDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, CompetitionCategory, Competition, DanceClass, AgeCategory, DanceCategory) {
		var vm = this;

		vm.competitionCategory = entity;
		vm.clear = clear;
		vm.save = save;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.competitions = Competition.query();
		vm.danceClasses = DanceClass.query();
		//vm.agecategories = AgeCategory.query();
		vm.dancecategories = DanceCategory.query();

		vm.agecategories = [];
		AgeCategory.query(onAgeCategoriesLoad);

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function save() {
			vm.isSaving = true;
			if(vm.competitionCategory.id !== null) {
				CompetitionCategory.update({competitionId: $stateParams.competitionId}, vm.competitionCategory, onSaveSuccess, onSaveError);
			} else {
				CompetitionCategory.save({competitionId: $stateParams.competitionId}, vm.competitionCategory, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess(result) {
			if(null != result.maxDanceClass.id) {
				result.danceClasses = [];
				var maxDanceClass = null;
				for(var j = 0; j < vm.danceClasses.length; j++) {
					if(vm.danceClasses[j].id == result.maxDanceClass.id) {
						maxDanceClass = vm.danceClasses[j];
						break;
					}
				}
				for(var j = 0; j < vm.danceClasses.length; j++) {
					if(vm.danceClasses[j].weight <= maxDanceClass.weight) {
						result.danceClasses.push(vm.danceClasses[j]);
					}
				}
			}
			$scope.$emit('danceFormApp:competitionCategoryUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		function onAgeCategoriesLoad(data) {
			for(var i = 0; i < data.length; i++) {
				vm.agecategories.push({id: data[i].id, name: data[i].name});
			}
		}

		vm.datePickerOpenStatus.date = false;

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}

	}
})();
