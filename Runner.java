import java.util.Arrays;

public class Runner {

	public static void main(String[] args) {
		int[][] myArray = new int[1000][1000];
		for (int i = 0; i < myArray.length; i++) {
			for (int j = 0; j < myArray[0].length; j++) {
				myArray[i][j] = i-j
;			}
		}
		
		int[][] anotherArray = new int[1000][1000];
		for (int i = 0; i < anotherArray.length; i++) {
			for (int j = 0; j < anotherArray[0].length; j++) {
				anotherArray[i][j] = i+j;
;			}
		}
		
		Matrix myMatrix = new Matrix(myArray);
		
		long start = System.currentTimeMillis();
		
		myMatrix.limitedThreadedMultiply(anotherArray);;
		
		long stop = System.currentTimeMillis();
		
		System.out.println(stop - start);
		
		start = System.currentTimeMillis();
		
		myMatrix.multiply(anotherArray);;
		
		stop = System.currentTimeMillis();
		
		System.out.println(stop - start);
	}
}
