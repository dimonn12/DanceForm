(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .factory('notificationInterceptor', notificationInterceptor);

    notificationInterceptor.$inject = ['$q', 'AlertService'];

    function notificationInterceptor ($q, AlertService) {
        var service = {
            response: response
        };

        return service;

        function response (response) {
            var alertKey = response.headers('X-danceFormApp-alert');
            if (angular.isString(alertKey)) {
                AlertService.success(alertKey, { param : response.headers('X-danceFormApp-params')});
            }
            return response;
        }
    }
})();
