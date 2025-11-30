package net.canelex.customsky.render;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class CustomSky {
    public final String skyName;
    private final Minecraft mc = Minecraft.getMinecraft();
    public CustomLayer dawnLayer = null;
    public CustomLayer dayLayer = null;
    public CustomLayer duskLayer = null;
    public CustomLayer nightLayer = null;
    private long worldTime;
    private int skyboxSize;

    public CustomSky(String name) {
        this.skyName = name;
    }

    public void renderSky() {
        this.worldTime = this.mc.theWorld.getWorldTime() % 24000L;
        this.skyboxSize = this.mc.gameSettings.renderDistanceChunks * 16;
        this.renderLayersOfTime(this.dawnLayer, 0L, 1000L);
        this.renderLayersOfTime(this.dayLayer, 1000L, 12000L);
        this.renderLayersOfTime(this.duskLayer, 12000L, 13000L);
        this.renderLayersOfTime(this.nightLayer, 13000L, 24000L);
    }

    private void renderLayersOfTime(CustomLayer layer, long thisTime, long nextTime) {
        float alpha = 0.0F;
        if (this.ticksUntil(this.worldTime, thisTime) > 12000L) {
            if (this.ticksUntil(this.worldTime, nextTime) < 12000L) {
                alpha = 1.0F;
            } else if (this.ticksSince(this.worldTime, nextTime) < 200L) {
                alpha = 1.0F - (float) this.ticksSince(this.worldTime, nextTime) / 200.0F;
            }
        } else if (this.ticksUntil(this.worldTime, thisTime) < 200L) {
            alpha = 1.0F - (float) this.ticksUntil(this.worldTime, thisTime) / 200.0F;
        }

        if (alpha != 0.0F) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
            layer.renderLayer(this.skyboxSize);
        }
    }

    private long ticksUntil(long now, long next) {
        return now > next ? next + 24000L - now : next - now;
    }

    private long ticksSince(long now, long last) {
        return now > last ? now - last : 24000L + now - last;
    }
}
