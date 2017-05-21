(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CompetitionNotificationDeleteController',CompetitionNotificationDeleteController);

    CompetitionNotificationDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompetitionNotification'];

    function CompetitionNotificationDeleteController($uibModalInstance, entity, CompetitionNotification) {
        var vm = this;

        vm.competitionNotification = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CompetitionNotification.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
