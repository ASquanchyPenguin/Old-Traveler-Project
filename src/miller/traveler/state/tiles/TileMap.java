package miller.traveler.state.tiles;

public class TileMap {

	protected Tile[][] map;
	
	protected int rows;
	protected int cols;
	protected int rowLimit;
	protected int colLimit;
	
	protected double rowMin;
	protected double colMin;
	
	public void renderMap() {		
		int rowOffs = (int) rowMin;
		int colOffs = (int) colMin;
		
		for (int r = 0; r < rowLimit; r++) {
			for (int c = 0; c < colLimit; c++) {
				map[r + rowOffs][c + colOffs].render((r * 32), (c * 32));
			}
		}	
	}
}
