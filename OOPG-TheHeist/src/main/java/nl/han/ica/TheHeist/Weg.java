package nl.han.ica.TheHeist;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.SpriteObject;

public class Weg extends SpriteObject {

	HeistWorld world;
	private boolean magWegPatsen = true;
	static String wegAfbeeldingPath = "nl/han/ica/TheHeist/media/road fixed.png";

	/**
	 * @param world de wereld waarin de weg gezet moet worden.
	 * @param wegAfbeeldingPath De lokatie waar de afbeelding staat op het filesystem.
	 */
	public Weg(HeistWorld world, String wegAfbeeldingPath) {
		super(new Sprite(wegAfbeeldingPath));
		this.world = world;
		setZ(-10000);//
	}

	/**
	 * Kijkt of de weg al weg moet gaan en of er een nieuwe weg gespawned mee kan worden.
	 */
	@Override
	public void update() {
		world.g.rotate(0);
		setySpeed(world.wegSnelheid);
		if (getY() >= getHeight()) {
			world.deleteGameObject(this);
			world.currentMissie.verhoogAfstand();
		}
		if (getY() <= 0 && getY() >= 0 - getySpeed() && magWegPatsen) {
			world.addGameObject(new Weg(world, wegAfbeeldingPath), 0, 0 - this.getHeight());
			magWegPatsen = false;
		}
	}

}
