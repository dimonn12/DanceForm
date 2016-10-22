(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionShowDetailController', CompetitionShowDetailController);

	CompetitionShowDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'Competition', 'CompetitionTimeline', 'CompetitionCategory'];

	function CompetitionShowDetailController($scope, $rootScope, $stateParams, previousState, Competition, CompetitionTimeline, CompetitionCategory) {
		var vm = this;

		vm.currentCompetition = null;
		vm.previousState = previousState.name;

		vm.categories = [];
		vm.totalRegisteredCount = 0;

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
