package me.jacoblu.pswithjava.methods;

import java.awt.image.BufferedImage;

public class ImageNegative {
	private BufferedImage output;
	
	//图像翻转方法，返回值：图像反转后的图像
	public ImageNegative(BufferedImage bi) {
		if (bi != null) {
			output = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
			int green, red, blue, rgb;
			int imageWidth = bi.getWidth();
			int imageHeight = bi.getHeight();
			for (int i = bi.getMinX(); i < imageWidth; i++) {
				for (int j = bi.getMinY(); j < imageHeight; j++) {
					Object data = bi.getRaster().getDataElements(i, j, null);
					red = bi.getColorModel().getRed(data);
					blue = bi.getColorModel().getBlue(data);
					green = bi.getColorModel().getGreen(data);
					red = 255 - red;
					green = 255 - green;
					blue = 255 - blue;
					rgb = (red * 256 + green) * 256 + blue;
					if (rgb > 8388608) rgb = rgb - 16777216;
					output.setRGB(i, j, rgb);
				}
			}
		}
	}
	
	public BufferedImage getOutput() {
		return output;
	}
}
