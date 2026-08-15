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
import java.util.Map;
import net.minecraft.world.item.ShovelItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Beta didn't have path flattening, so don't let the client flatten grass either
@Mixin(ShovelItem.class)
public class ShovelPathMixin {
  @Redirect(
      method = "useOn",
      at =
          @At(
              value = "INVOKE",
              target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
  private Object modernBetaAnnoyancesFix$disablePathFlattening(Map<?, ?> map, Object key) {
    if (ModernBetaAnnoyancesFixConfig.get().shovelPathDisabled) {
      return null;
    }
    return map.get(key);
  }
}
