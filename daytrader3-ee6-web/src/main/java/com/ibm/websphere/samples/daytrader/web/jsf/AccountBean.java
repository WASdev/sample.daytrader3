package com.ibm.websphere.samples.daytrader.web.jsf;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlDataTable;

import com.ibm.websphere.samples.daytrader.AccountDataBean;
import com.ibm.websphere.samples.daytrader.AccountProfileDataBean;
import com.ibm.websphere.samples.daytrader.HoldingDataBean;
import com.ibm.websphere.samples.daytrader.TradeConfig;
import com.ibm.websphere.samples.daytrader.direct.TradeDirect;
import com.ibm.websphere.samples.daytrader.ejb3.DirectSLSBLocal;
import com.ibm.websphere.samples.daytrader.ejb3.TradeSLSBLocal;
import com.ibm.websphere.samples.daytrader.util.Log;

@ManagedBean
@RequestScoped
public class AccountBean {

	//Local Interface to Session Bean used for the Entity Bean lookup
	@EJB
	private TradeSLSBLocal tradeSLSBBeanLocal;
	
	//Local Interface to Session Bean used for the Session Direct lookup
	@EJB
	private DirectSLSBLocal directSLSBLocal;

	private TradeDirect tradeDirect;
		
	//JPA Entities
	private AccountDataBean accountDataBean;
	private AccountProfileDataBean accountProfileDataBean;
	private Collection<HoldingDataBean> holdingsCollection;
	
	//Array of holdings associated with a User, used by the JSF DataTable
	private HoldingDataBean[] holdings;
	
	private HtmlDataTable dataTable;

	
	// Generates a random stock symbol to lookup
	public AccountBean() {
		super();
		
		if (TradeConfig.runTimeMode == TradeConfig.DIRECT) {
			try {
				tradeDirect = new TradeDirect();
            }
            catch (Exception e) {
                Log.error("TradeAction:TradeAction() Creation of Trade Direct failed\n" + e);
            }            
		}
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void userLookup() {
	    
		final int userNum = (int) (Math.random()* (TradeConfig.getMAX_USERS()-1));  // 15,000 quotes is normal database size
		final String userID = "uid:" + userNum;		
		
		if (TradeConfig.runTimeMode == TradeConfig.EJB3) {
			try {					
				accountDataBean = tradeSLSBBeanLocal.getAccountData(userID);
				accountProfileDataBean = tradeSLSBBeanLocal.getAccountProfileData(userID);
				holdingsCollection = (Collection<HoldingDataBean>) tradeSLSBBeanLocal.getHoldings(userID);
				
				//Convert the Collection to an Array for the JSF DataTable
				holdings = holdingsCollection.toArray(new HoldingDataBean[holdingsCollection.size()] );

			} catch (Exception e) {
				Log.error("AccountBean -- error getting account", e);
			} 
		}
		else if (TradeConfig.runTimeMode == TradeConfig.SESSION3) {
			try {					
				accountDataBean = directSLSBLocal.getAccountData(userID);
				accountProfileDataBean = directSLSBLocal.getAccountProfileData(userID);
				holdingsCollection = (Collection<HoldingDataBean>) directSLSBLocal.getHoldings(userID);
				
				//Convert the Collection to an Array for the JSF DataTable
				holdings = holdingsCollection.toArray(new HoldingDataBean[holdingsCollection.size()] );

			} catch (Exception e) {
				Log.error("AccountBean -- error getting account", e);
			}
		}
		else if (TradeConfig.runTimeMode == TradeConfig.DIRECT) {
			try {					
				accountDataBean = tradeDirect.getAccountData(userID);
				accountProfileDataBean = tradeDirect.getAccountProfileData(userID);
				holdingsCollection = (Collection<HoldingDataBean>) tradeDirect.getHoldings(userID);
				
				//Convert the Collection to an Array for the JSF DataTable
				holdings = holdingsCollection.toArray(new HoldingDataBean[holdingsCollection.size()] );

			} catch (Exception e) {
				Log.error("AccountBean -- error getting account", e);
			}
		}
		
		
	}
	
	
	// Getters
	public AccountDataBean getAccountDataBean() {
		return accountDataBean;
	}
	
	public AccountProfileDataBean getAccountProfileDataBean() {
		return accountProfileDataBean;
	}
	
	public Collection<HoldingDataBean> getHoldingsCollection() {
		return holdingsCollection;
	}
	
	public HoldingDataBean[] getHoldings() {
		return holdings;
	}

	public HtmlDataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(HtmlDataTable dataTable) {
		this.dataTable = dataTable;
	}

}
