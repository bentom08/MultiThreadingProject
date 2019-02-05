import java.util.ArrayList;
import java.util.List;

public class Matrix {

	private int[][] matrix;
	
	private int[][] threadedNewMatrix;
	
	public Matrix(int[][] matrix) {
		this.matrix = matrix;
	}
	
	public int[][] getMatrix() {
		return matrix;
	}
	
	public void multiply(int[][] multMatrix) {
		if (matrix[0].length != multMatrix.length) {
			System.out.println("Matrix mismatch. Cannot multiply");
			return;
		}
		
		int[][] newMatrix = new int[matrix.length][multMatrix[0].length];
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < multMatrix[0].length; j++) {
				
				int sum = 0;
				
				for (int row = 0; row < matrix[0].length; row++) {
					sum += matrix[i][row] * multMatrix[row][j];
				}
				
				newMatrix[i][j] = sum;
			}
		}
		
		matrix = newMatrix;
	}
	
	public void multiThreadedMultiply(int[][] multMatrix) {
		if (matrix[0].length != multMatrix.length) {
			System.out.println("Matrix mismatch. Cannot multiply");
			return;
		}
		
		threadedNewMatrix = new int[matrix.length][multMatrix[0].length];
		
		class Threaded implements Runnable {

			int i;
			
			public Threaded(int i) {
				this.i = i;
			}
			
			public void run() {
				for (int j = 0; j < multMatrix[0].length; j++) {
				
					int sum = 0;
				
					for (int row = 0; row < matrix[0].length; row++) {
						sum += matrix[i][row] * multMatrix[row][j];
					}
				
					threadedNewMatrix[i][j] = sum;
				}
			}
		}
		
		List<Thread> threads = new ArrayList<>();
		
		for (int i = 0; i < matrix.length; i++) {
				Thread myThread = new Thread(new Threaded(i));
				threads.add(myThread);
				myThread.start();
		}
		
		for (Thread t: threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		matrix = threadedNewMatrix;
	}
	
	public void limitedThreadedMultiply(int[][] multMatrix) {
		if (matrix[0].length != multMatrix.length) {
			System.out.println("Matrix mismatch. Cannot multiply");
			return;
		}
		
		int cores = Runtime.getRuntime().availableProcessors();
		
		threadedNewMatrix = new int[matrix.length][multMatrix[0].length];
		
		class Threaded implements Runnable {

			int no;
			
			public Threaded(int no) {
				this.no = no;
			}
			
			public void run() {
				
				for (int i = no * matrix.length/cores; i < (no+1) * matrix.length/cores; i++) {
					for (int j = 0; j < multMatrix[0].length; j++) {
				
						int sum = 0;
				
						for (int row = 0; row < matrix[0].length; row++) {
							sum += matrix[i][row] * multMatrix[row][j];
						}
				
						threadedNewMatrix[i][j] = sum;
					}
				}
			}
		}
		
		List<Thread> threads = new ArrayList<>();
		
		for (int i = 0; i < cores; i++) {
			Thread myThread = new Thread(new Threaded(i));
			threads.add(myThread);
			myThread.start();
		}
		
		for (Thread t: threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		matrix = threadedNewMatrix;
	}
}
