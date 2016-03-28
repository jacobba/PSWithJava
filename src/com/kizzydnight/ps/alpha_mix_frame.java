package com.kizzydnight.ps;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class alpha_mix_frame {
	public static BufferedImage output = null;
	static BufferedImage input = null;
	static JFrame jf_alpha_mix = null;
	static JLabel jl_image = null;
	static JPanel jp_image = null;
	static JPanel jp_props = null;
	static JButton jb_confirm = null;
	static JButton jb_cancel = null;
	static JSlider js_alpha = null;
	static JFileChooser jfc_open = null;
	static String s_file = null;
	static BufferedImage mix_origin = null;
	static Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
	
	private static void init_frame(BufferedImage bi){
		jf_alpha_mix = new JFrame("Alpha Mix Options");
		jf_alpha_mix.setSize((int)(screen_size.height*0.7), (int)(screen_size.height*0.5));
		jf_alpha_mix.setLayout(new BorderLayout());
		jp_image = new JPanel();
		jl_image = new JLabel();
		jp_image.add(jl_image);
		mix_origin = ImageTools.image_zoom(main_frame.bi, jf_alpha_mix.getWidth(), jf_alpha_mix.getHeight()-50, 0);
		output = ImageTools.image_alpha_mix(mix_origin, bi, 0.5f);
		jl_image.setIcon(new ImageIcon(output));
		jp_props = new JPanel();
		jb_confirm = new JButton("Confirm");
		jb_cancel = new JButton("Cancel");
		js_alpha = new JSlider(0,100,50);
		jp_props.add(js_alpha);
		jp_props.add(jb_confirm);
		jp_props.add(jb_cancel);
		jf_alpha_mix.add(jp_image,"Center");
		jf_alpha_mix.add(jp_props,"South");
		jf_alpha_mix.setLocationRelativeTo(main_frame.jf_main);
		jf_alpha_mix.setVisible(true);
		jf_alpha_mix.setResizable(false);
		js_alpha.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				output = ImageTools.image_alpha_mix(mix_origin, bi, (float)(js_alpha.getValue())/100);
				jl_image.setIcon(new ImageIcon(output));
			}
		});
		jb_confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				main_frame.setBi(ImageTools.image_alpha_mix(main_frame.bi, bi, (float)(js_alpha.getValue())/100));
				jf_alpha_mix.dispose();
			}
		});
		jb_cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				jf_alpha_mix.dispose();
			}
		});
	}
	private static BufferedImage init_chooser(){
			BufferedImage bi = null;
			String file_path = null;
			FileNameExtensionFilter fnef_jpg = new FileNameExtensionFilter("JPG Images", "jpg","jpeg");
			FileNameExtensionFilter fnef_png = new FileNameExtensionFilter("PNG Images", "png");
			FileNameExtensionFilter fnef_bmp = new FileNameExtensionFilter("BMP Images", "bmp");
			jfc_open = new JFileChooser();
			jfc_open.setFileFilter(fnef_jpg);
			jfc_open.addChoosableFileFilter(fnef_png);
			jfc_open.addChoosableFileFilter(fnef_bmp);
			jfc_open.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc_open.showOpenDialog(null);
			jfc_open.setMultiSelectionEnabled(false);
			jfc_open.setAcceptAllFileFilterUsed(false);
			try {
			file_path =jfc_open.getSelectedFile().getPath();
			} catch (Exception e1) {
				//JOptionPane.showMessageDialog(null, "File is not a valid image", "Error", JOptionPane.ERROR_MESSAGE); 
			}
			try {
				bi = ImageIO.read(new FileInputStream(file_path));
			}  catch (Exception e) {
				return null;
			}
			return bi;
	}
	alpha_mix_frame(){
		input = init_chooser();
		if(input!=null) init_frame(input);
		//getOutputImage();
	}
	BufferedImage getOutputImage(){
		return output;
	} 
}
