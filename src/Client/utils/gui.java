package Client.utils;
import config.Constaint;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class gui {
    public static void playSound(String filename) {
    try {
        String path = Constaint.pathRoot + "/assets/sound/" + filename + ".wav";
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    } catch(Exception ex) {
        System.out.println("Error with playing sound.");
        ex.printStackTrace();
    }
}
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
