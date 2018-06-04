import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

public class Node extends JLabel{
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
	}

class MouseListener extends MouseAdapter{
	int x,y;

	public void mousePressed(MouseEvent e) {
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
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		Node node = (Node) e.getSource();
		//RightPane.getJTextField().setText(node.getText());
		if(name.charAt(0)=='\t')
			Layout.RightPane.immutable.setText(name.split("\t")[1]);
		else
			Layout.RightPane.immutable.setText(name);
		Layout.RightPane.x.setText(Double.toString(node.getX()));
		Layout.RightPane.y.setText(Double.toString(node.getY()));
		Layout.RightPane.w.setText(Double.toString(node.getWidth()));
		Layout.RightPane.h.setText(Double.toString(node.getHeight()));
		Layout.RightPane.node = node;
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

}
