package me.jacoblu.pswithjava.views;

import me.jacoblu.pswithjava.methods.ImageAlphaMix;
import me.jacoblu.pswithjava.tools.Tools;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

class AlphaMixFrame {
	private static BufferedImage output = null;
	static private Color c = null;
	static private int mouseX = 0, mouseY = 0;
	static private int mouse_startX, mouse_startY, mouse_endX, mouse_endY;
	static private int moveX, moveY;
	static private JFrame jf_alpha_mix = null;
	static private JLabel jl_image = null;
	static private JSlider js_alpha = null;
	static private JSlider js_valve = null;
	static private BufferedImage mix_origin = null;
	static private Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
	
	private static void init_frame(final BufferedImage bi) {
		jf_alpha_mix = new JFrame("图像混合");
		jf_alpha_mix.setSize((int) (screen_size.width * 0.8), (int) (screen_size.height * 0.8));
		jf_alpha_mix.setLayout(new BorderLayout());
		JPanel jp_image = new JPanel();
		jl_image = new JLabel();
		JLabel jl_text_valve = new JLabel("阈值:");
		JLabel jl_text_alpha = new JLabel("透明度:");
		jp_image.add(jl_image);
		JScrollPane jsp_image = new JScrollPane(jp_image);
		jsp_image.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp_image.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JPanel jp_props = new JPanel();
		JButton jb_confirm = new JButton("确认");
		JButton jb_selbgcolor = new JButton("选取背景色");
		js_alpha = new JSlider(0, 100, 0);
		js_valve = new JSlider(0, 200, 20);
		js_valve.setEnabled(false);
		jp_props.add(jl_text_valve);
		jp_props.add(js_valve);
		jp_props.add(jl_text_alpha);
		jp_props.add(js_alpha);
		jp_props.add(jb_selbgcolor);
		jp_props.add(jb_confirm);
		jf_alpha_mix.add(jsp_image, "Center");
		jf_alpha_mix.add(jp_props, "South");
		jf_alpha_mix.setLocationRelativeTo(MainFrame.jf_main);
		jf_alpha_mix.setVisible(true);
		jf_alpha_mix.setResizable(false);
		mix_origin = MainFrame.bi;
		output = new ImageAlphaMix(mix_origin, bi, (float) (js_alpha.getValue()) / 100, c, js_valve.getValue(), moveX, moveY).getOutput();
		jl_image.setIcon(new ImageIcon(output));
		JOptionPane.showMessageDialog(null, "用鼠标拖动图片到合适位置", "提示", JOptionPane.INFORMATION_MESSAGE);
		ChangeListener cl = (ChangeEvent e) -> {
			output = new ImageAlphaMix(mix_origin, bi, (float) (js_alpha.getValue()) / 100, c, js_valve.getValue(), moveX, moveY).getOutput();
			jl_image.setIcon(new ImageIcon(output));
		};
		js_alpha.addChangeListener(cl);
		js_valve.addChangeListener(cl);
		jb_confirm.addActionListener((ActionEvent e) -> {
			MainFrame.setBi(new ImageAlphaMix(MainFrame.bi, bi, (float) (js_alpha.getValue()) / 100, c, js_valve.getValue(), moveX, moveY).getOutput());
			mouseX = 0;
			moveX = 0;
			mouseY = 0;
			moveY = 0;
			c = null;
			jf_alpha_mix.dispose();
		});
		jb_selbgcolor.addActionListener((ActionEvent e) -> {
			JOptionPane.showMessageDialog(null, "单击一个点来选择背景颜色，然后改变阈值滑条来预览效果", "提示", JOptionPane.INFORMATION_MESSAGE);
			jl_image.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					int r, g, b;
					mouseX = arg0.getX();
					mouseY = arg0.getY();
					try {
						Object data = output.getRaster().getDataElements(mouseX, mouseY, null);
						r = output.getColorModel().getRed(data);
						g = output.getColorModel().getGreen(data);
						b = output.getColorModel().getBlue(data);
						c = new Color(r, g, b);
						jf_alpha_mix.setTitle("图像混合----点击坐标:（" + mouseX + "," + mouseY + "）RGB:（" + r + "," + g + "," + b + "）");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "无法获取改点像素值", "错误", JOptionPane.ERROR_MESSAGE);
					}
					js_valve.setEnabled(true);
				}
			});
		});
		jp_image.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				mouse_startX = arg0.getX();
				mouse_startY = arg0.getY();
			}
		});
		jp_image.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent arg0) {
				mouse_endX = arg0.getX();
				mouse_endY = arg0.getY();
				moveX = mouse_endX - mouse_startX;
				moveY = mouse_endY - mouse_startY;
				output = new ImageAlphaMix(mix_origin, bi, (float) (js_alpha.getValue()) / 100, c, js_valve.getValue(), moveX, moveY).getOutput();
				jl_image.setIcon(new ImageIcon(output));
			}
			
			public void mouseMoved(MouseEvent arg0) {
			
			}
		});
	}
	
	private static BufferedImage init_chooser() {
		BufferedImage bi;
		String file_path = null;
		FileNameExtensionFilter fnef_jpg = new FileNameExtensionFilter("JPG Images", "jpg", "jpeg");
		FileNameExtensionFilter fnef_png = new FileNameExtensionFilter("PNG Images", "png");
		FileNameExtensionFilter fnef_bmp = new FileNameExtensionFilter("BMP Images", "bmp");
		JFileChooser jfc_open = new JFileChooser();
		jfc_open.setFileFilter(fnef_jpg);
		jfc_open.addChoosableFileFilter(fnef_png);
		jfc_open.addChoosableFileFilter(fnef_bmp);
		jfc_open.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc_open.showOpenDialog(null);
		jfc_open.setMultiSelectionEnabled(false);
		jfc_open.setAcceptAllFileFilterUsed(false);
		try {
			file_path = jfc_open.getSelectedFile().getPath();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "该文件不是有效的图像文件", "错误", JOptionPane.ERROR_MESSAGE);
		}
		try {
			assert file_path != null;
			bi = ImageIO.read(new FileInputStream(file_path));
		} catch (Exception e) {
			return null;
		}
		return bi;
	}
    
    public AlphaMixFrame() {
		Tools.ChangeUI();
		BufferedImage input = init_chooser();
		if (input != null) init_frame(input);
	}
}