package miller.traveler.entity;

import java.awt.Rectangle;

import org.newdawn.slick.opengl.Texture;

import miller.traveler.tiles.Tile;
import miller.traveler.tiles.TileMap;
import miller.traveler.world.World;

/**A BoundedEntity is an Entity that moves with the map. They are movable Entities that
 * may interact with each other. */
public abstract class BoundedEntity extends Entity {
	
	protected Rectangle rectangle;
	protected TileMap map;
	
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
			//Fixes the sliding issue when dx is small
			if (Math.abs(dx) < 0.15f) {
				dx = 0;
			} else {
				dx -= Math.copySign(friction, dx);
			}
		}
	}
	
	/**Apply gravity to the BoundedEntity.*/
	public void applyGravity(float gravity) {
		if (isFalling && (dy < World.TERMINAL_VELOCITY)) {
			dy += gravity;
		}
	}
	
	public Tile getBottomTile() {
		return (map.getTileAt((x + dx) - (width / 2), (y + dy) + (height / 2)));
	}
	
	public Tile getLeftTile() {
		return (map.getTileAt((x + dx) - (width * 1.5f), (y + dy)));
	}
	
	public Tile getRightTile() {
		return (map.getTileAt((x + dx) - (width / 2), (y + dy)));
	}
	
	public Tile getUpperTile() {
		return (map.getTileAt((x + dx) - (width / 2), (y + dy) - height));
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

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public TileMap getMap() {
		return map;
	}

	public void setMap(TileMap map) {
		this.map = map;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getDX() {
		return dx;
	}

	public void setDX(float dx) {
		this.dx = dx;
	}

	public float getDY() {
		return dy;
	}

	public void setDY(float dy) {
		this.dy = dy;
	}

	public boolean ignoreCollision() {
		return ignoreCollision;
	}

	public void ignoreCollision(boolean ignoreCollision) {
		this.ignoreCollision = ignoreCollision;
	}

	public boolean isFalling() {
		return isFalling;
	}

	public void setFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}
}