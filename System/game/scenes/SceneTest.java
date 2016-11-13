package game.scenes;

import s2D.*;
import s2D.sceneEngine.*;
import s2D.windowEngine.*;
import java.awt.*;

/**
 * La scene de l'ecran titre.
 * @author Guillaume Singland
 * @version 2.0
 */
public class SceneTest extends Scene {

	/**
 	 * Initializes all that needs to be in the beginning of the scene.
 	 */
 	public void initialize() {
 		WindowManager G = Game.WINDOW;
 		G.clear();
 		GraphicImage image = new GraphicImage("System/title.png", 0, 0, 1);
 		G.add(image);
 	}
 	
 	/**
 	 * Updates the scene ; is to be called every frame.
 	 */
 	public void update() { }
}
