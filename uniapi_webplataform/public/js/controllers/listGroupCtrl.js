angular.module('menuApp').controller("listGroupCtrl",function($scope,$http,uniapi,$compile){
	uniapi.getMyGroups().then(function(response){
		$scope.myGroups=response;
	});
	$scope.clickGroup=function(idGroup){
		$("#groupContent").append($compile("<gestiongroup ng-group='"+idGroup+"'></gestiongroup>")($scope));
		$("#projectModal").css("display","block");
	};
});
