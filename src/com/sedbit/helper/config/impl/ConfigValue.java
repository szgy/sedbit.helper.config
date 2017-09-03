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

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigValue<T> {
    private final Supplier<T> get;
    private final Consumer<T> set;
    
    public ConfigValue(Supplier<T> get, Consumer<T> set) {
        this.get = get;
        this.set = set;
    }
    
    public void set(T value) {
        set.accept(value);
    }
    
    public T get() {
        return get.get();
    }
    
    @Override
    public String toString() {
        return get().toString();
    }
}
