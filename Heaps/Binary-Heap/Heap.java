package com.meet.semester1.Practice.Heaps;

public class Heap {
	public Node[] heapArray;
	public int maxSize;
	public int currentIndex = 0;
	public Heap(int size){
		heapArray = new Node[size];
		maxSize = size;
	}
	
	public void insert(int data){
		if(currentIndex == maxSize)
			return;
		heapArray[currentIndex] = new Node(data);
		percolateUp(currentIndex++);
	}
	
	public Node remove(){
		Node top = heapArray[0];
		heapArray[0] = heapArray[--currentIndex];
		percolateDown(0);
		return top;
	}
	
	public void percolateUp(int index){
		int parent = (index-1)/2;
		Node current = heapArray[index];
		while(index > 0 && heapArray[parent].getData() < current.getData()){
			heapArray[index] = heapArray[parent];
			index = parent;
			parent = (parent-1)/2;
		}
		heapArray[index] = current;
	}
	
	public void percolateDown(int index){
		Node current = heapArray[index];
		int large;
		while(index < currentIndex/2){
			int left = 2*index + 1;
			int right = left + 1;
			if(right < currentIndex && heapArray[left].getData() < 
										heapArray[right].getData())
				
				large = right;
			else
				large = left;
			if(current.getData() >= heapArray[large].getData())
				break;
			heapArray[index] = heapArray[large];
			index = large;
		}
		heapArray[index] = current;
	}
	
	public void display(){
		for (int i = 0; i < currentIndex; i++) {
			System.out.print(heapArray[i].getData()+" ");
		}
		System.out.println();
	}

}
class Node{
	private int data;
	
	public Node(int data){
		this.data = data;
	}

	public int getData() {
		return data;
	}
	
}
