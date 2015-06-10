'use strict';

angular.module('springSecurityIntro')
    .controller('userController', function($scope, userService) {
        userService.all.get({}, function success(data) {
            $scope.users = data;
        });
    });