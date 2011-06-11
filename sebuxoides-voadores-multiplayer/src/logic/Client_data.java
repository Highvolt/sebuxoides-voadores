package logic;

import java.io.Serializable;
import java.util.HashSet;

public class Client_data implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4993184599254321915L;
	private String name;
	private HashSet<Integer> keyboard;
	
	
	
	
	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public HashSet<Integer> getKeyboard() {
		return keyboard;
	}




	public void setKeyboard(HashSet<Integer> keyboard) {
		this.keyboard = keyboard;
	}




	public Client_data(String name) {
		this.name=name;
		keyboard=new HashSet<Integer>();
	}
}
