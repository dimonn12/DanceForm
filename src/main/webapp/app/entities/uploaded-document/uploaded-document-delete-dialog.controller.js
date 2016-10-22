(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('UploadedDocumentDeleteController', UploadedDocumentDeleteController);

	UploadedDocumentDeleteController.$inject = ['$uibModalInstance', 'entity', 'UploadedDocument'];

	function UploadedDocumentDeleteController($uibModalInstance, entity, UploadedDocument) {
		var vm = this;

		vm.uploadedDocument = entity;
		vm.clear = clear;
		vm.confirmDelete = confirmDelete;

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function confirmDelete(id) {
			UploadedDocument.delete({id: id},
				function() {
					$uibModalInstance.close(true);
				});
		}
	}
})();
