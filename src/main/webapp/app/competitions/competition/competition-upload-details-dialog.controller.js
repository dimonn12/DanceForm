(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionUploadDetailsDialogController', CompetitionUploadDetailsDialogController);

	CompetitionUploadDetailsDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'DataUtils', 'entity', 'Competition', 'UploadedDocument'];

	function CompetitionUploadDetailsDialogController($timeout, $scope, $uibModalInstance, DataUtils, entity, Competition, UploadedDocument) {
		var vm = this;

		vm.competition = entity;
		vm.clear = clear;
		vm.upload = upload;
		vm.uploadedDocument = {entityId: vm.competition.id};
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function upload() {
			UploadedDocument.uploadToComeptition(vm.uploadedDocument, onSaveSuccess, onSaveError);
		}

		function onSaveSuccess(result) {
			$scope.$emit('danceFormApp:competitionUpdate', Competition.get({id: vm.competition.id}));
			$uibModalInstance.close(true);
		}

		function onSaveError() {
			//$uibModalInstance.close(true);
		}

		vm.setContent = function($file, uploadedDocument) {
			if($file) {
				uploadedDocument.fullName = $file.name;
				uploadedDocument.contentContentType = $file.type;
				DataUtils.toBase64($file, function(base64Data) {
					$scope.$apply(function() {
						uploadedDocument.content = base64Data;
						uploadedDocument.contentContentType = $file.type;
					});
				});
			}
		};
	}
})();
