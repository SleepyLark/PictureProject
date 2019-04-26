package pix.controller;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import pix.view.GlitchFrame;
import pixLab.classes.*;

public class PixController
{
	private Picture activeImage;
	private Picture originalImage;
	private Picture alteredImage;
	private File saveFolder;
	private String recentLoadPath;
	private String recentSavePath;
	private String extention;
	private GlitchFrame appFrame;
	private Dimension currentSize;
	private Picture lastChange;
	private boolean fileLoaded;

	public PixController()
	{
		appFrame = new GlitchFrame(this);
		recentLoadPath = "./src/pixLab/images";
		recentSavePath = "./savedImages/";
		activeImage = new Picture();
		fileLoaded = false;
		extention = ".jpg";
		currentSize = new Dimension();

	}

	public void start()
	{

	}

	public void loadImage()
	{
		JFileChooser explorer = new JFileChooser(recentLoadPath);
		explorer.setDialogTitle("What image do you want to load?");
		int result = explorer.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			// new File(fileName);
			String fileName = explorer.getSelectedFile().getAbsolutePath();
			activeImage.load(fileName);
			originalImage = new Picture(activeImage);
			extention = fileName.substring(fileName.lastIndexOf("."));
			appFrame.updateDisplay();
			fileLoaded = true;
		}

	}

	public void saveImage()
	{
		String[] option = { "Yes", "No" };
		int save = JOptionPane.showOptionDialog(null, "Do you want to save this image?", "Save?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
		if (save == 0)
		{
			JFileChooser explorer = new JFileChooser(recentSavePath);
			explorer.setDialogTitle("Where do you want to save?");
			String nameGlitch = "-glitched" + extention;
			File saveFile = new File(nameGlitch);
			explorer.setSelectedFile(saveFile);

			int result = explorer.showSaveDialog(null);

			if (result == JFileChooser.APPROVE_OPTION)
			{
				String writeTo = explorer.getSelectedFile().getAbsolutePath();
				// freaking Mac doesn't recognize '.'
				if (writeTo.indexOf(".") < writeTo.length() - 4)
				{
					String temp = writeTo.substring(0, writeTo.indexOf("."));
					writeTo = temp + writeTo.substring(writeTo.indexOf(".") + 2);
				}
				if (activeImage.write(writeTo))
				{
					System.out.println(writeTo);
					JOptionPane.showMessageDialog(null, "Save successful");
				}
			}

		}
	}

	public void glitch()
	{
		copyLastEdit();
		activeImage.glitch();
		alteredImage = new Picture(activeImage);
		appFrame.updateDisplay();
	}

	public DigitalPicture getCurrentImage()
	{
		return activeImage;
	}

	public DigitalPicture getOriginal()
	{
		return originalImage;
	}

	public DigitalPicture getAltered()
	{
		return alteredImage;
	}

	public DigitalPicture getLastChange()
	{
		return lastChange;
	}
	
	public boolean getFileLoaded()
	{
		return fileLoaded;
	}

	public void setCurrentImage(DigitalPicture imageToDisplay)
	{
		activeImage = (Picture) imageToDisplay;
	}

	public Dimension getPictureSize()
	{
		currentSize.setSize(activeImage.getWidth(), activeImage.getHeight());
		return currentSize;
	}

	private void copyLastEdit()
	{
		lastChange = new Picture(activeImage);
	}

	public void updateDisplay()
	{
		appFrame.updateDisplay();
	}
}