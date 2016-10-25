(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('ScheduleController', ScheduleController);

	ScheduleController.$inject = ['$scope', 'Principal', 'CompetitionSchedule', 'LoginService', 'AlertService', '$state'];

	function ScheduleController($scope, Principal, CompetitionSchedule, LoginService, AlertService, $state) {
		var vm = this;

		vm.account = null;
		vm.isAuthenticated = null;
		vm.login = LoginService.open;
		$scope.$on('authenticationSuccess', function() {
			getAccount();
		});

		getAccount();

		function getAccount() {
			Principal.identity().then(function(account) {
				vm.account = account;
				vm.isAuthenticated = Principal.isAuthenticated;
			});
		}

		loadCompetitions();

		function loadCompetitions() {
			CompetitionSchedule.query({}, onSuccess, onError);
			function onSuccess(data, headers) {
				vm.queryCount = vm.totalItems;
				vm.futureCompetitions = [];
				vm.pastCompetitions = [];
				var competitions = data;
				var today = new Date();
				today.setDate(today.getDate() - 1);
				for(var i = 0; i < competitions.length; i++) {
					if(new Date(competitions[i].date) >= today) {
						vm.futureCompetitions.push(competitions[i]);
					} else {
						vm.pastCompetitions.push(competitions[i]);
					}
				}

			}

			function onError(error) {
				AlertService.error(error.data.message);
			}
		}
	}
})();
