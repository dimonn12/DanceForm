(function() {
	'use strict';
	angular
		.module('danceFormApp')
		.factory('UploadedImage', UploadedImage);

	UploadedImage.$inject = ['$resource', 'DateUtils'];

	function UploadedImage($resource, DateUtils) {
		var resourceUrl = 'api/uploaded-images/:id';

		return $resource(resourceUrl, {}, {
			'query': {method: 'GET', isArray: true},
			'get': {
				method: 'GET',
				transformResponse: function(data) {
					if(data) {
						data = angular.fromJson(data);
						data.uploadedDate = DateUtils.convertDateTimeFromServer(data.uploadedDate);
					}
					return data;
				}
			},
			'update': {method: 'PUT'}
		});
	}
})();
