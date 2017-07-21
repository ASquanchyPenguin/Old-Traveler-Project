package miller.traveler.state;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import miller.traveler.Graphics;
import miller.traveler.Traveler;

public class Game extends State {
	
	public Game() {
		running = false;
	}

	@Override
	public void start() {
		Color orange = new Color(1, 0.47f, 0);
		Display.setTitle("Traveler | FPS: Calculating... | Current Time: 0");
		
		//TODO: Add code here to load before the game begins.
		running = true;
		this.startClock();		
				
		while (!(fps >= 60)) {
			updateFPS(false);
			
			Graphics.clear();
			Graphics.drawString("Loading...", Traveler.papyrus, orange, 900, 625);
			
			Display.update();
		}
		
		run();
	}

	@Override
	public void run() {
		this.resetClock();
		
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
