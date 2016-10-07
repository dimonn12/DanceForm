(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('AgeCategoryDeleteController',AgeCategoryDeleteController);

    AgeCategoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'AgeCategory'];

    function AgeCategoryDeleteController($uibModalInstance, entity, AgeCategory) {
        var vm = this;

        vm.ageCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AgeCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
