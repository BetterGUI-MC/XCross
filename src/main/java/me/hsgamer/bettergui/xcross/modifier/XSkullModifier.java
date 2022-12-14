package me.hsgamer.bettergui.xcross.modifier;

import com.cryptomorin.xseries.SkullUtils;
import me.hsgamer.hscore.bukkit.item.ItemMetaModifier;
import me.hsgamer.hscore.common.interfaces.StringReplacer;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class XSkullModifier extends ItemMetaModifier {
    private String skullString = "";

    @Override
    public String getName() {
        return "skull";
    }

    @Override
    public ItemMeta modifyMeta(ItemMeta meta, UUID uuid, Map<String, StringReplacer> stringReplacerMap) {
        if (!(meta instanceof SkullMeta)) {
            return meta;
        }
        String value = StringReplacer.replace(skullString, uuid, stringReplacerMap.values());
        return SkullUtils.applySkin(meta, value);
    }

    @Override
    public void loadFromItemMeta(ItemMeta meta) {
        this.skullString = Optional.ofNullable(SkullUtils.getSkinValue(meta)).orElse("");
    }

    @Override
    public boolean canLoadFromItemMeta(ItemMeta meta) {
        return meta instanceof SkullMeta;
    }

    @Override
    public boolean compareWithItemMeta(ItemMeta meta, UUID uuid, Map<String, StringReplacer> stringReplacerMap) {
        return false;
    }

    @Override
    public Object toObject() {
        return skullString;
    }

    @Override
    public void loadFromObject(Object object) {
        this.skullString = String.valueOf(object);
    }
}
