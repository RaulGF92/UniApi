angular.module('menuApp').directive('uniapiPath', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/uniapiPath.htm",
		scope: {
      			ngOnlypublic: '@'
    		},
		controller: ['$scope', 'uniapi','$compile', function($scope, uniapi,$compile) {
			
			$scope.load=function(){
				uniapi.getMainPathProjects()
				.then(function(response){
					$scope.projectsPath=response;
				});
				console.log("--------------------");
				uniapi.getMainPathGroups()
				.then(function(response){
					$scope.groupsPath=response;
				});
				$scope.navigations=[];
				$scope.navigations.push({name:"home",id:"main"});
				uniapi.getPublicID().then(function(response){
					window.localStorage.setItem("groupTarget",response);
					window.localStorage.setItem("projectTarget",response);
				});
				
			};
			$scope.go=function(groupID){
				if(groupID == "main"){
					uniapi.getPublicID().then(function(response){
						window.localStorage.setItem("groupTarget",response);
						window.localStorage.setItem("projectTarget",response);
					});
					uniapi.getMainPathProjects()
					.then(function(response){
						console.log("projects");
						console.log(response);
						$scope.projectsPath=response;
					});
					console.log("--------------------");
					uniapi.getMainPathGroups()
					.then(function(response){
						$scope.groupsPath=response;
					});

					$scope.navigations=[];
					$scope.navigations.push({name:"home",id:"main"});
				}else{
					window.localStorage.setItem("groupTarget",groupID);
					window.localStorage.setItem("projectTarget",groupID);
					for(var i=0;i<$scope.navigations.length;i++){
						$("menu"+groupID).removeClass("active");
							if($scope.navigations[i].id == groupID){
								$("menu"+groupID).addClass("active");
								$scope.navigations.splice(i, $scope.navigations.length);
								$scope.enterGroup(groupID);
								return;
							}
					}
				}			
			};
			$scope.clickEnterGroup=function(groupID){
				$scope.enterGroup(groupID);
				window.localStorage.setItem("groupTarget",groupID);
				window.localStorage.setItem("projectTarget",groupID);
			};
			$scope.enterGroup=function(groupID){
				uniapi.getGroup(groupID).then(function(response){
					$scope.navigations.push({name:response.groups[0].name,id:groupID});	
					uniapi.getPathProjects(groupID)
					.then(function(response){
						$scope.projectsPath=response;
					});
					uniapi.getPathGroups(groupID)
					.then(function(response){
						$scope.groupsPath=response;
					});
				});
			};
			$scope.clickInfoGroup=function(groupID){
				$("#pathContent").append($compile("<a ng-click='closeModal()' style='margin-left: 95%;'><spam class='glyphicon glyphicon-remove'></spam></a>")($scope));
				$("#pathContent").append($compile("<gestiongroup ng-group='"+groupID+"' ng-visit=true ></gestiongroup>")($scope));
				$("#pathModal").css("display","block");
	
			};
			$scope.clickInfoProject=function(projectID){
				$("#pathContent").append($compile("<a ng-click='closeModal()' style='margin-left: 95%;'><spam class='glyphicon glyphicon-remove'></spam></a>")($scope));
				$("#pathContent").append($compile("<gestionproject ng-project='"+projectID+"' ng-visitproject=true ></gestionproject>")($scope));
				$("#pathModal").css("display","block");
	
			};
			$scope.closeModal=function(){				
				$("#pathContent").empty();
				$("#pathModal").css("display","none");
			};
			angular.element(document).ready($scope.load());

		}],
	}
	
	
});
