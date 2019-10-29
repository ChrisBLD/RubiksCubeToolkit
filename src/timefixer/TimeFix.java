package timefixer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JScrollBar;

public class TimeFix extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimeFix frame = new TimeFix();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TimeFix() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 457, 363);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setLineWrap(true);
		textArea_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		textArea_1.setEditable(false);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setColumns(1);
		textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String input = textArea.getText();
				textArea_1.setText(clean(input));
			}
		});
		
		contentPane.add(textArea);
		Component horizontalStrut = Box.createHorizontalStrut(10);
		contentPane.add(horizontalStrut);
		contentPane.add(textArea_1);
	}
	
	private String clean(String input) {
		String[] lines = input.split("\n");
		String out = "";
		
		for(int i = 0; i < lines.length; i++) {
			String l = lines[i];
			
			if(l != null && l != "") {
				int index = l.indexOf(" ");
				int index2 = l.indexOf(" ", index + 1);
				
				if(index > 0 && index2 > 0) {
					l = l.substring(index, index2);
					
					out += l + ",";
				}
			}
		}
		
		return out;
	}

}
