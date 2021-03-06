package projectone;

import jig.ResourceManager;

import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


class StartUpState extends BasicGameState
{
	P1Game p1;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException 
	{
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) 
	{
		container.setSoundOn(true);
		p1 = (P1Game)game;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException 
	{
	
		P1Game p1 = (P1Game)game;
		
		//TODO add splash here
		g.drawString("Score: " + p1.score, p1.ScreenWidth - 150, p1.ScreenHeight - 70);
		g.drawString("Zelda Pacman survival:", 10, 150);
		g.drawString("Controls: [ W S D ]", 10, 170);
		g.drawString("Objective: collect potions and get to the door.", 10, 190);
		g.drawString("[ PRESS SPACE TO CONTINUE ]", 10, 210);
		g.drawString("Samantha Deshazer cs427 fall 2021", 10, 230);

			
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException 
	{
		Input input = container.getInput();
		
		if (input.isKeyDown(Input.KEY_SPACE))
		  p1.enterState(P1Game.LEVEL1);	
   		  
	}
	
	
	@Override
	public int getID() 
	{
		return P1Game.STARTUPSTATE;  // resource manager.
	}
}
