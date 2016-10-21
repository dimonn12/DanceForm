(function() {
    'use strict';

    angular
        .module('danceFormApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('uploaded-document', {
            parent: 'entity',
            url: '/uploaded-document',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'danceFormApp.uploadedDocument.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/uploaded-document/uploaded-documents.html',
                    controller: 'UploadedDocumentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uploadedDocument');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('uploaded-document-detail', {
            parent: 'entity',
            url: '/uploaded-document/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'danceFormApp.uploadedDocument.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/uploaded-document/uploaded-document-detail.html',
                    controller: 'UploadedDocumentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uploadedDocument');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UploadedDocument', function($stateParams, UploadedDocument) {
                    return UploadedDocument.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'uploaded-document',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('uploaded-document-detail.edit', {
            parent: 'uploaded-document-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uploaded-document/uploaded-document-dialog.html',
                    controller: 'UploadedDocumentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UploadedDocument', function(UploadedDocument) {
                            return UploadedDocument.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('uploaded-document.new', {
            parent: 'uploaded-document',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uploaded-document/uploaded-document-dialog.html',
                    controller: 'UploadedDocumentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                extension: null,
                                path: null,
                                externalPath: null,
                                content: null,
                                contentContentType: null,
                                uploadedDate: null,
                                uploadedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('uploaded-document', null, { reload: 'uploaded-document' });
                }, function() {
                    $state.go('uploaded-document');
                });
            }]
        })
        .state('uploaded-document.edit', {
            parent: 'uploaded-document',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uploaded-document/uploaded-document-dialog.html',
                    controller: 'UploadedDocumentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UploadedDocument', function(UploadedDocument) {
                            return UploadedDocument.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('uploaded-document', null, { reload: 'uploaded-document' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('uploaded-document.delete', {
            parent: 'uploaded-document',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uploaded-document/uploaded-document-delete-dialog.html',
                    controller: 'UploadedDocumentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UploadedDocument', function(UploadedDocument) {
                            return UploadedDocument.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('uploaded-document', null, { reload: 'uploaded-document' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
