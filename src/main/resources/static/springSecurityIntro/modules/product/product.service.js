'use strict';

angular.module('springSecurityIntro')
    .factory('productService', function($resource){
        return {
            all:    $resource('/product/', {},
                {
                    'get': {method: 'GET', isArray: true}
                }
            ),

            one:    $resource('/product/:id', {id: '@id'},
                {
                    'delete':       {method: 'DELETE'},
                    'create':       {method: 'POST'}
                }
            )
        }
    });
