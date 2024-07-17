package me.hsgamer.bettergui.xcross.modifier;

import com.cryptomorin.xseries.XPotion;
import me.hsgamer.hscore.bukkit.item.modifier.ItemMetaComparator;
import me.hsgamer.hscore.bukkit.item.modifier.ItemMetaModifier;
import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.common.StringReplacer;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class XPotionModifier implements ItemMetaModifier, ItemMetaComparator {
    private List<String> potionEffectList = Collections.emptyList();

    public List<PotionEffect> getParsed(UUID uuid, StringReplacer stringReplacer) {
        List<String> list = new ArrayList<>(potionEffectList);
        list.replaceAll(s -> stringReplacer.replaceOrOriginal(s, uuid));
        return XPotion.parseEffects(list).stream().map(XPotion.Effect::getEffect).collect(Collectors.toList());
    }

    @Override
    public @NotNull ItemMeta modifyMeta(@NotNull ItemMeta meta, @Nullable UUID uuid, @NotNull StringReplacer stringReplacer) {
        if (meta instanceof PotionMeta) {
            PotionMeta potionMeta = (PotionMeta) meta;
            getParsed(uuid, stringReplacer).forEach(potionEffect -> potionMeta.addCustomEffect(potionEffect, true));
            return potionMeta;
        }
        return meta;
    }

    @Override
    public boolean loadFromItemMeta(ItemMeta meta) {
        if (!(meta instanceof PotionMeta)) {
            return false;
        }
        this.potionEffectList = ((PotionMeta) meta).getCustomEffects()
                .stream()
                .map(potionEffect -> XPotion.matchXPotion(potionEffect.getType()).name() + ", " + potionEffect.getDuration() + ", " + potionEffect.getAmplifier())
                .collect(Collectors.toList());
        return true;
    }

    @Override
    public Object toObject() {
        return potionEffectList;
    }

    @Override
    public void loadFromObject(Object object) {
        this.potionEffectList = CollectionUtils.createStringListFromObject(object, true);
    }

    @Override
    public boolean compare(@NotNull ItemMeta meta, @Nullable UUID uuid, @NotNull StringReplacer stringReplacer) {
        if (!(meta instanceof PotionMeta)) {
            return false;
        }
        List<PotionEffect> list1 = getParsed(uuid, stringReplacer);
        List<PotionEffect> list2 = ((PotionMeta) meta).getCustomEffects();
        return list1.size() == list2.size() && new HashSet<>(list1).containsAll(list2);
    }
}
