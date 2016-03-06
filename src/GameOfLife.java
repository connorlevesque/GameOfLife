// CS 201 Game of Life Final Project
//
// 5-4-15
// Connor Levesque and Dylan Quenneville

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")

public class GameOfLife extends Applet implements ActionListener, KeyListener {
	// instance variables
	LifeCanvas c;
	Button runButton, stepButton, clearButton, randomButton, speedUpButton, speedDownButton, zoomInButton, zoomOutButton;
	Label genLabel, speedLabel;
	
	// init method
	public void init() {
		this.setLayout(new BorderLayout());
		c = new LifeCanvas(this);
        	c.setBackground(Color.white);
        	c.addMouseListener(c);
        	c.addMouseMotionListener(c);
        	c.addKeyListener(this);
        	this.add("Center", c);
		this.add("North", setLabelPanel());
		this.add("South", setButtonPanel());
	}
	
	// set up panel helper methods
	private Panel setButtonPanel() {
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		runButton = new Button();
		runButton.setLabel("Run");
		runButton.addActionListener(this);
		p.add(runButton);
		stepButton = new Button();
		stepButton.setLabel("Step");
		stepButton.addActionListener(this);
		p.add(stepButton);
		clearButton = new Button();
		clearButton.setLabel("Clear");
		clearButton.addActionListener(this);
		p.add(clearButton);
		randomButton = new Button();
		randomButton.setLabel("Randomize");
		randomButton.addActionListener(this);
		p.add(randomButton);
		speedDownButton = new Button();
		speedDownButton.setLabel("Speed-");
		speedDownButton.addActionListener(this);
		p.add(speedDownButton);
		speedUpButton = new Button();
		speedUpButton.setLabel("Speed+");
		speedUpButton.addActionListener(this);
		p.add(speedUpButton);
		zoomInButton = new Button();
		zoomInButton.setLabel("Zoom In");
		zoomInButton.addActionListener(this);
		p.add(zoomInButton);
		zoomOutButton = new Button();
		zoomOutButton.setLabel("Zoom Out");
		zoomOutButton.addActionListener(this);
		p.add(zoomOutButton);
		return p;
	}
	
	private Panel setLabelPanel() {
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		genLabel = new Label();
		genLabel.setText("Generation: " + c.getGeneration() + "             ");
		p.add(genLabel);
		speedLabel = new Label();
		speedLabel.setText("Speed: " + c.getSpeed() + "        ");
		p.add(speedLabel);
		return p;
	}
	
	// calls button methods
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == runButton) {
			c.runButton();
		} else if (e.getSource() == stepButton) {
			c.step();
		} else if (e.getSource() == clearButton) {
			c.clear();
		} else if (e.getSource() == randomButton) {
			c.random();
		} else if (e.getSource() == speedUpButton) {
			c.speedUp();
			speedLabel.setText("Speed: " + c.getSpeed());
		} else if (e.getSource() == speedDownButton) {
			c.speedDown();
			speedLabel.setText("Speed: " + c.getSpeed());
		} else if (e.getSource() == zoomInButton) {
			c.zoomIn();
		} else if (e.getSource() == zoomOutButton) {
			c.zoomOut();
		}
    	}
	
	// calls key methods
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();               
	        if (keyCode == KeyEvent.VK_UP) {
	            c.moveOrigin(0, -1);
	        } else if (keyCode == KeyEvent.VK_DOWN) {
	            c.moveOrigin(0, 1);
	        } else if (keyCode == KeyEvent.VK_LEFT ) {
	            c.moveOrigin(-1, 0);
	        } else if (keyCode == KeyEvent.VK_RIGHT ) {
	            c.moveOrigin(1, 0);
	        }
	}
	
	public void updateRunButton() {
	    	if (c.active) {
	    		runButton.setLabel("Stop");
	    	} else {
	    		runButton.setLabel("Run");
	    	}
	}
	
	public void updateGenerationLabel() {
		genLabel.setText("Generation: " + c.getGeneration());
	}
	
	// other required key methods
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
}
