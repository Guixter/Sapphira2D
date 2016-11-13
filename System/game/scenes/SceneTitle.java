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
public class SceneTitle extends Scene {

	/**
 	 * Initializes all that needs to be in the beginning of the scene.
 	 */
 	public void initialize() {
 		WindowManager W = Game.WINDOW;
 		GElement elt = new GText(1, 1, 1, "BONJOUR", Color.WHITE, new Font("SERIF", Font.PLAIN, 20));
 		GAnimation anim = new GAnimation(0, 0, 1, true);
 		anim.addFrame(elt);
 		anim.next();
 		W.add(anim);
 		W.addListener(s2D.windowEngine.Signal.keySignal(s2D.windowEngine.Signal.K_PRESSED), new SListener() {
 			public void onSignal(SCtx c) {
 				SKeyCtx k = c.getKeyCtx();
 				System.out.println(k.getText());
 			}
 		});
 	}
 	
 	/**
 	 * Updates the scene ; is to be called every frame.
 	 */
 	public void update() { }
}
