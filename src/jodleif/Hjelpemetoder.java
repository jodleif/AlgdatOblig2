package jodleif;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 04.02.2016.
 *
 * Hjelpemetoder for å dele opp greiner i flere mindre linjer.
 */
public class Hjelpemetoder
{
	public static ArrayList<ArrayList<double[]>> økMengdenPunkter(ArrayList<ArrayList<double[]>> tre, int splitnr)
	{
		ArrayList<ArrayList<double[]>> nyListe = new ArrayList<>();

		for(ArrayList<double[]> nivå : tre)
		{
			ArrayList<ArrayList<double[]>> deltNivå = økPunkter(nivå, splitnr);
			for (ArrayList<double[]> delteGreiner : deltNivå) {
				nyListe.add(delteGreiner);
			}
			if(splitnr>1) splitnr/=1.5;
		}

		return nyListe;
	}

	private static ArrayList<ArrayList<double[]>> økPunkter(ArrayList<double[]> punkter, int splitnr)
	{
		ArrayList<ArrayList<double[]>> nyListe = new ArrayList<>();
		double[] redusert = reduserLengde(punkter.get(0), splitnr);

		if(!sjekkLengde(redusert)) {
			nyListe.add(punkter);
			return nyListe;
		}

		ArrayList<double[]> førstePass = new ArrayList<>();

		for(double[] linje : punkter){
			førstePass.add(reduserLengde(linje,splitnr));
		}

		nyListe.add(førstePass);

		for(int i=1;i<splitnr;++i){
			ArrayList<double[]> linjeStykke = new ArrayList<>();
			for(double[] linje : førstePass)
			{
				linjeStykke.add(leggTilDelta(linje,i));
			}
			nyListe.add(linjeStykke);
		}
		return nyListe;
	}

	/**
	 * Reduserere lengden på en linje til en lengde som går opp i antallSplitt
	 * f.eks antallSplitt = 10, blir den nye lengden = gammelLengde/10
	 * @param linje linje man vil "forkorte"
	 * @param antallSplitt hvilket antall skal lengden gå opp i
	 * @return ny, forkortet linje (kun en)
	 */
	public static double[] reduserLengde(double[] linje, int antallSplitt)
	{
		double[] delta = finnDelta(linje);
		double nydeltax = (delta[0])/antallSplitt;
		double nydeltay = (delta[1])/antallSplitt;
		return new double[] {linje[0],linje[1],linje[0]+nydeltax,linje[1]+nydeltay};
	}


	public static double[] leggTilDelta(double[] linje, double linjestk)
	{
		double[] delta = finnDelta(linje);
		return new double[]{linje[2]+delta[0]*(linjestk-1),linje[3]+delta[1]*(linjestk-1),
			linje[2]+delta[0]*linjestk, linje[3]+delta[1]*linjestk};
	}

	private static double[] finnDelta(double[] linje)
	{
		return new double[] {linje[2]-linje[0],linje[3]-linje[1]};
	}

	public static boolean sjekkLengde(double[] linje)
	{
		int len = (int)finnLengde(linje);
		return len > 1;
	}

	public static double finnLengde(double[] linje)
	{
		return Math.sqrt(Math.pow(linje[2]-linje[0],2)+Math.pow(linje[3]-linje[1],2));
	}
}
