/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.world.GrassColors;
import net.minecraft.world.WorldType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.objects.blocks.TFCBlocks;
import net.dries007.tfc.objects.blocks.soil.SoilBlockType;
import net.dries007.tfc.objects.blocks.soil.TFCGrassBlock;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler
{
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onInitGuiPre(GuiScreenEvent.InitGuiEvent.Pre event)
    {

        LOGGER.debug("Init Gui Pre Event");
        if (event.getGui() instanceof CreateWorldScreen)
        {
            // Only change if default is selected, because coming back from customisation, this will be set already.
            CreateWorldScreen gui = ((CreateWorldScreen) event.getGui());
            Integer selectedIndex = ObfuscationReflectionHelper.getPrivateValue(CreateWorldScreen.class, gui, "field_146331_K");
            if (selectedIndex != null && selectedIndex == WorldType.DEFAULT.getId())
            {
                LOGGER.debug("Setting Selected World Type to TFC Default");
                ObfuscationReflectionHelper.setPrivateValue(CreateWorldScreen.class, gui, TerraFirmaCraft.getWorldType().getId(), "field_146331_K");
            }
        }
    }

    @SubscribeEvent
    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event)
    {
        LOGGER.debug("register Color Handler for Blocks event");

        BlockColors blockColors = event.getBlockColors();

        IBlockColor grassColor = (state, worldIn, pos, tintIndex) -> {
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
}
