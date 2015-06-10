'use strict';

angular.module('springSecurityIntro')
    .factory('authService', function ($http) {
        return {
            login: function(user) {
                var login = $.param(user);

                return $http({
                    method: 'POST',
                    url: '/login',
                    data: login,
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                });
            },

            logout: function(scope) {
                $http.post('/logout').success(function() {
                    scope.authenticated = false;
                });
            }
        };
    });