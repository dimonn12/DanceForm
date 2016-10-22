(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('DanceClassController', DanceClassController);

	DanceClassController.$inject = ['$scope', '$state', 'DanceClass'];

	function DanceClassController($scope, $state, DanceClass) {
		var vm = this;

		vm.danceClasses = [];

		loadAll();

		function loadAll() {
			DanceClass.query(function(result) {
				vm.danceClasses = result;
			});
		}
	}
})();
