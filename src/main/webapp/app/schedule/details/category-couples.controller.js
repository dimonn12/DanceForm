(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CategoryCouplesController', CategoryCouplesController);

	CategoryCouplesController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CompetitionScheduleCategory', 'RegisteredCouple', 'AlertService'];

	function CategoryCouplesController($scope, $rootScope, $stateParams, previousState, entity, CompetitionScheduleCategory, RegisteredCouple, AlertService) {
		var vm = this;

		vm.currentCompetition = entity;
		vm.previousState = previousState.name;

		vm.category = null;

		vm.couples = [];

		load();

		function load() {
			CompetitionScheduleCategory.get({
				competitionId: vm.currentCompetition.id,
				id: $stateParams.categoryId
			}, onSuccess, onError);
			function onSuccess(data, headers) {
				vm.category = data;
				loadCouples();
			}

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
