(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionDetailController', CompetitionDetailController);

	CompetitionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Competition'];

	function CompetitionDetailController($scope, $rootScope, $stateParams, previousState, entity, Competition) {
		var vm = this;

		vm.competition = entity;
		vm.previousState = previousState.name;

		var unsubscribe = $rootScope.$on('danceFormApp:competitionUpdate', function(event, result) {
			vm.competition = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
