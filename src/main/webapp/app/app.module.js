(function() {
	'use strict';

	angular
		.module('danceFormApp', [
			'ngStorage',
			'tmh.dynamicLocale',
			'pascalprecht.translate',
			'ngResource',
			'ngCookies',
			'ngAria',
			'ngCacheBuster',
			'ngFileUpload',
			'ui.bootstrap',
			'ui.bootstrap.datetimepicker',
			'ui.router',
			'infinite-scroll',
			// jhipster-needle-angularjs-add-module JHipster will add new module here
			'angular-loading-bar',
			'oi.select'
		])
		.run(run);

	run.$inject = ['$http', 'stateHandler', 'translationHandler'];

	function run($http, stateHandler, translationHandler) {
		$http.defaults.headers.get = {'Cache-Control': 'max-age=0'}
		stateHandler.initialize();
		translationHandler.initialize();
	}
})();
