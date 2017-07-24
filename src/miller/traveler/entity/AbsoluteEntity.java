package miller.traveler.entity;

import org.newdawn.slick.opengl.Texture;

/**An AbsoluteEntity is an Entity that does not move with map and instead has an absolute
 * position during the game. */
public abstract class AbsoluteEntity extends Entity {
	
	/**Creates an AbsoluteEntity with a desired Texture, location, and size.
	 * @param texture the desired texture
	 * @param x the initial x-position
	 * @param y the initial y-position
	 * @param width the desired width
	 * @param height the desired height */
	public AbsoluteEntity(Texture texture, float x, float y, int width, int height) {
		super(texture, x, y, width, height);
	}
	
	/**Creates an AbsoluteEntity with a desired Texture, location, and size.
	 * @param x the initial x-position
	 * @param y the initial y-position
	 * @param width the desired width
	 * @param height the desired height */
	public AbsoluteEntity(float x, float y, int width, int height) {
		this(null, x, y, width, height);
	}
}
