angular.module('menuApp').directive('runningProject', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/runningProject.htm",
		scope: {
      			
    		},
		controller: ['$scope','$compile', 'uniapi', function($scope,$compile,uniapi) {
			$scope.load=function(){
				uniapi.getExecutionsRunning()
				.then(function(response){
					console.log(response);
					$scope.principioExecution=0;
					$scope.finExecution=3;
					$scope.fullExecution=response;
					if($scope.fullExecution.lenght<4){
						$scope.exes=$scope.fullExecution;
					}else{
						$scope.exes=$scope
								.fullExecution
								.slice($scope.principioExecution,$scope.finExecution);
					}
					
				});
			};			
			
			$scope.clickLeft=function(){
				if($scope.principioExecution!=0){
					$scope.principioExecution--;
					$scope.finExecution++;
					$scope.exes=$scope
							.fullExecution
							.slice(principioExecution,finExecution);
				}
			};
			$scope.clickRight=function(){
				if($scope.finExecution!=$scope.fullExecution.length-1){
					if($scope.finExecution<$scope.fullExecution.length-1){
						$scope.principioExecution++;
						$scope.finExecution++;
						$scope.exes=$scope
							.fullExecution
							.slice(principioExecution,finExecution);
					}
				}
			};
			$scope.clickEnterExecution=function(executionID){
				$("#runExecutionContent").empty();
				$("#runExecutionContent").append($compile("<a ng-click='closeExeModal()' style='margin-left: 95%;'><spam class='glyphicon glyphicon-remove'></spam></a>")($scope));
				$("#runExecutionContent").append($compile("<ejecution-project ng-exe='"+executionID+"' ng-exegroup='' ng-exeproject=''></ejecution-project>")($scope));
				$("#runExecution").css("display","block");				
			};
			$scope.closeExeModal=function(){				
				$("#runExecutionContent").empty();
				$("#runExecution").css("display","none");
			};
			angular.element(document).ready($scope.load());
		}],
	}
	
	
});
