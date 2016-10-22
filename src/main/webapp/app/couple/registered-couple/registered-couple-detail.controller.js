(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('RegisteredCoupleDetailController', RegisteredCoupleDetailController);

	RegisteredCoupleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RegisteredCouple', 'DanceClass'];

	function RegisteredCoupleDetailController($scope, $rootScope, $stateParams, previousState, entity, RegisteredCouple, DanceClass) {
		var vm = this;

		vm.registeredCouple = entity;
		vm.previousState = previousState.name;

		var unsubscribe = $rootScope.$on('danceFormApp:registeredCoupleUpdate', function(event, result) {
			vm.registeredCouple = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
