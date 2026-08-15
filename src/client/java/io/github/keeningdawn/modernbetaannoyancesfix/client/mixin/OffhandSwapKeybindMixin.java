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
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Beta didn't have off-handing, so disable the keybind from doing anything
// See: OffhandKeybindListMixin.java, OffhandSlotMixin.java
@Mixin(KeyMapping.class)
public class OffhandSwapKeybindMixin {
  @Inject(method = "consumeClick", at = @At("HEAD"), cancellable = true)
  private void modernBetaAnnoyancesFix$blockOffhandSwap(CallbackInfoReturnable<Boolean> cir) {
    KeyMapping self = (KeyMapping) (Object) this;
    if (ModernBetaAnnoyancesFixConfig.get().offhandDisabled
        && "key.swapOffhand".equals(self.getName())) {
      cir.setReturnValue(false);
    }
  }
}
