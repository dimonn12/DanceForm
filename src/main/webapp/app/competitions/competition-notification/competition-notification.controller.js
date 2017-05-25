(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionNotificationController', CompetitionNotificationController);

	CompetitionNotificationController.$inject = ['$scope', '$state', 'CompetitionNotification'];

	function CompetitionNotificationController($scope, $state, CompetitionNotification) {
		var vm = this;

		vm.competitionNotifications = [];

		loadAll();

		function loadAll() {
			CompetitionNotification.query(function(result) {
				vm.competitionNotifications = result;
			});
		}
	}
})();
