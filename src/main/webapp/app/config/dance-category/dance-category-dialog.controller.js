(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('DanceCategoryDialogController', DanceCategoryDialogController);

	DanceCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DanceCategory'];

	function DanceCategoryDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, DanceCategory) {
		var vm = this;

		vm.danceCategory = entity;
		vm.clear = clear;
		vm.save = save;

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function save() {
			vm.isSaving = true;
			if(vm.danceCategory.id !== null) {
				DanceCategory.update(vm.danceCategory, onSaveSuccess, onSaveError);
			} else {
				DanceCategory.save(vm.danceCategory, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess(result) {
			$scope.$emit('danceFormApp:danceCategoryUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}


	}
})();
