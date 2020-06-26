import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.*;

public class JFontChooser {

	JFontChooser()
	{	
	}
	
	static Font selectedFont;
	
	static String fontName;
	static int fontStyle;
	static int fontSize;
	static Font returnFont;
	
	public static Font showDialog(JFrame parent, Font initialFont)
	{
		JDialog fontDialog = new JDialog(parent, "Font", true);
		fontDialog.setLayout(new GridLayout(2,1));
		fontDialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		fontDialog.setLocationRelativeTo(null);
		fontDialog.setSize(480, 460);
		fontDialog.setResizable(false);
		
		if(initialFont == null)
			initialFont = new Font("Courier New", Font.PLAIN, 11);
			
		selectedFont = initialFont;
		returnFont = initialFont;
		
		fontName = selectedFont.getFontName();
		fontStyle = selectedFont.getStyle();
		fontSize = selectedFont.getSize();
		
		
		String previewText = new String("The quick brown fox jumps over the lazy dog 0123456789\n");
		
		JTextField preview = new JTextField(previewText);
		preview.setFont(selectedFont);
		
		JPanel fontConfigPanel = new JPanel();
		fontConfigPanel.setLayout(new BoxLayout(fontConfigPanel, BoxLayout.X_AXIS));
		
		//left side of the panel
			JPanel leftSideConfigPanel = new JPanel();
			leftSideConfigPanel.setLayout(new BoxLayout(leftSideConfigPanel, BoxLayout.Y_AXIS));
			leftSideConfigPanel.setMaximumSize(new Dimension(400,200));
			
			
			JLabel fontLabel = new JLabel("Font: ");
			JTextField textSelFont = new JTextField();
			textSelFont.setColumns(16);
			textSelFont.setMaximumSize(new Dimension(320,10));
			
			String[] sysFontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
			//int sysFtLstSize = sysFontList.length;
			//JTextArea[] fontDisp = new JTextArea[sysFtLstSize];
					
			JList<String> fonts = new JList<>(sysFontList);
									
			fonts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			fonts.addListSelectionListener(listener->{
				fontName = fonts.getSelectedValue();
				textSelFont.setText(fontName);
				selectedFont = new Font(fontName,fontStyle, fontSize);
				preview.setFont(selectedFont);
				
			});
			
			
			leftSideConfigPanel.add(fontLabel);
			leftSideConfigPanel.add(textSelFont);
			leftSideConfigPanel.add(new JScrollPane(fonts));
			
			
			//JScrollPane jscrp1 = new JScrollPane(fontSelect);
			//jscrp1.setMaximumSize(new Dimension(320,200));
			//leftSideConfigPanel.add(jscrp1, BorderLayout.CENTER);
			
			
		
		//Center
			JPanel centerConfigPanel = new JPanel();
			
			centerConfigPanel.setLayout(new BoxLayout(centerConfigPanel, BoxLayout.Y_AXIS));
			centerConfigPanel.setMaximumSize(new Dimension(400,200));	
			
			JLabel fontStyleLabel = new JLabel("Font Style: ");
			JTextField textSelectStyle = new JTextField();
			textSelectStyle.setText("Plain");
			textSelectStyle.setColumns(6);
			textSelectStyle.setMaximumSize(new Dimension(200,10));
			
			String[] styles = {"Plain","Bold","Italic", "Bold and Italic"};
			JList<String> listFontStyle = new JList<String>(styles);
			
			listFontStyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listFontStyle.setFixedCellWidth(100);
			
			
			listFontStyle.addListSelectionListener(listener->{
				
				String selectedVal = listFontStyle.getSelectedValue();
				if(selectedVal == "Plain")
					fontStyle = 0;
				else if(selectedVal == "Bold")
					fontStyle = 1;
				else if(selectedVal == "Italic")
					fontStyle = 2;
				else if(selectedVal == "Bold and Italic")
					fontStyle = 3;	
				
				textSelectStyle.setText(listFontStyle.getSelectedValue());
				selectedFont = new Font(fontName,fontStyle, fontSize);
				preview.setFont(selectedFont);
				
			}); 
			
			
			centerConfigPanel.add(fontStyleLabel);
			centerConfigPanel.add(textSelectStyle);
			JScrollPane jscrp2 = new JScrollPane(listFontStyle);
			//jscrp2.setMaximumSize(new Dimension(320,200));
			centerConfigPanel.add(jscrp2);
		
			
		//Right side of the panel
			JPanel rightSideConfigPanel = new JPanel();
			
			rightSideConfigPanel.setLayout(new BoxLayout(rightSideConfigPanel, BoxLayout.Y_AXIS));
			rightSideConfigPanel.setMaximumSize(new Dimension(400,200));
			
			JLabel fontSizeLabel = new JLabel("Size: ");
			JTextField selectedSize = new JTextField();
			selectedSize.setMaximumSize(new Dimension(200,10));
			
			String[] sizes = {"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72"};
			JList<String> listFontSize = new JList<String>(sizes);
			
			listFontSize.setFixedCellWidth(60);
			listFontSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			listFontSize.addListSelectionListener(listener->{
				fontSize = Integer.parseInt(listFontSize.getSelectedValue());
				selectedSize.setText(Integer.toString(fontSize));
				selectedFont = new Font(fontName,fontStyle, fontSize);
				preview.setFont(selectedFont);
				
			});
					
			
			rightSideConfigPanel.add(fontSizeLabel);
			rightSideConfigPanel.add(selectedSize);
			JScrollPane jscrp3 = new JScrollPane(listFontSize);
			//jscrp3.setMaximumSize(new Dimension(320,200));
			rightSideConfigPanel.add(jscrp3);
			
		
		
		fontConfigPanel.add(leftSideConfigPanel);
		fontConfigPanel.add(Box.createRigidArea(new Dimension(15,0)));
		fontConfigPanel.add(centerConfigPanel);
		fontConfigPanel.add(Box.createRigidArea(new Dimension(15,0)));
		fontConfigPanel.add(rightSideConfigPanel);
		
		//====================================
		//Bottom preview and buttons section
		
		JPanel bottomPanel = new JPanel();
		
		
		preview.setEditable(false);
		preview.setSize(100, 100);
		
		JScrollPane jscrp4 = new JScrollPane(preview);
		jscrp4.setPreferredSize(new Dimension(460,150));
		
		JButton okBttn = new JButton("OK");
		okBttn.addActionListener(ae->{
			fontDialog.setVisible(false);
			returnFont = selectedFont;
		});
		
		
		JButton cancelBttn = new JButton("Cancel");
		cancelBttn.addActionListener(ae->{
			fontDialog.setVisible(false);
		});
		
		
		JPanel bttnPanel = new JPanel();
		bttnPanel.setLayout(new FlowLayout());
		
		bttnPanel.add(okBttn);
		bttnPanel.add(cancelBttn);
		
		bottomPanel.add(jscrp4);
		bottomPanel.add(bttnPanel);
		
		fontDialog.add(fontConfigPanel);
		fontDialog.add(bottomPanel);
		
		fontDialog.setVisible(true);
		
		
		return returnFont;
	}
	
}
