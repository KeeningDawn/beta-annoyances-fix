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
package io.github.keeningdawn.modernbetaannoyancesfix.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.keeningdawn.modernbetaannoyancesfix.ModernBetaAnnoyancesFix;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public class ModernBetaAnnoyancesFixConfig {
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  private static final Path PATH =
      FabricLoader.getInstance().getConfigDir().resolve("modern-beta-annoyances-fix.json");

  private static ModernBetaAnnoyancesFixConfig instance;

  public boolean cookieStackEnabled = true;
  public boolean breadStackEnabled = true;
  public boolean rawCodStackEnabled = true;
  public boolean cookedCodStackEnabled = true;
  public boolean rawPorkchopStackEnabled = true;
  public boolean cookedPorkchopStackEnabled = true;
  public boolean eggStackEnabled = true;
  public boolean signStackEnabled = true;
  public boolean doorStackEnabled = true;

  public boolean trapdoorPlacementEnabled = true;
  public boolean slabPlacementEnabled = true;
  public boolean stairPlacementEnabled = true;
  public boolean fenceConnectionEnabled = true;
  public boolean logPlacementEnabled = true;
  public boolean axeStrippingDisabled = true;
  public boolean shovelPathDisabled = true;
  public boolean offhandDisabled = true;

  public static ModernBetaAnnoyancesFixConfig get() {
    if (instance == null) {
      instance = load();
    }
    return instance;
  }

  public static ModernBetaAnnoyancesFixConfig load() {
    if (Files.exists(PATH)) {
      try {
        ModernBetaAnnoyancesFixConfig loaded =
            GSON.fromJson(Files.readString(PATH), ModernBetaAnnoyancesFixConfig.class);
        if (loaded != null) {
          instance = loaded;
          return instance;
        }
      } catch (IOException e) {
        ModernBetaAnnoyancesFix.LOGGER.error("Failed to load config, using defaults", e);
      }
    }
    instance = new ModernBetaAnnoyancesFixConfig();
    return instance;
  }

  public void save() {
    try {
      Files.writeString(PATH, GSON.toJson(this));
    } catch (IOException e) {
      ModernBetaAnnoyancesFix.LOGGER.error("Failed to save config", e);
    }
  }
}
