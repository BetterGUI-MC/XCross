package me.hsgamer.bettergui.xcross.modifier;

import com.cryptomorin.xseries.XMaterial;
import me.hsgamer.hscore.common.StringReplacer;
import me.hsgamer.hscore.minecraft.item.ItemComparator;
import me.hsgamer.hscore.minecraft.item.ItemModifier;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class XMaterialModifier implements ItemModifier<ItemStack>, ItemComparator<ItemStack> {
    private String materialString;

    @Override
    public @NotNull ItemStack modify(@NotNull ItemStack original, UUID uuid, @NotNull StringReplacer stringReplacer) {
        XMaterial.matchXMaterial(stringReplacer.replaceOrOriginal(materialString, uuid))
                .ifPresent(xMaterial -> xMaterial.setType(original));
        return original;
    }

    @Override
    public Object toObject() {
        return materialString;
    }

    @Override
    public void loadFromObject(Object object) {
        this.materialString = String.valueOf(object);
    }

    @Override
    public boolean loadFromItem(ItemStack itemStack) {
        XMaterial material = XMaterial.matchXMaterial(itemStack);
        this.materialString = material.name();
        return true;
    }

    @Override
    public boolean compare(@NotNull ItemStack itemStack, @Nullable UUID uuid, @NotNull StringReplacer stringReplacer) {
        return XMaterial
                .matchXMaterial(stringReplacer.replaceOrOriginal(materialString, uuid))
                .map(xMaterial -> xMaterial.isSimilar(itemStack))
                .orElse(false);
    }
}
