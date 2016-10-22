(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('DanceCategoryDeleteController', DanceCategoryDeleteController);

	DanceCategoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'DanceCategory'];

	function DanceCategoryDeleteController($uibModalInstance, entity, DanceCategory) {
		var vm = this;

		vm.danceCategory = entity;
		vm.clear = clear;
		vm.confirmDelete = confirmDelete;

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function confirmDelete(id) {
			DanceCategory.delete({id: id},
				function() {
					$uibModalInstance.close(true);
				});
		}
	}
})();
