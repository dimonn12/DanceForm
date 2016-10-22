(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('SystemSettingDetailController', SystemSettingDetailController);

	SystemSettingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SystemSetting'];

	function SystemSettingDetailController($scope, $rootScope, $stateParams, previousState, entity, SystemSetting) {
		var vm = this;

		vm.systemSetting = entity;
		vm.previousState = previousState.name;

		var unsubscribe = $rootScope.$on('danceFormApp:systemSettingUpdate', function(event, result) {
			vm.systemSetting = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
