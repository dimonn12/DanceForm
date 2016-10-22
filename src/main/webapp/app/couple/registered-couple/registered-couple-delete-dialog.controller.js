(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('RegisteredCoupleDeleteController', RegisteredCoupleDeleteController);

	RegisteredCoupleDeleteController.$inject = ['$uibModalInstance', 'entity', 'RegisteredCouple'];

	function RegisteredCoupleDeleteController($uibModalInstance, entity, RegisteredCouple) {
		var vm = this;

		vm.registeredCouple = entity;
		vm.clear = clear;
		vm.confirmDelete = confirmDelete;

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function confirmDelete(id) {
			RegisteredCouple.delete({id: id},
				function() {
					$uibModalInstance.close(true);
				});
		}
	}
})();
