package net.canelex.customsky.render;

import net.canelex.customsky.CustomSkyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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
        if (mc.theWorld.provider.getDimensionId() == 1) {
            this.renderEnd(partialTicks);
        } else if (mc.theWorld.provider.isSurfaceWorld() && this.mod.customSkies.size() > this.mod.currentSkyID) {
            this.mod.customSkies.get(this.mod.currentSkyID).renderSky();
        }
    }

    private void renderEnd(float partial) {
        GlStateManager.disableFog();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.depthMask(false);
        this.mc.renderEngine.bindTexture(this.locationEndSkyPng);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        for (int i = 0; i < 6; i++) {
            GL11.glPushMatrix();
            if (i == 1) {
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 2) {
                GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 3) {
                GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 4) {
                GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if (i == 5) {
                GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos(-100.0, -100.0, -100.0).tex(0.0, 0.0).color(40, 40, 40, 255).endVertex();
            worldrenderer.pos(-100.0, -100.0, 100.0).tex(0.0, 16.0).color(40, 40, 40, 255).endVertex();
            worldrenderer.pos(100.0, -100.0, 100.0).tex(16.0, 16.0).color(40, 40, 40, 255).endVertex();
            worldrenderer.pos(100.0, -100.0, -100.0).tex(16.0, 0.0).color(40, 40, 40, 255).endVertex();

            tessellator.draw();
            GlStateManager.popMatrix();
        }
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
    }
}
