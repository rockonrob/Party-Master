package com.partyrock.anim.ledpanel;

import java.util.ArrayList;
import java.util.EnumSet;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Shell;

import com.partyrock.LightMaster;
import com.partyrock.anim.ElementAnimation;
import com.partyrock.element.ElementController;
import com.partyrock.element.ElementType;
import com.partyrock.element.led.LEDPanelController;

/**
 * Forwards "wave" of whatever color you want
 * Trigger
 * 
 * @author William Koehler
 * 
 */
public class LEDWave extends ElementAnimation {

    // The color to fade to
    //private Color red = sysColor(255, 5, 10);
    //private Color blue = sysColor(6, 3, 255);
    private Color pink = sysColor(255, 2, 215);
    private int fadedcol = -1;

    public LEDWave(LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
        super(master, startTime, elementList, duration);

        // Tell the animation system to call our animation's step() method repeatedly so we can animate over time
        needsIncrements();
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
    	
        for (ElementController controller : getElements()) {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;
            
            int colon = (int) (percentage * panel.getPanelWidth());
            panel.setColor(7, 0, pink);
        	panel.setColor(8, 0, pink);
            
            if (fadedcol < colon) {
            	for (int c = fadedcol + 1; c <= colon && c < panel.getPanelWidth(); c++) {
            		if (c < 4) {
            			panel.setColor(7 - c, c, pink);
                		panel.setColor(8 - c, c, pink);
            		}
            		else if (c < 12) {
            			panel.setColor(c + 1, c, pink);
                		panel.setColor(c + 2, c, pink);
            		}
            		else {
            			panel.setColor(12 * 2 - c - 1, c, pink);
            			panel.setColor(13 * 2 - c - 2, c, pink);
            		}
            	}
            }
        }
    	/* HAIRPIN
    	int newfadedColumns = 0;
        for (ElementController controller : getElements()) {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;
            
            int colon = (int) (percentage * panel.getPanelWidth());
            panel.setColor(7, 0, pink);
        	panel.setColor(8, 0, pink);
            
            if (fadedcol < colon) {
            	for (int c = fadedcol + 1; c <= colon && c < panel.getPanelWidth(); c++) {
            		if (colon < 4) {
            			panel.setColor(7 - c, c, pink);
                		panel.setColor(8 - c, c, pink);
            		}
            		else if (colon < 8) {
            			panel.setColor(c, c, pink);
                		panel.setColor(c + 1, c, pink);
            		}
            		else {
            			panel.setColor(7, c, pink);
            			panel.setColor(8, c, pink);
            		}
            	}
            }
        }
        fadedcol = newfadedColumns;
        */
    }
    
    public void trigger() {
        // For each panel, set each LED to a random color
    	
        for (ElementController element : getElements()) {
            LEDPanelController panel = (LEDPanelController) element;
            for (int r = 0; r < panel.getPanelHeight(); r++) {
                for (int c = 0; c < panel.getPanelWidth(); c++) {
                    panel.setColor(r, c, sysColor(255, 255, 255));
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
