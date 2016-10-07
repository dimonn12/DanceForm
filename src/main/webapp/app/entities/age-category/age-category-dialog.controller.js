(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('AgeCategoryDialogController', AgeCategoryDialogController);

    AgeCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AgeCategory'];

    function AgeCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AgeCategory) {
        var vm = this;

        vm.ageCategory = entity;
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
            if (vm.ageCategory.id !== null) {
                AgeCategory.update(vm.ageCategory, onSaveSuccess, onSaveError);
            } else {
                AgeCategory.save(vm.ageCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('danceFormApp:ageCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
