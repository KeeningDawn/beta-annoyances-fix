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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Beta levers can only attach to the side of a block, never the top or bottom
@Mixin(FaceAttachedHorizontalDirectionalBlock.class)
public class LeverPlacementMixin {
  @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
  private void modernBetaAnnoyancesFix$restrictFace(
      BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
    if (!ModernBetaAnnoyancesFixConfig.get().leverPlacementEnabled) {
      return;
    }

    Object self = this;
    if (self != Blocks.LEVER) {
      return;
    }

    BlockState result = cir.getReturnValue();
    if (result != null
        && result.getValue(FaceAttachedHorizontalDirectionalBlock.FACE) != AttachFace.WALL) {
      cir.setReturnValue(null);
    }
  }
}
