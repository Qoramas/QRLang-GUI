import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


public class Statics {

	public static Image iconToImage(Icon icon) {
		if (icon instanceof ImageIcon) {
			return ((ImageIcon) icon).getImage();
		} else {
			BufferedImage image = new BufferedImage(icon.getIconWidth(),
					icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
			icon.paintIcon(null, image.getGraphics(), 0, 0);
			return image;
		}
	}

	public static void copyText(String string){
		StringSelection stringSelection = new StringSelection (string);
		Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		clpbrd.setContents (stringSelection, null);
	}

	public static void copyImage(BufferedImage bi) {
		TransferableImage trans = new TransferableImage( bi );
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		c.setContents( trans, null );
	}

	private static class TransferableImage implements Transferable {
		Image i;

		public TransferableImage( Image i ) {
			this.i = i;
		}

		public Object getTransferData( DataFlavor flavor ) throws UnsupportedFlavorException, IOException {
			if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
				return i;
			}
			else {
				throw new UnsupportedFlavorException( flavor );
			}
		}

		public DataFlavor[] getTransferDataFlavors() {
			DataFlavor[] flavors = new DataFlavor[ 1 ];
			flavors[ 0 ] = DataFlavor.imageFlavor;
			return flavors;
		}

		public boolean isDataFlavorSupported( DataFlavor flavor ) {
			DataFlavor[] flavors = getTransferDataFlavors();
			for ( int i = 0; i < flavors.length; i++ ) {
				if ( flavor.equals( flavors[ i ] ) ) {
					return true;
				}
			}

			return false;
		}
	}
	
	public static BufferedImage getQR(String data, int width, int height) {
		// get a byte matrix for the data
		BitMatrix matrix;
		Writer writer = new QRCodeWriter();
		try {
			matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE,width,height);
		} catch (com.google.zxing.WriterException e) {
			// exit the method
			return null;
		}

		// create buffered image to draw to
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// iterate through the matrix and draw the pixels to the image
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				boolean greyValue = matrix.get(x, y);
				image.setRGB(x, y, (greyValue ? 0 : 0xFFFFFF));
			}
		}

		return image;
	}
	
	public static void saveImage(BufferedImage image, String ext) throws IOException{
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(ext, ext, ext);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(filter);
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); 
        int success = fc.showSaveDialog(null);
        
        if(success == JFileChooser.APPROVE_OPTION){
        	String dir = fc.getSelectedFile().getAbsolutePath();
        	if(!dir.endsWith("." + ext)) dir += "." + ext;
        	ImageIO.write(image,"png",new File(dir));
        }
	}
	
	public static void errorMessage(String message){
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
