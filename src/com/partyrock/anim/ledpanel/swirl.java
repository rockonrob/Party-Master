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
 * Swirl Animation -- color red and color blue can be changed to whatever color wanted
 * 	easiest to just change the sysColor
 * NO Trigger
 * 
 * @author William Koehler
 * 
 */
public class swirl extends ElementAnimation {

    // The color to fade to
    private Color red = sysColor(255, 5, 10);
    private Color blue = sysColor(6, 3, 255);

    public swirl(LightMaster master, int startTime, ArrayList<ElementController> elementList, double duration) {
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
            int gofor = (int) (percentage * panel.getPanelWidth() * panel.getPanelHeight() / 2);
            int gone = 0;
            while (gofor > 0) {
            	if (gone < 16) {
            		panel.setColor(15, panel.getPanelWidth() - 1 - gone, red);
            		panel.setColor(0, gone, blue);
            	}
            	else if (gone < 30) {
            		panel.setColor(panel.getPanelHeight() - 2 - gone + 16, 0, red);
            		panel.setColor(gone - 15, 15, blue);
            	}
            	else if (gone < 44) {
            		panel.setColor(1, 1 + gone - 30, red);
            		panel.setColor(14, panel.getPanelWidth() - 2 - gone + 30, blue);
            	}
            	else if (gone < 56) {
            		panel.setColor(1 + gone - 43, 14, red);
            		panel.setColor(panel.getPanelHeight() - 3 - gone + 44, 1, blue);
            	}
            	else if (gone < 68) {
            		panel.setColor(13, panel.getPanelWidth() - 3 - gone + 56, red);
            		panel.setColor(2,gone - 56 + 2, blue);
            	}
            	else if (gone < 78) {
            		panel.setColor(panel.getPanelHeight() - 4 - gone + 68, 2, red);
            		panel.setColor(gone - 68 + 3, 13,blue);
            	}
            	else if (gone < 88) {//10
            		panel.setColor(3, gone - 78 + 3, red);
            		panel.setColor(12, panel.getPanelWidth() - 4 - gone + 78, blue);
            	}
            	else if (gone < 96) { //8
            		panel.setColor(gone - 88 + 4, 12, red);
            		panel.setColor(panel.getPanelHeight() - 5 - gone + 88, 3, blue);
            	}
            	else if (gone < 104) {//8
            		panel.setColor(11, panel.getPanelWidth() - 5 - gone + 96, red);
            		panel.setColor(4, gone - 96 + 4, blue);;
            	}
            	else if (gone < 110) {//6
            		panel.setColor(panel.getPanelHeight() - 6 - gone + 104, 4, red);
            		panel.setColor(gone - 104 + 5, 11, blue);
            	}
            	else if (gone < 116) {//6
            		panel.setColor(5, gone - 110 + 5, red);
            		panel.setColor(10, panel.getPanelWidth() - 6 - gone + 110, blue);
            	}
            	else if (gone < 120) {//4
            		panel.setColor(gone - 116 + 6, 10, red);
            		panel.setColor(panel.getPanelHeight() - 7 - gone + 116, 5, blue);
            	}
            	else if (gone < 124) {//4
            		panel.setColor(9,9 - gone + 120, red);
            		panel.setColor(6, 6 + gone - 120, blue);
            	}
            	else if (gone < 126) {//2
            		panel.setColor(8 - gone + 124, 6, red);
            		panel.setColor(gone - 124 + 7, 9, blue);
            	}
            	else if (gone < 127){//1
            		panel.setColor(7, 7, red);
            		panel.setColor(8, 8, blue);
            	}
            	else {//1
            		panel.setColor(7, 8, red);
            		panel.setColor(8, 7, blue);
            	}
            	gofor--;
            	gone++;
            	if (gone == 127) {
            		panel.setColor(7, 8, red);
            		panel.setColor(8, 7, blue);
            	}
            }
        }
    }
    
    public void trigger() {
    	/*
        // For each panel, set each LED to a random color
        for (ElementController element : getElements()) {
            LEDPanelController panel = (LEDPanelController) element;
            for (int r = 0; r < panel.getPanelHeight(); r++) {
                for (int c = 0; c < panel.getPanelWidth(); c++) {
                    panel.setColor(r, c, sysColor(255, 255, 255));
                }
            }
        }
        */
    }

    /**
     * This returns the kind of elements we support with this animation. In this case, it's simply LED Panels
     */
    public static EnumSet<ElementType> getSupportedTypes() {
        return EnumSet.of(ElementType.LEDS);
    }

}
