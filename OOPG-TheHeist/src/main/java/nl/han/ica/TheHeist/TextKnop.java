package nl.han.ica.TheHeist;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import processing.core.PGraphics;

public abstract class TextKnop extends GameObject {

	HeistWorld world;
	private String text;

	private Kleur knopKleur;
	private Kleur textKleur;
	private int textGroote = 25;

	/**
	 * @param text de text op de missieknop
	 * @param x de x coordienaat van de knop
	 * @param y de y coordienaat van de knop
	 * @param width de breedte van de knop
	 * @param height de hooogte van de knop
	 * @param knopKleur de kleur an de knop
	 * @param textKleur de kleur van de text
	 * @param world de wereld waarin de missieknoppen getekend worden.
	 */
	public TextKnop(String text, float x, float y, float width, float height, Kleur knopKleur, Kleur textKleur,
			HeistWorld world) {
		this.text = text;
		this.width = width;
		this.height = height;
		this.knopKleur = knopKleur;
		this.textKleur = textKleur;
		this.setX(x);
		this.setY(y);
		this.world = world;
	}

	@Override
	public void update() {

	}

	/**
	 * Teken de knop
	 */
	@Override
    public void draw(PGraphics g) {
        g.textAlign(g.LEFT,g.TOP);
        g.textSize(textGroote);
//        g.fill(knopKleur.r, knopKleur.g, knopKleur.b);
        g.fill(knopKleur.r, knopKleur.g, knopKleur.b, 100);
        g.noStroke();
        g.rect(getX(), getY(), width, height);
        g.fill(textKleur.r, textKleur.g, textKleur.b);
        g.text(text,getX(),getY());
    }

	/**
	 * Controleer of er op de knop geklikt is wanneer er geklikt wordt
	 */
	@Override
	public void mousePressed(int x, int y, int button) {
		if (x > getX() && x < getX() + width && 
			y > getY() && y < getY() + height) {
			doeKnopActie();
		}
		
	}

	/**
	 * @param set de text voor de textKnop
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * De uit te voeren actie als er op de knop wordt gedruikt.
	 */
	public abstract void doeKnopActie();

	/**
	 * Geef de text die de knop bevat
	 */
	public String getText() {
		return text;
	}

}
