(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .controller('CompetitionCategoryDialogController', CompetitionCategoryDialogController);

    CompetitionCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CompetitionCategory', 'Competition', 'DanceClass', 'AgeCategory'];

    function CompetitionCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CompetitionCategory, Competition, DanceClass, AgeCategory) {
        var vm = this;

        vm.competitionCategory = entity;
        vm.clear = clear;
        vm.save = save;
        vm.competitions = Competition.query();
        vm.danceclass = DanceClass.query();
        vm.agecategories = AgeCategory.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.competitionCategory.id !== null) {
                CompetitionCategory.update({competitionId : $stateParams.competitionId}, vm.competitionCategory, onSaveSuccess, onSaveError);
            } else {
                CompetitionCategory.save({competitionId : $stateParams.competitionId}, vm.competitionCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('danceFormApp:competitionCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
