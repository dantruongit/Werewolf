package Client.utils;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class gui {
    public static void changePanel(JPanel source, JPanel target){
        source.removeAll();
        source.setLayout(new BorderLayout());
        source.add(target, BorderLayout.CENTER);
        source.revalidate();
        source.repaint();
    }
    public static void showError(String titleError, String contentError, boolean needExit){
        JOptionPane.showConfirmDialog(null, contentError,titleError, JOptionPane.ERROR_MESSAGE);
        if(needExit) System.exit(0);
    }
    public static void showMessage(String content){
        JOptionPane.showMessageDialog(null, content);
    }
    public static void debug(String t){
        System.out.println(t);
    }
    
    public static ImageIcon resizeImage(String path, int width, int height){
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(resizedImage);
            return icon;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
