import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

class LeftPane extends JPanel{
			JTextArea textArea = new JTextArea();
			String [] contents;Random rand = new Random();
			Color [] color = new Color[100];
			JButton btn;
		    static List<Relationship> relationships = new ArrayList<>();

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
						Layout.centerPane.removeAll();
						relationships = new ArrayList<>();
						contents = textArea.getText().split("\n");
						Layout.root = new Node(contents[0]);
						Layout.root.setSize(70,70);
						Layout.root.setLocation(1360,900);
						Layout.root.setBackground(new Color(0x3C,0xB4,0xFF));
						Layout.root.color = new Color(0x3C,0xB4,0xFF);
						Layout.root.setHorizontalAlignment(SwingConstants.CENTER);
						Layout.centerPane.add(Layout.root);
						makeTree(Layout.root,contents,0);
						makeTreeColor(Layout.root);
						makeTreeLocation(Layout.root,150,0);
						makeTreeLine(Layout.root,-1);
						Layout.centerPane.revalidate();
						Layout.centerPane.repaint();
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
						Layout.centerPane.add(newNode);
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
						throw new IndexOutOfBoundsException();
				}
				catch(IndexOutOfBoundsException e) {
					try {
						int i = 0;
						while(true) {
							makeTreeLocation(node.childs.get(i),x-20,i);
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
			
			static void makeTreeLine(Node node,int select) { // 노드이름 '\t'(level) 기준으로 색상, 위치 적용 후 노드이름에서 \t 제거
				try {
					switch(select) {
						case -1:
								relate(node,node.childs.get(0),"-10");
								relate(node,node.childs.get(1),"-11");
								relate(node,node.childs.get(2),"-12");
								relate(node,node.childs.get(3),"-13");
								break;
						case 0 :
								relate(node,node.childs.get(0),"00");
								relate(node,node.childs.get(1),"01");
								relate(node,node.childs.get(2),"02");
								relate(node,node.childs.get(3),"03");
								break;
						case 1 :
								relate(node,node.childs.get(0),"10");
								relate(node,node.childs.get(1),"11");
								relate(node,node.childs.get(2),"12");
								relate(node,node.childs.get(3),"13");
								break;
						case 2 :
								relate(node,node.childs.get(0),"20");
								relate(node,node.childs.get(1),"21");
								relate(node,node.childs.get(2),"22");
								relate(node,node.childs.get(3),"23");
								break;
						case 3 :
								relate(node,node.childs.get(0),"30");
								relate(node,node.childs.get(1),"31");
								relate(node,node.childs.get(2),"32");
								relate(node,node.childs.get(3),"33");
								break;
					}
					node.childs.get(4);
			}
			catch(IndexOutOfBoundsException e) {
				try {
					int i = 0;
					while(true) {
						makeTreeLine(node.childs.get(i),i);
						++i;
					}
				}
				catch(IndexOutOfBoundsException e2) {
					return;
				}
			}
			
		}
			

		    static void relate(Node parent, Node child,String caseIdx) {
		        relationships.add(new Relationship(parent, child,caseIdx));
		    }
	}

	class Relationship {
	
	    Node parent;
	    Node child;
	    String caseIdx;
	
	    public Relationship(Node parent, Node child,String caseIdx) {
	        this.parent = parent;
	        this.child = child;
	        this.caseIdx = caseIdx;
	    }
	
	    public Node getChild() {
	        return child;
	    }
	
	    public Node getParent() {
	        return parent;
	    }
	
	}