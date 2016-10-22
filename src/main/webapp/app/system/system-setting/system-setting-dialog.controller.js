(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('SystemSettingDialogController', SystemSettingDialogController);

	SystemSettingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SystemSetting'];

	function SystemSettingDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, SystemSetting) {
		var vm = this;

		vm.systemSetting = entity;
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
			if(vm.systemSetting.id !== null) {
				SystemSetting.update(vm.systemSetting, onSaveSuccess, onSaveError);
			} else {
				SystemSetting.save(vm.systemSetting, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess(result) {
			$scope.$emit('danceFormApp:systemSettingUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}


	}
})();
