import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.management.ImmutableDescriptor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

//import Node.MouseListener;

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
		splitPane2.setDividerLocation(520);
		c.add(splitPane);
		splitPane.setContinuousLayout(true); // �������� ���̾ƿ� ��� Ȱ��ȭ
		splitPane.setLeftComponent(leftPane); // ���� ������Ʈ ����
		splitPane2.setContinuousLayout(true);
		splitPane2.setLeftComponent(new JScrollPane(centerPane));
		splitPane2.setRightComponent(rightPane);
		splitPane.setRightComponent(splitPane2);
		createMenu();
		createToolBar();
		setSize(1000, 900);
		setVisible(true);
	}

	void createMenu() {
		JMenuBar mb = new JMenuBar();
		mb.setBackground(Color.GRAY);
		mb.add(new JMenu("���� �����"));
		mb.add(new JMenu("����"));
		mb.add(new JMenu("����"));
		mb.add(new JMenu("�ٸ� �̸����� ����"));
		mb.add(new JMenu("�ݱ�"));
		mb.add(new JMenu("����"));
		mb.add(new JMenu("����"));

		setJMenuBar(mb);
	}

	void createToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(new JButton("���� �����"));
		toolBar.add(new JButton("����"));
		toolBar.add(new JButton("����"));
		toolBar.add(new JButton("�ٸ� �̸����� ����"));
		toolBar.add(new JButton("�ݱ�"));
		toolBar.add(new JButton("����"));
		toolBar.add(new JButton("����"));

		c.add(toolBar, BorderLayout.NORTH);
	}

	class LeftPane extends JPanel {
		JTextArea textArea = new JTextArea();
		String[] contents;

		public LeftPane() {
			setLayout(new BorderLayout());
			textArea.setTabSize(2);
			add(new JScrollPane(textArea), BorderLayout.CENTER);
			JButton btn = new JButton("����");
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					centerPane.removeAll();
					contents = textArea.getText().split("\n\t");
					Node node = new Node(contents[0]);
					node.setLocation(0, 300);
					node.setSize(40, 40);
					centerPane.add(node);
					centerPane.revalidate();
					centerPane.repaint();
				}
			});
			add(btn, BorderLayout.SOUTH);
		}
	}

	class CenterPane extends JPanel {
		public CenterPane() {
			setLayout(null);
		}
	}

	static class RightPane extends JPanel {
		JPanel jp = new JPanel();
		static JTextField immutable = new JTextField();
		static JTextField x = new JTextField(); //��ü ������ �����ڿ��� �ؾ��ϳ�?
		JTextField y = new JTextField();
		//JTextField w =
		static JButton btn ;
		public RightPane() {
			
			setLayout(new BorderLayout());
			add(new JScrollPane(jp), BorderLayout.CENTER);
			jp.setLayout(new GridLayout(6, 2, 20, 20));
			jp.add(new JLabel("TEXT: ", SwingConstants.CENTER));
			// JTextField immutable = new JTextField();
			immutable.setEditable(false); // �����ڵ�?
			// immutable.addMo
			jp.add(immutable );
			jp.add(new JLabel("XTLqkf: ", SwingConstants.CENTER));
			jp.add(x);
			jp.add(new JLabel("Y: ", SwingConstants.CENTER));
			jp.add(new JTextField(10));
			jp.add(new JLabel("W: ", SwingConstants.CENTER));
			jp.add(new JTextField(10));
			jp.add(new JLabel("H: ", SwingConstants.CENTER));
			jp.add(new JTextField(10));
			jp.add(new JLabel("Color: ", SwingConstants.CENTER));
			jp.add(new JTextField(10));
			 btn = new JButton("����");
			 btn.addMouseListener(new BtnMouseListener());
			add(btn, BorderLayout.SOUTH);
			
		}

		class BtnMouseListener extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				RightPane.btn = (JButton)e.getSource();
				Node.mouseClickedLocation.x = (int) Double.parseDouble(RightPane.x.getText());
				System.out.println("�����ư����!!");
			}
			
		}
		public static JTextField getJTextField() {
			return immutable;

		}
		public static JTextField getx() {
			return x;
		}

	}

	 static class Node extends JLabel {
		MouseListener ml = new MouseListener();
		static Point mouseClickedLocation = new Point(0, 0);

		Node(String name) {
			setOpaque(true);
			setBackground(Color.BLUE);
			setForeground(Color.WHITE);
			setText(name);
			addMouseListener(ml);
			addMouseMotionListener(ml);
		}
		
		/*static void setNodeLocation() {
			mouseClickedLocation = new Point()
		}*/

		class MouseListener extends MouseAdapter {
			int x, y;

			public void mousePressed(MouseEvent e) {
				Node node = (Node) e.getSource();
				node.setBackground(Color.BLACK);
				node.mouseClickedLocation.x = e.getX();
				node.mouseClickedLocation.y = e.getY();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				Node node = (Node) e.getSource();
				node.setLocation(e.getLocationOnScreen().x - node.mouseClickedLocation.x - 210,
						e.getLocationOnScreen().y - node.mouseClickedLocation.y - 100);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			
			
			} // ���ձ���

			@Override
			public void mouseReleased(MouseEvent e) {
				Node node = (Node) e.getSource();
				//RightPane.getJTextField().setText(node.getText());
				RightPane.immutable.setText(node.getText());
				RightPane.x.setText(Double.toString(getLocation().getX()));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		}

	}
}
