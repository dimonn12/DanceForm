(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('LanguageStringDeleteController', LanguageStringDeleteController);

	LanguageStringDeleteController.$inject = ['$uibModalInstance', 'entity', 'LanguageString'];

	function LanguageStringDeleteController($uibModalInstance, entity, LanguageString) {
		var vm = this;

		vm.languageString = entity;
		vm.clear = clear;
		vm.confirmDelete = confirmDelete;

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function confirmDelete(id) {
			LanguageString.delete({id: id},
				function() {
					$uibModalInstance.close(true);
				});
		}
	}
})();
