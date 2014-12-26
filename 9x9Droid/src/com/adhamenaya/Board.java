package com.adhamenaya;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

public class Board {

	ArrayList<Cell> cells = new ArrayList<Cell>();
	public Player mBlackPlayer, mWhitePlayer;

	public int noOfCells = 7;
	public int cellShift = 0;
	double d = 0.0;
	int counter = 0, coorX = 0, coorY = 0;
	private Context cx;

	int black = 9;
	int whtie = 9;

	int cellCounter = 1;
	private CellView innerMovingCellView = null;
	private Cell sourceCell;
	private Cell destinationCell;
	private int shift;
	private boolean mWhitePick = false;
	private boolean mBlackPick = false;
	private FrameLayout lytBoard;
	private Board mBoard = null;
	private UpdatePlayer mCallBack;

	public Board(UpdatePlayer callBack, Display display, Context cx,
			FrameLayout lytBoard) {
		mBlackPlayer = new Player();
		mWhitePlayer = new Player();
		calculateAllCells(display);
		calculateActiveCellsVertically();
		calculateActiveCellsHorizontally();

		this.cx = cx;
		this.mBoard = this;
		this.lytBoard = lytBoard;
		this.mCallBack = callBack;
	}

	/*
	 * getCell: This function to find the cell that the user touches according
	 * the x and y coordinates)
	 * -----------------------------------------------------------------------
	 * x: the x coordinates of the touch point -------------------------------
	 * y: the y coordinates of the touch point -------------------------------
	 */
	/*
	 * public Cell getCell(double x, double y) { for (int i = 0; i <
	 * cells.size(); i++) { if (x > cells.get(i).x1 && y > cells.get(i).y1 && x
	 * < cells.get(i).x2 && y < cells.get(i).y2) { return cells.get(i); } }
	 * return null; }
	 */

	public void colorScore(Cell c1, Cell c2, Cell c3) {
		if (getBoardCell(c1) != null)
			getBoardCell(c1).cv.setBackgroundColor(Color.GREEN);
		if (getBoardCell(c2) != null)
			getBoardCell(c2).cv.setBackgroundColor(Color.GREEN);
		if (getBoardCell(c3) != null)
			getBoardCell(c3).cv.setBackgroundColor(Color.GREEN);
	}

	public void clearScore(Cell c1, Cell c2, Cell c3) {
		if (getBoardCell(c1) != null)
			getBoardCell(c1).cv.setBackgroundColor(Color.YELLOW);
		if (getBoardCell(c2) != null)
			getBoardCell(c2).cv.setBackgroundColor(Color.YELLOW);
		if (getBoardCell(c3) != null)
			getBoardCell(c3).cv.setBackgroundColor(Color.YELLOW);
	}

