'use strict';

angular.module('springSecurityIntro')
    .controller('productController', function($scope, productService) {
        productService.all.get({}, function success(data) {
            $scope.products = data;
        });
    });