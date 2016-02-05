package jodleif.Logikk;

import jodleif.Main;
import jodleif.Felles.VinkelCache;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 03.02.2016.
 *
 * Treet.
 */
public class Tre implements Tegnbar
{
	private ArrayList<ArrayList<double[]>> punkterITre;
	private static final VinkelCache cache = new VinkelCache();

	private double VEKSTFAKTOR = 0.7;
	private double VINKEL_VEKST ;
	private double INITIELL_LENGDE;
	private int nivåer;

	public Tre(int nivåer, double initiell_lengde, double vinkel_vekst)
	{
		this.nivåer = nivåer;
		INITIELL_LENGDE = initiell_lengde;
		VINKEL_VEKST = vinkel_vekst;
		genererPunkter();
	}

	private void genererPunkter()
	{
		punkterITre = new ArrayList<>();
		for(int i=0;i<nivåer;++i) punkterITre.add(new ArrayList<>());

		tegnGrein((Main.WIDTH)/2.0,Main.HEIGHT,INITIELL_LENGDE,0,0);
	}

	private void tegnGrein(double x0, double y0, double length, double vinkel, int nivå)
	{
		if(nivå >= nivåer){
			return;
		}
		// Minus da treet vokser i negativ y-retning
		double nyX = x0-length*cache.getSinVinkel(vinkel);
		double nyY = y0-length*cache.getCosVinkel(vinkel);

		punkterITre.get(nivå).add(new double[] {x0,y0,nyX,nyY});

		tegnGrein(nyX,nyY, length*VEKSTFAKTOR, vinkel+VINKEL_VEKST, nivå+1);
		tegnGrein(nyX,nyY, length*VEKSTFAKTOR, vinkel-VINKEL_VEKST, nivå+1);
	}

	public BasisObjekt getBasisObjekt()
	{
		return BasisObjekt.Linje;
	}


	@Override
	public ArrayList<ArrayList<double[]>> getPunkter()
	{
		return punkterITre;
	}

	@Override
	public int getHøyde()
	{
		return punkterITre.size();
	}
}
