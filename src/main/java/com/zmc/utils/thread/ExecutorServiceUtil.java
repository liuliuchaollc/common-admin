package com.zmc.utils.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceUtil {

	public static ExecutorService executorService = Executors.newCachedThreadPool(); 
	
	class GenerateIndexTask implements Runnable{

		@Override
		public void run() {

			
			
		}
	}
	
	public static void dealIndex(Runnable r){
		
		executorService.submit(r);
		
	}
	
}
