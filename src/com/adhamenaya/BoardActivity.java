package com.adhamenaya;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BoardActivity extends Activity implements UpdatePlayer {

	Board mBoard;
	int d = 0;
	CellView prevCV = null;

	private FrameLayout lytBoard; // The ViewGroup that supports drag-drop.
	private Display mDisplay;
	private TextView tvBlackCells;
	private TextView tvBlackEarnCells;
	private TextView tvWhiteCells;
	private TextView tvWhiteEarnCells;
	private LinearLayout lytWhiteTurn;
	private LinearLayout lytBlackTrun;
	public static final boolean Debugging = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// mDragController = new DragController(this);
		setContentView(R.layout.main);
		initUI();
		displayBoard();
	}

	private void initUI() {
		mDisplay = getWindowManager().getDefaultDisplay();
		lytBoard = (FrameLayout) findViewById(R.id.lytBoard);
		lytBlackTrun = (LinearLayout) findViewById(R.id.lytBlackTrun);
		lytWhiteTurn = (LinearLayout) findViewById(R.id.lytWhiteTurn);
		tvBlackCells = (TextView) findViewById(R.id.tvBlackCells);
		tvBlackEarnCells = (TextView) findViewById(R.id.tvBlackEarnCells);
		tvWhiteCells = (TextView) findViewById(R.id.tvWhiteCells);
		tvWhiteEarnCells = (TextView) findViewById(R.id.tvWhiteEarnCells);
		mBoard = new Board(this, mDisplay, getApplicationContext(), lytBoard);

		updatePlayer();

	}

	private void updatePlayer() {
		tvBlackCells.setText(String.valueOf(mBoard.black));
		tvWhiteCells.setText(String.valueOf(mBoard.whtie));

		tvWhiteEarnCells.setText(String.valueOf(mBoard.mWhitePlayer.mEarnedmPieces.size()));
		tvBlackEarnCells.setText(String.valueOf(mBoard.mBlackPlayer.mEarnedmPieces.size()));

	}

	private void displayBoard() {
		for (int i = 0; i < mBoard.cells.size(); i++) {
			CellView cv = new CellView(this);

			TextView vi = new TextView(getApplicationContext());
			vi.setText(mBoard.cells.get(i).coorX + "-" + mBoard.cells.get(i).coorY);
			cv.addView(vi);

			int width = this.getResources().getDisplayMetrics().widthPixels / mBoard.noOfCells;
			int height = this.getResources().getDisplayMetrics().heightPixels / mBoard.noOfCells;

			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);

			params.setMargins((int) mBoard.cells.get(i).x1, (int) mBoard.cells.get(i).y1, 0, 0);

			if (i % 2 == 0)
				cv.setBackgroundColor(Color.rgb(255, 0, 0));
			else
				cv.setBackgroundColor(Color.rgb(255, 90, 90));

			if (mBoard.cells.get(i).isActive == 1)
				cv.setBackgroundColor(Color.rgb(255, 240, 20));

			lytBoard.addView(cv, params);
			mBoard.cells.get(i).setCellView(cv);
		}

		lytBoard.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent me) {
				int action = me.getAction();
				switch (action & me.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					double x = me.getX();
					double y = me.getY();
					mBoard.colorCell(x, y);

					break;
				}

				return true;
			}
		});
	}

	@Override
	public void doUpdate(int count, CellColor color) {
		switch (color) {
		case WHITE:
			tvWhiteCells.setText("" + count);
			break;
		case BLACK:
			tvBlackCells.setText("" + count);
			break;
		}
	}

	@Override
	public void getEarn() {
		tvBlackEarnCells.setText("" + mBoard.mBlackPlayer.getEarnCount());
		tvWhiteEarnCells.setText("" + mBoard.mWhitePlayer.getEarnCount());
	}

	@Override
	public void updateTurn(CellColor color) {
		switch (color) {
		case WHITE:
			lytBlackTrun.setBackgroundColor(Color.RED);
			lytWhiteTurn.setBackgroundColor(Color.GREEN);
			break;
		case BLACK:
			lytBlackTrun.setBackgroundColor(Color.GREEN);
			lytWhiteTurn.setBackgroundColor(Color.RED);
			break;

		}
	}

}