// StreamingPlayer.java

package ch.aplu.turtle;


import javax.sound.sampled.*;
import java.io.*;

class StreamingPlayer
{
  private class PlayerThread extends Thread
  {
    public void run()
    {
      byte buf[] = new byte[20000];
      try
      {
        int cnt;
        while ((cnt = audioInputStream.read(buf, 0, buf.length)) != -1)
        {
          if (cnt > 0)
            sourceDataLine.write(buf, 0, cnt);
        }
        sourceDataLine.drain();
        sourceDataLine.close();
      }
      catch (IOException ex)
      {
        System.out.println(ex);
        System.exit(1);
      }
    }
  }

  private AudioFormat audioFormat;
  private AudioInputStream audioInputStream;
  private SourceDataLine sourceDataLine;
  private PlayerThread playerThread;

  StreamingPlayer(InputStream is)
  {
    try
    {
      audioInputStream =
          AudioSystem.getAudioInputStream(is);
      audioFormat = audioInputStream.getFormat();
    }
    catch (Exception ex)
    {}
  }

  void start(boolean doFinish)
          throws LineUnavailableException
  {
    DataLine.Info dataLineInfo =
      new DataLine.Info(SourceDataLine.class, audioFormat);
    sourceDataLine =
      (SourceDataLine)AudioSystem.getLine(dataLineInfo);
    sourceDataLine.open(audioFormat);
    sourceDataLine.start();
    playerThread = new PlayerThread();
    playerThread.start();
    if (doFinish)
      waitToFinish();
  }

  boolean isPlaying()
  {
    if (playerThread == null)
      return false;
    return (playerThread.isAlive());
  }

  void waitToFinish()
  {
    if (playerThread == null)
      return;
    try
    {
      playerThread.join();
    }
    catch (InterruptedException ex) {}
  }
}
