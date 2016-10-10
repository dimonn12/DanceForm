(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('RegisteredCoupleController', RegisteredCoupleController);

    RegisteredCoupleController.$inject = ['$scope', '$state', 'RegisteredCouple'];

    function RegisteredCoupleController ($scope, $state, RegisteredCouple) {
        var vm = this;
        
        vm.registeredCouples = [];

        loadAll();

        function loadAll() {
            RegisteredCouple.query(function(result) {
                vm.registeredCouples = result;
            });
        }
    }
})();
