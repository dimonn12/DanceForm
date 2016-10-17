(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('DanceCategoryController', DanceCategoryController);

    DanceCategoryController.$inject = ['$scope', '$state', 'DanceCategory'];

    function DanceCategoryController ($scope, $state, DanceCategory) {
        var vm = this;
        
        vm.danceCategories = [];

        loadAll();

        function loadAll() {
            DanceCategory.query(function(result) {
                vm.danceCategories = result;
            });
        }
    }
})();
