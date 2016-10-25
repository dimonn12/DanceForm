(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionScheduleDetailsController', CompetitionScheduleDetailsController);

	CompetitionScheduleDetailsController.$inject = ['$state', '$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'CompetitionSchedule', 'AlertService'];

	function CompetitionScheduleDetailsController($state, $scope, $rootScope, $stateParams, previousState, DataUtils, CompetitionSchedule, AlertService) {
		var vm = this;

		vm.currentCompetition = null;
		vm.previousState = previousState.name;

		vm.categories = [];
		vm.totalRegisteredCount = 0;

		vm.document = {};

		load();

		function load() {
			CompetitionSchedule.get({id: $stateParams.id}, onSuccess, onError);
			function onSuccess(data, headers) {
				vm.currentCompetition = data;
				vm.categories = vm.currentCompetition.competitionCategoryDTOs;

				vm.totalRegisteredCount = 0;

				for(var i = 0; i < vm.categories.length; i++) {
					vm.totalRegisteredCount += vm.categories[i].registeredCouplesCount;
				}
			}

			function onError(error) {
				AlertService.error(error.data.message);
			}
		}

		var unsubscribe = $rootScope.$on('danceFormApp:competitionUpdate', function(event, result) {
			vm.competition = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
