angular.module('menuApp').directive('ejecutionProject', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/executionProject.htm",
		scope: {
      			ngExe:'@',
			ngExegroup:'@',
			ngExeproject:'@'
    		},
		controller: ['$scope','$interval','uniapi', function($scope,$interval,uniapi) {
			$scope.executedProject=function(){
				var inputs=$scope.input.get();
	
				uniapi.executedProject(inputs,$scope.ngExegroup,$scope.ngExeproject)
				.then(function(response){
					if(response.state==0){
						alert("Se ha iniciado la ejecucion correctamente");
					}else{
						alert("ha habido un error en la orden de ejecucion");
					}
				});
							
			};
			$scope.clickTab=function(tab){
				if(tab == "outputTAB"){
					$("#outputTAB").addClass("active");	
					$("#inputTAB").removeClass("active");
					$("#output").css("display","block");
					$("#input").css("display","none");
				}
				if(tab == "inputTAB"){
					$("#inputTAB").addClass("active");	
					$("#outputTAB").removeClass("active");
					$("#output").css("display","none");
					$("#input").css("display","block");
				}
			};
			$scope.jsonLoad=function(id,option,json){
				console.log(id);
				var container= document.getElementById(id);
				console.log(container);				
				var options = option;
				var editor = new JSONEditor(container, options);
				editor.set(json);
				return editor;
			};
			$scope.dateLoad=function(longInit,longFin){
				var init=longInit;
				if($scope.exe.finishDate == 0){
					$interval(function(init){ 
						$scope.dateExe=new Date(new Date().getTime()-new Date(longInit).getTime());
						$scope.executionTime=$scope.dateExe.toLocaleTimeString(); 
					}, 1000);
				}else{
					$scope.dateExe=new Date(longFin-longInit);
					$scope.executionTime=$scope.dateExe.toLocaleTimeString();
				}

				 
						
			};
			$scope.exeload=function(){
				console.log("execution:"+$scope.ngExe);
				console.log("group:"+$scope.ngExegroup);
				if($scope.ngExegroup == ""){
					
					uniapi.getProjectOfExecution($scope.ngExe)
					.then(function(response){
						$scope.projectExe=response;
					});
					
					uniapi.getExecution($scope.ngExe)
					.then(function(response){
						//Zona de resultado
						$scope.nuevo=false;
						console.log(response);
						$scope.exe=response.executions[0];
						$scope.dateLoad($scope.exe.creationDate,$scope.exe.finishDate);
						
						$scope.clickTab('outputTAB');
						$scope.startJson();
				
					});

				}else{
					$scope.nuevo=true;
					uniapi.getProject($scope.ngExeproject)
					.then(function(response){
						console.log(response);
						$scope.projectExe=response.projects[0];
						$scope.startJson();
					});
	
							
				}
				
			};
			$scope.startJson=function(){
				if($scope.ngExegroup == ""){
					//Zona de resultado
					var optionsInputs = {};
					var inputs = JSON.parse($scope.exe.inputJson);						
					$scope.input=$scope.jsonLoad("jsoneditorInput",optionsInputs,inputs);
						
					if($scope.exe.response != ""){
						var outputs = JSON.parse($scope.exe.response);
					}else{
						var outputs = {};
					}
					var optionsOutputs = {};
					
					$scope.output=$scope.jsonLoad("jsoneditorOutput",optionsOutputs,outputs);
				}else{
					//Zona de creado
					$scope.nuevo=true;
					var optionsNuevo = {};
					var nuevo = {"inputs": []};
					$scope.input=$scope.jsonLoad("jsoneditorInput",optionsNuevo,nuevo);	
				}
			};
			angular.element(document).ready($scope.exeload());
			
		}],
	}
	
	
});
