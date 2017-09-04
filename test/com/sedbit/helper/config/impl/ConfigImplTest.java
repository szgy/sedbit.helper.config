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

import com.sedbit.helper.config.ConfigTest;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Gyula Szabo D. (sedbit.com)
 */
public class ConfigImplTest {
        
    ConfigImpl instance = new ConfigImpl();
    
    @Before
    public void before() {
        ConfigTest.ConfigHolder1.counter = 0;
        ConfigTest.ConfigHolder1.date = null;
        ConfigTest.ConfigHolder2.stringConfig = null;
    }
    
    @Test
    public void testRegisterNewConfigCategory() {
        instance.registerConfigValue("ConfigGroup", "ConfigName", ConfigTest.ConfigHolder1::getCounter, ConfigTest.ConfigHolder1::setCounter);
        
        assertEquals(Integer.valueOf(0), instance.getValue("ConfigGroup", "ConfigName"));
    }

    @Test
    public void testRegisterTwiceConfigValue() {
        instance.registerConfigValue("ConfigGroup", "ConfigName", ConfigTest.ConfigHolder1::getCounter, ConfigTest.ConfigHolder1::setCounter);
        instance.setValue("ConfigGroup", "ConfigName", 42);
        
        instance.registerConfigValue("ConfigGroup", "ConfigName", ConfigTest.ConfigHolder2::get, ConfigTest.ConfigHolder2::set);
        
        assertNull(instance.getValue("ConfigGroup", "ConfigName"));
    }
    
    @Test
    public void testRegisterTwoConfigValue() {
        instance.registerConfigValue("ConfigGroup", "ConfigName", ConfigTest.ConfigHolder1::getCounter, ConfigTest.ConfigHolder1::setCounter);
        instance.registerConfigValue("ConfigGroup", "ConfigName2", ConfigTest.ConfigHolder2::get, ConfigTest.ConfigHolder2::set);

        instance.setValue("ConfigGroup", "ConfigName", 42);
        instance.setValue("ConfigGroup", "ConfigName2", "The Answer");
        
        assertEquals(Integer.valueOf(42), instance.getValue("ConfigGroup", "ConfigName"));
        assertEquals("The Answer", instance.getValue("ConfigGroup", "ConfigName2"));
    }
    
    @Test(expected = NullPointerException.class)
    public void testDeRegisterConfigValueThrowsNull() {
        instance.registerConfigValue("ConfigGroup", "ConfigName", ConfigTest.ConfigHolder1::getCounter, ConfigTest.ConfigHolder1::setCounter);
        instance.registerConfigValue("ConfigGroup", "ConfigName2", ConfigTest.ConfigHolder2::get, ConfigTest.ConfigHolder2::set);

        instance.setValue("ConfigGroup", "ConfigName", 42);
        instance.setValue("ConfigGroup2", "ConfigName2", "The Answer");
        
        instance.deRegisterConfigCategory("ConfigGroup");
        
        assertEquals("The Answer", instance.getValue("ConfigGroup", "ConfigName2"));
        assertEquals(Integer.valueOf(42), instance.getValue("ConfigGroup", "ConfigName"));
    }

}
