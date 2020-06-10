package nl.han.ica.TheHeist;

import java.util.List;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.SpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Sound.Sound;
import processing.core.PApplet;
import processing.core.PGraphics;

public abstract class Auto extends AnimatedSpriteObject implements ICollidableWithGameObjects {
	
	protected HeistWorld world;
	protected int werkelijkeSnelheid = 0;
	protected int maxSnelheid = 0;
	protected int rotatiehoek = 0;
	private int animatedSpriteFrames;

	/**
	 * 
	 * @param world
	 *            De wereld waarin de auto getekend moet worden
	 * @param sprite
	 *            De sprite met de auto
	 * @param h
	 *            De hoogte van de auto
	 * @param b
	 *            De breedte van de auto
	 * @param s
	 *            Snelheid van de auto
	 * @param startLocatieY
	 *            StartLocatie de startlocatie van de Y axis
	 * @param animatedSpriteFrames
	 *            aantal frames in de animatedSprite
	 * @param resizeSprite
	 *            of de sprite geresized moet worden
	 * 
	 *            zorgt voor het aanmaken van een abstract autoObject, wannnee
	 */
	public Auto(HeistWorld world, Sprite sprite, int h, int b, int s, int startLocatieY, int animatedSpriteFrames,
			boolean resizeSprite) {
		super(sprite, animatedSpriteFrames);
		this.world = world;
		this.animatedSpriteFrames = animatedSpriteFrames;
		this.setWidth(b);
		this.setHeight(h);
		if (resizeSprite) {
			sprite.resize(b, h);
		} else {
			sprite.resize(b * 2, h);
		}

		this.maxSnelheid = s;
		this.werkelijkeSnelheid = s;
		if (werkelijkeSnelheid < 0) {
			rotatiehoek = 180;
		}
		world.addGameObject(this, -200, -200);

		setY(startLocatieY);

		// botsGeluid = new Sound(world,
		// "src/main/java/nl/han/ica/TheHeist/media/car impact.mp3");
	}

	/**
	 * zorgt ervoor dat auto's niet buiten het speelveld komen, en anders
	 * verwijderd zullen worden
	 */
	@Override
	public void update() {
		// zorg dat auto's binnen het scherm blijven
		if (x + width > world.getWidth()) {
			setX(world.getWidth() - width);
			setxSpeed(Math.abs(getxSpeed()));
		} else if (x < 0) {
			setX(0);

			setxSpeed(0 - getxSpeed());

		}

		if (getY() > (world.getHeight() + (world.getHeight() / 2)) || getY() < 0 - (world.getHeight() / 2)) {
			world.deleteGameObject(this);
		}
	}
	/**
	 * Laat de auto's reageren op botsingen die gemaakt zijn.
	 */
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject g : collidedGameObjects) {
			reageerBotsing(g);
			verminderPunten();
		}
	}

	/**
	 * 
	 * @param g
	 * 
	 *            zorgt voor het rijgedrag inn het geval van een botsing voor
	 *            alle type's auto
	 */
	public void reageerBotsing(GameObject g) {

		if (g instanceof Auto) {
			// Andere namen ter verduidelijking
			Auto dezeAuto = this;
			Auto andereAuto = (Auto) g;

			// autoAngle en te gebruiken snelheid bepalen
			float dezeAutoAngle = dezeAuto.getAngleFrom(andereAuto);
			int dezeSpeed = werkelijkeSnelheid;

			verminderPunten();

			if (this instanceof Vluchtauto) {

				// de vluchtauto heeft veel minder last van botsingen, en heeft
				// daarom andere code
				andereAuto.setDirection(dezeAutoAngle);
				andereAuto.setSpeed(dezeSpeed);
				if (werkelijkeSnelheid >= 3) {
					werkelijkeSnelheid--;
					andereAuto.werkelijkeSnelheid += 3;
				};
				world.currentMissie.verminderBuit();
				world.dashBoardText.setText("$ "+world.currentMissie.overgeblevenBuit);
				world.botsing.rewind();
				world.botsing.play();
			} else {
				// zorg dat de snelste auto de berekening doet en de botsing
				// regelt
				if (dezeAuto.werkelijkeSnelheid > andereAuto.werkelijkeSnelheid) {
					if (!(andereAuto instanceof Vluchtauto)) {
						// snelle auto gaat langzamer reizen door botsing, en
						// langzame juist sneller
						dezeAuto.werkelijkeSnelheid -= andereAuto.werkelijkeSnelheid / 2;
						andereAuto.werkelijkeSnelheid += dezeAuto.werkelijkeSnelheid / 2;
						andereAuto.setDirection(dezeAutoAngle);
						if (this instanceof PolitieAuto) {
							andereAuto.setSpeed(dezeSpeed / 5);
						} else {
							andereAuto.setSpeed(dezeSpeed / 3);
						}
					}
				}
				// andereAuto.werkelijkeSnelheid = dezeSpeed;

				if (this instanceof BurgerAuto) {
					((BurgerAuto) this).bigCollisionTimer = System.currentTimeMillis();
				}
			}
		}
	}

	/**
	 * In deze functie wordt gebruikt gemaakt van polymorfie.
	 */
	public abstract void verminderPunten();

	// CODE OM ERVOOR TE ZORGEN DT AUTOS TE DRAAIEN ZIJN
	public void draw(PGraphics g) {
		// wanneer Auto's meer dan een frame hebben wordt alleen de
		// normale(super) functie aangeroepen,
		// wanneer dat niet het geval is dan zal er gekeken worden of een auto
		// kan draaien.

		if (animatedSpriteFrames > 1) {
			super.draw(g);
		} else {

			g.pushMatrix();

			g.translate(getCenterX(), getCenterY());

			g.rotate(PApplet.radians(rotatiehoek));

			g.image(getImage(), -width / 2, -height / 2);

			g.popMatrix();
		}

	}
	
	
/**
 * Onderstaande code gebaseerd op Tips bij OOPD gebruik.
 */
	
	
	public static float getRotationInRadians(float rotationInDegrees) {

		float rotationInRadians = (float) (PApplet.radians(rotationInDegrees) % Math.PI);

		rotationInRadians = (float) (((rotationInRadians > Math.PI * 0.5 && rotationInRadians < Math.PI * 1)

				|| (rotationInRadians > Math.PI * 1.5 && rotationInRadians < Math.PI * 2)) ? Math.PI - rotationInRadians
						: rotationInRadians);

		return rotationInRadians;

	}

	@Override
	public float getWidth() {

		// met dank aan

		// http://stackoverflow.com/questions/10392658/calculate-the-bounding-boxs-x-y-height-and-width-of-a-rotated-element-via-jav

		float rotationInRadians = getRotationInRadians(rotatiehoek);

		return (float) (Math.sin(rotationInRadians) * height +

				Math.cos(rotationInRadians) * width);

	}

	@Override

	public float getHeight() {

		float rotationInRadians = getRotationInRadians(rotatiehoek);

		return (float) (Math.sin(rotationInRadians) * width + Math.cos(rotationInRadians) * height);

	}

	@Override

	public float getX() {

		return -(getWidth() / 2) + getCenterX();

	}

	@Override

	public float getY() {

		return -(getHeight() / 2) + getCenterY();

	}
}
