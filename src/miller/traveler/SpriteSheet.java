package miller.traveler;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class SpriteSheet {
	
	private Texture texture;
	
	private long totalFrames = 0;
	
	private int frames;
	private int currentFrame;
	private int width;
	private int height;
	private int delay;
	
	public SpriteSheet(Texture texture, int frames, int width, int height, int delay) {
		this.texture = texture;
		this.frames = frames;
		this.width = width;
		this.height = height;
		this.delay = delay;
	}
	
	public SpriteSheet(Texture texture, int frames, int width, int height) {
		this(texture, frames, width, height, 10);
	}
	
	public void play(boolean isFacingLeft, float x, float y) {
		int col = isFacingLeft ? 1 : 0;
		
		tick();
		render(currentFrame, col, x, y);
	}
	
	public void render(int row, int col, float x, float y) {
		glEnable(GL_TEXTURE_2D);
		
		float tw = texture.getTextureWidth();
		float th = texture.getTextureHeight();
		
		float xrow = (row * width / tw);
		float ycol = (col * height / th);
		float xOffs = (width / tw);
		float yOffs = (height / th);
		
		x -= (width >> 1);
		y -= (height >> 1);
		
		Color.white.bind();
		texture.bind();
		
		glBegin(GL_QUADS);
			glTexCoord2f(xrow, ycol);
			glVertex2f(x, y);
			glTexCoord2f(xrow + xOffs, ycol);
			glVertex2f(x + width, y);
			glTexCoord2f(xrow + xOffs, ycol + yOffs);
			glVertex2f(x + width, y + height);
			glTexCoord2f(xrow, ycol + yOffs);
			glVertex2f(x, y + height);
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
	}
	
	public void reset() {
		currentFrame = 0;
	}
	
	public void tick() {
		if (++totalFrames % delay == 0) {
			if (++currentFrame >= frames) {
				currentFrame = 0;
			}
		}
	}
	
	public boolean hasCompletedAnimation() {
		return (currentFrame + 1 >= frames);
	}
}
