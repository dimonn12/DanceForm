(function() {
    'use strict';
    angular
        .module('danceFormApp')
        .factory('CompetitionNotification', CompetitionNotification);

    CompetitionNotification.$inject = ['$resource'];

    function CompetitionNotification ($resource) {
        var resourceUrl =  'api/competition-notifications/:id';

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
