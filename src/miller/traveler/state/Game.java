package miller.traveler.state;

import org.lwjgl.opengl.Display;

import miller.traveler.Graphics;
import miller.traveler.Traveler;

public class Game extends State {
	
	public Game() {
		running = false;
	}

	@Override
	public void start() {		
		//TODO: Add code here to load before the game begins.
		running = true;
		run();
	}

	@Override
	public void run() {
		this.startClock();
				
		while (running) {
			if (Display.isCloseRequested()) {
				running = false;
				break;
			}
			
			Graphics.clear();
			
			updateFPS(true);
			
			Display.update();
			Display.sync(60);
		}
		
		stop();
	}

	@Override
	public void stop() {
		Traveler.close();
	}

	@Override
	public void transfer() {
		
	}

}
