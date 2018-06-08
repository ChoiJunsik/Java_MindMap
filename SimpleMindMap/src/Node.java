import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class Node extends JLabel{
	JPopupMenu popup = new JPopupMenu();
	JMenuItem search = new JMenuItem("검색하기");
	MouseListener ml = new MouseListener();
	int level;
	String name;
	ArrayList<Node> childs = new ArrayList<>();
	Color color;
    Node(String name){
	   	this.name = name;
	   	setBorder(new ResizableBorder(8));
		setOpaque(true);
		setForeground(Color.WHITE);
		setText(name);
		addMouseListener(ml);
		addMouseMotionListener(ml);
		search.addActionListener(new searching());
		popup.add(search);
		add(popup);
	}

    private void resize() {
        if (getParent() != null) {
            ((JComponent) getParent()).revalidate();
        }
    }
class MouseListener extends MouseAdapter{
	int x,y;
    private int cursor;
    private Point startPos = null;

    void ShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                       e.getX()+10, e.getY()+10);
        }
    }
	public void mousePressed(MouseEvent e) {
		ShowPopup(e);
        ResizableBorder border = (ResizableBorder) getBorder();
        cursor = border.getCursor(e);
        startPos = e.getPoint();
        requestFocus();
        repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {

        if (startPos != null) {

            int x = getX();
            int y = getY();
            int w = getWidth();
            int h = getHeight();

            int dx = e.getX() - startPos.x;
            int dy = e.getY() - startPos.y;

            switch (cursor) {
                case Cursor.N_RESIZE_CURSOR:
                    if (!(h - dy < 50)) {
                        setBounds(x, y + dy, w, h - dy);
                        resize();
                    }
                    break;

                case Cursor.S_RESIZE_CURSOR:
                    if (!(h + dy < 50)) {
                        setBounds(x, y, w, h + dy);
                        startPos = e.getPoint();
                        resize();
                    }
                    break;

                case Cursor.W_RESIZE_CURSOR:
                    if (!(w - dx < 50)) {
                        setBounds(x + dx, y, w - dx, h);
                        resize();
                    }
                    break;

                case Cursor.E_RESIZE_CURSOR:
                    if (!(w + dx < 50)) {
                        setBounds(x, y, w + dx, h);
                        startPos = e.getPoint();
                        resize();
                    }
                    break;

                case Cursor.NW_RESIZE_CURSOR:
                    if (!(w - dx < 50) && !(h - dy < 50)) {
                        setBounds(x + dx, y + dy, w - dx, h - dy);
                        resize();
                    }
                    break;

                case Cursor.NE_RESIZE_CURSOR:
                    if (!(w + dx < 50) && !(h - dy < 50)) {
                        setBounds(x, y + dy, w + dx, h - dy);
                        startPos = new Point(e.getX(), startPos.y);
                        resize();
                    }
                    break;

                case Cursor.SW_RESIZE_CURSOR:
                    if (!(w - dx < 50) && !(h + dy < 50)) {
                        setBounds(x + dx, y, w - dx, h + dy);
                        startPos = new Point(startPos.x, e.getY());
                        resize();
                    }
                    break;

                case Cursor.SE_RESIZE_CURSOR:
                    if (!(w + dx < 50) && !(h + dy < 50)) {
                        setBounds(x, y, w + dx, h + dy);
                        startPos = e.getPoint();
                        resize();
                    }
                    break;

                case Cursor.MOVE_CURSOR:
                    Rectangle bounds = getBounds();
                    bounds.translate(dx, dy);
                    setBounds(bounds);
                    resize();
            }

        setCursor(Cursor.getPredefinedCursor(cursor));
        JLabel la = (JLabel)e.getSource();
        la.getParent().repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        if (hasFocus()) {
            ResizableBorder border = (ResizableBorder) getBorder();
            setCursor(Cursor.getPredefinedCursor(border.getCursor(me)));
        }
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

			startPos = null;

		//Layout.c.repaint();	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setBackground(Color.BLACK);
	}

	@Override
	public void mouseExited(MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
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
class ResizableBorder implements Border {

    private int dist = 8;

    int locations[] = {
        SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.WEST,
        SwingConstants.EAST, SwingConstants.NORTH_WEST,
        SwingConstants.NORTH_EAST, SwingConstants.SOUTH_WEST,
        SwingConstants.SOUTH_EAST
    };

    int cursors[] = {
        Cursor.N_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.W_RESIZE_CURSOR,
        Cursor.E_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
        Cursor.SW_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
    };

    public ResizableBorder(int dist) {
        this.dist = dist;
    }

    @Override
    public Insets getBorderInsets(Component component) {
        return new Insets(dist, dist, dist, dist);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component component, Graphics g, int x, int y,
            int w, int h) {
        
        g.setColor(Color.black);
        g.drawRect(x + (dist-7) / 2, y + (dist-7) / 2, w - (dist-7), h - (dist-7));

        if (component.hasFocus()) {

            for (int i = 0; i < locations.length; i++) {
                Rectangle rect = getRectangle(x, y, w, h, locations[i]);
                g.setColor(Color.WHITE);
                g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
                g.setColor(Color.BLACK);
                g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
            }
        }
    }

    private Rectangle getRectangle(int x, int y, int w, int h, int location) {

        switch (location) {
            case SwingConstants.NORTH:
                return new Rectangle(x + w / 2 - dist / 2, y, dist, dist);
            case SwingConstants.SOUTH:
                return new Rectangle(x + w / 2 - dist / 2, y + h - dist, dist,
                        dist);
            case SwingConstants.WEST:
                return new Rectangle(x, y + h / 2 - dist / 2, dist, dist);
            case SwingConstants.EAST:
                return new Rectangle(x + w - dist, y + h / 2 - dist / 2, dist,
                        dist);
            case SwingConstants.NORTH_WEST:
                return new Rectangle(x, y, dist, dist);
            case SwingConstants.NORTH_EAST:
                return new Rectangle(x + w - dist, y, dist, dist);
            case SwingConstants.SOUTH_WEST:
                return new Rectangle(x, y + h - dist, dist, dist);
            case SwingConstants.SOUTH_EAST:
                return new Rectangle(x + w - dist, y + h - dist, dist, dist);
        }
        return null;
    }

    public int getCursor(MouseEvent me) {

        Component c = me.getComponent();
        int w = c.getWidth();
        int h = c.getHeight();

        for (int i = 0; i < locations.length; i++) {
            Rectangle rect = getRectangle(0, 0, w, h, locations[i]);
            if (rect.contains(me.getPoint())) {
                return cursors[i];
            }
        }

        return Cursor.MOVE_CURSOR;
    }
}