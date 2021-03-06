package net.dries007.tfc.objects.potioneffects;


import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static net.dries007.tfc.api.util.TFCConstants.MOD_ID;
import static net.dries007.tfc.util.Helpers.getNull;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
@GameRegistry.ObjectHolder(MOD_ID)
public final class PotionEffectsTFC
{
    public static final Potion OVERBURDENED = getNull();

    @SubscribeEvent
    public static void registerPotionEffects(RegistryEvent.Register<Potion> event)
    {
        event.getRegistry().registerAll(
            new PotionOverburdened().setRegistryName(MOD_ID, "overburdened")
        );
    }
}
