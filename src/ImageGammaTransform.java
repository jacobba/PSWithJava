
import java.awt.image.BufferedImage;

class ImageGammaTransform {
    private BufferedImage output;
    ImageGammaTransform(BufferedImage im, int c){
        output = new BufferedImage(im.getWidth(),im.getHeight(),im.getType());
        int ro,go,bo;
        for (int i = 0;i<im.getWidth();i++) {
            for (int j = 0; j < im.getHeight(); j++) {
                Object data = im.getRaster().getDataElements(i, j, null);
                int r = im.getColorModel().getRed(data);
                int g = im.getColorModel().getGreen(data);
                int b = im.getColorModel().getBlue(data);
                ro = (int)(c*Math.log(1+r));
                go = (int)(c*Math.log(1+g));
                bo = (int)(c*Math.log(1+b));
                int rgb = (ro*256 + go)*256+bo;
                output.setRGB(i, j, rgb);
            }
        }
    }

    public BufferedImage getOutput() {
        return output;
    }
}
