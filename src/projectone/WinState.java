package projectone;

import jig.ResourceManager;

import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;



class WinState extends BasicGameState
{
	
	P1Game p1;
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException 
	{
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {

		P1Game p1 = (P1Game)game;
		
		p1.win = true;
		p1.level1 = false;
		p1.level2 = false;
		p1.level3 = false;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException 
	{
	
		P1Game p1 = (P1Game)game;
		
		//TODO add splash here
		g.drawString("Final Score: " + p1.score, 10, 210);
		g.drawString("[ YOU WON ]", 10, 190);
		g.drawString("- Samantha Deshazer cs427 fall 2021 -", 10, 260);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException 
	{
		p1 = (P1Game) game;
		Input input = container.getInput();
		if(p1.win) {
			if(input.isKeyDown(Input.KEY_Q) )
			{
				p1.enterState(P1Game.STARTUPSTATE);
			}
		}
		
	}
	
	
	@Override
	public int getID() 
	{
		return P1Game.WINSTATE;  // resource manager.
	}
}
