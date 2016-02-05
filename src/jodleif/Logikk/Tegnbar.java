package jodleif.Logikk;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 04.02.2016.
 *
 */
public interface Tegnbar
{
	ArrayList<ArrayList<double[]>> getPunkter();

	int getHøyde();

	/**
	 * Hvilket type objekt som skal tegnes
	 * @return basisobjekt enum
	 */
	BasisObjekt getBasisObjekt();
}
