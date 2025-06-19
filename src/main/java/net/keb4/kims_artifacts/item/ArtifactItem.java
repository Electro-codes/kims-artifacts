package net.keb4.kims_artifacts.item;

import net.keb4.kims_artifacts.util.MythicalRarity;
import net.minecraft.world.item.Item;

/**
 * @author Electro
 * @apiNote Generic Artifact item. Use {@link CurioArtifactItem} for Curio Artifact Items
 * **/
public class ArtifactItem extends Item implements IArtifact {
    public ArtifactItem() {
        super(new Item.Properties().stacksTo(1).rarity(MythicalRarity.MYTHICAL).fireResistant());
    }

    @Override
    public float getDefaultResonance() {
        return 0.5f;
    }
}