	private Cell getBoardCell(Cell topCell) {
		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i).coorX == topCell.coorX
					&& cells.get(i).coorY == topCell.coorY)
				return cells.get(i);
		}
		return null;
	}

	public Cell getCell(double x, double y) {

		int margin = (int) Math.ceil(cells.get(0).width * 0.3);// 30%
		for (int i = 0; i < cells.size(); i++) {
			if (x > cells.get(i).x1 - margin && y > cells.get(i).y1 - margin
					&& x < cells.get(i).x2 && y < cells.get(i).y2) {
				return cells.get(i);
			}
		}
		return null;
	}

	public Cell getHead(double x, double y) {
		for (int i = 0; i < cells.size(); i++) {
			if (getDistance(x, y, cells.get(i).x1, cells.get(i).y1) <= (d / Math
					.ceil(((double) noOfCells / 2.0)))) {
				return cells.get(i);
			}
		}
		return null;
	}

	/*
	 * getDistance: This function to get the distance between two points on the
	 * board
	 */
	public double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public boolean isFilled(Cell c) {
		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i).coorX == c.coorX && cells.get(i).coorY == c.coorY) {
				if (cells.get(i).isFilled())
					return true;
			}
		}
		return false;
	}

	public void setFilled(int coorX, int coorY, int status) {
		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i).coorX == coorX && cells.get(i).coorY == coorY) {
				cells.get(i).isFilled = status;
				return;
			}
		}
	}

	private void calculateAllCells(Display display) {
		int width = display.getWidth();
		d = width / noOfCells;
		Log.d("d", d + "");
		for (int x = 0; x <= width - d; x += d) {
			coorY = 0;
			for (int y = 0; y <= width - d; y += d) {

				Cell cell = new Cell();
				cell.x1 = x;
				cell.y1 = y;

				cell.x2 = x + d;
				cell.y2 = y + d;

				cell.id = counter;
				cell.coorX = coorX;
				cell.coorY = coorY;
				cells.add(cell);
				counter++;
				coorY++;
			}
			coorX++;
		}
	}

	/*
	 * calculateActiveCellsVertically: This function to find the active
	 * cells(the allowed cells that can the player add his piece inside it
	 * vertically)
	 */
	public void calculateActiveCellsVertically() {

		int half = noOfCells / 2;// 3
		for (int i = 0; i < half; i++) {
			for (int j = 0; j < cells.size(); j++) {
				Cell c = cells.get(j);

				if (c.coorX >= i && c.coorY >= i
						&& c.coorX <= ((noOfCells - 1) - i)
						&& c.coorY <= ((noOfCells - 1) - i)) {

					if (c.coorX == i || c.coorX == ((noOfCells - 1) - i)) {
						if ((c.coorY % (half - i)) == (i % 2))
							cells.get(j).isActive = 1;
						else
							cells.get(j).isActive = 0;
					}
				}
			}// The end of the inner loop
		}// The outer outer loop
	}

	/*
	 * calculateActiveCellsVertically: This function to find the active
	 * cells(the allowed cells that can the player add his piece inside it
	 * horizontally)
	 */
	public void calculateActiveCellsHorizontally() {

		int half = noOfCells / 2;
		for (int i = 0; i < half; i++) {
			for (int j = 0; j < cells.size(); j++) {
				Cell c = cells.get(j);

				if (c.isActive == 0) {

					if (c.coorY >= i && c.coorX >= i
							&& c.coorY <= ((noOfCells - 1) - i)
							&& c.coorX <= ((noOfCells - 1) - i)) {

						if (c.coorY == i || c.coorY == ((noOfCells - 1) - i)) {
							if ((c.coorX % (half - i)) == (i % 2))
								cells.get(j).isActive = 1;
							else
								cells.get(j).isActive = 2;
						}
					}
				}
			}// The end of inner loop
		}// The end of outer loop
	}

	void colorCell(double x, double y) {
		Cell cell = getCell(x, y);

		if (cell != null)
			if (cell.isActive()) {
				TextView log = new TextView(cx);

				if (!isFilled(cell)) {

					int innerCellWidth = cell.cv.getWidth() - 20;
					int innerCellheight = cell.cv.getWidth() - 20;

					final Cell innerMovingCell = new Cell();
					CellView innerCell = new CellView(cx);

					innerMovingCell.coorX = cell.coorX;
					innerMovingCell.coorY = cell.coorY;
					innerMovingCell.x1 = cell.x1;
					innerMovingCell.y1 = cell.y1;
					innerMovingCell.x2 = cell.x2;
					innerMovingCell.y2 = cell.y2;
					innerMovingCell.isActive = cell.isActive;
					innerMovingCell.isFilled = cell.isFilled;
					innerMovingCell.setCellView(innerCell);

					if (cellCounter % 2 == 0) {
						if (!mBlackPick && !mWhitePick) {
							if (whtie == 0)
								return;
							innerMovingCell.mColor = CellColor.WHITE;
							innerCell.setBackgroundColor(Color.WHITE);
							whtie--;
							mCallBack.doUpdate(whtie, CellColor.WHITE);
							this.mWhitePlayer.insertCell(innerMovingCell);
							boolean isScore = this.mWhitePlayer.checkScore(
									this, innerMovingCell);
							if (isScore) {
								mBlackPick = true;
							} else {
								mCallBack.updateTurn(CellColor.BLACK);

							}
						}

					} else {
						if (!mBlackPick && !mWhitePick) {
							if (black == 0)
								return;
							innerMovingCell.mColor = CellColor.BLACK;
							innerCell.setBackgroundColor(Color.BLACK);
							black--;
							mCallBack.doUpdate(black, CellColor.BLACK);
							this.mBlackPlayer.insertCell(innerMovingCell);
							boolean isScore = this.mBlackPlayer.checkScore(
									this, innerMovingCell);
							if (isScore) {
								mWhitePick = true;
							} else {
								mCallBack.updateTurn(CellColor.WHITE);

							}
						}
					}

					// Set Cell as filled
					this.setFilled(cell.coorX, cell.coorY, 1);

					FrameLayout.LayoutParams innerCellParams = new FrameLayout.LayoutParams(
							innerCellWidth, innerCellheight);

					innerCellParams.setMargins((int) innerMovingCell.x1 + 10,
							(int) innerMovingCell.y1 + 10, 0, 0);

					// Add new view with drag/drop option
					innerMovingCellView = innerMovingCell.cv;
					lytBoard.addView(innerMovingCellView, innerCellParams);
					cellCounter++;

					// Long tap to remove the item
					innerMovingCellView
							.setOnLongClickListener(new OnLongClickListener() {

								@Override
								public boolean onLongClick(View v) {

									if (mWhitePick
											&& innerMovingCell.mColor == CellColor.WHITE) {

										// Remove one piece from the white set
										mBoard.mWhitePlayer
												.removeCell(innerMovingCell);

										// Add one piece for the black set of
										// earns
										mBoard.mBlackPlayer
												.insertEarn(innerMovingCell);

										// Clear the location of the removed
										// piece
										setFilled(innerMovingCell.coorX,
												innerMovingCell.coorY, 0);

										innerMovingCell.cv
												.setVisibility(View.GONE);

										// Initiate the states
										mWhitePick = false;
										mBlackPick = false;

										// Switch the turn to the next player
										mCallBack.updateTurn(CellColor.WHITE);

									} else if (mBlackPick
											&& innerMovingCell.mColor == CellColor.BLACK) {
										mBoard.mBlackPlayer
												.removeCell(innerMovingCell);
										mBoard.mWhitePlayer
												.insertEarn(innerMovingCell);

										innerMovingCell.cv
												.setVisibility(View.GONE);
										setFilled(innerMovingCell.coorX,
												innerMovingCell.coorY, 0);

										mBlackPick = false;
										mWhitePick = false;

										mCallBack.updateTurn(CellColor.BLACK);

									}

									// Update the earned cells
									mCallBack.getEarn();
									return true;
								}
							});

					innerMovingCellView
							.setOnTouchListener(new OnTouchListener() {

								public boolean onTouch(View view,
										MotionEvent motionEvent) {

									if ((cellCounter % 2 == 0 && whtie > 0)
											|| (cellCounter % 2 == 1 && black > 0)) {
									} else {

										float dx = 0;
										float dy = 0;
										boolean firstTouch = false;

										switch (motionEvent.getAction()) {
										case MotionEvent.ACTION_DOWN:

											FrameLayout.LayoutParams viewParams = (LayoutParams) view
													.getLayoutParams();
											dx = motionEvent.getX();
											dy = motionEvent.getY();

											sourceCell = getCell(
													viewParams.leftMargin,
													viewParams.topMargin);

											break;

										case MotionEvent.ACTION_MOVE:
											float x = motionEvent.getX();
											float y = motionEvent.getY();
											FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view
													.getLayoutParams();
											float left = lp.leftMargin
													+ (x - dx);
											float top = lp.topMargin + (y - dy);
											lp.leftMargin = (int) left;
											lp.topMargin = (int) top;
											view.setLayoutParams(lp);

											destinationCell = getCell(left, top);
											break;

										case MotionEvent.ACTION_UP:
											if (!mBlackPick && !mWhitePick) {
												FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) view
														.getLayoutParams();

												if (destinationCell != null
														&& destinationCell
																.isActive()
														&& !isFilled(destinationCell)) {

													lp1.leftMargin = (int) destinationCell.x1 + 10;
													lp1.topMargin = (int) destinationCell.y1 + 10;

													// Check is valid move (one
													// hop horizontally or
													// vertically)

													int xHop = Math
															.abs(sourceCell.coorX
																	- destinationCell.coorX);
													int yHop = Math
															.abs(sourceCell.coorY
																	- destinationCell.coorY);

													if (xHop == 0) {
														// Check that moves in
														// the
														// Neighbor Y cell\
														if (destinationCell.coorX == 3)
															shift = 1;
														else
															shift = Math
																	.abs(3 - destinationCell.coorX);
														if (yHop > shift) {
															lp1.leftMargin = (int) sourceCell.x1 + 10;
															lp1.topMargin = (int) sourceCell.y1 + 10;
															view.setLayoutParams(lp1);
														} else {
															// Correct move
															// Reserve the
															// destination
															int temp = cellCounter % 2;

															// Check it is white
															// turn or not?
															if (temp == 0) {
																if (innerMovingCell.mColor == CellColor.WHITE) {
																	cellCounter++;
																	view.setLayoutParams(lp1);
																	setFilled(
																			destinationCell.coorX,
																			destinationCell.coorY,
																			1);
																	// Free the
																	// source
																	setFilled(
																			sourceCell.coorX,
																			sourceCell.coorY,
																			0);

																} else {
																	mWhitePlayer
																			.replaceCells(
																					sourceCell,
																					destinationCell);
																	boolean isScore = mWhitePlayer
																			.checkScore(
																					mBoard,
																					destinationCell);

																	if (isScore) {
																		mBlackPick = true;
																	} else {
																		mCallBack
																				.updateTurn(CellColor.BLACK);

																	}

																	lp1.leftMargin = (int) sourceCell.x1 + 10;
																	lp1.topMargin = (int) sourceCell.y1 + 10;
																	view.setLayoutParams(lp1);

																}

															} else {
																if (innerMovingCell.mColor == CellColor.BLACK) {
																	cellCounter++;
																	view.setLayoutParams(lp1);
																	setFilled(
																			destinationCell.coorX,
																			destinationCell.coorY,
																			1);

																	// Free the
																	// source
																	setFilled(
																			sourceCell.coorX,
																			sourceCell.coorY,
																			0);
																	mWhitePlayer
																			.replaceCells(
																					sourceCell,
																					destinationCell);

																	boolean isScore = mBlackPlayer
																			.checkScore(
																					mBoard,
																					destinationCell);
																	if (isScore) {
																		mWhitePick = true;
																	} else {
																		mCallBack
																				.updateTurn(CellColor.WHITE);

																	}

																} else {
																	lp1.leftMargin = (int) sourceCell.x1 + 10;
																	lp1.topMargin = (int) sourceCell.y1 + 10;
																	view.setLayoutParams(lp1);

																}
															}
														}
													} else if (yHop == 0) {
														// Check that moves in
														// the
														// Neighbor X cell

														if (destinationCell.coorY == 3)
															shift = 1;
														else
															shift = Math
																	.abs(3 - destinationCell.coorY);
														if (xHop > shift) {
															lp1.leftMargin = (int) sourceCell.x1 + 10;
															lp1.topMargin = (int) sourceCell.y1 + 10;
															view.setLayoutParams(lp1);
														} else {

															// Correct move
															// Reserve the
															// destination
															int temp = cellCounter % 2;

															// Check it is white
															// turn or not?
															if (temp == 0) {
																if (innerMovingCell.mColor == CellColor.WHITE) {
																	cellCounter++;
																	view.setLayoutParams(lp1);
																	setFilled(
																			destinationCell.coorX,
																			destinationCell.coorY,
																			1);

																	// Free the
																	// source
																	setFilled(
																			sourceCell.coorX,
																			sourceCell.coorY,
																			0);
																	mWhitePlayer
																			.replaceCells(
																					sourceCell,
																					destinationCell);
																	boolean isScore = mWhitePlayer
																			.checkScore(
																					mBoard,
																					destinationCell);
																	if (isScore) {
																		mBlackPick = true;
																	} else {
																		mCallBack
																				.updateTurn(CellColor.BLACK);

																	}

																} else {
																	lp1.leftMargin = (int) sourceCell.x1 + 10;
																	lp1.topMargin = (int) sourceCell.y1 + 10;
																	view.setLayoutParams(lp1);

																}

															} else {
																if (innerMovingCell.mColor == CellColor.BLACK) {
																	cellCounter++;
																	view.setLayoutParams(lp1);
																	setFilled(
																			destinationCell.coorX,
																			destinationCell.coorY,
																			1);
																	// Free the
																	// source
																	setFilled(
																			sourceCell.coorX,
																			sourceCell.coorY,
																			0);
																	mBlackPlayer
																			.replaceCells(
																					sourceCell,
																					destinationCell);

																	boolean isScore = mBlackPlayer
																			.checkScore(
																					mBoard,
																					destinationCell);
																	if (isScore) {
																		mWhitePick = true;
																	} else {
																		mCallBack
																				.updateTurn(CellColor.WHITE);

																	}

																} else {
																	lp1.leftMargin = (int) sourceCell.x1 + 10;
																	lp1.topMargin = (int) sourceCell.y1 + 10;
																	view.setLayoutParams(lp1);
																}
															}

														}
													} else {
														lp1.leftMargin = (int) sourceCell.x1 + 10;
														lp1.topMargin = (int) sourceCell.y1 + 10;
														view.setLayoutParams(lp1);
													}
												} else {
													lp1.leftMargin = (int) sourceCell.x1 + 10;
													lp1.topMargin = (int) sourceCell.y1 + 10;
													view.setLayoutParams(lp1);
												}
												break;
											}
										}// end switch
									}
									return false;
								}

							});

				}// end of isFull
			} // end of isActive
			else
				Log.d("action", "incorrect box");

	}// end of highLightCells ----------------------------
}
