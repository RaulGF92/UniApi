var app = angular.module('menuApp',["ngRoute"]);
	app.config(function($routeProvider) {
		    $routeProvider
		    .when("/", {
			templateUrl : "pages/main.htm"
			
		    })
		    .when("/Groups", {
			templateUrl : "pages/listGroup.htm",
			controller : "listGroupCtrl"
		    })
		    .when("/Projects", {
			templateUrl : "pages/listProject.htm",
			controller : "listProjectCtrl"
		    })
		    .when("/Config", {
			template : "<h1>Configuration:</h1>"
		    })
		    .when("/Admin", {
			template : "<h1>Administration:</h1>"
		    })
		    .when("/NewProject", {
			templateUrl : "pages/newProject.htm",
			controller : "newProjectCtrl"
		    })
		    .when("/NewGroup", {
			templateUrl : "pages/newGroup.htm",
			controller : "newGroupCtrl"
		    });
	});
