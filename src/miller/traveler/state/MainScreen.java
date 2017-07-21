package miller.traveler.state;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.Audio;

import miller.traveler.Graphics;
import miller.traveler.Traveler;

public class MainScreen extends State {
	
	public MainScreen() {
		this.running = false;
	}

	@Override
	public void start() {
		Traveler.open();
		running = true;
		run();
	}

	@Override
	public void run() {
		Audio tick = Traveler.loadSound("res/sounds/tick.wav");
		Color orange = new Color(1, 0.47f, 0);

		boolean transfer = false;
		
		float alpha = 1;
		float dAlpha = -0.01f;
				
		this.startClock();
		
		while (running) {
			if (Display.isCloseRequested()) {
				running = false;
				break;
			}
			
			if (Keyboard.next() || Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
				running = false;
				transfer = true;
				tick.playAsSoundEffect(1.0f, 1.0f, false);
				break;
			}
			
			Graphics.clear();
			alpha += dAlpha;
			
			if (alpha <= 0.5 || alpha >= 1) {
				dAlpha *= -1;
			}
			
			orange.a = alpha;
					
			Graphics.drawString("Press any key to begin", Traveler.papyrus, orange, 900, 625);			
			Display.update();
			Display.sync(60);
		}
		
		if (transfer) {
			transfer();
		} else {
			stop();
		}
	}

	@Override
	public void stop() {
		Traveler.close();
	}
	
	@Override
	public void transfer() {
		//NOTE: This code is temporary. It will be updated when saving is implemented.
		Game game = new Game();
		game.start();
	}
}
