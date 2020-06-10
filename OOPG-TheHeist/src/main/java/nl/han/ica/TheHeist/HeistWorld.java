package nl.han.ica.TheHeist;

import java.lang.reflect.Array;

import com.sun.prism.image.ViewPort;
import nl.han.ica.OOPDProcessingEngineHAN.Dashboard.Dashboard;
import nl.han.ica.OOPDProcessingEngineHAN.Engine.GameEngine;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Persistence.FilePersistence;
import nl.han.ica.OOPDProcessingEngineHAN.Persistence.IPersistence;
import nl.han.ica.OOPDProcessingEngineHAN.Sound.Sound;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.TileMap;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.TileType;
import nl.han.ica.OOPDProcessingEngineHAN.View.EdgeFollowingViewport;
import nl.han.ica.OOPDProcessingEngineHAN.View.View;
import processing.core.PApplet;
import processing.core.PImage;

@SuppressWarnings("serial")
public class HeistWorld extends GameEngine {
	Missie[] missies;
	Missie currentMissie;
	int wegSnelheid;
	Gebruiker gebruiker;
	Scherm scherm;
	int worldWidth, worldHeight;
	TextObject dashBoardText;
	
	//sounds
    Sound copRadio = new Sound(this, "src/main/java/nl/han/ica/theHeist/media/police radio.mp3");
    Sound trafficSound = new Sound(this, "src/main/java/nl/han/ica/theHeist/media/EMS.mp3");
    Sound backgroundMusic = new Sound(this, "src/main/java/nl/han/ica/theHeist/media/Gorilaz_Feel_Good.mp3");
    Sound botsing = new Sound(this, "src/main/java/nl/han/ica/theHeist/media/carImpact.mp3");

	
	public static void main(String[] args) {
		PApplet.main(new String[] { "nl.han.ica.TheHeist.HeistWorld" });
	}

	/**
	 * deze functie wordt in het begin uitgevoerd.
	 */
	@Override
	public void setupGame() {
		worldWidth = 700;
		worldHeight = 700;

		this.frameRate(25);
		size(worldWidth, worldHeight );
		maakMissies();
		createObjects();
		wegSnelheid = 0;
		initialiseerGeluid();
		frame.setTitle("The heist 2");
	}

	/**
	 * Creeer de objecten
	 */
	private void createObjects() {
		gebruiker = new Gebruiker(this);
		scherm = new Scherm(this, worldWidth, worldHeight, new Sprite("src/main/java/nl/han/ica/TheHeist/media/startscreen.png"));
		scherm.maakStartScherm(missies);
		dashBoardText = new TextObject("");
		addGameObject(dashBoardText);
	}
	
	/**
	 * Creeer de missies
	 */
	private void maakMissies(){
		missies = new Missie[6];
//		Missie(					HeistWorld world, String titel, 			String omschrijving, 																																														int lengte,	int gestolenBuit,   int verminderFactor,int drukteOpScherm, int aantalPolitie, int politieGezondheid, 	int politieAgressiviteit)
		missies[0] = new Missie(this, 			  "The beginning 123",  		"You have stolen a car in the town of ludendorf, it turned out to be the car of the local mayor", 																											25,			10000, 				50, 				8, 					3, 					100, 					3);
		missies[1] = new Missie(this, 			  "Subway robbery", 	"You had an argument about cheese, you decided to rob the shopand stole a vast amount of cash", 																											30, 		17000, 				100, 				9, 					4, 					100, 					3);
		missies[2] = new Missie(this, 			  "Breaking a bank", 		"A banker saw you had the argument at the subway and thought it was hillarious, he tipped you about a large amount of cash comming into his rival bank.",													40, 		35000, 				300, 				9, 					5, 					100, 					4);		
		missies[3] = new Missie(this, 			  "Black Casino",  		"Had bad luck the other day at the casino, the cashier laughed at you while you lost a grand. Now your taking fath into your own hands, rob the basterds!", 												50, 		60000, 				500, 				8, 					6, 					100, 					4);
		missies[4] = new Missie(this, 			  "Pablo escabar", 	"This is definitly a dangerous one, you have stolen a hugo amount of cash from drugdealers. They bribed the police to get after you. Get away from them, you do have to cross the whole dessert though!", 	90, 		200000, 			1000, 				9, 					7, 					100, 					5);
		missies[5] = new Missie(this, 			  "Fort Knox", 				"Today you have stolen diamonds and gold from Fort Knox, this is the big one. The whole street full of Police, FBI & CIA. Try to stay alive!",																60, 		2000000, 			8000, 				2, 					15, 				100, 					6);

	}
	
	/**
	 * @param screenWidth de schermbreedte
	 * @param screenHeight de schermhoogte
	 */
	private void createView(int screenWidth, int screenHeight) {
		View view = new View(screenWidth, screenHeight);
		setView(view);
	}
	
	/**
	 * maak het geluid aan
	 */
	private void initialiseerGeluid(){
		copRadio.loop(1000000000);
		trafficSound.loop(1000000000);
		backgroundMusic.loop(1000000000);
		
		copRadio.pause();
		trafficSound.pause();
		backgroundMusic.pause();
	}

	/**
	 * Update de missie, kijk of hij al klaar is
	 */
	@Override
	public void update() {
		if(currentMissie != null){
			currentMissie.update();
			if(currentMissie.isMissieKlaar()){
				currentMissie.stopMissie();
				scherm.maakStartScherm(missies);
			}
		}
		
	}

}
