
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;

class MainFrame {
    static private String s_file = null;
    static JFrame jf_main = null;
    static private JLabel jl_image = null;
    static private JMenuItem jmi_file_save = null;
    static private JMenuItem jmi_edit_negative = null;
    static private JMenuItem jmi_edit_restore = null;
    static private JMenuItem jmi_edit_alpha_mix = null;
    static private JMenuItem jmi_resize_NNI = null;
    static private JMenuItem jmi_resize_BI = null;
    static private JMenuItem jmi_rotate_BI = null;
    static private JMenuItem jmi_rotate_NNI = null;
    static private JMenuItem jm_transform_gray = null;
    static private JMenuItem jm_transform_gamma = null;
    static private JMenuItem jm_transform_rgb2gray = null;

    static private JMenuItem jmi_view_intensity = null;
    static private JMenuItem jmi_view_histogram_equalization = null;
    static private JMenuItem jmi_filter_meanfilter = null;
    static private JMenuItem jmi_filter_middlefilter = null;
    static private JMenuItem jmi_filter_weighted_meanfilter = null;
    static private JMenuItem jmi_filter_edge_detection = null;
    static private JMenuItem jmi_filter_laplacian_filter = null;
    //获取屏幕尺寸
    private static Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
    //文件过滤器
    static private FileNameExtensionFilter fnef_jpg = new FileNameExtensionFilter("JPG Images", "jpg","jpeg");
    static private FileNameExtensionFilter fnef_png = new FileNameExtensionFilter("PNG Images", "png");
    static private FileNameExtensionFilter fnef_bmp = new FileNameExtensionFilter("BMP Images", "bmp");
    static BufferedImage bi = null;

