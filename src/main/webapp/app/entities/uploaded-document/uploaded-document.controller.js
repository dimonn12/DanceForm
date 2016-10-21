(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('UploadedDocumentController', UploadedDocumentController);

    UploadedDocumentController.$inject = ['$scope', '$state', 'DataUtils', 'UploadedDocument'];

    function UploadedDocumentController ($scope, $state, DataUtils, UploadedDocument) {
        var vm = this;
        
        vm.uploadedDocuments = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            UploadedDocument.query(function(result) {
                vm.uploadedDocuments = result;
            });
        }
    }
})();
