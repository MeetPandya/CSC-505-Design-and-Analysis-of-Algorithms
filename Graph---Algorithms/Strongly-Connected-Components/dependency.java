/*
 * Author : Mitkumar Pandya
 * CSC-505 Fall 2016
 * Department of Computer Science
 * 	NC State
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class dependency{
	
	public static void main(String[] args) {
		try{
			//read input from scanner
			Scanner sc = new Scanner(System.in);
			int i = sc.nextInt();
			//Create a new graph object
			Graph g = new Graph(i);
			for (int j = 0; j < i; j++) {
				//iterate through the input and initialize node object
				Node node = new Node(sc.next(), j);
				node.color = "white";
				node.predecessor = null;
				//add node to the graph
				g.addNode(node);
			}
			int k = sc.nextInt();
			while(sc.hasNext()) {
				String[] s = sc.nextLine().split(" ");
				for(int l=0; l < s.length-1 ; l++) {
					//iterate through the adjacency list and create edges in graph
					Node node1 = g.getNode(s[l]);
					Node node2 = g.getNode(s[l+1]);
					g.addEdge(node1, node2);
				}
			}
			//Perform first DFS on the graph starting from any edge
			DFS(g);
			//Sort the graph nodes according to their finish time
			sort(g);
			//Reverse the edges of the graph
			g = reverseGraph(g);
			//Re perform DFS on the graph but this time in decreasing order of finish time
			DFST(g);
			//Create a Treemap which will sort the SCC based on insertion order
			Map<Integer, List<String>> map = new TreeMap<Integer,List<String>>();
			map.putAll(scc);
			//Iterate through the SCC and print the values
			Iterator<Integer> itr = map.keySet().iterator();
 			while(itr.hasNext()){
				Integer a = itr.next();
				System.out.println(scc.get(a).toString().replace("[", "").replace("]", "").replace(",", "").trim());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void DFS(Graph g){
		//DFS visit the graph
		boolean[][] adjMatrix = g.adjMatrix;
		//Iterate through the adjacency matrix
		for (int i = 0; i < adjMatrix.length; i++) {
			Node node = g.getNode(i);
			//If node is unvisited visit the node
			if(node.color.equals("white"))
				visit(node,g);
		}
	}
	
	static int time = 0;
	public static void visit(Node node, Graph g){
		//increment the time and mark the node visited but unfinished at this stage
		time = time+1;
		node.color = "gray";
		node.startTime = time;
		//Iterate through the adjacents of the current node
		for (int i = 0; i < g.adjMatrix[node.key].length; i++) {
			//Check if an edge exists between the nodes
			if(g.adjMatrix[node.key][i]==true){
				//If edge found, get that node set it's predecessor as current node and visit it
				Node node1 = g.getNode(i);
				if(node1.color.equals("white")){
					node1.predecessor = node;
					visit(node1,g);
				}
			}
		}
		//At this time all the adjacents of current nodes are explored hence mark the node completed
		node.color = "black";
		//Mark the node's finish time
		time = time+1;
		node.endTime = time;
	}
	public static Map<Integer, List<String>> scc = new HashMap<Integer, List<String>>();
	public static List<Node> sortedList = new ArrayList<Node>();
	
	public static void DFST(Graph g){
		//Performs the DFS on a graph
		Iterator<Node> itr = sortedList.iterator();
		while(itr.hasNext()){
			Node node = itr.next();
			List<String> list = new ArrayList<String>();
			if(node.color.equals("white")){
				visitT(node, g, list);
				list.add(node.name);
				if(list.size()>1){
					//get the dependency list and sort it in the input order
					for (int j = 1; j < list.size(); j++) {
						int key = g.getNode(list.get(j)).key;
						int k = j-1;
						while(k >= 0 && g.getNode(list.get(k)).key > key){
							Collections.swap(list, k, k+1);
							k--;
						}
					}
					Node n = g.getNode(list.get(0));
					scc.put(n.key, list);
				}
			}
		}
	}
	
	public static void visitT(Node node, Graph g, List<String> list){
		time = time+1;
		node.color = "gray";
		node.startTime = time;
		for (int i = 0; i < g.adjMatrix[node.key].length; i++) {
			//Here we find the strongly connected components
			if(g.adjMatrix[node.key][i]==true){
				Node node1 = g.getNode(i);
				if(node1.color.equals("white")){
					//add the adjacent node to list as a member of SCC
					list.add(node1.name);
					visitT(node1,g, list);
				}
			}
		}
		node.color = "black";
		time = time+1;
		node.endTime = time;
	}
	
	public static void sort(Graph g){
		//sort the vertexes of the graph according to their DFS finish time
		Map<String,Node> map = g.vertexList;
		List<Node> list = new LinkedList<Node>(map.values());
		for (int j = 1; j < list.size(); j++) {
			int key = list.get(j).endTime;
			int k = j-1;
			while(k >= 0 && list.get(k).endTime < key){
				Collections.swap(list, k, k+1);
				k--;
			}
		}
		for (int i = 0; i < list.size(); i++) {
			Node node = list.get(i);
			node.color = "white";
			sortedList.add(node);
		}
	}
	
	public static Graph reverseGraph(Graph g){
		//reverse the edges of the directed graph
		boolean adjMatrix[][] = g.adjMatrix;
		boolean adjMatrix1[][] = new boolean[adjMatrix.length][adjMatrix.length];
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix[i].length; j++) {
				if(adjMatrix[i][j]==true){
					adjMatrix1[j][i] = true;
				}
			}
		}
		g.adjMatrix = adjMatrix1;
		return g;
	}
	
}
class Graph{
	//Map contains node name as key and node object as value
	Map<String, Node> vertexList = new HashMap<String,Node>();
	//Adjacency matrix to connect links between nodes
	boolean adjMatrix[][];
	//Adds a node object in vertex map
	public void addNode(Node node){
		vertexList.put(node.name,node);
	}
	//Initializes the Graph class with empty adj matrix
	public Graph(int size){
		adjMatrix = new boolean[size][size];
	}
	//Adds an edge between given two vertexes
	public void addEdge(Node node1,Node node2){
		adjMatrix[node1.key][node2.key] = true;
	}
	//Removes an edge from two vertexes
	public void removeEdge(Node node1, Node node2){
		adjMatrix[node1.key][node2.key] = false;
	}
	public Node getNode(String name){
		//returns node from the graph corresponding to the unique name
		//returns null if name not found
		if(vertexList.containsKey(name))
			return vertexList.get(name);
		else
			return null;
	}
	public Node getNode(int key){
		//returns node from the graph corresponding to the unique key
		//returns null if key not found
		Iterator<Node> itr = vertexList.values().iterator();
		while(itr.hasNext()){
			Node node = itr.next();
			if(node.key == key)
				return node;
		}
		return null;
	}
}
class Node{
	//Node class 
	//contains necessary information
	String name;
	int key;
	Node predecessor;
	String color;
	int startTime;
	int endTime;
	//creates a new node with given name and unique key
	public Node(String name, int key){
		this.name = name;
		this.key = key;
	}
}
