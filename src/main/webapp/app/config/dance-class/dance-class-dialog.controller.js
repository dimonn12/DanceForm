(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('DanceClassDialogController', DanceClassDialogController);

    DanceClassDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'DanceClass'];

    function DanceClassDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, DanceClass) {
        var vm = this;

        vm.danceClass = entity;
        vm.clear = clear;
        vm.save = save;
        vm.danceclasses = DanceClass.query({filter: 'danceclass-is-null'});
        $q.all([vm.danceClass.$promise, vm.danceclasses.$promise]).then(function() {
            if (!vm.danceClass.danceClassId) {
                return $q.reject();
            }
            return DanceClass.get({id : vm.danceClass.danceClassId}).$promise;
        }).then(function(danceClass) {
            vm.danceclasses.push(danceClass);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.danceClass.id !== null) {
                DanceClass.update(vm.danceClass, onSaveSuccess, onSaveError);
            } else {
                DanceClass.save(vm.danceClass, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('danceFormApp:danceClassUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
