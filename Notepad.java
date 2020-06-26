// 
//    Name:  Murawski, Mateusz
//    Homework: 5#
//    Due:       May 9, 2019
//    Course: cs-2450-02-sp19 
// 
//    Description: 
// 	  Copy of Notepad found in Windows operating system.  
//
//


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Notepad implements ActionListener {
	
	JLabel jlab;
	JTextArea mainTxtView;
	static JFrame fram = new JFrame("Untitled - Notepad");
	String filePath;
	BufferedReader reader;
	BufferedWriter save;
	
	boolean viewChanged = false;
	
	JDialog goToDialog;
	JTextField goToArea;
	Clipboard clipb = Toolkit.getDefaultToolkit().getSystemClipboard();
	JLabel areUSureLabel;
	boolean exitbool = false;
	boolean dontSav = false;
	
	JFileChooser fileChooser = new JFileChooser();
	String fileName = fileChooser.getName();
	
	JDialog areUSure;
	
	 public void actionPerformed(ActionEvent ae)
	 	{
	        // If user chooses Exit, then exit the program. 
	        if (ae.getActionCommand().equals("Exit")) 
	        {
	        	if(viewChanged==true)
	        	{
	        		 exitbool = true;
	        		 areUSure.setVisible(true);
	        		 areUSureLabel.setText("Do you want to save changes to "+fileName);
	        	}
	        	else
	        	{
	        		 System.exit(0);
	        	}
	        	//upewnij sie ze viewchanged is false
	        	//jezeli jest true to daj dialog czy 
	           
	        }
	        else if(ae.getActionCommand().equals("Don't Save"))
	        {
	              	areUSure.setVisible(false);
	              	dontSav = true;
	              	if (exitbool == true)
	              	   	System.exit(0);
	        }
	        else if(ae.getActionCommand().equals("Print..."))
	        {
	        	JLabel msg = new JLabel("Printing...");
	        	JOptionPane.showMessageDialog(fram, msg, "Print",JOptionPane.PLAIN_MESSAGE, null);
	        }
	        else if(ae.getActionCommand().equals("About Notepad"))
	        {
	        	JLabel msg = new JLabel("(c) 2019 M. Murawski");
	        	ImageIcon image = new ImageIcon(new ImageIcon("Notepad.png").getImage().getScaledInstance(70, -1, Image.SCALE_SMOOTH));
	            		
	        	JOptionPane.showMessageDialog(fram, msg, "About",JOptionPane.PLAIN_MESSAGE, image);
	        	
	        }
	        else if(ae.getActionCommand().equals("Open..."))
	        {
	        	//najpierw upewnij sie czy chcesz zapisac 
	        	//jezeli zostalo cos zmienione
	        	
	        	if(viewChanged==true)
	        	{
	        		 areUSure.setVisible(true);
	        		 //daj tutaj czy chcesz zapisac zmiany
	        		 //if nacisneli kansel to wyjebnokgo cofaj do glownego
	        		 //jesli nacisneli don't save to wtedy rob wszystko jak gdyby nigdy nic
	        		 //jesli nacisneli save to bierzesz wszystko i zapisuejsz zmiankio
	        		 
	        	}
	        	else
	        	{
	        		 
	        	}
	        	
	    	    int approved = fileChooser.showOpenDialog(fram);
	    	    if(approved == JFileChooser.APPROVE_OPTION)
	    	    {
	    	       //filePath = fileChooser.getSelectedFile().getAbsolutePath();
	    	    	filePath = fileChooser.getSelectedFile().toString();
	    	    	fileName = fileChooser.getSelectedFile().getName();
	    	    	String name = fileChooser.getSelectedFile().getName();
	    	    	String line = null;
		    	    StringBuilder content = new StringBuilder(); 
		    	    
		    	
		    	    try {
						reader = new BufferedReader(new FileReader(new File(filePath)));
					} catch (FileNotFoundException e) {
						System.err.println("File does not exist");
						e.printStackTrace();
					}
	    	    	
	    	    	try {
						line = reader.readLine();
					
						while(line !=null)
						{
							content.append(line).append("\n");
							line = reader.readLine();
						}
	    	    	
						
	    	    	} catch (IOException e) {
	    	    		System.err.println("File could not be read");
	    	    		e.printStackTrace();
					}
		    	    
	    	    	
	    	    	fram.setTitle(name +" - Notepad");		
	    
	    	    	//mainTxtView.setDocument(content);
		    	    mainTxtView.setText(content.toString());
		    	    mainTxtView.setCaretPosition(0);
		    	     
	    	    }
	    	    
	    	    //viewChanged=false;

	        }
	        else if(ae.getActionCommand().equals("Go to..."))
	        {
	        	goToDialog.setVisible(true);
	        	
	        }
	        else if(ae.getActionCommand().equals("Go To"))
	        {
	        	//bedziesz musial sam to obliczac
	        	//get text from main view
	        	//update charsInALine 
	        	//update lineCount
	        	
	        	String allTheText = mainTxtView.getText();
	        	String[] SeperatedLines = allTheText.split("\n");
	        	int lineNum = SeperatedLines.length;
	        	
	        	
	        	
	        	//this is the number that user inputs
	        	//represent the line number we want to go to.
	        	
	        	//int line = Integer.parseInt(goToArea.getText())-1;
	        	int line = Integer.parseInt(goToArea.getText())-1;
	      
	        	int caretPos = 0;
	        	
	        	if(line>lineNum)
	        	{
	        		String msg = "The line number is beyond the total number of lines";
	        		JOptionPane.showMessageDialog(goToDialog, msg, "Notepad - Goto Line", JOptionPane.PLAIN_MESSAGE);
	           	}
	        	else
	        	{
	        		for (int i = 0; i<line;i++)
		        	{
		        		caretPos = caretPos+1+ SeperatedLines[i].length();
		        	}
		        	//illegal argumenta tu masz
	        		// staraj sie go zlapac
		        	mainTxtView.setCaretPosition(caretPos);	
			    	goToDialog.setVisible(false);
	        	}
	        	
	        }
	        else if(ae.getActionCommand().equals("Save"))
	        {
	        	//jesli filepath jest null otworz save as.
	        	if(filePath == null)
	        	{
		    	    int approved = fileChooser.showSaveDialog(fram);
			    	   
		    	    if(approved == JFileChooser.APPROVE_OPTION)
		    	    {
		    	       //filePath = fileChooser.getSelectedFile().getAbsolutePath();
		    	    	filePath = fileChooser.getSelectedFile().toString();
		    	    	
		    	    	String chosenExt = fileChooser.getFileFilter().getDescription();
		    	    	
		    	    	fileName = fileChooser.getSelectedFile().getName();
		    	    	fram.setTitle(fileName +" - Notepad");	
			        	
		    	    	if(chosenExt.equals("Java Source file"))
		    	    	{
		    	    		filePath = filePath.concat(".java");
		    	    	}
		    	    	else
		    	    	{
		    	    		filePath = filePath.concat(".txt");
		    	    	}
		    	    	
		    	    	FileWriter saveDest = null;
		    	    	
		    	    	try {
		    	    		saveDest =new FileWriter(filePath);
							save = new BufferedWriter(saveDest);
							String content = mainTxtView.getText();
							save.write(content);
							save.close();
		    	    	} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    	    	finally
		    	    	{
		    	    		if(saveDest !=null)
		    	    		{
		    	    			try
		    	    			{
		    	    				saveDest.flush();
		    	    				saveDest.close();
		    	    			}
		    	    			catch(IOException e)
		    	    			{
		    	    				
		    	    			}
		    	    		}	
		    	    	}
		    
		    	    	viewChanged = false;
		    	    }
		    
	        		areUSure.setVisible(false);
	        	}
	        	else
	        	{
	        		FileWriter saveDest = null;
	        		try {
	    	    		saveDest =new FileWriter(filePath);
						save = new BufferedWriter(saveDest);
						String content = mainTxtView.getText();
						save.write(content);
						save.close();
	    	    	} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    	finally
	    	    	{
	    	    		if(saveDest !=null)
	    	    		{
	    	    			try
	    	    			{
	    	    				saveDest.flush();
	    	    				saveDest.close();
	    	    			}
	    	    			catch(IOException e)
	    	    			{
	    	    				
	    	    			}
	    	    		}	
	    	    	}
	    
	        		fileName = fileChooser.getSelectedFile().getName(); 
		        	fram.setTitle(fileName +" - Notepad");	
		        	viewChanged = false;
	        		areUSure.setVisible(false);
		        	
	        	}
	        	
	        	
	        	
	        }
	        else if(ae.getActionCommand().equals("Save As..."))
	        {
	        	//jesli filepath jest null otworz save as.
	        	//save as powinnenes pokazac nowy filepath
	        
	    	    int approved = fileChooser.showSaveDialog(fram);
	    	    if(approved == JFileChooser.APPROVE_OPTION)
	    	    {
	    	       //filePath = fileChooser.getSelectedFile().getAbsolutePath();
	    	    	
	    	    	filePath = fileChooser.getSelectedFile().toString();
	    	    	String chosenExt = fileChooser.getFileFilter().getDescription();
	    	    	fileName = fileChooser.getSelectedFile().getName();
	    	    	fram.setTitle(fileName +" - Notepad");	
	    	    	
	    	    	if(chosenExt.equals("Java Source file"))
	    	    	{
	    	    		filePath = filePath.concat(".java");
	    	    	}
	    	    	else
	    	    	{
	    	    		filePath = filePath.concat(".txt");
	    	    	}
	    	  
	    	    	 //filePath = fileChooser.getSelectedFile().getAbsolutePath();
	    	    	FileWriter saveDest = null;
	    	    	
	    	    	try {
	    	    		saveDest =new FileWriter(filePath);
						save = new BufferedWriter(saveDest);
						String content = mainTxtView.getText();
						save.write(content);
						//.println(content);
						save.close();
	    	    	} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    	finally
	    	    	{
	    	    		if(saveDest !=null)
	    	    		{
	    	    			try
	    	    			{
	    	    				saveDest.flush();
	    	    				saveDest.close();
	    	    			}
	    	    			catch(IOException e)
	    	    			{
	    	    				
	    	    			}
	    	    		}	
	    	    	}
	    
		        	fileChooser.getSelectedFile().getName();
		        	fram.setTitle(fileName +" - Notepad");	
		        	
		        	viewChanged=false;
	    	    	
	    	    }
	    	    
	        	
	    	   
	        }
	        else if(ae.getActionCommand().equals("Copy"))
	        {
	        	String copyStuff = mainTxtView.getSelectedText();
	        	StringSelection strsel = new StringSelection(copyStuff);
	        	clipb.setContents(strsel, null);
	        }
	        else if(ae.getActionCommand().equals("Paste"))
	        {
	        	String pasteStuff = "";
	        	try {
					pasteStuff = clipb.getData(DataFlavor.stringFlavor).toString();
				} catch (UnsupportedFlavorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	Document doc = (Document)mainTxtView.getDocument();
	        	int selStart = mainTxtView.getSelectionStart();
	        	int selEnd = mainTxtView.getSelectionEnd();
	        	int selLen = Math.abs(selStart - selEnd);
	        	
	        	
	        	try {
					doc.insertString(selStart, pasteStuff, null);
					mainTxtView.setDocument(doc);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        else if(ae.getActionCommand().equals("Delete"))
	        {
	        	int selStart = mainTxtView.getSelectionStart();
	        	int selEnd = mainTxtView.getSelectionEnd();
	        	int selLen = Math.abs(selStart - selEnd);
	        	Document doc = (Document)mainTxtView.getDocument();
	        	
	        	try {
					doc.remove(selStart, selLen);
					mainTxtView.setDocument(doc);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        else if(ae.getActionCommand().equals("Cut"))
	        {	
	     
	        	String copyStuff = mainTxtView.getSelectedText();
	        	StringSelection strsel = new StringSelection(copyStuff);
	        	clipb.setContents(strsel, null);
	        	
	        	int selStart = mainTxtView.getSelectionStart();
	        	int selEnd = mainTxtView.getSelectionEnd();
	        	int selLen = Math.abs(selStart - selEnd);
	        	Document doc = (Document)mainTxtView.getDocument();
	        	
	        	try {
					doc.remove(selStart, selLen);
					mainTxtView.setDocument(doc);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	        		
	        	
	        }
	        else if(ae.getActionCommand().equals("New"))
	        {
	         	if(viewChanged == false)
	        	{
		        	filePath = null;
		        	fram.setTitle("Untitled - Notepad");
		        	mainTxtView.setText("");	
	        	}
	        	else
	        	{
	        		areUSure.setVisible(true);
	        		
	        		if(dontSav == true)
	        		{
	        			filePath = null;
			        	fram.setTitle("Untitled - Notepad");
			        	mainTxtView.setText("");
	        		}
	        		else
	        		{
	        			areUSure.setVisible(false);
	        		}
	        	}
	        }
	       
	    }
	
	 class DocListener implements DocumentListener {
	       
	        public void insertUpdate(DocumentEvent de) {	
	        	viewChanged = true; 
	            //System.out.println(fileName);
		    	fram.setTitle("*"+fileName +" - Notepad");	
	         }
	        public void removeUpdate(DocumentEvent de) {
	            viewChanged = true;
	               fram.setTitle("*"+fileName +" - Notepad");	
	          }
			@Override
			public void changedUpdate(DocumentEvent de) {
				viewChanged = true; 
				     fram.setTitle("*"+fileName +" - Notepad");	
	       
			}
	    }

	 
	 
	Notepad(){
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//frame setup ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		
	    fram.setLayout(new FlowLayout());
		fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		fram.setLocationRelativeTo(null);
		//fram.setResizable(false);
		
		fram.setSize(800,600);
		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    fileChooser.setCurrentDirectory(new File("."));
	    
	    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text file", "txt")); //adds ability to select only .txt files
	    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Java Source file", "java")); //adds ability to select only .java files
	    
	    fileChooser.setAcceptAllFileFilterUsed(false); //only txt and java files can be chosen 	    
	
	    if(fileName==null)
	    {
	    	fileName = "Untitled";
	    }
	    
		JMenuBar menuBar = new JMenuBar();
	
		mainTxtView = new JTextArea();
		mainTxtView.getDocument().addDocumentListener(new DocListener());
		mainTxtView.setFont(new Font("Courier New", Font.PLAIN, 12));
		mainTxtView.setForeground(Color.BLACK);
		mainTxtView.setBackground(Color.WHITE);
		
		mainTxtView.setLineWrap(false);
		mainTxtView.setWrapStyleWord(false);
		
		JCheckBoxMenuItem statusBarViewMI = new JCheckBoxMenuItem("Status Bar");
		
		
		areUSure = new JDialog(fram,"Notepad", true);
		areUSure.setLayout(new FlowLayout());
		areUSure.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		areUSure.setLocationRelativeTo(null);
		areUSure.setSize(240, 120);
		areUSure.setResizable(false);
		
		areUSureLabel = new JLabel("Do you want to save changes to "+fileName);
		
		JButton USureBttn1 = new JButton("Save");
		JButton USureBttn2 = new JButton("Don't Save");
		JButton USureBttn3 = new JButton("Cancel");
		
		
		USureBttn1.addActionListener(this);
		
		USureBttn2.addActionListener(this);
		
		USureBttn3.addActionListener(ae->
		{
			dontSav = false;
			areUSure.setVisible(false);
		});
		
		
		areUSure.add(areUSureLabel);
		areUSure.add(USureBttn1);
		areUSure.add(USureBttn2);
		areUSure.add(USureBttn3);
		
		JScrollPane scrlPane = new JScrollPane(mainTxtView);
		scrlPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		scrlPane.setPreferredSize(new Dimension(640,480));
		
		JMenu fileMenu = new JMenu("File");
		
		
		JMenuItem fileNewMI = new JMenuItem("New");
		JMenuItem fileOpenMI = new JMenuItem("Open...");
		JMenuItem fileSaveMI = new JMenuItem("Save");
		JMenuItem fileSaveAsMI = new JMenuItem("Save As...");
		JMenuItem filePrintSetMI = new JMenuItem("Print Setup...");
		JMenuItem filePrintMI = new JMenuItem("Print...");
		JMenuItem fileExitMI = new JMenuItem("Exit");
		
		fileNewMI.addActionListener(this);
		fileExitMI.addActionListener(this);
		fileOpenMI.addActionListener(this);
		fileSaveMI.addActionListener(this);
		fileSaveAsMI.addActionListener(this);
		
		fileNewMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		fileOpenMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		fileSaveMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		filePrintMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
		
		filePrintMI.setEnabled(false);
		filePrintSetMI.setEnabled(false);
		
		fileMenu.add(fileNewMI);
		fileMenu.add(fileOpenMI);
		fileMenu.add(fileSaveMI);
		fileMenu.add(fileSaveAsMI);
		fileMenu.addSeparator();		
		fileMenu.add(filePrintSetMI);
		fileMenu.add(filePrintMI);
		fileMenu.addSeparator();
		fileMenu.add(fileExitMI);
		
		
		JMenu editMenu = new JMenu("Edit");
		JMenuItem editUndo = new JMenuItem("Undo");
		JMenuItem editCut = new JMenuItem("Cut");	
		JMenuItem editCopy = new JMenuItem("Copy");
		JMenuItem editPaste = new JMenuItem("Paste");
		JMenuItem editDel = new JMenuItem("Delete");
		JMenuItem editFind = new JMenuItem("Find...");
		JMenuItem editFindNext = new JMenuItem("Find Next");
		JMenuItem editReplace = new JMenuItem("Replace...");
		JMenuItem editGoTo = new JMenuItem("Go to...");
		JMenuItem editSelAll = new JMenuItem("Select All");
		JMenuItem editTim = new JMenuItem("Time/Date");
		
		
		editCut.addActionListener(this);
		editCopy.addActionListener(this);
		editPaste.addActionListener(this);
		editDel.addActionListener(this);
		editFind.addActionListener(this);
		editFindNext.addActionListener(this);
		editGoTo.addActionListener(this);
		editSelAll.addActionListener(ae->
		{
			mainTxtView.setSelectionStart(0);	
			mainTxtView.setSelectionEnd(mainTxtView.getText().length());
		
		});
		editTim.addActionListener(ae->
		{
			Document doc = (Document)mainTxtView.getDocument();
			int selstr = mainTxtView.getSelectionStart();
			DateFormat dateFormat = new SimpleDateFormat("HH:mm MM/dd/yyyy");
			Date day = new Date();
			String dateStr = dateFormat.format(day);
			try {
				doc.insertString(selstr, dateStr, null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mainTxtView.setDocument(doc);
		});
		
		editUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));
		editCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
		editCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
		editPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));
		editDel.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
		editFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK));
		editFindNext.setAccelerator(KeyStroke.getKeyStroke("F3"));
		editReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
		editGoTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, Event.CTRL_MASK));
		editSelAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
		editTim.setAccelerator(KeyStroke.getKeyStroke("F5"));
		
		editMenu.add(editUndo);
		editMenu.addSeparator();
		editMenu.add(editCut);
		editMenu.add(editCopy);
		editMenu.add(editPaste);
		editMenu.add(editDel);
		editMenu.addSeparator();
		editMenu.add(editFind);
		editMenu.add(editFindNext);
		editMenu.add(editReplace);
		editMenu.add(editGoTo);
		editMenu.addSeparator();
		editMenu.add(editSelAll);
		editMenu.add(editTim);
		
		
		
		goToDialog = new JDialog(fram,"Go To Line", true);
		goToDialog.setLayout(new FlowLayout());
		goToDialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		goToDialog.setLocationRelativeTo(null);
		goToDialog.setSize(240, 140);
		goToDialog.setResizable(false);
			
		JLabel goToLabel = new JLabel("Line number:");
		goToArea = new JTextField();
		goToArea.setColumns(18);
		
		JButton goToBttn1 = new JButton("Go To");
		JButton goToBttn2 = new JButton("Cancel");
		
		goToBttn1.addActionListener(this);
		
		goToBttn2.addActionListener(ae->
		{
			goToDialog.setVisible(false);
		});
		
		
		goToDialog.add(goToLabel);
		goToDialog.add(goToArea);
		goToDialog.add(goToBttn1);
		goToDialog.add(goToBttn2);
		
		//goToDialog.pack();
		
		
		
		JMenu formatMenu = new JMenu("Format");
		
		JCheckBoxMenuItem formatWrapMI = new JCheckBoxMenuItem("Word Wrap");
		formatWrapMI.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				if(formatWrapMI.isSelected())
				{
					mainTxtView.setLineWrap(true);
					editGoTo.setEnabled(false);
					statusBarViewMI.setEnabled(false);
					statusBarViewMI.setSelected(false);
					//status bar disabled
				}
				else
				{
					mainTxtView.setLineWrap(false);
					editGoTo.setEnabled(true);
					statusBarViewMI.setEnabled(true);
					//status bar enabled
				}
					
			
			}

	
		});
		JMenuItem formatFontMI = new JMenuItem("Font...");
		formatFontMI.addActionListener(ae->{
			mainTxtView.setFont(JFontChooser.showDialog(fram, mainTxtView.getFont()));
		});
		
		JMenu formatColorPMI = new JMenu("Color");
		
		JMenuItem formatColorFront = new JMenuItem("Foreground");
		JMenuItem formatColorBack = new JMenuItem("Background");
		
		formatColorFront.addActionListener(ae ->
		{
			Color col = JColorChooser.showDialog(fram, "Select color", Color.BLACK); 
    		mainTxtView.setForeground(col);
		});
		
		formatColorBack.addActionListener(ae ->
		{
			Color col = JColorChooser.showDialog(fram, "Select color", Color.WHITE); 
			mainTxtView.setBackground(col);
		});
		
		formatColorPMI.add(formatColorFront);
		formatColorPMI.add(formatColorBack);
		
		formatMenu.add(formatWrapMI);
		formatMenu.add(formatFontMI);
		formatMenu.add(formatColorPMI);
		
		
		
		JMenu viewMenu = new JMenu("View");
		
		
		viewMenu.add(statusBarViewMI);
		
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem helpViewHelpMI = new JMenuItem("View Help");
		JMenuItem helpAboutMI = new JMenuItem("About Notepad");
		
		helpMenu.add(helpViewHelpMI);
		helpMenu.addSeparator();
		helpMenu.add(helpAboutMI);
		
		helpAboutMI.addActionListener(this);
		
		
		Image ikonka = Toolkit.getDefaultToolkit().getImage("Notepad.png");
		fram.setIconImage(ikonka);
		
		fram.setLayout(new BorderLayout());
		fram.add(scrlPane,BorderLayout.CENTER);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(formatMenu);
		menuBar.add(viewMenu);
		menuBar.add(helpMenu);
		
		
		
		fram.setJMenuBar(menuBar);
		
		fram.setVisible(true);
		fram.pack();
		viewChanged=false;
		
	}
	
	
	public static void main(String[] args){
				
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new Notepad();
			}
		});
	}
}