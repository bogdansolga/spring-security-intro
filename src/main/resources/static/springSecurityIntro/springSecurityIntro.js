'use strict';

angular.module('springSecurityIntro', ['ngRoute', 'ngResource', 'ngCookies'])
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/',         {templateUrl: 'springSecurityIntro/shared/templates/home.html',      controller: 'mainController'})
            .when('/home',     {templateUrl: 'springSecurityIntro/shared/templates/home.html',      controller: 'mainController'})

            .when('/user',     {templateUrl: 'springSecurityIntro/modules/user/user.html',          controller: 'userController'})
            .when('/product',  {templateUrl: 'springSecurityIntro/modules/product/product.html',    controller: 'productController'})

            .otherwise({redirectTo: '/'});
    }]
);
