package nl.han.ica.TheHeist;

import nl.han.ica.OOPDProcessingEngineHAN.View.View;

public class Missie {
	private HeistWorld world;
	private long startTijd;
	private boolean isGestart;
	public String Titel;
	public String omschrijving;
	private int lengte;
	private int geredenlengte;
	public int gestolenBuit;
	public int overgeblevenBuit;
	private int verminderFactor;
	private long vorigeBotsing;
	private long laatsGestart;
	//variabelen voor verkeersSpawner
	Weg weg;
	private int drukteOpScherm;
	private int aantalPolitie;
	private int politieGezondheid;
	private int politieAgressiviteit;

	/**
	 * 
	 * @param world de wereld waarin de missie zich afspeeld
	 * @param titel	De titel van de missie voor verhaal doeleinden
	 * @param omschrijving de beschrijving van de missie voor verhaal doeleinden
	 * @param lengte de af te leggen lengte van de missie in het aantal (weg) afbeeldingen die moet worden gespawned
	 * @param gestolenBuit De buit die voor de overvallen ( in het begin van de missie) wordt gemaakt, het geld als score
	 * @param verminderFactor de verminderfactor van score (buit) wanneer een auto botst
	 * @param drukteOpScherm het aantal burger auto's op het scherm
	 * @param aantalPolitie het aantal politie auto's op het scherm
	 * @param politieGezondheid de gezondheid van de politie (niet gebruikt)
	 * @param politieAgressiviteit de vaardigheid (en lompheid van de politie) 
	 */
	public Missie(HeistWorld world, String titel, String omschrijving, int lengte, int gestolenBuit, int verminderFactor, int drukteOpScherm, int aantalPolitie, int politieGezondheid, int politieAgressiviteit) {
		this.world = world;
		this.Titel = titel;
		this.omschrijving = omschrijving;
		this.lengte = lengte;
		this.gestolenBuit = gestolenBuit;
		this.verminderFactor = verminderFactor;
		
		//VerkeersSpawner variabelen
		this.drukteOpScherm = drukteOpScherm;
		this.aantalPolitie = aantalPolitie;
		this.politieGezondheid = politieGezondheid;
		this.politieAgressiviteit = politieAgressiviteit;
		
	}

	/**
	 * vermindert de buit die in een missiie kan worden gemaakt. Wordt aangeroepen wanneer een speler een actie maakt die niet de bedoeling is.
	 */
	public void verminderBuit() {
		if(System.currentTimeMillis() - vorigeBotsing >= 400 && this.overgeblevenBuit > 0){
			this.overgeblevenBuit-=verminderFactor;
			vorigeBotsing = System.currentTimeMillis();
		}
	}

	/**
	 * Deze functie houd de instellingen van de gereden afstand bij.
	 */
	public void verhoogAfstand(){
		this.geredenlengte++;
		isMissieKlaar();
	}
	
	/**
	 * @return of de missie al afgelopen is of niet.
	 */
	public boolean isMissieKlaar(){
		if(geredenlengte >= lengte){
			stopMissie();
			world.scherm.maakStartScherm(world.missies);
		}
		return false;
	}
	
	/**
	 * Start de huidige missie in wereld.
	 */
	public void startMissie(){
		world.deleteAllGameObjectsOfType(TextObject.class);
		weg = new Weg(world, Weg.wegAfbeeldingPath);
		world.addGameObject(weg, 0, -10);
		world.addGameObject(new VerkeerSpawner(world, this.drukteOpScherm, this.aantalPolitie, this.politieGezondheid, this.politieAgressiviteit));
		world.gebruiker.maakVluchtAuto();
		this.overgeblevenBuit = gestolenBuit;
		world.dashBoardText.setText("$ "+overgeblevenBuit);
		world.addGameObject(world.dashBoardText);
		
		//muziek
		world.copRadio.play();
		world.trafficSound.play();
		world.backgroundMusic.play();	
	}
	
	/**
	 * beindigt de huidige missie in wereld.
	 */
	public void stopMissie(){
		world.deleteAllDashboards();
		world.deleteAllGameObjectsOfType(VerkeerSpawner.class);
		world.deleteAllGameObjectsOfType(Vluchtauto.class);
		world.deleteAllGameObjectsOfType(BurgerAuto.class);
		world.deleteAllGameObjectsOfType(PolitieAuto.class);
		world.deleteAllGameObjectsOfType(Weg.class);
		world.currentMissie.isGestart = false;
		world.currentMissie.geredenlengte = 0;
		world.currentMissie = null;
		world.copRadio.pause();
		world.trafficSound.pause();
		world.backgroundMusic.pause();
		world.dashBoardText.setText("");
	}
	
	
	
	
	/**
	 * wordt aangeroepen door HeistWorld.update
	 */
	public void update(){
		if(System.currentTimeMillis() - startTijd > 5000 && !isGestart){
			isGestart = true;
			this.startMissie();	
			System.out.println("asdf");
		}
	}
	
	/**
	 * wordt geset op het moment dat de gebruiker op de knop drukt. 
	 */
	public void setStartTijd(long startTijd) {
		this.startTijd = startTijd;
	}
}
