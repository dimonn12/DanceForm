(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('TrainerDialogController', TrainerDialogController);

    TrainerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Trainer'];

    function TrainerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Trainer) {
        var vm = this;

        vm.trainer = entity;
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
            if (vm.trainer.id !== null) {
                Trainer.update(vm.trainer, onSaveSuccess, onSaveError);
            } else {
                Trainer.save(vm.trainer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('danceFormApp:trainerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
