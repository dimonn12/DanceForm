(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('LanguageStringDetailController', LanguageStringDetailController);

	LanguageStringDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LanguageString'];

	function LanguageStringDetailController($scope, $rootScope, $stateParams, previousState, entity, LanguageString) {
		var vm = this;

		vm.languageString = entity;
		vm.previousState = previousState.name;

		var unsubscribe = $rootScope.$on('danceFormApp:languageStringUpdate', function(event, result) {
			vm.languageString = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
