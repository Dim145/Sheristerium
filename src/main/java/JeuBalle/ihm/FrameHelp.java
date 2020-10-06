package JeuBalle.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameHelp extends JFrame implements ActionListener
{
	private static final long serialVersionUID = -22175843139077035L;
	
	private JButton buttonOK;
	
	public FrameHelp()
	{
		this.setTitle("Aide");
		this.setSize(500, 500);
		this.setLayout( new BorderLayout() );
		
		this.buttonOK = new JButton("OK");
		this.buttonOK.addActionListener(this);
		
		JPanel panelHTML = new JPanel();
		JLabel labelHTML = new JLabel( FrameHelp.construireHTML() );
		
		panelHTML.add( labelHTML );
		
		this.add( panelHTML, BorderLayout.CENTER );
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed( ActionEvent e )
	{
		if( e.getSource() == this.buttonOK )
			this.dispose();
	}
	
	private static String construireHTML()
	{
		return 	"<html>\r\n" + 
				"    <head>\r\n" + 
				"        <meta charset=\"UTF-8\">\r\n" + 
				"        <title>Premier JQuery</title>\r\n" + 
				"    </head>\r\n" + 
				"    <body>\r\n" + 
				"        <h1 style=\"background-color: green;\">Le reste de cette page est fait en JS avec JQuery</h1>\r\n" + 
				"\r\n" + 
				"        <!-- Balise destiné a etre modifier -->\r\n" + 
				"        <p id=\"balise-p1\" style=\"margin: 10%;color: blue;\">Je ne suis pas modifier</p>\r\n" + 
				"\r\n" + 
				"        <ul style=\"margin: 5%;\">\r\n" + 
				"            <li>Texte 1</li>\r\n" + 
				"            <li>Texte 2</li>\r\n" + 
				"        </ul>\r\n" + 
				"\r\n" + 
				"        <ul>\r\n" + 
				"            <li>Autre Texte 1</li>\r\n" + 
				"            <li>Autre Texte 2</li>\r\n" + 
				"            <li>Je ne veux pas etre vert !</li>\r\n" + 
				"        </ul>\r\n" + 
				"\r\n" + 
				"        <img src=\"./image0.png\">\r\n" + 
				"        <img src=\"/arduino.png\">\r\n" + 
				"        <img id=\"logo\">\r\n" + 
				"\r\n" + 
				"        <!-- Une ou plusieurs balises HTML pour définir le contenu du document -->\r\n" + 
				"        <script src=\"./bin/jquery-3.4.1.js\"/></script>\r\n" + 
				"        <script src=\"./bin/scriptPerso.js\" /></script>\r\n" + 
				"\r\n" + 
				"        <b>Spaw/Despawn</b>\r\n" + 
				"    </body>\r\n" + 
				"</html>\r\n";
	}

}
