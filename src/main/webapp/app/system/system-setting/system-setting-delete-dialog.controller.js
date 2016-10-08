(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('SystemSettingDeleteController',SystemSettingDeleteController);

    SystemSettingDeleteController.$inject = ['$uibModalInstance', 'entity', 'SystemSetting'];

    function SystemSettingDeleteController($uibModalInstance, entity, SystemSetting) {
        var vm = this;

        vm.systemSetting = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SystemSetting.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
