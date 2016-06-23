import java.awt.image.BufferedImage;

/**
 * Created by JacobLu on 6/6/16.
 */
class ImageRGB2Gray {
    BufferedImage output;
    ImageRGB2Gray(BufferedImage bi){
        output = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                Object data = bi.getRaster().getDataElements(i, j, null);
                int r = bi.getColorModel().getRed(data);
                int g = bi.getColorModel().getGreen(data);
                int b = bi.getColorModel().getBlue(data);
                int gray = (r*299 + g*587 + b*114 + 500) / 1000 ;
                int rgb = (gray*256+gray)*256+gray;
                output.setRGB(i, j, rgb);
            }
        }
    }

    public BufferedImage getOutput() {
        return output;
    }
}
