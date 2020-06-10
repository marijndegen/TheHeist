package nl.han.ica.TheHeist;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import processing.core.PGraphics;

public class TextObject extends GameObject {

    private String text;
    private int textGroote;

    /**
     * @param text de text die het object moet weergeven
     */
    public TextObject(String text) {
        this.text=text;
        this.x = getX();
        this.y = getY();
        this.textGroote = 50;
    }
    
    /**
     * @param text de text die het object moet weergeven
     * @param x de x coordinaat van het gameobject
     * @param y de y coordinaat van het gameobject
     * @param textGroote de groote van de text
     */
    public TextObject(String text, int x, int y, int textGroote) {
        this.text=text;
        this.x = x;
        this.y = y;
        this.textGroote = textGroote;
    }

    /**
     * @param text set de text die het object moet weergeven
     */
    public void setText(String text) {
        this.text=text;
    }

    @Override
    public void update() {

    }

    /**
     * Teken het textobject
     */
    @Override
    public void draw(PGraphics g) {
        g.textAlign(g.LEFT,g.TOP);
        g.textSize(textGroote);
        g.text(text,getX(),getY());
    }
}
