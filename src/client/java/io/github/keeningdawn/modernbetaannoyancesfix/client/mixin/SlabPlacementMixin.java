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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Beta slabs could only sit on the bottom half, so don't let the client place them on the top half
// either
@Mixin(SlabBlock.class)
public class SlabPlacementMixin {
  @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
  private void modernBetaAnnoyancesFix$restrictPlacement(
      BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
    if (!ModernBetaAnnoyancesFixConfig.get().slabPlacementEnabled) {
      return;
    }
    BlockState result = cir.getReturnValue();
    if (result != null && result.getValue(SlabBlock.TYPE) == SlabType.TOP) {
      cir.setReturnValue(result.setValue(SlabBlock.TYPE, SlabType.BOTTOM));
    }
  }
}
