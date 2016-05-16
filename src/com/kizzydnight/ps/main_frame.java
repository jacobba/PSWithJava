package com.kizzydnight.ps;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.*;

public class main_frame {
    static private String s_file = null;
    static JFrame jf_main = null;
    static private JLabel jl_image = null;
    static private JPanel jp_image = null;
    static private JScrollPane jsp_image = null;
    static private JMenuBar jmb_main = null;
    static private JMenu jm_file = null;
    static private JMenu jm_edit = null;
    static private JMenu jm_help = null;
    static private JMenu jm_resize = null;
    static private JMenu jm_rotate = null;
    static private JMenu jm_transform = null;
    static private JMenuItem jmi_file_open = null;
    static private JMenuItem jmi_file_save = null;
    static private JMenuItem jmi_edit_negative = null;
    static private JMenuItem jmi_edit_restore = null;
    static private JMenuItem jmi_edit_alpha_mix = null;
    static private JMenuItem jmi_edit_resize_NNI = null;
    static private JMenuItem jmi_edit_resize_BI = null;
    static private JMenuItem jmi_edit_rotate_BI = null;
    static private JMenuItem jmi_edit_rotate_NNI = null;
    static private JMenuItem jm_edit_transform_gray = null;
    static private JMenuItem jm_edit_transform_gamma = null;
    static private JMenuItem jmi_help_about = null;
    static private JFileChooser jfc_open = null;
    static private JFileChooser jfc_save = null;
    //获取屏幕尺寸
    static Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
    //文件过滤器
    static private FileNameExtensionFilter fnef_jpg = new FileNameExtensionFilter("JPG Images", "jpg","jpeg");
    static private FileNameExtensionFilter fnef_png = new FileNameExtensionFilter("PNG Images", "png");
    static private FileNameExtensionFilter fnef_bmp = new FileNameExtensionFilter("BMP Images", "bmp");
    static BufferedImage bi = null;
    private static BufferedImage src = null;
    //初始化
    static private void init(){
        //初始化组件
        jf_main = new JFrame("Photoshop");
        jl_image = new JLabel();
        jp_image = new JPanel();
        jsp_image = new JScrollPane(jp_image);
        jsp_image.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp_image.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jmb_main = new JMenuBar();
        jm_file = new JMenu("文件");
        jm_edit = new JMenu("编辑");
        jm_help = new JMenu("帮助");
        jm_resize = new JMenu("缩放");
        jm_rotate = new JMenu("旋转");
        jm_transform = new JMenu("变换");
        jmi_file_open = new JMenuItem("打开...");
        jmi_file_save = new JMenuItem("保存...");
        jmi_edit_negative = new JMenuItem("图像反转");
        jmi_edit_restore = new JMenuItem("重置图像");
        jmi_edit_alpha_mix = new JMenuItem("图像混合...");
        jmi_edit_resize_NNI = new JMenuItem("最近邻内插法...");
        jmi_edit_resize_BI = new JMenuItem("双线形内插法...");
        jmi_edit_rotate_BI = new JMenuItem("双线形内插法...");
        jmi_edit_rotate_NNI = new JMenuItem("最近邻内插法...");
        jm_edit_transform_gray = new JMenuItem("灰度拉伸");
        jm_edit_transform_gamma = new JMenuItem("Gamma");
        jmi_help_about = new JMenuItem("关于...");
        //快捷键
        jmi_file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
        jmi_edit_restore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
        jmi_edit_negative.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
        jmi_edit_alpha_mix.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,InputEvent.CTRL_MASK));
        jmi_edit_resize_NNI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
        jmi_edit_resize_BI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,InputEvent.CTRL_MASK));
        //向窗口中添加组件
        jf_main.add(jsp_image,"Center");
        jp_image.add(jl_image);
        jmb_main.add(jm_file);
        jmb_main.add(jm_edit);
        jmb_main.add(jm_help);
        jm_file.add(jmi_file_open);
        jm_file.add(jmi_file_save);
        //edit menu
        jm_edit.add(jmi_edit_restore);
        jm_edit.add(jmi_edit_negative);
        jm_edit.add(jmi_edit_alpha_mix);
        jm_edit.add(jm_resize);
        jm_edit.add(jm_rotate);
        jm_edit.add(jm_transform);
        //help menu
        jm_help.add(jmi_help_about);
        jm_resize.add(jmi_edit_resize_NNI);
        jm_resize.add(jmi_edit_resize_BI);
        jm_rotate.add(jmi_edit_rotate_NNI);
        jm_rotate.add(jmi_edit_rotate_BI);
        jm_transform.add(jm_edit_transform_gray);
        jm_transform.add(jm_edit_transform_gamma);
        //设置主窗口属性
        jf_main.setJMenuBar(jmb_main);
        jf_main.setSize((int)(screen_size.width*0.8),(int)(screen_size.height*0.8));
        jf_main.setResizable(true);
        jf_main.setLocationRelativeTo(null);
        jf_main.setVisible(true);
        jf_main. setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        isEnable(false);
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
        jmi_file_save.addActionListener((ActionEvent e)->save_file());
        //编辑-图像反转 按钮监听器
        jmi_edit_negative.addActionListener((ActionEvent e)->{
            if(bi!=null){
                bi = ImageTools.image_negative(bi);
                update();
                jf_main.setTitle("Photoshop---图像翻转");
            }

        });
        //编辑-图像混合 按钮监听器
        jmi_edit_alpha_mix.addActionListener((ActionEvent e)->new alpha_mix_frame());
        //编辑-缩放（最近邻内插法）... 按钮监听器
        jmi_edit_resize_NNI.addActionListener((ActionEvent e)->{
            JTextField jta = new JTextField();
            try {
                JOptionPane.showMessageDialog(null, jta, "请输入缩放系数", JOptionPane.PLAIN_MESSAGE);
                bi = ImageTools.image_resize_NNI(bi, Float.parseFloat(jta.getText()));
                update();
                jf_main.setTitle("Photoshop---图像缩放（最近邻内插法）");
            }catch (Exception e1){
                JOptionPane.showMessageDialog(null, "参数错误！", "错误", JOptionPane.ERROR_MESSAGE);
            };
        });
        //编辑-缩放（双线形内插法）... 按钮监听器
        jmi_edit_resize_BI.addActionListener((ActionEvent e)->{
            try {
                JTextField jta = new JTextField();
                JOptionPane.showMessageDialog(null, jta, "请输入缩放系数", JOptionPane.PLAIN_MESSAGE);
                bi = ImageTools.image_resize_BI(bi, Float.parseFloat(jta.getText()));
                update();
                jf_main.setTitle("Photoshop---图像缩放（双线形内插法）");
            }catch (Exception e1){
                JOptionPane.showMessageDialog(null, "参数错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        //编辑-旋转（双线形内插法）... 按钮监听器
        jmi_edit_rotate_BI.addActionListener((ActionEvent e)->{
            try {
                JTextField jta = new JTextField();
                JOptionPane.showMessageDialog(null, jta, "请输入旋转角度", JOptionPane.PLAIN_MESSAGE);
                bi = ImageTools.image_rotate_BI(bi, Float.parseFloat(jta.getText()));
                update();
                jf_main.setTitle("Photoshop---图像旋转（双线形内插法）");
            }catch (Exception e1){
                JOptionPane.showMessageDialog(null, "参数错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        //编辑-旋转（最近邻内插法）... 按钮监听器
        jmi_edit_rotate_NNI.addActionListener((ActionEvent e)->{
            try {
                JTextField jta = new JTextField();
                JOptionPane.showMessageDialog(null, jta, "请输入旋转角度", JOptionPane.PLAIN_MESSAGE);
                bi = ImageTools.image_rotate_NNI(bi, Float.parseFloat(jta.getText()));
                update();
                jf_main.setTitle("Photoshop---图像旋转（最近邻内插法）");
            }catch (Exception e1){
                JOptionPane.showMessageDialog(null, "参数错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        jm_edit_transform_gray.addActionListener((ActionEvent e)->{
            bi = ImageTools.Image_transform_gray(bi);
            update();
            jf_main.setTitle("Photoshop---图像灰度拉伸");
        });
        jm_edit_transform_gamma.addActionListener((ActionEvent e)->{
            JSlider js = new JSlider(0,100,20);
            JOptionPane.showMessageDialog(null, js, "请输入旋转角度", JOptionPane.PLAIN_MESSAGE);
            js.addChangeListener((ChangeEvent e1)->{
                System.out.print(js.getValue());
                bi = ImageTools.Image_transform_gamma(bi,js.getValue());
                update();
            });
            jf_main.setTitle("Photoshop---Gamma变换");
        });
        //编辑-重置图像 按钮监听器
        jmi_edit_restore.addActionListener((ActionEvent e)->{restoreImage();jf_main.setTitle("Photoshop");});
    }//初始化结束
    //设置更新主窗口图像的方法
    static void setBi(BufferedImage bi) {
        main_frame.bi = bi;
        update();
    }
    //重置、打开图像的方法
    private static void restoreImage(){
        try {
            src = ImageIO.read(new FileInputStream(s_file));
            initBi();
            if(src.getHeight()>jf_main.getHeight()||src.getWidth()>jf_main.getWidth()) {
                //bi = ImageTools.image_zoom(bi,jp_image.getWidth(),jp_image.getHeight(),0);
                update();
            }else jl_image.setIcon(new ImageIcon(src));
            isEnable(true);
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "文件未找到！", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e1) {
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
        jfc_open = new JFileChooser();
        jfc_open.setFileFilter(fnef_jpg);
        jfc_open.addChoosableFileFilter(fnef_png);
        jfc_open.addChoosableFileFilter(fnef_bmp);
        jfc_open.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc_open.showOpenDialog(jf_main);
        jfc_open.setMultiSelectionEnabled(false);
        jfc_open.setAcceptAllFileFilterUsed(false);
        try {
            s_file =jfc_open.getSelectedFile().getPath();
        } catch (Exception e1) {
            //JOptionPane.showMessageDialog(null, "该文件不是有效的图像文件", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    static private void save_file(){
        jfc_save = new JFileChooser();
        jfc_open.setFileFilter(fnef_jpg);
        jfc_save.showSaveDialog(jf_main);
        File f = new File(jfc_save.getSelectedFile().getPath()+".jpg");
        try {
            ImageIO.write(bi, "jpg", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //设置菜单栏按钮是否可用
    static private void isEnable(boolean b){
        jmi_edit_negative.setEnabled(b);
        jmi_edit_alpha_mix.setEnabled(b);
        jmi_edit_restore.setEnabled(b);
        jmi_file_save.setEnabled(b);
        jmi_edit_resize_NNI.setEnabled(b);
        jmi_edit_resize_BI.setEnabled(b);
        jmi_edit_rotate_NNI.setEnabled(b);
        jmi_edit_rotate_BI.setEnabled(b);
    }
    //主方法
    public static void main(String[] args) {
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
        //生成窗口
        init();
    }
}