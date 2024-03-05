package com.logic.lotrm.common.utils;

import com.logic.lotrm.LOTRManHunt;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import lotr.client.LOTRTextures;
import lotr.common.LOTRDimension;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModUtils
{
    public static String mapPath;

    public static void setGenMap()
    {
        try {
            BufferedImage biomeImage = null;
            String imageName = "assets/"+ LOTRManHunt.MODID+"/"+mapPath;
            ModContainer mc = FMLCommonHandler.instance().findContainerFor(LOTRManHunt.instance);
            if (!mc.getSource().isFile()) {
                File file = new File(LOTRManHunt.class.getResource("/" + imageName).toURI());
                biomeImage = ImageIO.read(new FileInputStream(file));
            } else {
                ZipFile zip = new ZipFile(mc.getSource());
                Enumeration<? extends ZipEntry> entries = zip.entries();

                while(entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry)entries.nextElement();
                    if (entry.getName().equals(imageName)) {
                        biomeImage = ImageIO.read(zip.getInputStream(entry));
                    }
                }

                zip.close();
            }

            if (biomeImage == null) {
                throw new RuntimeException("Could not load LOTRManHunt biome map image");
            }

            LOTRGenLayerWorld.imageWidth = biomeImage.getWidth();
            LOTRGenLayerWorld.imageHeight = biomeImage.getHeight();
            int[] colors = biomeImage.getRGB(0, 0, LOTRGenLayerWorld.imageWidth, LOTRGenLayerWorld.imageHeight, null, 0, LOTRGenLayerWorld.imageWidth);
            byte[] biomeImageData = new byte[LOTRGenLayerWorld.imageWidth * LOTRGenLayerWorld.imageHeight];

            for(int i = 0; i < colors.length; ++i) {
                int color = colors[i];
                Integer biomeID = LOTRDimension.MIDDLE_EARTH.colorsToBiomeIDs.get(color);
                if (biomeID != null) {
                    biomeImageData[i] = biomeID.byteValue();
                } else {
                    FMLLog.log(Level.ERROR, "Found unknown biome on map " + Integer.toHexString(color)+" at "+ (i % LOTRGenLayerWorld.imageWidth)+ ", " + (i/LOTRGenLayerWorld.imageWidth));
                    biomeImageData[i] = (byte) LOTRBiome.ocean.biomeID;
                }
            }
            Field bid = LOTRGenLayerWorld.class.getDeclaredField("biomeImageData");
            bid.setAccessible(true);
            bid.set(null, biomeImageData);
        } catch (Exception var8) {
            var8.printStackTrace();
        }
    }

    public static void setClientMap()
    {
        try {
            Field mt = LOTRTextures.class.getDeclaredField("mapTexture");
            Field smt = LOTRTextures.class.getDeclaredField("sepiaMapTexture");
            mt.setAccessible(true);
            smt.setAccessible(true);

            ResourceLocation mapTexture = new ResourceLocation(LOTRManHunt.MODID, mapPath);
            mt.set(null, mapTexture);
            System.out.println("Feanor");

            ResourceLocation sepiaMapTexture;

            Method c2s = LOTRTextures.class.getDeclaredMethod("convertToSepia", BufferedImage.class, ResourceLocation.class);
            c2s.setAccessible(true);

            try {
                BufferedImage mapImage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(mapTexture).getInputStream());
                sepiaMapTexture = (ResourceLocation) c2s.invoke(null, mapImage, new ResourceLocation("lotr:map_sepia"));
            } catch (IOException var1) {
                FMLLog.severe("Failed to generate LOTR sepia map");
                var1.printStackTrace();
                sepiaMapTexture = mapTexture;
            }
            smt.set(null, sepiaMapTexture);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
