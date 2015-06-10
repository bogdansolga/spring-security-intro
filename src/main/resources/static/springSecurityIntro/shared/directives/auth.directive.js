'use strict';

angular.module('springSecurityIntro')
    .directive('auth', function($rootScope, $http, $cookies, authService) {
        return {
            templateUrl: "springSecurityIntro/shared/templates/login.html",
            link: function(scope) {
                scope.login = function() {
                    authService.login(scope.login).success(function(data) {
                        // this callback will be called asynchronously when the response is available
                        scope.authenticated = true;

                        if (data && data.role && data.name) {
                            $cookies['name'] = data.name;
                            $cookies['role'] = data.role;
                        }

                        $rootScope.$broadcast('login', scope);
                        $('#login').modal('hide');
                    }).error(function(data, status) {
                        // called asynchronously if an error occurs or the server returns response with an error status
                        if (status == 401 || status == 403) {
                            // unauthorized
                            scope.authError = data.message;
                        } else {
                            console.log(data);
                        }
                    });
                };

                scope.logout = function() {
                    $http.post('/logout').success(function() {
                        scope.authenticated = false;
                        $rootScope.$broadcast('logout', scope);
                    });
                };
            }
        }
    });