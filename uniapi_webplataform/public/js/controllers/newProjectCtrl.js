angular.module('menuApp').controller("newProjectCtrl", function ($scope,$http,uniapi) {
			$scope.load=function(){
				$scope.fatherID=window.localStorage.getItem("projectTarget");
				if($scope.fatherID!=null){				
					uniapi.getGroup($scope.fatherID).then(function(response){
						$scope.father=response.groups[0];					
						window.localStorage.setItem("projectTarget",null);

					});
				}
		
			};
			angular.element(document).ready($scope.load());
			$scope.counter=0;
			$scope.addArgument = function(){
				$scope.counter++;
				$("#arrayDefaultInput").append("<li id='element_'"+$scope.counter+">["+$scope.counter+"]: <input id='value_"+$scope.counter+"' type='text'></li>");
			};
			$scope.removeArgument = function(){
				$("#arrayDefaultInput").children().get($scope.counter).remove();
				$scope.counter--;
			};
			$scope.makeSubmitDefaultInputs = function(){
				var response=new Array();
				if($scope.counter==0 && $("#value_"+0).val() == "")
					return response;
						
				for(var j=0;j<=$scope.counter;j++){
					response.push($("#value_"+j).val());
				}
				return response;
			};
			$scope.makeSubmit = function (){
				if($scope.type == null){
					alert("selecciona un tipo de proyecto");
					return;
				}
				var proyecto= new project("vacio",
							$scope.name, 
							$scope.type,
							$scope.description,
							$scope.gitRepositoryURL,
							$scope.email,
							$scope.password,
							new Date().getTime(),
							new Date().getTime(),
							$scope.mainName,
							$scope.responseName,
							//$scope.defaultInputs,
							$scope.makeSubmitDefaultInputs(),							
							$scope.inputDescription,
							$scope.outputDescription);
				console.log(proyecto);
				uniapi.createProject(proyecto)
				.then(function(response){
					if(response.state==0){
						uniapi.putGroupProject($scope.fatherID,response.relatedIDs[0]).then(function(response){
							if(response.state==0){
								alert("Se ha realizado la creación");
								window.location.href="/#main";
							}else{
								alert("Se ha realizado la creación, pero no se a enlazado con el grupo. Avise a un administrador");						window.location.href="/#main";
							}
						});						
						
					}else{
						alert("No se ha realizado la creación");
						window.location.href="/#main";
					}
				});
			};
			$scope.clickType= function (type){
				$scope.type=type;
				switch(type){
					case 'PYTHON':
        					$("#pythonPick").addClass("typeActive");
						$("#javaPick").removeClass("typeActive");
						$("#octavePick").removeClass("typeActive");
						$("#rPick").removeClass("typeActive");
						$scope.type="PYTHON";
        				break;
					case 'JAVA':
        					$("#javaPick").addClass("typeActive");
						$("#pythonPick").removeClass("typeActive");
						$("#octavePick").removeClass("typeActive");
						$("#rPick").removeClass("typeActive");
						$scope.type="JAVA";        				
					break;
					case 'R':
        					$("#rPick").addClass("typeActive");
						$("#javaPick").removeClass("typeActive");
						$("#octavePick").removeClass("typeActive");
						$("#pythonPick").removeClass("typeActive");
						$scope.type="R";
        				break;
					case 'OCTAVE':
        					$("#octavePick").addClass("typeActive");
						$("#javaPick").removeClass("typeActive");
						$("#pythonPick").removeClass("typeActive");
						$("#rPick").removeClass("typeActive");
						$scope.type="OCTAVE";        				
					break;
    					default:
        					$("#pythonPick").removeClass("typeActive");
						$("#javaPick").removeClass("typeActive");
						$("#octavePick").removeClass("typeActive");
						$("#rPick").removeClass("typeActive");
			
				}
			}
		});
