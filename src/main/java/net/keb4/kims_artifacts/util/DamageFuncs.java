package net.keb4.kims_artifacts.util;

/**
 * @apiNote A collection of damage calculation functions.
 * **/
public class DamageFuncs {

    /**
     * @apiNote Yields damage percentages based on integer numbers. Very compatible with enchantments.
     * **/
    public static class Point {

        /**
         * Yields ~77% damage reduction at 16 points.
         * **/
        public static float funcDefault(int in)
        {
            return (float) (1/((0.2 * in) + 1));
        }

    }
}
