(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CompetitionNotificationDialogController', CompetitionNotificationDialogController);

	CompetitionNotificationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Competition', 'CompetitionNotification'];

	function CompetitionNotificationDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Competition, CompetitionNotification) {
        var vm = this;

		vm.competitions = Competition.query();
        vm.competitionNotification = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.competitionNotification.id !== null) {
                CompetitionNotification.update(vm.competitionNotification, onSaveSuccess, onSaveError);
            } else {
                CompetitionNotification.save(vm.competitionNotification, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('danceFormApp:competitionNotificationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
