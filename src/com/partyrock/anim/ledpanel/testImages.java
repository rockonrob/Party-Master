package com.partyrock.anim.ledpanel;

import java.util.ArrayList;
import java.util.EnumSet;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Shell;

import com.partyrock.LightMaster;
import com.partyrock.anim.ElementAnimation;
import com.partyrock.element.ElementController;
import com.partyrock.element.ElementType;
import com.partyrock.element.led.LEDPanelController;

/**
 * This is a basic animation that will wipe an LED panel from top to bottom with a given color
 * 
 * @author Matthew
 * 
 */
public class testImages extends ElementAnimation {

    // The color to fade to
    private Color color;

    // The number of rows we've faded
    private int fadedRows = -1;

    public testImages(LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
        super(master, startTime, elementList, duration);

        // Tell the animation system to call our animation's step() method repeatedly so we can animate over time
        needsIncrements();

        // Set a new default color of white
        color = sysColor(255, 255, 255);
    }

    /**
     * This method is called once when the animation is created. You can use it to get user configurable settings like a
     * Color (as we do in this case)
     */
    @Override
    public void setup(Shell window) {
        // This code opens a SWT Color Dialog
        // After setup is called, we'll have the color set
    }

    /**
     * Since we're doing something over time, we need to implement increment()
     * 
     * @param percentage The percentage of the way through the animation we are. This is between 0 and 1
     */
    public void increment(double percentage) {
        int newFadedRows = 0;

        // For every element we're given
        for (ElementController controller : getElements()) {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;

            // How many rows should be on based on our percentage?
            int rowsOn = (int) (percentage * panel.getPanelHeight());

            // So if we haven't already done this
            if (fadedRows < rowsOn) {

                // The for every row we haven't done
                for (int r = fadedRows + 1; r <= rowsOn && r < panel.getPanelHeight(); r++) {
                    // and every column in that row
                    for (int c = 0; c < panel.getPanelWidth() - 1; c+=2) {
                        if (r % 2 == 0) {
                        	panel.setColor(r, c, sysColor(100,100,100));
                        	panel.setColor(r, c + 1, sysColor(200,200,100));
                        }
                        else {
                        	panel.setColor(r, c + 1, sysColor(100,100,100));
                        	panel.setColor(r, c, sysColor(200,200,100));
                        }
                    }
                }
            }

            newFadedRows = rowsOn;
        }

        fadedRows = newFadedRows;
    }
    
    public void trigger() {
        // For each panel, set each LED to a random color
        for (ElementController element : getElements()) {
            LEDPanelController panel = (LEDPanelController) element;
            for (int r = 0; r < panel.getPanelHeight(); r++) {
                for (int c = 0; c < panel.getPanelWidth(); c++) {
                    panel.setColor(r, c, color);
                }
            }
        }
    }

    /**
     * This returns the kind of elements we support with this animation. In this case, it's simply LED Panels
     */
    public static EnumSet<ElementType> getSupportedTypes() {
        return EnumSet.of(ElementType.LEDS);
    }

}
