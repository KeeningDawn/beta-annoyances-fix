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
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Beta logs can only be placed vertically, so force the client to place them that way too
@Mixin(RotatedPillarBlock.class)
public class LogPlacementMixin {
  @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
  private void modernBetaAnnoyancesFix$forceVertical(
      BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
    if (!ModernBetaAnnoyancesFixConfig.get().logPlacementEnabled) {
      return;
    }

    Object self = this;
    if (self != Blocks.OAK_LOG && self != Blocks.BIRCH_LOG && self != Blocks.SPRUCE_LOG) {
      return;
    }

    BlockState result = cir.getReturnValue();
    if (result != null) {
      cir.setReturnValue(result.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y));
    }
  }
}
