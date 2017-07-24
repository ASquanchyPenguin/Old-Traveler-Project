package miller.traveler.entity;

import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;

import miller.traveler.world.World;

/**A BoundedEntity is an Entity that moves with the map. They are movable Entities that
 * may interact with each other. */
public abstract class BoundedEntity extends Entity {
	
	protected Rectangle rectangle;
	
	protected float speed;
	protected float dx;
	protected float dy;
	
	protected boolean ignoreCollision;
	protected boolean isFalling;
	
	/**Creates a BoundedEntity with a desired Texture, location, and size.
	 * @param texture the desired texture
	 * @param x the initial x-position
	 * @param y the initial y-position
	 * @param width the desired width
	 * @param height the desired height */
	public BoundedEntity(Texture texture, float x, float y, int width, int height) {
		super(texture, x, y, width, height);
		
		this.rectangle = new Rectangle((int)x, (int)y, width, height);
		this.speed = 0.25f;
		this.dx = 0;
		this.dy = 0;
		this.ignoreCollision = false;
		this.isFalling = false;
	}
	
	/**Creates a BoundedEntity with a desired Texture, location, and size.
	 * @param x the initial x-position
	 * @param y the initial y-position
	 * @param width the desired width
	 * @param height the desired height */
	public BoundedEntity(float x, float y, int width, int height) {
		this(null, x, y, width, height);
	}
	
	/**Apply friction (from the ground) to the BoundedEntity. */
	public void applyFriction(float friction) {
		if (!isFalling && Math.abs(dx) > 0) {
			dx -= Math.copySign(friction, dx);
		}
	}
	
	/**Apply gravity to the BoundedEntity.*/
	public void applyGravity(float gravity) {
		if (isFalling && (dy < World.TERMINAL_VELOCITY)) {
			dy += gravity;
		}
	}
	
	public void update(int delta) {
		
	}
	
	/**Moves the BoundedEntity's rectangle to its projected location.*/
	public void updateRectangle() {
		rectangle.x = (int) (x + dx);
		rectangle.y = (int) (y + dy);
		rectangle.width = width;
		rectangle.height = height;
	}
	
	/**Translates the BoundedEntity by a small amount. This method is primarily
	 * intended for translating the BoundedEntity after the map has been moved.*/
	public void translate(float dx, float dy) {
		x += dx;
		y += dy;
	}
	
	/**Determines if this BoundedEntity is colliding with another. It will return false
	 * if the argument is itself or either BoundedEntity is ignoring collision.
	 * @return if a collision has happened */
	public boolean isCollidingWith(BoundedEntity e) {
		if (e == this) {
			return false;
		} else if (ignoreCollision || e.ignoreCollision) {
			return false;
		} else {
			return (rectangle.intersects(e.rectangle));
		}
	}
}