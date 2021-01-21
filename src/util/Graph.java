package util;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Graph {
	
	public int x, y, graphWidth, graphHeight, graphLength, numCategories;
	
	ArrayList<ArrayDeque<Double>> categories;
	ArrayList<Color> colors;

	public Graph(int x, int y, int graphWidth, int graphHeight, int graphLength, int numCategories) {
		
		this.x = x;
		this.y = y;
		this.graphHeight = graphHeight;
		this.graphWidth = graphWidth;
		this.graphLength = graphLength;
		
		this.categories = new ArrayList<ArrayDeque<Double>>();
		this.numCategories = numCategories;
		
		this.colors = new ArrayList<Color>();
		
		initCategories();
		
	}
	
	public void initCategories() {
		for(int i = 0; i < numCategories + 1; i++) {
			categories.add(new ArrayDeque<Double>());
			if(i == 0) {
				for(int j = 0; j < graphLength; j++) {
					categories.get(i).add((double) 1);
				}
			}
			
			else {
				for(int j = 0; j < graphLength; j++) {
					categories.get(i).add((double) 0);
				}
			}
			
			colors.add(Color.WHITE);
			
		}
	}
	
	public void reset() {
		for(int i = 0; i < numCategories + 1; i++) {
			if(i == 0) {
				for(int j = 0; j < graphLength; j++) {
					
					categories.get(i).add((double) 1);
					categories.get(i).pop();
				}
			}
			
			else {
				for(int j = 0; j < graphLength; j++) {
					
					categories.get(i).add((double) 0);
					categories.get(i).pop();
				}
			}
		}
	}
	
	public void addCategory(ArrayDeque<Double> category) {
		
		this.categories.add(category);
		numCategories++;
		
	}
	
	public void updateGraph(double[] values) {
		
		categories.get(0).pop();
		categories.get(0).add((double) 0);
		
		for(int i = 0; i < values.length; i++) {
			categories.get(i + 1).pop();
			categories.get(i + 1).add(values[i]);
		}
		
	}
	
	public void setColor(int whichCategory, Color color) {
		
		colors.set(whichCategory + 1, color);
		
	}
	
	public void draw(Graphics g) {
		
		//System.out.println(graphLength + " " + categories.get(0).size());
		//System.out.println(numCategories + " " + categories.size());
		
		double unitWidth = (double) graphWidth / graphLength;
		ArrayList<double[]> printGraph = new ArrayList<double[]>();
		
		for(int i = 0; i < numCategories + 1; i++) {
			
			Double[] temp = categories.get(i).toArray(new Double[graphLength]);
			double[] doubleTemp = new double[graphLength];
			
			for(int j = 0; j < temp.length; j++) {
				doubleTemp[j] = temp[j].doubleValue();
			}
			
			printGraph.add(doubleTemp);
			//System.out.println(categories.get(0).size());
		}
		
//		printGraph[0] = categories.get(0).toArray(new Double[0]);
//		printGraph[1] = categories.get(1).toArray(new Double[0]);
//		printGraph[2] = categories.get(2).toArray(new Double[0]);
//		printGraph[3] = categories.get(3).toArray(new Double[0]);
//		printGraph[4] = categories.get(4).toArray(new Double[0]);
		
		for(int i = 0; i < graphLength; i++) {
			
			double heightSum = graphHeight;
			
			for(int j = 0; j < numCategories + 1; j++) {
				
				
				double curHeight =  (printGraph.get(j)[i] * graphHeight);
				//double curHeight = 0;
				//System.out.println(j + " " + i);
				
				g.setColor(colors.get(j));
				
				g.fillRect((int)(i * unitWidth) + x, (int)this.y, (int)unitWidth + 1, (int)heightSum);
				heightSum -= curHeight;
			}
			
		}
		
	}
	
}
