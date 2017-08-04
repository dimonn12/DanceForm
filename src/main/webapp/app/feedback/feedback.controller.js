(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('FeedbackController', FeedbackController);

	FeedbackController.$inject = ['$timeout', '$scope', 'Message'];

	function FeedbackController($timeout, $scope, Message) {
		var vm = this;

		vm.success = false;
		vm.failure = false;

		vm.feedback = {};

		vm.save = onSave;

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function onSave() {
			vm.success = false;
			vm.failure = false;
			Message.save(vm.feedback, onSaveSuccess, onSaveError);
		}

		function onSaveSuccess() {
			vm.success = true;
			vm.feedback = {};
			$scope.form.$setPristine();
		}

		function onSaveError() {
			vm.failure = true;
		}
	}
})();
