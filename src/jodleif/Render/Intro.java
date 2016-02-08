package jodleif.Render;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import jodleif.Main;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jo Øivind Gjernes og Magnus Poppe Wang on 05.02.2016.
 *
 * Tegne "Introen" på skjermen.
 */
public class Intro
{

	private static final String målTekst = "Jo Øivind Gjernes og Magnus Poppe Wang";

	public Canvas tegnBokstaver()
	{
		Canvas nyttCanvas = Plotter.lagNyttTegneområde();
		nyttCanvas.getGraphicsContext2D().setFont(new Font(40));

		StringBuilder buffer = new StringBuilder();

		ArrayList<BokstavTimer> oppgaveListe =
			ekspanderStreng(buffer,nyttCanvas.getGraphicsContext2D());

		oppgaveListe.addAll(erstattMedBokstaver(buffer,nyttCanvas.getGraphicsContext2D()));

		utførTimerList(oppgaveListe, 125);
		return nyttCanvas;
	}

	/**
	 * Oppretter oppgaver som gradvis bygger en streng med tilfeldige bokstaver
	 * @param kontekst grafisk kontekst oppgaven skal tegnes på
	 * @return liste over oppgaver
	 */
	private ArrayList<BokstavTimer> ekspanderStreng(StringBuilder buffer, GraphicsContext kontekst)
	{
		ArrayList<BokstavTimer> oppgaveListe = new ArrayList<>() ;
		for(int i = 0; i< målTekst.length(); ++i){
			byggRandomString(buffer,i);
			oppgaveListe.add(new BokstavTimer(buffer.toString(), kontekst));
			if(i!= målTekst.length()-1) buffer.setLength(0);
		}
		return oppgaveListe;
	}

	/**
	 * Oppretter oppgaver som gradvis erstatter tilfeldige bokstaver med "ønskede" bokstaver
 	 * @param kontekst grafikk-kontekst for canvaset oppgaven skal utføres på
	 * @return Liste med oppgaver
	 */
	private ArrayList<BokstavTimer> erstattMedBokstaver(StringBuilder buffer, GraphicsContext kontekst)
	{
		ArrayList<BokstavTimer> oppgaveListe = new ArrayList<>() ;
		for(int i = 0; i< målTekst.length(); ++i){
			buffer.setCharAt(i, målTekst.charAt(i));
			oppgaveListe.add(new BokstavTimer(buffer.toString(), kontekst));
		}
		return oppgaveListe;
	}

	/**
	 * Metode for å generere en string med "tilfeldige" bokstaver
	 * @param strBld StringBuilder som benyttes for å bygge Stringen i
	 * @param lengde Ønsket lengde på tilfeldig streng.
	 */
	private void byggRandomString(StringBuilder strBld, int lengde)
	{
		for(int j=0;j<=lengde;++j){
				char random = (char) (Math.random() * (double) 'Z');
				while ((int) random < (int) 'A' || (int) random > (int) 'z') {
					random = (char) (Math.random() * (double) 'z');
				}
				strBld.append(random);
		}
	}

	/**
	 * Utfør en liste med TimerTasks på en Timer. (tidsforsinkede oppgaver)
	 * @param t liste med BokstavTimer oppgaver
	 * @param forsinkelse Forsinkelse i millisekunder mellom hver "oppgave"
	 */
	private void utførTimerList(ArrayList<BokstavTimer> t, long forsinkelse)
	{
		Timer timer=new Timer();
		for(int i=0;i<t.size();++i){
			timer.schedule(t.get(i),i*forsinkelse);
		}
	}

	/**
	 * Timer-task.
	 *
	 * En oppgave som kan utføres av en timer- med en spesifisert forsinkning
	 */
	class BokstavTimer extends TimerTask
	{
		private String åSkrive;
		private GraphicsContext kontekst;
		private double x,y;

		public BokstavTimer(String åSkrive, GraphicsContext kontekst)
		{
			this(åSkrive, kontekst, 25,Main.HEIGHT/2+20);
		}

		public BokstavTimer(String åSkrive, GraphicsContext kontekst, double x, double y)
		{
			this.åSkrive = åSkrive;
			this.kontekst = kontekst;
			this.x = x;
			this.y = y;

		}

		@Override
		public void run()
		{
			kontekst.clearRect(0,0, Main.WIDTH,Main.HEIGHT);
			kontekst.fillText(åSkrive, x,y);

		}
	}
}
