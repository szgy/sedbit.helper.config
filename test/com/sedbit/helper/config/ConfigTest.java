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
package com.sedbit.helper.config;

import java.time.Instant;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gyula Szabo D. (sedbit.com)
 */
public class ConfigTest {

    public static class ConfigHolder1 {

        public static int counter;
        public static Instant date;

        public static void setCounter(Integer i) {
            counter = i;
        }
        public static Integer getCounter() {
            return counter;
        }

        private static Instant getDate() {
            return date;
        }

        private static void setDate(Instant date) {
            ConfigHolder1.date = date;
        }

        
        
    }
    
    public static class ConfigHolder2 {

        public static String stringConfig;

        public static void set(String i) {
            stringConfig = i;
        }

        public static String get() {
            return stringConfig;
        }
    }
    
    @Before
    public void before() {
        ConfigHolder1.counter = 0;
        ConfigHolder1.date = null;
        ConfigHolder2.stringConfig = null;
        
        Config.CONFIG.deRegisterConfigCategory("ConfigHolder1");
        Config.CONFIG.deRegisterConfigCategory("ConfigHolder2");
    }

    @Test
    public void useCase_registerGroupOfConfigs() {
        //Register a config group and a name
        //Usually:
        //    the group is implemented as a class
        //    the name is field in this class
        //    the fields must be static - config values are intended for one application
        Config.CONFIG.registerConfigValue("ConfigHolder1", "counter", ConfigHolder1::getCounter, ConfigHolder1::setCounter);
        
        //We save a value
        Config.CONFIG.setValue("ConfigHolder1", "counter", 15);
        
        //We read a value
        int counterValue = Config.CONFIG.getValue("ConfigHolder1", "counter");
        
        //Asserting value
        assertEquals(15, counterValue);
        
        //Register arbitrary Object
        //getter and setter must be visible
        //getter and setter must refer to same Type
        Config.CONFIG.registerConfigValue("ConfigHolder1", "date", ConfigHolder1::getDate, ConfigHolder1::setDate);
        
        //We save a correct type
        Config.CONFIG.setValue("ConfigHolder1", "date", Instant.ofEpochMilli(1001001));
        
        //We read a value
        Instant dateValue = Config.CONFIG.getValue("ConfigHolder1", "date");
        
        //Asserting value
        assertEquals(Instant.ofEpochMilli(1001001), dateValue);
        
        //Attempting to save invalid type
        try {
            Config.CONFIG.setValue("ConfigHolder1", "date", "Invalid");
            fail("Should throw exception");
        } catch (ClassCastException e) {
            //ignore
        }
        
        //We read an invalid value
        try {
            String stringValue = Config.CONFIG.getValue("ConfigHolder1", "date");
            fail("Should throw exception");
        } catch (ClassCastException e) {
            //ignore
        }
    }
    
    @Test
    public void useCase_registerMultipleConfigs() {
        //Register a config group and a name
        Config.CONFIG.registerConfigValue("ConfigHolder1", "counter", ConfigHolder1::getCounter, ConfigHolder1::setCounter);
        Config.CONFIG.registerConfigValue("ConfigHolder1", "date", ConfigHolder1::getDate, ConfigHolder1::setDate);
        Config.CONFIG.registerConfigValue("ConfigHolder2", "string", ConfigHolder2::get, ConfigHolder2::set);

        //We save a correct type
        Config.CONFIG.setValue("ConfigHolder1", "counter", 42);
        Config.CONFIG.setValue("ConfigHolder1", "date", Instant.ofEpochMilli(1001001));
        Config.CONFIG.setValue("ConfigHolder2", "string", "A Value");

        assertEquals(Integer.valueOf(42), Config.CONFIG.getValue("ConfigHolder1", "counter"));
        assertEquals(Instant.ofEpochMilli(1001001), Config.CONFIG.getValue("ConfigHolder1", "date"));
        assertEquals("A Value", Config.CONFIG.getValue("ConfigHolder2", "string"));
    }
    
    @Test
    public void useCase_invalidOperations() {
        //Register a config group and a name
        Config.CONFIG.registerConfigValue("ConfigHolder1", "counter", ConfigHolder1::getCounter, ConfigHolder1::setCounter);
        Config.CONFIG.registerConfigValue("ConfigHolder1", "date", ConfigHolder1::getDate, ConfigHolder1::setDate);
        
        //We save an inexisting name
        try {
            Config.CONFIG.setValue("ConfigHolder1", "foo", 42);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            //ignore
        }
        
        //We save in an inexisting group
        try {
            Config.CONFIG.setValue("ConfigHolder2", "date", Instant.ofEpochMilli(1001001));
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            //ignore
        }
        
        //We read an inexisting name
        try {
            Config.CONFIG.getValue("ConfigHolder1", "string");
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            //ignore
        }
        
        //We read from an inexisting group
        try {
            Config.CONFIG.getValue("ConfigHolder2", "date");
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            //ignore
        }
    }    
}
