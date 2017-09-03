/*
 * Copyright 2017 Gyula. (sedbit.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sedbit.helper.config.impl;

import com.sedbit.helper.config.Config;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigImpl implements Config {

    HashMap<String, HashMap<String, ConfigValue>> configCategories = new HashMap<>();
    
    private void registerConfigCategory(String category) {
        configCategories.put(category, new HashMap<>());
    }
    
    @Override
    public void deRegisterConfigCategory(String category) {
        configCategories.remove(category);
    }
    
    @Override
    public <T> void registerConfigValue(String category, String name, Supplier<T> get, Consumer<T> set) {
        if (!configCategories.containsKey(category)) {
            registerConfigCategory(category);
        }
        HashMap<String, ConfigValue> configCategory = configCategories.get(category);
        
        configCategory.put(name, new ConfigValue<>(get, set));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getValue(String category, String name) {
        return (T) configCategories.get(category).get(name).get();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> void setValue(String category, String name, T value) {
        configCategories.get(category).get(name).set(value);
    }
    
    @Override
    public String toString() {
        return configCategories.toString();
    }
    
}
