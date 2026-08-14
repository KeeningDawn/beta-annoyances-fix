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
package io.github.keeningdawn.betaannoyancesfix.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.keeningdawn.betaannoyancesfix.BetaAnnoyancesFix;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;

public class BetaAnnoyancesFixConfig {
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  private static final Path PATH =
      FabricLoader.getInstance().getConfigDir().resolve("beta-annoyances-fix.json");

  private static BetaAnnoyancesFixConfig instance;

  public StackSizeOption cookieStack =
      new StackSizeOption(8, "minecraft:cookie", "minecraft:quartz");
  public StackSizeOption breadStack =
      new StackSizeOption(1, "minecraft:bread", "minecraft:nautilus_shell");
  public StackSizeOption rawCodStack =
      new StackSizeOption(1, "minecraft:cod", "minecraft:rabbit_foot");
  public StackSizeOption cookedCodStack =
      new StackSizeOption(1, "minecraft:cooked_cod", "minecraft:rabbit_hide");
  public StackSizeOption rawPorkchopStack =
      new StackSizeOption(1, "minecraft:porkchop", "minecraft:fermented_spider_eye");
  public StackSizeOption cookedPorkchopStack =
      new StackSizeOption(1, "minecraft:cooked_porkchop", "minecraft:nether_brick");
  public StackSizeOption eggStack = new StackSizeOption(16, "minecraft:egg");
  public StackSizeOption signStack = new StackSizeOption(1, "minecraft:oak_sign");
  public StackSizeOption doorStack = new StackSizeOption(1, "minecraft:oak_door");

  public List<StackSizeOption> allOptions() {
    return List.of(
        cookieStack,
        breadStack,
        rawCodStack,
        cookedCodStack,
        rawPorkchopStack,
        cookedPorkchopStack,
        eggStack,
        signStack,
        doorStack);
  }

  public static BetaAnnoyancesFixConfig get() {
    if (instance == null) {
      instance = load();
    }
    return instance;
  }

  public static BetaAnnoyancesFixConfig load() {
    if (Files.exists(PATH)) {
      try {
        BetaAnnoyancesFixConfig loaded =
            GSON.fromJson(Files.readString(PATH), BetaAnnoyancesFixConfig.class);
        if (loaded != null) {
          instance = loaded;
          return instance;
        }
      } catch (IOException e) {
        BetaAnnoyancesFix.LOGGER.error("Failed to load config, using defaults", e);
      }
    }
    instance = new BetaAnnoyancesFixConfig();
    return instance;
  }

  public void save() {
    try {
      Files.writeString(PATH, GSON.toJson(this));
    } catch (IOException e) {
      BetaAnnoyancesFix.LOGGER.error("Failed to save config", e);
    }
  }
}
