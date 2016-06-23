
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Tools {
    static private FileNameExtensionFilter fnef_jpg = new FileNameExtensionFilter("JPG Images", "jpg","jpeg");
    static private FileNameExtensionFilter fnef_png = new FileNameExtensionFilter("PNG Images", "png");
    static private FileNameExtensionFilter fnef_bmp = new FileNameExtensionFilter("BMP Images", "bmp");
    static private Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
    public static int screen_x = screen_size.width;
    public static int screen_y = screen_size.height;
    //获取数组总和
    public static int getArraySum(int[] array){
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum+= array[i];
        }
        return sum;
    }
    //保存文件方法
    public static boolean save_file(BufferedImage bi,JFrame jf){
        JFileChooser jfc_save = new JFileChooser();
        jfc_save.setFileFilter(fnef_jpg);
        jfc_save.showSaveDialog(jf);
        try {
            File f = new File(jfc_save.getSelectedFile().getPath()+".jpg");
            ImageIO.write(bi, "jpg", f);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "图像未保存！", "警告", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    //更换系统UI字体
    public static void ChangeUI() {
        //更换字体
        Font defaultFont = new Font("System", Font.PLAIN, 12);
        UIManager.put("Button.font", defaultFont);
        UIManager.put("CheckBox.font", defaultFont);
        UIManager.put("RadioButton.font", defaultFont);
        UIManager.put("ToolTip.font", defaultFont);
        UIManager.put("ComboBox.font", defaultFont);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("List.font", defaultFont);
        UIManager.put("Table.font", defaultFont);
        UIManager.put("TableHeader.font", defaultFont);
        UIManager.put("MenuBar.font", defaultFont);
        UIManager.put("Menu.font", defaultFont);
        UIManager.put("MenuItem.font", defaultFont);
        UIManager.put("PopupMenu.font", defaultFont);
        UIManager.put("Tree.font", defaultFont);
        UIManager.put("ToolBar.font", defaultFont);
    }
}
