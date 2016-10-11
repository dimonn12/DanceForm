(function() {
    'use strict';
    angular
        .module('danceFormApp')
        .factory('CompetitionTimelineCategory', CompetitionTimelineCategory);

    CompetitionTimelineCategory.$inject = ['$resource', 'DateUtils'];

    function CompetitionTimelineCategory ($resource, DateUtils) {
        var resourceUrl =  '/api/competition-timeline/competitions/:competitionId/category/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertLocalDateFromServer(data.date);
                    }
                    return data;
                }
            }
        });
    }
})();
