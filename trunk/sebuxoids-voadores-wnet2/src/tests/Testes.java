package tests;

import junit.framework.Assert;
import logic.Engine;

import org.junit.Test;

public class Testes {


	/* testa se a nave e' colocada no centro do tabuleiro no inicio do jogo
	 * 
	 */
	@Test
	public void SpaceShipInitialPlacement() {   
		int height = 50;
		int width = 50;
		Engine jogo = new Engine(height, width);

		Assert.assertEquals((double) height / 2, jogo.getSpaceShip().getY(),
				0.0);
		Assert.assertEquals((double) width / 2, jogo.getSpaceShip().getX(), 0.0);

	}

	/*
	 * testa se a nave se move de acordo com a formula pre-establecida a cada update do estado do jogo
	 */

	@Test
	public void SpaceShipMovement() {
		int height = 50;
		int width = 50;
		Engine jogo = new Engine(height, width);
		jogo.update();
		// Nao ha' movimento porque a aceleracao inicial e' 0
		jogo.getSpaceShip().setAceleration((float) 1);
		Assert.assertEquals((double) height / 2, jogo.getSpaceShip().getY(),
				0.0);
		Assert.assertEquals((double) width / 2, jogo.getSpaceShip().getX(), 0.0);
		jogo.acceleration();
		double xi = jogo.getSpaceShip().getX();
		double yi = jogo.getSpaceShip().getY();


		jogo.update();
		float vx = jogo.getSpaceShip().getVx();
		float vy = jogo.getSpaceShip().getVy();
		boolean a = ((vx + vy) != 0);

		Assert.assertTrue(a);
		Assert.assertEquals(xi + vx, jogo.getSpaceShip().getX(), 0.001);
		Assert.assertEquals(yi - vy, jogo.getSpaceShip().getY(), 0.001);
	}


	/*
	 * testa se os misseis sao criados quando se dispara
	 */

	@Test
	public void shootcreator(){
		int height = 50;
		int width = 50;
		Engine jogo = new Engine(height, width);
		Assert.assertEquals(0,jogo.getBullets().size());
		jogo.shoot();
		Assert.assertEquals(1, jogo.getBullets().size());

	}

	/*
	 * Testa se os misseis se movem conforme a formula a cada update do estado do jogo
	 */

	@Test
	public void shootmovement(){
		int height = 50;
		int width = 50;
		Engine jogo = new Engine(height, width);
		Assert.assertEquals(0,jogo.getBullets().size());
		jogo.shoot();
		float xi=jogo.getBullets().get(0).getPos_x();
		float yi=jogo.getBullets().get(0).getPos_y();
		float acc=jogo.getBullets().get(0).getAceleration();
		float angle=jogo.getBullets().get(0).getAngle();

		float nextx=(float) (xi + acc* Math.sin(angle));
		float nexty=(float) (yi - acc* Math.cos(angle));
		jogo.update();
		Assert.assertEquals(nextx, jogo.getBullets().get(0).getPos_x(), 0.0);
		Assert.assertEquals(nexty, jogo.getBullets().get(0).getPos_y(), 0.0);
	}

	/*
	 * Testa se os asteroids sao colocados correctamente
	 */

	@Test
	public void asteroidcreation(){
		int height = 200;
		int width = 200;
		Engine jogo = new Engine(height, width);
		Assert.assertEquals(0, jogo.getAsteroids().size());
		jogo.place_new_asteroid();
		Assert.assertEquals(1, jogo.getAsteroids().size());
	}

	/*
	 * Testa se o asteroid se destruiu quando atinguido por um missel 
	 */

	@Test
	public void shootAsteroid(){
		int height = 200;
		int width = 200;
		Engine jogo = new Engine(height, width);
		Assert.assertEquals(0, jogo.getAsteroids().size());
		jogo.place_new_asteroid();
		Assert.assertEquals(1, jogo.getAsteroids().size());
		float xa=jogo.getAsteroids().get(0).getX();
		float ya=jogo.getAsteroids().get(0).getY();
		int type=jogo.getAsteroids().get(0).getType();
		jogo.shoot(xa, ya);
		jogo.update();
		if(type!=1){
			Assert.assertEquals(2,jogo.getAsteroids().size()); //destroi o actual e constroi dois novos
			Assert.assertEquals(type-1, jogo.getAsteroids().get(0).getType());
			Assert.assertEquals(type-1, jogo.getAsteroids().get(1).getType());
		}else{
			Assert.assertEquals(0,jogo.getAsteroids().size());
		}                                 
	}

	/*
	 * Testa se a nave passa de um lado para o outro do ecrã quando atinge o limite do ecra de jogo
	 */

	@Test
	public void SpaceShipCrosstheScreen(){
		int height = 50;
		int width = 50;
		Engine jogo = new Engine(height, width);
		jogo.update();
		// Nao ha' movimento porque a aceleracao inicial e' 0
		jogo.getSpaceShip().setAceleration((float) 1);
		jogo.getSpaceShip().setY(-1);
		jogo.update();
		Assert.assertEquals(height, jogo.getSpaceShip().getY(), 0.0);
		jogo.getSpaceShip().setX(-2);
		jogo.update();
		Assert.assertEquals(height, jogo.getSpaceShip().getX(), width);

	}


	/*
	 * testa se o jogo acaba se o um asteroide embater na nave
	 */
	@Test
	public void LostTheGame(){

		int height = 50;
		int width = 50;
		Engine jogo = new Engine(height, width);
		jogo.place_new_asteroid();
		jogo.getSpaceShip().setX(jogo.getAsteroids().get(0).getX());
		jogo.getSpaceShip().setY(jogo.getAsteroids().get(0).getY());
		//System.out.println("xa:"+jogo.getAsteroids().get(0).getX()+" ya: "+jogo.getAsteroids().get(0).getY()+" x: "+jogo.getSpaceShip().getX()+" y: "+jogo.getSpaceShip().getY());
		while(jogo.getLives()>0){
			jogo.update();
		}


		Assert.assertTrue(!jogo.isVivo());

	}




}
