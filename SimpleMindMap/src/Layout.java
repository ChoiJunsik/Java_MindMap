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
import java.util.Random;

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
		splitPane2.setDividerLocation(700);
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
			JSONObject jsonObject = new JSONObject();
			JMenuBar mb = new JMenuBar();
			JMenu save = new JMenu("저장");
			JMenu open = new JMenu("열기");
			save.addMenuListener(new MenuListener() {
				@Override
				public void menuCanceled(MenuEvent arg0) {}
				@Override
				public void menuDeselected(MenuEvent arg0) {}
				@Override
				public void menuSelected(MenuEvent arg0) {
					JOptionPane.showMessageDialog(null, "저장되었습니다.");
					String [] contents = leftPane.textArea.getText().split("\n");
					for(int i=0; i<contents.length; ++i)
						jsonObject.put(Integer.toString(i), contents[i]);
					try (FileWriter file = new FileWriter("C:\\Users\\Junsik Choi\\Documents\\Java_MindMap\\SimpleMindMap\\src\\test.json")) {
						file.write(jsonObject.toJSONString());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("파일저장 실패");
					}
				}
			});	
			open.addMenuListener(new MenuListener() {
				@Override
				public void menuCanceled(MenuEvent arg0) {}
				@Override
				public void menuDeselected(MenuEvent arg0) {}
				@Override
				public void menuSelected(MenuEvent arg0) {
					String toTextArea="";
				    JFileChooser fileChooser = new JFileChooser();
				    JSONObject jsonObject=null;
				    Object obj;
				    FileNameExtensionFilter filter =  new FileNameExtensionFilter("JSON","json");
				    fileChooser.setFileFilter(filter);
					int returnVal = fileChooser.showOpenDialog(getContentPane());
		            if( returnVal == JFileChooser.APPROVE_OPTION)
		            {
		                //열기 버튼을 누르면	
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
						for(int i=0;;++i) {
							if(jsonObject.get(Integer.toString(i))==null)
																break;
							toTextArea += jsonObject.get(Integer.toString(i)).toString()+"\n";
						}

						leftPane.textArea.setText(toTextArea);
		            }
				}
			});	
			mb.setBackground(Color.GRAY);
			mb.add(new JMenu("새로 만들기"));
			mb.add(open);
			mb.add(save);
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
			String [] contents;Random rand = new Random();
			Color [] color = new Color[100];
			
			public LeftPane() {	
				for(int i=0; i<color.length; ++i) {
					 int redValue = rand.nextInt(255);
					 int greenValue = rand.nextInt(255);
					 int blueValue = rand.nextInt(255);
					 
					 color[i] = new Color(redValue, greenValue, blueValue);
				 }
				setLayout(new BorderLayout());
				textArea.setTabSize(2);
				add(new JScrollPane(textArea),BorderLayout.CENTER);
				JButton btn = new JButton("적용");
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int i=0;
						centerPane.removeAll();
						contents = textArea.getText().split("\n");
						Node root = new Node(contents[0]);
						root.setSize(80,40);
						root.setLocation(300,260);
						root.setBackground(new Color(0x3C,0xB4,0xFF));
						root.color = new Color(0x3C,0xB4,0xFF);
						root.setHorizontalAlignment(SwingConstants.CENTER);
						centerPane.add(root);
						makeTree(root,contents,i);
						makeTreeAttribute(root);
						centerPane.revalidate();
						centerPane.repaint();
					}
				});
				add(btn,BorderLayout.SOUTH);
			}
			int makeTree(Node node,String []input,int i) {
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
						newNode.setSize(80,40);
						newNode.setHorizontalAlignment(SwingConstants.CENTER);
						centerPane.add(newNode);
						node.childs.add(newNode);
						i = makeTree(newNode,input,i+1);
						if(i!=0)
							continue;
						return 0;
				}
				else
					return i;
				
				}while(i!=0);
				return 0;

			}
			
			void makeTreeAttribute(Node node) { // 노드이름 '\t'(level) 기준으로 색상, 위치 적용 후 노드이름에서 \t 제거
				try {
					int i=0,level;
					while(true) {
						level=0;
						while(node.name.charAt(level)=='\t')
											++level;//
						node.childs.get(i).setBackground(color[level]);
						node.childs.get(i).color = color[level];
						++i;
					}
				}
				catch(IndexOutOfBoundsException e) {
					try {
						int i = 0;
						while(true) {
							makeTreeAttribute(node.childs.get(i));
							++i;
						}
					}
					catch(IndexOutOfBoundsException e2) {
						return;
					}
				}
				
			}
		}
		class CenterPane extends JPanel{
			public CenterPane() {
				setBackground(new Color(0xFF,0xE6,0xEB));
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
				jp.add(c);
				JButton btn = new JButton("변경");
				add(btn,BorderLayout.SOUTH);
			}
		}
		
		
	
		
		
}

