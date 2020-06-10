package nl.han.ica.TheHeist;

import java.util.ArrayList;

import nl.han.ica.OOPDProcessingEngineHAN.Dashboard.Dashboard;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.View.View;
import processing.core.PImage;

public class Scherm {
public  Dashboard dashboard;

	private HeistWorld world;
	private Kleur achtergrond;
	private View view;
	private int geklikteKnop;
	private float width, height;
	private Sprite sprite;
	private Sprite afbeelding;
	private String text;
	
	//De kleuren die gebruikt worden tijdens het maken van het dashboard en de dashboard items
	final Kleur oranje = new Kleur(200, 100, 0);
	final Kleur rood = new Kleur(255, 0, 0);
	final Kleur groen = new Kleur(0, 200, 50);
	final Kleur wit = new Kleur(255, 255, 255);

	//Dimensies van de knoppen.
	int knopBreedte = 200;
	int knopHoogte = 35;

	/**
	 * 
	 * @param world de wereld waarin het scherm wordt geplaatst
	 * @param width de volledige breedte van het scherm
	 * @param height de volledige hoogte van het scherm
	 * @param afbeelding de afbeelding van het te genereren scherm.
	 */
	Scherm(HeistWorld world, float width, float height, Sprite afbeelding) {
		this.world  = world;
		this.width  = width;
		this.height = height;
		this.afbeelding = afbeelding;
	}	

	/*
	 * Maakt de view en het scherm aan.
	 */
	public void maakStartScherm(Missie[] missies) {
		dashboard = new Dashboard(0, 0, width, height);
		world.addDashboard(dashboard);
		world.addGameObject(dashboard);
		view = new View((int) width, (int) height);
		if (afbeelding == null) {
			view.setBackground(achtergrond.r, achtergrond.g, achtergrond.b);
		} else {
			view.setBackground(afbeelding.getPImage()
					);
		}
		world.setView(view);
		int offset = 160;
		for (int i = 0; i < missies.length; i++) {
			voegKnopToe(new MissieKnop(missies[i].Titel, 0, knopHoogte * i + offset,
					knopBreedte, knopHoogte, wit, oranje, missies[i], world));
					offset += 5;
		}

	}
	
	/*
	 * Voeg een knop toe aan het scherm
	 */
	public void voegKnopToe(MissieKnop textknop) {
		dashboard.addGameObject(textknop);
		world.addGameObject(textknop);
	}

}
