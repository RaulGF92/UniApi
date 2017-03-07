angular.module('menuApp').controller("listGroupCtrl",function($scope,$http,uniapi,$compile){
	uniapi.getMyGroups().then(function(response){
		$scope.myGroups=response;
	});
	$scope.clickGroup=function(idGroup){
		$("#groupContent").append($compile("<a ng-click='closeModal()' style='margin-left: 95%;'><spam class='glyphicon glyphicon-remove'></spam></a>")($scope));
		$("#groupContent").append($compile("<gestiongroup ng-group='"+idGroup+"' ng-visit=false ></gestiongroup>")($scope));
		$("#projectModal").css("display","block");
	};
	$scope.closeModal=function(){				
				$("#groupContent").empty();
				$("#projectModal").css("display","none");
	};
});
