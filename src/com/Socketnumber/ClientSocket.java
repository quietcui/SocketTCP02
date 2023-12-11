package com.Socketnumber;

import java.io.*;

import java.awt.event.ActionEvent;
import java.net.Socket;

public class ClientSocket extends ServerSocketDemo {
	private PrintWriter out1;
	private BufferedReader in1;

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = inputField.getText().trim(); // 获取输入消息框的内容，并去除首尾空格
		if (!message.isEmpty()) { // 如果消息不为空
			out1.println(message); // 向服务器发送消息
			out1.flush();
			inputField.setText(""); // 清空输入消息框的内容
			appendMessage("客户端：" + message);
		}
	}

	public void Client() {
		init();
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", 5555);
			this.appendMessage("连接成功，对话开始");
			// 打开输入流输出流
			out1 = new PrintWriter(socket.getOutputStream(), true);
			in1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String fromserver, fromuser;
			while (true) {

				System.out.println("服务器:");

				fromserver = in1.readLine();
				System.out.println(fromserver);
				this.appendMessage("服务端:" + fromserver);
				if (fromserver.equals("再见"))
					break;
			}
			in1.close();
			socket.close();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		ClientSocket client = new ClientSocket();
		client.Client();
	}
}
