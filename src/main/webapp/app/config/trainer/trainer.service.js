(function() {
    'use strict';
    angular
        .module('danceFormApp')
        .factory('Trainer', Trainer);

    Trainer.$inject = ['$resource'];

    function Trainer ($resource) {
        var resourceUrl =  'api/config/trainers/:id';

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
