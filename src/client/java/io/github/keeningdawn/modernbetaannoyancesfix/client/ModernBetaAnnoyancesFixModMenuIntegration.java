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
package io.github.keeningdawn.modernbetaannoyancesfix.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.keeningdawn.modernbetaannoyancesfix.config.ModernBetaAnnoyancesFixConfig;
import java.util.function.Consumer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;

public class ModernBetaAnnoyancesFixModMenuIntegration implements ModMenuApi {
  @Override
  public ConfigScreenFactory<?> getModConfigScreenFactory() {
    return parent -> {
      ModernBetaAnnoyancesFixConfig config = ModernBetaAnnoyancesFixConfig.get();

      ConfigBuilder builder =
          ConfigBuilder.create()
              .setParentScreen(parent)
              .setTitle(Component.translatable("modern-beta-annoyances-fix.config.title"))
              .solidBackground()
              .setSavingRunnable(config::save);

      ConfigEntryBuilder entryBuilder = builder.entryBuilder();
      ConfigCategory stackSizes =
          builder.getOrCreateCategory(
              Component.translatable("modern-beta-annoyances-fix.config.category.stack_sizes"));
      ConfigCategory placementAndInteraction =
          builder.getOrCreateCategory(
              Component.translatable(
                  "modern-beta-annoyances-fix.config.category.placement_and_interaction"));

      // Stack sizes, alphabetized by display name
      addToggle(
          entryBuilder,
          stackSizes,
          "modern-beta-annoyances-fix.config.bread_stack",
          config.breadStackEnabled,
          v -> config.breadStackEnabled = v);
      addToggle(
          entryBuilder,
          stackSizes,
          "modern-beta-annoyances-fix.config.cooked_cod_stack",
          config.cookedCodStackEnabled,
          v -> config.cookedCodStackEnabled = v);
      addToggle(
          entryBuilder,
          stackSizes,
          "modern-beta-annoyances-fix.config.cooked_porkchop_stack",
          config.cookedPorkchopStackEnabled,
          v -> config.cookedPorkchopStackEnabled = v);
      addToggle(
          entryBuilder,
          stackSizes,
          "modern-beta-annoyances-fix.config.cookie_stack",
          config.cookieStackEnabled,
          v -> config.cookieStackEnabled = v);
      addToggle(
          entryBuilder,
          stackSizes,
          "modern-beta-annoyances-fix.config.egg_stack",
          config.eggStackEnabled,
          v -> config.eggStackEnabled = v);
      addToggle(
          entryBuilder,
          stackSizes,
          "modern-beta-annoyances-fix.config.door_stack",
          config.doorStackEnabled,
          v -> config.doorStackEnabled = v);
      addToggle(
          entryBuilder,
          stackSizes,
          "modern-beta-annoyances-fix.config.sign_stack",
          config.signStackEnabled,
          v -> config.signStackEnabled = v);
      addToggle(
          entryBuilder,
          stackSizes,
          "modern-beta-annoyances-fix.config.raw_cod_stack",
          config.rawCodStackEnabled,
          v -> config.rawCodStackEnabled = v);
      addToggle(
          entryBuilder,
          stackSizes,
          "modern-beta-annoyances-fix.config.raw_porkchop_stack",
          config.rawPorkchopStackEnabled,
          v -> config.rawPorkchopStackEnabled = v);

      // Placement and interaction, alphabetized by display name
      addToggle(
          entryBuilder,
          placementAndInteraction,
          "modern-beta-annoyances-fix.config.axe_stripping",
          config.axeStrippingDisabled,
          v -> config.axeStrippingDisabled = v);
      addToggle(
          entryBuilder,
          placementAndInteraction,
          "modern-beta-annoyances-fix.config.fence_connection",
          config.fenceConnectionEnabled,
          v -> config.fenceConnectionEnabled = v);
      addToggle(
          entryBuilder,
          placementAndInteraction,
          "modern-beta-annoyances-fix.config.log_placement",
          config.logPlacementEnabled,
          v -> config.logPlacementEnabled = v);
      addToggle(
          entryBuilder,
          placementAndInteraction,
          "modern-beta-annoyances-fix.config.offhand",
          config.offhandDisabled,
          v -> config.offhandDisabled = v);
      addToggle(
          entryBuilder,
          placementAndInteraction,
          "modern-beta-annoyances-fix.config.shovel_path",
          config.shovelPathDisabled,
          v -> config.shovelPathDisabled = v);
      addToggle(
          entryBuilder,
          placementAndInteraction,
          "modern-beta-annoyances-fix.config.slab_placement",
          config.slabPlacementEnabled,
          v -> config.slabPlacementEnabled = v);
      addToggle(
          entryBuilder,
          placementAndInteraction,
          "modern-beta-annoyances-fix.config.stair_placement",
          config.stairPlacementEnabled,
          v -> config.stairPlacementEnabled = v);
      addToggle(
          entryBuilder,
          placementAndInteraction,
          "modern-beta-annoyances-fix.config.trapdoor_placement",
          config.trapdoorPlacementEnabled,
          v -> config.trapdoorPlacementEnabled = v);

      return builder.build();
    };
  }

  private static void addToggle(
      ConfigEntryBuilder entryBuilder,
      ConfigCategory category,
      String key,
      boolean current,
      Consumer<Boolean> setter) {
    category.addEntry(
        entryBuilder
            .startBooleanToggle(Component.translatable(key), current)
            .setDefaultValue(true)
            .setSaveConsumer(setter::accept)
            .build());
  }
}
