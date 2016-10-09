'use strict';

describe('Controller Tests', function() {

    describe('CompetitionCategory Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCompetitionCategory, MockCompetition, MockDanceClas, MockAgeCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCompetitionCategory = jasmine.createSpy('MockCompetitionCategory');
            MockCompetition = jasmine.createSpy('MockCompetition');
            MockDanceClas = jasmine.createSpy('MockDanceClas');
            MockAgeCategory = jasmine.createSpy('MockAgeCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CompetitionCategory': MockCompetitionCategory,
                'Competition': MockCompetition,
                'DanceClas': MockDanceClas,
                'AgeCategory': MockAgeCategory
            };
            createController = function() {
                $injector.get('$controller')("CompetitionCategoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'danceFormApp:competitionCategoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
