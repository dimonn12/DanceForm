(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('DanceClassDetailController', DanceClassDetailController);

	DanceClassDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DanceClass'];

	function DanceClassDetailController($scope, $rootScope, $stateParams, previousState, entity, DanceClass) {
		var vm = this;

		vm.danceClass = entity;
		vm.previousState = previousState.name;

		var unsubscribe = $rootScope.$on('danceFormApp:danceClassUpdate', function(event, result) {
			vm.danceClass = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
