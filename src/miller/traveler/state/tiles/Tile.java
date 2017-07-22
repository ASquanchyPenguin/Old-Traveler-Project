package miller.traveler.state.tiles;

import org.newdawn.slick.opengl.Texture;

import miller.traveler.Graphics;

public class Tile {
	
	protected Texture texture;
	protected TileType type;
	
	protected int width;
	protected int height;
	
	/**Creates a tile with a given texture, type, and size. 
	 * @param texture the on-screen representation for this tile
	 * @param type the desired type of the tile
	 * @param width the desired width
	 * @param height the desired height */
	public Tile(Texture texture, TileType type, int width, int height) {
		this.texture = texture;
		this.type = type;
		this.width = width;
		this.height = height;
	}
	
	/**Creates a tile with a given texture and type but default size (32x32).
	 * @param texture the on-screen representation for this tile
	 * @param type the desired type of this tile */
	public Tile(Texture texture, TileType type) {
		this(texture, type, 32, 32);
	}
	
	/**Creates a blank tile with default size (32x32).*/
	public Tile() {
		this(null, TileType.BLANK);
	}
	
	/**Draws the Tile to screen if it is not blank.
	 * @param x the x-position
	 * @param y the y-position */
	public void render(float x, float y) {
		if (type == TileType.BLANK) {
			return;
		}
		
		Graphics.drawTexture(texture, width, height, x, y);
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
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

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}
}
