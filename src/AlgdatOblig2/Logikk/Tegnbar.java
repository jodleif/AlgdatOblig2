package AlgdatOblig2.Logikk;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes og Magnus Poppe Wang on 04.02.2016.
 *
 */
public interface Tegnbar
{
	/**
	 * En liste med lister av "BasisObjekter"
	 * linje:
	 * double[] {x0,y0,x1,y1}
	 *
	 * triangel:
	 * double[] {
	 * 	x0,y0,
	 * 	x1,y1,
	 * 	x2,y2
	 * 	}
	 * @return Liste med double[] definert som i beskrivelsen over
	 */
	ArrayList<ArrayList<double[]>> getPunkter();

	/**
	 * antall "nivåer" i det Tegnbare objektet, der det gir mening
	 * @return getPunkter().size() (antall lister med punkter, der en liste representerer ett nivå!)
	 */
	int getHøyde();

	/**
	 * Hvilket type objekt som skal tegnes
	 * @return basisobjekt enum
	 */
	BasisObjekt getBasisObjekt();
}
