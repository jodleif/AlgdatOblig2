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
		}

		return nyListe;
	}

	public static ArrayList<ArrayList<double[]>> økPunkter(ArrayList<double[]> punkter, int splitnr)
	{
		ArrayList<ArrayList<double[]>> nyListe = new ArrayList<>();
		//double[] start = punkter.get(0);
		//double[] redusert = reduserLengde(start, splitnr);

		/*if(!sjekkLengde(redusert)) {
			nyListe.add(punkter);
			return nyListe;
		}*/

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

	public static boolean sjekkLengde(double[] linjer)
	{
		int len = (int)Math.sqrt(Math.pow(linjer[2]-linjer[0],2)+Math.pow(linjer[3]-linjer[1],2));
		return len > 1;
	}
}
