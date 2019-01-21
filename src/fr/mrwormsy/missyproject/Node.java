package fr.mrwormsy.missyproject;

public class Node {

	private String label;
	
	public Node(String label) {
		this.setLabel(label);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
	public void displayNode() {
		System.out.println("   " + this.getLabel());
	}
}
