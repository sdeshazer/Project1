package projectone;

import java.util.ArrayList;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import projectone.GameOverState;
import projectone.PlayingState;
import projectone.SceneTransition;
import projectone.StartUpState;


public class Game extends StateBasedGame{
	
	public static final int STARTUPSTATE = 0;
	public static final int lEVEL1 = 1;
	public static final int lEVEL2 = 2;
	public static final int lEVEL3 = 3;
	public static final int GAMEOVERSTATE = 4;
	public static final int STATETRANSITION = 5;
	
	public static final String STUMP_NODE = "projectone/resource/treestump_00.png";
	public final int MAZEWIDTH = 28;
	public final int MAZEHEIGHTH = 31; 
	
	public final int ScreenWidth;
	public final int ScreenHeight;
	
	Player player;
	Monster monster; 
	
	
	
	// the typical pacman maze (31 x 28):
	// 0 are normally dots, 2 is an energy dot. 3 is blank, 1 is a stump
	int maze[][] = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,2,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,2,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,0,1,1,1,1,1,3,1,1,3,1,1,1,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,1,1,1,3,1,1,3,1,1,1,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,3,3,3,3,3,3,3,3,3,3,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,3,1,3,3,3,3,3,3,1,3,1,1,0,1,1,1,1,1,1},
			{3,6,3,3,3,3,0,3,3,3,1,3,3,3,3,3,3,1,3,3,3,0,3,3,3,3,5,3},//midpoint
			{1,1,1,1,1,1,0,1,1,3,1,3,3,3,3,3,3,1,3,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,3,3,3,3,3,3,3,3,3,3,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,2,0,0,1,1,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,1,1,0,0,2,1},
			{1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1},
			{1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1},
			{1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};
	//31 x 28:	
	int mazetemp[][] = new int[MAZEHEIGHTH][MAZEWIDTH];

	int maze2[][]={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
			{1,2,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,2,1},
			{1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,3,0,0,0,0,0,0,0,0,3,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,3,3,3,3,3,3,3,3,3,3,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,1,3,1,3,3,3,3,3,3,1,3,1,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,3,3,3,1,3,3,3,3,3,3,1,3,3,3,0,1,1,1,1,0,1},//midpoint
			{1,0,1,1,1,1,0,1,1,3,1,3,3,3,3,3,3,1,3,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,1,3,3,3,3,3,3,3,3,3,3,1,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,3,1,1,1,1,1,1,1,1,3,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,3,0,0,0,0,0,0,0,0,3,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,1,3,1,1,3,1,1,3,1,1,3,1,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,3,1,1,3,1,1,3,1,1,3,1,1,0,1,1,1,1,0,1},
			{1,2,1,1,1,1,0,1,1,3,1,1,3,1,1,3,1,1,3,1,1,0,1,1,1,1,2,1},
			{1,0,1,1,1,1,0,1,1,3,1,1,3,1,1,3,1,1,3,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};
	
	int maze2Temp[][]= new int[MAZEHEIGHTH][MAZEWIDTH];
	
	int maze2cando[][] = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,1,0,1,3,3,3,0,3,3,1,0,1,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,0,0,0,1,3,3,3,0,3,3,1,0,0,0,0,1,1,1,1,0,1},//midpoint
			{1,0,1,1,1,1,0,1,1,0,1,3,3,3,3,3,3,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}	
	};
	
	int mazecando[][] = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,1,0,0,0,0,0,0,1,0,1,1,0,1,1,1,1,1,1},
			{0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0},//midpoint
			{1,1,1,1,1,1,0,1,1,0,1,0,0,0,0,0,0,1,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
			{1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1},
			{1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1},
			{1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1},
			{1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
			
	};
	
	//////
	public Game(String title, int width, int height) {
		super(title);
		ScreenHeight = height;
		ScreenWidth = width;		
		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);		
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new Level1());
		addState(new SceneTransition());
		addState(new GameOverState());
		
		player = new Player(232,424,0,0);
		
		ResourceManager.loadImage(STUMP_NODE);
		
		for(int v=0;v<MAZEHEIGHTH;v++){
			for(int z=0;z<MAZEWIDTH;z++){
				mazetemp[v][z]=maze[v][z];
				maze2Temp[v][z]=maze2[v][z];
			}
		}		
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new Game("Project One", 448, 576));
			app.setDisplayMode(448, 576, false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}	
}
