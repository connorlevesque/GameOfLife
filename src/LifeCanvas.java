// CS 201 Game of Life Final Project
//
// 5-4-15
// Connor Levesque and Dylan Quenneville

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")

public class LifeCanvas extends Canvas implements Runnable, MouseListener, MouseMotionListener {
	// instance variables
	int cellSize = 20;
	int speed = 5;
	int maxSpeed = 10;
	boolean active = false;
	boolean dragColor = true;
	boolean isMousePressed = false;
    	Image offscreen;
    	Dimension offscreensize;
	Graphics g2;
	GameOfLife parent;
	private Thread t;
	private Board board = new Board();
	private Point origin = new Point(board.size/2 , board.size/2); // point in the grid anchored to the center
	
	// constructor
	public LifeCanvas(GameOfLife p) {
		parent = p;
		t = new Thread(this);
		t.start();
	}
	
	// paint method
	public void update(Graphics g) {
		Dimension d = getSize();
		if ((offscreen == null) || (d.width != offscreensize.width)
	        			|| (d.height != offscreensize.height)) {
			offscreen = createImage(d.width, d.height);
			offscreensize = d;
			g2 = offscreen.getGraphics();
			g2.setFont(getFont());
		}
		g2.setColor(Color.white);
		g2.fillRect(0, 0, getSize().width, getSize().height);
		g2.setColor(Color.black);
		for (int x = 0; x <= board.size; x++) {
			g2.drawLine(realX(x), realY(0), realX(x), realY(board.size));
		}
		for (int y = 0; y <= board.size; y++) {
			g2.drawLine(realX(0), realY(y), realX(board.size), realY(y));
		}
		for (int y = 0; y < board.size; y++) {
			for (int x = 0; x < board.size; x++) {
				if (board.getCell(x, y)) {
					g2.fillRect(realX(x), realY(y), cellSize, cellSize);
				}
			}
		}
		g.drawImage(offscreen, 0, 0, null);
	} 
	
	// paint method
	public void paint(Graphics g) {
		update(g);
	}

	// get x on canvas from x in grid
	public int realX(int x) {
		return (int)((x - origin.x) * cellSize + getSize().getWidth()/2);
	}
	
	// get y on canvas from y in grid
	public int realY(int y) {
		return (int)((y - origin.y) * cellSize + getSize().getHeight()/2);
	}

	// get x in grid from x on canvas
	public int boardX(int x) {
		return (int)((x - getSize().getWidth()/2) / cellSize + origin.x);
	}

	// get y in grid from y on canvas
	public int boardY(int y) {
		return (int)((y - getSize().getHeight()/2) / cellSize + origin.y);
	}
	
	// button methods
	public void runButton() {
		if (!active) {
			start();
		} else {
			stop();
		}
		parent.updateRunButton();
	}
	
	public void step() {
		board.step();
		parent.updateGenerationLabel();
		repaint();
	}
	
	public void clear() {
		stop();
		step(); // ensure current step is completed
		board.clear();
		parent.updateGenerationLabel();
		parent.updateRunButton();
		repaint();
	}
	
	public void random() {
		stop();
		step(); // ensure current step is completed
		board.random();
		parent.updateGenerationLabel();
		parent.updateRunButton();
		repaint();
	}

	public void speedUp() {
		if (speed < maxSpeed)
			speed += 1;
	}
	
	public void speedDown() {
		if (speed > 1)
			speed -= 1;
	}
	public void zoomIn() {
		cellSize += 2;
		repaint();
	}
	
	public void zoomOut() {
		if (cellSize > 4)
			cellSize -= 2;
		repaint();
	}
	
	// running methods
	public void start() {
		active = true;
	}
	
	public void stop() {
		active = false;
	}
	
	public void run() {
		// always running in the background
		while (true) {
			// if the thread is active and the mouse is not being pressed
			if (active && !isMousePressed) {
				step();
			}
		    try {
		    	Thread.sleep(200/speed);
		    } catch (InterruptedException e) {};
		}
	}
	
	// inverts a cell when it is clicked
	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		int x = boardX(p.x);
		int y = boardY(p.y);
		if ((x < board.size) && (y < board.size)) {
			board.invertCell(x, y);
		}
		repaint();
	}
	
	// moves origin (called in response to arrow keys)
	public void moveOrigin(int dx, int dy) {
		origin.x += dx;
		origin.y += dy;
		repaint();
	}

	// sets origin (called in zoomIn/Out)
	public void setOrigin(int x, int y) {
		origin.x = x;
		origin.y = y;
		repaint();
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getGeneration() {
		return board.generation;
	}
	
	// mouse methods allowing the user to drag to set the pixels
	// always sets the dragged over pixels to the opposite state of the first pixel pressed (stored in dragColor variable)
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		int x = boardX(p.x);
		int y = boardY(p.y);
		if ((x < board.size) && (y < board.size)) {
			board.setCell(x, y, dragColor);
		}
		repaint();
	}
	
	public void mousePressed(MouseEvent e) {
		isMousePressed = true; // to stop the loop when the mouse is pressed
		Point p = e.getPoint();
		int x = boardX(p.x);
		int y = boardY(p.y);
		if ((x < board.size) && (y < board.size)) {
			// changes the dragColor when pressed
			dragColor = !board.getCell(x, y);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		isMousePressed = false; // to start the loop again when the mouse is released
	}
	
	// other required mouse methods
	public void mouseMoved(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
}
