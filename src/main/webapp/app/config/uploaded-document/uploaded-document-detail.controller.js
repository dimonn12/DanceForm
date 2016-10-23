(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('UploadedDocumentDetailController', UploadedDocumentDetailController);

	UploadedDocumentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'UploadedDocument'];

	function UploadedDocumentDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, UploadedDocument) {
		var vm = this;

		vm.uploadedDocument = entity;
		vm.previousState = previousState.name;
		vm.byteSize = DataUtils.byteSize;
		vm.openFile = DataUtils.openFile;

		var unsubscribe = $rootScope.$on('danceFormApp:uploadedDocumentUpdate', function(event, result) {
			vm.uploadedDocument = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
