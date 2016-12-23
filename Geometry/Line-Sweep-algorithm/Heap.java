/*
 * Author : Mitkumar Pandya
 * CSC-505 Fall 2016
 * Department of Computer Science
 * 	NC State
 */

public class Heap {
	public Point[] heapArray;
	public int maxSize;
	public int currentIndex = 0;
	public Heap(int size){
		heapArray = new Point[size];
		maxSize = size;
	}
	
	public void insert(Point p){
		if(currentIndex == maxSize)
			return;
		heapArray[currentIndex] = p;
		percolateUp(currentIndex++);
	}
	
	public Point remove(){
		Point top = heapArray[0];
		heapArray[0] = heapArray[--currentIndex];
		percolateDown(0);
		return top;
	}
	
	public void percolateUp(int index){
		int parent = (index-1)/2;
		Point current = heapArray[index];
		while(index > 0 && ((heapArray[parent].x > current.x) || (heapArray[parent].x == current.x && heapArray[parent].y > current.y))){
			heapArray[index] = heapArray[parent];
			index = parent;
			parent = (parent-1)/2;
		}
		heapArray[index] = current;
	}
	
	public void percolateDown(int index){
		Point current = heapArray[index];
		int large;
		while(index < currentIndex/2){
			int left = 2*index + 1;
			int right = left + 1;
			if(right < currentIndex && (heapArray[left].x > heapArray[right].x 
					|| (heapArray[left].x == heapArray[right].x && heapArray[left].y > heapArray[right].y)))
				large = right;
			else
				large = left;
			if(current.x < heapArray[large].x || (current.x == heapArray[large].x && current.y < heapArray[large].y))
				break;
			heapArray[index] = heapArray[large];
			index = large;
		}
		heapArray[index] = current;
	}

}
