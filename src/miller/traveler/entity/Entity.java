package miller.traveler.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import miller.traveler.Graphics;

/**An Entity is anything that can be drawn to the screen and should
 * be updated regularly during the game.*/
public abstract class Entity {
	
	protected Texture texture;
	
	protected float x;
	protected float y;
	
	protected int width;
	protected int height;
	
	protected boolean isVisible;
	
	/**Creates an Entity with a desired Texture, location, and size.
	 * @param texture the desired texture
	 * @param x the initial x-position
	 * @param y the initial y-position
	 * @param width the desired width
	 * @param height the desired height */
	public Entity(Texture texture, float x, float y, int width, int height) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isVisible = true;
	}
	
	/**Creates an entity with a desired location and size.
	 * @param x the initial x-position
	 * @param y the initial y-position
	 * @param width the desired width
	 * @param height the desired height */
	public Entity(float x, float y, int width, int height) {
		this(null, x, y, width, height);
	}
	
	/**Updates the Entity's current action.
	 * @param delta the time passed between frames */
	public abstract void update(int delta);
	
	/**Renders the Entity to the screen. By default it will render the Entity's 
	 * Texture unless it does not exist. In that case, it will render a red 
	 * rectangle.*/
	public void render() {
		if (hasTexture()) {
			Graphics.drawTexture(texture, width, height, x, y);
		} else {
			Graphics.drawRectangle(Color.red, width, height, x, y);
		}
	}
	
	/**Determines whether or not this Entity has a Texture. 
	 * @return if such a Texture exists */
	public boolean hasTexture() {
		return (texture != null);
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}