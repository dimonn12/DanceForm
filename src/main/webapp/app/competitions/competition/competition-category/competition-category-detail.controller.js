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

		var unsubscribe = $rootScope.$on('danceFormApp:competitionCategoryUpdate', function(event, result) {
			vm.competitionCategory = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
