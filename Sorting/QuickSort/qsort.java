/*
 * Author : Mitkumar Pandya
 * Unity ID : mhpandya
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class qsort {
	
	public static void main(String[] args) {
		try{
			int cutoff = 0;
			if(args[0]!= null){
				cutoff = Integer.parseInt(args[0]);
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			List<Integer> list = new ArrayList<Integer>();
			String nextLine;
			while((nextLine = in.readLine()) != null){
				list.add(Integer.parseInt(nextLine));
			}
			int[] a = new int[list.size()];
			Iterator<Integer> itr = list.iterator();
			int c = 0;
			while(itr.hasNext()){
				a[c++] = itr.next();
			}
			long t0 = getMilliseconds();
			quickSort(a, 0, a.length-1,cutoff);
			long t1 = getMilliseconds();

			System.err.println("Time taken : "+ (t1 - t0) );
		    	for (int i = 0; i < a.length; i++) {
				System.out.println(a[i]);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void quickSort(int[] a, int low, int high, int cutoff){
		int pivot = 0;
		if(low < high){
			
			if(high-low < cutoff){
				insertionSort(a, low, high+1);
			}
			else{
				pivot = partition(a, low, high);
				quickSort(a, low, pivot-1,cutoff);
				quickSort(a, pivot+1, high,cutoff);
			}
		}
	}
	public static int partition(int[]a ,int low, int high){

		//Generating randomized pivot from the list
		/*Random random = new Random();
		int randomPivot = low + random.nextInt(high-low+1);
		//swap pivot to start index as described in Ho-are partition pseudo code
		swap(a,randomPivot, low);*/
		int pivot = a[high];
		
		int start = low-1;
		//Loop until list is completely partitioned
		for (int i = low; i <= high-1; i++) {
			if(a[i] <= pivot){
				start++;
				swap(a, start, i);
			}
		}
		swap(a,start+1,high);
		return start+1;
	}
	
	public static int hoarePartition(int[] a, int low, int high){
		//Generating randomized pivot from the list
		/*Random random = new Random();
		int randomPivot = low + random.nextInt(high-low+1);
		//swap pivot to start index as described in Ho-are partition pseudo code
		swap(a,randomPivot, low);*/
		int pivot = a[low];
		
		int start = low-1;
		int end = high+1;
		//Loop until list is completely partitioned
		while(true){
			do {
				end--;
			}
			while(a[end] > pivot);//check for lesser value from the end
			do{
				start++;
			}
			while(a[start] < pivot);//check for higher value from the start
			//if found swap the two 
			if(start < end)
				swap(a,start,end);
			else{
				return end;
			}
		} 
	}
	public static void insertionSort(int[] a, int s, int e){
		for (int i = s+1; i < e; i++) {
			int key = a[i];
			int j = i-1;
			while(j >= 0 && a[j] > key){
				a[j+1] = a[j];
				j--;
			}
			a[j + 1] = key;
		}
		
	}
	public static void swap(int[] a, int l, int r){
		int temp = a[r];
		a[r] = a[l];
		a[l] = temp;
	}
	
	public static void display(int[] a){
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i]+" ");
		}
		System.out.println();
	}
	public static int randomGenerator(int range){
		// random number generator for the given range
		Random random = new Random();
		return random.nextInt(range);
	}

	public static long getMilliseconds() {
		return System.currentTimeMillis();
	}

}
