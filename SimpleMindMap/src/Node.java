import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class Node extends JLabel{
	MouseListener ml = new MouseListener();
    Node(String name){
		setOpaque(true);
		setBackground(Color.BLUE);
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
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		setBackground(Color.BLACK);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setBackground(Color.BLUE);
	}

}

}
