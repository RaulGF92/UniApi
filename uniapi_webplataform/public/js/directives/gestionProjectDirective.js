angular.module('menuApp').directive('gestionproject', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/gestionProject.htm",
		scope: {
      			ngProject: '@',
			ngVisitproject:'@'
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
			uniapi.getProject($scope.ngProject).then(function(response){
				$scope.project=response.projects[0];
				$scope.inputsDefault=$scope.project.defaultInputs.slice();
			});
			$scope.removeDefaultInput=function(){
				$scope.inputsDefault.pop();
			};
			$scope.resetDefaultInput=function(){
				$scope.inputsDefault=$scope.project.defaultInputs.slice();
			};
			$scope.addDefaultInput=function(){
				$scope.inputsDefault.push("");		
			};
			$scope.makeClickUpdate=function(){
				for(var i=0;i<$scope.inputsDefault.length;i++){				
					$scope.inputsDefault[i]=$("#value_"+i).val();
				}
				console.log($scope.inputsDefault);
				$scope.project.defaultInputs=$scope.inputsDefault.slice();
				if (confirm('¿Estas seguro que quieres actualizar el proyecto?')) {
					uniapi.updateProject($scope.ngProject,$scope.project).then(function(response){
						if(response.state==0){
							alert("se ha realizado la actualización.");
							uniapi.getProject($scope.ngProject).then(function(response){
								$scope.project=response.projects[0];
								$scope.inputsDefault=$scope.project.defaultInputs.slice();
							});
						}	
					});
				}
				
			};
			$scope.makeClickDelete=function(){
				if (confirm('¿Estas seguro que quieres borrar el proyecto?')) {
					uniapi.deleteProject($scope.ngProject).then(function(response){
						if(response.state==0){
							alert("se ha realizado la eliminación.");
							location.reload(); 
						}else{
							alert("Ha ocurrido un error!!");
							location.reload(); 
						}	
					});
				}
				
			};
			
			
		}],
		link: function(scope, iElement, iAttrs, ctrl) {
		     		scope.id=iAttrs.ngProject;
			
		}
	}
	
	
});
