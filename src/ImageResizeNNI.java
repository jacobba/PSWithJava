
import java.awt.image.BufferedImage;

class ImageResizeNNI {
    //缩放-最近邻内插法
    private BufferedImage output = null;
    ImageResizeNNI(BufferedImage im, float resizeTimes){
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
    }

    public BufferedImage getOutput() {
        return output;
    }
}
