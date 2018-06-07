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
		        Point2D p=null;
		        Point2D c=null;
		        for (Relationship relationship : LeftPane.relationships) {
		        	double centerXP = relationship.getParent().getBounds().getCenterX();
		        	double centerYP = relationship.getParent().getBounds().getCenterY();
		        	double centerXC = relationship.getChild().getBounds().getCenterX();
		        	double centerYC = relationship.getChild().getBounds().getCenterY();

		        	//위 y값 
		        	double point0P = centerYP -(centerYP-relationship.getParent().getBounds().getY());
		        	//왼쪽측면 x값
		        	double point1P = centerXP -(centerXP-relationship.getParent().getBounds().getX());
		        	//오른쪽 측면 x값
		        	double point2P = centerXP +(centerXP-relationship.getParent().getBounds().getX());
		        	//아래 y값
		        	double point3P = centerYP +(centerYP-relationship.getParent().getBounds().getY());
		        	
		        	//위 y값 
		        	double point0C = centerYC -(centerYC-relationship.getChild().getBounds().getY());
		        	//왼쪽측면 x값
		        	double point1C = centerXC -(centerXC-relationship.getChild().getBounds().getX());
		        	//오른쪽 측면 x값
		        	double point2C = centerXC +(centerXC-relationship.getChild().getBounds().getX());
		        	//아래 y값
		        	double point3C = centerYC +(centerYC-relationship.getChild().getBounds().getY());

		        	switch(relationship.caseIdx){
		        		case "-10":
		        			p = new Point2D.Double(centerXP, point0P);
		        			c = new Point2D.Double(point2C,centerYC);
	        				break;
		        		case "-11":
		        			p = new Point2D.Double(point2P, centerYP);
	        				c = new Point2D.Double(point1C,centerYC);
	        				break;
		        		case "-12":
		        			p = new Point2D.Double(point1P, centerYP);
		        			c = new Point2D.Double(centerXC,point0C);
	        				break;
		        		case "-13":
		        			p = new Point2D.Double(centerXP, point3P);
	        				c = new Point2D.Double(point1C,centerYC);
	        			break;
			        	case "01":
		        			p = new Point2D.Double(centerXP, point0P);
		        			c = new Point2D.Double(point2C,centerYC);
		        			break;
		        		case "00":
		        			p = new Point2D.Double(point1P, centerYP);
		        			c = new Point2D.Double(point2C,centerYC);
		        			break;
		        		case "02":
		        			p = new Point2D.Double(centerXP, point3P);
		        			c = new Point2D.Double(point2C,centerYC);
		        			break;
		        		case "11":
		        			p = new Point2D.Double(point2P, centerYP);
		        			c = new Point2D.Double(point1C,centerYC);
		        			break;
		        		case "10":
		        			p = new Point2D.Double(centerXP, point0P);
		        			c = new Point2D.Double(point1C,centerYC);
		        			break;
		        		case "12":
		        			p = new Point2D.Double(centerXP, point3P);
		        			c = new Point2D.Double(point1C,centerYC);
		        			break;
		        		case "20":
		        			p = new Point2D.Double(point1P, centerYP);
		        			c = new Point2D.Double(centerXC,point0C);
		        			break;
		        		case "21":
		        			p = new Point2D.Double(centerXP, point3P);
		        			c = new Point2D.Double(centerXC,point0C);
		        			break;
		        		case "22":
		        			p = new Point2D.Double(point2P, centerYP);
		        			c = new Point2D.Double(centerXC,point0C);
		        			break;
		        		case "32":
		        			p = new Point2D.Double(centerXP, point3P);
		        			c = new Point2D.Double(point1C,centerYC);
		        			break;
		        		case "31":
		        			p = new Point2D.Double(point2P, centerYP);
		        			c = new Point2D.Double(point1C,centerYC);
		        			break;
		        		case "30":
		        			p = new Point2D.Double(centerXP, point0P);
		        			c = new Point2D.Double(point1C,centerYC);
		        			break;
		        		
		        	}

		            g2.draw(new Line2D.Double(p, c));
		        }

		        g2.dispose();

		    }
		}