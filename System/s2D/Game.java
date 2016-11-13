package s2D;

import s2D.windowEngine.*;
import s2D.dataEngine.*;
import s2D.sceneEngine.*;

/**
 * Begins and preserves the game.
 * @author Guillaume Singland
 * @version 2.0
 */
public class Game {
	
	//////////////// PARAMETERS /////////////////////////////////
	
	static final Scene FIRST_SCENE = new game.scenes.SceneTitle();				// The first scene to be launched
	public static final WindowManager WINDOW = new WindowManager(640, 480, "Test", "System/S2DIcon.png");	// The Graphic Manager
	//public static final EventManager EVENTS = new EventManager();				// The Event Manager
	public static final DataManager DATA = new DataManager();				// The Data Manager
	public static final SceneManager SCENE = new SceneManager();				// The Scene Manager
	
	/////////////////////////////////////////////////////////////
	
	
	/**
	 * Launch the first game scene and manages what follows.
	 */
	public static void launch() {
		SCENE.handleScene(FIRST_SCENE, false);
		System.exit(0);
	}
}
