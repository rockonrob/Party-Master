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
 * Swirl Animation
 * 
 * @author William Koehler
 * 
 */
public class HairPin extends ElementAnimation {

    // The color to fade to
    //private Color red = sysColor(255, 5, 10);
    //private Color blue = sysColor(6, 3, 255);
    private Color purple = sysColor(190, 3, 255);
    private int fadedcol = -1;

    public HairPin(LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
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
    	int newfadedColumns = 0;
        for (ElementController controller : getElements()) {
            // We only put LEDS in our getSupportedTypes(), so that's all we're going to get.
            LEDPanelController panel = (LEDPanelController) controller;
            
            int colon = (int) (percentage * panel.getPanelWidth());
            panel.setColor(7, 0, purple);
        	panel.setColor(8, 0, purple);
            
            if (fadedcol < colon) {
            	for (int c = fadedcol + 1; c <= colon && c < panel.getPanelWidth(); c++) {
            		if (colon < 4) {
            			panel.setColor(7 - c, c, purple);
                		panel.setColor(8 - c, c, purple);
            		}
            		else if (colon < 8) {
            			panel.setColor(c, c, purple);
                		panel.setColor(c + 1, c, purple);
            		}
            		else {
            			panel.setColor(7, c, purple);
            			panel.setColor(8, c, purple);
            		}
            	}
            }
        }
        fadedcol = newfadedColumns;
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
