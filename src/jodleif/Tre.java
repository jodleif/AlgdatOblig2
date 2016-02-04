package jodleif;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jo Øivind Gjernes on 03.02.2016.
 *
 * Treet.
 */
public class Tre
{
	private ArrayList<Stack<double[]>> greiner;
	private static final VinkelCache cache = new VinkelCache();

	private double VEKSTFAKTOR = 0.7;
	private double VINKEL_VEKST ;
	private double INITIELL_LENGDE;
	private int nivåer;

	public Tre()
	{
		nivåer = 10;
		VINKEL_VEKST = 0.33;
		INITIELL_LENGDE = 150;
	}
	public Tre(int nivåer, double initiell_lengde, double vinkel_vekst)
	{
		this.nivåer = nivåer;
		INITIELL_LENGDE = initiell_lengde;
		VINKEL_VEKST = vinkel_vekst;
	}

	private void tegnTre()
	{
		greiner = new ArrayList<>();
		for(int i=0;i<nivåer;++i) greiner.add(new Stack<>());

		tegnGrein((Main.WIDTH-100)/2.0,Main.HEIGHT-100,INITIELL_LENGDE,0,nivåer);
	}
	public void oppdater()
	{
		tegnTre();
	}

	private void tegnGrein(double x0, double y0, double length, double vinkel, int nivå)
	{
		if(nivå <= 0){
			return;
		}
		// Minus da treet vokser i negativ y-retning
		double nyX = x0-length*cache.getSinVinkel(vinkel);
		double nyY = y0-length*cache.getCosVinkel(vinkel);

		greiner.get(nivå-1).add(new double[] {x0,y0,nyX,nyY});

		tegnGrein(nyX,nyY, length*VEKSTFAKTOR, vinkel+VINKEL_VEKST, nivå-1);
		tegnGrein(nyX,nyY, length*VEKSTFAKTOR, vinkel-VINKEL_VEKST, nivå-1);
	}

	public ArrayList<Stack<double[]>> getGreiner()
	{
		return greiner;
	}

	public static void tegnNivå(Stack<double[]> punkter, GraphicsContext grafikk, int nivå)
	{
		if(nivå<2)
			grafikk.setStroke(Color.GREEN);
		else if (nivå<3)
			grafikk.setStroke(Color.RED);
		else
			grafikk.setStroke(Color.BLACK);

		while(!punkter.isEmpty()){
			double linje[] = punkter.pop();
			grafikk.strokeLine(linje[0],linje[1],linje[2],linje[3]);
		}
	}

	public void tegn(Canvas canvas)
	{
		tegnTre();
		Timer timer = new Timer();
		int i = nivåer-1;
		for(Stack<double[]> punkter : greiner){
			timer.schedule(new TimerTaskJodl(nivåer-i-1, punkter, canvas.getGraphicsContext2D())
			, 1000*i);
			--i;
		}
	}

	public void tegnNivå(GraphicsContext context, int nivå)
	{
		if((nivå-1)>greiner.size()||nivå<0) return;
		Stack<double[]> nivået = greiner.get(nivå);
		int size = nivået.size();
		for(int i=0;i<size;++i){
			double[] linje = nivået.pop();
			context.strokeLine(linje[0],linje[1],linje[2],linje[3]);
		}
	}

	private static void pause()
	{
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
	}

	class TimerTaskJodl extends TimerTask
	{
		protected int nivå;
		protected Stack<double[]> punkter;
		protected GraphicsContext cont;
		public TimerTaskJodl(int nivå, Stack<double[]> punkter, GraphicsContext cont)
		{
			this.nivå = nivå;
			this.punkter = punkter;
			this.cont = cont;
		}
		@Override
		public void run()
		{
			tegnNivå(punkter, cont, nivå);
		}
	}
}
