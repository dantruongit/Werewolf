package server;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import config.DBConnection;
import javax.swing.UIManager;

/**
 *
 * @author cr4zyb0t
 */

public class RunServer {
    public static void main(String[] args) {
        DBConnection.gI().getConnection();
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        new MainFrame().setVisible(true);
    }
    
}
