/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.example.android.bluetoothlegatt;

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static HashMap<String, String> CharacterToLookFor = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

    static {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");

        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "UUID_DEVINFO_SERV ");
        attributes.put("00002A26-0000-1000-8000-00805f9b34fb", "UUID_DEVINFO_FWREV ");

        attributes.put("f000aa00-0451-4000-b000-000000000000", "UUID_IRT_SERV ");
        attributes.put("f000aa01-0451-4000-b000-000000000000", "UUID_IRT_DATA ");
        attributes.put("f000aa02-0451-4000-b000-000000000000", "UUID_IRT_CONF "); // 0: disable, 1: enable
        attributes.put("f000aa03-0451-4000-b000-000000000000", "UUID_IRT_PERI "); // Period in tens of milliseconds

        attributes.put("f000aa10-0451-4000-b000-000000000000", "UUID_ACC_SERV ");
        attributes.put("f000aa11-0451-4000-b000-000000000000", "UUID_ACC_DATA ");
        attributes.put("f000aa12-0451-4000-b000-000000000000", "UUID_ACC_CONF "); // 0: disable, 1: enable
        attributes.put("f000aa13-0451-4000-b000-000000000000", "UUID_ACC_PERI "); // Period in tens of milliseconds

        attributes.put("f000aa20-0451-4000-b000-000000000000", "UUID_HUM_SERV ");
        attributes.put("f000aa21-0451-4000-b000-000000000000", "UUID_HUM_DATA ");
        attributes.put("f000aa22-0451-4000-b000-000000000000", "UUID_HUM_CONF "); // 0: disable, 1: enable
        attributes.put("f000aa23-0451-4000-b000-000000000000", "UUID_HUM_PERI "); // Period in tens of milliseconds

        attributes.put("f000aa30-0451-4000-b000-000000000000", "UUID_MAG_SERV ");
        attributes.put("f000aa31-0451-4000-b000-000000000000", "UUID_MAG_DATA ");
        attributes.put("f000aa32-0451-4000-b000-000000000000", "UUID_MAG_CONF "); // 0: disable, 1: enable
        attributes.put("f000aa33-0451-4000-b000-000000000000", "UUID_MAG_PERI "); // Period in tens of milliseconds

        attributes.put("f000aa70-0451-4000-b000-000000000000", "UUID_OPT_SERV ");
        attributes.put("f000aa71-0451-4000-b000-000000000000", "UUID_OPT_DATA ");
        attributes.put("f000aa72-0451-4000-b000-000000000000", "UUID_OPT_CONF "); // 0: disable, 1: enable
        attributes.put("f000aa73-0451-4000-b000-000000000000", "UUID_OPT_PERI "); // Period in tens of milliseconds

        attributes.put("f000aa40-0451-4000-b000-000000000000", "UUID_BAR_SERV ");
        attributes.put("f000aa41-0451-4000-b000-000000000000", "UUID_BAR_DATA ");
        attributes.put("f000aa42-0451-4000-b000-000000000000", "UUID_BAR_CONF "); // 0: disable, 1: enable
        attributes.put("f000aa43-0451-4000-b000-000000000000", "UUID_BAR_CALI "); // Calibration characteristic
        attributes.put("f000aa44-0451-4000-b000-000000000000", "UUID_BAR_PERI "); // Period in tens of milliseconds

        attributes.put("f000aa50-0451-4000-b000-000000000000", "UUID_GYR_SERV ");
        attributes.put("f000aa51-0451-4000-b000-000000000000", "UUID_GYR_DATA ");
        attributes.put("f000aa52-0451-4000-b000-000000000000", "UUID_GYR_CONF "); // 0: disable, bit 0: enable x, bit 1: enable y, bit 2: enable z
        attributes.put("f000aa53-0451-4000-b000-000000000000", "UUID_GYR_PERI "); // Period in tens of milliseconds

        attributes.put("f000aa80-0451-4000-b000-000000000000", "UUID_MOV_SERV ");
        attributes.put("f000aa81-0451-4000-b000-000000000000", "UUID_MOV_DATA ");
        attributes.put("f000aa82-0451-4000-b000-000000000000", "UUID_MOV_CONF "); // 0: disable, bit 0: enable x, bit 1: enable y, bit 2: enable z
        attributes.put("f000aa83-0451-4000-b000-000000000000", "UUID_MOV_PERI "); // Period in tens of milliseconds


        attributes.put("0000ffe0-0000-1000-8000-00805f9b34fb", "UUID_KEY_SERV ");
        attributes.put("0000ffe1-0000-1000-8000-00805f9b34fb", "UUID_KEY_DATA ");

        CharacterToLookFor.put("UUID_IRT_DATA ", "f000aa01-0451-4000-b000-000000000000");
//        CharacterToLookFor.put("UUID_ACC_DATA ", "f000aa11-0451-4000-b000-000000000000");
        CharacterToLookFor.put("UUID_HUM_DATA ", "f000aa21-0451-4000-b000-000000000000");
        CharacterToLookFor.put("UUID_MAG_DATA ", "f000aa31-0451-4000-b000-000000000000");
        CharacterToLookFor.put("UUID_OPT_DATA ", "f000aa71-0451-4000-b000-000000000000");
        CharacterToLookFor.put("UUID_BAR_DATA ", "f000aa41-0451-4000-b000-000000000000");
//        CharacterToLookFor.put("UUID_GYR_DATA ", "f000aa51-0451-4000-b000-000000000000");
//        CharacterToLookFor.put("UUID_MOV_DATA ", "f000aa81-0451-4000-b000-000000000000");

    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
