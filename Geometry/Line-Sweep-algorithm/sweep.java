/*
 * Author : Mitkumar Pandya
 * CSC-505 Fall 2016
 * Department of Computer Science
 * 	NC State
 */

import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class sweep {
	//Initializing the list of lines used throughout the program
	static List<Line> lines;
	public static void main(String[] args) {
		try{
			//Read through input file
			Scanner sc = new Scanner(System.in);
			int input = sc.nextInt();
			lines = new LinkedList<Line>();
			//Create a min-heap to sort the point based on lower x axis first
			//If x axis values are same then lower y value will be considered
			Heap points = new Heap(input*2);
			while(sc.hasNext()){
				String s = sc.nextLine();
				if(s!="" && s.length()>0){
					String[] s1 = s.split(" ");
					double x1 = Double.parseDouble(s1[0]);
					double y1 = Double.parseDouble(s1[1]);
					double x2 = Double.parseDouble(s1[2]);
					double y2 = Double.parseDouble(s1[3]);
					Point p1 = null;
					Point p2 = null;
					//From input lines the lower x value will be taken as left point
					if (x1 > x2){
						//Create points with x,y values and identify whether it's left or right
						p1 = new Point(x2, y2);
						p1.isLeft = true;
						p2 = new Point(x1, y1);
						p2.isLeft = false;
					}else{
						p1 = new Point(x1, y1);
						p1.isLeft = true;
						p2 = new Point(x2, y2);
						p2.isLeft = false;
					}
					//Create line segment using two points
					Line l = new Line(p1, p2);
					//insert points to min-heap
					points.insert(p1);
					points.insert(p2);
					//insert line in lines list
					lines.add(l);
				}
			}
			//Find if segment intersects using Sweep Line Algorithm
			findSegmentIntersect(points);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Method to implement Line sweep algorithm
	static void findSegmentIntersect(Heap points){
		//Create an empty TreeMap which works as self balancing Binary Search tree
		TreeMap<Integer,Line> tree1 = new TreeMap<Integer,Line>();
		List<Line> list = new LinkedList<Line>();
		//Iterate through all the points
		for (int i = 0; i < points.maxSize; i++) {
			//Remove leftmost point from the heap
			Point p = points.remove();
			//Get the corresponding line segment from the point
			Line line = getLine(p);
			//System.out.println(i);
			//If p is the left point of the segment
			if(p.isLeft){
				//assign point's y axis value as key of the line
				line.index = (int) p.y;
				//Put the line in the treemap
				tree1.put(line.index, line);
				list.add(line);
				Line above = null;
				Line below = null;
				//if current key has predecessor
				if(tree1.lowerKey(line.index)!=null){
					int a = tree1.lowerKey(line.index);
					//name this segment as below
					below = tree1.get(a);
				}
				//if current key has successor
				if(tree1.higherKey(line.index)!=null){
					int b = tree1.higherKey(line.index);
					//name this segment as above
					above = tree1.get(b);
				}
				//if above segment exists and intersects with current segment
				if(above!=null && intersects(line, above)){
					//calculate the intersection point
					calculateIntersectionPoint(line, above);
				}
				//if below exists and intersects with current segment
				if(below!=null && intersects(line, below)){
					//calculate the intersection point
					calculateIntersectionPoint(line, below);
				}
			}
			//If p is the right point of the segment
			if(!p.isLeft){
				Line above = null;
				Line below = null;
				//if current key has predecessor
				if(tree1.lowerKey(line.index)!=null){
					int a = tree1.lowerKey(line.index);
					below = tree1.get(a);
				}
				//if current key has successor
				if(tree1.higherKey(line.index)!=null){
					int b = tree1.higherKey(line.index);
					above = tree1.get(b);
				}
				//if both above and below exists and intersects with each other
				if(above!=null && below!=null && intersects(above, below)){
					//calculate the intersection point
					calculateIntersectionPoint(above, below);
				}
				//Right point has been processed hence delete this segment
				tree1.remove(line.index);
			}
		}
	}
	//method to return line based on the point
	static Line getLine(Point p){
		for (int i = 0; i < lines.size(); i++) {
			Line line = lines.get(i);
			//if point is the left one
			if(line.p1 == p)
				return line;
			//if point is the right one
			if(line.p2 == p)
				return line;
		}
		return null;
	}
	//method to find if two segments intersects
	static boolean intersects(Line l1, Line l2){
		//get the left and right points of the two segments
		Point p1 = l1.p1;
		Point p2 = l1.p2;
		Point p3 = l2.p1;
		Point p4 = l2.p2;
		//Calculate the orientation of each end point respect to the other segments 
		double d1 = direction(p3, p4, p1);
		double d2 = direction(p3, p4, p2);
		double d3 = direction(p1, p2, p3);
		double d4 = direction(p1, p2, p4);
		
		//if the direction signs differ, then the segments straddles each other
		if(d1 != d2 && d3 != d4)
			return true;
		/*else if(((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0))&& ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0)))
			return true;*/
		//If relative orientation is zero and point pi is on Pj and Pk
		else if(d1==0.0 && onSegment(p3, p4, p1))
			return true;
		else if(d2==0.0 && onSegment(p3, p4, p2))
			return true;
		else if(d3==0.0 && onSegment(p1, p2, p3))
			return true;
		else if(d4==0.0 && onSegment(p1, p2, p4))
			return true;
		//else return no intersection
		return false;
	}
	//Calculate the relative orientation of ech point
	//Using cross product formula
	static int direction(Point pi, Point pj, Point pk){
		//(pk-pi)*(pj-pi)
		double d = ((pk.x - pi.x)*(pj.y - pi.y))-((pj.x - pi.x)*(pk.y - pi.y));
		if(d == 0.0)
			return 0;
		else {
			return (d > 0 ? 1 : 2);
		}
	}
	//Check if point pi is on segment pj and pk
	static boolean onSegment(Point pi, Point pj, Point pk){
		if((pk.x <= Math.max(pi.x, pj.x) && pk.x >= Math.min(pi.x, pj.x)) &&
				(pk.y <= Math.max(pi.y, pj.y) && pk.y >= Math.min(pi.y, pj.y))){
			return true;
		}
		return false;
	}
	//Method to calculate intersection point if there exists any
	static void calculateIntersectionPoint(Line l1, Line l2){
		Point p1 = l1.p1;
		Point p2 = l1.p2;
		Point p3 = l2.p1;
		Point p4 = l2.p2; 
		double x1 = p1.x;
		double x2 = p2.x;
		double x3 = p3.x;
		double x4 = p4.x;
		double y1 = p1.y;
		double y2 = p2.y;
		double y3 = p3.y;
		double y4 = p4.y;
		//cross products of x and y
		double x = (((x2*y1 - x1*y2)*(x4 - x3))-((x4*y3 - x3*y4)*(x2 - x1)))/((x2 - x1)*(y4 - y3)-(x4 - x3)*(y2 - y1));
		double y = (((x2*y1 - x1*y2)*(y4 - y3))-((x4*y3 - x3*y4)*(y2 - y1)))/((x2 - x1)*(y4 - y3)-(x4 - x3)*(y2 - y1));
		//round the x and y values to 2 decimals
		x = Math.round(x*100.00)/100.00;
		y = Math.round(y*100.00)/100.00;
		//print as output
		System.out.println(x+" "+y);
	}
	
	static void getLineIntersection(Line l1 , Line l2){
		Point p1 = l1.p1;
		Point p2 = l1.p2;
		Point p3 = l2.p1;
		Point p4 = l2.p2; 
		double s1x = p2.x - p1.x;
		double s2x = p4.x - p3.x;
		double s1y = p2.y - p1.y;
		double s2y = p4.y - p3.y;
		double s , t;
		s = (-s1y * (p1.x - p3.x) + s1x * (p1.y - p3.y))/(-s2x * s1y + s1x * s2y);
		t = ( s2x * (p1.y - p3.y) - s2y * (p1.x - p3.x))/(-s2x * s1y + s1x * s2y);
		
		if(s >= 0 && s<= 1 && t>= 0 && t <= 1)
		{
			double x = p1.x + ( t * s1x );
			double y = p1.y + ( t * s1y );
			System.out.println(x+" "+y);
		}
	}
	
}
//class representation of line or segment
class Line {
	Point p1;
	Point p2;
	int index;
	
	public Line(Point p1, Point p2){
		this.p1 = p1;
		this.p2 = p2;
	}
}
//class representation of point
class Point{
	double x;
	double y;
	boolean isLeft;
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
}

