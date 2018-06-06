import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MyToolbarListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton select = (JButton)e.getSource();
		switch(select.getText()) {
			case "저장":
				forSave();
				break;
			case "열기":
				forOpen();
				break;
			case "다른 이름으로 저장":
				forSaveAs();
				break;
			case "새로 만들기":
				forRestart();
				break;
			case "닫기":
				forExit();
				break;
		}
	}
		void forSave() {
			JFileChooser fileChooser = new JFileChooser();
		    Object obj;
		    FileNameExtensionFilter filter =  new FileNameExtensionFilter("json","json");
		    fileChooser.setFileFilter(filter);
		    if(Layout.saveCnt == false) {
		    	int returnVal = fileChooser.showSaveDialog(Layout.c);
		    	Layout.saveCnt = true;
		        if( returnVal == JFileChooser.APPROVE_OPTION)
		        {
		            //열기 버튼을 누르면	
		        	Layout.path = fileChooser.getSelectedFile().toString() + "." + fileChooser.getFileFilter().getDescription();
		            String [] contents = Layout.leftPane.textArea.getText().split("\n");
		            Layout.jsonObject = new JSONObject();
					Layout.getAttribute(Layout.root); // 배열 인덱스 0: 이름 , 1: x, 2: y, 3: w, 4: h
					try (FileWriter file = new FileWriter(Layout.path)) {
						file.write(Layout.jsonObject.toJSONString());
						JOptionPane.showMessageDialog(null, "저장되었습니다.");
						Layout.c.setTitle(Layout.path);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("파일저장 실패");
					}
		        }
		        else
		        	return;
		    }
		    else {
		    	String [] contents = Layout.leftPane.textArea.getText().split("\n");
		    	Layout.jsonObject = new JSONObject();
		    	Layout.getAttribute(Layout.root); // 배열 인덱스 0: 이름 , 1: x, 2: y, 3: w, 4: h
				try (FileWriter file = new FileWriter(Layout.path)) {
					file.write(Layout.jsonObject.toJSONString());
					JOptionPane.showMessageDialog(null, "저장되었습니다.");
					Layout.c.setTitle(Layout.path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("파일저장 실패");
				}
		    }
			
		}
		
		void forSaveAs() {
			JFileChooser fileChooser = new JFileChooser();
		    Object obj;
		    FileNameExtensionFilter filter =  new FileNameExtensionFilter("json","json");
		    Layout.idx = 0;
		    fileChooser.setFileFilter(filter);
			int returnVal = fileChooser.showSaveDialog(Layout.c);
	        if( returnVal == JFileChooser.APPROVE_OPTION)
	        {
	            //열기 버튼을 누르면	
	        	Layout.path = fileChooser.getSelectedFile().toString() + "." + fileChooser.getFileFilter().getDescription();
	            String [] contents = Layout.leftPane.textArea.getText().split("\n");
	            Layout.jsonObject = new JSONObject();
	            Layout.getAttribute(Layout.root); // 배열 인덱스 0: 이름 , 1: x, 2: y, 3: w, 4: h
				try (FileWriter file = new FileWriter(Layout.path)) {
					file.write(Layout.jsonObject.toJSONString());
					JOptionPane.showMessageDialog(null, "저장되었습니다.");
					Layout.c.setTitle(Layout.path);
					Layout.saveCnt=true;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("파일저장 실패");
				}
	        }
	        else
	        	return;
		}
		
		void forOpen() {
			String toTextArea="";
		    JFileChooser fileChooser = new JFileChooser();
		    Object obj;
		    FileNameExtensionFilter filter =  new FileNameExtensionFilter("JSON","json");
		    fileChooser.setFileFilter(filter);
			int returnVal = fileChooser.showOpenDialog(Layout.c);
	        if( returnVal == JFileChooser.APPROVE_OPTION)
	        {
	            //열기 버튼을 누르면	
	        	Layout.path = fileChooser.getSelectedFile().getPath();
	            JSONParser parser = new JSONParser();
				try {
					obj = parser.parse(new FileReader(Layout.path));
					Layout.jsonObject = (JSONObject) obj;
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
					if(Layout.jsonObject.get(Integer.toString(i))==null)
														break;
					toTextArea += ((JSONArray) Layout.jsonObject.get(Integer.toString(i))).get(0)+"\n";
				}
				Layout.leftPane.textArea.setText(toTextArea);
				int i=0;
				Layout.centerPane.removeAll();
				Layout.leftPane.contents = Layout.leftPane.textArea.getText().split("\n");
				Layout.root = new Node(Layout.leftPane.contents[0]);
				Layout.root.setSize(80,40);
				Layout.root.setLocation((int)(long) ((JSONArray) Layout.jsonObject.get(Integer.toString(i))).get(1),(int)(long) ((JSONArray) Layout.jsonObject.get(Integer.toString(i))).get(2));
				Layout.root.setBackground(new Color(0x3C,0xB4,0xFF));
				Layout.root.color = new Color(0x3C,0xB4,0xFF);
				Layout.root.setHorizontalAlignment(SwingConstants.CENTER);
				Layout.centerPane.add(Layout.root);
				Layout.idx = 1;
				Layout.makeTreeForOpen(Layout.root,Layout.leftPane.contents,i);
				Layout.centerPane.revalidate();
				Layout.centerPane.repaint();
				Layout.c.setTitle(Layout.path);
				Layout.saveCnt = true;
	        }
		}
		void forRestart() {
			LeftPane.relationships = new ArrayList<>(2000);
			Layout.leftPane.textArea.setText("");
			Layout.centerPane.removeAll();
			Layout.rightPane.immutable.setText("");
			Layout.rightPane.x.setText("");
			Layout.rightPane.y.setText("");
			Layout.rightPane.w.setText("");
			Layout.rightPane.h.setText("");
			Layout.rightPane.c.setText("");
			Layout.centerPane.revalidate();
			Layout.centerPane.repaint();
		}
		void forExit() {
			System.exit(0);					
		}

}
