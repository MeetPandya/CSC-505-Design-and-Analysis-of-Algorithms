/*
 * Author : Mitkumar Pandya
 * Unity ID : mhpandya
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class huffman {
	
	public static void main(String[] args) {
		try{
			//read input from the scanner
			Scanner sc = new Scanner(System.in);
			StringBuffer sb = new StringBuffer();
			while (sc.hasNext()) {
				sb.append(sc.nextLine());
			}
			//create the min heap
			heap3 heap = new heap3();
			String[] s1 = sb.toString().split("");
			Map<String, Integer> map = new HashMap<String, Integer>();
			//create a map with default freq. 1 for every byte value
			for(int a = -129; a < Byte.MAX_VALUE; a++){
			    if( a > 32 && a < 127){
			    	Character c = ((char) a);
			    	map.put(c.toString(), 1);
			    }else{
			    	String s = Integer.toHexString(a);
			    	s = s.toUpperCase();
			    	if(s.length()>2){
			    		s = s.substring(6,8);
			    	}
			    	map.put(s,1);
			    }
			}
			//count the frequency of every byte character in given input
			for (String character : s1) {
				if(map.containsKey(character))
					map.put(character,map.get(character)+1);
			}
			//create an extra character representing end of file
			map.put("EOF", 1);
			//for every map entry calculate the freq and insert it into min heap
			for (String key : map.keySet()) {
				if(key!=null && !key.equals("")){
					Node node = new Node(key,map.get(key));
					heap.insert(node);
				}
			}
			//iterate through the heap and join two lowest character during each iteration
			while (heap.size() > 1) {
				Node node1 = null;
				Node node2 = null;
				//remove the lowest freq. nodes
				node1 = heap.removeMin();
				node2 = heap.removeMin();
				//if nodes are not null
				if(node1!=null && node2!=null){
					//combine the freq. of both the nodes
					int freq = node1.data+node2.data;
					//create new node object and insert the nodes as leaves
					Node node = new Node("", freq);
					node.left = node1;
					node.right = node2;
					//insert the new node to min heap
					heap.insert(node);
				}
			}
			//traverse through heap tree i.e. Huffman tree
			traverse(heap.removeMin(), "");
			for (int a = 0; a < Byte.MAX_VALUE; a++) {
				if( a > 32 && a < 127){
			    	Character c = ((char) a);
			    	System.out.println(String.format("%3s", c) + " " +codeMap.get(c.toString()));
			    }else{
			    	String s = Integer.toHexString(a);
			    	s = s.toUpperCase();
			    	String temp = s;
			    	if(s.length()>2){
			    		temp = s.substring(6,8);
			    	}
			    	else if(s.length()<2){
			    		temp = "0"+s;
			    	}
			    	System.out.println(String.format("%3s", temp) + " " +codeMap.get(s));
			    }
			}
			//traverse through all bytes again in the specified order and print the Huffman code
			for(int a = -129; a < 0; a++){
			    if( a > 32 && a < 127){
			    	Character c = ((char) a);
			    	System.out.println(String.format("%3s", c) + " " +codeMap.get(c.toString()));
			    }else{
			    	String s = Integer.toHexString(a);
			    	s = s.toUpperCase();
			    	if(s.length()>2){
			    		s = s.substring(6,8);
			    	}
			    	else if(s.length()<2){
			    		s = "0"+s;
			    	}
			    	System.out.println(String.format("%3s", s) + " " +codeMap.get(s));
			    }
			}
			System.out.println("EOF" +" "+ codeMap.get("EOF"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	static Map<String, String> codeMap = new HashMap<String, String>();
	public static void traverse(Node root, String code){
        if (root!=null){
        	//check left tree
            if (root.left!=null)
                traverse(root.left,code+"0");
            //check right tree
            if (root.right!=null)
            	traverse(root.right,code+"1");
            //print the value
            if (root.left==null && root.right==null)
            	codeMap.put(root.character, code);
        }
    }
}
