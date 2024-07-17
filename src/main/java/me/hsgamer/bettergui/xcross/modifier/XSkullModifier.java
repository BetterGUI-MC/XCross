package me.hsgamer.bettergui.xcross.modifier;

import com.cryptomorin.xseries.profiles.builder.XSkull;
import com.cryptomorin.xseries.profiles.objects.Profileable;
import me.hsgamer.hscore.bukkit.item.modifier.ItemMetaModifier;
import me.hsgamer.hscore.common.StringReplacer;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class XSkullModifier implements ItemMetaModifier {
    private String skullString = "";

    @Override
    public @NotNull ItemMeta modifyMeta(@NotNull ItemMeta meta, @Nullable UUID uuid, @NotNull StringReplacer stringReplacer) {
        if (!(meta instanceof SkullMeta)) {
            return meta;
        }
        String value = stringReplacer.replaceOrOriginal(skullString, uuid);
        return XSkull.of(meta).profile(Profileable.detect(value)).apply();
    }

    @Override
    public boolean loadFromItemMeta(ItemMeta meta) {
        if (!(meta instanceof SkullMeta)) {
            return false;
        }
        this.skullString = Optional.ofNullable(XSkull.of(meta).getProfileString()).orElse("");
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
