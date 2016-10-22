(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('DanceClassDeleteController', DanceClassDeleteController);

	DanceClassDeleteController.$inject = ['$uibModalInstance', 'entity', 'DanceClass'];

	function DanceClassDeleteController($uibModalInstance, entity, DanceClass) {
		var vm = this;

		vm.danceClass = entity;
		vm.clear = clear;
		vm.confirmDelete = confirmDelete;

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function confirmDelete(id) {
			DanceClass.delete({id: id},
				function() {
					$uibModalInstance.close(true);
				});
		}
	}
})();
