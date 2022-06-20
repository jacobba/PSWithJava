package me.jacoblu.pswithjava.methods;

import me.jacoblu.pswithjava.tools.Tools;
import me.jacoblu.pswithjava.views.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * Created by JacobLu on 6/6/16.
 */
public class EdgeDetectionFrame {
	BufferedImage input;
	private JFrame jf_edge_detection;
	
	private JPanel jp_bot;
	private JPanel jp_middle;
	private JPanel jp_middle_origin;
	private JPanel jp_middle_prewitt;
	private JPanel jp_middle_sobel;
	private JPanel jp_middle_roberts;
	
	private JLabel jl_origin;
	private JLabel jl_sobel;
	private JLabel jl_prewitt;
	private JLabel jl_roberts;
	private JLabel jl_threshold_value;
	private JComboBox jcb_mathods;
	private JSlider js_threshold;
	private JButton jb_confirm;
	static private Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
	
	private void init() {
		jf_edge_detection = new JFrame("边缘提取");
		jp_bot = new JPanel();
		
		jb_confirm = new JButton("保存");
		jcb_mathods = new JComboBox();
		jcb_mathods.addItem("Sobel算子");
		jcb_mathods.addItem("Prewitt算子");
		jcb_mathods.addItem("Roberts算子");
		js_threshold = new JSlider(0, 500, 250);
		jp_bot = new JPanel();
		jp_middle = new JPanel();
		jl_threshold_value = new JLabel();
		jf_edge_detection.setSize((int) (screen_size.width * 0.8), (int) (screen_size.height * 0.8));
		
		jf_edge_detection.setLocationRelativeTo(MainFrame.jf_main);
		jf_edge_detection.setLayout(new BorderLayout());
		
		
		jp_bot.add(new JLabel("阈值:"));
		jp_bot.add(js_threshold);
		jp_bot.add(jl_threshold_value);
		jp_bot.add(jcb_mathods);
		jp_bot.add(jb_confirm);
		jp_middle.setLayout(new GridLayout(2, 2));
		
		jl_origin = new JLabel();
		jl_prewitt = new JLabel();
		jl_roberts = new JLabel();
		jl_sobel = new JLabel();
		jp_middle_origin = new JPanel();
		jp_middle_sobel = new JPanel();
		jp_middle_prewitt = new JPanel();
		jp_middle_roberts = new JPanel();
		jp_middle_origin.setLayout(new BorderLayout());
		jp_middle_sobel.setLayout(new BorderLayout());
		jp_middle_prewitt.setLayout(new BorderLayout());
		jp_middle_roberts.setLayout(new BorderLayout());
		jp_middle_origin.add(new JScrollPane(jl_origin), "Center");
		jp_middle_origin.add(new JLabel("原图"), "South");
		jp_middle_sobel.add(new JScrollPane(jl_sobel), "Center");
		jp_middle_sobel.add(new JLabel("Sobel算子效果"), "South");
		jp_middle_prewitt.add(new JScrollPane(jl_prewitt), "Center");
		jp_middle_prewitt.add(new JLabel("Prewitt算子效果"), "South");
		jp_middle_roberts.add(new JScrollPane(jl_roberts), "Center");
		jp_middle_roberts.add(new JLabel("Roberts算子效果"), "South");
		
		jp_middle.add(jp_middle_origin);
		jp_middle.add(jp_middle_sobel);
		jp_middle.add(jp_middle_prewitt);
		jp_middle.add(jp_middle_roberts);
		
		jf_edge_detection.add(jp_middle, "Center");
		jf_edge_detection.add(jp_bot, "South");
		jf_edge_detection.setVisible(true);
		js_threshold.addChangeListener((ChangeEvent e) -> {
			jl_sobel.setIcon(new ImageIcon(new EdgeDetection(input).Sobel(js_threshold.getValue())));
			jl_prewitt.setIcon(new ImageIcon(new EdgeDetection(input).Prewitt(js_threshold.getValue())));
			jl_roberts.setIcon(new ImageIcon(new EdgeDetection(input).Roberts()));
			jl_threshold_value.setText(js_threshold.getValue() + "");
		});
		jb_confirm.addActionListener((ActionEvent e) -> {
			switch (jcb_mathods.getSelectedIndex()) {
				case 0:
					Tools.saveFile(new EdgeDetection(input).Sobel(js_threshold.getValue()), jf_edge_detection);
					break;
				case 1:
					Tools.saveFile(new EdgeDetection(input).Prewitt(js_threshold.getValue()), jf_edge_detection);
					break;
				case 2:
					Tools.saveFile(new EdgeDetection(input).Roberts(), jf_edge_detection);
					break;
				default:
				
			}
		});
	}
	
	public EdgeDetectionFrame(BufferedImage bi) {
		input = bi;
		Tools.ChangeUI();
		init();
		jl_origin.setIcon(new ImageIcon(input));
		jl_sobel.setIcon(new ImageIcon(new EdgeDetection(input).Sobel(js_threshold.getValue())));
		jl_prewitt.setIcon(new ImageIcon(new EdgeDetection(input).Prewitt(js_threshold.getValue())));
		jl_roberts.setIcon(new ImageIcon(new EdgeDetection(input).Roberts()));
		
	}
}
