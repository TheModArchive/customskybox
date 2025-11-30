package net.canelex.customsky.render;

import net.canelex.customsky.CustomSkyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

public class SkyRenderer extends IRenderHandler {
    private final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
    private final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
    private final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
    private final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
    private final Minecraft mc = Minecraft.getMinecraft();
    private final CustomSkyMod mod;

    public SkyRenderer(CustomSkyMod mod) {
        this.mod = mod;
    }

    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        if (mc.theWorld.provider.dimensionId == 1) {
            this.renderEnd(partialTicks);
        } else if (mc.theWorld.provider.isSurfaceWorld() && this.mod.customSkies.size() > this.mod.currentSkyID) {
            this.mod.customSkies.get(this.mod.currentSkyID).renderSky();
        }
    }

    private void renderEnd(float partial) {
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        RenderHelper.disableStandardItemLighting();
        GL11.glDepthMask(false);
        this.mc.renderEngine.bindTexture(this.locationEndSkyPng);
        Tessellator tessellator = Tessellator.instance;

        for (int i = 0; i < 6; i++) {
            GL11.glPushMatrix();
            if (i == 1) {
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 2) {
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 3) {
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 4) {
                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if (i == 5) {
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            tessellator.startDrawingQuads();
            tessellator.setColorOpaque_I(2631720);
            tessellator.addVertexWithUV(-100.0, -100.0, -100.0, 0.0, 0.0);
            tessellator.addVertexWithUV(-100.0, -100.0, 100.0, 0.0, 16.0);
            tessellator.addVertexWithUV(100.0, -100.0, 100.0, 16.0, 16.0);
            tessellator.addVertexWithUV(100.0, -100.0, -100.0, 16.0, 0.0);
            tessellator.draw();
            GL11.glPopMatrix();
        }

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }
}
