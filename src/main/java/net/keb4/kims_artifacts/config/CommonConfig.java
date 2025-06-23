package net.keb4.kims_artifacts.config;

import net.keb4.kims_artifacts.Main;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    //add usable value
    public static double smrRange = 100.0D;

    public static int potionBagBrewTime = 200; // 10 seconds

    public static int potionSyncPeriod = potionBagBrewTime/10;


    private static final ForgeConfigSpec.DoubleValue SMR_RANGE = BUILDER
            .comment("SMR Artifact Raycast Range")
            .defineInRange("smrRange", 100.0D, 0D, 500D);

    private static final ForgeConfigSpec.IntValue POTION_BAG_BREW_TIME = BUILDER
            .comment("Potion Bag Brew Time")
            .defineInRange("potionBagBrewTime", 200, 10, Integer.MAX_VALUE);




    //register forge value in configs
    static ForgeConfigSpec SPEC = BUILDER.build();

    //"bake" config file value into memory
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        smrRange = SMR_RANGE.get();
        potionBagBrewTime = POTION_BAG_BREW_TIME.get();
    }

    public static void register(FMLJavaModLoadingContext context)
    {
        context.registerConfig(ModConfig.Type.COMMON, SPEC);
    }
}
