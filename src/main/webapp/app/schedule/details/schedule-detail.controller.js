(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionScheduleDetailsController', CompetitionScheduleDetailsController);

	CompetitionScheduleDetailsController.$inject = ['$state', '$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'CompetitionSchedule', 'DanceClass', 'AlertService'];

	function CompetitionScheduleDetailsController($state, $scope, $rootScope, $stateParams, previousState, DataUtils, CompetitionSchedule, DanceClass, AlertService) {
		var vm = this;

		vm.currentCompetition = null;
		vm.previousState = previousState.name;

		vm.categories = [];
		vm.totalRegisteredCount = 0;

		vm.document = {};

		vm.danceClasses = DanceClass.query();

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

				for(var i = 0; i < vm.categories.length; i++) {
					if(null != vm.categories[i].maxDanceClass.id) {
						vm.categories[i].danceClasses = [];
						var maxDanceClass = null;
						for(var j = 0; j < vm.danceClasses.length; j++) {
							if(vm.danceClasses[j].id == vm.categories[i].maxDanceClass.id) {
								maxDanceClass = vm.danceClasses[j];
								break;
							}
						}
						for(var j = 0; j < vm.danceClasses.length; j++) {
							if(vm.danceClasses[j].weight <= maxDanceClass.weight) {
								vm.categories[i].danceClasses.push(vm.danceClasses[j]);
							}
						}
						var doRemoveHiddenClasses = false;
						for(var j = 0; j < vm.categories[i].danceClasses.length; j++) {
							if(vm.categories[i].danceClasses[j].weight > 0) {
								doRemoveHiddenClasses = true;
							}
						}
						var newCategoriesDanceClasses = [];
						if(doRemoveHiddenClasses) {
							for(var j = 0; j < vm.categories[i].danceClasses.length; j++) {
								if(vm.categories[i].danceClasses[j].weight > 0) {
									newCategoriesDanceClasses.push(vm.categories[i].danceClasses[j]);
								}
							}
						} else {
							for(var j = 0; j < vm.categories[i].danceClasses.length; j++) {
								if(vm.categories[i].danceClasses[j].weight > -100) {
									newCategoriesDanceClasses.push(vm.categories[i].danceClasses[j]);
								}
							}
						}
						vm.categories[i].danceClasses = newCategoriesDanceClasses;
					}
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
