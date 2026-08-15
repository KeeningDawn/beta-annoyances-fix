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
package io.github.keeningdawn.modernbetaannoyancesfix;

import io.github.keeningdawn.modernbetaannoyancesfix.config.ModernBetaAnnoyancesFixConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModernBetaAnnoyancesFix implements ModInitializer {
  public static final String MOD_ID = "modern-beta-annoyances-fix";

  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  @Override
  public void onInitialize() {
    ModernBetaAnnoyancesFixConfig.load();
    LOGGER.info("Modern Beta Annoyances Fix initialized!");
  }

  public static Identifier id(String path) {
    return Identifier.fromNamespaceAndPath(MOD_ID, path);
  }
}
