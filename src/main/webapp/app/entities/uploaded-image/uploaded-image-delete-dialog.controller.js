(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('UploadedImageDeleteController',UploadedImageDeleteController);

    UploadedImageDeleteController.$inject = ['$uibModalInstance', 'entity', 'UploadedImage'];

    function UploadedImageDeleteController($uibModalInstance, entity, UploadedImage) {
        var vm = this;

        vm.uploadedImage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UploadedImage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
