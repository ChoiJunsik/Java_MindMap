import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Layout extends JFrame{
	static JFrame c;
    JSplitPane splitPane,splitPane2;
    static CenterPane centerPane = new CenterPane();
    static LeftPane leftPane = new LeftPane();
	static RightPane rightPane = new RightPane();
	static JSONObject jsonObject;
	static Node root;
	static Boolean saveCnt = false;
	static String path=null;
	static int idx=0;
	public Layout() {
		setTitle("SimpleMindMap");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = this;
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(200);
		splitPane2.setDividerLocation(1200);
		c.add(splitPane);
		splitPane.setContinuousLayout(true); //연속적인 레이아웃 기능 활성화
		splitPane.setLeftComponent(leftPane); //좌측 컴포넌트 장착
        splitPane2.setContinuousLayout(true);
        splitPane2.setLeftComponent(new JScrollPane(centerPane));
        splitPane2.setRightComponent(rightPane);
        splitPane.setRightComponent(splitPane2);
		createMenu();
		createToolBar();
		setSize(1800,1000);
		setVisible(true);
	}
	
		void createMenu() {
			
			JMenuBar mb = new JMenuBar();
			JMenu save = new JMenu("저장");
			JMenu open = new JMenu("열기");
			JMenu restart = new JMenu("새로 만들기");
			JMenu exit = new JMenu("닫기");
			JMenu saveAs = new JMenu("다른 이름으로 저장");
			save.addMenuListener(new MyMenuListener());
			saveAs.addMenuListener(new MyMenuListener());			
			open.addMenuListener(new MyMenuListener());
			restart.addMenuListener(new MyMenuListener());
			exit.addMenuListener(new MyMenuListener());
			mb.setBackground(Color.GRAY);
			mb.add(restart);
			mb.add(open);
			mb.add(save);
			mb.add(saveAs);
			mb.add(exit);			
			setJMenuBar(mb);
		}
		
		static void getAttribute(Node node) {
			try {
				JSONArray list = new JSONArray();
				list.add(node.name);
				list.add(node.getX());
				list.add(node.getY());
				list.add(node.getWidth());
				list.add(node.getHeight());
				list.add(Integer.toHexString(node.color.getRGB()).substring(2));
				jsonObject.put(Integer.toString(idx),list);
				++idx;
				int i=0;
				while(true) {
					getAttribute(node.childs.get(i));
					++i;
				}
			}
			catch(IndexOutOfBoundsException e) {
				return;
			}
			catch(NullPointerException e) {
				JOptionPane.showMessageDialog(c, "(주의)적용을 시킨 후 저장하세요.");
				return;
			}
		}
		
		static int makeTreeForOpen(Node node,String []input,int i) {
			do {
			int levelF=0, levelA=0;
			try {
				while(node.name.charAt(levelF)=='\t') {
					++levelF;
				}
				while(input[i+1].charAt(levelA)=='\t') {
					++levelA;
				}
			}
			catch(ArrayIndexOutOfBoundsException e){
				return 0;
			}
			if((levelF == levelA-1)) {
					Node newNode = new Node(input[i+1]);
					newNode.setSize((int)(long) ((JSONArray) jsonObject.get(Integer.toString(idx))).get(3),(int)(long) ((JSONArray) jsonObject.get(Integer.toString(idx))).get(4));
					newNode.setLocation((int)(long) ((JSONArray) jsonObject.get(Integer.toString(idx))).get(1),(int)(long) ((JSONArray) jsonObject.get(Integer.toString(idx))).get(2));
					String color = (String)((JSONArray) jsonObject.get(Integer.toString(idx))).get(5);
					Color color2 = new Color(Integer.valueOf(color.substring(0,2),16),Integer.valueOf(color.substring(2,4),16),Integer.valueOf(color.substring(4),16)); 
					newNode.setBackground(color2);
					newNode.color = color2;
					++idx;
					newNode.setHorizontalAlignment(SwingConstants.CENTER);
					centerPane.add(newNode);
					node.childs.add(newNode);
					i = makeTreeForOpen(newNode,input,i+1);
					if(i!=0)
						continue;
					return 0;
			}
			else
				return i;
			
			}while(i!=0);
			return 0;

		}

		
		void createToolBar() {
			JToolBar toolBar = new JToolBar();
			toolBar.setFloatable(false);
			JButton save = new JButton("저장");
			JButton open = new JButton("열기");
			JButton restart = new JButton("새로 만들기");
			JButton exit = new JButton("닫기");
			JButton saveAs = new JButton("다른 이름으로 저장");
			
			save.addActionListener(new MyToolbarListener());				
			saveAs.addActionListener(new MyToolbarListener());
			open.addActionListener(new MyToolbarListener());
			restart.addActionListener(new MyToolbarListener());
			exit.addActionListener(new MyToolbarListener());
			toolBar.setBackground(Color.GRAY);
			toolBar.add(restart);
			toolBar.add(open);
			toolBar.add(save);
			toolBar.add(saveAs);
			toolBar.add(exit);			
					
			c.add(toolBar,BorderLayout.NORTH);
		}
		
}

