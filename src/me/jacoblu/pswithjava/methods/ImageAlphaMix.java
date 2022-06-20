package me.jacoblu.pswithjava.methods;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 图像混合的方法 bia: 底层图片 bib: 顶层图片 f: 混合透明度 c: 顶层图片背景颜色 valve: 去背景的阈值 x: 顶层图片相对底层图片的x轴偏移量 y: 顶层图片相对底层图片的y轴偏移量 返回值：图像混合后的图像
 */
public class ImageAlphaMix {
	private BufferedImage output;
	
	public ImageAlphaMix(BufferedImage bia, BufferedImage bib, float f, Color c, int valve, int x, int y) {
		int ra, rb, ga, gb, ba, bb, ro, go, bo;
		Object dataa, datab;
		output = new BufferedImage(bia.getWidth(), bia.getHeight(), bia.getType());
		//遍历顶层图像的像素点
		for (int i = 0; i < output.getWidth(); i++) {
			for (int j = 0; j < output.getHeight(); j++) {
				if ((i - x >= 0) && (i - x < bib.getWidth()) && (j - y >= 0) && (j - y < bib.getHeight())) {
					//获取顶层图像在（i,j）点处的rgb值
					dataa = bia.getRaster().getDataElements(i, j, null);
					ra = bia.getColorModel().getRed(dataa);
					ga = bia.getColorModel().getGreen(dataa);
					ba = bia.getColorModel().getBlue(dataa);
					//获取底层图像在（i,j）点处的rgb值
					datab = bib.getRaster().getDataElements(i - x, j - y, null);
					rb = bia.getColorModel().getRed(datab);
					gb = bia.getColorModel().getGreen(datab);
					bb = bia.getColorModel().getBlue(datab);
					//如果该像素为背景色那么直接将底层图片的像素赋值给输出图像，并跳过此次循环继续进行下个像素的处理
					if (c != null && (rb >= c.getRed() - valve) && (rb <= c.getRed() + valve) &&
							(gb >= c.getGreen() - valve) && (gb <= c.getGreen() + valve) &&
							(bb >= c.getBlue() - valve) && (bb <= c.getBlue() + valve)) {
						int rgb = (ra * 256 + ga) * 256 + ba;
						output.setRGB(i, j, rgb);
						continue;
					}
					//如果该像素不是背景色，计算混合后的像素值，赋值给输出图像
					ro = Math.round(f * ra + (1 - f) * rb);
					go = Math.round(f * ga + (1 - f) * gb);
					bo = Math.round(f * ba + (1 - f) * bb);
					int rgb = (ro * 256 + go) * 256 + bo;
					output.setRGB(i, j, rgb);
				} else { //如果该像素超出顶层图像的坐标系范围，则将底层图像的像素值赋值给输出图像
					dataa = bia.getRaster().getDataElements(i, j, null);
					ra = bia.getColorModel().getRed(dataa);
					ga = bia.getColorModel().getGreen(dataa);
					ba = bia.getColorModel().getBlue(dataa);
					int rgb = (ra * 256 + ga) * 256 + ba;
					output.setRGB(i, j, rgb);
				}
			}
		}
	}
	
	public BufferedImage getOutput() {
		return output;
	}
}
