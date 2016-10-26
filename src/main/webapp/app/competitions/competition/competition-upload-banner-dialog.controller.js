(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionUploadBannerDialogController', CompetitionUploadBannerDialogController);

	CompetitionUploadBannerDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'DataUtils', 'entity', 'Competition', 'UploadedDocument'];

	function CompetitionUploadBannerDialogController($timeout, $scope, $uibModalInstance, DataUtils, entity, Competition, UploadedDocument) {
		var vm = this;

		vm.competition = entity;
		vm.clear = clear;
		vm.upload = upload;
		vm.uploadedImage = {entityId: vm.competition.id};
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function upload() {
			UploadedDocument.uploadImageToComeptition(vm.uploadedImage, onSaveSuccess, onSaveError);
		}

		function onSaveSuccess(result) {
			$scope.$emit('danceFormApp:competitionUpdate', Competition.get({id: vm.competition.id}));
			$uibModalInstance.close(true);
		}

		function onSaveError() {
			//$uibModalInstance.close(true);
		}

		vm.setContent = function($file, uploadedImage) {
			if($file && $file.$error === 'pattern') {
				return;
			}
			if($file) {
				uploadedImage.fullName = $file.name;
				DataUtils.toBase64($file, function(base64Data) {
					$scope.$apply(function() {
						uploadedImage.content = base64Data;
						uploadedImage.contentContentType = $file.type;
					});
				});
			}
		};
	}
})();
