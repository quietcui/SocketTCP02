package com.Socketnumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDemo extends JFrame implements ActionListener {
	private JTextArea chatArea;// ；聊天记录区域
	public JTextField inputField;// 消息输入框
	private JButton sendButton;// 发送消息按钮
	private String name;
	private PrintWriter out;
	private BufferedReader in;

	/**
	 * 
	 */
	public void init() {
		setTitle("多人聊天 - " + name); // 设置窗口标题
		setSize(600, 400); // 设置窗口大小
		setLocationRelativeTo(null); // 设置窗口居中
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 设置窗口关闭时退出程序

		chatArea = new JTextArea(); // 创建聊天记录区域
		chatArea.setEditable(false); // 设置不可编辑
		chatArea.setLineWrap(true); // 设置自动换行

		inputField = new JTextField(); // 创建输入消息框
		inputField.addActionListener(this); // 添加回车事件监听器

		sendButton = new JButton("发送"); // 创建发送消息按钮
		sendButton.addActionListener(this); // 添加点击事件监听器

		JScrollPane chatPane = new JScrollPane(chatArea); // 创建聊天记录区域的滚动面板
		// JScrollPane userPane = new JScrollPane(userList); // 创建在线用户列表的滚动面板

		JPanel inputPanel = new JPanel(new BorderLayout()); // 创建输入面板，使用边界布局
		inputPanel.add(inputField, BorderLayout.CENTER); // 将输入消息框放在中间
		inputPanel.add(sendButton, BorderLayout.EAST); // 将发送消息按钮放在右边

		add(chatPane, BorderLayout.CENTER); // 将聊天记录区域的滚动面板放在窗口中间
		// add(userPane, BorderLayout.EAST); // 将在线用户列表的滚动面板放在窗口右边
		add(inputPanel, BorderLayout.SOUTH); // 将输入面板放在窗口下边

		setVisible(true); // 设置窗口可见7
	}

	public void appendMessage(String message) {
		chatArea.append(message + "\n");
		chatArea.setCaretPosition(chatArea.getDocument().getLength());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = inputField.getText().trim(); // 获取输入消息框的内容，并去除首尾空格
		if (!message.isEmpty()) { // 如果消息不为空
			out.println(message); // 向服务器发送消息
			out.flush();
			inputField.setText(""); // 清空输入消息框的内容
			appendMessage("服务端：" + message);
		}
	}

	public void Socket1() {
		init();
		ServerSocket serversocket = null;
		Socket clientsocket = null;// 从客户端接受的Socketd对象
		try {
			serversocket = new ServerSocket(5555);
			System.out.println("等待对话...");
			this.appendMessage("等待连接...");
			clientsocket = serversocket.accept();
			System.out.println("连接成功，对话开始！");
			this.appendMessage("链接成功，开始对话");
			System.out.println();
			// 打开输入输出流
			out = new PrintWriter(clientsocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
			String inputLine = null;

			while (true) {
				// if (inbye == false) {
				inputLine = in.readLine();// 获取客户端信息
				this.appendMessage("客户端:" + inputLine);
				if (inputLine.equals("再见"))
					break;
			}
			in.close();
			clientsocket.close();
			serversocket.close();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		ServerSocketDemo serverSocket = new ServerSocketDemo();
		serverSocket.Socket1();

	}

}
