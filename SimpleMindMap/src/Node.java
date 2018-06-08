import java.awt.Color;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.EtchedBorder;

public class Node extends JLabel{
	JPopupMenu popup = new JPopupMenu();
	JMenuItem search = new JMenuItem("검색하기");
	MouseListener ml = new MouseListener();
	EtchedBorder eborder;
	int level;
	String name;
	ArrayList<Node> childs = new ArrayList<>();
	Color color;
    Node(String name){
	   	this.name = name;
	   	eborder=new EtchedBorder(EtchedBorder.RAISED);
	   	setBorder(eborder);
		setOpaque(true);
		setForeground(Color.WHITE);
		setText(name);
		addMouseListener(ml);
		addMouseMotionListener(ml);
		search.addActionListener(new searching());
		popup.add(search);
		add(popup);
	}

class MouseListener extends MouseAdapter{
	int x,y;
    void ShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                       e.getX()+10, e.getY()+10);
        }
    }
	public void mousePressed(MouseEvent e) {
		ShowPopup(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point endP = e.getPoint();
        JLabel la = (JLabel)e.getSource();
        Point p = la.getLocation();
        
        la.setLocation(p.x+endP.x - (int)la.getWidth()/2, p.y+ endP.y - (int)la.getHeight()/2);
        la.getParent().repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	  public void mouseClicked(MouseEvent e)
	  {
	  }

	@Override
	public void mouseReleased(MouseEvent e) {
        ShowPopup(e);

		Node node = (Node) e.getSource();
		//RightPane.getJTextField().setText(node.getText());
		if(name.charAt(0)=='\t')
			RightPane.immutable.setText(name.replace("\t", ""));
		else
			RightPane.immutable.setText(name);
			RightPane.x.setText(Double.toString(node.getX()));
			RightPane.y.setText(Double.toString(node.getY()));
			RightPane.w.setText(Double.toString(node.getWidth()));
			RightPane.h.setText(Double.toString(node.getHeight()));
			RightPane.c.setText(Integer.toHexString(node.color.getRGB()).substring(2));
			
			RightPane.node = node;
			
		//Layout.c.repaint();	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setBackground(Color.BLACK);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setBackground(color);
	}

	}
	class searching implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String searchingName;

			searchingName = name.replace("\t", "");
 			try { 
				Desktop.getDesktop().browse(new URI("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query="+searchingName)); 
				} 
			catch (IOException e) { 
				e.printStackTrace(); 
				} 
			catch (URISyntaxException e) { 
				e.printStackTrace(); }}
		
	}

}
