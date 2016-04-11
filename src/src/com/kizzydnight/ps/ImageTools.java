package com.kizzydnight.ps;

import java.awt.Color;
import java.awt.image.BufferedImage;
//图像工具类
public class ImageTools {
	//图像翻转方法，返回值：图像反转后的图像
	public static BufferedImage image_negative(BufferedImage bi){
		if(bi!=null){
			int green=0,red=0,blue=0,rgb;
			int imageWidth = bi.getWidth();  
	        int imageHeight = bi.getHeight();
	        for(int i = bi.getMinX();i < imageWidth ;i++){  
	            for(int j = bi.getMinY();j < imageHeight ;j++){  
	                Object data = bi.getRaster().getDataElements(i, j, null);
	                red = bi.getColorModel().getRed(data);  
	                blue = bi.getColorModel().getBlue(data);  
	                green = bi.getColorModel().getGreen(data);  
	                red = 255-red;  
	                green = 255-green;  
	                blue = 255-blue;  
	                rgb = (red*256 + green)*256+blue;  
	                if(rgb>8388608)  rgb = rgb - 16777216;  
	                bi.setRGB(i, j, rgb);  
	            }  
	        }
		}
    return bi;
	}
	//图像混合的方法
	/* bia: 底层图片
	 * bib: 顶层图片
	 * f: 混合透明度
	 * c: 顶层图片背景颜色
	 * valve: 去背景的阈值
	 * x: 顶层图片相对底层图片的x轴偏移量
	 * y: 顶层图片相对底层图片的y轴偏移量
	 * 返回值：图像混合后的图像
	 * */
	static BufferedImage image_alpha_mix(BufferedImage bia,BufferedImage bib,float f,Color c,int valve,int x,int y){
		BufferedImage output ;
		int ra,rb,ga,gb,ba,bb,ro,go,bo = 0;
		int ib,jb;
		Object dataa,datab;
		output = new BufferedImage( bia.getWidth(),bia.getHeight(),bia.getType());
		//遍历顶层图像的像素点
		for (int i = 0;i<output.getWidth();i++){
			for (int j = 0;j<output.getHeight();j++){
				if((i-x>=0)&&(i-x<bib.getWidth())&&(j-y>=0)&&(j-y<bib.getHeight())){
					//获取顶层图像在（i,j）点处的rgb值
					dataa = bia.getRaster().getDataElements(i, j, null);
					ra = bia.getColorModel().getRed(dataa);
					ga = bia.getColorModel().getGreen(dataa);
					ba = bia.getColorModel().getBlue(dataa);
					//获取底层图像在（i,j）点处的rgb值
					datab = bib.getRaster().getDataElements(i-x, j-y, null);
					rb = bia.getColorModel().getRed(datab);
					gb = bia.getColorModel().getGreen(datab);
					bb = bia.getColorModel().getBlue(datab);
					//如果该像素为背景色那么直接将底层图片的像素赋值给输出图像，并跳过此次循环继续进行下个像素的处理
					if(c!=null&&(rb>=c.getRed()-valve)&&(rb<=c.getRed()+valve)&&
						(gb>=c.getGreen()-valve)&&(gb<=c.getGreen()+valve)&&
						(bb>=c.getBlue()-valve)&&(bb<=c.getBlue()+valve)){
						int rgb = (ra*256+ga)*256+ba;
						output.setRGB(i, j, rgb);
						continue;
					} 
					//如果该像素不是背景色，计算混合后的像素值，赋值给输出图像
					ro = Math.round(f*ra+(1-f)*rb);
					go = Math.round(f*ga+(1-f)*gb);
					bo = Math.round(f*ba+(1-f)*bb);
					int rgb = (ro*256+go)*256+bo;
					output.setRGB(i, j, rgb);
				}else { //如果该像素超出顶层图像的坐标系范围，则将底层图像的像素值赋值给输出图像
					dataa = bia.getRaster().getDataElements(i, j, null);
					ra = bia.getColorModel().getRed(dataa);
					ga = bia.getColorModel().getGreen(dataa);
					ba = bia.getColorModel().getBlue(dataa);
					int rgb = (ra*256+ga)*256+ba;
					output.setRGB(i, j, rgb);
				}
			}
		}
		return output;
	}
	//图像缩放
	/* im: 输入图像
	 * Width: 想要缩放至的宽度
	 * Height: 想要缩放至的高度
	 * resizeTimes: 缩放系数
	 * 返回值：缩放后的图像
	 * 说明: 
	 * 1.如果只想用resizeTimes缩放，那么将Width和Height参数填为0即可
	 * 2.如果只想用Width和Height进行缩放，resizeTiemes为任何值都不起作用
	 * 3.两种方法均为等比缩放，根据Width和Height缩放，能自动计算出图像最大
	 * 限度的填充长为Width宽为Height的矩形时，缩放的系数resizeTimes。
	 * */
	public static BufferedImage image_zoom(BufferedImage im,int Width,int Height,float resizeTimes) {  
        BufferedImage result = null;  
        int width = im.getWidth();  
        int height = im.getHeight();
        if(Width != 0 && Height != 0)
        resizeTimes = min((float)Width/im.getWidth(),(float)Height/im.getHeight());
        int toWidth = (int) (width * resizeTimes);  
        int toHeight = (int) (height * resizeTimes);
        result = new BufferedImage(toWidth, toHeight, im.getType());  
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);  
        return result;  
  
    }
	//比较大小，返回最小值
	static float min(float x, float y){
		if(x>=y) return y;
		else return x;
	}
}
