(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('MessageDetailController', MessageDetailController);

	MessageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Message'];

	function MessageDetailController($scope, $rootScope, $stateParams, previousState, entity, Message) {
		var vm = this;

		vm.message = entity;
		vm.previousState = previousState.name;

		var unsubscribe = $rootScope.$on('danceFormApp:messageUpdate', function(event, result) {
			vm.message = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
