angular.module('menuApp').controller("listProjectCtrl",function ($scope,$http,uniapi,$compile){

			uniapi.getMyProjects().then(function(data){
				$scope.projects=data;
			});
			$scope.makeClick = function(projectID){
				$("#projectContent").append($compile("<a ng-click='closeModal()'><spam class='glyphicon glyphicon-remove'></spam></a>")($scope));
				$("#projectContent").append($compile("<gestionproject ng-project='"+projectID+"' ng-visit=false ></gestionproject>")($scope));
				$("#projectModal").css("display","block");
			};
			$scope.closeModal=function(){				
				$("#projectContent").empty();
				$("#projectModal").css("display","none");
			};
});
