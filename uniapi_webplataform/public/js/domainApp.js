	function person(id,name,subname,birthday,country,province,
	birthplace,biografy,profileImageUrl){
		this.id=id
		this.name=name;
		this.subname=subname;
		this.birthday=birthday;
		this.country=country;
		this.province=province;
		this.birthplace=birthplace;
		this.biografy=biografy;
		this.profileImageUrl=profileImageUrl;
	};
	
	function userSystem(id,email,password,creationDate){
		this.id=id;
		this.email=email;
		this.password=password;
		this.creationDate=creationDate;
	};
	
	function project(hashcode,name,type,description,gitRepositoryURL,
			email,password,creationDate,modifyDate,mainName,responseName,
			defaultInputs,inputDescription,outputDescription){
			
		this.hashcode=hashcode;
		this.name=name;
		this.type=type;
		this.description=description;
		this.gitRepositoryURL=gitRepositoryURL;
		this.email=email;
		this.password=password;
		this.creationDate=creationDate;
		this.modifyDate=modifyDate;
		this.mainName=mainName;
		this.responseName=responseName;
		this.defaultInputs=defaultInputs;
		this.inputDescription=inputDescription;
		this.outputDescription=outputDescription;

	}
