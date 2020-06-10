package nl.han.ica.TheHeist;

import java.util.ArrayList;
import java.util.Random;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import processing.core.PGraphics;

public class VerkeerSpawner extends GameObject {
	private HeistWorld world;
	private int drukteOpScherm;
	private int aantalPolitie;
	private int politieGezondheid;
	private int politieAgressiviteit;

	Random random = new Random();
	int[][] rijbanen = { { 50, 120, 180, 245 }, { 375, 445, 505, 570 } };

	String[][] autos = {
			// afbeeldingsadres, hoogteverhouding, breedte, gezondheid, snelheid

			{ "nl/han/ica/TheHeist/media/Car1.png", "2.02", "50", "100", "5", },
			{ "nl/han/ica/TheHeist/media/Car2.png", "1.69", "50", "100", "7" },
			{ "nl/han/ica/TheHeist/media/Car3.png", "2.01", "50", "100", "6" },
			{ "nl/han/ica/TheHeist/media/Car4.png", "2.04", "50", "100", "4" },
			{ "nl/han/ica/TheHeist/media/Car5.png", "1.2", "50", "100", "5" },
			{ "nl/han/ica/TheHeist/media/Car6.png", "1.5", "50", "100", "8" },
			{ "nl/han/ica/TheHeist/media/Car7.png", "2.01", "50", "100", "7" } };
	String[][] politieAutos = {
			// afbeeldingsadres, hoogteverhouding, breedte, percentage,
			// snelheid, agressiviteit nummer frames
			{ "nl/han/ica/TheHeist/media/UnderCover_1_sm.png", "2.015", "50", "8", "2", "2" },
			{ "nl/han/ica/TheHeist/media/PoliceCar_1_sm.png", "2.06", "50", "8", "2", "2" } };

	/**
	 * 
	 * @param world
	 * @param drukteOpScherm
	 * @param aantalPolitie
	 * @param PolitieGezondheid
	 * 
	 *            Doet ervoor zorgendat alle auto's spawnen volgens de
	 *            meegegeven variabelen
	 */
	public VerkeerSpawner(HeistWorld world, int drukteOpScherm, int aantalPolitie, int politieGezondheid, int politieAgressiviteit) {
		super();
		this.world = world;
		this.drukteOpScherm = drukteOpScherm;
		this.aantalPolitie = aantalPolitie;
		this.politieGezondheid = politieGezondheid;
		this.politieAgressiviteit = politieAgressiviteit;
	}

	/**
	 * checkt of er nog genoeg auto's op het scherm zijn en patst er anders een
	 * bij
	 */
	@Override
	public void update() {
		int numBurgerAutos = 0;
		int numPolitieAutos = 0;
		for (GameObject object : world.getGameObjectItems()) {
			if (object instanceof BurgerAuto) {
				numBurgerAutos++;
			}
			if (object instanceof PolitieAuto) {
				numPolitieAutos++;
			}
		}

		if (numBurgerAutos < drukteOpScherm) {
			spawnBurgerAuto();
		}
		if (numPolitieAutos < aantalPolitie) {
			spawnPolitieAuto();
		}

	}

	/**
	 * patst een burgerauto met willekeurige rijstrook van een willekeurig model
	 * in de ijst met autos
	 */
	private void spawnBurgerAuto() {
		int type = random.nextInt(autos.length) + 0;
		int rijRichting = random.nextInt(3) + 0;
		int rijBaan = random.nextInt(4) + 0;

		switch (rijRichting) {
		case 0:
			new BurgerAuto(world, new Sprite(autos[type][0]),
					(int) Math.round(Float.parseFloat(autos[type][1]) * Integer.parseInt(autos[type][2])),
					Integer.parseInt(autos[type][2]), rijbanen[0], rijBaan, Integer.parseInt(autos[type][3]),
					0 - Integer.parseInt(autos[type][4]), -200);
			break;
		case 1:
			new BurgerAuto(world, new Sprite(autos[type][0]),
					(int) Math.round(Float.parseFloat(autos[type][1]) * Integer.parseInt(autos[type][2])),
					Integer.parseInt(autos[type][2]), rijbanen[1], rijBaan, Integer.parseInt(autos[type][3]),
					Integer.parseInt(autos[type][4]), world.getHeight() + 200);
			break;
		case 2:
			new BurgerAuto(world, new Sprite(autos[type][0]),
					(int) Math.round(Float.parseFloat(autos[type][1]) * Integer.parseInt(autos[type][2])),
					Integer.parseInt(autos[type][2]), rijbanen[1], rijBaan, Integer.parseInt(autos[type][3]),
					Integer.parseInt(autos[type][4]), -200);
			break;
		}
	}

	/**
	 * patst een politieAuto met willekeurige rijstrook van een willekeurig
	 * model in de ijst met autos
	 */
	private void spawnPolitieAuto() {
		int rijRichting = random.nextInt(3) + 0;
		int rijBaan = random.nextInt(4) + 0;
		int type = random.nextInt(politieAutos.length) + 0;

		switch (rijRichting) {
		case 0:
			new PolitieAuto(world, new Sprite(politieAutos[type][0]),
					(int) Math.round(Float.parseFloat(politieAutos[type][1]) * Integer.parseInt(politieAutos[type][2])),
					Integer.parseInt(politieAutos[type][2]), rijbanen[0], rijBaan, politieGezondheid,
					Integer.parseInt(politieAutos[type][3]), politieAgressiviteit, -200, Integer.parseInt(politieAutos[type][4]));
			break;
		case 1:
			new PolitieAuto(world, new Sprite(politieAutos[type][0]),
					(int) Math.round(Float.parseFloat(politieAutos[type][1]) * Integer.parseInt(politieAutos[type][2])),
					Integer.parseInt(politieAutos[type][2]), rijbanen[0], rijBaan, politieGezondheid,
					Integer.parseInt(politieAutos[type][3]), politieAgressiviteit, world.getHeight() + 200,
					Integer.parseInt(politieAutos[type][4]));
			break;
		case 2:
			new PolitieAuto(world, new Sprite(politieAutos[type][0]),
					(int) Math.round(Float.parseFloat(politieAutos[type][1]) * Integer.parseInt(politieAutos[type][2])),
					Integer.parseInt(politieAutos[type][2]), rijbanen[1], rijBaan, politieGezondheid,
					Integer.parseInt(politieAutos[type][3]), politieAgressiviteit, -200, Integer.parseInt(politieAutos[type][4]));
			break;
		case 3:
			new PolitieAuto(world, new Sprite(politieAutos[type][0]),
					(int) Math.round(Float.parseFloat(politieAutos[type][1]) * Integer.parseInt(politieAutos[type][2])),
					Integer.parseInt(politieAutos[type][2]), rijbanen[1], rijBaan, politieGezondheid,
					Integer.parseInt(politieAutos[type][3]), politieAgressiviteit, world.getHeight() + 200,
					Integer.parseInt(politieAutos[type][4]));
			break;
		}

	}

	@Override
	public void draw(PGraphics g) {
	}

}
