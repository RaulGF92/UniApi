angular.module('menuApp').directive('ejecutionProject', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/executionProject.htm",
		scope: {
      			
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
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
			$scope.load=function(){
				var options = {};
				var container1= document.getElementById("jsoneditorInput");
				var editor1 = new JSONEditor(container1, options);

				var container2 = document.getElementById("jsoneditorOutput");
				var editor2 = new JSONEditor(container2, options);

				// set json
				var json = {
				    "Array": [1, 2, 3],
				    "Boolean": true,
				    "Null": null,
				    "Number": 123,
				    "Object": {"a": "b", "c": "d"},
				    "String": "Hello World"
				};

				editor1.set(json);
				editor2.set(json);

				// get json
				var json = editor1.get();
			};
			angular.element(document).ready($scope.load());
		}],
	}
	
	
});
