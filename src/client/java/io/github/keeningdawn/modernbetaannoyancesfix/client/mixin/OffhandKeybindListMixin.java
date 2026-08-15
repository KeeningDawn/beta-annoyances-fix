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
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Offhanding is fully disabled, so drop the "Swap Hands" keybind entirely.
// See: OffhandSlotMixin.java, OffhandSwapKeybindMixin.java
@Mixin(Options.class)
public class OffhandKeybindListMixin {
  @Shadow @Final @Mutable public KeyMapping[] keyMappings;

  @Shadow public KeyMapping keySwapOffhand;

  @Inject(method = "<init>", at = @At("RETURN"))
  private void modernBetaAnnoyancesFix$hideOffhandKeybind(
      Minecraft minecraft, File optionsFile, CallbackInfo ci) {
    if (!ModernBetaAnnoyancesFixConfig.get().offhandDisabled) {
      return;
    }
    List<KeyMapping> filtered = new ArrayList<>(Arrays.asList(this.keyMappings));
    filtered.remove(this.keySwapOffhand);
    this.keyMappings = filtered.toArray(new KeyMapping[0]);
  }
}
