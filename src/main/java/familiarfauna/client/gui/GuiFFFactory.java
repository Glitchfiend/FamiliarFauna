package familiarfauna.client.gui;

import java.util.ArrayList;
import java.util.List;

import familiarfauna.config.ConfigurationHandler;
import familiarfauna.core.FamiliarFauna;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.DefaultGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import static familiarfauna.config.ConfigurationHandler.SPAWN_SETTINGS;

public class GuiFFFactory extends DefaultGuiFactory
{
    public GuiFFFactory()
    {
        super(FamiliarFauna.MOD_ID, FamiliarFauna.MOD_NAME);
    }
    
    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen)
    {
        return new GuiConfig(parentScreen, getConfigElements(), modid, false, false, title);
    }

    private static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        List<IConfigElement> spawn_settings = new ConfigElement(ConfigurationHandler.config.getCategory(SPAWN_SETTINGS.toLowerCase())).getChildElements();

        list.add(new DummyCategoryElement(I18n.translateToLocal("config.category.spawnSettings.title"), "config.category.spawnSettings", spawn_settings));

        return list;
    }
}