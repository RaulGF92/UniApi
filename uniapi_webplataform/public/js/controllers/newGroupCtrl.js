angular.module('menuApp').controller("newGroupCtrl", function ($scope,$http,uniapi) {
			$scope.load=function(){
				$scope.type="PUBLIC_GROUP";
				$scope.fatherID=window.localStorage.getItem("groupTarget");
				if($scope.fatherID!=null){				
					uniapi.getGroup($scope.fatherID).then(function(response){
						$scope.father=response.groups[0];					
						$scope.sharingGroupPermissions=$scope.convertVector($scope.father.sharingGroup);
						$scope.projectPropertiesPermissions=$scope.convertVector($scope.father.projectProperties);
						$scope.memberGestionPermissions=$scope.convertVector($scope.father.memberGestion);
						$scope.groupCreationPermissions=$scope.convertVector($scope.father.groupCreation);
						window.localStorage.setItem("groupTarget",null);

						$scope.prepareTheCheckBox("sharingGroupPermissions",$scope.sharingGroupPermissions);
						$scope.prepareTheCheckBox("projectPropertiesPermissions",$scope.projectPropertiesPermissions);
						$scope.prepareTheCheckBox("memberGestionPermissions",$scope.memberGestionPermissions);
						$scope.prepareTheCheckBox("groupCreationPermissions",$scope.groupCreationPermissions);	
					});
				}
		
			};
			angular.element(document).ready($scope.load());
			$scope.prepareTheCheckBox=function(checkBoxID,vector){
				if(!vector[0]){
					for(var i=0;i<vector.length;i++){
						$("#"+checkBoxID+i).removeProp("checked");
						$("#"+checkBoxID+i).attr("disabled","disabled");
						$("#"+checkBoxID+i).val("false");
					}						
				}else{
					for(var i=0;i<vector.length;i++){
						if(!vector[i]){
							$("#"+checkBoxID+i).removeProp("checked");
							$("#"+checkBoxID+i).attr("disabled","disabled");
							$("#"+checkBoxID+i).val("false");
						}
					}
				}
				
			};
			$scope.checkBox=function(checkBox){
				var check=$("#"+checkBox).prop("checked");
				if(!check){
					for(var i=0;i<=3;i++){
						$("#"+checkBox+i).removeProp("checked");
						$("#"+checkBox+i).attr("disabled","disabled");
						
					
						if(checkBox=="sharingGroupPermissions"){
							$scope.sharingGroupPermissions[i]=false;
							$("#"+checkBox+i).val("false");					
						}
						if(checkBox=="projectPropertiesPermissions" && i<3){
							$scope.projectPropertiesPermissions[i]=false;
							$("#"+checkBox+i).val("false");					
						}
						if(checkBox=="memberGestionPermissions"){
							$scope.memberGestionPermissions[i]=false;
							$("#"+checkBox+i).val("false");			
						}
						if(checkBox=="groupCreationPermissions"){
							$scope.groupCreationPermissions[i]=false;
							$("#"+checkBox+i).val("false");
							
						}
					}
				}else{
					for(var i=0;i<=3;i++){
						
						if(checkBox=="sharingGroupPermissions"){
							if($scope.convertVector($scope.father.sharingGroup)[i]){
								$("#"+checkBox+i).prop("checked","true");
								$("#"+checkBox+i).removeAttr("disabled");
								$scope.sharingGroupPermissions[i]=true;		
								$("#"+checkBox+i).val("true");
							}			
						}
						if(checkBox=="projectPropertiesPermissions" && i<3){
							if($scope.convertVector($scope.father.projectProperties)[i]){
								$("#"+checkBox+i).prop("checked","true");
								$("#"+checkBox+i).removeAttr("disabled");
								$scope.projectPropertiesPermissions[i]=true;
								$("#"+checkBox+i).val("true");
							}		
						}
						if(checkBox=="memberGestionPermissions"){
							if($scope.convertVector($scope.father.memberGestion)[i]){
								$("#"+checkBox+i).prop("checked","true");
								$("#"+checkBox+i).removeAttr("disabled");
								$scope.memberGestionPermissions[i]=true;
								$("#"+checkBox+i).val("true");
							}				
						}
						if(checkBox=="groupCreationPermissions"){
							if($scope.convertVector($scope.father.groupCreation)[i]){
								$("#"+checkBox+i).prop("checked","true");
								$("#"+checkBox+i).removeAttr("disabled");		
								$scope.groupCreationPermissions[i]=true;
								$("#"+checkBox+i).val("true");
							}
						}
					}
				}
			};
			$scope.clickTypeGroup=function(id){
				switch(id){
					case "privateButton":
						$("#privateButton").addClass("active");
						$("#mainButton").removeClass("active");
						$("#publicButton").removeClass("active");
						$scope.type="RESTRICTED_GROUP";
					break;
					case "publicButton":
						$("#publicButton").addClass("active");
						$("#privateButton").removeClass("active");
						$("#mainButton").removeClass("active");
						$scope.type="PUBLIC_GROUP";
					break;
					case "mainButton":
						$("#publicButton").removeClass("active");
						$("#privateButton").removeClass("active");
						$("#mainButton").addClass("active");
						$scope.type="MAIN_GROUP";
					break;
				};
			};
			$scope.parseVector=function(vector){
				var aux=new Array();					
				for(var i=0;i<vector.length;i++){
					if(vector[i]==true){
						aux.push("YES");
					}
					if(vector[i]==false){
						aux.push("NO");
					}
				}
				return aux;
			};
			$scope.convertVector=function(vector){
				var aux=new Array();					
				for(var i=0;i<vector.length;i++){
					if(vector[i]=="YES"){
						aux.push(true);
					}
					if(vector[i]=="NO"){
						aux.push(false);
					}
				}
				return aux;
			};
			$scope.makeSubmit=function(){

				$scope.sharingGroupPermissions=$scope.parseVector($scope.sharingGroupPermissions);
				$scope.projectPropertiesPermissions=$scope.parseVector($scope.projectPropertiesPermissions);
				$scope.memberGestionPermissions=$scope.parseVector($scope.memberGestionPermissions);
				$scope.groupCreationPermissions=$scope.parseVector($scope.groupCreationPermissions);

				var groupToCreate=new group(
					$scope.name,
					new Date().getTime(),
					$scope.type,
					$scope.sharingGroupPermissions,
					$scope.projectPropertiesPermissions,
					$scope.memberGestionPermissions,
					$scope.groupCreationPermissions,
					$scope.description
				);
				uniapi.createGroup(groupToCreate)
					.then(function(response){
						if(response.state==0){
							var groupID=response.relatedIDs[0];
							uniapi.createSubgroup($scope.fatherID,groupID)
							.then(function(response){
								if(response.state==0){
									alert("Se ha realizado completa la petición");
								}else{
									alert("Se ha creado el grupo, pero no el enlace con el grupo padre. Contacte con un administrador para arreglarlo.");
								}
							});
						}else{
							alert("No se ha podido realizar el subgrupo");
						}
				});
			};
		});
