package com.cowthan.swing;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.cowthan.cmd.Cmd;
import com.cowthan.cmd.CmdLineWindow;

public class MDMInstallTool {
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JPanel buttonPanel;
	private JPanel controlPanel;
	private JLabel msglabel;
	private String filePathName;

	public MDMInstallTool() {
		prepareGUI();
	}

	public static void main(String[] args) {
		MDMInstallTool swingContainerDemo = new MDMInstallTool();
		swingContainerDemo.showJFrameDemo();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Java Swing Examples");
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		headerLabel = new JLabel("", JLabel.CENTER);

		msglabel = new JLabel("Welcome to TutorialsPoint SWING Tutorial.",
				JLabel.CENTER);

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		buttonPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(buttonPanel);
		mainFrame.setVisible(true);
	}

	private void showJFrameDemo() {
		headerLabel.setText("请选择设备类型:");

		final JFrame frame = new JFrame();
		frame.setSize(300, 300);
		frame.setLayout(new FlowLayout());
		frame.add(msglabel);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				frame.dispose();
			}
		});
		ButtonGroup bg = new ButtonGroup();
		JRadioButton normalButton = new JRadioButton("Android5.0以下非定制机");
		JRadioButton normalButton1 = new JRadioButton("Android5.1以上非定制机");
		JRadioButton normalButton2 = new JRadioButton("华为EMU4.1系列，OPPOR9和A37手机");
		GridLayout gridLayout = new GridLayout(3, 1);
		bg.add(normalButton);
		bg.add(normalButton1);
		bg.add(normalButton2);
		normalButton.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				JRadioButton jop = (JRadioButton) e.getSource();
				if (jop.isSelected()) {
					filePathName = "normal-below21"; //Android5.0以下非定制机
				}
			}
		});
		normalButton1.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				JRadioButton jop = (JRadioButton) e.getSource();
				if (jop.isSelected()) {
					filePathName = "normal-above22"; //Android5.1以上非定制机
				}
			}
		});
		normalButton2.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				JRadioButton jop = (JRadioButton) e.getSource();
				if (jop.isSelected()) {
					filePathName = "normal-special"; //华为EMU4.1系列及OPPOR9和A37手机
				}
			}
		});
		JPanel pan = new JPanel();
		pan.setLayout(gridLayout);
		pan.add(normalButton);
		pan.add(normalButton1);
		pan.add(normalButton2);
		controlPanel.add(pan);
		JButton installButton = new JButton("开始安装");
		installButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openBatFile();
				/*String msg = getStringFromFile();
				if (msg != null) {
					msglabel.setText(msg);
				} else {
					msglabel.setText("没有选择手机");
				}
				frame.setVisible(true);*/
			}
		});
		buttonPanel.add(installButton);
		mainFrame.setVisible(true);
	}
	
	private void openBatFile(){
		Runtime run= Runtime.getRuntime();
		if (filePathName == null) {
			return;
		} else {
			String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
			if(path.startsWith("/")){
				path = path.substring(1);
			}
			System.out.println(path);  //F:\ws-jee\JavaAyo\aa.dd
			try {
				path = URLDecoder.decode(path, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			path = path.substring(0, path.lastIndexOf("/")) + "/" + filePathName + "/adb.bat";
			System.out.println(path);
			try {
				//run.exec(path);
				//Cmd.dir(new File(path).getParentFile());
				
				CmdLineWindow c = new CmdLineWindow();
				String s = c.exec("adb.bat", new File(path).getParentFile());
				
				//String s = new CmdLineWindow().exec("cc.bat", null);
				System.out.println("结果：" + s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getStringFromFile() {
		if (filePathName == null) {
			return null;
		} else {
			String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
			try {
				path = URLDecoder.decode(path, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			path = path.substring(0, path.lastIndexOf("/"));
			System.out.println(path);
			String result;
			try {
				File file = new File(path + "/" + filePathName + "/readme.txt");
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), "utf-8");// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				result = bufferedReader.readLine();
				System.out.println(result);
				read.close();
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
