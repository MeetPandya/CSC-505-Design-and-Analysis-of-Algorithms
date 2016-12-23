/*
 * Author : Mitkumar Pandya
 * CSC-505 Fall 2016
 * Department of Computer Science
 * 	NC State
 */
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class maxflow {
	//This is a static map which will store the BFS travelled augmenting path
	static Map<Integer, Integer> pathMap = new HashMap<Integer, Integer>();
	static final int SOURCE = 0;
	static final int SINK = 1;
	public static void main(String[] args) {
		try{
			//Scan through the input file
			Scanner sc = new Scanner(System.in);
			String s = sc.nextLine();
			String s1[] = s.split(" ");
			int a = Integer.parseInt(s1[0]);
			int b = Integer.parseInt(s1[1]);
			//Create an empty graph object
			Graph1 g = new Graph1(a);
			//keep track of input sequence
			List<Integer> inputSeq = new LinkedList<Integer>();
			for (int i = 0; i < b; i++) {
				//Get the input line as String
				String input = sc.nextLine();
				//Split the string to parse three integer values
				//The values are node1, node2, the weight of the edge between node1 & node2 respectively
				String s2[] = input.split(" ");
				int node1 = Integer.parseInt(s2[0]);
				int node2 = Integer.parseInt(s2[1]);
				int weight = Integer.parseInt(s2[2]);
				//If the node is not already present in the graph, dd it
				if(!g.hasNode(node1))
					g.addNode(node1);
				if(!g.hasNode(node2))
					g.addNode(node2);
				//Add the edge between the nodes
				g.addEdge(node1, node2, weight);
				//Keep track of input sequence
				inputSeq.add(node1);
				inputSeq.add(node2);
			}
			int maxFlow = 0;
			//Iterate till there exist an augmenting path
			while(bfs(g, SOURCE, SINK)){
				//Get the adjacency matrix of the graph
				int[][] adjMatrix = g.adjMatrix;
				//Get the path found using BFS from Source to Sink
				LinkedList<Integer> path = getPath();
				//Calculate the max flow of the augmenting path
				int currentMaxFlow = getMaxFlow(adjMatrix, path);
				//Add this maxflow to overall maxflow of the graph
				maxFlow = maxFlow + currentMaxFlow;
				//System.out.println(path.toString()+" "+currentMaxFlow);
				//Compute the new adjacency matrix by reducing the flow of previously found augmenting path
				g.adjMatrix = getNewAdjMatrix(adjMatrix, path, currentMaxFlow);
			}
			System.out.println(maxFlow);
			//Print the flow in the order of input sequence
			for (int i = 0; i < inputSeq.size()-1; i = i+2) {
				int e1 = inputSeq.get(i);
				int e2 = inputSeq.get(i+1);
				System.out.println(e1+" "+e2+" "+g.adjMatrix[e2][e1]);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//This method calculates and returns the max flow of the input augmenting path
	public static int getMaxFlow(int[][] adjMatrix, LinkedList<Integer> path){
		int maxFlow = Integer.MAX_VALUE; //let the max flow infinite
		int size = (path.size()-1);
		for (int i = 0; i < size; i++) {
			//Find the minimum of the flow of the edges in this path
			if(adjMatrix[path.get(i)][path.get(i+1)] < maxFlow)
				maxFlow = adjMatrix[path.get(i)][path.get(i+1)]; 
		}
		return maxFlow;
	}
	//Method to reduce the flow of the current path in adjacency matrix also called as calculate the residual capacity of each edges of the path
	public static int[][] getNewAdjMatrix(int[][] adjMatrix, LinkedList<Integer> path, int maxFlow){
		int size = path.size()-1;
		for (int i = 0; i < size; i++) {
			//Reduce the flow from the actual path
			adjMatrix[path.get(i)][path.get(i+1)] = adjMatrix[path.get(i)][path.get(i+1)] - maxFlow;
			//Add a reverse edge with same weight in opposite direction
			adjMatrix[path.get(i+1)][path.get(i)] = adjMatrix[path.get(i+1)][path.get(i)] + maxFlow;
		}
		return adjMatrix;
	}
	//Method to find the augmenting path from the Graph using Breadth First Search
	public static boolean bfs(Graph1 g, int source, int sink){
		//Remove the previous path from the path map
		pathMap.clear();
		//get the graph's adjacency matrix
		int[][] adjMatrix = g.adjMatrix;
		//Create  list to keep track of visited nodes
		List<Integer> visited = new ArrayList<Integer>();
		//Create an empty Queue object to iterate through the graph in order
		Queue q = new Queue();
		//Start with the source
		q.add(source);
		//Mark source as visited
		visited.add(source);
		//While Queue is not empty
		while(!q.isEmpty()){
			//Get the front value from the queue
			int current = q.remove();
			//Mark it visited
			visited.add(current);
			//Iterate through the current node's adjacency list
			for (int i = 0; i < adjMatrix[current].length; i++) {
				//If there is an edge from current to other node and that other node is not already visited
				if(adjMatrix[current][i]>0 && !visited.contains(i)){
					int next = i;
					//Add it to the queue
					q.add(next);
					//Add the node to path map with it's parent node
					pathMap.put(next,current);
					//If the found node is destination i.e. sink then break the loop and return true
					if(next==sink)
						return true;
				}
			}
		}
		//return false if no such path was found
		return false;
	}
	//This method returns the augmenting path found using BFS from the pathmap
	//Start from the destination and recursively find the path to the source 
	public static LinkedList<Integer> getPath(){
		//Create an empty stack object
		Stack stack = new Stack();
		//Sink is always 1
		int current = SINK;
		while(true){
			int i = current;
			stack.push(i);
			if(i==0)
				break;
			//Get the parent of current node
			current = pathMap.get(i);
		}
		//Store the path value in a LinkedList
		LinkedList<Integer> path = new LinkedList<Integer>();
		while(!stack.isEmpty()){
			path.add(stack.pop());
		}
		return path;
	}

}
//Graph object which keeps track of nodes and edges
class Graph1{
	List<Integer> nodes;
	int[][] adjMatrix;
	
	public Graph1(int size){
		nodes = new ArrayList<Integer>();
		adjMatrix = new int[size][size];
	}
	public void addNode(int key){
		nodes.add(key);
	}
	public void addEdge(int a, int b, int weight){
		adjMatrix[a][b] = weight;
	}
	public boolean hasNode(int key){
		return nodes.contains(key);
	}
}
//Queue implementation using LinkedList
class Queue{
	LinkedList<Integer> list;
	public Queue(){
		list = new LinkedList<Integer>();
	}
	public void add(int key){
		list.add(key);
	}
	public int remove(){
		return list.remove(0);
	}
	public boolean isEmpty(){
		return list.size()==0;
	}
}
//Stack implementation using LinkedList
class Stack{
	LinkedList<Integer> list;
	public Stack(){
		list = new LinkedList<Integer>();
	}
	public void push(int key){
		list.add(key);
	}
	public int pop(){
		return list.remove(list.size()-1);
	}
	public boolean isEmpty(){
		return list.size()==0;
	}
}
