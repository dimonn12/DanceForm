(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionCategoryController', CompetitionCategoryController);

	CompetitionCategoryController.$inject = ['$scope', '$state', '$stateParams', 'previousState', 'CompetitionCategory', 'DanceClass'];

	function CompetitionCategoryController($scope, $state, $stateParams, previousState, CompetitionCategory, DanceClass) {
		var vm = this;

		vm.competitionId = $stateParams.competitionId;
		vm.previousState = previousState;

		vm.competitionCategories = [];
		vm.danceClasses = DanceClass.query();

		loadAll();

		function loadAll() {
			CompetitionCategory.query({competitionId: vm.competitionId}, function(result) {
				vm.competitionCategories = result;

				for(var i = 0; i < vm.competitionCategories.length; i++) {
					if(null != vm.competitionCategories[i].maxDanceClass.id) {
						vm.competitionCategories[i].danceClasses = [];
						var maxDanceClass = null;
						for(var j = 0; j < vm.danceClasses.length; j++) {
							if(vm.danceClasses[j].id == vm.competitionCategories[i].maxDanceClass.id) {
								maxDanceClass = vm.danceClasses[j];
								break;
							}
						}
						for(var j = 0; j < vm.danceClasses.length; j++) {
							if(vm.danceClasses[j].weight < maxDanceClass.weight) {
								vm.competitionCategories[i].danceClasses.push(vm.danceClasses[j]);
							}
						}
					}
				}

			});
		}
	}
})();
