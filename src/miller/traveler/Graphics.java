package miller.traveler;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

public class Graphics {
	
	private Graphics() {
		
	}
	
	/**Clears all graphics currently drawn to the screen. */
	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	/**Draws a rectangle to the screen with a specified color, size, and location. 
	 * @param color the desired color of the rectangle
	 * @param width the desired width of the rectangle
	 * @param height the desired height of the rectangle
	 * @param the x-position of the rectangle
	 * @param the y-position of the rectangle */
	public static void drawRectangle(Color color, int width, int height, float x, float y) {
		glColor4f(color.r, color.g, color.b, color.a);
		
		glBegin(GL_QUADS);
			glVertex2f(x, y);
			glVertex2f(x + width, y);
			glVertex2f(x + width, y + height);
			glVertex2f(x, y + height);
		glEnd();
	}
	
	/**Draws a string to the screen with a specified font, color, and location. 
	 * @param text the string to appear on the screen
	 * @param font the font the string will appear in
	 * @param color the color the string will appear in
	 * @param x the x-position of the text
	 * @param y the y-position of the text */
	public static void drawString(String text, TrueTypeFont font, Color color, float x, float y) {
		glEnable(GL_TEXTURE_2D);
		font.drawString(x, y, text, color);
		glDisable(GL_TEXTURE_2D);
	}
	
	/**Draws a white string to the screen with a specified font and location. 
	 * @param text the string to appear on the screen
	 * @param font the font the string will appear in
	 * @param x the x-position of the text
	 * @param y the y-position of the text */
	public static void drawString(String text, TrueTypeFont font, float x, float y) {
		drawString(text, font, Color.white, x, y);
	}
	
	/**Draws a white string to the screen in the courier font at a specified location. 
	 * @param text the string to appear on the screen
	 * @param x the x-position of the text
	 * @param y the y-position of the text */
	public static void drawString(String text, float x, float y) {
		drawString(text, Traveler.courier, Color.white, x, y);
	}
	
	/**Draws a texture to the screen at a specified location.
	 * @param texture the texture to be drawn
	 * @param the width of the texture
	 * @param the height of the texture
	 * @param the x-position of the texture
	 * @param the y-position of the texture */
	public static void drawTexture(Texture texture, int width, int height, float x, float y) {
		glEnable(GL_TEXTURE_2D);
			
		Color.white.bind();
		texture.bind();
			
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(1, 0);
			glVertex2f(x + width, y);
			glTexCoord2f(1, 1);
			glVertex2f(x + width, y + height);
			glTexCoord2f(0, 1);
			glVertex2f(x, y + height);
		glEnd();
			
		glDisable(GL_TEXTURE_2D);
	}
}
