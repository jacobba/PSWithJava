package com.kizzydnight.ps;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageTools {
	//image negative
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
	static BufferedImage image_alpha_mix(BufferedImage bia,BufferedImage bib,float f,Color c,int valve){
		BufferedImage output ;
		int ra,rb,ga,gb,ba,bb,ro,go,bo = 0;
		int ib,jb;
		Object dataa,datab;
		//if((bib.getHeight()>bia.getHeight())||(bib.getWidth()>bia.getWidth()))
		bib = image_zoom(bib,bia.getWidth(),bia.getHeight(),0);
		output = new BufferedImage( bia.getWidth(),bia.getHeight(),bia.getType());
		for (int i = 0;i<output.getWidth();i++){
			for (int j = 0;j<output.getHeight();j++){
				if((j<bib.getHeight())&&(i<bib.getWidth())){
					dataa = bia.getRaster().getDataElements(i, j, null);
					datab = bib.getRaster().getDataElements(i, j, null);
					ra = bia.getColorModel().getRed(dataa);
					ga = bia.getColorModel().getGreen(dataa);
					ba = bia.getColorModel().getBlue(dataa);
					rb = bia.getColorModel().getRed(datab);
					gb = bia.getColorModel().getGreen(datab);
					bb = bia.getColorModel().getBlue(datab);
					if(c!=null&&(rb>=c.getRed()-valve)&&(rb<=c.getRed()+valve)&&
						(gb>=c.getGreen()-valve)&&(gb<=c.getGreen()+valve)&&
						(bb>=c.getBlue()-valve)&&(bb<=c.getBlue()+valve)){
						int rgb = (ra*256+ga)*256+ba;
						if(rgb>8388608) rgb = rgb-16777216;
						output.setRGB(i, j, rgb);
						continue;
					} 
					ro = Math.round(f*ra+(1-f)*rb);
					go = Math.round(f*ga+(1-f)*gb);
					bo = Math.round(f*ba+(1-f)*bb);
					int rgb = (ro*256+go)*256+bo;
					if(rgb>8388608) rgb = rgb-16777216;
					output.setRGB(i, j, rgb);
				}else {
					dataa = bia.getRaster().getDataElements(i, j, null);
					ra = bia.getColorModel().getRed(dataa);
					ga = bia.getColorModel().getGreen(dataa);
					ba = bia.getColorModel().getBlue(dataa);
					int rgb = (ra*256+ga)*256+ba;
					if(rgb>8388608) rgb = rgb-16777216;
					output.setRGB(i, j, rgb);
				}
			}
		}
		return output;
	}
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
	static float min(float x, float y){
		if(x>=y) return y;
		else return x;
	}
}
