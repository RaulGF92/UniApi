package es.uniapi.modules.sessiongestion.impl;

import java.io.DataOutput;
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
			try {
				if (sessions.size() > 0) {
					counter=0;
					timeSleep=TIME_LEAST_TO_DELETE;
					
					Iterator<String> it=sessions.keySet().iterator();
					String[] container;
					String token;
					while(it.hasNext()){
						semaphore.acquire();
						token=it.next();
						container=sessions.get(token);
						if(checkTimeToErase(new DateTime(container[1]).toDate()))
							sessions.remove(token);
						semaphore.release();
					}
				}
				
				Thread.sleep(timeSleep);
				
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
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public boolean checkTimeToErase(Date date) {

		long diferentTime = new Date().getTime() - date.getTime();
		if (diferentTime < (TIME_LEAST_TO_DELETE+(TIME_LEAST_TO_DELETE/2)))
			return true;
		return false;
	}

}
