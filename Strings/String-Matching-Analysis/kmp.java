/*
 * Author : Mitkumar Pandya
 * Unity ID : mhpandya
 */

import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.Scanner;

public class kmp {
	
	public static void main(String[] args) {
		try{
			//Initialize the string to be matched and pattern
			String h = null;
			String n = null;
			// If standard input is given
			if(args.length > 0){
				//Read the file
				Scanner sc = new Scanner(new FileReader(args[0]));
				while(sc.hasNext()){
					h = sc.nextLine();
					n = sc.nextLine();
				}
			}else{
				//Compute the randomly generated string and pattern
				String[] s = generateRandomString();
				h = s[0];
				n = s[1];
			}
			//Time taken by naive approach
			long t0 = getMilliseconds();
			System.out.println("found at: "+naive(h, n));
			long t1 = getMilliseconds();
			System.out.println("naive search time: "+ (t1 - t0) );
			//Time taken by java indexOf method
			long t2 = getMilliseconds();
			System.out.println("found at: "+standard(h, n));
			long t3 = getMilliseconds();
			System.out.println("standard search time: "+ (t3 - t2) );
			//Time taken by KMP algorithm
			long t4 = getMilliseconds();
			System.out.println("found at: "+kmp(h, n));
			long t5 = getMilliseconds();
			System.out.println("kmp search time: "+ (t5 - t4) );
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//Naive approach to search pattern in a string
	static int naive(String h, String n){
		//convert strings to character arrays for comparison
		char[] h1 = h.toCharArray();
		char[] n1 = n.toCharArray();
		int l2 = n.length();
		//Outer loop runs from first index of the string to be matched - pattern's length
		for (int i = 0; i < h1.length-l2+1; i++) {
			//j keeps track of characters matched
			int j = 0;
			//iterate through the pattern
			for (int k = 0; k < n1.length; k++) {
				//if string's current character and pattern's current character matches
				if(n1[k]==h1[i+k])
					//increment j
					j++;
			}
			//if j == pattern's length i.e. all the characters matched
			if(j == l2)
				//return the starting index from where all the characters matches 
				return i;
		}
		//return -1 if pattern not found
		return -1;
	}
	//Using java's inbuilt indexOf method to find pattern in a string
	static int standard(String h, String n){
		return h.indexOf(n);
	}
	//Implementation of knuth morris pratt algorithm to search for pattern in a string
	static int kmp(String h, String n){
		//convert both the strings to character array
		char[] h1 = h.toCharArray();
		char[] n1 = n.toCharArray();
		//compute the prefix values of the pattern to be matched
		int[] k = computePrefix(n);
		int q = 0; //No. of characters matched
		//iterate through the haystack string
		for (int i = 0; i < h1.length; i++) {
			//if next character does not match, report the no. of characters matched in the prefix array
			while(q >= 0 && n1[q]!=h1[i])
				q = k[q];
			//else increment q by 1
			q++;
			//if all the characters in the pattern matches return the start index of pattern in haystack string
			if (q == n1.length){
				//return the starting index of the string from where all the character matches
				q = k[q];
				return i - n1.length+1;
			}
		}
		return -1; 
	}
	
	static int[] computePrefix(String n){
		//convert the pattern string to array
		char[] n1 = n.toCharArray();
		int i = 0;
		//j keeps track of matched values
		int j = -1;
	    //create an array to store prefix values
	    int[] k = new int[n1.length + 1];
	    //starting with -1
	    k[i] = j;
	    //iterate through all the characters
	    while (i < n1.length) {            
	    	while (j >= 0 && n1[i] != n1[j]) {
	    		// if current character does not match and j > 0
	    		//Update j's value with last matched values
	    		j = k[j];
	    	}
	    	i++;
	    	j++;
	    	//Insert no. of matching values till current index in K
	    	k[i] = j;
	    }
	    //return computed array
	    return k;
	}
	//This functions generates random string
	static String[] generateRandomString(){
	        StringBuilder builder = new StringBuilder();
	        StringBuilder pattern = new StringBuilder();
	        // 999999 zeros followed by 1 
	        while(builder.length() < 1000000) {
	            builder.append("0");
	        }
	        builder.append("1");
	        // 499 zeros followed by 1
	        while(pattern.length() < 500){
	        	pattern.append("0");
	        }
	        pattern.append("1");
	        String[] s = new String[2];
	        s[0] = builder.toString();
	        s[1] = pattern.toString();
	        return s;
	}
	//method to get current time in milli seconds
	static long getMilliseconds() {
		return System.currentTimeMillis();
	}
}
