(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('FeedbackController', FeedbackController);

	FeedbackController.$inject = ['$scope', '$state', 'Message'];

	function FeedbackController($scope, $state, Message) {
		var vm = this;

		vm.success = false;
		vm.failure = false;

		vm.feedback = {};

		vm.save = onSave;

		function onSave() {
			vm.success = false;
			vm.failure = false;
			Message.save(vm.feedback, onSaveSuccess, onSaveError);
		}

		function onSaveSuccess(result) {
			vm.success = true;
			vm.feedback = {};
		}

		function onSaveError() {
			vm.failure = true;
		}
	}
})();
