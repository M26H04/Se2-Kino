package de.uni_hamburg.informatik.swt.se2.kino.ui.beobachter;

/**
 * Interface für Beobachter
 */
@FunctionalInterface
public interface Beobachter 
{
	/**
	 * Reagiert auf Änderungen in einem der Controller
	 * @param quelle Quelle wo die Änderung stattgefunden hat
	 */
	public void reagiereAufAenderung(Beobachtbar quelle);
}
