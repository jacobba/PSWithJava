package com.kizzydnight.ps;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
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
        //int ib,jb;
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
    //缩放-最近邻内插法
    public static BufferedImage image_resize_NNI(BufferedImage im,float resizeTimes){
        BufferedImage output = null;
        int outputWidth = (int)(im.getWidth()*resizeTimes);
        int outputHeight = (int)(im.getHeight()*resizeTimes);
        int ra = 0;
        int ga = 0;
        int ba = 0;
        Object data_im;
        output = new BufferedImage(outputWidth,outputHeight,im.getType());
        for (int i = 0;i<output.getWidth();i++){
            for (int j = 0;j<output.getHeight();j++){
                float resize_i = i/resizeTimes;
                float resize_j = j/resizeTimes;
                float u = resize_i-(int)resize_i;
                float v = resize_j-(int)resize_j;
                if(u<=0.5&&v<=0.5){
                    data_im = im.getRaster().getDataElements((int)resize_i, (int)resize_j, null);
                    ra = im.getColorModel().getRed(data_im);
                    ga = im.getColorModel().getGreen(data_im);
                    ba = im.getColorModel().getBlue(data_im);
                }
                if(u>0.5&&v<0.5){
                    if((int)resize_i+1>=im.getWidth())
                        data_im = im.getRaster().getDataElements((int)resize_i, (int)resize_j, null);
                    else
                        data_im = im.getRaster().getDataElements((int)resize_i+1, (int)resize_j, null);
                    ra = im.getColorModel().getRed(data_im);
                    ga = im.getColorModel().getGreen(data_im);
                    ba = im.getColorModel().getBlue(data_im);
                }
                if(u<0.5&&v>0.5){
                    if((int)resize_j+1>=im.getHeight())
                        data_im = im.getRaster().getDataElements((int)resize_i, (int)resize_j, null);
                    else
                        data_im = im.getRaster().getDataElements((int)resize_i, (int)resize_j+1, null);
                    ra = im.getColorModel().getRed(data_im);
                    ga = im.getColorModel().getGreen(data_im);
                    ba = im.getColorModel().getBlue(data_im);
                }
                if(u>0.5&&v>0.5){
                    if(((int)resize_i+1>=im.getWidth())||((int)resize_j+1>=im.getHeight()))
                        data_im = im.getRaster().getDataElements((int)resize_i, (int)resize_j, null);
                    else
                        data_im = im.getRaster().getDataElements((int)resize_i+1, (int)resize_j+1, null);
                    ra = im.getColorModel().getRed(data_im);
                    ga = im.getColorModel().getGreen(data_im);
                    ba = im.getColorModel().getBlue(data_im);
                }
                int rgb = (ra*256+ga)*256+ba;
                output.setRGB(i, j, rgb);
            }
        }
        return output;
    }
    //缩放-双线形内插法
    public static BufferedImage image_resize_BI(BufferedImage im,float resizeTimes){
        BufferedImage output = null;
        int outputWidth = (int)(im.getWidth()*resizeTimes);
        int outputHeight = (int)(im.getHeight()*resizeTimes);
        float r = 0;float g = 0;float b = 0;
        int ro = 0;int go = 0;int bo = 0;
        int r_i_j = 0;int r_ip_j = 0;int r_i_jp = 0;int r_ip_jp = 0;
        int g_i_j = 0;int g_ip_j = 0;int g_i_jp = 0;int g_ip_jp = 0;
        int b_i_j = 0;int b_ip_j = 0;int b_i_jp = 0;int b_ip_jp = 0;
        Object data_im;Object data_im_i_j;Object data_im_ip_j;Object data_im_i_jp;Object data_im_ip_jp;
        output = new BufferedImage(outputWidth,outputHeight,im.getType());
        for (int i = 0;i<output.getWidth();i++){
            for (int j = 0;j<output.getHeight();j++){
                float resize_i = i/resizeTimes;
                float resize_j = j/resizeTimes;
                float u = resize_i-(int)resize_i;
                float v = resize_j-(int)resize_j;
                if(((int)resize_i+1>=im.getWidth())||((int)resize_j+1>=im.getHeight())){
                    data_im = im.getRaster().getDataElements((int)resize_i, (int)resize_j, null);
                    ro = im.getColorModel().getRed(data_im);
                    go = im.getColorModel().getGreen(data_im);
                    bo = im.getColorModel().getBlue(data_im);
                }else{
                    data_im_i_j = im.getRaster().getDataElements((int)resize_i, (int)resize_j, null);
                    r_i_j = im.getColorModel().getRed(data_im_i_j);
                    g_i_j = im.getColorModel().getGreen(data_im_i_j);
                    b_i_j = im.getColorModel().getBlue(data_im_i_j);
                    data_im_ip_j = im.getRaster().getDataElements((int)resize_i+1, (int)resize_j, null);
                    r_ip_j = im.getColorModel().getRed(data_im_ip_j);
                    g_ip_j = im.getColorModel().getGreen(data_im_ip_j);
                    b_ip_j = im.getColorModel().getBlue(data_im_ip_j);
                    data_im_i_jp = im.getRaster().getDataElements((int)resize_i, (int)resize_j+1, null);
                    r_i_jp = im.getColorModel().getRed(data_im_i_jp);
                    g_i_jp = im.getColorModel().getGreen(data_im_i_jp);
                    b_i_jp = im.getColorModel().getBlue(data_im_i_jp);
                    data_im_ip_jp = im.getRaster().getDataElements((int)resize_i+1, (int)resize_j+1, null);
                    r_ip_jp = im.getColorModel().getRed(data_im_ip_jp);
                    g_ip_jp = im.getColorModel().getGreen(data_im_ip_jp);
                    b_ip_jp = im.getColorModel().getBlue(data_im_ip_jp);

                    r=(v*(u*r_ip_jp+(1-u)*r_i_jp)+(1-v)*(u*r_ip_j+(1-u)*r_i_j));
                    g=(v*(u*g_ip_jp+(1-u)*g_i_jp)+(1-v)*(u*g_ip_j+(1-u)*g_i_j));
                    b=(v*(u*b_ip_jp+(1-u)*b_i_jp)+(1-v)*(u*b_ip_j+(1-u)*b_i_j));
                    //对浮点数RGB取整
                    ro = Math.round(r);
                    go = Math.round(g);
                    bo = Math.round(b);
                }
                int rgb = (int)((ro*256+go)*256+bo);
                output.setRGB(i, j, rgb);
            }
        }
        return output;
    }
    static BufferedImage image_rotate_BI(BufferedImage im,float f){
        BufferedImage output;
        int imWidth = im.getWidth();
        int imHeight = im.getHeight();
        float cosf = (float) Math.cos(f*Math.PI/180);
        float sinf = (float) Math.sin(f*Math.PI/180);
        output = new BufferedImage(1000,1000, BufferedImage.TYPE_INT_ARGB);
        int ra = 0;
        int ga = 0;
        int ba = 0;
        Object data_im;
        for (int i = 0;i<output.getWidth();i++){
            for (int j = 0;j<output.getHeight();j++){
                float resize_i = (i-imWidth/2)*cosf + (j-imHeight/2)*sinf + imWidth/2;
                float resize_j = - (i-imWidth/2)*sinf + (j-imHeight/2)*cosf + imHeight/2;
                //System.out.println("i:"+resize_i+" j:"+resize_j);
                if((resize_i<0)||(resize_i>=imWidth)||(resize_j<0)||(resize_j>=imHeight)){
                    output.setRGB(i, j, 255);
                    continue;
                }
                float u = resize_i-(int)resize_i;
                float v = resize_j-(int)resize_j;
                if(u<=0.5&&v<=0.5){
                    data_im = im.getRaster().getDataElements((int)resize_i, (int)resize_j, null);
                    ra = im.getColorModel().getRed(data_im);
                    ga = im.getColorModel().getGreen(data_im);
                    ba = im.getColorModel().getBlue(data_im);
                }
                if(u>0.5&&v<0.5){
                    if((int)resize_i+1>=im.getWidth())
                        data_im = im.getRaster().getDataElements((int)resize_i, (int)resize_j, null);
                    else
                        data_im = im.getRaster().getDataElements((int)resize_i+1, (int)resize_j, null);
                    ra = im.getColorModel().getRed(data_im);
                    ga = im.getColorModel().getGreen(data_im);
                    ba = im.getColorModel().getBlue(data_im);
                }
                if(u<0.5&&v>0.5){
                    if((int)resize_j+1>=im.getHeight())
                        data_im = im.getRaster().getDataElements((int)resize_i, (int)resize_j, null);
                    else
                        data_im = im.getRaster().getDataElements((int)resize_i, (int)resize_j+1, null);
                    ra = im.getColorModel().getRed(data_im);
                    ga = im.getColorModel().getGreen(data_im);
                    ba = im.getColorModel().getBlue(data_im);
                }
                if(u>0.5&&v>0.5){
                    if(((int)resize_i+1>=im.getWidth())||((int)resize_j+1>=im.getHeight()))
                        data_im = im.getRaster().getDataElements((int)resize_i, (int)resize_j, null);
                    else
                        data_im = im.getRaster().getDataElements((int)resize_i+1, (int)resize_j+1, null);
                    ra = im.getColorModel().getRed(data_im);
                    ga = im.getColorModel().getGreen(data_im);
                    ba = im.getColorModel().getBlue(data_im);
                }
                int rgb = (ra*256+ga)*256+ba;
                output.setRGB(i, j, rgb);
            }
        }
        return output;
    }
    //比较大小，返回最小值
    static float min(float x, float y){
        if(x>=y) return y;
        else return x;
    }
}