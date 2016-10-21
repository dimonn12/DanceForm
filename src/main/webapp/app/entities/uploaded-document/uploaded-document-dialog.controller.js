(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('UploadedDocumentDialogController', UploadedDocumentDialogController);

    UploadedDocumentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'UploadedDocument'];

    function UploadedDocumentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, UploadedDocument) {
        var vm = this;

        vm.uploadedDocument = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.uploadedDocument.id !== null) {
                UploadedDocument.update(vm.uploadedDocument, onSaveSuccess, onSaveError);
            } else {
                UploadedDocument.save(vm.uploadedDocument, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('danceFormApp:uploadedDocumentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setContent = function ($file, uploadedDocument) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        uploadedDocument.content = base64Data;
                        uploadedDocument.contentContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.uploadedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
