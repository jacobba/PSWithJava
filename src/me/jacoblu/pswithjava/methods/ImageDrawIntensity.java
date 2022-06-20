package me.jacoblu.pswithjava.methods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ImageDrawIntensity {
	private BufferedImage bi = null;
	private int outputSize = 280;
	private int offset = 2;
	private int maxGrayValue = -1;
	private int maxRedValue = -1;
	private int maxGreenValue = -1;
	private int maxBlueValue = -1;
	private BufferedImage gray_output = new BufferedImage(outputSize, outputSize, BufferedImage.TYPE_4BYTE_ABGR);
	private BufferedImage red_output = new BufferedImage(outputSize, outputSize, BufferedImage.TYPE_4BYTE_ABGR);
	private BufferedImage green_output = new BufferedImage(outputSize, outputSize, BufferedImage.TYPE_4BYTE_ABGR);
	private BufferedImage blue_output = new BufferedImage(outputSize, outputSize, BufferedImage.TYPE_4BYTE_ABGR);
	private int grayValue[] = new int[256];
	private int redValue[] = new int[256];
	private int greenValue[] = new int[256];
	private int blueValue[] = new int[256];
	
	private void getMaxValue() {
		for (int i = 0; i < grayValue.length; i++) {
			if (grayValue[i] > maxGrayValue) maxGrayValue = grayValue[i];
			if (redValue[i] > maxRedValue) maxRedValue = redValue[i];
			if (greenValue[i] > maxGreenValue) maxGreenValue = greenValue[i];
			if (blueValue[i] > maxBlueValue) maxBlueValue = blueValue[i];
		}
	}
	
	private void getValue() {
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j++) {
				Object data = bi.getRaster().getDataElements(i, j, null);
				int r = bi.getColorModel().getRed(data);
				int g = bi.getColorModel().getGreen(data);
				int b = bi.getColorModel().getBlue(data);
				int gray = (int) (0.299 * (double) r + 0.587 * (double) g + 0.114 * (double) b);
				grayValue[gray]++;
				redValue[r]++;
				greenValue[g]++;
				blueValue[b]++;
			}
		}
	}
	
	private void gray_output() {
		//gray_output
		Graphics2D gray_g2d = gray_output.createGraphics();
		gray_g2d.setPaint(Color.BLACK);
		gray_g2d.fillRect(0, 0, outputSize, outputSize);
		gray_g2d.setPaint(Color.WHITE);
		gray_g2d.drawLine(5, 250, 265, 250);
		gray_g2d.drawLine(5, 250, 5, 5);
		gray_g2d.setPaint(Color.GRAY);
		float gray_rate = 200.0f / maxGrayValue;
		for (int i = 0; i < grayValue.length; i++) {
			int frequency = (int) (grayValue[i] * gray_rate);
			gray_g2d.drawLine(5 + offset + i, 250, 5 + offset + i, 250 - frequency);
		}
		gray_g2d.setPaint(Color.RED);
		gray_g2d.drawString("灰度直方图", 100, 270);
	}
	
	private void red_output() {
		//red_output
		Graphics2D red_g2d = red_output.createGraphics();
		red_g2d.setPaint(Color.BLACK);
		red_g2d.fillRect(0, 0, outputSize, outputSize);
		red_g2d.setPaint(Color.WHITE);
		red_g2d.drawLine(5, 250, 265, 250);
		red_g2d.drawLine(5, 250, 5, 5);
		red_g2d.setPaint(Color.RED);
		float red_rate = 200.0f / maxGrayValue;
		for (int i = 0; i < redValue.length; i++) {
			int frequency = (int) (redValue[i] * red_rate);
			red_g2d.drawLine(5 + offset + i, 250, 5 + offset + i, 250 - frequency);
		}
		red_g2d.setPaint(Color.RED);
		red_g2d.drawString("红色直方图", 100, 270);
	}
	
	private void green_output() {
		//green_output
		Graphics2D green_g2d = green_output.createGraphics();
		green_g2d.setPaint(Color.BLACK);
		green_g2d.fillRect(0, 0, outputSize, outputSize);
		green_g2d.setPaint(Color.WHITE);
		green_g2d.drawLine(5, 250, 265, 250);
		green_g2d.drawLine(5, 250, 5, 5);
		green_g2d.setPaint(Color.GREEN);
		float green_rate = 200.0f / maxGrayValue;
		for (int i = 0; i < greenValue.length; i++) {
			int frequency = (int) (greenValue[i] * green_rate);
			green_g2d.drawLine(5 + offset + i, 250, 5 + offset + i, 250 - frequency);
		}
		green_g2d.setPaint(Color.RED);
		green_g2d.drawString("绿色直方图", 100, 270);
	}
	
	private void blue_output() {
		//blue_output
		Graphics2D blue_g2d = blue_output.createGraphics();
		blue_g2d.setPaint(Color.BLACK);
		blue_g2d.fillRect(0, 0, outputSize, outputSize);
		blue_g2d.setPaint(Color.WHITE);
		blue_g2d.drawLine(5, 250, 265, 250);
		blue_g2d.drawLine(5, 250, 5, 5);
		blue_g2d.setPaint(Color.BLUE);
		float blue_rate = 200.0f / maxGrayValue;
		for (int i = 0; i < blueValue.length; i++) {
			int frequency = (int) (blueValue[i] * blue_rate);
			blue_g2d.drawLine(5 + offset + i, 250, 5 + offset + i, 250 - frequency);
		}
		blue_g2d.setPaint(Color.RED);
		blue_g2d.drawString("蓝色直方图", 100, 270);
	}
	
	public ImageDrawIntensity(BufferedImage bi) {
		this.bi = bi;
		getValue();
		getMaxValue();
		gray_output();
		red_output();
		green_output();
		blue_output();
	}
	
	private HashMap<Integer, Integer> ArrayTOMap(int value[]) {
		HashMap<Integer, Integer> Map = new HashMap<>();
		int sum = 0;
		for (int aValue : value) {
			sum = sum + aValue;
		}
		for (int i = 0; i < value.length; i++) {
			int currentSum = 0;
			for (int j = 0; j <= i; j++) {
				currentSum += value[j];
			}
			Map.put(i, (int) (255 * ((float) currentSum / sum)));
		}
		return Map;
	}
	
	public BufferedImage histogram_equalization_RGB() {
		BufferedImage equalizationed_RGB = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		HashMap RMap = ArrayTOMap(redValue);
		HashMap GMap = ArrayTOMap(greenValue);
		HashMap BMap = ArrayTOMap(blueValue);
		for (int i = 0; i < equalizationed_RGB.getWidth(); i++) {
			for (int j = 0; j < equalizationed_RGB.getHeight(); j++) {
				Object data = bi.getRaster().getDataElements(i, j, null);
				int r = bi.getColorModel().getRed(data);
				int g = bi.getColorModel().getGreen(data);
				int b = bi.getColorModel().getBlue(data);
				int ro = (int) RMap.get(r);
				int go = (int) GMap.get(g);
				int bo = (int) BMap.get(b);
				int rgb = (ro * 256 + go) * 256 + bo;
				equalizationed_RGB.setRGB(i, j, rgb);
			}
		}
		return equalizationed_RGB;
	}
	
	
	public BufferedImage getBlue_output() {
		return blue_output;
	}
	
	public BufferedImage getGray_output() {
		return gray_output;
	}
	
	public BufferedImage getGreen_output() {
		return green_output;
	}
	
	public BufferedImage getRed_output() {
		return red_output;
	}
	
}
