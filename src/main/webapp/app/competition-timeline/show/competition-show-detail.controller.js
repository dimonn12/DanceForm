(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionShowDetailController', CompetitionShowDetailController);

	CompetitionShowDetailController.$inject = ['$state', '$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'UploadedDocument', 'CompetitionTimeline', 'CompetitionCategory'];

	function CompetitionShowDetailController($state, $scope, $rootScope, $stateParams, previousState, DataUtils, UploadedDocument, CompetitionTimeline, CompetitionCategory) {
		var vm = this;

		vm.currentCompetition = null;
		vm.previousState = previousState.name;

		vm.categories = [];
		vm.totalRegisteredCount = 0;

		vm.loadDocumentDetails = loadDocumentDetails;
		vm.openFile = DataUtils.openFile;

		vm.document = {};

		load();

		function load() {
			CompetitionTimeline.get({id: $stateParams.id}, onSuccess, onError);
			function onSuccess(data, headers) {
				vm.currentCompetition = data;
				vm.categories = vm.currentCompetition.competitionCategoryDTOs;

				vm.totalRegisteredCount = 0;

				for(var i = 0; i < vm.categories.length; i++) {
					vm.totalRegisteredCount += vm.categories[i].registeredCouplesCount;
				}

				//vm.document = UploadedDocument.get({id: vm.currentCompetition.detailsDocumentId});
			}

			function onError(error) {
				AlertService.error(error.data.message);
			}
		}

		var unsubscribe = $rootScope.$on('danceFormApp:competitionUpdate', function(event, result) {
			vm.competition = result;
		});
		$scope.$on('$destroy', unsubscribe);

		function loadDocumentDetails() {
			var url = $state.href('competition-timeline-category-file', {id: vm.currentCompetition.id, documentId: vm.currentCompetition.detailsDocumentId});
			window.open(url,'_blank');
			//$state.go('competition-timeline-category-file', {documentId: vm.currentCompetition.detailsDocumentId});
			/*UploadedDocument.get({id: vm.currentCompetition.detailsDocumentId}, onSaveSuccess, onSaveError);

			function onSaveSuccess(result) {
				$window.open('data:' + vm.document.contentContentType + ';base64,' + vm.document.content, '_blank');
				//vm.openFile(vm.document.contentContentType, vm.document.content);
			}

			function onSaveError() {
			}*/
		}
	}
})();
