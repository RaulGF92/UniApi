var person=function Person(name,subname,birthday,country,province,
	birthplace,biografy,profileImageUrl){
		this.name=name;
		this.subname=subname;
		this.birthday=birthday;
		this.country=country;
		this.province=province;
		this.birthplace=birthplace;
		this.biografy=biografy;
		this.profileImageUrl=profileImageUrl;
	};
	
var userSystem=function UserSystem(email,password,rol,creationDate){
		this.email=email;
		this.password=password;
		this.rol=rol;
		this.creationDate=creationDate;
	};

module.exports.person=person;
module.exports.userSystem=userSystem;
