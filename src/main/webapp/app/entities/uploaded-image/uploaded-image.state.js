(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('uploaded-image', {
            parent: 'entity',
            url: '/uploaded-image?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'danceFormApp.uploadedImage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/uploaded-image/uploaded-images.html',
                    controller: 'UploadedImageController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uploadedImage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('uploaded-image-detail', {
            parent: 'entity',
            url: '/uploaded-image/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'danceFormApp.uploadedImage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/uploaded-image/uploaded-image-detail.html',
                    controller: 'UploadedImageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uploadedImage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UploadedImage', function($stateParams, UploadedImage) {
                    return UploadedImage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'uploaded-image',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('uploaded-image-detail.edit', {
            parent: 'uploaded-image-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uploaded-image/uploaded-image-dialog.html',
                    controller: 'UploadedImageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UploadedImage', function(UploadedImage) {
                            return UploadedImage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('uploaded-image.new', {
            parent: 'uploaded-image',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uploaded-image/uploaded-image-dialog.html',
                    controller: 'UploadedImageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fullName: null,
                                path: null,
                                externalPath: null,
                                uploadedBy: null,
                                uploadedDate: null,
                                contentType: null,
                                content: null,
                                contentContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('uploaded-image', null, { reload: 'uploaded-image' });
                }, function() {
                    $state.go('uploaded-image');
                });
            }]
        })
        .state('uploaded-image.edit', {
            parent: 'uploaded-image',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uploaded-image/uploaded-image-dialog.html',
                    controller: 'UploadedImageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UploadedImage', function(UploadedImage) {
                            return UploadedImage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('uploaded-image', null, { reload: 'uploaded-image' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('uploaded-image.delete', {
            parent: 'uploaded-image',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uploaded-image/uploaded-image-delete-dialog.html',
                    controller: 'UploadedImageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UploadedImage', function(UploadedImage) {
                            return UploadedImage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('uploaded-image', null, { reload: 'uploaded-image' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
