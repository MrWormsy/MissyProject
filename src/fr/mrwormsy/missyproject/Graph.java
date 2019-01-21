package fr.mrwormsy.missyproject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.mysql.jdbc.Statement;

public class Graph {

	//Variables
	private ArrayList<Node> nodes;
	private ArrayList<Arc> arcs;
	
	
	//Constructor
	public Graph() {
		this.setNodes(new ArrayList<Node>());
		this.setArcs(new ArrayList<Arc>());
	}

	
	//Getters, setters and adders
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	
	public void addNode(Node node) {
		this.nodes.add(node);
	}

	public ArrayList<Arc> getArcs() {
		return arcs;
	}

	public void setArcs(ArrayList<Arc> arcs) {
		this.arcs = arcs;
	}
	
	public void addArc(Arc arc) {
		this.arcs.add(arc);
	}

	//Methods
	public void handleInput(Scanner scanner) {		
		String dataFromInput = scanner.nextLine();
		
		ArrayList<String> data = new ArrayList<String>(Arrays.asList(dataFromInput.split(" ")));
		
		String previousWord = null;
		
		for(String word : data) {
			
			//Remove all the ponctuation...
			word.replaceAll("([?!()<>.,&\"'])+", "");
			
			
			//If the wordis not in the database we add it
			if (!this.wordExists(word.toLowerCase())) {
				this.createWordNode(word.toLowerCase());
			}
			
			//Here we want the add the relation bewteen this word and the previous one (only if this one is not the first)
			if (previousWord != null) {
				this.addArcRelation(previousWord.toLowerCase(), word.toLowerCase());
			}
			
			previousWord = word;
		}
	}


	private void addArcRelation(String previousWord, String word) {
		//Get the two nodes
		Node leftNode = this.getNodeFromLabel(previousWord);
		Node rightNode = this.getNodeFromLabel(word);
		
		//First check if this arc already exists and then 
		Arc arc = this.getArcFromNodes(leftNode, rightNode);
		if (arc == null) {
			this.createArcFromNode(leftNode, rightNode);
		} else {
			arc.increaseWeight();
		}
		
	}


	private void createArcFromNode(Node leftNode, Node rightNode) {
		Arc arc = new Arc(leftNode, rightNode);
		this.addArc(arc);
	}


	private Arc getArcFromNodes(Node leftNode, Node rightNode) {
		for(Arc arc : this.getArcs()) {
			if (arc.getLeftEdge() == leftNode && arc.getRightEdge() == rightNode) {
				return arc;
			}
		}
		return null;
	}


	private Node createWordNode(String word) {
		Node node = new Node(word);
		this.addNode(node);
		return node;
	}


	private boolean wordExists(String word) {		
		return this.getNodeFromLabel(word) != null;
	}
	
	public Node getNodeFromLabel(String label) {
		for(Node node : this.getNodes()) {
			if (node.getLabel().equalsIgnoreCase(label)) {
				return node;
			}
		}
		return null;
	}


	public void display() {
		System.out.println("---- Nodes ----");
		for(Node node : this.getNodes()) {
			node.displayNode();
		}
		
		System.out.println("---- Arcs ----");
		for(Arc arc : this.getArcs()) {
			arc.displayArc();
		}
	}
	
	
	public void saveIntoDatabase() {
		try {
			Statement statement = (Statement) MissySQL.getConnection().createStatement();
			
			//For nodes we only have to add the ones which are missing
			for(Node node : this.getNodes()) {
				statement.execute("INSERT INTO node(label) SELECT * FROM (SELECT '" + node.getLabel() + "') AS tmp WHERE NOT EXISTS (SELECT label FROM node WHERE label = '" + node.getLabel() + "') LIMIT 1;");
			}
			
			for(Arc arc : this.getArcs()) {
				
				statement.execute("INSERT INTO arc(leftNodeId, rightNodeId, weight) SELECT * FROM (SELECT (SELECT `id` FROM `node` WHERE `label` = '" + arc.getLeftEdge().getLabel() + "'), (SELECT `id` FROM `node` WHERE `label` = '" + arc.getRightEdge().getLabel() + "'), '" + arc.getWeight() + "') AS tmp WHERE NOT EXISTS (SELECT leftNodeId, rightNodeId FROM arc WHERE leftNodeId = (SELECT `id` FROM `node` WHERE `label` = '" + arc.getLeftEdge().getLabel() + "') and rightNodeId = (SELECT `id` FROM `node` WHERE `label` = '" + arc.getRightEdge().getLabel() + "')) LIMIT 1;");
				
				//statement.execute("INSERT INTO arc (leftNodeId, rightNodeId, weight) VALUES ((SELECT `id` FROM `node` WHERE `label` = '" + arc.getLeftEdge().getLabel() + "'), (SELECT `id` FROM `node` WHERE `label` = '" + arc.getRightEdge().getLabel() + "'), '" + arc.getWeight() + "');");
				
				//statement.execute("INSERT INTO arc (leftNodeId, rightNodeId, weight) VALUES ((SELECT `id` FROM `node` WHERE `label` = '" + arc.getLeftEdge().getLabel() + "'), (SELECT `id` FROM `node` WHERE `label` = '" + arc.getRightEdge().getLabel() + "'), '" + arc.getWeight() + "');");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
