package s2D.sceneEngine;

import java.util.*;
import java.lang.*;
import s2D.*;

/**
 * A Scene Manager.
 * @author Guillaume Singland
 * @version 2.0
 */
public class SceneManager {
	
	static final int FRAME_GAP = 20;	// The gap between two frames (in ms)
	
	/////////////////////////////////////////////////
	
	private List<Thread> list;		// The list of the current threads
	
	/////////////////////////////////////////////////
	
	/**
	 * Create a SceneManager.
	 */
	public SceneManager() {
		this.list = new ArrayList<Thread>();
	}
	
	/**
	 * Handle the scene, from its beginning to its end.
	 * @param scene The scene to handle
	 * @param relaunch Whether the scene is relaunched (after a pause) or not
	 */
	public void handleScene(Scene scene, boolean relaunch) {
		try {
			// Initialize the scene
			Game.DATA.clearCache();
			if (relaunch) {
			
			} else {
				scene.initialize();
			}
		
			// Launch all the threads
			list.add(new Thread(new SceneUpdater(scene)));
			list.add(new Thread(new GraphicUpdater(scene)));
			list.add(new Thread(new EventUpdater(scene)));
			list.add(new Thread(new SoundUpdater(scene)));
			for (Thread t : list) {
				t.start();
			}
		
			// Wait for the threads to finish (ie the scene paused/stopped)
			for (Thread t : list) {
				t.join();
			}
			list.clear();
		
			// Handle the end or the pause of the scene
			if (scene.isPaused()) {
				handleScene(scene.nextScene(), false);
				scene.unpause();
				handleScene(scene, true);
			} else {
				if (scene.nextScene() != null) {
					handleScene(scene.nextScene(), false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////
	
	/**
	 * Wait some time.
	 * @param millis The number of milliseconds to wait
	 */
	private static void sleep(long millis) {
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < millis);
	}
	
	/**
	 * The Scene updater.
	 */
	class SceneUpdater implements Runnable {
		private Scene scene;
		public SceneUpdater(Scene scene) {
			this.scene = scene;
		}
		public void run() {
			while (scene.isRunning()) {
				long tic = System.currentTimeMillis();
				scene.update();
				tic = System.currentTimeMillis() - tic;
				if (tic < FRAME_GAP) {
					sleep(FRAME_GAP - tic);
				}
			}
		}
	}
	
	/**
	 * The Graphic updater.
	 */
	class GraphicUpdater implements Runnable {
		private Scene scene;
		public GraphicUpdater(Scene scene) {
			this.scene = scene;
		}
		public void run() {
			while (scene.isRunning()) {
				long tic = System.currentTimeMillis();
				Game.WINDOW.update();
				tic = System.currentTimeMillis() - tic;
				if (tic < FRAME_GAP) {
					sleep(FRAME_GAP - tic);
				}
			}
		}
	}
	
	/**
	 * The Event updater.
	 */
	class EventUpdater implements Runnable {
		private Scene scene;
		public EventUpdater(Scene scene) {
			this.scene = scene;
		}
		public void run() {
			
		}
	}
	
	/**
	 * The Sound updater.
	 */
	class SoundUpdater implements Runnable {
		private Scene scene;
		public SoundUpdater(Scene scene) {
			this.scene = scene;
		}
		public void run() {
			
		}
	}
}
