angular.module('menuApp').directive('seeAllprojects', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/seeAllProjects.htm",
		scope: {
      			
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
			uniapi.getAllProjects().then(function(response){
				$scope.projects=response.projects;
				for(var i=0;i<$scope.projects.length;i++){
					$scope.projects[i].creationDate=new Date($scope.projects[i].creationDate).toLocaleDateString();
				}
			});
		}],
	}
	
	
});
