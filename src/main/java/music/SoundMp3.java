package music;

import javazoom.jl.player.advanced.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

// MP3, WMA, MPEG, WAV compatible

public class SoundMp3 implements Runnable
{
	private String path;
	
	private boolean        isPlaying = false;
	private AdvancedPlayer player    = null;
	private boolean        reLaunch  = false;
	
	public SoundMp3(String path, boolean reLaunch) throws Exception
	{				
		this.reLaunch = reLaunch;
		this.path     = path;
		
		InputStream audioSrc   = this.getClass().getResourceAsStream(path);
		
		InputStream in = new BufferedInputStream( audioSrc );
		player = new AdvancedPlayer(in);
	}

	public SoundMp3(String path, PlaybackListener listener) throws Exception
	{
		InputStream in = (InputStream) new BufferedInputStream(new FileInputStream(new File(path)));
		player = new AdvancedPlayer(in);
		player.setPlayBackListener(listener);
	}

	public void play() throws Exception
	{
		if (player != null)
		{
			isPlaying = true;
			player.play();
		}
	}

	public void play( int begin, int end ) throws Exception
	{
		if (player != null)
		{
			isPlaying = true;
			player.play(begin, end);
		}
	}

	public void stop() throws Exception
	{
		if (player != null)
		{
			isPlaying = false;
			player.stop();
		}
	}

	public boolean isPlaying()
	{
		return isPlaying;
	}
	
	@Override
	public void run()
	{
		try
		{
			this.play();
			
			while( this.reLaunch )
			{
				InputStream audioSrc   = this.getClass().getResourceAsStream(path);
				
				InputStream in = new BufferedInputStream( audioSrc );
				player = new AdvancedPlayer(in);
				
				this.play();
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
