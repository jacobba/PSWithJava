import java.awt.image.BufferedImage;

/**
 * Created by JacobLu on 6/6/16.
 */
class EdgeDetection {
    BufferedImage Output;
    BufferedImage bi;
    EdgeDetection(BufferedImage bi){
        this.bi = new ImageRGB2Gray(bi).getOutput();
    }

    BufferedImage Sobel(int t){
        int[] xop = {-1,0,1,-2,0,2,-1,0,1};
        int[] yop = {1,2,1,0,0,0,-1,-2,-1};
        return KenalMain(xop,yop,t);
    }
    BufferedImage Prewitt(int t){
        int[] xop = {-1,-1,-1,0,0,0,1,1,1};
        int[] yop = {-1,0,-1,-1,0,-1,-1,0,-1};
        return KenalMain(xop,yop,t);
    }

    BufferedImage Roberts(){
        Output = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                try {
                    Object data_i_j = bi.getRaster().getDataElements(i, j, null);
                    int gray_i_j = bi.getColorModel().getRed(data_i_j);
                    Object data_ip_j = bi.getRaster().getDataElements(i+1, j, null);
                    int gray_ip_j = bi.getColorModel().getRed(data_ip_j);
                    Object data_i_jp = bi.getRaster().getDataElements(i, j, null);
                    int gray_i_jp = bi.getColorModel().getRed(data_i_jp);
                    Object data_ip_jp = bi.getRaster().getDataElements(i, j, null);
                    int gray_ip_jp = bi.getColorModel().getRed(data_ip_jp);

                    int G = (int)(Math.sqrt((gray_i_j-gray_ip_jp)*(gray_i_j-gray_ip_jp)+
                            (gray_i_jp-gray_ip_j)*(gray_i_jp-gray_ip_j))+0.5);
                    int rgb = (G * 256 + G) * 256 + G;
                    Output.setRGB(i, j, rgb);
                }catch (Exception e){
                    Output.setRGB(i, j, 0);
                }
            }
        }
        return Output;
    }
    private BufferedImage KenalMain (int[] xop,int[] yop,int t) {
        Output = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                int Gx ,Gy ,G ;
                int[] grayValueX = new int[9];
                int[] grayValueY = new int[9];


                for (int k = i - 1; k <= i + 1; k++) {
                    int x = 0;
                    for (int l = j - 1; l <= j + 1; l++) {
                        try {
                            Object data = bi.getRaster().getDataElements(k, l, null);
                            int gray = bi.getColorModel().getRed(data);
                            grayValueX[x] = gray*xop[x];
                            grayValueY[x] = gray*yop[x];
                        }catch (Exception e){
                            grayValueX[x] = 0;
                            grayValueY[x] = 0;
                        }
                        x++;
                    }
                }

                Gx = new Tools().getArraySum(grayValueX);
                Gy = new Tools().getArraySum(grayValueY);
                G = (int)(Math.sqrt( Gx*Gx + Gy*Gy )+0.5);
                if (t == -1){
                    int rgb = (G * 256 + G) * 256 + G;
                    Output.setRGB(i, j, rgb);
                }
                if(G>t){
                    int rgb = (255 * 256 + 255) * 256 + 255;
                    Output.setRGB(i, j, rgb);
                }
            }
        }
        return Output;
    }
}
