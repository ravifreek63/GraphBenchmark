	
public class StatsPrinter implements Runnable {
		private  boolean[] _threadsDone;
		private  int _numberThreads;
		
		public StatsPrinter(int numberThreads){
			_numberThreads = numberThreads;
			_threadsDone = new boolean[numberThreads];
			_threadsDone[0] = true;
		}
		
		public  void threadDone(int index){
			_threadsDone[index] = true;
		}
		
		private int threadsStopped(){
			int sum=0;
			for (int index = 0; index<_numberThreads; index++){
				if(_threadsDone[index])
					sum = sum + 1;
			}
			return sum;
		}
		
		private  boolean shouldStop(){
			return(threadsStopped() == _numberThreads);
		}
		
	    public  void run() {
	    	int counter = 0;
	    	double rate;
	        try {
				while(shouldStop() == false){					
					Thread.sleep(1000);
					counter++;
					rate = (double)Statistics.totalEdgesTraversed()/counter; 
				System.out.println("Queries Done:" + Statistics.totalQueriesExecuted()
						+ ", EdgesTraversed: " + Statistics.totalEdgesTraversed() + ", rate:" + rate);
				}
				System.out.println("Queries Done:" + Statistics.totalQueriesExecuted()
						+ ", EdgesTraversed: " + Statistics.totalEdgesTraversed());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}