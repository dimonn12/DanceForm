(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('TimelineController', TimelineController);

    TimelineController.$inject = ['$scope', 'Principal', 'CompetitionTimeline', 'LoginService', 'AlertService', '$state'];

    function TimelineController ($scope, Principal, CompetitionTimeline, LoginService, AlertService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        loadCompetitions();

        function loadCompetitions () {
            CompetitionTimeline.query({}, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.queryCount = vm.totalItems;
                vm.futureCompetitions = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
