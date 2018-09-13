/*
 * Copyright © 2016 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package co.cask.hydrator.common.batch;

import org.apache.hadoop.conf.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for providing operations on the {@link Configuration}.
 */
public final class ConfigurationUtils {
  private ConfigurationUtils() {
    // no-op
  }

  /**
   * Get the {@link Map} of properties which are newly added or have updated in the configuration instance passed
   * to this method as a parameter. The modified configurations are compared with the default configurations
   * created using {@code new Configuation()}.
   *
   * @param modifiedConf the updated configurations
   * @return the {@link Map} of properties which are newly added or have updated in the modifiedConf.
   */
  public static Map<String, String> getNonDefaultConfigurations(Configuration modifiedConf) {
    Map<String, String> defaultConf = getConfigurationAsMap(new Configuration());
    Map<String, String> result = new HashMap<>();
    for (Map.Entry<String, String> modifiedEntry : modifiedConf) {
      String key = modifiedEntry.getKey();
      String modifiedVal = modifiedEntry.getValue();
      String defaultVal = defaultConf.get(key);
      if (!Objects.equals(modifiedVal, defaultVal)) {
        result.put(key, modifiedVal);
      }
    }
    return result;
  }

  private static Map<String, String> getConfigurationAsMap(Configuration conf) {
    Map<String, String> map = new HashMap<>();
    for (Map.Entry<String, String> entry : conf) {
      map.put(entry.getKey(), entry.getValue());
    }
    return map;
  }
}
