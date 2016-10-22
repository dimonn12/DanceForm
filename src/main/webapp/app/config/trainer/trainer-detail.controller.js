(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('TrainerDetailController', TrainerDetailController);

	TrainerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Trainer'];

	function TrainerDetailController($scope, $rootScope, $stateParams, previousState, entity, Trainer) {
		var vm = this;

		vm.trainer = entity;
		vm.previousState = previousState.name;

		var unsubscribe = $rootScope.$on('danceFormApp:trainerUpdate', function(event, result) {
			vm.trainer = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
