/*
 * Copyright 2017 Gyula Szabo D. (sedbit.com)
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
package com.sedbit.helper.config;

import com.sedbit.helper.config.impl.ConfigImpl;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author gyula.szabo
 */
public interface Config {
    public static final Config CONFIG = new ConfigImpl();
    
    default void deRegisterConfigCategory(String category) {
        CONFIG.deRegisterConfigCategory(category);
    }
    
    default <T> void registerConfigValue(String category, String name, Supplier<T> get, Consumer<T> set) {
        CONFIG.registerConfigValue(category, name, get, set);
    }

    default <T> T getValue(String category, String name) {
        return CONFIG.getValue(category, name);
    }
    
    default <T> void setValue(String category, String name, T value) {
        CONFIG.setValue(category, name, value);
    }
}
