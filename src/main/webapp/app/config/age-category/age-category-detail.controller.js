(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('AgeCategoryDetailController', AgeCategoryDetailController);

    AgeCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AgeCategory'];

    function AgeCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, AgeCategory) {
        var vm = this;

        vm.ageCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('danceFormApp:ageCategoryUpdate', function(event, result) {
            vm.ageCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
