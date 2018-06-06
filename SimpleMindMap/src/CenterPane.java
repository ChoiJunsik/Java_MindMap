import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

class CenterPane extends JPanel{
			public CenterPane() {
				setBackground(new Color(0xFF,0xE6,0xEB));
				setLayout(null);
				setPreferredSize(new Dimension(3000, 2000));
			}
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Graphics2D g2 = (Graphics2D) g.create();
		        
		        for (Relationship relationship : LeftPane.relationships) {

		            Point2D p1 = new Point2D.Double(relationship.getParent().getBounds().getCenterX(), relationship.getParent().getBounds().getCenterY());
		            Point2D p2 = new Point2D.Double(relationship.getChild().getBounds().getCenterX(), relationship.getChild().getBounds().getCenterY());

		            g2.draw(new Line2D.Double(p1, p2));

		        }

		        g2.dispose();

		    }
		}