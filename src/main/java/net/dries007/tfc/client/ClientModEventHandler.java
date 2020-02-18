package net.dries007.tfc.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.GrassColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import net.dries007.tfc.objects.blocks.TFCBlocks;
import net.dries007.tfc.objects.blocks.soil.SoilBlockType;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEventHandler
{
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event)
    {
        LOGGER.debug("register Color Handler for Blocks event");

        BlockColors blockColors = event.getBlockColors();

        IBlockColor grassColor = (state, worldIn, pos, tintIndex) -> {
            LOGGER.debug("called grass color delegate");
            if (pos != null)
            {
                double temp = 0.5; //MathHelper.clamp((ClimateTFC.getMonthlyTemp(pos) + 30) / 60, 0, 1);
                double rain = 0.5; //MathHelper.clamp((ClimateTFC.getRainfall(pos) - 50) / 400, 0, 1);
                return GrassColors.get(temp, rain);
            }
            return GrassColors.get(0.5, 0.5);
        };

        blockColors.register(grassColor, TFCBlocks.SOIL.get(SoilBlockType.GRASS).values().stream().map(RegistryObject::get).toArray(Block[]::new));
        blockColors.register(grassColor, TFCBlocks.SOIL.get(SoilBlockType.DRY_GRASS).values().stream().map(RegistryObject::get).toArray(Block[]::new));
    }

    @SubscribeEvent
    public static void registerColorHandlerItems(ColorHandlerEvent.Item event)
    {
        LOGGER.debug("register Color Handler for Items event");

        ItemColors itemColors = event.getItemColors();

        IItemColor grassColor = (stack, tintIndex) -> event.getBlockColors().getColor(((BlockItem) stack.getItem()).getBlock().getDefaultState(), null, null, tintIndex);

        itemColors.register(grassColor, TFCBlocks.SOIL.get(SoilBlockType.GRASS).values().stream().map(RegistryObject::get).toArray(Block[]::new));
        itemColors.register(grassColor, TFCBlocks.SOIL.get(SoilBlockType.DRY_GRASS).values().stream().map(RegistryObject::get).toArray(Block[]::new));
    }
}
