package test;

import java.util.Date;

public class TestDate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Date date1=new Date();
		System.out.println(date1.getTime());
		System.out.println("--------------------------");
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date2=new Date();
		System.out.println(date2.getTime());
		System.out.println("--------------------------");
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date3=new Date();
		System.out.println(date3.getTime());
		System.out.println("--------------------------");
	}

}
