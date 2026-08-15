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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Beta didn't have off-handing, so don't let the client put items in the offhand slot
// See: OffhandKeybindListMixin.java, OffhandSwapKeybindMixin.java
@Mixin(Slot.class)
public class OffhandSlotMixin {
  @Inject(method = "mayPlace", at = @At("HEAD"), cancellable = true)
  private void modernBetaAnnoyancesFix$blockOffhandInsert(
      ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
    if (!ModernBetaAnnoyancesFixConfig.get().offhandDisabled) {
      return;
    }
    Slot self = (Slot) (Object) this;
    if (self.container instanceof Inventory && self.getContainerSlot() == Inventory.SLOT_OFFHAND) {
      cir.setReturnValue(false);
    }
  }
}
