package com.adhamenaya;

import java.util.ArrayList;

import android.view.Display;

/**
 * This class to arrange the pieces for a player on the panel---
 */
public class Panel {

	ArrayList<Cell> mPieces = new ArrayList<Cell>();
	static int noOfPieces = 9;
	double d = 0.0;
	int counter = 0, coorX = 0, coorY = 0;

	public Panel(Display display) {
		int width = display.getWidth();
		d = (double) width / noOfPieces;
		for (int x = 0; x < d; x++) {
			Cell p = new Cell();
			p.id = x + 1;
			p.width = (float) d;
			p.height = (float) d;

			mPieces.add(p);
		}
	}

	public void setCells(Player player) {
		player.setCells(mPieces);
	}
}
