package pix.view.editTools;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pix.controller.PixController;

/**
 * components that will be constantly reused
 * 
 * @author Skylark
 *
 */
public class EditingTools extends JPanel
{
	private PixController app;
	private JSlider xAxis;
	private JSlider yAxis;
	private GridLayout mainLayout;
	private JPanel make3DPanel;
	private JPanel xAxisPanel;
	private JPanel yAxisPanel;
	private JPanel scanPanel;
	private JPanel rgbPanel;
	private TextBox redBox;
	private TextBox greenBox;
	private TextBox blueBox;
	private TextBox shiftX;
	private TextBox shiftY;
	private JPanel buttonPanel;
	private JButton redButton;
	private JButton greenButton;
	private JButton blueButton;
	private JButton cyanButton;
	private JButton magentaButton;
	private JButton yellowButton;
	private int currentBaseColor;
	private int currentDirection;
	private final int MAKE3D = 0;
	private final int SCANLINE = 1;
	private final int RED = 0;
	private final int GREEN = 1;
	private final int BLUE = 2;
	private final int CYAN = 3;
	private final int MAGENTA = 4;
	private final int YELLOW = 5;
	private final int HORIZONTAL = 0;
	private final int VERTICAL = 1;
	private final int LCD = 2;
	private Color currentColor;
	private int currentEditMode; 
	private int width;
	private int height;

	public EditingTools(PixController app)
	{
		super();
		this.app = app;
		currentDirection = HORIZONTAL;
		currentEditMode = -1;
		currentBaseColor = RED;
		currentColor = new Color(255,255,255);
		width = -99;
		height = -99;
		mainLayout = new GridLayout(0,1);
		make3DPanel = new JPanel(new GridLayout(0, 1));
		shiftX = new TextBox(app, "x-Axis:");
		shiftY = new TextBox(app, "y-Axis:");
		redBox = new TextBox(app, "Red:");
		greenBox = new TextBox(app, "Green:");
		blueBox = new TextBox(app, "Blue:");
		
		xAxisPanel = new JPanel(new GridLayout(1, 0));
		yAxisPanel = new JPanel(new GridLayout(1, 0));
		
		redButton = new JButton("Red");
		greenButton = new JButton("Green");
		blueButton = new JButton("Blue");
		cyanButton = new JButton("Cyan");
		magentaButton = new JButton("Magenta");
		yellowButton = new JButton("Yellow");
		
		buttonPanel = new JPanel(new GridLayout(3,2));
		rgbPanel = new JPanel(new GridLayout(1,0));
		
		xAxis = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		yAxis = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		setupLayout();
		setupListeners();
		
	}

	private void setupLayout()
	{
		this.setLayout(mainLayout);
		xAxisPanel.add(shiftX, 0);
		xAxisPanel.add(xAxis, 1);
		yAxisPanel.add(shiftY, 0);
		yAxisPanel.add(yAxis, 1);
		
		buttonPanel.add(redButton);
		buttonPanel.add(cyanButton);
		buttonPanel.add(greenButton);
		buttonPanel.add(magentaButton);
		buttonPanel.add(blueButton);
		buttonPanel.add(yellowButton);
		
		rgbPanel.add(redBox);
		rgbPanel.add(greenBox);
		rgbPanel.add(blueBox);
		
		make3DPanel.add(xAxisPanel, 0);
		make3DPanel.add(yAxisPanel, 1);
		


	}

