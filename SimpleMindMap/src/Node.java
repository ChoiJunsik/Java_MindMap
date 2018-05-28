import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class Node extends JLabel{
	MouseListener ml = new MouseListener();
	int level;
	String name;
    Node(String name,int level){
    	this.level = level;
    	this.name = name;
		setOpaque(true);
		setBackground(new Color(0x3C,0xB4,0xFF));
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
		Layout.RightPane.immutable.setText(node.getText());
		Layout.RightPane.x.setText(Double.toString(getLocation().getX()));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setBackground(Color.BLACK);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setBackground(new Color(0x3C,0xB4,0xFF));
	}

}

}
