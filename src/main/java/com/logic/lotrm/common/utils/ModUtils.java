package com.logic.lotrm.common.utils;

import com.logic.lotrm.LOTRManHunt;
import com.logic.lotrm.common.reflection.Reflection;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import lotr.client.LOTRTextures;
import lotr.common.LOTRDimension;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModUtils
{
    public static String mapPath;

    /**
     * Sets the generated map for the mod.
     *
     * @param modID The ID of the mod.
     * @param dimensions The dimensions of the generated map.
     */
    public static void setGenMap(String modID, LOTRDimension dimensions) {
        try {
            BufferedImage biomeImage = null;
            String imageName = "assets/" + modID + "/" + mapPath;
            ModContainer mc = FMLCommonHandler.instance().findContainerFor(LOTRManHunt.instance);
            if (!mc.getSource().isFile()) {
                File file = new File(LOTRManHunt.class.getResource("/" + imageName).toURI());
                biomeImage = ImageIO.read(new FileInputStream(file));
            } else {
                ZipFile zip = new ZipFile(mc.getSource());
                Enumeration<? extends ZipEntry> entries = zip.entries();

                while(entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    if (entry.getName().equals(imageName)) {
                        biomeImage = ImageIO.read(zip.getInputStream(entry));
                    }
                }

                zip.close();
            }

            if (biomeImage == null) {
                throw new RuntimeException("Could not load LOTRManHunt biome map image");
            }

            // Set image dimensions
            LOTRGenLayerWorld.imageWidth = biomeImage.getWidth();
            LOTRGenLayerWorld.imageHeight = biomeImage.getHeight();

            // Process image colors
            int[] colors = biomeImage.getRGB(0, 0, LOTRGenLayerWorld.imageWidth, LOTRGenLayerWorld.imageHeight, null, 0, LOTRGenLayerWorld.imageWidth);
            byte[] biomeImageData = new byte[LOTRGenLayerWorld.imageWidth * LOTRGenLayerWorld.imageHeight];

            for(int i = 0; i < colors.length; ++i) {
                int color = colors[i];
                Integer biomeID = dimensions.colorsToBiomeIDs.get(color);
                if (biomeID != null) {
                    biomeImageData[i] = biomeID.byteValue();
                } else {
                    FMLLog.log(Level.ERROR, "Found unknown biome on map " + Integer.toHexString(color) + " at " + (i % LOTRGenLayerWorld.imageWidth) + ", " + (i / LOTRGenLayerWorld.imageWidth));
                    biomeImageData[i] = (byte) LOTRBiome.ocean.biomeID;
                }
            }

            // Set biome image data
            Field biomeImageDataField = LOTRGenLayerWorld.class.getDeclaredField("biomeImageData");
            biomeImageDataField.setAccessible(true);
            biomeImageDataField.set(null, biomeImageData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the client map for the mod.
     */
    public static void setClientMap() {
        try {
            Field mapTextureField = LOTRTextures.class.getDeclaredField("mapTexture");
            Field sepiaMapTextureField = LOTRTextures.class.getDeclaredField("sepiaMapTexture");
            mapTextureField.setAccessible(true);
            sepiaMapTextureField.setAccessible(true);

            ResourceLocation mapTexture = new ResourceLocation(LOTRManHunt.MODID, mapPath);
            mapTextureField.set(null, mapTexture);

            ResourceLocation sepiaMapTexture;

            Method convertToSepiaMethod = LOTRTextures.class.getDeclaredMethod("convertToSepia", BufferedImage.class, ResourceLocation.class);
            convertToSepiaMethod.setAccessible(true);

            try {
                BufferedImage mapImage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(mapTexture).getInputStream());
                sepiaMapTexture = (ResourceLocation) convertToSepiaMethod.invoke(null, mapImage, new ResourceLocation("lotr:map_sepia"));
            } catch (IOException e) {
                FMLLog.severe("Failed to generate LOTR sepia map");
                e.printStackTrace();
                sepiaMapTexture = mapTexture;
            }
            sepiaMapTextureField.set(null, sepiaMapTexture);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeFactionMapRegions()
    {
        for(LOTRFaction fac: LOTRFaction.values())
            Reflection.FactionReflection.moveLOTRMapRegion(fac, fac.factionMapInfo, 8);
    }
}
