package JeuBalle.ihm;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

public class Apropos extends JOptionPane
{
    public Apropos()
    {
      String sAff = "<HTML><BODY><FONT COLOR=\"RED\"> <FONT SIZE=\"+1\"> <B>Sphéristérium</B> </FONT> </FONT></BODY></HTML>\n\n\n\n" +
                    "<HTML><BODY><FONT COLOR=\"WHITE\">Créer par: Dimitri Dubois</FONT> </BODY></HTML>\n\n"                   +
                    "<HTML><BODY><FONT COLOR=\"WHITE\">Développé en Septembre 2019</FONT> </BODY></HTML></FONT> </BODY></HTML> \n" +
                    "<HTML><BODY><FONT COLOR=\"WHITE\">Version beta 1.2.7</FONT> </BODY></HTML>";

      ColorUIResource basePane  = (ColorUIResource) UIManager.getColor( "OptionPane.background" );
      ColorUIResource basePanel = (ColorUIResource) UIManager.getColor( "Panel.background" );

      UIManager.put("OptionPane.background",new ColorUIResource(50,50,50));
      UIManager.put("Panel.background"     ,new ColorUIResource(50,50,50));

      this.showMessageDialog( null, sAff, "A propos...", JOptionPane.INFORMATION_MESSAGE, new ImageIcon( Apropos.class.getResource("/logoV1.png") ) );

      UIManager.put("OptionPane.background", basePane );
      UIManager.put("Panel.background"     , basePanel);
    }
}