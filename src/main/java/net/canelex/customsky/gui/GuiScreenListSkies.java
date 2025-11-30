package net.canelex.customsky.gui;

import net.canelex.customsky.CustomSkyMod;
import net.canelex.customsky.render.CustomSky;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

public class GuiScreenListSkies extends GuiScreen {
    private final CustomSkyMod mod;
    private boolean clickedScrollBar = false;
    private int scrollBarY = 0;
    private int scrollBarHeight = 0;
    private int scrollBarAmount = 0;
    private int scrollBoxHeight = 0;
    private int lastY;

    public GuiScreenListSkies(CustomSkyMod mod) {
        this.mod = mod;
    }

    public void initGui() {
        this.scrollBoxHeight = this.mod.customSkies.size() * 32;
        this.scrollBarHeight = (this.scrollBoxHeight - (this.scrollBoxHeight - this.height)) * this.height / this.scrollBoxHeight;
    }

    public void drawScreen(int x, int y, float partial) {
        drawRect(this.width / 2 - 100, 0, this.width / 2 + 100, this.height, new Color(0, 0, 0, 130).getRGB());
        this.scrollBar(y);

        for (int id = 0; id < this.mod.customSkies.size(); id++) {
            this.drawSkyResource(id);
        }
    }

    protected void mouseClicked(int x, int y, int button) {
        if (x > this.width / 2 + 101 && x <= this.width / 2 + 107 && y >= this.scrollBarY && y <= this.scrollBarY + this.scrollBarHeight) {
            this.clickedScrollBar = true;
        }

        for (int id = 0; id < this.mod.customSkies.size(); id++) {
            if (x >= this.width / 2 - 99
                    && x <= this.width / 2 + 99
                    && y > 5 + id * 32 - this.scrollBarAmount
                    && y < 33 + id * 32 - this.scrollBarAmount) {
                this.mod.currentSkyID = id;
                break;
            }
        }
    }

    protected void mouseReleased(int x, int y, int button) {
        this.clickedScrollBar = false;
    }

    private void drawSkyResource(int id) {
        CustomSky sky = this.mod.customSkies.get(id);
        if (id == this.mod.currentSkyID) {
            drawRect(
                    this.width / 2 - 100,
                    4 + id * 32 - this.scrollBarAmount,
                    this.width / 2 + 100,
                    36 + id * 32 - this.scrollBarAmount,
                    Color.WHITE.getRGB()
            );
        }

        drawRect(
                this.width / 2 - 99,
                5 + id * 32 - this.scrollBarAmount,
                this.width / 2 + 99,
                35 + id * 32 - this.scrollBarAmount,
                Color.BLACK.getRGB()
        );
        int var10003 = this.width / 2;
        this.drawCenteredString(
                this.mc.fontRendererObj,
                this.mod.customSkies.get(id).skyName,
                var10003,
                15 + id * 32 - this.scrollBarAmount,
                Color.WHITE.getRGB()
        );
    }

    private void scrollBar(int y) {
        if (this.scrollBoxHeight > this.height) {
            if (this.clickedScrollBar) {
                this.scrollBarY = this.scrollBarY + (y - this.lastY);
                if (this.scrollBarY < 0) {
                    this.scrollBarY = 0;
                }

                if (this.scrollBarY + this.scrollBarHeight > this.height) {
                    this.scrollBarY = this.height - this.scrollBarHeight;
                }

                this.scrollBarAmount = this.scrollBarY * this.scrollBoxHeight / this.height;
            }

            this.lastY = y;
            drawRect(this.width / 2 + 100, 0, this.width / 2 + 108, this.height, new Color(0, 0, 0, 170).getRGB());
            drawRect(
                    this.width / 2 + 101, this.scrollBarY, this.width / 2 + 107, this.scrollBarY + this.scrollBarHeight, Color.WHITE.getRGB()
            );
        }
    }
}
