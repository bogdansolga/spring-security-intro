'use strict';

angular.module('springSecurityIntro')
    .controller('mainController', function($scope, $location, $cookies) {
        $scope.$on('login', function(details) {
            $scope.authenticated = true;

            var role = $cookies['role'];
            if (angular.isDefined(role)) {
                $scope.role = role;
            }

            $scope.name = $cookies['name'];
            $location.path('/');
        });

        $scope.$on('logout', function() {
            $scope.authenticated = false;
            $scope.role = undefined;

            $cookies['name'] = undefined;
            $cookies['role'] = undefined;

            $location.path('/');
        });
    });