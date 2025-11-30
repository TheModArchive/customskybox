package net.canelex.customsky;

import com.google.common.collect.Lists;
import net.canelex.customsky.gui.GuiScreenListSkies;
import net.canelex.customsky.render.CustomLayer;
import net.canelex.customsky.render.CustomSky;
import net.canelex.customsky.render.SkyRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.util.List;

@Mod(modid = "customskymod", name = "Custom Sky Mod", version = "1.0")
public class CustomSkyMod {
    public final List<CustomSky> customSkies = Lists.newArrayList();
    private final Minecraft mc = Minecraft.getMinecraft();
    public int currentSkyID = 0;
    private final KeyBinding keyOpenSkies = new KeyBinding("Open Skies Gui", 37, "CustomSky");
    private IRenderHandler render;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientRegistry.registerKeyBinding(this.keyOpenSkies);
        List<IResourcePack> resourcePacks = null;
        resourcePacks = ObfuscationReflectionHelper.getPrivateValue(
                Minecraft.class, this.mc, new String[]{"defaultResourcePacks", "field_110449_ao"}
        );
        resourcePacks.add(new SkyResources(new File(this.mc.mcDataDir.getPath())));
        FMLCommonHandler.instance().bus().register(this);
        this.loadSkyResources();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKey() == this.keyOpenSkies.getKeyCode()) {
            this.mc.displayGuiScreen(new GuiScreenListSkies(this));
            this.loadSkyResources();
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mc.theWorld != null && this.mc.theWorld.provider != null && this.mc.theWorld.provider.getSkyRenderer() == null) {
            this.render = this.mc.theWorld.provider.getSkyRenderer();
            this.mc.theWorld.provider.setSkyRenderer(new SkyRenderer(this));
        }
    }

    private void loadSkyResources() {
        this.customSkies.clear();
        this.loadDefaultResource("Default Sky I");
        this.loadDefaultResource("Default Sky II");
        this.loadDefaultResource("Default Sky III");

        for (File skyDir : SkyResources.resourceDir.listFiles()) {
            if (skyDir.isDirectory()) {
                CustomSky customSky = new CustomSky(skyDir.getName());
                if (new File(skyDir, "dawn.png").exists()) {
                    customSky.dawnLayer = new CustomLayer(skyDir.getName() + "/dawn.png");
                    if (new File(skyDir, "day.png").exists()) {
                        customSky.dayLayer = new CustomLayer(skyDir.getName() + "/day.png");
                        if (new File(skyDir, "dusk.png").exists()) {
                            customSky.duskLayer = new CustomLayer(skyDir.getName() + "/dusk.png");
                            if (new File(skyDir, "night.png").exists()) {
                                customSky.nightLayer = new CustomLayer(skyDir.getName() + "/night.png");
                                this.customSkies.add(customSky);
                            }
                        }
                    }
                }
            }
        }
    }

    private void loadDefaultResource(String resource) {
        CustomSky customSky = new CustomSky(resource);
        customSky.dawnLayer = new CustomLayer("customskymod", resource + "/dawn.png");
        customSky.dayLayer = new CustomLayer("customskymod", resource + "/day.png");
        customSky.duskLayer = new CustomLayer("customskymod", resource + "/dusk.png");
        customSky.nightLayer = new CustomLayer("customskymod", resource + "/night.png");
        this.customSkies.add(customSky);
    }
}
