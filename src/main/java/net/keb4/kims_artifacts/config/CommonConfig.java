package net.keb4.kims_artifacts.config;

import net.keb4.kims_artifacts.Main;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.xml.sax.SAXParseException;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    //add usable value
    public static boolean resonanceEnabled = true;
    public static double smrRange = 100.0D;


    //add forge recognition in the config file
    private static final ForgeConfigSpec.BooleanValue RESONANCE_ENABLED = BUILDER
            .comment("Disabling this disables power imbalances with artifacts among players. All players can equally use all artifacts")
            .define("resonanceEnabled", true);

    private static final ForgeConfigSpec.DoubleValue SMR_RANGE = BUILDER
            .comment("SMR Artifact Raycast Range")
            .defineInRange("smrRange", 100.0D, 0D, 500D);



    //register forge value in configs
    static ForgeConfigSpec SPEC = BUILDER.build();

    //"bake" config file value into memory
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        resonanceEnabled = RESONANCE_ENABLED.get();
        smrRange = SMR_RANGE.get();
    }

    public static void register(FMLJavaModLoadingContext context)
    {
        context.registerConfig(ModConfig.Type.COMMON, SPEC);
    }
}
