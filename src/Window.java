import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.plaf.metal.MetalIconFactory;

import qor.Compressor;
import qor.IllegalCharacterException;


/**
 * GUI for the QRLang project. This takes valid QRLang code and compresses it down to its string
 * form and returns the QR code for it.
 * @author Samuel Haycock
 *
 */
public class Window extends JFrame {

	private final static int WIDTH = 720;
	private final static int HEIGHT = 480;
	public final static boolean WLAF = true; //Windows look and feel
	
	private JButton copyTextButton = new JButton("Copy Text");
	private JButton copyImageButton = new JButton("Copy QRCode");
	private JButton saveImageButton = new JButton("Save QRCode");
	private JButton compressButton = new JButton("Compress");
	
	private JLabel inputLabel = new JLabel("Input:");
	private JLabel outputLabel = new JLabel("Output:");
	
	private JLabel imageLabel = new JLabel();

	private JPanel mainPanel = new JPanel(); //Holds all of the panels
	private JEditorPane codeArea = new JEditorPane();
	private JScrollPane codeScrollPane = new JScrollPane(codeArea);
	
	private JTextArea outputArea = new JTextArea();
	private JScrollPane outputScrollPane = new JScrollPane(outputArea);
	
	private Compressor c = new Compressor();
	private BufferedImage qrCode;

	/**
	 * Constructor
	 */
	public Window() {
		setTitle("QR Compressor");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setIconImage(Statics.iconToImage(MetalIconFactory.getTreeHardDriveIcon()));
		setLocationRelativeTo(null); //Centres the Window
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		Font font = new Font("Courier New",Font.PLAIN, 12);
		
		codeArea.setFont(font);
		codeScrollPane.getViewport().add(codeArea);
		codeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		codeArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		outputArea.setFont(font);
		outputArea.setLineWrap(true);
		outputArea.setEditable(false);
		outputScrollPane.getViewport().add(outputArea);
		outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		outputArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		mainPanel.add(codeScrollPane);
		mainPanel.add(outputScrollPane);
		
		mainPanel.add(inputLabel);
		mainPanel.add(outputLabel);
		mainPanel.add(imageLabel);
		
		mainPanel.add(copyTextButton);
		mainPanel.add(copyImageButton);
		mainPanel.add(saveImageButton);
		mainPanel.add(compressButton);
		
		
		copyTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(qrCode != null){
					Statics.copyText(outputArea.getText());
				} else {
					Statics.errorMessage("No code has been generated");
				}
			}
		});
		
		copyImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(qrCode != null){
					Statics.copyImage(qrCode);
				} else {
					Statics.errorMessage("No QR Code has been generated");
				}
			}
		});
		
		saveImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(qrCode != null){
					try {
						Statics.saveImage(qrCode, "png");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Statics.errorMessage("No QR Code has been generated");
				}
			}
		});
		
		compressButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(codeArea.getText().equals("")){
					Statics.errorMessage("No code to compress");
				} else {
					try {
						outputArea.setText(c.compress(codeArea.getText()));
						qrCode = Statics.getQR(outputArea.getText(), 180, 180);
						imageLabel.setIcon(new ImageIcon(qrCode));
					} catch (IllegalCharacterException e) {
						Statics.errorMessage("Illegal Character : " + e.getIllegalCharacter());
					}
					
				}
			}
		});

		setBoundsAll();
	}
	
	/**
	 * Sets the location and size of all components
	 */
	private void setBoundsAll() {
		inputLabel.setBounds(10, 10, 500, 20);
		codeScrollPane.setBounds(10, 30, 500, 200);
		outputLabel.setBounds(10, 240, 500, 20);
		outputScrollPane.setBounds(10, 260, 500, 180);
		
		imageLabel.setBounds(520, 260, 180, 180);
		
		copyTextButton.setBounds(520, 10, 180, 30);
		copyImageButton.setBounds(520, 50, 180, 30);
		saveImageButton.setBounds(520, 90, 180, 30);
		compressButton.setBounds(520, 130, 180, 30);
	}


	public static void main(String[] args) {
		if (WLAF) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Window w = new Window();
				w.setVisible(true);
				w.setBoundsAll();
				
			}
		});
	}
}