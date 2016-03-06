// CS 201 Game of Life Final Project
//
// 5-4-15
// Connor Levesque and Dylan Quenneville

import java.util.*;

public class Board {
	// instance variables
	public int size = 200;
	public int generation = 0;
	double randomRatio = 0.2;
	private Vector<Vector<Boolean>> grid;
	
	// constructor
	public Board() {
		//set up grid (stored as booleans in a vector of vectors)
		grid = new Vector<Vector<Boolean>>();
		for (int y = 0; y < size; y++) {
			Vector<Boolean> gridRow = new Vector<Boolean>();
			for (int x = 0; x < size; x++) {
				Boolean cell = false;
				gridRow.add(cell);
			}
			grid.add(gridRow);
		}
	}
	
	// button methods
	
	// steps board to the next generation
	public void step() {
		// constructs a new grid according to the rules
		Vector<Vector<Boolean>> newGrid = new Vector<Vector<Boolean>>();
		for (int y = 0; y < size; y++) {
			Vector<Boolean> gridRow = new Vector<Boolean>();
			for (int x = 0; x < size; x++) {
				Boolean cell = getCell(x,y);
				Boolean newCell = stepCell(x,y,cell);
				gridRow.add(newCell);
			}
			newGrid.add(gridRow);
		}
		grid = newGrid;
		// increments the generation
		generation++;
	}
	
	// clear all cells
	public void clear() {
		Vector<Vector<Boolean>> clearGrid = new Vector<Vector<Boolean>>();
		for (int y = 0; y < size; y++) {
			Vector<Boolean> gridRow = new Vector<Boolean>();
			for (int x = 0; x < size; x++) {
				Boolean cell = false;
				gridRow.add(cell);
			}
			clearGrid.add(gridRow);
		}
		grid = clearGrid;
		generation = 0;
	}
	
	// sets all cells to either white or black
	public void random() {
		Vector<Vector<Boolean>> randomGrid = new Vector<Vector<Boolean>>();
		for (int y = 0; y < size; y++) {
			Vector<Boolean> gridRow = new Vector<Boolean>();
			for (int x = 0; x < size; x++) {
				// ratio of white to black cells controlled by randomRatio variable
				Boolean cell = (Math.random() < randomRatio);
				gridRow.add(cell);
			}
			randomGrid.add(gridRow);
		}
		grid = randomGrid;
		generation = 0;
	}
	
	// return a cell's state in the next generation
	public Boolean stepCell(int x, int y, Boolean state) {
		int c = 0; // count
		// counts live neighbor cells
		c += cellToInt(x-1, y-1);
		c += cellToInt(x, y-1);
		c += cellToInt(x+1, y-1);
		c += cellToInt(x-1, y);
		c += cellToInt(x+1, y);
		c += cellToInt(x-1, y+1);
		c += cellToInt(x, y+1);
		c += cellToInt(x+1, y+1);
		// the rules of life and death
		if (c == 2) {
			return state;
		} else if (c == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	// returns the int value of a cell, used to simplify stepCell method
	public int cellToInt(int x, int y) {
		if ((x >= 0) && (x < size) && (y >= 0) && (y < size)) {
			if (getCell(x,y)) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
	
	// get a cell from the grid
	public Boolean getCell(int x, int y) {
		return grid.get(y).get(x);
	}
	
	// set a cell to a true or false state
	public void setCell(int x, int y, Boolean state) {
		Vector<Boolean> row = grid.get(y);
		row.set(x, state);
		grid.set(y, row);
	}
	
	// change a cell from true to false or vice versus
	public void invertCell(int x, int y) {
		if (getCell(x,y)) {
			setCell(x,y, false);
		} else {
			setCell(x,y, true);
		}
	}
	
}