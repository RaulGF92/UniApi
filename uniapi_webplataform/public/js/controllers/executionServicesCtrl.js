angular.module('menuApp').controller("executionServicesCtrl", function ($scope,$http,uniapi,$compile) {
	$scope.openModal=function(){
		$("#executionContent").append($compile("<a ng-click='closeModal()' style='margin-left: 95%;'><spam class='glyphicon glyphicon-remove'></spam></a>")($scope));
		$("#executionContent").append($compile("<ejecution-project></ejecution-project>")($scope));
		$("#executionModal").css("display","block");
	};
	$scope.closeModal=function(){				
				$("#executionContent").empty();
				$("#executionModal").css("display","none");
	};
});
