(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CompetitionNotificationDetailController', CompetitionNotificationDetailController);

    CompetitionNotificationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CompetitionNotification'];

    function CompetitionNotificationDetailController($scope, $rootScope, $stateParams, previousState, entity, CompetitionNotification) {
        var vm = this;

        vm.competitionNotification = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('danceFormApp:competitionNotificationUpdate', function(event, result) {
            vm.competitionNotification = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
