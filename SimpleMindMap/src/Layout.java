import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    CenterPane centerPane = new CenterPane();
    LeftPane leftPane = new LeftPane();
	RightPane rightPane = new RightPane();
	public Layout() {
		setTitle("SimpleMindMap");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = (Container)getContentPane();
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(200);
		splitPane2.setDividerLocation(520);
		c.add(splitPane);
		splitPane.setContinuousLayout(true); //연속적인 레이아웃 기능 활성화
		splitPane.setLeftComponent(leftPane); //좌측 컴포넌트 장착
        splitPane2.setContinuousLayout(true);
        splitPane2.setLeftComponent(new JScrollPane(centerPane));
        splitPane2.setRightComponent(rightPane);
        splitPane.setRightComponent(splitPane2);
		createMenu();
		createToolBar();
		setSize(1200,800);
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
			String [] contents;
			public LeftPane() {	
				setLayout(new BorderLayout());
				textArea.setTabSize(2);
				add(new JScrollPane(textArea),BorderLayout.CENTER);
				JButton btn = new JButton("적용");
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						centerPane.removeAll();
						contents = textArea.getText().split("\n\t");
						Node node = new Node(contents[0]);
						node.setLocation(300,300);
						node.setSize(40,40);
						centerPane.add(node);
						centerPane.revalidate();
						centerPane.repaint();
					}
				});
				add(btn,BorderLayout.SOUTH);
			}
		}
		class CenterPane extends JPanel{
			public CenterPane() {
				setLayout(null);
			}
		}
		static class RightPane extends JPanel{
			JPanel jp = new JPanel();
			static JTextField immutable = new JTextField(10);
			static JTextField x = new JTextField(10);
			static JTextField y = new JTextField(10);
			static JTextField w = new JTextField(10);
			static JTextField h = new JTextField(10);
			static JTextField c = new JTextField(10);
			public RightPane() {
				setLayout(new BorderLayout());
				add(new JScrollPane(jp),BorderLayout.CENTER);
				jp.setLayout(new GridLayout(6,2,20,20));
				jp.add(new JLabel("TEXT: ",SwingConstants.CENTER));
				immutable.setEditable(false);
				jp.add(immutable);
				jp.add(new JLabel("X: ",SwingConstants.CENTER));
				jp.add(x);
				jp.add(new JLabel("Y: ",SwingConstants.CENTER));
				jp.add(y);
				jp.add(new JLabel("W: ",SwingConstants.CENTER));
				jp.add(w);
				jp.add(new JLabel("H: ",SwingConstants.CENTER));
				jp.add(h);
				jp.add(new JLabel("Color: ",SwingConstants.CENTER));
				jp.add(c);
				JButton btn = new JButton("변경");
				add(btn,BorderLayout.SOUTH);
			}
		}
		
		
	
		
		
}

