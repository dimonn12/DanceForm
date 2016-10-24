(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('UploadedImageDialogController', UploadedImageDialogController);

    UploadedImageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'UploadedImage'];

    function UploadedImageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, UploadedImage) {
        var vm = this;

        vm.uploadedImage = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        vm.uploadedImage.entityId = 1;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.uploadedImage.id !== null) {
                UploadedImage.update(vm.uploadedImage, onSaveSuccess, onSaveError);
            } else {
                UploadedImage.save(vm.uploadedImage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('danceFormApp:uploadedImageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.uploadedDate = false;

        vm.setContent = function ($file, uploadedImage) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        uploadedImage.content = base64Data;
                        uploadedImage.contentContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
