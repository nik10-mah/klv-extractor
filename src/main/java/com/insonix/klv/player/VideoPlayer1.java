/**
 * 
 */
package com.insonix.klv.player;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * @author DELL PC
 *
 */
public class VideoPlayer1 {

		
	private final JFrame frame;

    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    
    private static String VLC_LIB_PATH = "C:\\Program Files\\VideoLAN\\VLC";
	
	public VideoPlayer1()  {
		
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

        frame = new JFrame("vlcj quickstart");
        frame.setLocation(50, 50);
        frame.setSize(800, 600);
        frame.setContentPane(mediaPlayerComponent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("jdk version:  " + System.getProperty("sun.arch.data.model") + " bits.");
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), VLC_LIB_PATH);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		final String mrl = args[0];

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VideoPlayer1().start(mrl);
            }
        });

	}
	
	private void start(String mrl) {
        mediaPlayerComponent.getMediaPlayer().playMedia(mrl);
    }

}
