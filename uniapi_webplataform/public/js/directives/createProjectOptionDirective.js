angular.module('menuApp').directive('createprojectOption', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/createProjectOption.htm",
		scope: {
      			ngGroup: '@'
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
			
			uniapi.getMyProjects().then(function(response){
					$scope.data = {
					    model: null,
					    projects: response
					};					
			});
			$scope.createNewProject=function(){
				window.localStorage.setItem("projectTarget",$scope.ngGroup);
				window.location.href="/#NewProject";
			};
			$scope.putProjectIntoGroup=function(){
				if($scope.data.model =="null"){
					alert("projectoNull");
				}else{
					alert($scope.data.model);
				}
				uniapi.putGroupProject($scope.ngGroup,$scope.data.model)
				.then(function(response){
					if(response.state==0){
						alert("Se ha realizado la introducción al grupo");
						window.location.href="/#Groups";
					}else{
						alert("no se ha realizado la introducción al grupo");
					}
				});
			};
		}],
		link: function(scope, iElement, iAttrs, ctrl) {
		     		scope.id=iAttrs.ngProject;
			
		}
	}
	
	
});
