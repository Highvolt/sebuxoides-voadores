package logic;

import java.util.ArrayList;
import java.awt.FontMetrics;

public class Highscores implements Comparable<Highscores>{

	private	double x;
	private double y;
	private double a;
	private double b;
	private String name;
	private Integer score;
	private Integer pos;
	private ArrayList<Asteroid> ast ;
	private FontMetrics letras;
	private boolean done=false;
	private long timestamp=0;
	
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public ArrayList<Asteroid> getAst() {
		return ast;
	}

	public void setAst(ArrayList<Asteroid> ast) {
		this.ast = ast;
	}

	public Highscores(String nome, int score){
		this.name=nome;
		this.score=score;
		this.timestamp=System.currentTimeMillis();
		

	}


	@Override
	public boolean equals(Object obj) {
		Highscores a=(Highscores) obj;
		return name.equals(a.name) && score.equals(a.score) && (Math.abs(timestamp-a.timestamp)<100);
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void reset(){
		ast=null;
		done=false;
	}

	public void update(int pos) {
		
		if(letras != null){
			if(this.x>30){
				x=x-3;
			}
			if(this.y>(10+50*pos)){
				y=y-4;
			}
			if(!(this.x>30 || this.y>(10+10*b))){
				done=true;
			}
			a=letras.stringWidth((name+"   "+score))/2+letras.stringWidth((name+"   "+score))/4;
			b=letras.getHeight();
			if(ast==null){
				ast = new ArrayList<Asteroid>();
				for(int i=(int)(-a/2);i<(a/2+a);i=i+60){
					ast.add(new Asteroid((float) i, (float)0,0,1,2));
					ast.add(new Asteroid((float) i, (float)0,0,-1,2));
					
				}
				/*
				ast.add(new Asteroid((float)(0+a/2),(float) (0+2*b), 0, 1, 2));
				ast.add(new Asteroid((float)(0+a),(float) (0+b), 0, 1, 2));
				ast.add(new Asteroid((float)(0+a/2),(float) (0), 0, -1, 2));
				ast.add(new Asteroid((float)(0+a),(float) (0+b), 0, -1, 2));*/
			}
			for(Asteroid at: ast){
				if(Double.isNaN(getye((double)(at.getX()+at.getRotation()*1)-(0+a/2),(int)at.getRotation()))){
					at.setRotation(-1*at.getRotation());
					//System.out.println(at.getRotation());
					//at.setX((float)(at.getX()+at.getRotation()*5));
					
			
				}
				at.setX((float)(at.getX()+at.getRotation()*1));
				at.setY((float)(y-b/2)+(float)getye((double) at.getX()-(0+a/2),(int)at.getRotation()));
				/*if(at.getX()>(a+x)){
					at.setRotation(-1);
				}
				if(at.getX()<(x-a)){
					at.setRotation(1);
				}*/
				
				//System.out.println("x: "+at.getX()+" Y: "+at.getY());
			}
			//System.out.println("done!");

		} 

	}

	public FontMetrics getLetras() {
		return letras;
	}

	private double getye(double x, int rot){
		//System.out.println("y calc:"+Math.sqrt((1-(Math.pow(x, 2.0)/Math.pow(a, 2.0)))*Math.pow(b,2.0)));
		if(rot>0){
		return Math.sqrt((1-(Math.pow(x, 2.0)/Math.pow(a, 2.0)))*Math.pow(b,2.0));}
		else{
			return -Math.sqrt((1-(Math.pow(x, 2.0)/Math.pow(a, 2.0)))*Math.pow(b,2.0));
		}
	}

	public void setLetras(FontMetrics letras) {
		this.letras = letras;
	}

	@Override
	public int compareTo(Highscores o) {
		Highscores a= (Highscores)o;
		return  a.score.compareTo(score);
	}




}
