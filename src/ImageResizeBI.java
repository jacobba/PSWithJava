
import java.awt.image.BufferedImage;

class ImageResizeBI {
    //缩放-双线形内插法
    private BufferedImage output = null;
    ImageResizeBI(BufferedImage im, float resizeTimes){
        int outputWidth = (int)(im.getWidth()*resizeTimes);
        int outputHeight = (int)(im.getHeight()*resizeTimes);
        float r;float g;float b;
        int ro;int go;int bo;
        int r_i_j;int r_ip_j;int r_i_jp;int r_ip_jp;
        int g_i_j;int g_ip_j;int g_i_jp;int g_ip_jp;
        int b_i_j;int b_ip_j;int b_i_jp;int b_ip_jp;
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
                int rgb = (ro*256+go)*256+bo;
                output.setRGB(i, j, rgb);
            }
        }
    }

    public BufferedImage getOutput() {
        return output;
    }
}
