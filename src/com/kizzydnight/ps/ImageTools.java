package com.kizzydnight.ps;

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
	
	public static BufferedImage image_zoom(BufferedImage im,float resizeTimes) {  
        BufferedImage result = null;  
            int width = im.getWidth();  
            int height = im.getHeight();  
            int toWidth = (int) (width * resizeTimes);  
            int toHeight = (int) (height * resizeTimes);
            result = new BufferedImage(toWidth, toHeight, im.getType());  
            result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);  
        return result;  
  
    }
}
