(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CompetitionCategoryDeleteController',CompetitionCategoryDeleteController);

    CompetitionCategoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompetitionCategory'];

    function CompetitionCategoryDeleteController($uibModalInstance, entity, CompetitionCategory) {
        var vm = this;

        vm.competitionCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CompetitionCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
