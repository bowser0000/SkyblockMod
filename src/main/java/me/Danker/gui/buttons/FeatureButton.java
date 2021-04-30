package me.Danker.gui.buttons;

import net.minecraft.client.gui.GuiButton;

import java.util.Arrays;
import java.util.List;

public class FeatureButton extends GuiButton {

    public List<String> hoverText;

    public FeatureButton(String displayString, String hoverText) {
        super(0, 0, 0, displayString);
        this.hoverText = Arrays.asList(hoverText.split("\n"));
    }

}
