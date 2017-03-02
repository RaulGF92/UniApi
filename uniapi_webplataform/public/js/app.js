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
		    .when("/Exe", {
			template : "<h1>Configuration:</h1>"
		    })
		    .when("/Admin", {
			templateUrl : "pages/admin.htm",
			controller: "adminCtrl"
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
