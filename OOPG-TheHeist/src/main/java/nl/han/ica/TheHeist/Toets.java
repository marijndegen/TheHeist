package nl.han.ica.TheHeist;

public class Toets {
	private char toets;
	boolean isIngedrukt;
	
	/**
	 * @param toets waar naar geluisterd moet kunnen worden.
	 */
	public Toets(char toets) {
		this.toets = toets;
	}
	
	/**
	 * Pak de toets (char) die in deze klas zit.
	 */
	public char getToets(){
		return toets;
	}
	
	/**
	 * @return geeft aan of de toets is ingedrukt
	 */
	public boolean isIngedrukt() {
		return isIngedrukt;
	}
	
	/**
	 * Laat de gebruiker de knop indrukken.
	 */
	public void setIngedrukt(boolean isIngedrukt) {
		this.isIngedrukt = isIngedrukt;
	}

}
