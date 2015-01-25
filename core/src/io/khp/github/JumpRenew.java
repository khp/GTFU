package io.khp.github;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.*;

// 

public class JumpRenew {
	
	 private Circle circle;
	 private final int radius = 10;
	 
	 public JumpRenew(int x, int y){
		 // small floating white dot on screen
		 circle = new Circle();
		 circle.radius = radius;
		 circle.x = x;
		 circle.y = y;
	 }	 
	 
	 public Circle getCircle(){
		 return circle;
	 }
	 
	 public float getX(){
		 return circle.x;
	 }
	 
	 public float getY(){
		 return circle.y;
	 }
	 public float getRadius(){
		 return circle.radius;
	 }
	 
	 public void checkCollisions(Player player){
		 if (Intersector.overlaps(this.getCircle(), player.getRect())){
				player.setAirborne(false);
			}
	 }
}
