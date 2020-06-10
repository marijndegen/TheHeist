package nl.han.ica.TheHeist;

import java.util.Calendar;
import java.util.List;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;

public class Vluchtauto extends Auto implements ICollidableWithGameObjects {
	private HeistWorld world;
	private Toets[] toetsen = { new Toets('w'), new Toets('s'), new Toets('a'), new Toets('d') };
	private float minHeight;
	private float maxHeight;
	private float currentHeight;

	/**
	 * 
	 * @param world de wereld waarin de auto gespawned moet worden.
	 * @param sprite het afbeeldingobject van de auto
	 * @param b
	 *            - breedte
	 * @param h
	 *            - hoogte
	 * 
	 *            intitialiseert de vluchtauto
	 */
	public Vluchtauto(HeistWorld world, Sprite sprite, int b, int h) {
		super(world, sprite, h, b, 0, world.getHeight() - 100, 1, true);

		minHeight = world.getHeight() * 0.65F;
		maxHeight = world.getHeight() * 0.80F;
		currentHeight = maxHeight;
		this.world = world;
		this.setX((world.getWidth() / 2) - (getWidth() / 2));
		// this.setY(world.getHeight() - 100);
		this.setZ(100);
	}

	/**
	 * update toetsen wanneer ingedrukt
	 */
	@Override
	public void keyPressed(int keyCode, char key) {
		for (int i = 0; i < toetsen.length; i++) {
			if (key == toetsen[i].getToets()) {
				toetsen[i].setIngedrukt(true);
			}
		}
	}

	/**
	 * update toetsen wanneer losgelaten
	 */
	@Override
	public void keyReleased(int keyCode, char key) {
		for (int i = 0; i < toetsen.length; i++) {
			if (key == toetsen[i].getToets()) {
				toetsen[i].setIngedrukt(false);
			}
		}
	}

	/**
	 * bekijkt of er toetsen zijn ingedrukt en doet aan de hand daarvan
	 * versnellen/ vertragen
	 */
	@Override
	public void update() {
		super.update();
		final int speed = 5;
		boolean acceleratie = false;
		boolean sturen = false;
		long time = System.currentTimeMillis();
		for (Toets toets : toetsen) {
			if (toets.isIngedrukt()) {
				switch (toets.getToets()) {
				case 'w':
					acceleratie = true;
					if (currentHeight > minHeight) {
						currentHeight -= speed;
					}

					if (werkelijkeSnelheid < 10 && time % 18 == 0) {
						werkelijkeSnelheid++;
					}
					break;
				case 's':
					if (currentHeight < maxHeight) {
						currentHeight += speed;
					}
					break;
				case 'a':
					setxSpeed(-3);
					sturen = true;
					break;
				case 'd':
					setxSpeed(+3);
					sturen = true;

					break;
				}
			}
		}
		if (!acceleratie && werkelijkeSnelheid != 0 && time % 18 == 0) {
			werkelijkeSnelheid--;
		}
		if (!sturen) {
			setxSpeed(0);
		}

		world.wegSnelheid = werkelijkeSnelheid;
		y = currentHeight;
	}

/**
 * Verminderd het gestolen bedrag
 */
	public void verminderPunten() {

	}

}
