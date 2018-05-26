import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class Node extends JLabel{
	MouseListener ml = new MouseListener();
    Point mouseClickedLocation = new Point(0, 0);
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
		Node node = (Node)e.getSource();
		node.setBackground(Color.BLACK);
		node.mouseClickedLocation.x = e.getX();
        node.mouseClickedLocation.y = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Node node = (Node)e.getSource();
		node.setLocation(e.getLocationOnScreen().x - node.mouseClickedLocation.x -210,
	                e.getLocationOnScreen().y - node.mouseClickedLocation.y -100);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}

}
