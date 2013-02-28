package com.partyrock;

import java.util.ArrayList;

import com.partyrock.anim.LightAnimationManager;
import com.partyrock.comm.uc.Microcontroller;
import com.partyrock.element.ElementController;
import com.partyrock.gui.LightWindowManager;
import com.partyrock.location.LightLocationManager;
import com.partyrock.music.LightMusicManager;
import com.partyrock.show.ShowInfo;

/**
 * LightMaster is, as the name implies, the master class. Everything starts
 * here.
 * @author Matthew
 * 
 */
public class LightMaster {
	private LightWindowManager windowManager;
	private ArrayList<ElementController> elements;
	private ArrayList<Microcontroller> controllers;
	private ShowInfo show;
	private LightMusicManager musicManager;
	private LightLocationManager locationManager;
	private LightAnimationManager animationManager;

	public LightMaster(String... args) {

		elements = new ArrayList<ElementController>();
		controllers = new ArrayList<Microcontroller>();

		// Construct the music manager to manage the show's music
		musicManager = new LightMusicManager(this);

		// Construct the animation manager to manage the list of animations for
		// someone to select from
		animationManager = new LightAnimationManager(this);

		// Construct the location manager to manage the show file
		locationManager = new LightLocationManager(this);

		// Construct the GUI. This will make the main window.
		windowManager = new LightWindowManager(this);

		// Temporary development measure - Try to load the location.loc file if
		// it exists. This can be commented out for normal operation.
		// Must be done after everything else is loaded, but before the loop
		locationManager.loadNormalLocFile();

		// This starts the GUI loop, so nothing should be called after this
		windowManager.loop();
	}

	public static void main(String... args) {
		new LightMaster(args);
	}

	/**
	 * Returns the list of elements (lights, lasers, LEDs, etc.)
	 */
	public ArrayList<ElementController> getElements() {
		return elements;
	}

	/**
	 * Returns the list of microcontrollers
	 */
	public ArrayList<Microcontroller> getControllers() {
		return controllers;
	}

	/**
	 * Adds an element to the list
	 * @param elementList The list of elements to add
	 */
	public void addElement(ElementController... elementList) {
		// Add all elements to the element list
		for (ElementController element : elementList) {
			elements.add(element);
		}
	}

	/**
	 * Removes an element from the list
	 * @param victim The element to remove
	 */
	public void removeElement(ElementController victim) {
		elements.remove(victim);
		getLocationManager().removeElement(victim);
		getLocationManager().unsavedChanges();
	}

	/**
	 * Adds a list of microcontrollers to the existing microcontroller list
	 * @param controllerList The list of controllers to add.
	 */
	public void addController(Microcontroller... controllerList) {
		for (Microcontroller controller : controllerList) {
			controllers.add(controller);
		}
	}

	public LightWindowManager getWindowManager() {
		return windowManager;
	}

	public ShowInfo getShow() {
		return show;
	}

	public LightMusicManager getMusicManager() {
		return musicManager;
	}

	public LightLocationManager getLocationManager() {
		return locationManager;
	}

	public LightAnimationManager getAnimationManager() {
		return animationManager;
	}

	/**
	 * Called when the main window is closed, and the system is going down
	 */
	public void onDispose() {
		locationManager.attemptToSave();
	}
}
