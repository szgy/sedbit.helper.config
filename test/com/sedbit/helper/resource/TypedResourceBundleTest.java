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
package com.sedbit.helper.resource;

import java.time.LocalDate;
import java.time.Month;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gyula Szabo D. (sedbit.com)
 */
public class TypedResourceBundleTest {
    
    ResourceBundle rb = new ListResourceBundle() {
        @Override
        protected Object[][] getContents() {
            return new Object[][] {
                {"KeyString", "A String"},
                {"KeyInt", "42"},
                {"KeyDate", "2123-08-27"}
            };
        }
    };
    
    
    @Test
    public void testGetString() {
        //Initialize resource bundle
        TypedResourceBundle typed = new TypedResourceBundle(rb);
        
        //Get a string
        String keyString = typed.getString("KeyString");
        assertEquals("A String", keyString);
        
        //Get an int
        int keyInt = typed.getObject("KeyInt", Integer::valueOf);
        assertEquals(42, keyInt);

        //Get a LocalDate
        LocalDate keyDate = typed.getObject("KeyDate", LocalDate::parse);
        assertEquals(LocalDate.of(2123, Month.AUGUST, 27), keyDate);        
    }
}
