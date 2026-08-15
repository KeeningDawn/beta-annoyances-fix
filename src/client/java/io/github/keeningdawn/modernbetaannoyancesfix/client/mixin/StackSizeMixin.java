/*
 * Copyright (C) 2026 KeeningDawn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package io.github.keeningdawn.modernbetaannoyancesfix.client.mixin;

import io.github.keeningdawn.modernbetaannoyancesfix.config.ModernBetaAnnoyancesFixConfig;
import java.util.List;
import java.util.Set;
import java.util.function.BooleanSupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Beta had different stack sizes for a bunch of items, some servers emulate this behavior
// but the vanilla client still tries to support modern stack sizes, causing rare UI desyncs
@Mixin(ItemStack.class)
public class StackSizeMixin {
  private record StackFix(BooleanSupplier enabled, int stackSize, Set<String> itemIds) {}

  private static List<StackFix> fixes() {
    ModernBetaAnnoyancesFixConfig config = ModernBetaAnnoyancesFixConfig.get();
    return List.of(
        new StackFix(
            () -> config.cookieStackEnabled, 8, Set.of("minecraft:cookie", "minecraft:quartz")),
        new StackFix(
            () -> config.breadStackEnabled,
            1,
            Set.of("minecraft:bread", "minecraft:nautilus_shell")),
        new StackFix(
            () -> config.rawCodStackEnabled, 1, Set.of("minecraft:cod", "minecraft:rabbit_foot")),
        new StackFix(
            () -> config.cookedCodStackEnabled,
            1,
            Set.of("minecraft:cooked_cod", "minecraft:rabbit_hide")),
        new StackFix(
            () -> config.rawPorkchopStackEnabled,
            1,
            Set.of("minecraft:porkchop", "minecraft:fermented_spider_eye")),
        new StackFix(
            () -> config.cookedPorkchopStackEnabled,
            1,
            Set.of("minecraft:cooked_porkchop", "minecraft:nether_brick")),
        new StackFix(() -> config.eggStackEnabled, 16, Set.of("minecraft:egg")),
        new StackFix(() -> config.signStackEnabled, 1, Set.of("minecraft:oak_sign")),
        new StackFix(() -> config.doorStackEnabled, 1, Set.of("minecraft:oak_door")));
  }

  @Inject(method = "getMaxStackSize", at = @At("RETURN"), cancellable = true)
  private void modernBetaAnnoyancesFix$capStackSize(CallbackInfoReturnable<Integer> cir) {
    ItemStack self = (ItemStack) (Object) this;
    Identifier id = BuiltInRegistries.ITEM.getKey(self.getItem());
    String idString = id.toString();

    for (StackFix fix : fixes()) {
      if (fix.enabled().getAsBoolean() && fix.itemIds().contains(idString)) {
        cir.setReturnValue(fix.stackSize());
        return;
      }
    }
  }
}
