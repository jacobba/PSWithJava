import java.awt.image.BufferedImage;

/**
 * Created by JacobLu on 6/6/16.
 * 5*5 Mean Filter
 */

class MeanFilter {
    BufferedImage output;
    MeanFilter(BufferedImage bi){
        output = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                int[] redValue = new int[25];
                int[] greenValue = new int[25];
                int[] blueValue = new int[25];
                int x = 0;
                try {
                    for (int k = i - 2; k <= i + 2; k++) {
                        for (int l = j - 2; l <= j + 2; l++) {
                            Object data = bi.getRaster().getDataElements(k, l, null);
                            int r = bi.getColorModel().getRed(data);
                            int g = bi.getColorModel().getGreen(data);
                            int b = bi.getColorModel().getBlue(data);
                            redValue[x] = r;
                            greenValue[x] = g;
                            blueValue[x] = b;
                            x++;
                        }
                    }
                    int ro = getAverage(redValue);
                    int go = getAverage(greenValue);
                    int bo = getAverage(blueValue);
                    int rgb = (ro * 256 + go) * 256 + bo;
                    output.setRGB(i, j, rgb);

                }catch(Exception e){
                    Object data = bi.getRaster().getDataElements(i, j, null);
                    int ro = bi.getColorModel().getRed(data);
                    int go = bi.getColorModel().getGreen(data);
                    int bo = bi.getColorModel().getBlue(data);
                    int rgb = (ro*256+go)*256+bo;
                    output.setRGB(i, j, rgb);
                    continue;
                }

            }
        }


    }
    private int getAverage(int[] array){
        int result;
        int sum = 0;
        for (int i = 0; i < array.length ; i++) {
            sum += array[i];
        }
        result = (int)((sum/array.length)+0.5);
        return result;
    }
    public BufferedImage getOutput() {
        return output;
    }
}
