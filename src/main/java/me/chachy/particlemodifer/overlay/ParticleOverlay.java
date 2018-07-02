package me.chachy.particlemodifer.overlay;

import cc.hyperium.gui.main.HyperiumOverlay;
import cc.hyperium.gui.main.components.OverlaySlider;
import me.chachy.particlemodifer.ParticleMod;

/*
 * Created by Cubxity on 02/07/2018
 */
public class ParticleOverlay extends HyperiumOverlay {
    public ParticleOverlay() {
        getComponents().add(new OverlaySlider("Multiplier", 1, 100, 1, v -> ParticleMod.multiplier = Math.round(v), true));
    }
}
