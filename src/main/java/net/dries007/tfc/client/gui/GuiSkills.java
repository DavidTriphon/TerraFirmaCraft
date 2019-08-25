/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.skill.CapabilityPlayerSkills;
import net.dries007.tfc.api.capability.skill.IPlayerSkills;
import net.dries007.tfc.api.capability.skill.Skill;
import net.dries007.tfc.api.capability.skill.SkillType;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.client.button.GuiButtonPlayerInventoryTab;
import net.dries007.tfc.network.PacketSwitchPlayerInventoryTab;
import net.dries007.tfc.util.Helpers;

import static net.dries007.tfc.api.util.TFCConstants.MOD_ID;

@SideOnly(Side.CLIENT)
public class GuiSkills extends GuiContainerTFC
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation(MOD_ID, "textures/gui/player_skills.png");

    private final String[] skillTooltips;
    private final int[] skillBarWidths;

    private int currentPage;
    private int skillsToDisplay;

    public GuiSkills(Container container, InventoryPlayer playerInv)
    {
        super(container, playerInv, BACKGROUND);

        this.skillTooltips = new String[4];
        this.skillBarWidths = new int[4];

        updateSkillValues();
    }

    @Override
    public void initGui()
    {
        super.initGui();

        int buttonId = 0;
        addButton(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.INVENTORY, guiLeft, guiTop, ++buttonId, true));
        addButton(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.SKILLS, guiLeft, guiTop, ++buttonId, false));
        addButton(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.CALENDAR, guiLeft, guiTop, ++buttonId, true));
        addButton(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.NUTRITION, guiLeft, guiTop, ++buttonId, true));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        for (int i = 0; i < skillsToDisplay; i++)
        {
            // Background
            //drawTexturedModalRect(guiLeft + 7, guiTop + 16 + (16 * i), 0, 169, 162, 5);

            // Progress Bar
            drawTexturedModalRect(guiLeft + 8, guiTop + 17 + (16 * i), 0, 166, skillBarWidths[i], 3);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        for (int i = 0; i < skillsToDisplay; i++)
        {
            // Tooltip
            fontRenderer.drawString(skillTooltips[i], 7, 7 + (16 * i), 0x404040);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button instanceof GuiButtonPlayerInventoryTab && ((GuiButtonPlayerInventoryTab) button).isActive())
        {
            GuiButtonPlayerInventoryTab tabButton = (GuiButtonPlayerInventoryTab) button;
            if (tabButton.isActive())
            {
                if (tabButton.getGuiType() == TFCGuiHandler.Type.INVENTORY)
                {
                    this.mc.displayGuiScreen(new GuiInventory(playerInv.player));
                }
                TerraFirmaCraft.getNetwork().sendToServer(new PacketSwitchPlayerInventoryTab(tabButton.getGuiType()));
            }
        }
    }

    private void updateSkillValues()
    {
        skillsToDisplay = 0;

        IPlayerSkills skills = playerInv.player.getCapability(CapabilityPlayerSkills.CAPABILITY, null);
        if (skills != null)
        {
            List<SkillType<? extends Skill>> skillOrder = SkillType.getSkills();
            int totalSkills = skillOrder.size();
            int startSkill = currentPage * 4;
            if (startSkill >= totalSkills || startSkill < 0)
            {
                TerraFirmaCraft.getLog().warn("Invalid skill page! Page: {}, Start at: {}, Skill Order is: {}", currentPage, startSkill, skillOrder);
                return;
            }

            // Loop through the next four or less skills
            skillsToDisplay = Math.min(4, totalSkills - startSkill);
            for (int i = 0; i < skillsToDisplay; i++)
            {
                // Load the skills as per the skill order
                SkillType<? extends Skill> skillType = skillOrder.get(startSkill + i);
                Skill skill = skills.getSkill(skillType);
                if (skill != null)
                {
                    skillTooltips[i] = I18n.format("tfc.skill." + skillType.getName(), I18n.format(Helpers.getEnumName(skill.getTier())));
                    skillBarWidths[i] = (int) (160 * skill.getLevel());
                }
            }
        }

    }
}
