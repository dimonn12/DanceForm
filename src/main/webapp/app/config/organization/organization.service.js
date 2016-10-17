(function() {
    'use strict';
    angular
        .module('danceFormApp')
        .factory('Organization', Organization);

    Organization.$inject = ['$resource'];

    function Organization ($resource) {
        var resourceUrl =  'api/config/organizations/:id';

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
