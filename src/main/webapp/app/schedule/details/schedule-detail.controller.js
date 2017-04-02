(function() {
	'use strict';

	angular
		.module('danceFormApp')
		.controller('CompetitionScheduleDetailsController', CompetitionScheduleDetailsController);

	CompetitionScheduleDetailsController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'CompetitionSchedule', 'CompetitionReport', 'DanceClass', 'AlertService'];

	function CompetitionScheduleDetailsController($scope, $rootScope, $stateParams, previousState, CompetitionSchedule, CompetitionReport, DanceClass, AlertService) {
		var vm = this;

		vm.currentCompetition = null;
		vm.previousState = previousState.name;

		vm.categories = [];
		vm.totalRegisteredCount = 0;

		vm.document = {};

		vm.danceClasses = DanceClass.query();

		vm.download = donwload;

		load();

		function load() {
			CompetitionSchedule.get({id: $stateParams.id}, onSuccess, onError);
			function onSuccess(data) {
				vm.currentCompetition = data;
				vm.categories = vm.currentCompetition.competitionCategoryDTOs;

				vm.totalRegisteredCount = 0;

				for(var i = 0; i < vm.categories.length; i++) {
					vm.totalRegisteredCount += vm.categories[i].registeredCouplesCount;
				}

				for(var i = 0; i < vm.categories.length; i++) {
					if(null != vm.categories[i].maxDanceClass.id) {
						vm.categories[i].danceClasses = [];
						var maxDanceClass = null;
						for(var j = 0; j < vm.danceClasses.length; j++) {
							if(vm.danceClasses[j].id == vm.categories[i].maxDanceClass.id) {
								maxDanceClass = vm.danceClasses[j];
								break;
							}
						}
						for(var j = 0; j < vm.danceClasses.length; j++) {
							if(vm.danceClasses[j].weight <= maxDanceClass.weight) {
								vm.categories[i].danceClasses.push(vm.danceClasses[j]);
							}
						}
						var doRemoveHiddenClasses = false;
						for(var j = 0; j < vm.categories[i].danceClasses.length; j++) {
							if(vm.categories[i].danceClasses[j].weight > 0) {
								doRemoveHiddenClasses = true;
							}
						}
						var newCategoriesDanceClasses = [];
						if(doRemoveHiddenClasses) {
							for(var j = 0; j < vm.categories[i].danceClasses.length; j++) {
								if(vm.categories[i].danceClasses[j].weight > 0) {
									newCategoriesDanceClasses.push(vm.categories[i].danceClasses[j]);
								}
							}
						} else {
							for(var j = 0; j < vm.categories[i].danceClasses.length; j++) {
								if(vm.categories[i].danceClasses[j].weight > -100) {
									newCategoriesDanceClasses.push(vm.categories[i].danceClasses[j]);
								}
							}
						}
						vm.categories[i].danceClasses = newCategoriesDanceClasses;
					}
				}
			}

			function onError(error) {
				AlertService.error(error.data.message);
			}
		}
		
		function donwload() {
			CompetitionReport.get({id: $stateParams.id}, onSuccess, onError);
			function onSuccess(result) {
				var url = URL.createObjectURL(new Blob([result.data]));
				var a = document.createElement('a');
				a.href = url;
				a.download = result.filename;
				var event = document.createEvent("MouseEvents");
				event.initMouseEvent(
					"click", true, false, window, 0, 0, 0, 0, 0
					, false, false, false, false, 0, null
				);
				a.dispatchEvent(event);
				/*a.target = '_blank';
				a.click();*/
			}
			function onError(error) {
				var charCodeArray = Array.apply(null, new Uint8Array(error).data);
				var result = '';
				for (var i = 0, len = charCodeArray.length; i < len; i++) {
					var code = charCodeArray[i];
					result += String.fromCharCode(code);
				}
				AlertService.error(result);
			}
		}

		var unsubscribe = $rootScope.$on('danceFormApp:competitionUpdate', function(event, result) {
			vm.competition = result;
		});
		$scope.$on('$destroy', unsubscribe);
	}
})();
