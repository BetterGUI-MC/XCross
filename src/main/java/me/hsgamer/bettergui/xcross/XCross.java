package me.hsgamer.bettergui.xcross;

import me.hsgamer.bettergui.builder.ActionBuilder;
import me.hsgamer.bettergui.builder.ItemModifierBuilder;
import me.hsgamer.bettergui.xcross.action.MusicAction;
import me.hsgamer.bettergui.xcross.action.XSoundAction;
import me.hsgamer.bettergui.xcross.modifier.XEnchantmentModifier;
import me.hsgamer.bettergui.xcross.modifier.XMaterialModifier;
import me.hsgamer.bettergui.xcross.modifier.XPotionModifier;
import me.hsgamer.bettergui.xcross.modifier.XSkullModifier;
import me.hsgamer.hscore.bukkit.addon.PluginAddon;

public final class XCross extends PluginAddon {
    @Override
    public void onEnable() {
        ActionBuilder.INSTANCE.register(MusicAction::new, "music");
        ActionBuilder.INSTANCE.register(XSoundAction::new, "x-sound", "xsound");

        ItemModifierBuilder.INSTANCE.register(XEnchantmentModifier::new, "x-enchantment", "xenchantment", "x-enchant", "xenchant", "x-enc", "xenc");
        ItemModifierBuilder.INSTANCE.register(XMaterialModifier::new, "x-material", "xmaterial", "x-mat", "xmat", "x-id", "xid");
        ItemModifierBuilder.INSTANCE.register(XPotionModifier::new, "x-potion", "xpotion", "x-effect", "xeffect");
        ItemModifierBuilder.INSTANCE.register(XSkullModifier::new, "x-skull", "xskull", "x-head", "xhead");
    }
}
