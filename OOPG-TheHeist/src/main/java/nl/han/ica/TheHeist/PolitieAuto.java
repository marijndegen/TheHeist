package nl.han.ica.TheHeist;

import java.util.List;
import java.util.Random;
import java.util.Timer;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Sound.Sound;

public class PolitieAuto extends Auto implements ICollidableWithGameObjects {
	private HeistWorld world;
	private int[] rijbanen;
	private int rijbaan;
	private int agressieviteit;
	private int gezondheid;
	private long rijgedragTimer = System.currentTimeMillis();
	private Random rand = new Random();

	/**
	 * 
	 * @param world
	 *            De wereld waarin het spel zich afspeelt
	 * @param sprite
	 *            De afbeelding
	 * @param h
	 *            Hoogte van de auto
	 * @param b
	 *            Breedte van de auto
	 * @param rijbanen
	 *            de rijbanen
	 * @param rijbaan
	 *            De rijbaan waarop gereden wordt
	 * @param gezondheid
	 *            De gezondheid van de auto
	 * @param snelheid
	 *            De snelheid van de auto
	 * @param agressiviteit
	 *            De agressieviteiit van de politie
	 * @param startLocatieY
	 *            De Startlocatie op de Y-axis
	 * @param animatedSpriteFrames
	 *            het aantal frames die een auto heeft
	 * 
	 *            Start een nieuwe PolitieAuto met alle gewenste variabelen voor
	 *            rijgedrag
	 */
	public PolitieAuto(HeistWorld world, Sprite sprite, int h, int b, int[] rijbanen, int rijbaan, int gezondheid,
			int snelheid, int agressiviteit, int startLocatieY, int animatedSpriteFrames) {
		super(world, sprite, h, b, snelheid, startLocatieY, animatedSpriteFrames, false);
		sprite.resize(b * 2, h);
		this.agressieviteit = agressiviteit;
		this.world = world;
		this.rijbanen = rijbanen;
		this.rijbaan = rijbaan;
		this.gezondheid = gezondheid;

		System.out.println("NieuweGemaakt" + this);
		setX(rijbanen[rijbaan]);
		this.setCurrentFrameIndex(0);
	}

	/**
	 * doet versnellen/ vertragen obv variabelen, en verwijderd het object
	 * wanneer deze zich te ver buiten het speelveld bevind
	 */
	@Override
	public void update() {
		super.update();
		setySpeed(world.wegSnelheid - werkelijkeSnelheid);
		if (maxSnelheid > werkelijkeSnelheid) {
			werkelijkeSnelheid++;
		}

		aanpassenRijgedrag();
	}

	/**
	 * Zorgt voor het zelfrijdende gedrag van een politieAuto
	 */
	void aanpassenRijgedrag() {
		if (System.currentTimeMillis() - rijgedragTimer > 200) {
			volgVluchtAuto();
			if (this.getCurrentFrameIndex() == 1) {
				this.setCurrentFrameIndex(0);
			} else {
				this.setCurrentFrameIndex(1);
			}

			// snelheid aanpassen op maxSnelheid
			if (werkelijkeSnelheid > maxSnelheid) {
				werkelijkeSnelheid--;
			} else if (werkelijkeSnelheid < maxSnelheid) {
				werkelijkeSnelheid++;
			}

			rijgedragTimer = System.currentTimeMillis();
		}
	}

	/**
	 * 
	 * volgt de vluchtAuto
	 */
	public void volgVluchtAuto() {
		// max snelheid aanpassen obv vluchtAuto
		if (y < world.gebruiker.vluchtauto.getY() - 150) {
			maxSnelheid = world.gebruiker.vluchtauto.werkelijkeSnelheid - agressieviteit;
		} else if (y > world.gebruiker.vluchtauto.getY() + 150) {
			maxSnelheid = world.gebruiker.vluchtauto.werkelijkeSnelheid + agressieviteit;

		}

		// wanneer voldoende voor auto auto volgen
		if ((y + height) < (world.gebruiker.vluchtauto.getY() - 100)) {
			if (x > world.gebruiker.vluchtauto.getX()) {
				System.out.println(this.getCurrentFrameIndex());

				// politieAuto rechts van vluchtAuto
				setxSpeed((0 - (agressieviteit / 2)));

			} else {
				setxSpeed(agressieviteit / 2);
			}
		} else {
			setxSpeed(0);
		}
	}

	/**
	 * verminderd de gezondheid van een auto
	 */
	public void verminderPunten() {
		gezondheid -= 10;
	}

}
