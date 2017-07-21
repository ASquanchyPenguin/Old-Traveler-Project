package miller.traveler.state;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import miller.traveler.Traveler;

public abstract class State {
	
	protected boolean running;
	
	protected int fps;
	protected long seconds;
	protected long lastFrame;
	protected long targetTime;
	
	/**Prepares the current state to run.*/
	public abstract void start();
	
	/**Updates the current state while it is active.*/
	public abstract void run();
	
	/**Stops the current state.*/
	public abstract void stop();
	
	/**Transfers the program to another state.*/
	public abstract void transfer();
	
	/**Starts the clock to count FPS and seconds.*/
	public void startClock() {
		getDelta();
		targetTime = getTime();
	}
	
	/**Resets and restarts the clock.*/
	public void resetClock() {
		fps = 0;
		seconds = 0;
		lastFrame = 0;
		startClock();
	}
	
	/**Calculates the current FPS the game is running at and updates seconds.
	 * @param updateTitle show the fps and seconds in Display. */
	public void updateFPS(boolean updateTitle) {
		if (getTime() - targetTime > 1000) {
			if (updateTitle) {
				Display.setTitle(String.format("%s | FPS: %d | Current Time: %d", Traveler.TITLE, fps, ++seconds));
			}
			
			fps = 0;
			targetTime += 1000;
		}
		
		fps++;
	}
	
	/**Calculates how much time passed between frames.*/
	public int getDelta() {
		long time = getTime();
		int dt = (int)(time - lastFrame);
		lastFrame = time;
		
		return dt;
	}
	
	/**Returns the current system time in milliseconds.*/
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
}
