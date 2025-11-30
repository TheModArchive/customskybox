package net.canelex.customsky;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Set;

public class SkyResources implements IResourcePack {
    public static File resourceDir;

    public SkyResources(File mcDataDir) {
        resourceDir = new File(mcDataDir, "skyresources");
        if (!resourceDir.exists()) {
            resourceDir.mkdir();
        }
    }

    public InputStream getInputStream(ResourceLocation resource) {
        File resourceFile = new File(resourceDir, resource.getResourcePath());
        if (resourceFile.exists()) {
            try {
                return Files.newInputStream(resourceFile.toPath());
            } catch (IOException ignored) {
            }
        }
        return null;
    }

    @Override
    public boolean resourceExists(ResourceLocation resource) {
        return resource.getResourcePath().contains(".png");
    }

    @Override
    public Set getResourceDomains() {
        return ImmutableSet.of("skyresources");
    }

    @Override
    public IMetadataSection getPackMetadata(IMetadataSerializer meta, String par2) {
        return null;
    }

    @Override
    public BufferedImage getPackImage() {
        return null;
    }

    @Override
    public String getPackName() {
        return "SkyResourcePack";
    }
}
