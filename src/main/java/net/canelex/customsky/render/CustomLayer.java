package net.canelex.customsky.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
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
        Minecraft.getMinecraft().renderEngine.bindTexture(this.resourceLocation);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        Tessellator tess = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        this.renderSide(tess, 4, size);
        GL11.glPushMatrix();
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        this.renderSide(tess, 1, size);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
        this.renderSide(tess, 0, size);
        GL11.glPopMatrix();
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        this.renderSide(tess, 5, size);
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        this.renderSide(tess, 2, size);
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        this.renderSide(tess, 3, size);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void renderSide(Tessellator tess, int side, int size) {
        double du = side % 3 / 3.0;
        double dv = side / 3 / 2.0;
        tess.startDrawingQuads();
        tess.addVertexWithUV(-size, -size, -size, du, dv);
        tess.addVertexWithUV(-size, -size, size, du, dv + 0.5);
        tess.addVertexWithUV(size, -size, size, du + 0.3333333333333333, dv + 0.5);
        tess.addVertexWithUV(size, -size, -size, du + 0.3333333333333333, dv);
        tess.draw();
    }
}
