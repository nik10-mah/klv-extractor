/**
 * 
 */
package com.insonix.klv.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.insonix.klv.player.InfoPanel;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * @author Nikhil Mahajan
 * 
 * @since Apr 24, 2018
 */
public class MainApp {

	private final JFrame frame;

	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

	private static String VLC_LIB_PATH = "C:\\Program Files\\VideoLAN\\VLC";

	public MainApp() throws HeadlessException {

		frame = new JFrame("KLV Data Extractor");
		frame.setLocation(20, 20);
		frame.setSize(1000, 650);
		// frame.setContentPane(mediaPlayerComponent);

		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		init();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// frame.setContentPane(container);
	}

	/***
	 * Initialize Frame with two side by side panels
	 */
	public void init() {

		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

		mediaPlayerComponent.setPreferredSize(new Dimension(500, 320));
		mediaPlayerComponent.setBackground(Color.decode("#072336"));

		container.add(mediaPlayerComponent);
		container.add(new InfoPanel(mediaPlayerComponent));

		frame.setContentPane(container);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Need to load VLC Libs from LIB path to embed VLC Media Player
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), VLC_LIB_PATH);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainApp();
			}
		});

	}

}
