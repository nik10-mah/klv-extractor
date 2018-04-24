/**
 * 
 */
package com.insonix.klv.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.text.DefaultCaret;

import com.insonix.klv.UdpReader;
import com.insonix.klv.dao.SQLServerConnection;
import com.insonix.klv.threads.KlvRead;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

/**
 * The information panel where upd url is input and KLV data is printed
 *  Media PLayer Ref Link http://capricasoftware.co.uk/#/projects/vlcj
 *  
 * 
 * @author Nikhil Mahajan
 * 
 * @since Apr 22, 2018
 */
public class InfoPanel extends JPanel {

	private SpringLayout layout = new SpringLayout();

	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

	private JTextField udpStreamUrlInput;

	private UdpReader reader;

	// private JTextArea klvDatalText;
	private JEditorPane klvDatalText;

	public InfoPanel(EmbeddedMediaPlayerComponent mediaPlayerComponent) {
		super();

		setLayout(layout);
		setPreferredSize(new Dimension(500, 320));
		setBackground(Color.decode("#072336"));

		this.mediaPlayerComponent = mediaPlayerComponent;

		init();

	}

	
	/**
	 * Initializaing the form compnenets 
	 */
	public void init() {
		JLabel udpStreamUrlLbl = new JLabel("Enter Stream Url");
		udpStreamUrlLbl.setForeground(Color.decode("#A0ADB5"));
		layout.putConstraint(SpringLayout.WEST, udpStreamUrlLbl, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, udpStreamUrlLbl, 30, SpringLayout.NORTH, this);
		this.add(udpStreamUrlLbl);

		udpStreamUrlInput = new JTextField("udp://@127.0.0.1:2000", 28);
		// udpStreamUrlInput.setSize(180, 30);
		layout.putConstraint(SpringLayout.WEST, udpStreamUrlInput, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, udpStreamUrlInput, 30, SpringLayout.NORTH, udpStreamUrlLbl);
		this.add(udpStreamUrlInput);

		JLabel klvDatalLbl = new JLabel("Extracted Klv Data");
		klvDatalLbl.setForeground(Color.decode("#A0ADB5"));
		layout.putConstraint(SpringLayout.WEST, klvDatalLbl, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, klvDatalLbl, 30, SpringLayout.NORTH, udpStreamUrlInput);
		this.add(klvDatalLbl);

		// klvDatalText = new JTextArea(23, 27);
		klvDatalText = new JEditorPane("text/html", "<strong>KLV Data wil be here</strong>");

		// layout.putConstraint(SpringLayout.WEST, klvDatalText, 15, SpringLayout.WEST,
		// this);
		// layout.putConstraint(SpringLayout.NORTH, klvDatalText, 30,
		// SpringLayout.NORTH, klvDatalLbl);
		klvDatalText.setEditable(false);
		JScrollPane scroll = new JScrollPane(klvDatalText, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		layout.putConstraint(SpringLayout.WEST, scroll, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, scroll, 30, SpringLayout.NORTH, klvDatalLbl);
		DefaultCaret caret = (DefaultCaret) klvDatalText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scroll.setPreferredSize(new Dimension(350, 390));
		scroll.setMinimumSize(new Dimension(10, 10));
		this.add(scroll);
		// this.add(klvDatalText);

		JButton startBtn = new JButton("Start");
		startBtn.setPreferredSize(new Dimension(100, 25));
		startBtn.setForeground(Color.LIGHT_GRAY);
		startBtn.setBackground(Color.decode("#021422"));
		layout.putConstraint(SpringLayout.WEST, startBtn, 15, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, startBtn, 30, SpringLayout.SOUTH, scroll);
		this.add(startBtn);
		startBtn.addActionListener((e) -> {
			try {
				startBtnAction(e);
			} catch (InterruptedException | ExecutionException e1) {
				e1.printStackTrace();
			}
		});

		JButton stopBtn = new JButton("Stop");
		stopBtn.setPreferredSize(new Dimension(100, 25));
		stopBtn.setForeground(Color.LIGHT_GRAY);
		stopBtn.setBackground(Color.decode("#021422"));
		layout.putConstraint(SpringLayout.WEST, stopBtn, 15, SpringLayout.EAST, startBtn);
		layout.putConstraint(SpringLayout.NORTH, stopBtn, 30, SpringLayout.SOUTH, scroll);
		this.add(stopBtn);
		stopBtn.addActionListener((e) -> stopBtnAction(e));

		JButton resetBtn = new JButton("Reset");
		resetBtn.setPreferredSize(new Dimension(100, 25));
		resetBtn.setForeground(Color.LIGHT_GRAY);
		resetBtn.setBackground(Color.decode("#021422"));
		layout.putConstraint(SpringLayout.WEST, resetBtn, 15, SpringLayout.EAST, stopBtn);
		layout.putConstraint(SpringLayout.NORTH, resetBtn, 30, SpringLayout.SOUTH, scroll);
		this.add(resetBtn);
		resetBtn.addActionListener((e) -> resetBtnAction(e));

	}

	private void startBtnAction(ActionEvent e) throws InterruptedException, ExecutionException {
		// todo play video
		System.out.println("Action called");
		playVideo();

		// setKlvData();
	}

	private void stopBtnAction(ActionEvent e) {
		SQLServerConnection.closeConnection();
		// TODO: Terminate All Threads
		// TODO: Remove IConstants.KLV_FILE_PATH file
		mediaPlayerComponent.getMediaPlayer().stop();
		reader.stop();
	}

	private void resetBtnAction(ActionEvent e) {
		SQLServerConnection.closeConnection();
		// Stope video
		mediaPlayerComponent.getMediaPlayer().stop();
		// stop klv thread
		reader.stop();
		// empty klv textarea
		klvDatalText.setText("");

	}

	private void playVideo() {
		mediaPlayerComponent.getMediaPlayer().playMedia(getMrl());
		// start parse data thread
		initKlvRead();
	}

	public String getMrl() {
		return udpStreamUrlInput.getText();
	}

	/**
	 * @deprecated Not in USE. Use initKlvRead() instead
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void setKlvData() throws InterruptedException, ExecutionException {
		// ExecutorService executor = Executors.newFixedThreadPool(2);

		// try {
		//
		// HTMLEditorKit kit = (HTMLEditorKit)klvDatalText.getEditorKit();
		// HTMLDocument doc = (HTMLDocument)klvDatalText.getDocument();
		// try {
		// kit.insertHTML(doc, doc.getEndPosition().getOffset()-1, "<i>Quick Brow
		// fox</i>" + "\n" , 1,0,null );
		// } catch (BadLocationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		KlvRead kr = new KlvRead(this.klvDatalText);
		Thread t = new Thread(kr);
		t.start();

		// kr.run();
		// Future<?> krf = executor.submit(kr);
		// krf.get(10, TimeUnit.MINUTES);

		// if KwWrite is a sepaate application then pass udp url to it
		// KlvWrite kw = new KlvWrite(getMrl());
		// Future<?> kwf = executor.submit(kw);
		// kwf.get(2, TimeUnit.MINUTES);

		// Wait until all threads are finish
		// while (!executor.isTerminated()) {
		//
		// }
		System.out.println("\nFinished all threads");
		// executor.shutdown();
		// } catch (TimeoutException e) {
		// e.printStackTrace();
		// }

	}

	/**
	 * Initlaize extarcton of KLV from UDP ULR
	 */
	private void initKlvRead() {

		// KlvRead kr = new KlvRead(this.klvDatalText);

		// TODO: Check if the mrl is url or a statci video file
		// extract port from URL
		String port = getMrl().split(":")[2];
		System.out.println("port " + port);
		// pass port and jeditor pane componenet to udpReader for updating klv data
		this.reader = new UdpReader(this.klvDatalText, Integer.parseInt(port) + 1);
		// reader.start();

		Thread t = new Thread(reader);
		t.start();

	}

}
