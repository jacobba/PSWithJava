package me.jacoblu.pswithjava.methods;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by JacobLu on 6/20/16.
 */
public class MiddleFilter {
	BufferedImage output;
	
	public MiddleFilter(BufferedImage bi) {
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
							redValue[x] = r;
							greenValue[x] = g;
							blueValue[x] = b;
							x++;
						}
					}
					Arrays.sort(redValue);
					Arrays.sort(greenValue);
					Arrays.sort(blueValue);
					int ro = redValue[4];
					int go = greenValue[4];
					int bo = greenValue[4];
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
	
	public BufferedImage getOutput() {
		return output;
	}
}
