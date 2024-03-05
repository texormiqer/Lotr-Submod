package com.logic.lotrm.common.reflection;

import cpw.mods.fml.relauncher.ReflectionHelper;
import lotr.client.gui.LOTRMapLabels;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRank;
import lotr.common.fac.LOTRFactionRelations;
import lotr.common.fac.LOTRMapRegion;
import lotr.common.world.map.LOTRRoads;
import lotr.common.world.map.LOTRWaypoint;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection
{
    /*
    *
    * Reflection regarding the map: Waypoints, Maplabels, Faction Map Region, Roads
    *
     */
    public static class MapReflection
    {
        /*
        * Changes the private coord field of the waypoint and adds, or remove, an amount of pixels
         */
        public static void changeWaypointCoords(LOTRWaypoint wp, double x, double y, int downSizeScale)
        {
            ReflectionHelper.setPrivateValue(LOTRWaypoint.class, wp, x/=downSizeScale, "imgX");
            ReflectionHelper.setPrivateValue(LOTRWaypoint.class, wp, y/=downSizeScale, "imgY");
            ReflectionHelper.setPrivateValue(LOTRWaypoint.class, wp, LOTRWaypoint.mapToWorldX(x), "xCoord");
            ReflectionHelper.setPrivateValue(LOTRWaypoint.class, wp, LOTRWaypoint.mapToWorldZ(y), "zCoord");
        }

        /*
        * Changes the Faction of a LOTRWaypoint
         */
        public static void changeWaypointFaction(LOTRWaypoint wp, LOTRFaction faction)
        {
            wp.faction = faction;
        }

        /*
        * Remove a LOTRWaypoint
         */
        public static void removeWaypoint(LOTRWaypoint wp)
        {
            wp.faction = LOTRFaction.HOSTILE;
            ReflectionHelper.setPrivateValue(LOTRWaypoint.class, wp, true, "isHidden");
        }

        /*
        * Change the position of a road based on a downsizing scale
         */

        public static void changeRoadPoistion(LOTRMapLabels mapLabels, int downSizeScale)
        {

        }
        /*
        * Moves the position of a map label based on a scale
         */
        public static void moveMapLabel(LOTRMapLabels labels, int x, int y, float minZoom, float maxZoom, float scale, int downSizeScale)
        {
            ReflectionHelper.setPrivateValue(LOTRMapLabels.class, labels, x/=downSizeScale , "posX");
            ReflectionHelper.setPrivateValue(LOTRMapLabels.class, labels, y/=downSizeScale , "posY");
            //ReflectionHelper.setPrivateValue(LOTRMapLabels.class, labels, minZoom/=downSizeScale , "minZoom");
            //ReflectionHelper.setPrivateValue(LOTRMapLabels.class, labels, maxZoom/=downSizeScale , "maxZoom");
            ReflectionHelper.setPrivateValue(LOTRMapLabels.class, labels, scale/=downSizeScale , "scale");
        }

    }
    public static class FactionReflection
    {
        /*
         * Moves the mapRegion of a Faction based on a scale
         */
        public static void moveLOTRMapRegion(LOTRFaction faction, LOTRMapRegion lotrMapRegion, int scale)
        {
            ReflectionHelper.setPrivateValue(LOTRFaction.class, faction, new LOTRMapRegion(lotrMapRegion.mapX/scale, lotrMapRegion.mapY/scale, lotrMapRegion.radius/scale), "factionMapInfo");
        }
    }
    /*
     * Clears road database
     */
    public static class Roads
    {
        public static void clearRoadDataBase()
        {
            Object dataBase = ReflectionUtils.findAndInvokeConstructor("lotr.common.world.map.LOTRRoads$RoadPointDatabase");
            ReflectionHelper.setPrivateValue(LOTRRoads.class, null, dataBase, "roadPointDatabase");
        }
        public static void registerRoad(String name, Object... waypoints)
        {
            Reflection.ReflectionUtils.findAndInvokeMethod(new Object[] {name, waypoints}, LOTRRoads.class, null, "registerRoad", String.class, Object[].class);
        }
    }
    /*
    * Util methods for Reflection methods
     */
    public static class ReflectionUtils
    {
        public static Object findAndInvokeConstructor(String className, Class<?>... parameterTypes)
        {
            return findAndInvokeConstructor(new Object[] {}, className, parameterTypes);
        }

        public static Object findAndInvokeConstructor(Object[] args, String className, Class<?>... parameterTypes)
        {
            try {
                return findAndInvokeConstructor(args, (Class<? super Object>) Class.forName(className), parameterTypes);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static <E> E findAndInvokeConstructor(Object[] args, Class<E> clazz, Class<?>... parameterTypes)
        {
            Constructor<E> constructor = findConstructor(clazz, parameterTypes);
            assert constructor != null;
            constructor.setAccessible(true);
            try {
                return constructor.newInstance(args);
            } catch (InstantiationException e)
            {
                e.printStackTrace();
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            } catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            } catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
            return null;
        }
        public static <E> Constructor<E> findConstructor(Class<E> clazz, Class<?>... parameterTypes)
        {
            try {
                return clazz.getDeclaredConstructor(parameterTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            return null;
        }
        public static <T, E> T findAndInvokeMethod(Class<? super E> clazz, E instance, String methodName)
        {
            return (T) findAndInvokeMethod(new Object[0], clazz, instance, methodName);
        }
        public static <E> void findAndInvokeMethod(Object arg, Class<? super E> clazz, E instance, String methodName, Class<?>... methodTypes)
        {
            findAndInvokeMethod(new Object[] {arg}, clazz, instance, methodName, methodTypes);
        }

        public static <E> LOTRFactionRank findAndInvokeMethod(Object[] args, Class<? super E> clazz, E instance, String methodName, Class<?>... methodTypes)
        {
            Method addControlZoneMethod = findMethod(clazz, instance, methodName, methodTypes);
            try {
                addControlZoneMethod.invoke(instance, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
        public static <E> Method findMethod(Class<? super E> clazz, E instance, String methodName, Class<?>... methodTypes)
        {
            return ReflectionHelper.findMethod(clazz, instance, new String[] {methodName}, methodTypes);
        }
    }
}
