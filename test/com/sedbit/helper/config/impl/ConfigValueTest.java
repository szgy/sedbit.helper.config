/*
 * Copyright 2017 Gyula Szabo D. (sedbit.com).
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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gyula Szabo D. (sedbit.com)
 */
public class ConfigValueTest {
    Integer intValue;
    
    @Test
    public void testSetGetDelegated() {
        
        Consumer<Integer> setter = (i) -> {intValue = i;};
        Supplier<Integer> getter = () -> {return intValue;};
        
        ConfigValue<Integer> instance = new ConfigValue<>(getter, setter);
        instance.set(42);
        assertEquals(Integer.valueOf(42), instance.get());
    }

    @Test
    public void testToString() {
        Consumer<Integer> setter = (i) -> {intValue = i;};
        Supplier<Integer> getter = () -> {return intValue;};
        
        ConfigValue<Integer> instance = new ConfigValue<>(getter, setter);
        instance.set(42);
        assertEquals("42", instance.toString());
    }
    
}
