angular.module('menuApp').controller("newProjectCtrl", function ($scope,$http) {
			$scope.clean = function(){
				
			};
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
				var anwser=sendNewProyect(proyecto);
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
