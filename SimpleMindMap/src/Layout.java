import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Layout extends JFrame{
	Container c;
	JFrame layout = this;
    JSplitPane splitPane,splitPane2;
    CenterPane centerPane = new CenterPane();
    LeftPane leftPane = new LeftPane();
	RightPane rightPane = new RightPane();
	JSONObject jsonObject;
	Node root;
	Boolean saveCnt = false;
	String path=null;
	int idx=0;
	public Layout() {
		setTitle("SimpleMindMap");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = (Container)getContentPane();
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
			
			save.addMenuListener(new MenuListener() {
				@Override
				public void menuCanceled(MenuEvent arg0) {}
				@Override
				public void menuDeselected(MenuEvent arg0) {}
				@Override
				public void menuSelected(MenuEvent arg0) {
					
					JFileChooser fileChooser = new JFileChooser();
				    Object obj;
				    FileNameExtensionFilter filter =  new FileNameExtensionFilter("json","json");
				    idx = 0;
				    fileChooser.setFileFilter(filter);
				    if(saveCnt == false) {
				    	int returnVal = fileChooser.showSaveDialog(getContentPane());
				    	saveCnt = true;
			            if( returnVal == JFileChooser.APPROVE_OPTION)
			            {
			                //열기 버튼을 누르면	
			                path = fileChooser.getSelectedFile().toString() + "." + fileChooser.getFileFilter().getDescription();
			                String [] contents = leftPane.textArea.getText().split("\n");
			                jsonObject = new JSONObject();
							getAttribute(root); // 배열 인덱스 0: 이름 , 1: x, 2: y, 3: w, 4: h
							try (FileWriter file = new FileWriter(path)) {
								file.write(jsonObject.toJSONString());
								JOptionPane.showMessageDialog(null, "저장되었습니다.");
								layout.setTitle(path);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								System.out.println("파일저장 실패");
							}
			            }
			            else
			            	return;
				    }
				    else {
				    	String [] contents = leftPane.textArea.getText().split("\n");
		                jsonObject = new JSONObject();
						getAttribute(root); // 배열 인덱스 0: 이름 , 1: x, 2: y, 3: w, 4: h
						try (FileWriter file = new FileWriter(path)) {
							file.write(jsonObject.toJSONString());
							JOptionPane.showMessageDialog(null, "저장되었습니다.");
							layout.setTitle(path);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							System.out.println("파일저장 실패");
						}
				    }
					
				}
			});
			saveAs.addMenuListener(new MenuListener() {
				@Override
				public void menuCanceled(MenuEvent arg0) {}
				@Override
				public void menuDeselected(MenuEvent arg0) {}
				@Override
				public void menuSelected(MenuEvent arg0) {
					JFileChooser fileChooser = new JFileChooser();
				    Object obj;
				    FileNameExtensionFilter filter =  new FileNameExtensionFilter("json","json");
				    idx = 0;
				    fileChooser.setFileFilter(filter);
					int returnVal = fileChooser.showSaveDialog(getContentPane());
		            if( returnVal == JFileChooser.APPROVE_OPTION)
		            {
		                //열기 버튼을 누르면	
		                path = fileChooser.getSelectedFile().toString() + "." + fileChooser.getFileFilter().getDescription();
		                String [] contents = leftPane.textArea.getText().split("\n");
		                jsonObject = new JSONObject();
						getAttribute(root); // 배열 인덱스 0: 이름 , 1: x, 2: y, 3: w, 4: h
						try (FileWriter file = new FileWriter(path)) {
							file.write(jsonObject.toJSONString());
							JOptionPane.showMessageDialog(null, "저장되었습니다.");
							layout.setTitle(path);
							saveCnt=true;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							System.out.println("파일저장 실패");
						}
		            }
		            else
		            	return;
					
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
				    Object obj;
				    FileNameExtensionFilter filter =  new FileNameExtensionFilter("JSON","json");
				    fileChooser.setFileFilter(filter);
					int returnVal = fileChooser.showOpenDialog(getContentPane());
		            if( returnVal == JFileChooser.APPROVE_OPTION)
		            {
		                //열기 버튼을 누르면	
		            	path = fileChooser.getSelectedFile().getPath();
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
							toTextArea += ((JSONArray) jsonObject.get(Integer.toString(i))).get(0)+"\n";
						}
						leftPane.textArea.setText(toTextArea);
						int i=0;
						centerPane.removeAll();
						leftPane.contents = leftPane.textArea.getText().split("\n");
						root = new Node(leftPane.contents[0]);
						root.setSize(80,40);
						root.setLocation((int)(long) ((JSONArray) jsonObject.get(Integer.toString(i))).get(1),(int)(long) ((JSONArray) jsonObject.get(Integer.toString(i))).get(2));
						root.setBackground(new Color(0x3C,0xB4,0xFF));
						root.color = new Color(0x3C,0xB4,0xFF);
						root.setHorizontalAlignment(SwingConstants.CENTER);
						centerPane.add(root);
						idx = 1;
						makeTreeForOpen(root,leftPane.contents,i);
						centerPane.revalidate();
						centerPane.repaint();
						layout.setTitle(path);
						saveCnt = true;
		            }
				}
			});
			restart.addMenuListener(new MenuListener() {
				public void menuCanceled(MenuEvent arg0) {}
				public void menuDeselected(MenuEvent arg0) {}
				public void menuSelected(MenuEvent arg0) {
					leftPane.textArea.setText("");
					centerPane.removeAll();
					rightPane.immutable.setText("");
					rightPane.x.setText("");
					rightPane.y.setText("");
					rightPane.w.setText("");
					rightPane.h.setText("");
					rightPane.c.setText("");
					centerPane.revalidate();
					centerPane.repaint();					
				}});
			exit.addMenuListener(new MenuListener() {
				public void menuCanceled(MenuEvent arg0) {}
				public void menuDeselected(MenuEvent arg0) {}
				public void menuSelected(MenuEvent arg0) {
					System.exit(0);					
				}});
			mb.setBackground(Color.GRAY);
			mb.add(restart);
			mb.add(open);
			mb.add(save);
			mb.add(saveAs);
			mb.add(exit);			
			setJMenuBar(mb);
		}
		
		void getAttribute(Node node) {
			try {
				JSONArray list = new JSONArray();
				list.add(node.name);
				list.add(node.getX());
				list.add(node.getY());
				list.add(node.getWidth());
				list.add(node.getHeight());
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
				JOptionPane.showMessageDialog(getContentPane(), "(주의)적용을 시킨 후 저장하세요.");
				return;
			}
		}
		
		int makeTreeForOpen(Node node,String []input,int i) {
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
					newNode.setBackground(Color.blue);
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
			
			save.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					JFileChooser fileChooser = new JFileChooser();
				    Object obj;
				    FileNameExtensionFilter filter =  new FileNameExtensionFilter("json","json");
				    idx = 0;
				    fileChooser.setFileFilter(filter);
				    if(saveCnt == false) {
				    	int returnVal = fileChooser.showSaveDialog(getContentPane());
				    	saveCnt = true;
			            if( returnVal == JFileChooser.APPROVE_OPTION)
			            {
			                //열기 버튼을 누르면	
			                path = fileChooser.getSelectedFile().toString() + "." + fileChooser.getFileFilter().getDescription();
			                String [] contents = leftPane.textArea.getText().split("\n");
			                jsonObject = new JSONObject();
							getAttribute(root); // 배열 인덱스 0: 이름 , 1: x, 2: y, 3: w, 4: h
							try (FileWriter file = new FileWriter(path)) {
								file.write(jsonObject.toJSONString());
								JOptionPane.showMessageDialog(null, "저장되었습니다.");
								layout.setTitle(path);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								System.out.println("파일저장 실패");
							}
			            }
			            else
			            	return;
				    }
				    else {
				    	String [] contents = leftPane.textArea.getText().split("\n");
		                jsonObject = new JSONObject();
						getAttribute(root); // 배열 인덱스 0: 이름 , 1: x, 2: y, 3: w, 4: h
						try (FileWriter file = new FileWriter(path)) {
							file.write(jsonObject.toJSONString());
							JOptionPane.showMessageDialog(null, "저장되었습니다.");
							layout.setTitle(path);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							System.out.println("파일저장 실패");
						}
				    }
					
				}
			});
			saveAs.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					JFileChooser fileChooser = new JFileChooser();
				    Object obj;
				    FileNameExtensionFilter filter =  new FileNameExtensionFilter("json","json");
				    idx = 0;
				    fileChooser.setFileFilter(filter);
					int returnVal = fileChooser.showSaveDialog(getContentPane());
		            if( returnVal == JFileChooser.APPROVE_OPTION)
		            {
		                //열기 버튼을 누르면	
		                path = fileChooser.getSelectedFile().toString() + "." + fileChooser.getFileFilter().getDescription();
		                String [] contents = leftPane.textArea.getText().split("\n");
		                jsonObject = new JSONObject();
						getAttribute(root); // 배열 인덱스 0: 이름 , 1: x, 2: y, 3: w, 4: h
						try (FileWriter file = new FileWriter(path)) {
							file.write(jsonObject.toJSONString());
							JOptionPane.showMessageDialog(null, "저장되었습니다.");
							layout.setTitle(path);
							saveCnt=true;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							System.out.println("파일저장 실패");
						}
		            }
		            else
		            	return;
					
				}
			});
			
			open.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent l){
					
					String toTextArea="";
				    JFileChooser fileChooser = new JFileChooser();
				    Object obj;
				    FileNameExtensionFilter filter =  new FileNameExtensionFilter("JSON","json");
				    fileChooser.setFileFilter(filter);
					int returnVal = fileChooser.showOpenDialog(getContentPane());
		            if( returnVal == JFileChooser.APPROVE_OPTION)
		            {
		                //열기 버튼을 누르면	
		            	path = fileChooser.getSelectedFile().getPath();
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
							toTextArea += ((JSONArray) jsonObject.get(Integer.toString(i))).get(0)+"\n";
						}
						leftPane.textArea.setText(toTextArea);
						int i=0;
						centerPane.removeAll();
						leftPane.contents = leftPane.textArea.getText().split("\n");
						root = new Node(leftPane.contents[0]);
						root.setSize(80,40);
						root.setLocation((int)(long) ((JSONArray) jsonObject.get(Integer.toString(i))).get(1),(int)(long) ((JSONArray) jsonObject.get(Integer.toString(i))).get(2));
						root.setBackground(new Color(0x3C,0xB4,0xFF));
						root.color = new Color(0x3C,0xB4,0xFF);
						root.setHorizontalAlignment(SwingConstants.CENTER);
						centerPane.add(root);
						idx = 1;
						makeTreeForOpen(root,leftPane.contents,i);
						centerPane.revalidate();
						centerPane.repaint();
						layout.setTitle(path);
						saveCnt = true;
		            }
				}
			});
			restart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					leftPane.textArea.setText("");
					rightPane.immutable.setText("");
					rightPane.x.setText("");
					rightPane.y.setText("");
					rightPane.w.setText("");
					rightPane.h.setText("");
					rightPane.c.setText("");
					centerPane.removeAll();
					centerPane.revalidate();
					centerPane.repaint();					
				}});
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					System.exit(0);					
				}});
			toolBar.setBackground(Color.GRAY);
			toolBar.add(restart);
			toolBar.add(open);
			toolBar.add(save);
			toolBar.add(saveAs);
			toolBar.add(exit);			
		
			
			
			
			c.add(toolBar,BorderLayout.NORTH);
		}
		class LeftPane extends JPanel{
			JTextArea textArea = new JTextArea();
			String [] contents;Random rand = new Random();
			Color [] color = new Color[100];
			JButton btn;
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
				btn = new JButton("적용");
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int i=0;
						centerPane.removeAll();
						contents = textArea.getText().split("\n");
						root = new Node(contents[0]);
						root.setSize(80,40);
						root.setLocation(560,380);
						root.setBackground(new Color(0x3C,0xB4,0xFF));
						root.color = new Color(0x3C,0xB4,0xFF);
						root.setHorizontalAlignment(SwingConstants.CENTER);
						centerPane.add(root);
						makeTree(root,contents,i);
						makeTreeColor(root);
						makeTreeLocation(root,120,0);
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
						newNode.setSize(60,30);
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
			
			void makeTreeLocation(Node node,int x,int select) { // 노드이름 '\t'(level) 기준으로 색상, 위치 적용 후 노드이름에서 \t 제거
				try {
						switch(select) {
							case 0 :
									node.childs.get(0).setLocation(node.getX()-x,node.getY()-x);
									node.childs.get(1).setLocation(node.getX()+x,node.getY()-x);
									node.childs.get(2).setLocation(node.getX()-x,node.getY()+x);
									node.childs.get(3).setLocation(node.getX()+x,node.getY()+x);
									break;
							case 1 :
								node.childs.get(0).setLocation(node.getX()-x,node.getY()-x);
								node.childs.get(1).setLocation(node.getX()+x,node.getY()-x);
								node.childs.get(2).setLocation(node.getX()+x,node.getY()+x);
								node.childs.get(3).setLocation(node.getX()-x,node.getY()+x);
								break;
							case 2 :
								node.childs.get(0).setLocation(node.getX()-x,node.getY()-x);
								node.childs.get(1).setLocation(node.getX()-x,node.getY()+x);
								node.childs.get(2).setLocation(node.getX()+x,node.getY()+x);
								node.childs.get(3).setLocation(node.getX()+x,node.getY()-x);
								break;
							case 3 :
								node.childs.get(0).setLocation(node.getX()+x,node.getY()-x);
								node.childs.get(1).setLocation(node.getX()+x,node.getY()+x);
								node.childs.get(2).setLocation(node.getX()-x,node.getY()+x);
								node.childs.get(3).setLocation(node.getX()-x,node.getY()-x);
								break;
						}
						node.childs.get(4);
				}
				catch(IndexOutOfBoundsException e) {
					try {
						int i = 0;
						while(true) {
							makeTreeLocation(node.childs.get(i),x-25,i);
							++i;
						}
					}
					catch(IndexOutOfBoundsException e2) {
						return;
					}
				}
				
			} 
			
			void makeTreeColor(Node node) { // 노드이름 '\t'(level) 기준으로 색상, 위치 적용 후 노드이름에서 \t 제거
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
							makeTreeColor(node.childs.get(i));
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
				setPreferredSize(new Dimension(3000, 2000));
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
				jp.add(c);
				JButton btn = new JButton("변경");
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						node.setLocation((int)Double.parseDouble(x.getText()),(int)Double.parseDouble(y.getText()));
						node.setSize((int)Double.parseDouble(w.getText()), (int)Double.parseDouble(h.getText()));						
					}
				});
				add(btn,BorderLayout.SOUTH);
			}
		}
		
		
	
		
		
}

