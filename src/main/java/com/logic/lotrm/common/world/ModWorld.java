package com.logic.lotrm.common.world;

import com.logic.lotrm.common.reflection.Reflection;
import cpw.mods.fml.relauncher.ReflectionHelper;
import lotr.client.gui.LOTRMapLabels;
import lotr.common.world.map.LOTRRoads;
import lotr.common.world.map.LOTRWaypoint;

import java.util.ArrayList;

public class ModWorld
{
    private static final int scale = 8;
    public static class WorldUtils
    {
        public static void preInit()
        {
            changeWaypoints();
            changeMapLabelsPosition();
        }
        public static void init()
        {
            ReflectionHelper.setPrivateValue(LOTRRoads.class, null, new ArrayList<LOTRRoads>(), "allRoads");
            Reflection.Roads.clearRoadDataBase();
            setupRoads();
        }
        /*
        * Change the position of the LOTRWaypoints based on the scale
         */
        public static void changeWaypoints()
        {
            for (LOTRWaypoint waypoint : LOTRWaypoint.values())
            {
                double newX = waypoint.getX();
                double newY = waypoint.getY();
                Reflection.MapReflection.changeWaypointCoords(waypoint, newX, newY, scale);
            }
        }
        /*
        * Change the position of the MapLables based on the scale
         */
        public static void changeMapLabelsPosition()
        {
            for(LOTRMapLabels labels: LOTRMapLabels.values())
                Reflection.MapReflection.moveMapLabel(labels, labels.posX, labels.posY, labels.minZoom, labels.maxZoom, labels.scale, scale);
        }
        /*
        * Replace LOTR Roads
         */
        public static void setupRoads()
        {
             Reflection.Roads.registerRoad("EredLuin", LOTRWaypoint.NOGROD, LOTRWaypoint.BELEGOST);
             Reflection.Roads.registerRoad("NogrodForlond", LOTRWaypoint.NOGROD, LOTRWaypoint.FORLOND);
             Reflection.Roads.registerRoad("NogrodMithlond", LOTRWaypoint.NOGROD, new int[]{654, 650}, LOTRWaypoint.MITHLOND_NORTH);
             Reflection.Roads.registerRoad("Mithlond", LOTRWaypoint.HARLOND, new int[]{658, 755}, LOTRWaypoint.MITHLOND_SOUTH, new int[]{690, 711}, new int[]{681, 705}, LOTRWaypoint.MITHLOND_NORTH, new int[]{644, 733}, new int[]{603, 733}, new int[]{554, 715}, LOTRWaypoint.FORLOND);
             Reflection.Roads.registerRoad("WestEast", LOTRWaypoint.MITHLOND_SOUTH, LOTRWaypoint.TOWER_HILLS, LOTRWaypoint.GREENHOLM, LOTRWaypoint.MICHEL_DELVING, LOTRWaypoint.WAYMEET, LOTRWaypoint.BYWATER, LOTRWaypoint.FROGMORTON, LOTRWaypoint.WHITFURROWS, LOTRWaypoint.BRANDYWINE_BRIDGE, new int[]{870, 718}, new int[]{902, 729}, LOTRWaypoint.BREE);
             Reflection.Roads.registerRoad("WestEast", LOTRWaypoint.BREE, new double[]{LOTRWaypoint.BREE.getX() + 0.5, LOTRWaypoint.BREE.getY()});
             Reflection.Roads.registerRoad("WestEast", new double[]{LOTRWaypoint.BREE.getX() + 2.0, LOTRWaypoint.BREE.getY() + 1.5}, new double[]{LOTRWaypoint.STADDLE.getX(), LOTRWaypoint.STADDLE.getY() + 5.0}, LOTRWaypoint.FORSAKEN_INN, new double[]{LOTRWaypoint.WEATHERTOP.getX(), LOTRWaypoint.WEATHERTOP.getY() + 2.0}, LOTRWaypoint.LAST_BRIDGE, new int[]{1132, 723}, new int[]{1178, 704}, LOTRWaypoint.HIGH_PASS, LOTRWaypoint.OLD_FORD, LOTRWaypoint.RIVER_GATE, LOTRWaypoint.DALE_CROSSROADS, LOTRWaypoint.REDWATER_FORD, new int[]{1785, 775}, LOTRWaypoint.RHUN_NORTH_FORD, LOTRWaypoint.RHUN_NORTHEAST, LOTRWaypoint.RHUN_ROAD_WAY, LOTRWaypoint.BARAZ_DUM);
             Reflection.Roads.registerRoad("WestEast", new double[]{LOTRWaypoint.BREE.getX() - 0.375, LOTRWaypoint.BREE.getY() - 2.476}, new double[]{LOTRWaypoint.BREE.getX() + 2.0, LOTRWaypoint.BREE.getY() - 1.5});
             Reflection.Roads.registerRoad("WestEast", new double[]{LOTRWaypoint.BREE.getX() + 0.5, LOTRWaypoint.BREE.getY()}, new double[]{LOTRWaypoint.BREE.getX() + 2.0, LOTRWaypoint.BREE.getY()});
             Reflection.Roads.registerRoad("WestEast", new double[]{LOTRWaypoint.BREE.getX() + 2.0, LOTRWaypoint.BREE.getY() - 1.5}, new double[]{LOTRWaypoint.BREE.getX() + 2.0, LOTRWaypoint.BREE.getY() + 1.5});
             Reflection.Roads.registerRoad("BywaterRoad", LOTRWaypoint.BYWATER, LOTRWaypoint.HOBBITON);
             Reflection.Roads.registerRoad("Overhill", LOTRWaypoint.HOBBITON, LOTRWaypoint.OVERHILL);
             Reflection.Roads.registerRoad("BucklandRoad", LOTRWaypoint.HAY_GATE, LOTRWaypoint.BUCKLEBURY, LOTRWaypoint.HAYSEND);
             Reflection.Roads.registerRoad("Chetroad", new double[]{LOTRWaypoint.STADDLE.getX(), LOTRWaypoint.STADDLE.getY() + 5.0}, LOTRWaypoint.STADDLE, LOTRWaypoint.COMBE, LOTRWaypoint.ARCHET);
             Reflection.Roads.registerRoad("Chetroad", LOTRWaypoint.STADDLE, new double[]{LOTRWaypoint.STADDLE.getX() - 0.5, LOTRWaypoint.STADDLE.getY()});
             Reflection.Roads.registerRoad("Chetroad", LOTRWaypoint.COMBE, new double[]{LOTRWaypoint.COMBE.getX() + 0.5, LOTRWaypoint.COMBE.getY()});
             Reflection.Roads.registerRoad("Chetroad", LOTRWaypoint.ARCHET, new double[]{LOTRWaypoint.ARCHET.getX(), LOTRWaypoint.ARCHET.getY() - 0.5});
             Reflection.Roads.registerRoad("ElfPath", LOTRWaypoint.FOREST_GATE, LOTRWaypoint.ENCHANTED_RIVER, LOTRWaypoint.THRANDUIL_HALLS);
             Reflection.Roads.registerRoad("EreborRoad", LOTRWaypoint.LONG_LAKE, LOTRWaypoint.DALE_CITY, LOTRWaypoint.EREBOR);
             Reflection.Roads.registerRoad("DalePortRoad", LOTRWaypoint.DALE_CITY, LOTRWaypoint.DALE_CROSSROADS, LOTRWaypoint.DALE_PORT);
             Reflection.Roads.registerRoad("DaleSouthRoad", LOTRWaypoint.EAST_RHOVANION_ROAD, LOTRWaypoint.OLD_RHOVANION, LOTRWaypoint.RUNNING_FORD, LOTRWaypoint.DALE_CROSSROADS, LOTRWaypoint.WEST_PEAK);
             Reflection.Roads.registerRoad("IronHills", LOTRWaypoint.WEST_PEAK, new int[]{1652, 621}, LOTRWaypoint.EAST_PEAK);
             Reflection.Roads.registerRoad("DorwinionSouthRoad", LOTRWaypoint.DALE_PORT, LOTRWaypoint.DORWINION_CROSSROADS, LOTRWaypoint.DORWINION_COURT, LOTRWaypoint.DORWINION_FORD);
             Reflection.Roads.registerRoad("DorwinionEastRoad", LOTRWaypoint.OLD_RHOVANION, LOTRWaypoint.DORWINION_CROSSROADS, LOTRWaypoint.DORWINION_PORT);
             Reflection.Roads.registerRoad("RhunRoad", LOTRWaypoint.DORWINION_FORD, LOTRWaypoint.BORDER_TOWN, LOTRWaypoint.RHUN_SEA_CITY, LOTRWaypoint.RHUN_CAPITAL, new int[]{1888, 958}, LOTRWaypoint.RHUN_NORTH_CITY, LOTRWaypoint.BAZYLAN, LOTRWaypoint.RHUN_NORTHEAST);
             Reflection.Roads.registerRoad("RhunEastRoad", LOTRWaypoint.RHUN_NORTH_CITY, LOTRWaypoint.RHUN_EAST_TOWN, LOTRWaypoint.RHUN_EAST_CITY);
             Reflection.Roads.registerRoad("Nobottle", LOTRWaypoint.TIGHFIELD, LOTRWaypoint.LITTLE_DELVING, LOTRWaypoint.NOBOTTLE, LOTRWaypoint.NEEDLEHOLE);
             Reflection.Roads.registerRoad("Oatbarton", LOTRWaypoint.OATBARTON, LOTRWaypoint.FROGMORTON);
             Reflection.Roads.registerRoad("Stock", LOTRWaypoint.TUCKBOROUGH, LOTRWaypoint.STOCK);
             Reflection.Roads.registerRoad("Deephallow", LOTRWaypoint.SCARY, LOTRWaypoint.WHITFURROWS, LOTRWaypoint.STOCK, LOTRWaypoint.DEEPHALLOW);
             Reflection.Roads.registerRoad("Willowbottom", LOTRWaypoint.WILLOWBOTTOM, LOTRWaypoint.DEEPHALLOW);
             Reflection.Roads.registerRoad("ArnorRoad", LOTRWaypoint.ANNUMINAS, LOTRWaypoint.FORNOST);
             Reflection.Roads.registerRoad("Greenway", LOTRWaypoint.FORNOST, LOTRWaypoint.BREE, LOTRWaypoint.GREENWAY_CROSSROADS);
             Reflection.Roads.registerRoad("ElvenWay", LOTRWaypoint.WEST_GATE, new int[]{1133, 867}, new int[]{1124, 868}, LOTRWaypoint.OST_IN_EDHIL, new int[]{1073, 864}, LOTRWaypoint.OLD_ELF_WAY, new int[]{1002, 849}, new int[]{992, 860}, LOTRWaypoint.THARBAD, new int[]{959, 889}, new int[]{926, 913}, new int[]{902, 942}, LOTRWaypoint.LOND_DAER);
             Reflection.Roads.registerRoad("BruinenPath", LOTRWaypoint.FORD_BRUINEN, LOTRWaypoint.RIVENDELL);
             Reflection.Roads.registerRoad("NimrodelRoad", LOTRWaypoint.DIMRILL_DALE, LOTRWaypoint.NIMRODEL);
             Reflection.Roads.registerRoad("AnduinRoad", LOTRWaypoint.MORANNON, new int[]{1428, 1066}, LOTRWaypoint.EAST_RHOVANION_ROAD, LOTRWaypoint.ANDUIN_CROSSROADS, new int[]{1325, 820}, new int[]{1318, 735}, LOTRWaypoint.FOREST_GATE);
             Reflection.Roads.registerRoad("DolGuldurRoad", LOTRWaypoint.ANDUIN_CROSSROADS, LOTRWaypoint.DOL_GULDUR);
             Reflection.Roads.registerRoad("Framsburg", LOTRWaypoint.FOREST_GATE, new int[]{1278, 605}, LOTRWaypoint.FRAMSBURG, new int[]{1260, 565}, LOTRWaypoint.DAINS_HALLS);
             Reflection.Roads.registerRoad("NorthSouth", LOTRWaypoint.LITTLE_DELVING, LOTRWaypoint.WAYMEET, LOTRWaypoint.LONGBOTTOM, LOTRWaypoint.SARN_FORD, LOTRWaypoint.GREENWAY_CROSSROADS, LOTRWaypoint.THARBAD, LOTRWaypoint.ENEDWAITH_ROAD, LOTRWaypoint.FORDS_OF_ISEN, LOTRWaypoint.HELMS_CROSSROADS, LOTRWaypoint.GRIMSLADE, LOTRWaypoint.EDORAS, LOTRWaypoint.ALDBURG, LOTRWaypoint.MERING_STREAM, LOTRWaypoint.AMON_DIN);
             Reflection.Roads.registerRoad("TirithRoad", LOTRWaypoint.AMON_DIN, LOTRWaypoint.MINAS_TIRITH);
             Reflection.Roads.registerRoad("OsgiliathRoad", LOTRWaypoint.MINAS_TIRITH, LOTRWaypoint.OSGILIATH_WEST);
             Reflection.Roads.registerRoad("OsgiliathCrossing", LOTRWaypoint.OSGILIATH_WEST, LOTRWaypoint.OSGILIATH_EAST);
             Reflection.Roads.registerRoad("OsgiliathMorgulRoad", LOTRWaypoint.OSGILIATH_EAST, LOTRWaypoint.CROSSROADS_ITHILIEN, LOTRWaypoint.MINAS_MORGUL);
             Reflection.Roads.registerRoad("GondorSouthRoad", LOTRWaypoint.MINAS_TIRITH, LOTRWaypoint.CROSSINGS_ERUI, new int[]{1408, 1291}, LOTRWaypoint.PELARGIR, LOTRWaypoint.LINHIR, new int[]{1266, 1301}, LOTRWaypoint.ETHRING, LOTRWaypoint.CALEMBEL, LOTRWaypoint.TARLANG, LOTRWaypoint.ERECH);
             Reflection.Roads.registerRoad("IsengardRoad", LOTRWaypoint.FORDS_OF_ISEN, LOTRWaypoint.ISENGARD);
             Reflection.Roads.registerRoad("IsengardRoad", LOTRWaypoint.ISENGARD, new double[]{LOTRWaypoint.ISENGARD.getX(), LOTRWaypoint.ISENGARD.getY() - 3.5});
             Reflection.Roads.registerRoad("HelmRoad", LOTRWaypoint.HELMS_CROSSROADS, LOTRWaypoint.HELMS_DEEP);
             Reflection.Roads.registerRoad("WoldRoad", LOTRWaypoint.EDORAS, LOTRWaypoint.ENTWADE, new int[]{1260, 1060}, LOTRWaypoint.WOLD);
             Reflection.Roads.registerRoad("DolAmroth", new int[]{1266, 1301}, LOTRWaypoint.TARNOST, LOTRWaypoint.EDHELLOND, new int[]{1185, 1325}, LOTRWaypoint.DOL_AMROTH);
             Reflection.Roads.registerRoad("Pelargir", LOTRWaypoint.PELARGIR, new int[]{1394, 1352});
             Reflection.Roads.registerRoad("Poros", new int[]{1397, 1355}, LOTRWaypoint.CROSSINGS_OF_POROS);
             Reflection.Roads.registerRoad("CairAndros", LOTRWaypoint.AMON_DIN, LOTRWaypoint.CAIR_ANDROS, LOTRWaypoint.NORTH_ITHILIEN);
             Reflection.Roads.registerRoad("SauronRoad", LOTRWaypoint.MINAS_MORGUL, LOTRWaypoint.MOUNT_DOOM, LOTRWaypoint.BARAD_DUR, LOTRWaypoint.SEREGOST, new int[]{1742, 1209}, new int[]{1809, 1172}, LOTRWaypoint.EASTERN_GUARD, LOTRWaypoint.MORDOR_FORD, LOTRWaypoint.RHUN_SOUTH_PASS, new int[]{1875, 1003}, new int[]{1867, 996}, LOTRWaypoint.RHUN_CAPITAL);
             Reflection.Roads.registerRoad("MorannonRoad", LOTRWaypoint.MORANNON, LOTRWaypoint.UDUN);
             Reflection.Roads.registerRoad("MorannonRhunRoad", LOTRWaypoint.MORANNON, new int[]{1520, 1130}, new int[]{1658, 1140}, new int[]{1780, 1115}, LOTRWaypoint.MORDOR_FORD, LOTRWaypoint.RHUN_SOUTHEAST, LOTRWaypoint.KHAND_NORTH_ROAD, LOTRWaypoint.KHAND_FORD, LOTRWaypoint.HARNEN_BLACK_TOWN, LOTRWaypoint.CROSSINGS_OF_LITHNEN, LOTRWaypoint.HARNEN_ROAD_TOWN, LOTRWaypoint.HARNEN_RIVER_TOWN, LOTRWaypoint.HARNEN_SEA_TOWN, LOTRWaypoint.COAST_FORTRESS, LOTRWaypoint.GATE_FUINUR, LOTRWaypoint.UMBAR_CITY, LOTRWaypoint.GATE_HERUMOR);
             Reflection.Roads.registerRoad("GorgorothRoad", LOTRWaypoint.UDUN, LOTRWaypoint.CARACH_ANGREN, LOTRWaypoint.BARAD_DUR, LOTRWaypoint.THAURBAND);
             Reflection.Roads.registerRoad("HaradRoad", LOTRWaypoint.MORANNON, LOTRWaypoint.NORTH_ITHILIEN, LOTRWaypoint.CROSSROADS_ITHILIEN, LOTRWaypoint.CROSSINGS_OF_POROS, new int[]{1429, 1394}, new int[]{1408, 1432}, new int[]{1428, 1470}, new int[]{1435, 1526}, LOTRWaypoint.CROSSINGS_OF_HARAD, LOTRWaypoint.HARNEN_ROAD_TOWN, LOTRWaypoint.DESERT_TOWN);
             Reflection.Roads.registerRoad("UmbarRoad", LOTRWaypoint.UMBAR_CITY, LOTRWaypoint.UMBAR_GATE, LOTRWaypoint.AIN_AL_HARAD, LOTRWaypoint.GARDENS_BERUTHIEL, LOTRWaypoint.FERTILE_VALLEY, LOTRWaypoint.SOUTH_DESERT_TOWN);
             Reflection.Roads.registerRoad("GulfRoad", LOTRWaypoint.TOWN_BONES, new int[]{1794, 2110}, LOTRWaypoint.GULF_FORD, LOTRWaypoint.GULF_TRADE_TOWN, LOTRWaypoint.GULF_CITY, LOTRWaypoint.GULF_NORTH_TOWN, new int[]{1702, 1940}, LOTRWaypoint.GULF_OF_HARAD, new int[]{1775, 2002}, LOTRWaypoint.GULF_EAST_PORT);
             Reflection.Roads.registerRoad("JungleNorthRoad", LOTRWaypoint.JUNGLE_CITY_TRADE, LOTRWaypoint.JUNGLE_CITY_OLD, LOTRWaypoint.JUNGLE_CITY_NORTH);
             Reflection.Roads.registerRoad("JungleMangroveRoad", LOTRWaypoint.JUNGLE_CITY_NORTH, LOTRWaypoint.JUNGLE_CITY_EAST, LOTRWaypoint.HARADUIN_MOUTH);
             Reflection.Roads.registerRoad("JungleDeepRoad", LOTRWaypoint.JUNGLE_CITY_NORTH, LOTRWaypoint.JUNGLE_CITY_CAPITAL, LOTRWaypoint.JUNGLE_CITY_CAVES, LOTRWaypoint.JUNGLE_CITY_DEEP);
             Reflection.Roads.registerRoad("JungleWestEastRoad", LOTRWaypoint.JUNGLE_CITY_OLD, LOTRWaypoint.JUNGLE_CITY_STONE, LOTRWaypoint.JUNGLE_CITY_CAPITAL, LOTRWaypoint.JUNGLE_LAKES, LOTRWaypoint.JUNGLE_CITY_WATCH);
             Reflection.Roads.registerRoad("JungleLakeRoad", LOTRWaypoint.JUNGLE_LAKES, LOTRWaypoint.JUNGLE_CITY_EAST, LOTRWaypoint.HARADUIN_BRIDGE, LOTRWaypoint.OLD_JUNGLE_RUIN);
        }
    }
}