    //初始化
    static private void init(){
        //初始化组件
        jf_main = new JFrame("Photoshop");
        jl_image = new JLabel();
        JPanel jp_image = new JPanel();
        JScrollPane jsp_image = new JScrollPane(jp_image);
        jsp_image.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_image.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JMenuBar jmb_main = new JMenuBar();
        JMenu jm_file = new JMenu("文件");
        JMenu jm_edit = new JMenu("编辑");
        JMenu jm_view = new JMenu("查看");
        JMenu jm_filter = new JMenu("滤波器");
        JMenu jm_help = new JMenu("帮助");
        JMenu jm_resize = new JMenu("缩放");
        JMenu jm_rotate = new JMenu("旋转");
        JMenu jm_transform = new JMenu("变换");
        JMenuItem jmi_file_open = new JMenuItem("打开...");
        jmi_file_save = new JMenuItem("保存...");
        jmi_edit_negative = new JMenuItem("图像反转");
        jmi_edit_restore = new JMenuItem("重置图像");
        jmi_edit_alpha_mix = new JMenuItem("图像混合...");
        jmi_resize_NNI = new JMenuItem("最近邻内插法...");
        jmi_resize_BI = new JMenuItem("双线形内插法...");
        jmi_rotate_BI = new JMenuItem("双线形内插法...");
        jmi_rotate_NNI = new JMenuItem("最近邻内插法...");
        jm_transform_gray = new JMenuItem("灰度拉伸");
        jm_transform_rgb2gray = new JMenuItem("RGB to GRAY");
        jm_transform_gamma = new JMenuItem("Gamma变换");
        jmi_view_intensity = new JMenuItem("图像直方图");
        jmi_view_histogram_equalization = new JMenuItem("直方图均衡化(RGB)");
        jmi_filter_meanfilter = new JMenuItem("均值滤波器");
        jmi_filter_middlefilter = new JMenuItem("中值滤波器");
        jmi_filter_weighted_meanfilter = new JMenuItem("加权均值滤波器");
        jmi_filter_laplacian_filter = new JMenuItem("拉普拉斯滤波器");
        jmi_filter_edge_detection = new JMenuItem("图像边缘提取");
        JMenuItem jmi_help_github = new JMenuItem("此程序的Github主页...");
        JMenuItem jmi_help_about = new JMenuItem("关于...");
        //快捷键
        jmi_file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
        jmi_edit_restore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
        jmi_edit_negative.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
        jmi_edit_alpha_mix.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,InputEvent.CTRL_MASK));
        jmi_resize_NNI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
        jmi_resize_BI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,InputEvent.CTRL_MASK));
        //向窗口中添加组件
        jf_main.add(jsp_image,"Center");
        jp_image.add(jl_image);
        jmb_main.add(jm_file);
        jmb_main.add(jm_edit);
        jmb_main.add(jm_resize);
        jmb_main.add(jm_rotate);
        jmb_main.add(jm_transform);
        jmb_main.add(jm_view);
        jmb_main.add(jm_filter);
        jmb_main.add(jm_help);

        //file menu
        jm_file.add(jmi_file_open);
        jm_file.add(jmi_file_save);
        //edit menu
        jm_edit.add(jmi_edit_restore);
        jm_edit.add(jmi_edit_negative);
        jm_edit.add(jmi_edit_alpha_mix);

        //view menu
        jm_view.add(jmi_view_intensity);
        jm_view.add(jmi_view_histogram_equalization);
        //filter menu
        jm_filter.add(jmi_filter_meanfilter);
        jm_filter.add(jmi_filter_middlefilter);
        jm_filter.add(jmi_filter_weighted_meanfilter);
        jm_filter.add(jmi_filter_edge_detection);
        jm_filter.add(jmi_filter_laplacian_filter);
        //help menu
        jm_help.add(jmi_help_about);
        jm_help.add(jmi_help_github);
        jm_resize.add(jmi_resize_NNI);
        jm_resize.add(jmi_resize_BI);
        jm_rotate.add(jmi_rotate_NNI);
        jm_rotate.add(jmi_rotate_BI);
        jm_transform.add(jm_transform_gray);
        jm_transform.add(jm_transform_rgb2gray);
        jm_transform.add(jm_transform_gamma);
        //设置主窗口属性
        jf_main.setJMenuBar(jmb_main);
        jf_main.setSize((int)(screen_size.width*0.8),(int)(screen_size.height*0.8));
        jf_main.setResizable(true);
        jf_main.setLocationRelativeTo(null);
        jf_main.setVisible(true);
        jf_main. setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        isEnable(false);
        jmi_help_about.addActionListener((ActionEvent e)->new AboutMe());
        jmi_help_github.addActionListener((ActionEvent e)->{
            try {
                URI uri = new URI("https://github.com/jacobba/PSWithJava");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException e1) {
                e1.printStackTrace();
            }
        });
        //主窗口关闭监听器
        jf_main.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int isClose = JOptionPane.showConfirmDialog(null, "确认退出 ?", "询问", JOptionPane.YES_NO_OPTION);
                if(isClose==0) System.exit(0);
            }
        });

        //文件-打开 按钮监听器
        jmi_file_open.addActionListener((ActionEvent e)->{
            open_file();
            restoreImage();
        });
        //文件-保存 按钮监听器
        jmi_file_save.addActionListener((ActionEvent e)-> Tools.save_file(bi,jf_main));

        //编辑-图像反转 按钮监听器
        jmi_edit_negative.addActionListener((ActionEvent e)->{
            if(bi!=null){
                bi = new ImageNegative(bi).getOutput();
                update();
                jf_main.setTitle("Photoshop---图像反转");
            }

        });

        //编辑-图像混合 按钮监听器
        jmi_edit_alpha_mix.addActionListener((ActionEvent e)->{
            new AlphaMixFrame();
            jf_main.setTitle("Photoshop---图像混合");
        });

        //编辑-缩放（最近邻内插法）... 按钮监听器
        jmi_resize_NNI.addActionListener((ActionEvent e)->{
            JTextField jta = new JTextField();
            try {
                JOptionPane.showMessageDialog(null, jta, "请输入缩放系数", JOptionPane.PLAIN_MESSAGE);
                bi = new ImageResizeNNI(bi, Float.parseFloat(jta.getText())).getOutput();
                update();
                jf_main.setTitle("Photoshop---图像缩放（最近邻内插法）");
            }catch (Exception e1){
                JOptionPane.showMessageDialog(null, "参数错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        //编辑-缩放（双线形内插法）... 按钮监听器
        jmi_resize_BI.addActionListener((ActionEvent e)->{
            try {
                JTextField jta = new JTextField();
                JOptionPane.showMessageDialog(null, jta, "请输入缩放系数", JOptionPane.PLAIN_MESSAGE);
                bi = new ImageResizeBI(bi, Float.parseFloat(jta.getText())).getOutput();
                update();
                jf_main.setTitle("Photoshop---图像缩放（双线形内插法）");
            }catch (Exception e1){
                JOptionPane.showMessageDialog(null, "参数错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        //编辑-旋转（双线形内插法）... 按钮监听器
        jmi_rotate_BI.addActionListener((ActionEvent e)->{
            try {
                JTextField jta = new JTextField();
                JOptionPane.showMessageDialog(null, jta, "请输入旋转角度", JOptionPane.PLAIN_MESSAGE);
                bi = new ImageRotateBI(bi, Float.parseFloat(jta.getText())).getOutput();
                update();
                jf_main.setTitle("Photoshop---图像旋转（双线形内插法）");
            }catch (Exception e1){
                JOptionPane.showMessageDialog(null, "参数错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        //编辑-旋转（最近邻内插法）... 按钮监听器
        jmi_rotate_NNI.addActionListener((ActionEvent e)->{
            try {
                JTextField jta = new JTextField();
                JOptionPane.showMessageDialog(null, jta, "请输入旋转角度", JOptionPane.PLAIN_MESSAGE);
                bi = new ImageRotateNNI(bi, Float.parseFloat(jta.getText())).getOutput();
                update();
                jf_main.setTitle("Photoshop---图像旋转（最近邻内插法）");
            }catch (Exception e1){
                JOptionPane.showMessageDialog(null, "参数错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        //编辑-图像灰度拉伸 按钮监听器
        jm_transform_gray.addActionListener((ActionEvent e)->{
            bi = new ImageGrayTransform(bi).getOutput();
            update();
            jf_main.setTitle("Photoshop---图像灰度拉伸");
        });
        //RGB2GRAY
        jm_transform_rgb2gray.addActionListener((ActionEvent e)->{
            bi = new ImageRGB2Gray(bi).getOutput();
            update();
            jf_main.setTitle("Photoshop---RGB2GRAY");
        });
        //编辑-Gamma变换 按钮监听器
        jm_transform_gamma.addActionListener((ActionEvent e)->{
            JTextField jta = new JTextField();
            JOptionPane.showMessageDialog(null, jta, "请输入c值", JOptionPane.PLAIN_MESSAGE);
            bi = new ImageGammaTransform(bi,Integer.parseInt(jta.getText())).getOutput();
            update();
            jf_main.setTitle("Photoshop---Gamma变换 c="+Integer.parseInt(jta.getText()));
        });
        //编辑-重置图像 按钮监听器
        jmi_edit_restore.addActionListener((ActionEvent e)->{restoreImage();jf_main.setTitle("Photoshop");});
        //查看-图像直方图 按钮监听器
        jmi_view_intensity.addActionListener((ActionEvent e)->new IntensityFrame(bi));
        //查看-直方图均衡化(RGB) 按钮监听器
        jmi_view_histogram_equalization.addActionListener((ActionEvent e)->{
            bi = new ImageDrawIntensity(bi).histogram_equalization_RGB();
            update();
            jf_main.setTitle("Photoshop---直方图均衡化");
        });
        //滤波器-均值滤波器 按钮监听器
        jmi_filter_meanfilter.addActionListener((ActionEvent e)-> {
            bi = new MeanFilter(bi).getOutput();
            update();
            jf_main.setTitle("Photoshop---5*5均值滤波");
        });
        //
        jmi_filter_middlefilter.addActionListener((ActionEvent e)->{
            bi = new MiddleFilter(bi).getOutput();
            update();
            jf_main.setTitle("Photoshop---中值滤波器");
        });
        //滤波器-加权均值滤波器 按钮监听器
        jmi_filter_weighted_meanfilter.addActionListener((ActionEvent e)->{
            bi = new WeightedMeanFilter(bi).getOutput();
            update();
            jf_main.setTitle("Photoshop---3*3加权均值滤波");
        });
        //滤波器-图像边缘提取
        jmi_filter_edge_detection.addActionListener((ActionEvent e)->
            new EdgeDetectionFrame(bi));
        jmi_filter_laplacian_filter.addActionListener((ActionEvent e)->{
            bi = new LaplacianFilter(bi).getOutput();
            update();
            jf_main.setTitle("Photoshop---Laplacian滤波");
        });
    }//初始化结束

    //设置更新主窗口图像的方法
    static void setBi(BufferedImage bi) {
        MainFrame.bi = bi;
        update();
    }

    //重置、打开图像的方法
    private static void restoreImage(){
        try {
            BufferedImage src = ImageIO.read(new FileInputStream(s_file));
            initBi();
            if(src.getHeight()>jf_main.getHeight()|| src.getWidth()>jf_main.getWidth()) {

                update();
            }else jl_image.setIcon(new ImageIcon(src));
            isEnable(true);
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "文件未找到！", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ignored) {
        }
    }

    //初始化临时图像
    static private void initBi(){
        try {
            bi = ImageIO.read(new FileInputStream(s_file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "无法加载图像！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //更新主窗口图像
    static private void update(){
        jl_image.setIcon(new ImageIcon(bi));
    }

    //文件选择器
    static private void open_file(){
        JFileChooser jfc_open = new JFileChooser();
        jfc_open.setFileFilter(fnef_jpg);
        jfc_open.addChoosableFileFilter(fnef_png);
        jfc_open.addChoosableFileFilter(fnef_bmp);
        jfc_open.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc_open.showOpenDialog(jf_main);
        jfc_open.setMultiSelectionEnabled(false);
        jfc_open.setAcceptAllFileFilterUsed(false);
        try {
            s_file = jfc_open.getSelectedFile().getPath();
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "该文件不是有效的图像文件", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //设置菜单栏按钮是否可用
    static private void isEnable(boolean b){
        jmi_edit_negative.setEnabled(b);
        jmi_edit_alpha_mix.setEnabled(b);
        jmi_edit_restore.setEnabled(b);
        jmi_file_save.setEnabled(b);
        jmi_resize_NNI.setEnabled(b);
        jm_transform_gray.setEnabled(b);
        jm_transform_gamma.setEnabled(b);
        jmi_resize_BI.setEnabled(b);
        jmi_rotate_NNI.setEnabled(b);
        jmi_rotate_BI.setEnabled(b);
        jmi_view_intensity.setEnabled(b);
        jmi_view_histogram_equalization.setEnabled(b);
        jmi_filter_weighted_meanfilter.setEnabled(b);
        jmi_filter_meanfilter.setEnabled(b);
        jmi_filter_laplacian_filter.setEnabled(b);
        jmi_filter_edge_detection.setEnabled(b);
        jm_transform_rgb2gray.setEnabled(b);
        jmi_filter_middlefilter.setEnabled(b);
    }

    //主方法
    MainFrame() {
        Tools.ChangeUI();
        //生成窗口
        init();
    }
}