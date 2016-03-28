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
	//static BorderLayout blo = new BorderLayout();
	//get screen size
	static Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
	//file filters
	static FileNameExtensionFilter fnef_jpg = new FileNameExtensionFilter("JPG Images", "jpg","jpeg");
	static FileNameExtensionFilter fnef_png = new FileNameExtensionFilter("PNG Images", "png");
	static FileNameExtensionFilter fnef_bmp = new FileNameExtensionFilter("BMP Images", "bmp");
	public static BufferedImage bi = null;
	public static BufferedImage src = null;
	//init components
	
	static void init(){
		//init
		jf_main = new JFrame("PS");
		jl_image = new JLabel();
		jp_image = new JPanel();
		jmb_main = new JMenuBar();
		jm_file = new JMenu("File");
		jm_edit = new JMenu("Edit");
		jm_help = new JMenu("Help");
		jmi_file_open = new JMenuItem("Open...");
		jmi_edit_negative = new JMenuItem("Negative");
		jmi_edit_restore = new JMenuItem("Restore");
		jmi_edit_alpha_mix = new JMenuItem("Alpha Mix...");
		jmi_help_about = new JMenuItem("About...");
		//key stroke
		jmi_file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		jmi_edit_restore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
		jmi_edit_negative.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		//add
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
		//set main frame props 
		jf_main.setJMenuBar(jmb_main);
		jf_main.setSize((int)(screen_size.width*0.8),(int)(screen_size.height*0.8));
		jf_main.setResizable(true);
		jf_main.setLocationRelativeTo(null);
		jf_main.setVisible(true);
		jf_main. setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		//
		jf_main.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int isClose = JOptionPane.showConfirmDialog(null, "Close Application ?", "Asking", JOptionPane.YES_NO_OPTION);
				if(isClose==0) System.exit(0);
			}
		});
		//add file-open function
		jmi_file_open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				open_file();
				restoreImage();
			}	
		}); 
		//add image negative
		jmi_edit_negative.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(bi!=null){
					bi = ImageTools.image_negative(bi);
					update();
				}
			}
		});
		//add a pic to exist one
		jmi_edit_alpha_mix.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new alpha_mix_frame();	
			}
		});
		jmi_edit_restore.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				restoreImage();
			}
		});
		jf_main.addWindowListener(new WindowAdapter(){
			public void windowActivated(WindowEvent e){
				
			}
		});
	}//end init()

	public static void setBi(BufferedImage bi) {
		main_frame.bi = bi;
		update();
	}

	public static void restoreImage(){
		try {
			src = ImageIO.read(new FileInputStream(s_file));
			initBi();
			if(src.getHeight()>jf_main.getHeight()||src.getWidth()>jf_main.getWidth()) {
				bi = ImageTools.image_zoom(bi,jp_image.getWidth(),jp_image.getHeight(),0);
				update();
			}else jl_image.setIcon(new ImageIcon(src));
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "File Not Found", "Error", JOptionPane.ERROR_MESSAGE); 
		} catch (Exception e1) {
		}
	}
	//init BufferedImage bi
	static void initBi(){
			try {
				bi = ImageIO.read(new FileInputStream(s_file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "No image file was loaded!", "Error", JOptionPane.ERROR_MESSAGE); 
			}	
	}
	 //update
	static void update(){
		jl_image.setIcon(new ImageIcon(bi));
	}
	 //method of open files
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
		}
	}
  
      
	//main method
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		init();
	}
}
