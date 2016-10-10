(function() {
    'use strict';
    angular
        .module('danceFormApp')
        .factory('AgeCategory', AgeCategory);

    AgeCategory.$inject = ['$resource'];

    function AgeCategory ($resource) {
        var resourceUrl =  'api/config/age-categories/:id';

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
