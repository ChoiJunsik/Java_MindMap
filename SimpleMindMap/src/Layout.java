import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Layout extends JFrame {
	Container c;
	JSplitPane splitPane, splitPane2;
	CenterPane centerPane = new CenterPane();
	LeftPane leftPane = new LeftPane();
	RightPane rightPane = new RightPane();

	public Layout() {
		setTitle("SimpleMindMap");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = (Container) getContentPane();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(200);
		splitPane2.setDividerLocation(700);
		c.add(splitPane);
		splitPane.setContinuousLayout(true); // �뿰�냽�쟻�씤 �젅�씠�븘�썐 湲곕뒫 �솢�꽦�솕
		splitPane.setLeftComponent(leftPane); // 醫뚯륫 而댄룷�꼳�듃 �옣李�
		splitPane2.setContinuousLayout(true);
		splitPane2.setLeftComponent(new JScrollPane(centerPane));
		splitPane2.setRightComponent(rightPane);
		splitPane.setRightComponent(splitPane2);
		createMenu();
		createToolBar();
		setSize(1200, 800);
		setVisible(true);
	}

	void createMenu() {
		JSONObject jsonObject = new JSONObject();
		JMenuBar mb = new JMenuBar();
		JMenu save = new JMenu("���옣");
		JMenu open = new JMenu("�뿴湲�");
		save.addMenuListener(new MenuListener() {
			@Override
			public void menuCanceled(MenuEvent arg0) {
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				JOptionPane.showMessageDialog(null, "���옣�릺�뿀�뒿�땲�떎.");
				String[] contents = leftPane.textArea.getText().split("\n");
				for (int i = 0; i < contents.length; ++i)
					jsonObject.put(Integer.toString(i), contents[i]);
				try (FileWriter file = new FileWriter(
						"C:\\Users\\Junsik Choi\\Documents\\Java_MindMap\\SimpleMindMap\\src\\test.json")) {
					file.write(jsonObject.toJSONString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("�뙆�씪���옣 �떎�뙣");
				}
			}
		});
		open.addMenuListener(new MenuListener() {
			@Override
			public void menuCanceled(MenuEvent arg0) {
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				String toTextArea = "";
				JFileChooser fileChooser = new JFileChooser();
				JSONObject jsonObject = null;
				Object obj;
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", "json");
				fileChooser.setFileFilter(filter);
				int returnVal = fileChooser.showOpenDialog(getContentPane());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// �뿴湲� 踰꾪듉�쓣 �늻瑜대㈃
					String path = fileChooser.getSelectedFile().getPath();
					JSONParser parser = new JSONParser();
					try {
						obj = parser.parse(new FileReader(path));
						jsonObject = (JSONObject) obj;
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (int i = 0;; ++i) {
						if (jsonObject.get(Integer.toString(i)) == null)
							break;
						toTextArea += jsonObject.get(Integer.toString(i)).toString() + "\n";
					}

					leftPane.textArea.setText(toTextArea);
				}
			}
		});
		mb.setBackground(Color.GRAY);
		mb.add(new JMenu("�깉濡� 留뚮뱾湲�"));
		mb.add(open);
		mb.add(save);
		mb.add(new JMenu("�떎瑜� �씠由꾩쑝濡� ���옣"));
		mb.add(new JMenu("�떕湲�"));
		mb.add(new JMenu("�쟻�슜"));
		mb.add(new JMenu("蹂�寃�"));

		setJMenuBar(mb);
	}

	void createToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(new JButton("�깉濡� 留뚮뱾湲�"));
		toolBar.add(new JButton("�뿴湲�"));
		toolBar.add(new JButton("���옣"));
		toolBar.add(new JButton("�떎瑜� �씠由꾩쑝濡� ���옣"));
		toolBar.add(new JButton("�떕湲�"));
		toolBar.add(new JButton("�쟻�슜"));
		toolBar.add(new JButton("蹂�寃�"));

		c.add(toolBar, BorderLayout.NORTH);
	}

	class LeftPane extends JPanel {
		JTextArea textArea = new JTextArea();
		String[] contents;

		public LeftPane() {
			setLayout(new BorderLayout());
			textArea.setTabSize(2);
			add(new JScrollPane(textArea), BorderLayout.CENTER);
			JButton btn = new JButton("�쟻�슜");
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int level = 0;
					centerPane.removeAll();
					contents = textArea.getText().split("\n");
					for (int i = 0; i < contents.length; ++i) {
						while (contents[i].charAt(level) == '\t') {
							++level;
						}
						Node node = new Node((contents[i]), level);

						level = 0;

						node.setSize(100, 100);
						centerPane.add(node);
						centerPane.revalidate();
						centerPane.repaint();
					}
				}
			});
			add(btn, BorderLayout.SOUTH);
		}
	}

	class CenterPane extends JPanel {
		public CenterPane() {
			setBackground(new Color(0xFF, 0xE6, 0xEB));
			setLayout(null);
		}
	}

	static class RightPane extends JPanel {
		JPanel jp = new JPanel();
		static JTextField immutable = new JTextField(10);
		static JTextField x = new JTextField(10);
		
		static JTextField y = new JTextField(10);
		static JTextField w = new JTextField(10);
		static JTextField h = new JTextField(10);
		static JTextField c = new JTextField(10);

		public RightPane() {
			setLayout(new BorderLayout());
			add(new JScrollPane(jp), BorderLayout.CENTER);
			jp.setLayout(new GridLayout(6, 2, 20, 20));
			jp.add(new JLabel("TEXT: ", SwingConstants.CENTER));
			immutable.setEditable(false);
			immutable.setHorizontalAlignment(JTextField.CENTER);
			jp.add(immutable);
			jp.add(new JLabel("X: ", SwingConstants.CENTER));
			x.setHorizontalAlignment( JTextField.CENTER);
			jp.add(x);
			jp.add(new JLabel("Y: ", SwingConstants.CENTER));
			jp.add(y);
			jp.add(new JLabel("W: ", SwingConstants.CENTER));
			jp.add(w);
			jp.add(new JLabel("H: ", SwingConstants.CENTER));
			jp.add(h);
			jp.add(new JLabel("Color: ", SwingConstants.CENTER));
			jp.add(c);
			JButton btn = new JButton("蹂�寃�");
			add(btn, BorderLayout.SOUTH);
		}
	}

}
