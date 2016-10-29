(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionDialogController', CompetitionDialogController);

	CompetitionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Competition'];

	function CompetitionDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Competition) {
		var vm = this;

		vm.competition = entity;
		vm.clear = clear;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.save = save;

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function save() {
			vm.isSaving = true;
			if(vm.competition.id !== null) {
				Competition.update(vm.competition, onSaveSuccess, onSaveError);
			} else {
				Competition.save(vm.competition, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess(result) {
			$scope.$emit('danceFormApp:competitionUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		vm.datePickerOpenStatus.startDate = false;
		vm.datePickerOpenStatus.endDate = false;

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}
	}
})();
