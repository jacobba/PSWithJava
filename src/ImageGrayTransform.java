
import java.awt.image.BufferedImage;

class ImageGrayTransform {
    private BufferedImage output = null;
    ImageGrayTransform(BufferedImage im){
        output = new BufferedImage(im.getWidth(),im.getHeight(),im.getType());
        int maxr = 0,maxg = 0,maxb =0,minr =255,ming=255,minb=255;
        Object data;
        for (int i = 0;i<im.getWidth();i++) {
            for (int j = 0; j < im.getHeight(); j++) {
                data = im.getRaster().getDataElements(i, j, null);
                int r = im.getColorModel().getRed(data);
                int g = im.getColorModel().getGreen(data);
                int b = im.getColorModel().getBlue(data);
                if(r>=maxr) maxr=r;
                if(g>=maxg) maxg=g;
                if(b>=maxb) maxb=b;
                if(r<=minr) minr=r;
                if(g<=ming) ming=g;
                if(b<=minb) minb=b;
            }
        }
        float ar =255f/(maxr-minr);
        float br = -minr*255f/(maxr-minr);
        float ag =255f/(maxg-ming);
        float bg = -ming*255f/(maxg-ming);
        float ab =255f/(maxb-minb);
        float bb = -minb*255f/(maxb-minb);

        for (int i = 0;i<im.getWidth();i++) {
            for (int j = 0; j < im.getHeight(); j++) {
                data = im.getRaster().getDataElements(i, j, null);
                int r = im.getColorModel().getRed(data);
                int g = im.getColorModel().getGreen(data);
                int b = im.getColorModel().getBlue(data);
                int yr = (int)(ar*r+br);
                int yg = (int)(ag*g+bg);
                int yb = (int)(ab*b+bb);
                int rgb = (yr*256 + yg)*256+yb;
                output.setRGB(i, j, rgb);
            }
        }
    }

    public BufferedImage getOutput() {
        return output;
    }
}
