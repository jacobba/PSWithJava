package com.kizzydnight.ps;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;

public class main_frame {
	static String s_file = null;
	static JFrame jf_main = null;
	static JLabel jl_image = null;
	static JPanel jp_image = null;
	static JMenuBar jmb_main = null;
	static JMenu jm_file = null;
	static JMenu jm_edit = null;
	static JMenu jm_help = null;
	static JMenuItem jmi_file_open = null;
	static JMenuItem jmi_edit_negative = null;
	static JMenuItem jmi_edit_restore = null;
	static JMenuItem jmi_edit_alpha_mix = null;
	static JMenuItem jmi_help_about = null;
	static JFileChooser jfc_open = null;
	//获取屏幕尺寸
	static Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
	//文件过滤器
	static FileNameExtensionFilter fnef_jpg = new FileNameExtensionFilter("JPG Images", "jpg","jpeg");
	static FileNameExtensionFilter fnef_png = new FileNameExtensionFilter("PNG Images", "png");
	static FileNameExtensionFilter fnef_bmp = new FileNameExtensionFilter("BMP Images", "bmp");
	public static BufferedImage bi = null;
	public static BufferedImage src = null;
	//初始化
	static void init(){
		//初始化组件
		jf_main = new JFrame("PS");
		jl_image = new JLabel();
		jp_image = new JPanel();
		jmb_main = new JMenuBar();
		jm_file = new JMenu("文件");
		jm_edit = new JMenu("编辑");
		jm_help = new JMenu("帮助");
		jmi_file_open = new JMenuItem("打开...");
		jmi_edit_negative = new JMenuItem("图像反转");
		jmi_edit_restore = new JMenuItem("重置图像");
		jmi_edit_alpha_mix = new JMenuItem("图像混合...");
		jmi_help_about = new JMenuItem("关于...");
		//快捷键
		jmi_file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		jmi_edit_restore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
		jmi_edit_negative.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		jmi_edit_alpha_mix.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,ActionEvent.CTRL_MASK));
		//向窗口中添加组件
		jf_main.add(jp_image,"Center");
		jp_image.add(jl_image);
		jmb_main.add(jm_file);
		jmb_main.add(jm_edit);
		jmb_main.add(jm_help);
		jm_file.add(jmi_file_open);
		jm_edit.add(jmi_edit_restore);
		jm_edit.add(jmi_edit_negative);
		jm_edit.add(jmi_edit_alpha_mix);
		jm_help.add(jmi_help_about);
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
		jmi_file_open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				open_file();
				restoreImage();
			}	
		}); 
		//编辑-图像反转 按钮监听器
		jmi_edit_negative.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(bi!=null){
					bi = ImageTools.image_negative(bi);
					update();
				}
			}
		});
		//编辑-图像混合 按钮监听器
		jmi_edit_alpha_mix.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new alpha_mix_frame();	
			}
		});
		//编辑-重置图像 按钮监听器
		jmi_edit_restore.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				restoreImage();
			}
		});
	}//初始化结束
	//设置更新主窗口图像的方法
	public static void setBi(BufferedImage bi) {
		main_frame.bi = bi;
		update();
	}
	//重置、打开图像的方法
	public static void restoreImage(){
		try {
			src = ImageIO.read(new FileInputStream(s_file));
			initBi();
			if(src.getHeight()>jf_main.getHeight()||src.getWidth()>jf_main.getWidth()) {
				bi = ImageTools.image_zoom(bi,jp_image.getWidth(),jp_image.getHeight(),0);
				update();
			}else jl_image.setIcon(new ImageIcon(src));
			isEnable(true);
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "文件未找到！", "错误", JOptionPane.ERROR_MESSAGE); 
		} catch (Exception e1) {
		}
	}
	//初始化临时图像
	static void initBi(){
			try {
				bi = ImageIO.read(new FileInputStream(s_file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "无法加载图像！", "错误", JOptionPane.ERROR_MESSAGE); 
			}	
	}
	 //更新主窗口图像
	static void update(){
		jl_image.setIcon(new ImageIcon(bi));
	}
	 //文件选择器
	static void open_file(){
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
			JOptionPane.showMessageDialog(null, "该文件不是有效的图像文件", "错误", JOptionPane.ERROR_MESSAGE);
		}
	}
	//设置菜单栏按钮是否可用
     static void isEnable(boolean b){
    	 jmi_edit_negative.setEnabled(b);
    	 jmi_edit_alpha_mix.setEnabled(b);
    	 jmi_edit_restore.setEnabled(b);
     }
	//主方法
	public static void main(String[] args) {
		init();
	}
}
