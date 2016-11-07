(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('OrganizationController', OrganizationController);

	OrganizationController.$inject = ['$scope', '$state', 'Organization', 'ParseLinks', 'AlertService'];

	function OrganizationController($scope, $state, Organization, ParseLinks, AlertService) {
		var vm = this;

		vm.organizations = Organization.query();
	}
})();
