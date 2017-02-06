package es.uniapi.modules.sessiongestion.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

import org.joda.time.DateTime;

public class Limpito extends Thread {

	HashMap<String, String[]> sessions;
	private long TIME_LEAST_TO_DELETE;
	Semaphore semaphore;

	public Limpito(HashMap<String, String[]> sessions2, long time_least_to_delete, Semaphore semaphore) {
		// TODO Auto-generated constructor stub
		this.sessions = sessions2;
		this.TIME_LEAST_TO_DELETE = time_least_to_delete;
		this.semaphore=semaphore;
	}

	public void run() {
		
		esperarPorFatherThread();
		
		long timeSleep = TIME_LEAST_TO_DELETE;
		int counter=0;
		while (true) {
			ArrayList<String> tokenForDelete=new ArrayList<String>();
			try {
				if (sessions.size() > 0) {
					timeSleep=TIME_LEAST_TO_DELETE;
					String[] container;
					String token;
					semaphore.acquire();
					Iterator<String> it=sessions.keySet().iterator();
					while(it.hasNext()){
						token=it.next();
						container=sessions.get(token);
						if(checkTimeToErase(new DateTime(Long.parseLong(container[1])).toDate())){
							tokenForDelete.add(token);
							counter=0;
						}
					}
					for(int i=0;i<tokenForDelete.size();i++){
						sessions.remove(tokenForDelete.get(i));
						System.out.println("Limpito ha realizado una limpieza/n Clientes restantes:"+sessions.size());
					}
					semaphore.release();
				}
				Thread.sleep(timeSleep*counter);
				
				if(counter<4)
					counter++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void esperarPorFatherThread() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public boolean checkTimeToErase(Date date) {

		long diferentTime = new Date().getTime() - date.getTime();
		if (diferentTime > (TIME_LEAST_TO_DELETE+(TIME_LEAST_TO_DELETE/2)))
			return true;
		return false;
	}

}
