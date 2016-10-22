(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('RegisteredCoupleDialogController', RegisteredCoupleDialogController);

	RegisteredCoupleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'RegisteredCouple', 'DanceClass'];

	function RegisteredCoupleDialogController($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, RegisteredCouple, DanceClass) {
		var vm = this;

		vm.registeredCouple = entity;
		vm.clear = clear;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.save = save;
		vm.partner1danceclasssts = DanceClass.query({filter: 'registeredcouple-is-null'});
		$q.all([vm.registeredCouple.$promise, vm.partner1danceclasssts.$promise]).then(function() {
			if(!vm.registeredCouple.partner1DanceClassSTId) {
				return $q.reject();
			}
			return DanceClass.get({id: vm.registeredCouple.partner1DanceClassSTId}).$promise;
		}).then(function(partner1DanceClassST) {
			vm.partner1danceclasssts.push(partner1DanceClassST);
		});
		vm.partner1danceclasslas = DanceClass.query({filter: 'registeredcouple-is-null'});
		$q.all([vm.registeredCouple.$promise, vm.partner1danceclasslas.$promise]).then(function() {
			if(!vm.registeredCouple.partner1DanceClassLAId) {
				return $q.reject();
			}
			return DanceClass.get({id: vm.registeredCouple.partner1DanceClassLAId}).$promise;
		}).then(function(partner1DanceClassLA) {
			vm.partner1danceclasslas.push(partner1DanceClassLA);
		});
		vm.partner2danceclasssts = DanceClass.query({filter: 'registeredcouple-is-null'});
		$q.all([vm.registeredCouple.$promise, vm.partner2danceclasssts.$promise]).then(function() {
			if(!vm.registeredCouple.partner2DanceClassSTId) {
				return $q.reject();
			}
			return DanceClass.get({id: vm.registeredCouple.partner2DanceClassSTId}).$promise;
		}).then(function(partner2DanceClassST) {
			vm.partner2danceclasssts.push(partner2DanceClassST);
		});
		vm.danceclasses = DanceClass.query();

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function save() {
			vm.isSaving = true;
			if(vm.registeredCouple.id !== null) {
				RegisteredCouple.update(vm.registeredCouple, onSaveSuccess, onSaveError);
			} else {
				RegisteredCouple.save(vm.registeredCouple, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess(result) {
			$scope.$emit('danceFormApp:registeredCoupleUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		vm.datePickerOpenStatus.partner1DateOfBirth = false;
		vm.datePickerOpenStatus.partner2DateOfBirth = false;

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}
	}
})();
