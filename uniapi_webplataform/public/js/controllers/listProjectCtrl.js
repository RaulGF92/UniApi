angular.module('menuApp').controller("listProjectCtrl",function ($scope,$http){

			var tokenSession=localStorage.getItem("tokenSession");
			$scope.projects=new Array();
			$http({
				method : "GET",
				url : "http://localhost:8080/project/"+tokenSession+"/all"
			}).then(function(response){
				console.log(response.data.relatedIDs);
				$scope.IDs=response.data.relatedIDs;
				if($scope.IDs.length<=0){
					$("#infoNoProjects").css("display","block");
				}
				for(var j=0;j<$scope.IDs.length;j++){
					$http({
						method:"GET",
						url:"http://localhost:8080/project/"+tokenSession+"/"+$scope.IDs[j]
					}).then(function(response){
						console.log(response);
						if(response.data.state==0)
							$scope.projects.push(response.data.projects[0]);
					});
							
				}

			});

			$scope.loadProjects= function(){
				var tokenSession=localStorage.getItem("tokenSession");
				$scope.projects=new Array();
				$http({
					method : "GET",
					url : "http://localhost:8080/project/"+tokenSession+"/all"
				}).then(function(response){
					console.log(response.data.relatedIDs);
					$scope.IDs=response.data.relatedIDs;
					for(var j=0;j<$scope.IDs.length;j++){
						$http({
							method:"GET",
							url:"http://localhost:8080/project/"+tokenSession+"/"+$scope.IDs[j]
						}).then(function(response){
							console.log(response);
							if(response.data.state==0)
								$scope.projects.push(response.data.projects[0]);
						});
							
					}

				});
		
			};

			$scope.makeClick = function(id){
				if($("#secondaryInfo-"+id).css("display") == "block")
					return;
				for(var i=0;i<$scope.IDs.length;i++){
					if(id==i){
						$scope.lookProject(i);
					}else{
						$scope.unLookProject(i);
					}
				}

			};
			$scope.makeClickUpdate = function(id){
				//Abrimos un modal y preguntamos si queremos modificar.
				console.log($scope.projects[id]);
				$scope.msg="¿De verdad quieres editar el proyecto "+$scope.projects[id].name+" ?";
				$scope.id=id;
				$scope.actionDelete=false;
				$scope.actionUpdate=true;
				$scope.actionShare=false;
				$("#projectContent").css("width","50%");				
				$("#projectModal").css("display","block");
			};
			$scope.makeClickDelete = function(id){
				//Abrimos un modal y preguntamos si queremos modificar.
				console.log($scope.projects[id]);
				$scope.msg="¿De verdad quieres eliminar el proyecto "+$scope.projects[id].name+" ?";
				$scope.id=id;
				$scope.actionDelete=true;
				$scope.actionUpdate=false;
				$scope.actionShare=false;
				$("#projectContent").css("width","50%");				
				$("#projectModal").css("display","block");
			};
			$scope.makeAction= function(){
				
				if($scope.actionUpdate)
					$scope.updateProject();
				if($scope.actionDelete)
					$scope.deleteProject();
				if($scope.actionShare)
					$scope.shareProject();
			};
			$scope.dontMakeAction= function(){
				$("#projectModal").css("display","block");
			};
			$scope.lookProject= function(id){
				$("#secondaryInfo-"+id).css("display","block");
				$("#action-"+id).css("display","block");
				$("#description-"+id).css("border","1px solid #ccc");
			};
			$scope.unLookProject= function(id){
				$scope.id=id;
				$("#secondaryInfo-"+id).css("display","none");
				$("#action-"+id).css("display","none");
				$("#description-"+id).css("border","0");
			};
			$scope.updateProject=function(){
				$("#loading").css("display","block");
				$("#question").css("display","none");
				console.log("name: "+$scope.projects[$scope.id].name);
				$http({
					method:"PATCH",
					url:"http://localhost:8080/project/"+tokenSession+"/"+$scope.projects[$scope.id].hashcode,
					data:JSON.stringify($scope.projects[$scope.id])					
				}).then(function(response){
					console.log(response.data);
					if(response.data.state != 0){
						$("#loadingGif").css("display","none");
						$("#wrongGif").css("display","block");
						var counter=0;
						while(counter<200){
							counter++;
						}
					}else{
						$scope.loadProjects();						
						var counter=0;
						while(counter<200){
							counter++;
						}						
						$("#projectModal").css("display","none");
						
					}
				});
			};
			$scope.deleteProject=function(){
				$("#loading").css("display","block");
				$("#question").css("display","none");
				$http({
					method:"DELETE",
					url:"http://localhost:8080/project/"+tokenSession+"/"+$scope.projects[$scope.id].hashcode,
				}).then(function(response){
					console.log(response.data);
					if(response.data.state != 0){
						$("#loadingGif").css("display","none");
						$("#wrongGif").css("display","block");
						var counter=0;
						while(counter<200){
							counter++;
						}
					}else{
						$scope.loadProjects();						
						var counter=0;
						while(counter<200){
							counter++;
						}						
						$("#projectModal").css("display","none");
						
					}
				});
			};
		});
