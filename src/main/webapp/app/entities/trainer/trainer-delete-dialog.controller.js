(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('TrainerDeleteController',TrainerDeleteController);

    TrainerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Trainer'];

    function TrainerDeleteController($uibModalInstance, entity, Trainer) {
        var vm = this;

        vm.trainer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Trainer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
