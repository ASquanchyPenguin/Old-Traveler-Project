package miller.traveler.tiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.newdawn.slick.opengl.Texture;

import miller.traveler.Traveler;

public class TileMap {

	protected Tile[][] map;
	
	protected int rows;
	protected int cols;
	protected int rowLimit;
	protected int colLimit;
	
	protected double rowMin;
	protected double colMin;
	
	/**Creates the default TileMap. */
	private TileMap(int rowLimit, int colLimit) {
		this.rowLimit = rowLimit;
		this.colLimit = colLimit;
		this.rowMin = 0;
		this.colMin = 0;
	}
	
	/**Creates a TileMap with a random map.*/
	public TileMap() {
		this(40, 23);
		
		this.rows = 50;
		this.cols = 30;
		this.rowMin = 0;
		this.colMin = 0;
		
		generateRandomMap();
	}
	
	/**Creates a TileMap from a file.
	 * @param file the folder containing the text-document and tiles folder
	 * @param name the name of the text-document
	 * @throws FileNotFoundException */
	public TileMap(String folder, String name) throws FileNotFoundException {
		this(40, 23);
		
		generateMapFromFile(folder, name);
	}
	
	/**Returns the Tile currently being drawn at the specified location.
	 * @param x the x-position of the tile
	 * @param y the y-position of the tile */
	public Tile getTileAt(float x, float y) {
		return map[(int)(x / 32)][(int) y / 32];
	}
	
	/**Generates a map from a text file.
	 * @param folder the folder containing the text-document and tiles folder
	 * @param name the name of the text-document
	 * @throws FileNotFoundException when the text file could not be found */
	public void generateMapFromFile(String folder, String name) throws FileNotFoundException {
		//Load the textures from their subfolder
		File sub = new File(folder + "tiles/");
		String[] list = sub.list();
		Texture[] textures = new Texture[list.length];
		int[] types = new int[list.length];
		
		//Set the textures and their types
		String path = sub.getPath().concat("/");
		String index;
		for (int i = 0; i < list.length; i++) {
			index = list[i];
			textures[i] = Traveler.loadTexture(path + index);
			types[i] = Integer.parseInt(index.split("\\W")[1]);
		}
		
		//Load the text file to scan
		File text = new File(folder + name);
		Scanner scanner = new Scanner(text);

		//Set local variables with the first line
		String[] input = scanner.nextLine().split("\\W");
		rows = Integer.parseInt(input[1]);
		cols = Integer.parseInt(input[2]);
		map = new Tile[rows][cols];
		
		//Set the rest of the data for the map
		int i = 0;
		int r = 0;
		int c = 0;
		TileType[] values = TileType.values();
		while (scanner.hasNextInt()) {
			i = scanner.nextInt();
			
			if (i == 0) {
				map[r][c] = new Tile();
			} else {
				map[r][c] = new Tile(textures[(i-1)], values[types[i-1]]);
			}
			
			if (++r >= rows) {
				r = 0;
				++c;
			}
		}
		
		scanner.close();
	}
	
	
	/**Generates a random map with default tiles*/
	public void generateRandomMap() {
		Texture[] textures = new Texture[] {
				Traveler.loadTexture("res/maps/basic/tiles/basic1.png"),
				Traveler.loadTexture("res/maps/basic/tiles/basic2.png"),
				Traveler.loadTexture("res/maps/basic/tiles/basic3.png")
			};
			
		this.map = new Tile[rows][cols];

		int rand = 0;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				rand = (int)(Math.random() * 4);
				
				switch (rand) {
					case 0:
						map[r][c] = new Tile();
					break;
					
					case 1:
						map[r][c] = new Tile(textures[0], TileType.NO_EFFECT);
					break;
					
					case 2:
						map[r][c] = new Tile(textures[1], TileType.NO_EFFECT);
					break;
					
					case 3:
						map[r][c] = new Tile(textures[2], TileType.BLOCKED);
					break;
				}				
			}
		}
	}
	
	/**Renders the TileMap to the Display.*/
	public void renderMap() {		
		int rowOffs = (int) rowMin;
		int colOffs = (int) colMin;
		
		for (int r = 0; r < rowLimit; r++) {
			for (int c = 0; c < colLimit; c++) {
				map[r + rowOffs][c + colOffs].render((r * 32), (c * 32));
			}
		}
	}
	
	/**Moves the TileMap in such a way that it renders around a single entity.
	 * <br><br>
	 * ***<strong>Note</strong>: This function will be updated when Entities are created.
	 *          Additionally, it is likely that a "shift" value will be needed
	 *          when moving the map like this to update all other entities on
	 *          the map. 
	 * <br><br>
	 * @param delta the time passed between frames */
	public void followEntity(float x, float y, float dx, float dy, int delta) {
		float xabs = Math.abs(x - Traveler.WIDTH / 2);
		float yabs = Math.abs(y - Traveler.HEIGHT / 2);
		
		if (isRowLocked()) {
			x += dx;
		} 
		
		if (isColumnLocked()) {
			y += dy;
		}
		
		if (xabs > 1 && xabs < 16) {
			rowMin += (dx / delta);
		}
		
		if (yabs > 1 && yabs < 16) {
			colMin += (dx / delta);
		}
		
		validateBounds();
	}
		
	/**Validates that the bounds for the TileMap are within the range of the array.*/
	public void validateBounds() {
		rowMin = (rowMin <= 0) ? 0 : rowMin;
		rowMin = (rowMin + rowLimit >= rows) ? (rows - rowLimit) : rowMin;
		colMin = (colMin <= 0) ? 0 : colMin;
		colMin = (colMin + colLimit >= cols) ? (cols - colLimit) : colMin;
	}
	
	public boolean isColumnLocked() {
		return (colMin <= 0 || (colMin + colLimit >= cols));
	}
	
	public boolean isRowLocked() {
		return (rowMin <= 0 || (rowMin + rowLimit) >= rows);
	}
}
