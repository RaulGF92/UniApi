angular.module('menuApp').controller("executionServicesCtrl", function ($scope,uniapi,$compile) {
	$scope.openModal=function(executionID){
		$("#executionContent").append($compile("<a ng-click='closeListExeModal()' style='margin-left: 95%;'><spam class='glyphicon glyphicon-remove'></spam></a>")($scope));
		$("#executionContent").append($compile("<ejecution-project ng-exe='"+executionID+"' ng-exegroup='' ng-exeproject=''></ejecution-project>")($scope));
		$("#executionModal").css("display","block");
	};
	$scope.closeListExeModal=function(){				
				$("#executionContent").empty();
				$("#executionModal").css("display","none");
	};
	$scope.loadliRun=function(id,item){
		$("#"+id).append($compile("<li class='list-group-item list-group-item-warning dameAire' ng-click=openModal('"+item.hashcode+"')>"+item.nameExecution+"</li>")($scope));
	};
	$scope.loadliFin=function(id,item){
		var clase=""
		if(item.stateOfExecution =="FINISH_SUCESS"){
			clase="list-group-item-success"
		}
		if(item.stateOfExecution =="FINISH_WITH_ERROR"){
			clase="list-group-item-warning"
		}
		$("#"+id).append($compile("<li class='list-group-item "+clase+" dameAire' ng-click=openModal('"+item.hashcode+"')>"+item.nameExecution+"</li>")($scope));
	};
	$scope.exeload=function(){
		uniapi.getExecutionsRunning()
				.then(function(responseas){
					console.log(responseas.length);
					$scope.running=responseas;
					for(var i=0;i<$scope.running.length;i++){
						$scope.loadliRun("running",$scope.running[i]);
					}
		});
		uniapi.getExecutionFinish()
				.then(function(response){
					$scope.finish=response;
					console.log(response);	
					for(var i=0;i<$scope.finish.length;i++){
						$scope.loadliFin("finish",$scope.finish[i]);
					}
		});
	};
	angular.element(document).ready($scope.exeload());
});
