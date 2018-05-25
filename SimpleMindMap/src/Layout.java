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
		splitPane.setContinuousLayout(true); //연속적인 레이아웃 기능 활성화
        splitPane.setLeftComponent(new LeftPane()); //좌측 컴포넌트 장착
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
			mb.add(new JMenu("새로 만들기"));
			mb.add(new JMenu("열기"));
			mb.add(new JMenu("저장"));
			mb.add(new JMenu("다른 이름으로 저장"));
			mb.add(new JMenu("닫기"));
			mb.add(new JMenu("적용"));
			mb.add(new JMenu("변경"));
			
			setJMenuBar(mb);
		}
		void createToolBar() {
			JToolBar toolBar = new JToolBar();
			toolBar.setFloatable(false );
			toolBar.add(new JButton("새로 만들기"));
			toolBar.add(new JButton("열기"));
			toolBar.add(new JButton("저장"));
			toolBar.add(new JButton("다른 이름으로 저장"));
			toolBar.add(new JButton("닫기"));
			toolBar.add(new JButton("적용"));
			toolBar.add(new JButton("변경"));
			
			c.add(toolBar,BorderLayout.NORTH);
		}
		class LeftPane extends JPanel{
			JTextArea textArea = new JTextArea();
			public LeftPane() {
				setLayout(new BorderLayout());
				add(new JScrollPane(textArea),BorderLayout.CENTER);
				JButton btn = new JButton("적용");
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
				JButton btn = new JButton("변경");
				add(btn,BorderLayout.SOUTH);
			}
		}
		
		
}

