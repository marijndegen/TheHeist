package nl.han.ica.TheHeist;

import java.util.List;
import java.util.Random;
import java.util.Timer;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Sound.Sound;

public class BurgerAuto extends Auto implements ICollidableWithGameObjects {
	HeistWorld world;
	Random rand = new Random();
	Sound toeter;

	protected int[] rijbanen;
	protected int rijbaan;
	protected long rijgedragTimer = System.currentTimeMillis();
	protected long bigCollisionTimer = System.currentTimeMillis();
	protected int gezondheid = 100;
	boolean verandertRijstrook = false;

	/**
	 * 
	 * @param world De wereld waarin de auto geplaatst wordt
	 * @param sprite De afbeelding van de auto
	 * @param h Hoogte van de auto
	 * @param b Breedte van de auto
	 * @param rijbanen De rijbanen waar de auto op mag rijden
	 * @param rijbaan Startrijbaan
	 * @param gezondheid een niet toegepast variable, deze zou van toepassing zijn als de auto's "dood" konden gaan
	 * @param snelheid de snelheid van de auto op de weg.
	 * @param startLocatieY De locatie waar de auto spawnt op de Y-axis
	 * 
	 *            Doet een gewone auto aanmaken met alle gewenste variabelen
	 */
	public BurgerAuto(HeistWorld world, Sprite sprite, int h, int b, int[] rijbanen, int rijbaan, int gezondheid,
			int snelheid, int startLocatieY) {
		super(world, sprite, h, b, snelheid, startLocatieY, 1, true);
		this.world = world;
		this.rijbanen = rijbanen;
		this.rijbaan = rijbaan;
		this.gezondheid = gezondheid;
		// toeter = new Sound(world,
		// "src/main/java/nl/han/ica/TheHeist/media/car horn.mp3");
		setX(rijbanen[rijbaan]);

	}

	/**
	 * doet het rijgedrag realtime aanpassen
	 */
	@Override
	public void update() {
		super.update();
		setySpeed(world.wegSnelheid - werkelijkeSnelheid);
		aanpassenRijgedrag();
	}

	/**
	 * past het rijgedrag aan zodat een auto altijd (redelijk) kan rijden;
	 */
	void aanpassenRijgedrag() {
		// iedere 500ms wordt de auto in de richting van de juiste rijbaan
		// gestuurd
		if (System.currentTimeMillis() - rijgedragTimer > 500) {

			if (werkelijkeSnelheid > maxSnelheid) {
				werkelijkeSnelheid--;
			} else if (werkelijkeSnelheid < maxSnelheid) {
				werkelijkeSnelheid++;
			}

			if (getxSpeed() > 0) {
				setxSpeed(getxSpeed() - 1);
			} else if (getxSpeed() < 0) {
				setxSpeed(getxSpeed() + 1);
			}
			rijgedragTimer = System.currentTimeMillis();

			// naar goede rijbaan sturen
			if (x == rijbanen[rijbaan] && System.currentTimeMillis() - bigCollisionTimer > 1000) {
				setxSpeed(0);
				verandertRijstrook = false;

				if (x < rijbanen[rijbaan]) {
					setxSpeed(1);
				} else if (x > rijbanen[rijbaan]) {
					setxSpeed(-1);

				}
			}
		}

	}

	/**
	 * kan ingedien aangeroepen de rijbaan verandren naar een andere rijbaan in
	 * de array van rijbanen
	 */
	void switchRijbaan() {
		rijbaan = rand.nextInt(rijbanen.length - 1) + 0;
	}

	/**
	 * Verminder de levenspunten van de auto.
	 */
	public void verminderPunten() {
		gezondheid -= 10;
	}

}