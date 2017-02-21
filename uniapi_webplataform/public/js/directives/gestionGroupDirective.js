angular.module('menuApp').directive('gestiongroup', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/gestionGroup.htm",
		scope: {
      			ngGroup: '@'
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
			
     			uniapi.getGroup($scope.ngGroup).then(function(data){
				$scope.group=data.groups[0];
				$scope.sharingGroupPermissions=$scope.parseVector($scope.group.sharingGroup);
				$scope.projectPropertiesPermissions=$scope.parseVector($scope.group.projectProperties);
				$scope.memberGestionPermissions=$scope.parseVector($scope.group.memberGestion);
				$scope.groupCreationPermissions=$scope.parseVector($scope.group.groupCreation);
				$scope.makeBioTAB();			
			});
			$scope.makeClickUpdate=function(){
				$scope.group.sharingGroup=$scope.convertVector($scope.sharingGroupPermissions);
				$scope.group.projectProperties=$scope.convertVector($scope.projectPropertiesPermissions);
				$scope.group.memberGestion=$scope.convertVector($scope.memberGestionPermissions);
				$scope.group.groupCreation=$scope.convertVector($scope.groupCreationPermissions);
				if (confirm('¿Estas seguro que quieres actualizar el grupo?')) {				
					uniapi.updateGroup($scope.ngGroup,$scope.group).then(function(response){
						console.log(response);
						if(response.state==0){
							alert("se ha realizado la actualización.");
						}else{
							alert("Ha ocurrido un error!!");
							location.reload(); 
						}	
					});
				}	
			};
			$scope.makeClickDelete=function(){
				if (confirm('¿Estas seguro que quieres actualizar el grupo?')) {				
					uniapi.deleteGroup($scope.ngGroup).then(function(response){
						if(response.state==0){
							alert("se ha realizado la eliminación.");
							location.reload(); 
						}else{
							alert("Ha ocurrido un error!!");
							location.reload(); 
						}	
					});
				}
			}
			$scope.changeTab=function(nameTab){
				switch(nameTab){
					case "bioTAB":
						$scope.makeBioTAB();

						$("#bioTAB").css("display","block");
						$("#subGroupsTAB").css("display","none");
						$("#projectInsideTAB").css("display","none");
						$("#permisionTAB").css("display","none");

						$("#bioTABTAB").addClass("active");
						$("#subGroupsTABTAB").removeClass("active");
						$("#projectInsideTABTAB").removeClass("active");
						$("#permisionTABTAB").removeClass("active");
						
					break;
					case "permisionTAB":

						$scope.makePermisionTAB();

						$("#permisionTAB").css("display","block");
						$("#bioTAB").css("display","none");
						$("#subGroupsTAB").css("display","none");
						$("#projectInsideTAB").css("display","none");

						$("#permisionTABTAB").addClass("active");
						$("#bioTABTAB").removeClass("active");
						$("#subGroupsTABTAB").removeClass("active");
						$("#projectInsideTABTAB").removeClass("active");

						
					break;
					case "subGroupsTAB":
						
						$scope.makeSubGroupsTAB();

						$("#subGroupsTAB").css("display","block");
						$("#bioTAB").css("display","none");
						$("#permisionTAB").css("display","none");
						$("#projectInsideTAB").css("display","none");

						$("#subGroupsTABTAB").addClass("active");
						$("#bioTABTAB").removeClass("active");
						$("#permisionTABTAB").removeClass("active");
						$("#projectInsideTABTAB").removeClass("active");

						
					break;
					case "projectInsideTAB":

						$scope.makeprojectInsideTAB();

						$("#subGroupsTAB").css("display","none");
						$("#bioTAB").css("display","none");
						$("#permisionTAB").css("display","none");
						$("#projectInsideTAB").css("display","block");
						
						$("#subGroupsTABTAB").removeClass("active");
						$("#bioTABTAB").removeClass("active");
						$("#permisionTABTAB").removeClass("active");
						$("#projectInsideTABTAB").addClass("active");
					break;
				};
			};
			//-----------------------bioTAB-----------------------------
			$scope.makeBioTAB=function(){
				//take members of the group
				uniapi.getAllGroupMembers($scope.ngGroup).then(function(data){
					$scope.members=data.users;
					for(var i=0;i<$scope.members.length;i++){
						$scope.members[i].since=new Date(data.users[i].since).toLocaleDateString();
					}
					
				});
			};
			$scope.makeClickDeleteMember=function(user){
				uniapi.removeMemberOfTheGroup($scope.ngGroup,user).then(function(response){
					console.log(response);
					if(response.state == 0){
						alert("Se ha realizado la eliminación");
						
						$scope.makeBioTAB();
					}else{
						alert("No se ha realizado la eliminación");
					}
				});
			};
			$scope.interactAddMember=function(){
				if($("#addMember").css("display") == "none"){
					$("#addMember").css("display","block");
					return;
				}

				if($("#addMember").css("display") == "block"){
					$("#addMember").css("display","none");
					return;
				}
			};
			$scope.addNewMember=function(){
				uniapi.inviteToGroup($scope.ngGroup,$scope.userToMakeMember).then(function(response){
					if(response.state == 0){
						alert("Se ha realizado la añadición");
						$scope.makeBioTAB();
						$("#addMember").css("display","none");
						$scope.userToMakeMember="";
					}else{
						alert("No se ha realizado la añadición");
						$scope.userToMakeMember="";
					}
				});
			};
			//--------------------SubGroupsTAB----------------------
			$scope.makeSubGroupsTAB=function(){

			};
			//---------------------------projectsInsideTAB--------------
			$scope.makeprojectInsideTAB=function(){

			};
			//-----------------------Permision TAB------------------------
			$scope.makePermisionTAB=function(){

				var check=$scope.sharingGroupPermissions[0]
				var checkBox="sharingGroupPermissions";
				$scope.modifyCheckBox(check,checkBox);
				
				var check=$scope.projectPropertiesPermissions[0]
				var checkBox="projectPropertiesPermissions";
				$scope.modifyCheckBox(check,checkBox);
			
				var check=$scope.memberGestionPermissions[0]
				var checkBox="memberGestionPermissions";
				$scope.modifyCheckBox(check,checkBox);
		
				var check=$scope.groupCreationPermissions[0]
				var checkBox="groupCreationPermissions";
				$scope.modifyCheckBox(check,checkBox);
			};
			$scope.modifyCheckBox=function(check,checkBox){
				console.log(check);
				console.log(checkBox);
				if(check){
					for(var i=1;i<=3;i++){
						$("#"+checkBox+i).removeAttr("disabled");
					}
				}else{
					for(var i=1;i<=3;i++){
						$("#"+checkBox+i).attr("disabled","disabled");
					}
				}

			};
			$scope.parseVector= function (vector){
				var response=[];
				for(var i=0;i<vector.length;i++){
					if(vector[i] == "YES"){
						response.push(true);
					}
					if(vector[i] == "NO"){
						response.push(false);
					}
				}
				return response;
			};
			$scope.convertVector= function (vector){
				var response=[];
				for(var i=0;i<vector.length;i++){
					if(vector[i]){
						response.push("YES");
					}else{
						response.push("NO");
					}
				}
				return response;
			};
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
			//----------------------------------------------------------------------
   		 }],
		link: function(scope, iElement, iAttrs, ctrl) {
     			 scope.id=iAttrs.ngGroup;
			
   		 }
	}
	
	
});
