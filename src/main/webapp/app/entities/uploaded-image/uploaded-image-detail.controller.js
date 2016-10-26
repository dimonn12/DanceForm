(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('UploadedImageDetailController', UploadedImageDetailController);

	UploadedImageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'UploadedImage'];

	function UploadedImageDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, UploadedImage) {
		var vm = this;

		vm.uploadedImage = entity;
		vm.previousState = previousState.name;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;

		var unsubscribe = $rootScope.$on('danceFormApp:uploadedImageUpdate', function(event, result) {
			vm.uploadedImage = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
