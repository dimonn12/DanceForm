(function() {
    'use strict';
    angular
        .module('danceFormApp')
        .factory('SystemSetting', SystemSetting);

    SystemSetting.$inject = ['$resource'];

    function SystemSetting ($resource) {
        var resourceUrl =  'api/system/system-settings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
