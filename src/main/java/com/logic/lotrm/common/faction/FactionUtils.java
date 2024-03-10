package com.logic.lotrm.common.faction;

import com.logic.lotrm.common.reflection.Reflection;
import cpw.mods.fml.relauncher.ReflectionHelper;
import lotr.common.fac.LOTRControlZone;
import lotr.common.fac.LOTRFaction;

import java.lang.reflect.Field;

public class FactionUtils
{
    public static void init()
    {
        changeInfluenceZones();
        //changeFactionMapRegions();
    }
    /*
    * Method to change influence zones
    * radiusField is a final field, so we use Reflection to access it
     */
    public static void changeInfluenceZones()
    {
        for (LOTRFaction faction : LOTRFaction.values())
            for (LOTRControlZone lotrControlZone : faction.getControlZones())
            {
                int currentRadius = ReflectionHelper.getPrivateValue(LOTRControlZone.class, lotrControlZone, "radius");
                ReflectionHelper.setPrivateValue(LOTRControlZone.class, lotrControlZone, currentRadius / 12, "radius");
            }
    }
    /*
    * changeFactionMapRegions divides the map regions by 8
     */
    public static void changeFactionMapRegions()
    {
        for(LOTRFaction fac: LOTRFaction.values())
            Reflection.FactionReflection.moveLOTRMapRegion(fac, fac.factionMapInfo, 8);
    }
}
