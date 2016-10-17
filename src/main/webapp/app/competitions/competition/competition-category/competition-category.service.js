(function () {
    'use strict';
    angular
        .module('danceFormApp')
        .factory('CompetitionCategory', CompetitionCategory);

    CompetitionCategory.$inject = ['$resource'];

    function CompetitionCategory($resource) {
        var resourceUrl = 'api/competition/:competitionId/competition-categories/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {method: 'PUT'},
            'available': {
                method: 'POST',
                isArray: true,
                url: resourceUrl + '/available'
            }
        });
    }
})();
