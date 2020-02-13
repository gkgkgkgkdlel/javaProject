package kr.koreait.networkTest4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MultiChrClient extends Frame implements ActionListener, Runnable {

	JLabel textArea;
	JPanel panel;
	JTextField textField;
	JButton button;
	
	Socket socket;
	PrintWriter printWriter;
	Scanner scanner;
	String message = "";
	
	public MultiChrClient() {
		setTitle("1:1 채팅 프로그램(클라이언트)");
		setBounds(800, 50, 500, 700);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(textArea, "채팅을 종료하겠습니까?", "채팅 종료", JOptionPane.YES_NO_OPTION);
				if(result == 0) {
					printWriter.write("저 나가요~~~~~ 바이바이~~~~~\n");
					printWriter.write("bye\n");
					printWriter.flush();
					if(socket != null) { try { socket.close(); } catch (IOException e1) { e1.printStackTrace(); } }
					if(printWriter != null) { try { printWriter.close(); } catch (Exception e1) { e1.printStackTrace(); } }
					if(scanner != null) { try { scanner.close(); } catch (Exception e1) { e1.printStackTrace(); } }
					System.exit(0);
				}
			}
		});
		
		textArea = new JLabel();
		textArea.setOpaque(true);
		textArea.setBackground(Color.ORANGE);
		textArea.setFont(new Font("D2Coding", Font.BOLD, 15));
		textArea.setVerticalAlignment(JLabel.BOTTOM);
		add(textArea);
		
		panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(500, 40));
		textField = new JTextField();
		panel.add(textField, BorderLayout.CENTER);
		button = new JButton("전송");
		panel.add(button, BorderLayout.EAST);
		add(panel, BorderLayout.SOUTH);
		
		textField.addActionListener(this);
		button.addActionListener(this);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		MultiChrClient client = new MultiChrClient();
		
		try {
			client.socket = new Socket("192.168.7.254", 10004);
			client.message = "192.168.7.254 서버의 10004번 포트로 접속 시도<br>";
			client.message = client.message + client.socket + "접속 성공!!!";
			client.textArea.setText("<html>" + client.message + "</html>");
			client.textField.requestFocus();
			client.printWriter = new PrintWriter(client.socket.getOutputStream());
			client.scanner = new Scanner(client.socket.getInputStream());
			Thread thread = new Thread(client);
			thread.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String str = textField.getText().trim();
		if(str.length() > 0) {
			message = message + "<br>client >> " + str;
			textArea.setText("<html>" + message + "</html>");
			if(printWriter != null) {
				printWriter.write(str + "\n");
				printWriter.flush();
			}
		}
		textField.setText("");
		textField.requestFocus();
		
	}

	@Override
	public void run() {
		
		while(socket != null) {
			String str = "";
			try {
				str = scanner.nextLine().trim();
			} catch(NoSuchElementException e) {
				break;
			}
			if(str.length() > 0) {
				message = message + "<br>server >> " + str;
				textArea.setText("<html>" + message + "</html>");
				if(str.toLowerCase().equals("bye")) {
					break;
				}
			}
			try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
		}
		textField.setEnabled(false);
		button.setEnabled(false);
		if(socket != null) { try { socket.close(); } catch (IOException e) { e.printStackTrace(); } }
		if(printWriter != null) { try { printWriter.close(); } catch (Exception e) { e.printStackTrace(); } }
		if(scanner != null) { try { scanner.close(); } catch (Exception e) { e.printStackTrace(); } }
	}

}

















