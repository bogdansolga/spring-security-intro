'use strict';

angular.module('springSecurityIntro')
    .controller('productController', function($scope, $cookies, productService) {
        productService.all.get({}, function success(data) {
            $scope.products = data;
        });

        $scope.hasAccess = function() {
            var role = $cookies['role'];
            return (role === 'admin' || role === 'manager');
        }
    });