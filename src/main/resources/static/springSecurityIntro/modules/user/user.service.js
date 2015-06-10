'use strict';

angular.module('springSecurityIntro')
    .factory('userService', function($resource){
        return {
            all:    $resource('/user/', {},
                {
                    'get': {method: 'GET', isArray: true}
                }
            ),

            one:    $resource('/user/:id', {id: '@id'},
                {
                    'delete':       {method: 'DELETE'},
                    'create':       {method: 'POST'}
                }
            )
        }
    });
