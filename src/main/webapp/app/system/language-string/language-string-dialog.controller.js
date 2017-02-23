(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('LanguageStringDialogController', LanguageStringDialogController);

	LanguageStringDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LanguageString'];

	function LanguageStringDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, LanguageString) {
		var vm = this;

		vm.languageString = entity;
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
			if(vm.languageString.id !== null) {
				LanguageString.update(vm.languageString, onSaveSuccess, onSaveError);
			} else {
				LanguageString.save(vm.languageString, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess(result) {
			$scope.$emit('danceFormApp:languageStringUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}


	}
})();
