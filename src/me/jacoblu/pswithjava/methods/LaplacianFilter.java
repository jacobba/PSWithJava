package me.jacoblu.pswithjava.methods;

import me.jacoblu.pswithjava.tools.Tools;

import java.awt.image.BufferedImage;

/**
 * Created by JacobLu on 6/7/16.
 */
public class LaplacianFilter {
	BufferedImage output;
	
	public LaplacianFilter(BufferedImage bi) {
		output = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j++) {
				boolean isBreak = false;
				int[] redValue = new int[9];
				int[] greenValue = new int[9];
				int[] blueValue = new int[9];
				for (int k = i - 1; k <= i + 1; k++) {
					int x = 0;
					for (int l = j - 1; l <= j + 1; l++) {
						try {
							Object data = bi.getRaster().getDataElements(k, l, null);
							int r = bi.getColorModel().getRed(data);
							int g = bi.getColorModel().getGreen(data);
							int b = bi.getColorModel().getBlue(data);
							if (x == 0 || x == 2 || x == 6 || x == 8) {
								redValue[x] = 0;
								greenValue[x] = 0;
								blueValue[x] = 0;
							} else if (x == 1 || x == 3 || x == 5 || x == 7) {
								redValue[x] = r;
								greenValue[x] = g;
								blueValue[x] = b;
							} else if (x == 4) {
								redValue[x] = r * (-4);
								greenValue[x] = g * (-4);
								blueValue[x] = b * (-4);
							}
						} catch (Exception e) {
							isBreak = true;
							break;
						}
						x++;
					}
					if (isBreak) break;
				}
				if (isBreak) {
					Object data = bi.getRaster().getDataElements(i, j, null);
					int ro = bi.getColorModel().getRed(data);
					int go = bi.getColorModel().getGreen(data);
					int bo = bi.getColorModel().getBlue(data);
					int rgb = (ro * 256 + go) * 256 + bo;
					output.setRGB(i, j, rgb);
					continue;
				}
				int ro = Tools.getArraySum(redValue);
				int go = Tools.getArraySum(greenValue);
				int bo = Tools.getArraySum(blueValue);
				int rgb = (ro * 256 + go) * 256 + bo;
				output.setRGB(i, j, rgb);
			}
		}
	}
	
	public BufferedImage getOutput() {
		return output;
	}
}
