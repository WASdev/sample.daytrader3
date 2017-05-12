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

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path(value = "/addresses")
public class AddressBook {

    static {

          Address Addr = new Address("Entry5", "12345", "Address", "City", "State", "Country");
          AddressBookDatabase.storeAddress("Entry5", Addr);

          Addr = new Address("Entry4", "12345", "Address", "City", "State", "Country");
          AddressBookDatabase.storeAddress("Entry4", Addr);

          Addr = new Address("Entry3", "12345", "Address", "City", "State", "Country");
          AddressBookDatabase.storeAddress("Entry3", Addr);
          
          Addr = new Address("Entry2", "12345", "Address", "City", "State", "Country");
          AddressBookDatabase.storeAddress("Entry2", Addr);

          Addr = new Address("Entry1", "12345", "Address", "City", "State", "Country");
          AddressBookDatabase.storeAddress("Entry1", Addr);

    }

    public AddressBook() {

    }

    @GET
    @Path(value = "/search/{searchstring}")
    @Produces(value = { "application/json" })
    public AddressList search(@PathParam(value = "searchstring") String searchString) {
        AddressList addressList = new AddressList();
        List<Address> addresses = addressList.getAddresses();
        Iterator<Address> addressIter = AddressBookDatabase.getAddresses();
        while (addressIter.hasNext()) {
            Address address = addressIter.next();
            if (address.getEntryName().startsWith(searchString)) {
                addresses.add(address);
            }
        }
        return addressList;
    }

    @GET
    @Produces(value = { "application/json" })
    public AddressList getAddresses() {
        AddressList addressList = new AddressList();
        List<Address> addresses = addressList.getAddresses();
        Iterator<Address> addressIter = AddressBookDatabase.getAddresses();
        while (addressIter.hasNext()) {
            Address address = addressIter.next();
            addresses.add(address);
        }
        return addressList;
    }

    @Path("/{entryName}")
    public Address getAddress(@PathParam(value = "entryName") String entryName) {
        Address addr = AddressBookDatabase.getAddress(entryName);
        return addr;
    }


}
