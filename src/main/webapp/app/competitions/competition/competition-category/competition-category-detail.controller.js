(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionCategoryDetailController', CompetitionCategoryDetailController);

	CompetitionCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CompetitionCategory', 'Competition', 'DanceClass', 'AgeCategory'];

	function CompetitionCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, CompetitionCategory, Competition, DanceClass, AgeCategory) {
		var vm = this;

		vm.competitionCategory = entity;
		vm.previousState = previousState.name;

		vm.danceClasses = DanceClass.query(onLoad);

		function onLoad() {
			if(null != vm.competitionCategory.maxDanceClass.id) {
				vm.competitionCategory.danceClasses = [];
				var maxDanceClass = null;
				for(var j = 0; j < vm.danceClasses.length; j++) {
					if(vm.danceClasses[j].id == vm.competitionCategory.maxDanceClass.id) {
						maxDanceClass = vm.danceClasses[j];
						break;
					}
				}
				for(var j = 0; j < vm.danceClasses.length; j++) {
					if(vm.danceClasses[j].weight <= maxDanceClass.weight) {
						vm.competitionCategory.danceClasses.push(vm.danceClasses[j]);
					}
				}
			}
		}

		var unsubscribe = $rootScope.$on('danceFormApp:competitionCategoryUpdate', function(event, result) {
			vm.competitionCategory = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
