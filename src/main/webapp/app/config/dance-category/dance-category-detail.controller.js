(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('DanceCategoryDetailController', DanceCategoryDetailController);

	DanceCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DanceCategory'];

	function DanceCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, DanceCategory) {
		var vm = this;

		vm.danceCategory = entity;
		vm.previousState = previousState.name;

		var unsubscribe = $rootScope.$on('danceFormApp:danceCategoryUpdate', function(event, result) {
			vm.danceCategory = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
