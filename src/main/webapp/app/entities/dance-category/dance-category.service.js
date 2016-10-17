(function() {
    'use strict';
    angular
        .module('danceFormApp')
        .factory('DanceCategory', DanceCategory);

    DanceCategory.$inject = ['$resource'];

    function DanceCategory ($resource) {
        var resourceUrl =  'api/dance-categories/:id';

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
