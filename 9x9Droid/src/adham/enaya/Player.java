package adham.enaya;

import java.util.ArrayList;

public class Player {

	public ArrayList<Cell> mCells = new ArrayList<Cell>();
	public ArrayList<Cell> mEarnedmPieces = new ArrayList<Cell>();
	public boolean hasTrun = false;

	public void setCells(ArrayList<Cell> mCells) {
		this.mCells = mCells;
	}

	public void insertCell(Cell p) {
		mCells.add(p);
	}

	public void replaceCells(Cell source, Cell destination) {
		for (int i = 0; i < mCells.size(); i++) {
			if (mCells.get(i).coorX == source.coorX
					&& mCells.get(i).coorY == source.coorY) {
				mCells.get(i).coorX = destination.coorX;
				mCells.get(i).coorY = destination.coorY;
				mCells.get(i).x1 = destination.x1;
				mCells.get(i).y1 = destination.y1;
				mCells.get(i).x2 = destination.x2;
				mCells.get(i).y2 = destination.y2;

			}
		}
	}

	public void removeCell(Cell cell) {
		for (int i = 0; i < mCells.size(); i++) {
			if (mCells.get(i).coorX == cell.coorX
					&& mCells.get(i).coorY == cell.coorY) {

				Cell c = mCells.remove(i);
				return;
			}
		}
	}

	public void insertEarn(Cell c) {
		mEarnedmPieces.add(c);
	}

	public int getEarnCount() {
		return mEarnedmPieces.size();
	}

	public boolean checkScore(Board borad, Cell lastCellMoved) {
		boolean hasScore = false;

		if (mCells.size() > 2) {

			Cell rightCell = lastCellMoved.getRight(mCells, lastCellMoved);
			Cell leftCell = lastCellMoved.getLeft(mCells, lastCellMoved);
			Cell topCell = lastCellMoved.getTop(mCells, lastCellMoved);
			Cell bottomCell = lastCellMoved.getBottom(mCells, lastCellMoved);

			// Check the score horizontally
			if (rightCell != null && leftCell != null) {
				// The cell is in the center
				hasScore = Equal3(leftCell.coorY, lastCellMoved.coorY,
						rightCell.coorY);

				if (hasScore) {
					borad.colorScore(leftCell, lastCellMoved, rightCell);
					return true;
				} else {
					borad.clearScore(leftCell, lastCellMoved, rightCell);
				}
			} else {
				if (rightCell != null) {
					// The cell in the left(move two right)
					Cell farRightCell = new Cell();
					farRightCell = farRightCell.getRight(mCells, rightCell);
					if (farRightCell != null) {

						hasScore = Equal3(lastCellMoved.coorY, rightCell.coorY,
								farRightCell.coorY);
						if (hasScore) {
							borad.colorScore(lastCellMoved, rightCell,
									farRightCell);
							return true;
						} else {
							borad.clearScore(lastCellMoved, rightCell,
									farRightCell);
						}
					}

				} else if (leftCell != null) {
					// The cell in the right (move tow left)
					Cell farLeftCell = new Cell();
					farLeftCell = farLeftCell.getLeft(mCells, leftCell);
					if (farLeftCell != null) {
						hasScore = Equal3(lastCellMoved.coorY, leftCell.coorY,
								farLeftCell.coorY);

						if (hasScore) {
							borad.colorScore(lastCellMoved, leftCell,
									farLeftCell);
							return true;
						} else {
							borad.clearScore(lastCellMoved, leftCell,
									farLeftCell);
						}
					}
				}
			}

			if (!hasScore) {
				// Check the score vertically
				if (topCell != null && bottomCell != null) {
					// The cell is in the center
					hasScore = Equal3(topCell.coorX, lastCellMoved.coorX,
							bottomCell.coorX);
					if (hasScore) {
						borad.colorScore(topCell, lastCellMoved, bottomCell);
					} else {
						borad.clearScore(topCell, lastCellMoved, bottomCell);
					}

				} else {
					if (topCell != null) {
						// The cell in the left(move two right)
						Cell farTopCell = new Cell();
						farTopCell = farTopCell.getTop(mCells, topCell);
						if (farTopCell != null) {
							hasScore = Equal3(lastCellMoved.coorX,
									topCell.coorX, farTopCell.coorX);
							if (hasScore) {
								borad.colorScore(lastCellMoved, topCell,
										farTopCell);
								return true;

							} else {
								borad.clearScore(lastCellMoved, topCell,
										farTopCell);
							}
						}

					} else if (bottomCell != null) {
						// The cell in the right (move tow left)
						Cell farBottomCell = new Cell();
						farBottomCell = farBottomCell.getBottom(mCells,
								bottomCell);
						if (farBottomCell != null) {
							hasScore = Equal3(lastCellMoved.coorX,
									bottomCell.coorX, farBottomCell.coorX);
							if (hasScore) {
								borad.colorScore(lastCellMoved, bottomCell,
										farBottomCell);
								return true;
							} else {
								borad.clearScore(lastCellMoved, bottomCell,
										farBottomCell);
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean Equal3(int i1, int i2, int i3) {
		if (i1 == i2)
			if (i2 == i3)
				return true;
			else
				return false;
		else
			return false;
	}
}
