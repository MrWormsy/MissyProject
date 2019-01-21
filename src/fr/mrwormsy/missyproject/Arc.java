package fr.mrwormsy.missyproject;

public class Arc {

	private Node leftEdge;
	private Node rightEdge;
	private int weight;
	
	public Arc(Node leftEdge, Node rightEdge) {
		this.setLeftEdge(leftEdge);
		this.setRightEdge(rightEdge);
		this.setWeight(1);
	}

	public Node getLeftEdge() {
		return leftEdge;
	}

	public void setLeftEdge(Node leftEdge) {
		this.leftEdge = leftEdge;
	}

	public Node getRightEdge() {
		return rightEdge;
	}

	public void setRightEdge(Node rightEdge) {
		this.rightEdge = rightEdge;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public void increaseWeight() {
		this.weight++;
	}
	
	public void displayArc() {
		System.out.println("   " + this.getLeftEdge().getLabel() + " --> " + this.getRightEdge().getLabel() + " (" + this.getWeight() + ")");
	}
	
}
