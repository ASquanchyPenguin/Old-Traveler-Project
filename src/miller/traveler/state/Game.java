package miller.traveler.state;

import java.io.FileNotFoundException;

import org.lwjgl.opengl.Display;

import miller.traveler.Graphics;
import miller.traveler.Traveler;
import miller.traveler.entity.Player;
import miller.traveler.tiles.TileMap;

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
		
		Player player = new Player(32, 656);
		TileMap map = null;
		
		try {
			map = new TileMap("res/maps/basic/", "test.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int dt;
		
		while (running) {
			if (Display.isCloseRequested()) {
				running = false;
				break;
			}
			
			Graphics.clear();
			updateFPS(true);
			dt = getDelta();
			
			player.pollInput(dt);
			map.followEntity(player, dt);
			player.update(dt);
			map.renderMap();
			player.render();
			
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
