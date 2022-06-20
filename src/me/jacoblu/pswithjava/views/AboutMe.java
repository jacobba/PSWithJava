package me.jacoblu.pswithjava.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;
import java.io.IOException;
/**
 * Created by JacobLu on 7/5/16.
 */
public class AboutMe {
    public AboutMe(){
        JFrame jf_aboutme = new JFrame("关于我");
        JTextArea jta = new JTextArea();
        jta.setEditable(false);
        jta.setText("鲁周民jacobba\n\n山东财经大学\n计算机科学与技术学院\n数字媒体技术专业\n\n2016.07.05  MIT License\n" +
                "喜欢编程与设计\n\n联系方式:\nQQ:3021931632\nTel:15306408061\n\n欢迎访问我的GitHub地址。");
        JPanel jp_down = new JPanel();
        JButton jb_github = new JButton("访问我的GitHub");
        jf_aboutme.setLocationRelativeTo(null);
        jf_aboutme.setSize(400,300);
        jf_aboutme.setResizable(false);
        jf_aboutme.setLayout(new BorderLayout());
        jf_aboutme.add(jta,"Center");
        jf_aboutme.add(jp_down,"South");
        jp_down.add(jb_github);
        jf_aboutme.setVisible(true);

        jb_github.addActionListener((ActionEvent e)->{
            try {
                URI uri = new URI("https://github.com/zmlu");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });
    }
}
