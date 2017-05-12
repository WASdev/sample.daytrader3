/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.ibm.websphere.samples.daytrader.rest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Sample that will mock a database for the AddressBook resource
 * @author Dustin
 *
 */
public class AddressBookDatabase {

    private static Map<String, Address> addressMap = new HashMap<String, Address>();

    public static Address getAddress(String entryName) {
        return addressMap.get(entryName);
    }

    public static void storeAddress(String entryName, Address address) {
        addressMap.put(entryName, address);
    }

    public static Iterator<Address> getAddresses() {
        return addressMap.values().iterator();
    }

    public static void removeAddress(String entryName) {
        addressMap.remove(entryName);
    }

    public static void clearEntries() {
        if (addressMap != null) {
            addressMap.clear();
        }
    }

}
