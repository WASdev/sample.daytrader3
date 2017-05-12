package com.ibm.websphere.samples.daytrader.web.jsf;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ibm.websphere.samples.daytrader.QuoteDataBean;
import com.ibm.websphere.samples.daytrader.TradeConfig;
import com.ibm.websphere.samples.daytrader.direct.TradeDirect;
import com.ibm.websphere.samples.daytrader.ejb3.DirectSLSBLocal;
import com.ibm.websphere.samples.daytrader.ejb3.TradeSLSBLocal;
import com.ibm.websphere.samples.daytrader.util.Log;

@ManagedBean
@RequestScoped
public class QuoteBean {

	//Local Interface to Session Bean used for the Entity Bean lookup
	@EJB
	private TradeSLSBLocal tradeSLSBBeanLocal;
	
	//Local Interface to Session Bean used for the Session Direct lookup
	@EJB
	private DirectSLSBLocal directSLSBLocal;

	private TradeDirect tradeDirect;
	
	private QuoteDataBean quoteDataBean;
		
	// Generates a random stock symbol to lookup
	public QuoteBean() {
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
	
	@PostConstruct
	public void quoteLookup() {
		
		final int symbolNum = (int) (Math.random()*(TradeConfig.getMAX_QUOTES()-1));  // 10,000 quotes is normal database size
		
		if (TradeConfig.runTimeMode == TradeConfig.EJB3) {
			try {			
				quoteDataBean = tradeSLSBBeanLocal.getQuote("s:" + symbolNum);
			} catch (Exception e) {
				Log.error("QuoteBean:quoteLookup -- error getting quote", e);
			}			 
		}
		else if (TradeConfig.runTimeMode == TradeConfig.SESSION3) {
			try {			
				quoteDataBean = directSLSBLocal.getQuote("s:" + symbolNum);
			} catch (Exception e) {
				Log.error("QuoteBean:quoteLookup -- error getting quote", e);
			}		
		}
		else if (TradeConfig.runTimeMode == TradeConfig.DIRECT) {
			try {			
				quoteDataBean = tradeDirect.getQuote("s:" + symbolNum);
			} catch (Exception e) {
				Log.error("QuoteBean:quoteLookup -- error getting quote", e);
			}	
		}
		

	}
	
	public QuoteDataBean getQuoteDataBean() {
		return quoteDataBean;
	}
	
}
