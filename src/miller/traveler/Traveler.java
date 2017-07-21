package miller.traveler;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import miller.traveler.state.MainScreen;

public class Traveler {
	
	public static TrueTypeFont papyrus;
	public static TrueTypeFont courier;
	
	public static final String TITLE = "Traveler";
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public static void main(String[] args) {
		MainScreen main = new MainScreen();
		main.start();
	}
	
	/**Initializes the display to be ready to use.*/
	public static void initDisplay() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		Display.setTitle(TITLE);
		Display.setInitialBackground(0.07f, 0.09f, 0.14f);
		Display.create();
	}
	
	/**Initializes OpenGL*/
	public static void initGL() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	/**Loads the default fonts into the program.*/
	public static void loadDefaultFonts() {
		papyrus = loadFont(new Font("Papyrus", Font.PLAIN, 24));
		courier = loadFont(new Font("Courier", Font.PLAIN, 12));
	}
	
	/**Loads and sets the icons for the Display.*/
	public static void loadIcons() throws IOException {
		ByteBuffer[] list = new ByteBuffer[2];
		
		list[0] = createByteBuffer(ImageIO.read(new File("res/icon16.png")));
		list[1] = createByteBuffer(ImageIO.read(new File("res/icon32.png")));

		Display.setIcon(list);
	}
	
	/**Opens Traveler by performing all necessary start-up tasks.*/
	public static void open() {
		try {
			loadIcons();
			initDisplay();
			initGL();
			loadDefaultFonts();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**Closes Traveler and exits the program.*/
	public static void close() {
		AL.destroy();
		System.exit(0);
	}
	
	/**Load a sound into the game.
	 * @param path the path to the desired sound.*/
	public static Audio loadSound(String path) {
		Audio audio = null;
		
		try {
			audio = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return audio;
	}
	
	/**Creates a ByteBuffer from a BufferedImage.*/
	public static ByteBuffer createByteBuffer(BufferedImage img) {
		return new ImageIOImageData().imageToByteBuffer(img, false, false, null);
	}
	
	/**Load a font found on most systems.*/
	public static TrueTypeFont loadFont(Font font) {
		return new TrueTypeFont(font, false);
	}
	
	/**Loads and returns a texture.
	 * @param path the path to the texture
	 * @return the texture found at that path */
	public static Texture loadTexture(String path) {
		Texture texture = null;
		
		try {
			InputStream stream = ResourceLoader.getResourceAsStream(path);
			texture = TextureLoader.getTexture("png", stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return texture;
	}
}