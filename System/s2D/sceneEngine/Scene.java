package s2D.sceneEngine;

import java.lang.*;

/**
 * Specifies a game scene.
 * @author Guillaume Singland
 * @version 2.0
 */
public abstract class Scene {
	
	private boolean paused;
	private boolean running;
	private Scene nextScene;

	/////////////////////////////////////////////////
	
	/**
 	 * Build a scene.
 	 */
	public Scene() {
		paused = false;
		running = true;
		nextScene = null;
	}
	
	/////////////////////////////////////////////////
 	
 	/**
 	 * Initializes all that needs to be in the beginning of the scene.
 	 */
 	public abstract void initialize();
 	
 	/**
 	 * Updates the scene ; is to be called every frame.
 	 */
 	public abstract void update();
 	
 	/////////////////////////////////////////////////
 	
 	/**
 	 * Pause the scene and launch another one.
 	 * @param scene The new scene (can't be null)
 	 */
 	protected final void pause(Scene scene) {
 		if (scene == null) {
 			throw new IllegalArgumentException("Trying to pause a scene with giving a null new scene.");
 		}
 		nextScene = scene;
 		paused = true;
 		running = false;
 	}
 	
 	/**
 	 * Stop the pause of the scene.
 	 */
 	final void unpause() {
 		paused = false;
 		running = true;
 	}
 	
 	/**
 	 * End the scene and launch another one if needed.
 	 * @param scene The new scene (can be null)
 	 */
 	protected final void terminate(Scene scene) {
 		nextScene = scene;
 		running = false;
 	}
 	
 	/////////////////////////////////////////////////
 	
 	/**
 	 * Whether the scene is paused or not.
 	 */
 	final boolean isPaused() {
 		return paused;
 	}
 	
 	/**
 	 * Whether the scene is running or not.
 	 */
 	final boolean isRunning() {
 		return running;
 	}
 	
 	/**
 	 * Get the next scene of this one.
 	 */
 	final Scene nextScene() {
 		return nextScene;
 	}
 }
