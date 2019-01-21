package fr.mrwormsy.missyproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MissyProject {

	public static void main(String[] args) {
		
		Graph graph = new Graph();
		
		MissySQL.connect();
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("book.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		scanner = new Scanner(System.in);
		
		while (true) {
			graph.handleInput(scanner);
			graph.display();
			
			graph.saveIntoDatabase();
		}
		
		//MissySQL.disconnect();

	}
}
