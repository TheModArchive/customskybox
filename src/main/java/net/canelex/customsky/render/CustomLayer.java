package net.canelex.customsky.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CustomLayer {
    public final ResourceLocation resourceLocation;

    public CustomLayer(String resName) {
        this.resourceLocation = new ResourceLocation("skyresources", resName);
    }

    public CustomLayer(String domain, String resName) {
        this.resourceLocation = new ResourceLocation(domain, resName);
    }

    public void renderLayer(int size) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
        GlStateManager.alphaFunc(516, 0.1f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableFog();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        Tessellator tess = Tessellator.getInstance();
        GlStateManager.pushMatrix();
        GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-90.0f, 0.0f, 0.0f, 1.0f);
        this.renderSide(tess, 4, size);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
        this.renderSide(tess, 1, size);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
        this.renderSide(tess, 0, size);
        GlStateManager.popMatrix();
        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
        this.renderSide(tess, 5, size);
        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
        this.renderSide(tess, 2, size);
        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
        this.renderSide(tess, 3, size);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableFog();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }

    private void renderSide(Tessellator tess, int side, int size) {
        WorldRenderer wr = tess.getWorldRenderer();
        double du = (double) (side % 3) / 3.0;
        double dv = (double) (side / 3) / 2.0;
        wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        wr.pos(-size, -size, -size).tex(du, dv).endVertex();
        wr.pos(-size, -size, size).tex(du, dv + 0.5).endVertex();
        wr.pos(size, -size, size).tex(du + 0.3333333333333333, dv + 0.5).endVertex();
        wr.pos(size, -size, -size).tex(du + 0.3333333333333333, dv).endVertex();
        tess.draw();
    }
}
