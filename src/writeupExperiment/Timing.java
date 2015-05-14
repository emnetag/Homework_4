package writeupExperiment;


public class Timing {
	
	private static final int NUM_TESTS = 9;
	
	private static final int NUM_WARMUP = 3;
	
	private static final String TEXT = "sixthousand.txt";

	public static void main(String[] args) {
		
		String[] open = new String[]{"-o", TEXT};
		double openRunTime = getAverageRuntime(open);
		System.out.println("Average Run Time:\t" + openRunTime);

//		String[] sep = new String[]{"-s", TEXT};	
//		double sepRunTime = getAverageRuntime(sep);
//		System.out.println("Average Run Time:\t" + sepRunTime);
	}
	
	private static double getAverageRuntime(String[] args) {
		double totalTime = 0.0;
		
		for (int i = 0; i < NUM_TESTS; i++) {
			long startTime = System.currentTimeMillis();
			WordCount.main(args);
			long endTime = System.currentTimeMillis();
			if (NUM_WARMUP <= i) {
				totalTime += (endTime - startTime);
			}
		}
		return totalTime / (NUM_TESTS - NUM_WARMUP);
	}
}
