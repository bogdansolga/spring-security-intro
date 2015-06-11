'use strict';

angular.module('springSecurityIntro')
    .controller('userController', function($scope, $cookies, userService) {
        userService.all.get({}, function success(data) {
            $scope.users = data;
        });

        $scope.hasAccess = function() {
            var role = $cookies['role'];
            return role === 'manager';
        }
    });