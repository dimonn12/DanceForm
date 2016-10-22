(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('AgeCategoryController', AgeCategoryController);

	AgeCategoryController.$inject = ['$scope', '$state', 'AgeCategory'];

	function AgeCategoryController($scope, $state, AgeCategory) {
		var vm = this;

		vm.ageCategories = [];

		loadAll();

		function loadAll() {
			AgeCategory.query(function(result) {
				vm.ageCategories = result;
			});
		}
	}
})();
