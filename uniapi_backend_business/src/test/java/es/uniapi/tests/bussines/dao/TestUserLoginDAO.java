package es.uniapi.tests.bussines.dao;

import java.util.Date;
import java.util.Scanner;

import es.uniapi.modules.business.dao.neo4j.entities.UserLoginNeo4j;
import es.uniapi.modules.model.UserLogin;
import es.uniapi.modules.model.config.SHA1;

public class TestUserLoginDAO {

	//CREATE CONSTRAINT ON ( userlogin:UserLogin ) ASSERT userlogin.user IS UNIQUE
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean loop = true;
		int option;
		try {
			while (loop) {
				option = paintMenu();
				switch (option) {
				case 1:
					newUser();
					break;
				case 2:
					searchForEmail();
					break;
				case 3:
					searchForID();
					break;
				case 4:
					searchAllUsers();
					break;
				case 5:
					deleteUser();
					break;
				default:
					loop = false;
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private static void deleteUser() throws Exception {
		// TODO Auto-generated method stub
		System.out.flush();
		Scanner teclado=new Scanner(System.in);
		System.out.println("Vamos a buscar un usuario por su email");
		System.out.println("Cual es su email:");
		String email=teclado.next();
		UserLoginNeo4j dao=new UserLoginNeo4j();
		UserLogin user=dao.findByEmail(email);
		dao.delete(user);
	}
	private static void searchAllUsers() throws Exception {
		// TODO Auto-generated method stub
		System.out.flush();
		System.out.println("Vamos a mostrar todos los usuarios");
		UserLoginNeo4j dao=new UserLoginNeo4j();
		UserLogin[] users=dao.findAll();
		for(int i=0;i<users.length;i++){
			System.out.println(""+i+") "+users[i].toString());
		}
	}
	private static void searchForID() throws Exception {
		// TODO Auto-generated method stub
		System.out.flush();
		Scanner teclado=new Scanner(System.in);
		System.out.println("Vamos a buscar un usuario por su id");
		System.out.println("Cual es su id:");
		long id=teclado.nextLong();
		UserLoginNeo4j dao=new UserLoginNeo4j();
		UserLogin user=dao.findByID(id);
		user.toString();
	}
	private static void searchForEmail() throws Exception {
		// TODO Auto-generated method stub
		System.out.flush();
		Scanner teclado=new Scanner(System.in);
		System.out.println("Vamos a buscar un usuario por su email");
		System.out.println("Cual es su email:");
		String email=teclado.next();
		UserLoginNeo4j dao=new UserLoginNeo4j();
		UserLogin user=dao.findByEmail(email);
		user.toString();
	}
	private static void newUser() throws Exception {
		// TODO Auto-generated method stub
		Scanner teclado=new Scanner(System.in);
		System.out.flush();
		System.out.println("Vamos a crear un nuevo usuario:");
		System.out.println("Cual es su email:");
		String user=teclado.next();
		teclado=new Scanner(System.in);
		System.out.println("Cual es su password:");
		String pass=teclado.next();
		teclado=new Scanner(System.in);
		pass=SHA1.encryptPassword(pass);
		
		UserLogin userLogin=new UserLogin(user, pass,new Date());
		UserLoginNeo4j dao=new UserLoginNeo4j();
		dao.create(userLogin);
		
	}
	public static int paintMenu(){
		System.out.flush();
		System.out.println("TestUserLoginDAO menu:");
		System.out.println("------------------------------------");
		System.out.println("1) Nuevo usuario");
		System.out.println("2) Buscar usuario por email");
		System.out.println("3) Buscar usuario por id");
		System.out.println("4) Buscar todos los usuarios");
		System.out.println("5) Eliminar usuario");
		System.out.println("0) Salir de aquí!");
		Scanner teclado=new Scanner(System.in);
		return teclado.nextInt();
		
	}

}
