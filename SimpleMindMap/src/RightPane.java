import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class RightPane extends JPanel{
			JPanel jp = new JPanel();
			static JTextField immutable = new JTextField(10);
			static JTextField x = new JTextField(10);
			static JTextField y = new JTextField(10);
			static JTextField w = new JTextField(10);
			static JTextField h = new JTextField(10);
			static JTextField c = new JTextField(10);
			static Node node;
			public RightPane() {
				setLayout(new BorderLayout());
				add(new JScrollPane(jp),BorderLayout.CENTER);
				jp.setLayout(new GridLayout(6,2,20,20));
				jp.add(new JLabel("TEXT: ",SwingConstants.CENTER));
				immutable.setEditable(false);
				immutable.setHorizontalAlignment( JTextField.CENTER);
				jp.add(immutable);
				jp.add(new JLabel("X: ",SwingConstants.CENTER));
				x.setHorizontalAlignment( JTextField.CENTER);
				jp.add(x);
				jp.add(new JLabel("Y: ",SwingConstants.CENTER));
				y.setHorizontalAlignment( JTextField.CENTER);
				jp.add(y);
				jp.add(new JLabel("W: ",SwingConstants.CENTER));
				w.setHorizontalAlignment( JTextField.CENTER);
				jp.add(w);
				jp.add(new JLabel("H: ",SwingConstants.CENTER));
				h.setHorizontalAlignment( JTextField.CENTER);
				jp.add(h);
				jp.add(new JLabel("Color: ",SwingConstants.CENTER));
				c.setHorizontalAlignment( JTextField.CENTER);
				jp.add(c);				
				JButton btn = new JButton("변경");
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						node.setLocation((int)Double.parseDouble(x.getText()),(int)Double.parseDouble(y.getText()));
						node.setSize((int)Double.parseDouble(w.getText()), (int)Double.parseDouble(h.getText()));
						String color = (String)c.getText();
						node.setBackground(new Color(Integer.valueOf(color.substring(0,2),16),Integer.valueOf(color.substring(2,4),16),Integer.valueOf(color.substring(4),16)));
				        node.getParent().repaint();
					}
				});
				add(btn,BorderLayout.SOUTH);
			}
		}