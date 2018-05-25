import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

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

public class Layout extends JFrame{
	Container c;
    JSplitPane splitPane,splitPane2;
	public Layout() {
		setTitle("SimpleMindMap");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = (Container)getContentPane();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		c.add(splitPane);
		splitPane.setContinuousLayout(true); //�������� ���̾ƿ� ��� Ȱ��ȭ
        splitPane.setLeftComponent(new LeftPane()); //���� ������Ʈ ����
        splitPane2.setContinuousLayout(true);
        splitPane2.setLeftComponent(new CenterPane());
        splitPane2.setRightComponent(new RightPane());
        splitPane.setRightComponent(splitPane2);
		createMenu();
		createToolBar();
		setSize(1000,1000);
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
			toolBar.setFloatable(false );
			toolBar.add(new JButton("���� �����"));
			toolBar.add(new JButton("����"));
			toolBar.add(new JButton("����"));
			toolBar.add(new JButton("�ٸ� �̸����� ����"));
			toolBar.add(new JButton("�ݱ�"));
			toolBar.add(new JButton("����"));
			toolBar.add(new JButton("����"));
			
			c.add(toolBar,BorderLayout.NORTH);
		}
		class LeftPane extends JPanel{
			JTextArea textArea = new JTextArea();
			public LeftPane() {
				setLayout(new BorderLayout());
				add(new JScrollPane(textArea),BorderLayout.CENTER);
				JButton btn = new JButton("����");
				add(btn,BorderLayout.SOUTH);
			}
		}
		class CenterPane extends JPanel{
			JPanel jp = new JPanel();
			public CenterPane() {
				setLayout(new BorderLayout());
				add(new JScrollPane(jp),BorderLayout.CENTER);
			}
		}
		class RightPane extends JPanel{
			JPanel jp = new JPanel();
			public RightPane() {
				setLayout(new BorderLayout());
				add(new JScrollPane(jp),BorderLayout.CENTER);
				jp.setLayout(new GridLayout(6,2,20,20));
				jp.add(new JLabel("TEXT: ",SwingConstants.CENTER));
				JTextField immutable = new JTextField(10);
				immutable.setEditable(false);
				jp.add(immutable);
				jp.add(new JLabel("X: ",SwingConstants.CENTER));
				jp.add(new JTextField(10));
				jp.add(new JLabel("Y: ",SwingConstants.CENTER));
				jp.add(new JTextField(10));
				jp.add(new JLabel("W: ",SwingConstants.CENTER));
				jp.add(new JTextField(10));
				jp.add(new JLabel("H: ",SwingConstants.CENTER));
				jp.add(new JTextField(10));
				jp.add(new JLabel("Color: ",SwingConstants.CENTER));
				jp.add(new JTextField(10));
				JButton btn = new JButton("����");
				add(btn,BorderLayout.SOUTH);
			}
		}
		
		
}

