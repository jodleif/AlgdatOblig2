package jodleif.Render;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import jodleif.Main;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jo Øivind Gjernes on 05.02.2016.
 */
public class Intro
{

	private static final String target = "Jo Øivind Gjernes og Magnus Poppe Wang";

	public Canvas tegnBokstaver()
	{
		Canvas nyttCanvas = Plotter.lagNyttTegneområde();
		nyttCanvas.getGraphicsContext2D().setFont(new Font(40));
		ArrayList<BokstavTimer> taskListe = new ArrayList<>();
		StringBuilder strBld = new StringBuilder();

		for(int i=0;i<target.length();++i){
			byggRandomString(strBld,i);
			taskListe.add(new BokstavTimer(strBld.toString(), nyttCanvas.getGraphicsContext2D()));
			if(i!=target.length()-1) strBld.setLength(0);
		}

		for(int i=0;i<target.length();++i){
			strBld.setCharAt(i, target.charAt(i));
			taskListe.add(new BokstavTimer(strBld.toString(), nyttCanvas.getGraphicsContext2D()));
		}
		utførTimerList(taskListe, 100);
		return nyttCanvas;
	}

	private void byggRandomString(StringBuilder strBld, int lengde)
	{
		for(int j=0;j<=lengde;++j){
			//if(target.charAt(j)!=' ') {
				char random = (char) (Math.random() * (double) 'Z');
				while ((int) random < (int) 'A' || (int) random > (int) 'z') {
					random = (char) (Math.random() * (double) 'z');
				}
				strBld.append(random);
			//} else {
				//strBld.append(' ');
			//}
		}
	}

	private void utførTimerList(ArrayList<BokstavTimer> t, long forsinkelse)
	{
		Timer timer=new Timer();
		for(int i=0;i<t.size();++i){
			timer.schedule(t.get(i),i*forsinkelse);
		}
	}


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
