(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('TrainerController', TrainerController);

	TrainerController.$inject = ['$scope', '$state', 'Trainer', 'ParseLinks', 'AlertService'];

	function TrainerController($scope, $state, Trainer, ParseLinks, AlertService) {
		var vm = this;

		vm.trainers = Trainer.query();
	}
})();
