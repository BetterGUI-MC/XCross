package me.hsgamer.bettergui.xcross.modifier;

import com.cryptomorin.xseries.SkullUtils;
import me.hsgamer.hscore.bukkit.item.modifier.ItemMetaModifier;
import me.hsgamer.hscore.common.StringReplacer;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class XSkullModifier implements ItemMetaModifier {
    private String skullString = "";

    @Override
    public @NotNull ItemMeta modifyMeta(@NotNull ItemMeta meta, @Nullable UUID uuid, @NotNull Collection<StringReplacer> stringReplacers) {
        if (!(meta instanceof SkullMeta)) {
            return meta;
        }
        String value = StringReplacer.replace(skullString, uuid, stringReplacers);
        return SkullUtils.applySkin(meta, value);
    }

    @Override
    public boolean loadFromItemMeta(ItemMeta meta) {
        if (!(meta instanceof SkullMeta)) {
            return false;
        }
        this.skullString = Optional.ofNullable(SkullUtils.getSkinValue(meta)).orElse("");
        return true;
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
