package me.jacoblu.pswithjava.methods;

import java.awt.image.BufferedImage;

public class ImageRotateNNI {
	//旋转-最近临法
	private BufferedImage output;
	
	public ImageRotateNNI(BufferedImage im, float f) {
		f %= 360;
		int imWidth = im.getWidth();
		int imHeight = im.getHeight();
		float cosf = Math.abs((float) Math.cos(f * Math.PI / 180));
		float sinf = Math.abs((float) Math.sin(f * Math.PI / 180));
		int outWidth = (int) (imWidth * cosf + imHeight * sinf);
		int outHeight = (int) (imHeight * cosf + imWidth * sinf);
		output = new BufferedImage(outWidth, outHeight, im.getType());
		int ra = 0;
		int ga = 0;
		int ba = 0;
		Object data_im;
		for (int i = 0; i < output.getWidth(); i++) {
			for (int j = 0; j < output.getHeight(); j++) {
				float resize_i = (i - outWidth / 2) * cosf + (j - outHeight / 2) * sinf + imWidth / 2;
				float resize_j = (j - outHeight / 2) * cosf + (-i + outWidth / 2) * sinf + imHeight / 2;
				if ((resize_i < 0) || (resize_i >= imWidth) || (resize_j < 0) || (resize_j >= imHeight)) {
					output.setRGB(i, j, 16777215);
					continue;
				}
				float u = resize_i - (int) resize_i;
				float v = resize_j - (int) resize_j;
				if (u <= 0.5 && v <= 0.5) {
					data_im = im.getRaster().getDataElements((int) resize_i, (int) resize_j, null);
					ra = im.getColorModel().getRed(data_im);
					ga = im.getColorModel().getGreen(data_im);
					ba = im.getColorModel().getBlue(data_im);
				}
				if (u > 0.5 && v < 0.5) {
					if ((int) resize_i + 1 >= im.getWidth())
						data_im = im.getRaster().getDataElements((int) resize_i, (int) resize_j, null);
					else
						data_im = im.getRaster().getDataElements((int) resize_i + 1, (int) resize_j, null);
					ra = im.getColorModel().getRed(data_im);
					ga = im.getColorModel().getGreen(data_im);
					ba = im.getColorModel().getBlue(data_im);
				}
				if (u < 0.5 && v > 0.5) {
					if ((int) resize_j + 1 >= im.getHeight())
						data_im = im.getRaster().getDataElements((int) resize_i, (int) resize_j, null);
					else
						data_im = im.getRaster().getDataElements((int) resize_i, (int) resize_j + 1, null);
					ra = im.getColorModel().getRed(data_im);
					ga = im.getColorModel().getGreen(data_im);
					ba = im.getColorModel().getBlue(data_im);
				}
				if (u > 0.5 && v > 0.5) {
					if (((int) resize_i + 1 >= im.getWidth()) || ((int) resize_j + 1 >= im.getHeight()))
						data_im = im.getRaster().getDataElements((int) resize_i, (int) resize_j, null);
					else
						data_im = im.getRaster().getDataElements((int) resize_i + 1, (int) resize_j + 1, null);
					ra = im.getColorModel().getRed(data_im);
					ga = im.getColorModel().getGreen(data_im);
					ba = im.getColorModel().getBlue(data_im);
				}
				int rgb = (ra * 256 + ga) * 256 + ba;
				output.setRGB(i, j, rgb);
			}
		}
	}
	
	public BufferedImage getOutput() {
		return output;
	}
}
