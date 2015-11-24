import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Miniplayer {

	static JFrame ramka = new JFrame("Moje pierwsze muzyczne wideo");
	static MyPanelGraf panel;
	
	public static void main(String[] args) {
		Miniplayer mini = new Miniplayer();
		mini.toWork();
	}
	
	public void konfigurujGUI() {
		
		ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new MyPanelGraf();
		ramka.setContentPane(panel);
		ramka.setBounds(30,30,300,300);
		ramka.setVisible(true);
		
	} // koniec metody
	
	public void toWork() {
		   konfigurujGUI();
		
		   try {
			
		
			   Sequencer sekwenser = MidiSystem.getSequencer();
			   sekwenser.open();
			   sekwenser.addControllerEventListener(panel, new int[] {127});
			   Sequence sekw = new Sequence(Sequence.PPQ, 4);
			   Track sciezka = sekw.createTrack();
		
			   int r = 0;
			   for (int i = 5; i < 60; i+=4) {
				   r = (int) ((Math.random() * 50) + 1);
				   sciezka.add(tworzZdarzenie(144,1,r,100,i));
				   sciezka.add(tworzZdarzenie(176,1,127,0,i));
				   sciezka.add(tworzZdarzenie(128,1,r,100,i + 2));
			   } // koniec pętli
	 //*******************************************************************************************		   
			   sekwenser.setSequence(sekw);
			   sekwenser.setTempoInBPM(220);
			   sekwenser.start();
		   } catch (Exception ex) { ex.printStackTrace(); }
		} // koniec metody
	//*******************************************************************************************
			public static MidiEvent tworzZdarzenie(int plc, int kanal, int jeden, int dwa, int takt) {
				
				MidiEvent zdarzenie = null;
				try {
					ShortMessage a = new ShortMessage();
					a.setMessage(plc, kanal, jeden, dwa);
					zdarzenie = new MidiEvent(a, takt);
				} catch(Exception e) { }
				return zdarzenie;
			} // koniec metody
	//*******************************************************************************************		
		class MyPanelGraf extends JPanel implements ControllerEventListener {
			
			boolean komunikat = false;
			
			public void controlChange(ShortMessage zdarzenie) {
				komunikat = true;
				repaint();
			}
			
			public void paintComponent(Graphics g) {
				
				if (komunikat) {
					Graphics2D g2 = (Graphics2D) g;
					int c = (int) (Math.random() * 250);
					int z = (int) (Math.random() * 250);
					int n = (int) (Math.random() * 250);
					g.setColor(new Color(c,z,n));
					int wys = (int) ((Math.random() * 120) + 10);
					int szer = (int) ((Math.random() * 120) + 10);
					int x = (int) ((Math.random() * 40) + 10);
					int y = (int) ((Math.random() * 40) + 10);
					g.fillRect(x,y,wys,szer);
					komunikat = false;
			} // koniec if
		} // koniec metody
	} // koniec klasy wewnÚtrznej
} // koniec klasy

