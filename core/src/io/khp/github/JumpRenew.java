package io.khp.github;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.*;

// 

public class JumpRenew {
	
	 private Circle circle;
	 private final int radius = 10;
	 
	 public JumpRenew(){
		 // small floating white dot on screen
		 circle = new Circle();
		 circle.radius = radius;
		 circle.x = MainGame.getBoardHeight() / 3;
		 circle.y = 50;
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
}
