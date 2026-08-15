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
package io.github.keeningdawn.betaannoyancesfix.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.keeningdawn.betaannoyancesfix.config.BetaAnnoyancesFixConfig;
import io.github.keeningdawn.betaannoyancesfix.config.StackSizeOption;
import java.util.ArrayList;
import java.util.List;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;

public class BetaAnnoyancesFixModMenuIntegration implements ModMenuApi {
  @Override
  public ConfigScreenFactory<?> getModConfigScreenFactory() {
    return parent -> {
      BetaAnnoyancesFixConfig config = BetaAnnoyancesFixConfig.get();

      ConfigBuilder builder =
          ConfigBuilder.create()
              .setParentScreen(parent)
              .setTitle(Component.translatable("beta-annoyances-fix.config.title"))
              .solidBackground()
              .setSavingRunnable(config::save);

      ConfigEntryBuilder entryBuilder = builder.entryBuilder();
      ConfigCategory general =
          builder.getOrCreateCategory(
              Component.translatable("beta-annoyances-fix.config.category.stack_sizes"));

      // toggles first, alphabetized by display name
      addToggle(entryBuilder, general, "beta-annoyances-fix.config.bread_stack", config.breadStack);
      addToggle(
          entryBuilder,
          general,
          "beta-annoyances-fix.config.cooked_cod_stack",
          config.cookedCodStack);
      addToggle(
          entryBuilder,
          general,
          "beta-annoyances-fix.config.cooked_porkchop_stack",
          config.cookedPorkchopStack);
      addToggle(
          entryBuilder, general, "beta-annoyances-fix.config.cookie_stack", config.cookieStack);
      addToggle(entryBuilder, general, "beta-annoyances-fix.config.egg_stack", config.eggStack);
      addToggle(entryBuilder, general, "beta-annoyances-fix.config.door_stack", config.doorStack);
      addToggle(entryBuilder, general, "beta-annoyances-fix.config.sign_stack", config.signStack);
      addToggle(
          entryBuilder, general, "beta-annoyances-fix.config.raw_cod_stack", config.rawCodStack);
      addToggle(
          entryBuilder,
          general,
          "beta-annoyances-fix.config.raw_porkchop_stack",
          config.rawPorkchopStack);

      // item id lists below, same alphabetized order (only the ones that support alternate ids)
      addItemList(
          entryBuilder,
          general,
          "beta-annoyances-fix.config.bread_stack",
          config.breadStack,
          List.of("minecraft:bread", "minecraft:nautilus_shell"));
      addItemList(
          entryBuilder,
          general,
          "beta-annoyances-fix.config.cooked_cod_stack",
          config.cookedCodStack,
          List.of("minecraft:cooked_cod", "minecraft:rabbit_hide"));
      addItemList(
          entryBuilder,
          general,
          "beta-annoyances-fix.config.cooked_porkchop_stack",
          config.cookedPorkchopStack,
          List.of("minecraft:cooked_porkchop", "minecraft:nether_brick"));
      addItemList(
          entryBuilder,
          general,
          "beta-annoyances-fix.config.cookie_stack",
          config.cookieStack,
          List.of("minecraft:cookie", "minecraft:quartz"));
      addItemList(
          entryBuilder,
          general,
          "beta-annoyances-fix.config.raw_cod_stack",
          config.rawCodStack,
          List.of("minecraft:cod", "minecraft:rabbit_foot"));
      addItemList(
          entryBuilder,
          general,
          "beta-annoyances-fix.config.raw_porkchop_stack",
          config.rawPorkchopStack,
          List.of("minecraft:porkchop", "minecraft:fermented_spider_eye"));

      return builder.build();
    };
  }

  private static void addToggle(
      ConfigEntryBuilder entryBuilder,
      ConfigCategory category,
      String key,
      StackSizeOption option) {
    category.addEntry(
        entryBuilder
            .startBooleanToggle(Component.translatable(key), option.enabled)
            .setDefaultValue(true)
            .setSaveConsumer(value -> option.enabled = value)
            .build());
  }

  private static void addItemList(
      ConfigEntryBuilder entryBuilder,
      ConfigCategory category,
      String key,
      StackSizeOption option,
      List<String> defaultItemIds) {
    category.addEntry(
        entryBuilder
            .startStrList(Component.translatable(key + ".ids"), option.itemIds)
            .setDefaultValue(defaultItemIds)
            .setSaveConsumer(value -> option.itemIds = new ArrayList<>(value))
            .build());
  }
}
