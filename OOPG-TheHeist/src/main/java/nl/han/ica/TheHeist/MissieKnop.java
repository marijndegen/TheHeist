package nl.han.ica.TheHeist;

import nl.han.ica.OOPDProcessingEngineHAN.Dashboard.Dashboard;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.View.View;

public class MissieKnop extends TextKnop {

	private Missie missie;
	
	/**
	 * 
	 * @param text de text op de missieknop
	 * @param x de x coordienaat van de kno
	 * @param y de y coordienaat van de kno
	 * @param width de breedte van de knop
	 * @param height de hooogte van de knop
	 * @param knopKleur de kleur an de knop
	 * @param textKleur de kleur van de text
	 * @param missie de af te spelen missie die de knop bijhoud.
	 * @param world de wereld waarin de missieknoppen getekend worden.
	 */
	public MissieKnop(String text, float x, float y, float width, float height, Kleur knopKleur, Kleur textKleur, Missie missie, HeistWorld world) {
		super(text, x, y, width, height, knopKleur, textKleur, world);
		this.missie = missie;
	}
	
	/**
	 * De uit te voeren knop actie, laat het missieoverzichtscherm met de beschrijving van de missie zien.
	 */
	@Override
	public void doeKnopActie() {
		world.deleteAllGameObjectsOfType(MissieKnop.class);
		world.deleteAllGameObjectsOfType(Dashboard.class);
		world.deleteDashboard(world.scherm.dashboard);
		world.currentMissie = missie;
		
		View view = new View(world.worldWidth, world.worldHeight);
		world.setView(view);
		
		TextObject titel 		= new TextObject(world.currentMissie.Titel, 0, 250, 35);
		world.addGameObject(titel);
		
		int chars = 50;
		int textGroote = 20;
		for(int i = 0; i < world.currentMissie.omschrijving.length(); i+= chars){
			int end;
			if(i + chars  >  world.currentMissie.omschrijving.length()){
				end = world.currentMissie.omschrijving.length();
			}else{
				end = i + chars;
			}
			TextObject beschrijving	= new TextObject(world.currentMissie.omschrijving.substring(i, end), 0, 300 + (i / chars * textGroote), 20); 
			world.addGameObject(beschrijving);
		}
		
		TextObject geld 		= new TextObject("+ $" + world.currentMissie.gestolenBuit + "", 0, 600, 50);
		world.addGameObject(geld);
		missie.setStartTijd(System.currentTimeMillis());		
	}

}
