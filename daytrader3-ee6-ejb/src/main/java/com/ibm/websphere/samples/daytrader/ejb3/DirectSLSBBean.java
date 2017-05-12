/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ibm.websphere.samples.daytrader.ejb3;

import com.ibm.websphere.samples.daytrader.AccountDataBean;
import com.ibm.websphere.samples.daytrader.AccountProfileDataBean;
import com.ibm.websphere.samples.daytrader.HoldingDataBean;
import com.ibm.websphere.samples.daytrader.MarketSummaryDataBean;
import com.ibm.websphere.samples.daytrader.OrderDataBean;
import com.ibm.websphere.samples.daytrader.QuoteDataBean;
import com.ibm.websphere.samples.daytrader.RunStatsDataBean;
import com.ibm.websphere.samples.daytrader.direct.TradeDirect;

import java.math.BigDecimal;
import java.util.Collection;
import javax.ejb.*;


@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DirectSLSBBean implements DirectSLSBLocal {

	public DirectSLSBBean() {
	}

	public MarketSummaryDataBean getMarketSummary() throws Exception {
		return (new TradeDirect(true)).getMarketSummary();
	}


	public OrderDataBean buy(String userID, String symbol, double quantity, int orderProcessingMode) throws Exception {
		return (new TradeDirect(true)).buy(userID, symbol, quantity, orderProcessingMode);
	}

	public OrderDataBean sell(String userID, Integer holdingID, int orderProcessingMode) throws Exception {
		return (new TradeDirect(true)).sell(userID, holdingID, orderProcessingMode);
	}

	public void queueOrder(Integer orderID, boolean twoPhase) throws Exception {
		(new TradeDirect(true)).queueOrder(orderID, twoPhase);
	}

	public OrderDataBean completeOrder(Integer orderID, boolean twoPhase) throws Exception {
		return (new TradeDirect(true)).completeOrder(orderID, twoPhase);
	}

	public void cancelOrder(Integer orderID, boolean twoPhase) throws Exception {
		(new TradeDirect(true)).cancelOrder(orderID, twoPhase);
	}

	public void orderCompleted(String userID, Integer orderID) throws Exception {
		(new TradeDirect(true)).orderCompleted(userID, orderID);
	}

	public Collection<?> getOrders(String userID) throws Exception {
		return (new TradeDirect(true)).getOrders(userID);
	}

	public Collection<?> getClosedOrders(String userID) throws Exception {
		return (new TradeDirect(true)).getClosedOrders(userID);
	}

	public QuoteDataBean createQuote(String symbol, String companyName, BigDecimal price) throws Exception {
		return (new TradeDirect(true)).createQuote(symbol, companyName, price);
	}

	public QuoteDataBean getQuote(String symbol) throws Exception {
		return (new TradeDirect(true)).getQuote(symbol);
	}

	public Collection<?> getAllQuotes() throws Exception {
		return (new TradeDirect(true)).getAllQuotes();
	}

	public QuoteDataBean updateQuotePriceVolume(String symbol, BigDecimal newPrice, double sharesTraded) throws Exception {
		return (new TradeDirect(true)).updateQuotePriceVolume(symbol, newPrice, sharesTraded);
	}

	public Collection<?> getHoldings(String userID) throws Exception {
		return (new TradeDirect(true)).getHoldings(userID);
	}

	public HoldingDataBean getHolding(Integer holdingID) throws Exception {
		return (new TradeDirect(true)).getHolding(holdingID);
	}

	public AccountDataBean getAccountData(String userID) throws Exception {
		return (new TradeDirect(true)).getAccountData(userID);
	}

	public AccountProfileDataBean getAccountProfileData(String userID) throws Exception {
		return (new TradeDirect(true)).getAccountProfileData(userID);
	}

	public AccountProfileDataBean updateAccountProfile(AccountProfileDataBean profileData) throws Exception {
		return (new TradeDirect(true)).updateAccountProfile(profileData);
	}

	public AccountDataBean login(String userID, String password) throws Exception {
		return (new TradeDirect(true)).login(userID, password);
	}

	public void logout(String userID) throws Exception {
		(new TradeDirect(true)).logout(userID);
	}

	public AccountDataBean register(String userID, String password, String fullname, String address, String email, String creditcard, BigDecimal openBalance) throws Exception {
		return (new TradeDirect(true)).register(userID, password, fullname, address, email, creditcard, openBalance);
	}

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public RunStatsDataBean resetTrade(boolean deleteAll) throws Exception {
		return (new TradeDirect(false)).resetTrade(deleteAll);
	}
}
