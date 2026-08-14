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
package io.github.keeningdawn.betaannoyancesfix.client.mixin;

import io.github.keeningdawn.betaannoyancesfix.config.BetaAnnoyancesFixConfig;
import io.github.keeningdawn.betaannoyancesfix.config.StackSizeOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// b1.7.3 had different stack sizes for a bunch of items, some servers emulate this
// but the vanilla client still shows modern stack sizes, causing UI desyncs on shift-click etc.
@Mixin(ItemStack.class)
public class StackSizeMixin {
  @Inject(method = "getMaxStackSize", at = @At("RETURN"), cancellable = true)
  private void betaAnnoyancesFix$capStackSize(CallbackInfoReturnable<Integer> cir) {
    ItemStack self = (ItemStack) (Object) this;
    Identifier id = BuiltInRegistries.ITEM.getKey(self.getItem());
    String idString = id.toString();

    for (StackSizeOption option : BetaAnnoyancesFixConfig.get().allOptions()) {
      if (option.enabled && option.itemIds.contains(idString)) {
        cir.setReturnValue(option.stackSize);
        return;
      }
    }
  }
}
