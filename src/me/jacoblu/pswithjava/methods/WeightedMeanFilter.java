package me.jacoblu.pswithjava.methods;

import java.awt.image.BufferedImage;

/**
 * Created by JacobLu on 6/6/16.
 */
public class WeightedMeanFilter {
	BufferedImage output;
	
	public WeightedMeanFilter(BufferedImage bi) {
		output = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j++) {
				int[] redValue = new int[9];
				int[] greenValue = new int[9];
				int[] blueValue = new int[9];
				int x = 0;
				try {
					for (int k = i - 1; k <= i + 1; k++) {
						for (int l = j - 1; l <= j + 1; l++) {
							Object data = bi.getRaster().getDataElements(k, l, null);
							int r = bi.getColorModel().getRed(data);
							int g = bi.getColorModel().getGreen(data);
							int b = bi.getColorModel().getBlue(data);
							if (x == 0 || x == 2 || x == 6 || x == 8) {
								redValue[x] = 0;
								greenValue[x] = 0;
								blueValue[x] = 0;
							} else {
								redValue[x] = r;
								greenValue[x] = g;
								blueValue[x] = b;
							}
							x++;
						}
					}
					int ro = getWeightedAverage(redValue);
					int go = getWeightedAverage(greenValue);
					int bo = getWeightedAverage(blueValue);
					int rgb = (ro * 256 + go) * 256 + bo;
					output.setRGB(i, j, rgb);
				} catch (Exception e) {
					Object data = bi.getRaster().getDataElements(i, j, null);
					int ro = bi.getColorModel().getRed(data);
					int go = bi.getColorModel().getGreen(data);
					int bo = bi.getColorModel().getBlue(data);
					int rgb = (ro * 256 + go) * 256 + bo;
					output.setRGB(i, j, rgb);
					continue;
				}
				
			}
		}
		
		
	}
	
	private int getWeightedAverage(int[] array) {
		int result;
		int sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		result = (int) ((sum / 4) + 0.5);
		return result;
	}
	
	public BufferedImage getOutput() {
		return output;
	}
}
