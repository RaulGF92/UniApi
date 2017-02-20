angular.module('menuApp').controller("newGroupCtrl", function ($scope,$http) {
			$scope.loadPermission=function(){
				$scope.sharingGroupPermissions=[false,false,false,false];
				$scope.projectPropertiesPermissions=[false,false,false];
				$scope.memberGestionPermissions=[false,false,false,false];
				$scope.groupCreationPermissions=[false,false,false,false];
			};
			angular.element(document).ready($scope.loadPermission());
			$scope.checkBox=function(checkBox){
				var check=$("#"+checkBox).prop("checked");
				if(!check){
					for(var i=1;i<=3;i++){
						$("#"+checkBox+i).removeProp("checked");
						$("#"+checkBox+i).attr("disabled","disabled");
						$("#"+checkBox+i).val("false");
					}
					if(checkBox=="sharingGroupPermissions"){
						$scope.sharingGroupPermissions[1]=false;
						$scope.sharingGroupPermissions[2]=false;
						$scope.sharingGroupPermissions[3]=false;						
					}
					if(checkBox=="projectPropertiesPermissions"){
						$scope.projectPropertiesPermissions[1]=false;
						$scope.projectPropertiesPermissions[2]=false;					
					}
					if(checkBox=="memberGestionPermissions"){
						$scope.memberGestionPermissions[1]=false;
						$scope.memberGestionPermissions[2]=false;
						$scope.memberGestionPermissions[3]=false;				
					}
					if(checkBox=="groupCreationPermissions"){
						$scope.groupCreationPermissions[1]=false;
						$scope.groupCreationPermissions[2]=false;
						$scope.groupCreationPermissions[3]=false;
					}
				}else{
					for(var i=1;i<=3;i++){
						$("#"+checkBox+i).prop("checked","true");
						$("#"+checkBox+i).removeAttr("disabled");
						$("#"+checkBox+i).val("true");
						if(checkBox=="sharingGroupPermissions"){
							$scope.sharingGroupPermissions[1]=true;
							$scope.sharingGroupPermissions[2]=true;
							$scope.sharingGroupPermissions[3]=true;						
						}
						if(checkBox=="projectPropertiesPermissions"){
							$scope.projectPropertiesPermissions[1]=true;
							$scope.projectPropertiesPermissions[2]=true;					
						}
						if(checkBox=="memberGestionPermissions"){
							$scope.memberGestionPermissions[1]=true;
							$scope.memberGestionPermissions[2]=true;
							$scope.memberGestionPermissions[3]=true;				
						}
						if(checkBox=="groupCreationPermissions"){
							$scope.groupCreationPermissions[1]=true;
							$scope.groupCreationPermissions[2]=true;
							$scope.groupCreationPermissions[3]=true;
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
				vector=aux;
				return vector;
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
				console.log(groupToCreate);
				sendNewGroup(groupToCreate);
			};
		});
