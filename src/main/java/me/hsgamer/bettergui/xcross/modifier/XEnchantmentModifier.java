package me.hsgamer.bettergui.xcross.modifier;

import com.cryptomorin.xseries.XEnchantment;
import me.hsgamer.hscore.bukkit.item.modifier.ItemMetaComparator;
import me.hsgamer.hscore.bukkit.item.modifier.ItemMetaModifier;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.common.StringReplacer;
import me.hsgamer.hscore.common.Validate;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class XEnchantmentModifier implements ItemMetaModifier, ItemMetaComparator {
    private List<String> enchantmentList = Collections.emptyList();

    private Map<XEnchantment, Integer> getParsed(UUID uuid, StringReplacer stringReplacer) {
        Map<XEnchantment, Integer> enchantments = new HashMap<>();
        for (String string : enchantmentList) {
            Optional<XEnchantment> enchantment;
            string = stringReplacer.replaceOrOriginal(string, uuid);

            int level = 1;
            if (string.contains(",")) {
                String[] split = string.split(",", 2);
                enchantment = XEnchantment.of(split[0].trim());
                String rawLevel = split[1].trim();
                Optional<BigDecimal> optional = Validate.getNumber(rawLevel);
                if (optional.isPresent()) {
                    level = optional.get().intValue();
                } else {
                    MessageUtils.sendMessage(uuid, "&cInvalid enchantment level: " + rawLevel);
                    continue;
                }
            } else {
                enchantment = XEnchantment.of(string.trim());
            }
            if (enchantment.isPresent()) {
                enchantments.put(enchantment.get(), level);
            } else {
                MessageUtils.sendMessage(uuid, "&cInvalid enchantment: " + string);
            }
        }
        return enchantments;
    }

    @Override
    public @NotNull ItemMeta modifyMeta(@NotNull ItemMeta meta, @Nullable UUID uuid, @NotNull StringReplacer stringReplacer) {
        Map<XEnchantment, Integer> map = getParsed(uuid, stringReplacer);
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        for (Map.Entry<XEnchantment, Integer> entry : map.entrySet()) {
            Enchantment enchantment = entry.getKey().get();
            if (enchantment != null) {
                enchantments.put(enchantment, entry.getValue());
            }
        }
        if (meta instanceof EnchantmentStorageMeta) {
            enchantments.forEach((enchant, level) -> ((EnchantmentStorageMeta) meta).addStoredEnchant(enchant, level, true));
        } else {
            enchantments.forEach((enchant, level) -> meta.addEnchant(enchant, level, true));
        }
        return meta;
    }

    @Override
    public boolean loadFromItemMeta(ItemMeta meta) {
        if (!meta.hasEnchants()) {
            return false;
        }
        this.enchantmentList = meta.getEnchants().entrySet()
                .stream()
                .map(entry -> XEnchantment.of(entry.getKey()).name() + ", " + entry.getValue())
                .collect(Collectors.toList());
        return true;
    }

    @Override
    public boolean compare(@NotNull ItemMeta meta, @Nullable UUID uuid, @NotNull StringReplacer stringReplacer) {
        Map<XEnchantment, Integer> list1 = getParsed(uuid, stringReplacer);
        Map<XEnchantment, Integer> list2 = new HashMap<>();
        meta.getEnchants().forEach(((enchantment, integer) -> list2.put(XEnchantment.of(enchantment), integer)));
        if (list1.size() != list2.size()) {
            return false;
        }
        for (Map.Entry<XEnchantment, Integer> entry : list1.entrySet()) {
            XEnchantment enchantment = entry.getKey();
            int lvl = entry.getValue();
            if (!list2.containsKey(enchantment) || list2.get(enchantment) != lvl) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object toObject() {
        return enchantmentList;
    }

    @Override
    public void loadFromObject(Object object) {
        this.enchantmentList = CollectionUtils.createStringListFromObject(object, true);
    }
}
