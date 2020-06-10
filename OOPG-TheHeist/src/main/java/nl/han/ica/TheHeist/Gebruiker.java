package nl.han.ica.TheHeist;

import java.util.ArrayList;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;

public class Gebruiker {
	private HeistWorld world;
	public Vluchtauto vluchtauto;
	private int totaalGestolenGeld = 0;

	// TODO dit fixen
	// private ArrayList<Missie> = new ArrayList<>();

	/**
	 * 
	 * @param world de wereld waarin de objecten van gebruikerm moeten worden aangemaakt.
	 */
	Gebruiker(HeistWorld world) {
		this.world = world;
	}
	
	/**
	 * Maakt de vluchtauto aan.
	 */
	public void maakVluchtAuto(){
		vluchtauto = new Vluchtauto(world, new Sprite("nl/han/ica/TheHeist/media/HeistCar.png"), 46, 100);
	}
}
