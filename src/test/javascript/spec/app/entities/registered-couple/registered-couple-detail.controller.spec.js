'use strict';

describe('Controller Tests', function() {

    describe('RegisteredCouple Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRegisteredCouple, MockDanceClass;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRegisteredCouple = jasmine.createSpy('MockRegisteredCouple');
            MockDanceClass = jasmine.createSpy('MockDanceClass');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'RegisteredCouple': MockRegisteredCouple,
                'DanceClass': MockDanceClass
            };
            createController = function() {
                $injector.get('$controller')("RegisteredCoupleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'danceFormApp:registeredCoupleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
