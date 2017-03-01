angular.module('menuApp').directive('uniapiPath', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/uniapiPath.htm",
		scope: {
      			ngOnlypublic: '@'
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
			$scope.load=function(){
				var publicID=uniapi.getPublicID();
				uniapi.getPath(publicID)
				.then(function(response){
					$scope.groupsPath=response.groups;
				});
				uniapi.getGroupProjects(publicID)
				.then(function(response){
					$scope.projectsPath=response.projects;
				});		
				$scope.navigations=[];
				$scope.navigations.push({name:"home",id:publicID});
			}
		}],
	}
	
	
});
