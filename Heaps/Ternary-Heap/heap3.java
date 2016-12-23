package com.meet.semester1.CSC505.assignment2;
/*
 * Author : Mitkumar Pandya
 * Unity ID : mhpandya
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class heap3 {
	
	public List<Node> heapArray = new LinkedList<Node>();
	public int currentIndex = 0;
	
	public static void main(String[] args) {
		try{
			//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			/*BufferedReader in = new BufferedReader(new FileReader("input.txt"));
			List<Integer> output = new ArrayList<Integer>(); 
			Pattern p1 = Pattern.compile("add");
			Pattern p2 = Pattern.compile("remove");
			heap3 th = new heap3();
			String nextLine;
		    while ((nextLine = in.readLine()) != null) {
				Matcher m1 = p1.matcher(nextLine);
				Matcher m2 = p2.matcher(nextLine);
				if(m1.find()){
					nextLine = nextLine.replace("add", "").trim();
					th.insert(Integer.parseInt(nextLine));
				}
				else if(m2.find()){
						Node node = th.removeMin();
						System.out.println(node.data);
						//output.add((node.data));
				}
			}
		    in.close();*/
			Scanner sc = new Scanner(System.in);
			Pattern p1 = Pattern.compile("add");
			Pattern p2 = Pattern.compile("remove");
			heap3 th = new heap3();
			String nextLine;
			while (sc.hasNext()) {
				nextLine = sc.nextLine();
				if(nextLine.contains("add")){
					nextLine = nextLine.replace("add", "").trim();
					th.insert(Integer.parseInt(nextLine));
				}
				else{
					Node node = th.removeMin();
					System.out.println(node.data);
				}
				/*Matcher m1 = p1.matcher(nextLine);
				Matcher m2 = p2.matcher(nextLine);
				if(m1.find()){
					nextLine = nextLine.replace("add", "").trim();
					th.insert(Integer.parseInt(nextLine));
				}
				else if(m2.find()){
						Node node = th.removeMin();
						System.out.println(node.data);
						//output.add((node.data));
				}*/
				
			}
		    /*BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
		    Iterator<Integer> itr = output.listIterator();
		    while (itr.hasNext()) {
		    	String s = itr.next().toString();
		    	out.write(s);
		    	out.flush();
			}
		    out.close();*/
		   
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insert(int data){
		//insert into arraylist
		heapArray.add(new Node(data));
		//move the inserted value up
		percolateUp(currentIndex++);
	}
	
	public Node removeMin(){
		//remove root node which is minimum value in heap
		Node top = heapArray.get(0);
		heapArray.set(0, heapArray.remove(--currentIndex));
		percolateDown(0);
		return top;
	}
	
	public void percolateUp(int index){
		//get parent index
		int parent = (index-1)/3;
		Node current = heapArray.get(index);
		//move current node up until its parent value is greater than its value
		while(index > 0 && heapArray.get(parent).data > current.data){
			//Collections.swap(heapArray, index, parent);
			//swap parent and current child
			heapArray.set(index, heapArray.get(parent));
			index = parent;
			//get new parent index
			parent = (parent-1)/3;
		}
		//set current value at last index
		heapArray.set(index, current);
	}
	
	public void percolateDown(int index){
		// get current index
		Node current = heapArray.get(index);
		int small;
		//loop until atleast one child is present
		while((3*index+1)<heapArray.size()){
			//get all the children index
			int left = 3*index + 1;
			int mid = left + 1;
			int right = mid + 1;
			//if right child exist and its value is less than both the other child then mark it as small
			if(right < currentIndex 
					&& heapArray.get(left).data > heapArray.get(right).data
					&& heapArray.get(mid).data > heapArray.get(right).data){
				small = right;
			}
			//if middle child exist and its value is less than left child then mark it as small
			else if(mid < currentIndex 
					&& heapArray.get(left).data > heapArray.get(mid).data){
				small = mid;
			}
			//else assume left child is smallest
			else{
				small = left;
			}
			//if parent is less than smallest child then exit
			if(current.data <= heapArray.get(small).data)
				break;
			//Collections.swap(heapArray, index, small);
			//else swap small value with parent
			heapArray.set(index, heapArray.get(small));
			index = small;
		}
		//set current value at last index
			heapArray.set(index,current);
	}
	public void display(){
		Iterator<Node> itr = heapArray.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next().data);
		}
		System.out.println();
	}
	
}
class Node {
	public int data;
	
	public Node(int data){
		this.data = data;
	}
}
