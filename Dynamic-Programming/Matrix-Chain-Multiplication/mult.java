/*
 * Author : Mitkumar Pandya
 * Unity ID : mhpandya
 */
import java.io.FileReader;
import java.util.Scanner;

public class mult {
	
	public static void main(String[] args) {
		try{
			//Read input from standard input
			Scanner sc = new Scanner(System.in);
			int i = sc.nextInt();
			int[] p = new int[i+1];
			int j = 0;
			while(sc.hasNext()){
				//create an integer array from input
				p[j++] = sc.nextInt();
			}
			matrixChainOrder(p);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Bottom-Up Matrix Chain Multiplication algorithm
	//assuming the matrices are compliant
	//time complexity O(n^3)
	public static void matrixChainOrder(int[] p){
		int n = p.length - 1;
		int[][] m = new int[n][n];
		int[][] s = new int[n][n];
		
		//initially setting the multiplication cost to 0
		for (int i = 1; i < n; i++) {
			m[i][i] = 0;
		}
		//looping through the chain of the matrices
		for (int l = 1; l < n; l++) {
			//Start from first matrix
			for (int i = 0; i < n-l;i++){
				int j = i + l;
				//set multiplication cost to maximum value
				m[i][j] = Integer.MAX_VALUE;
				int q = 0;
				//check all the posibilities of multiplication cost from staring matrix to current matrix
				//let k be the intermediate matrix between start matrix i.e. i and current matrix i.e. j
				for (int k = i; k < j; k++) {
					//apply matrix chain multiplication formulae
					q = m[i][k] + m[k+1][j] + (p[i]*p[k+1]*p[j+1]);
					//Is the cost lower than previously calculated cost
					if(q < m[i][j]){
						//if yes replace it and put it in resulting table s
						m[i][j] = q;
						s[i][j] = k;
					}
				}
			}
		}
		//print the optimal multiplication path
		printMatrixMultOrder(s, 0, n-1);
		//remove white spaces if any
		out = out.trim();
		//remove unnecessary front and trailing paranthesis
		System.out.println(out.substring(2,out.length()-1));
	}
	public static String out = "";
	//space complexity : theta(n^2) time complexity : theta(n^2)
	public static void printMatrixMultOrder(int[][] s,int i,int j){
		//if fiund print M and matrix no i.e. i
		if(i==j){
			out = out +("M"+(i+1)+" ");
		}else{
			//loop through the result table s recursively
			out = out +("( ");
			printMatrixMultOrder(s,i,s[i][j]);
			//if lowest cost found between two matrices print *
			out = out +("* ");
			//print the second matrix
			printMatrixMultOrder(s,s[i][j]+1,j);
			out = out +(") ");
		}
	}
}
