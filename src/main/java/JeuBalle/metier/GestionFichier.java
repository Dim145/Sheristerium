package JeuBalle.metier;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class GestionFichier
{	
	private static String[] slashSystem = { "\\", "/" };
	private static int      indSlash    = System.getProperty("os.name").contains("Windows") ? 0 : 1;

	public static void enregistrerScore( int level )
	{
		PrintWriter pw;
		
		try
		{
			File fichBase = new File("./highScore.jbd");
			File fichCryp = new File(fichBase.getAbsolutePath().substring(0, fichBase.getAbsolutePath().lastIndexOf(slashSystem[indSlash])) + "highCryp.jbd");
			fichCryp.delete();
			
			pw = new PrintWriter( new FileWriter ("./highScore.jbd") );
			pw.println( level );
			
			pw.close();
			
			GestionFichier.traitement( Cipher.ENCRYPT_MODE, "e8jfc7e56318619f12b6fc91aa74a5ec", "AES", "AES", fichBase, fichCryp);
			
			fichBase.delete();
			
			if( GestionFichier.indSlash == 0 )
				GestionFichier.cacher( true );
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void cacher( boolean bCacher )
	{
		String commande;
		
		if ( bCacher )
			commande = "attrib +s +h ./.highCryp.jbd";
		else
			commande = "attrib -s -h ./.highCryp.jbd";
		
		Runtime runtime = Runtime.getRuntime();
		
		try { runtime.exec( commande );}
		catch( Exception e ) { e.printStackTrace();}
	}

	public static int lireScore()
	{
		Scanner sc;
		
		try
		{
			File fichCryp = new File(".highCryp.jbd" );
			File fichBase = new File(fichCryp.getAbsolutePath().substring(0, fichCryp.getAbsolutePath().lastIndexOf(slashSystem[indSlash])+1) + "highScore.jbd");
			
			GestionFichier.traitement( Cipher.DECRYPT_MODE, "e8jfc7e56318619f12b6fc91aa74a5ec", "AES", "AES", fichCryp, fichBase);
			
			sc = new Scanner ( new FileReader( "./highScore.jbd" ) ) ;
			
			int temp = Integer.parseInt(sc.nextLine());
			
			sc.close();
			fichBase.delete();
			
			return temp;
		}
		catch( Exception e)
		{
			return 0;
		}
	}
	
	private static void traitement(int mode, String cle,String algorithme,String transformation,File fichierEntree,File fichierSortie)
    {
        try
        {
            Key cleSecret = new SecretKeySpec ( cle.getBytes(), algorithme );
            Cipher cipher = Cipher.getInstance( transformation );

            cipher.init( mode, cleSecret );

            FileInputStream inputStream = new FileInputStream(fichierEntree);
            byte[]          inputBytes  = new byte[(int) fichierEntree.length()];

            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(fichierSortie);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();
        }
        catch (IOException e )              { return; }
        catch (NoSuchAlgorithmException e)  { e.printStackTrace(); }
        catch (NoSuchPaddingException e)    { e.printStackTrace(); }
        catch (InvalidKeyException e)       { e.printStackTrace(); }
        catch (IllegalBlockSizeException e) { e.printStackTrace(); }
        catch (BadPaddingException e)       { e.printStackTrace(); }
    }
}
