/*
 * Author : Mitkumar Pandya
 * Unity id : mhpandya
 */
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class allpairs {
	//initializing the variables as static as they will be used in different methods.
	static int[][] path;
	static int infinite = 99999;
	static double reachableWords = 0.0;
	
	public static void main(String[] args) {
		try{
			//Scan and read through the input file
			Scanner sc = new Scanner(System.in);
			int input = sc.nextInt();
			//Create a newGraph object
			Graph g = new Graph(input);
			//Add input nodes in graph object
			for (int i = 0; i < input; i++) {
				g.addNode(sc.next(),i);
			}
			//Create an empty List of String objects to store the input query strings.
			List<String> list = new ArrayList<String>();
			//Create an empty 2d int array to store the path values
			path = new int[input][input];
			//Add all the graph nodes to the list
			list.addAll(g.nodes.keySet());
			/*Parse through all the nodes to find similarity
			 * between words which will create edges between words
			 */
			for (int i = 0; i < list.size(); i++) {
				//Take first word
				String s1 = list.get(i);
				//Parse through the rest of the list to find edges between current word i.e. s1
				for (int j = 0; j < list.size(); j++) {
					String s2 = list.get(j);
					//If length of both the words is same
					if(s1.length() == s2.length()){
						//Find similarity between them
						int similarity = findSimilarity(s1, s2);
						int distance = 0;
						//If the similarity between two words differs by only one letter
						if(similarity == 1){
							//Find the distance i.e. difference index between the different letter
							distance = findDistance(list.get(i),list.get(j));
							//Add the distance as an edge between these two words.
							g.addEdge(s1, s2, distance);
						}
					}
				}
			}
			//Compute the shortest path between all the reachable words.
			int[][] shortestPath = computeFloydWarshallSP(g);
			//Iterate through the 2d array of shortest path
			for (int i = 0; i < shortestPath.length; i++) {
				for (int j = 0; j < shortestPath[i].length; j++) {
					//Count no. of reachable words for whole list
					if(shortestPath[i][j]!=infinite)
						reachableWords++;
				}
			}
			//Compute the average of reachable words to no.of words rounded to 2 decimals
			System.out.println(Math.round((reachableWords/input)*100)/100D);
			int tests = sc.nextInt();
			sc.nextLine();
			//Scan through the input queries
			while(sc.hasNext()){
				String s = sc.nextLine();
				//Split the input line to get both the words
				String[] s1 = s.split(" ");
				//Find the unique key of both the words from graph
				int key1 = g.getNode(s1[0]);
				int key2 = g.getNode(s1[1]);
				//Get the distance between two words
				int dist = shortestPath[key1][key2];
				//If both the words are reachable from each other
				if(dist!=infinite){
					//If there is an edge between two words with shortest distance print it as output
					if(g.adjMatrix[key1][key2]==dist)
						System.out.println(dist+" "+g.getNode(key1)+" "+g.getNode(key2));
					else{
						//Else trace back the shortest path as computed using
						//Floyd-warshall algorithm
						int k = path[key1][key2];
						System.out.print(dist+" "+g.getNode(key1)+" ");
						while(true){
							System.out.print(g.getNode(k)+" ");
							//Break the loop when the destination is found else continue recursively
							if(k == key2)
								break;
							k = path[k][key2];
						}
					}
					System.out.println();
				}
				else{
					//Print the recursively found path as output
					System.out.println(g.getNode(key1)+" "+g.getNode(key2)+" not reachable");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Implementation of Floyd-Warshall algorithm to compute the shortest distance
	static int[][] computeFloydWarshallSP(Graph g){
		//Get the graph's adjacency matrix
		int[][] adjMatrix = g.adjMatrix;
		int size = adjMatrix.length;
		//Create an empty 2d array to calculate the distance
		int[][] distance = new int[size][size];
		//Iterate through the adjacency matrix
		for (int i = 0; i < adjMatrix.length; i++) {
			for (int j = 0; j < adjMatrix[i].length; j++) {
				//If there exist an edge between two words
				if(adjMatrix[i][j] > 0){
					//Get that distance as shortest path
					distance[i][j] = adjMatrix[i][j];
					path[i][j] = j;
				}
				//else mark words as not reachable
				else if(i!=j)
					distance[i][j] = infinite;  
			}
		}
		
		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					//If the intermediate distance is lower than existing distance
					if(distance[i][j] > distance[i][k] + distance[k][j]){
						//update shortest distance as newly computed distance
						distance[i][j] = distance[i][k] + distance[k][j];
						//update intermediate node as path
						//will be used while tracing back the shortest path between words
							path[i][j] = path[i][k];
					}
				} 
			}
		}
		return distance;
	}
	
	static int findSimilarity(String s1, String s2){
		//Find the similarity index between two words
		char[] a = s1.toCharArray();
		char[] b = s2.toCharArray();
		int c = 0;
		for (int i = 0; i < a.length; i++) {
			if(a[i] != b[i])
				c++;
		}
		return c;
	}
	static int findDistance(String s1, String s2){
		//Find the difference between two letters of the 2 words
		char[] a = s1.toCharArray();
		char[] b = s2.toCharArray();
		int c = 0;
		for (int i = 0; i < a.length; i++) {
			if(a[i] != b[i]){
				c = Math.abs(((int) a[i])-((int) b[i]));
			}
		}
		return c;
	}

}
//Graph object which keeps track of nodes and edges
class Graph{
	Map<String,Integer> nodes;
	int[][] adjMatrix;
	
	public Graph(int size){
		nodes = new HashMap<String,Integer>();
		adjMatrix = new int[size][size];
	}
	public void addNode(String name, int key){
		nodes.put(name,key);
	}
	public void addEdge(String s1, String s2, int weight){
		adjMatrix[nodes.get(s1)][nodes.get(s2)] = weight;
	}
	public int getNode(String s){
		return nodes.get(s); 
	}
	public String getNode(int key){
		Iterator<String> itr = nodes.keySet().iterator();
		while(itr.hasNext()){
			String s = itr.next();
			if(nodes.get(s)==key)
				return s;
		}
		return null;
	}
}
