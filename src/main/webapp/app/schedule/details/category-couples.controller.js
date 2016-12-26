(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CategoryCouplesController', CategoryCouplesController);

	CategoryCouplesController.$inject = ['$stateParams', 'previousState', 'CompetitionSchedule', 'CompetitionScheduleCategory', 'RegisteredCouple', 'AlertService'];

	function CategoryCouplesController($stateParams, previousState, CompetitionSchedule, CompetitionScheduleCategory, RegisteredCouple, AlertService) {
		var vm = this;

		vm.currentCompetition = null;
		vm.previousState = previousState.name;

		vm.category = null;

		vm.couples = [];

		load();

		function load() {
			CompetitionSchedule.get({id: $stateParams.id}, function(data) {
				vm.currentCompetition = data;
				CompetitionScheduleCategory.get({
					competitionId: vm.currentCompetition.id,
					id: $stateParams.categoryId
				}, onSuccess, onError);
				function onSuccess(data, headers) {
					vm.category = data;
					loadCouples();
				}
			}, onError);

			function onError(error) {
				AlertService.error(error.data.message);
			}
		}

		function loadCouples() {
			RegisteredCouple.query({categoryId: vm.category.id}, onSuccess, onError);
			function onSuccess(data, headers) {
				vm.couples = data;
			}

			function onError(error) {
				AlertService.error(error.data.message);
			}
		}

	}
})();
