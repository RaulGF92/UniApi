angular.module('menuApp').controller("addItems",function ($scope,$http,uniapi,$compile){

			$scope.optionProject = function(projectID){
				$scope.groupID=window.localStorage.getItem("projectTarget");
				if($scope.groupID == null)
					return;
				uniapi.getGroup($scope.groupID).then(function(response){
					if(response.state!=0)
						return;
					if(response.groups[0].sharingGroup[0]=="YES" && response.groups[0].sharingGroup[1]=="YES"){
						$("#addContent").append(
							$compile(
								"<a ng-click='closeModal()'><spam class='glyphicon glyphicon-remove'></spam></a>"
						)($scope));
						$("#addContent").append(
							$compile(
								"<createproject-option ng-group='"+$scope.groupID+"'></createproject-option>"
						)($scope));

						$("#addModal").css("display","block");
					}else{
						alert("No tienes permisos para crear un proyecto");
					}
				});
			};
			$scope.optionGroup = function(projectID){
				$scope.groupID=window.localStorage.getItem("groupTarget");
				if($scope.groupID == null)
					return;
				uniapi.getGroup($scope.groupID).then(function(response){
					if(response.state!=0)
						return;
					if(response.groups[0].groupCreation[0]=="YES" && response.groups[0].groupCreation[1]=="YES"){
						window.location.href="/#NewGroup"
					}else{
						alert("No tienes permisos para crear un grupo");
					}
				});
			};
			$scope.closeModal=function(){				
				$("#addContent").empty();
				$("#addModal").css("display","none");
			};
});
