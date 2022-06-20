package me.jacoblu.pswithjava.views;

import me.jacoblu.pswithjava.methods.ImageDrawIntensity;
import me.jacoblu.pswithjava.tools.Tools;

import javax.swing.*;
import java.awt.image.BufferedImage;

class IntensityFrame {
	private static JLabel gray_jl = null;
	private static JLabel red_jl = null;
	private static JLabel green_jl = null;
	private static JLabel blue_jl = null;
	
	private void init() {
		JFrame jf_intensity_frame = new JFrame("图像直方图");
		gray_jl = new JLabel();
		red_jl = new JLabel();
		green_jl = new JLabel();
		blue_jl = new JLabel();
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("Gray", gray_jl);
		jtp.addTab("Red", red_jl);
		jtp.addTab("Green", green_jl);
		jtp.addTab("Blue", blue_jl);
		
		jf_intensity_frame.setSize(280, 330);
		jf_intensity_frame.add(jtp);
		jf_intensity_frame.setVisible(true);
		jf_intensity_frame.setLocationRelativeTo(MainFrame.jf_main);
		jf_intensity_frame.setResizable(false);
	}
	
	public IntensityFrame(BufferedImage bi) {
		Tools.ChangeUI();
		init();
		BufferedImage gray_output = new ImageDrawIntensity(bi).getGray_output();
		BufferedImage red_output = new ImageDrawIntensity(bi).getRed_output();
		BufferedImage green_output = new ImageDrawIntensity(bi).getGreen_output();
		BufferedImage blue_output = new ImageDrawIntensity(bi).getBlue_output();
		gray_jl.setIcon(new ImageIcon(gray_output));
		red_jl.setIcon(new ImageIcon(red_output));
		green_jl.setIcon(new ImageIcon(green_output));
		blue_jl.setIcon(new ImageIcon(blue_output));
	}
}