	private void setupListeners()
	{
		setupColorButtons();
		
		xAxis.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent slide)
			{
				shiftX.setCurrentValue(xAxis.getValue());
				applyEdit(currentEditMode);
			}
		});

		shiftX.getTextField().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent enter)
			{
				shiftY.setCurrentValue(shiftY.getTextFieldText());
				yAxis.setValue(shiftY.getCurrentValue());
				applyEdit(currentEditMode);
			}
		});
		
		yAxis.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent slide)
			{
				shiftY.setCurrentValue(yAxis.getValue());
				applyEdit(currentEditMode);
			}
		});

		shiftY.getTextField().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent enter)
			{
				shiftY.setCurrentValue(shiftY.getTextFieldText());
				yAxis.setValue(shiftY.getCurrentValue());
				applyEdit(currentEditMode);
			}
		});
		
		redBox.getTextField().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent enter)
			{
				redBox.setCurrentValue(redBox.getTextFieldText());
				if(redBox.getCurrentValue() < 0)
				{
					redBox.setCurrentValue(0);
				}
				else if(redBox.getCurrentValue() > 255)
				{
					redBox.setCurrentValue(255);
				}
				applyEdit(currentEditMode);
			}
		});
		
		greenBox.getTextField().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent enter)
			{
				greenBox.setCurrentValue(greenBox.getTextFieldText());
				if(greenBox.getCurrentValue() < 0)
				{
					greenBox.setCurrentValue(0);
				}
				else if(greenBox.getCurrentValue() > 255)
				{
					greenBox.setCurrentValue(255);
				}
				applyEdit(currentEditMode);
			}
		});

		blueBox.getTextField().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent enter)
			{
				blueBox.setCurrentValue(blueBox.getTextFieldText());
				if(blueBox.getCurrentValue() < 0)
				{
					blueBox.setCurrentValue(0);
				}
				else if(blueBox.getCurrentValue() > 255)
				{
					blueBox.setCurrentValue(255);
				}
				applyEdit(currentEditMode);
			}
		});
		
	}

	public void restartPanel()
	{
		for (Component current : this.getComponents())
		{
			this.remove(current);
		}
		
		xAxis.setValue(0);
		yAxis.setValue(0);
		
		shiftX.setCurrentValue(0);
		shiftY.setCurrentValue(0);
		redBox.setCurrentValue(0);
		greenBox.setCurrentValue(0);
		blueBox.setCurrentValue(0);
		
		
	}
	
	private void applyEdit(int type)
	{
		if(type == MAKE3D)
		{
			app.make3D((shiftX.getCurrentValue() * -1),(shiftY.getCurrentValue()),currentBaseColor);
		}
		else if(type == SCANLINE)
		{
			currentColor = new Color(redBox.getCurrentValue(),greenBox.getCurrentValue(), blueBox.getCurrentValue());
			app.scanline(shiftX.getCurrentValue(), shiftY.getCurrentValue(),currentColor,HORIZONTAL);
		}
	}
	public void setMake3D()
	{
		shiftX.setText("x-Axis");
		shiftY.setText("y-Axis");
		
		xAxis.setMaximum(width / 2);
		xAxis.setMinimum(-width / 2);

		yAxis.setMinimum(-height / 2);
		yAxis.setMaximum(height / 2);
		
		
		this.add(make3DPanel,0);
		this.add(buttonPanel,1);
		currentEditMode = MAKE3D;
	}
	
	public void setScanline()
	{
		shiftX.setText("Thickness:");
		shiftY.setText("Spread:");
		
		xAxis.setMinimum(0);
		yAxis.setMinimum(0);
		
		xAxis.setMaximum(10); //TODO: find a consistent formula for finding a good amount
		yAxis.setMaximum(10);
		this.add(make3DPanel,0);
		this.add(rgbPanel,1);
		currentEditMode = SCANLINE;
	}
	
	private void setupColorButtons()
	{
		redButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = RED;
				applyEdit(currentEditMode);

			}
		});
		greenButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = GREEN;
				applyEdit(currentEditMode);

			}
		});
		blueButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = BLUE;
				applyEdit(currentEditMode);

			}
		});
		cyanButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = CYAN;
				applyEdit(currentEditMode);

			}
		});
		magentaButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = MAGENTA;
				applyEdit(currentEditMode);

			}
		});
		yellowButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = YELLOW;
				applyEdit(currentEditMode);

			}
		});
	}

	public void updateDimensions()
	{
		width = (int) app.getPictureSize().getWidth();
		height = (int) app.getPictureSize().getHeight();
	}
}