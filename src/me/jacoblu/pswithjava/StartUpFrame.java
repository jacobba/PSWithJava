package me.jacoblu.pswithjava;

import me.jacoblu.pswithjava.tools.Tools;
import me.jacoblu.pswithjava.views.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Created by JacobLu on 6/7/16.
 */
class StartUpFrame {
	private static JFrame jf_start_up = null;
	private static JLabel jl_start = null;
	private static String imgPath = "start_up.jpg";
	private static BufferedImage start_up;
	private static JProgressBar jpb_start;
	private static int x = Tools.screen_y;
	private static int y = (int) (Tools.screen_y * 0.5625);
	private static int speed = 15;
	private static Timer timer;
	
	private static void init() {
		try {
			start_up = ImageIO.read(new FileInputStream(imgPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage sized = new BufferedImage(x, y, start_up.getType());
		sized.getGraphics().drawImage(start_up.getScaledInstance(x, y, Image.SCALE_SMOOTH), 0, 0, null);
		jl_start = new JLabel();
		jl_start.setIcon(new ImageIcon(sized));
		jf_start_up = new JFrame();
		jf_start_up.setUndecorated(true);
		jf_start_up.setSize(x, y);
		jf_start_up.setLocationRelativeTo(null);
		jf_start_up.setLayout(new BorderLayout());
		jf_start_up.add(jl_start, "Center");
		jpb_start = new JProgressBar();
		jpb_start.setOrientation(JProgressBar.HORIZONTAL);
		jpb_start.setMinimum(0);
		jpb_start.setMaximum(100);
		jpb_start.setValue(0);
		jpb_start.setStringPainted(false);
		jpb_start.setBorderPainted(false);
		jpb_start.setPreferredSize(new Dimension(y, 5));
		jf_start_up.add(jpb_start, "South");
		timer = new Timer(100, (ActionEvent e) -> {
			int value = jpb_start.getValue();
			if (value < 100) {
				jpb_start.setValue(value += Math.random() * speed);
			} else {
				new MainFrame();
				jf_start_up.dispose();
				timer.stop();
			}
		});
		
		jf_start_up.setVisible(true);
	}
	
	public static void main(String[] srgs) {
		Tools.ChangeUI();
		init();
		timer.start();
	}
}
