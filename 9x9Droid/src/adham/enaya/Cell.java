package adham.enaya;

import java.util.ArrayList;

public class Cell {

	int id;

	int coorX;
	int coorY;

	public double x1; // left
	public double y1; // top

	public double x2; // right
	public double y2; // bottom

	public int isActive = 0; // 0:not active 1:active
	public int isFilled = 0; // 0:free 1:filled

	public float height = 0;
	public float width = 0;

	public CellColor mColor;

	CellView cv;

	void setCellView(CellView cv) {
		this.cv = cv;
	}
	public boolean isActive() {
		return (isActive == 1 ? true : false);
	}
	public boolean isFilled() {
		return (isFilled == 1 ? true : false);
	}
	public Cell getRight(ArrayList<Cell> cells, Cell c) {
		int shift = Math.abs(3 - c.coorY);
		if (c.coorY == 3)
			shift = 1;
		return getNeighbourCell(cells, c.coorX, c.coorY, Direction.RIGHT, shift);
	}
	public Cell getLeft(ArrayList<Cell> cells, Cell c) {
		int shift = Math.abs(3 - c.coorY);
		if (c.coorY == 3)
			shift = 1;
		return getNeighbourCell(cells, c.coorX, c.coorY, Direction.LEFT, shift);

	}
	public Cell getTop(ArrayList<Cell> cells, Cell c) {
		int shift = Math.abs(3 - c.coorX);
		if (c.coorX == 3)
			shift = 1;
		return getNeighbourCell(cells, c.coorX, c.coorY, Direction.TOP, shift);

	}
	public Cell getBottom(ArrayList<Cell> cells, Cell c) {
		int shift = Math.abs(3 - c.coorX);
		if (c.coorX == 3)
			shift = 1;
		return getNeighbourCell(cells, c.coorX, c.coorY, Direction.BOTTOM,
				shift);

	}
	public Cell getCellByCoords(ArrayList<Cell> cells, int x, int y) {
		for (int i = 0; i < cells.size(); i++) {

			if (cells.get(i).coorX == x && cells.get(i).coorY == y) {
				return cells.get(i);
			}

		}
		return null;
	}
	public Cell getNeighbourCell(ArrayList<Cell> cells, int x, int y,
			Direction direction, int shift) {
		int x0 = 0, y0 = 0;

		switch (direction) {
		case LEFT:// 0:left
			x0 = x - shift;
			y0 = y; break;
		case RIGHT:// 1:right
			x0 = x + shift;
			y0 = y;break;
		case TOP:// 2:top
			x0 = x;
			y0 = y - shift;break;
		case BOTTOM:// 3:bottom
			x0 = x; 
			y0 = y + shift;break;
		}
		for (int i = 0; i < cells.size(); i++) {

			if (cells.get(i).coorX == x0 && cells.get(i).coorY == y0
					&& cells.get(i).isActive()) {
 				
				return cells.get(i);
			}
		}
		return null;
	}
}
