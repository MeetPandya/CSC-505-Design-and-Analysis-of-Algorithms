/*
 * Author : Mitkumar Pandya
 * CSC-505 Fall 2016
 * Department of Computer Science
 * 	NC State
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

public class heap3{
	
	public List<Node> heapArray = new LinkedList<Node>();
	public int currentIndex = 0;
	
	
	public void insert(Node node){
		//insert into arraylist
		heapArray.add(node);
		//move the inserted value up
		percolateUp(currentIndex++);
	}
	
	public Node removeMin(){
		//remove root node which is minimum value in heap
		Node top = heapArray.get(0);
		if(currentIndex>1){
			heapArray.set(0, heapArray.remove(--currentIndex));
			percolateDown(0);
		}else
			heapArray.remove(--currentIndex);
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
	public int size(){
		return heapArray.size();
	}
	public boolean hasNext(){
		return heapArray.size() > 0;
	}
}
class Node{
	public int data;
	public String character;
	public Node(String character, int data){
		this.character = character;
		this.data = data;
	}
	public Node left;
	public Node mid;
	public Node right;
}
