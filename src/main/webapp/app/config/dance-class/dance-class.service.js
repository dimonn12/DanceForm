(function() {
    'use strict';
    angular
        .module('danceFormApp')
        .factory('DanceClass', DanceClass);

    DanceClass.$inject = ['$resource'];

    function DanceClass ($resource) {
        var resourceUrl =  'api/config/dance-classes/:id';

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
