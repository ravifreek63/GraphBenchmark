	
public class StatsPrinter implements Runnable {
		private  boolean[] _threadsDone;
		private  int _numberThreads;
		private long _startTime;
		private int _totalTime;
		
		
		public StatsPrinter(int numberThreads, int time){
			_numberThreads = numberThreads;
			_threadsDone = new boolean[numberThreads];
			_threadsDone[0] = true;
			_totalTime = time;
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
	    	_startTime = System.nanoTime();
	    	double timeDifference;
	    	double rate;
	        try {
				while(shouldStop() == false){
					long currentTime = System.nanoTime();
					timeDifference = (((double)(currentTime - _startTime))/(Math.pow(10, 9)));
					Thread.sleep(1000);
					rate = (double)Statistics.totalEdgesTraversed()/timeDifference; 
				System.out.println("Queries Done:" + Statistics.totalQueriesExecuted()
						+ ", EdgesTraversed: " + Statistics.totalEdgesTraversed() + ", rate:" + rate + " time:" +timeDifference);
				if(timeDifference>_totalTime || Statistics.totalEdgesTraversed() > Benchmark._numberEdgesTraversed)
					System.exit(-1);
				}
				System.out.println("Queries Done:" + Statistics.totalQueriesExecuted()
						+ ", EdgesTraversed: " + Statistics.totalEdgesTraversed());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}