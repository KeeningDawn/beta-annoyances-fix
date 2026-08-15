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
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Beta stairs only place upright in all 4 facings, never forming corners
@Mixin(StairBlock.class)
public class StairPlacementMixin {
  @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
  private void modernBetaAnnoyancesFix$restrictHalf(
      BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
    if (!ModernBetaAnnoyancesFixConfig.get().stairPlacementEnabled) {
      return;
    }
    BlockState result = cir.getReturnValue();
    if (result != null && result.getValue(StairBlock.HALF) == Half.TOP) {
      cir.setReturnValue(result.setValue(StairBlock.HALF, Half.BOTTOM));
    }
  }

  @Inject(method = "getStairsShape", at = @At("HEAD"), cancellable = true)
  private static void modernBetaAnnoyancesFix$restrictShape(
      BlockState state, BlockGetter level, BlockPos pos, CallbackInfoReturnable<StairsShape> cir) {
    if (ModernBetaAnnoyancesFixConfig.get().stairPlacementEnabled) {
      cir.setReturnValue(StairsShape.STRAIGHT);
    }
  }
}
