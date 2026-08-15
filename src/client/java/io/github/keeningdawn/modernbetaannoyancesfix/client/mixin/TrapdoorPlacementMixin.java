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
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Beta trapdoors sit on the bottom half, against a solid side face, and never freestanding
@Mixin(TrapDoorBlock.class)
public class TrapdoorPlacementMixin {
  @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
  private void modernBetaAnnoyancesFix$restrictPlacement(
      BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
    if (!ModernBetaAnnoyancesFixConfig.get().trapdoorPlacementEnabled) {
      return;
    }
    BlockState result = cir.getReturnValue();
    if (result == null) {
      return;
    }

    boolean freestanding =
        !context.replacingClickedOnBlock() && !context.getClickedFace().getAxis().isHorizontal();
    if (freestanding) {
      cir.setReturnValue(null);
      return;
    }

    Direction facing = result.getValue(TrapDoorBlock.FACING);
    BlockPos supportPos = context.getClickedPos().relative(facing.getOpposite());
    Block support = context.getLevel().getBlockState(supportPos).getBlock();
    if (support instanceof SlabBlock || support instanceof StairBlock) {
      cir.setReturnValue(null);
      return;
    }

    cir.setReturnValue(result.setValue(TrapDoorBlock.HALF, Half.BOTTOM));
  }
}
