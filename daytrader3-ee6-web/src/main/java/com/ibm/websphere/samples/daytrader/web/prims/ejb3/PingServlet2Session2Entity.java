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
package com.ibm.websphere.samples.daytrader.web.prims.ejb3;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.annotation.WebServlet;

import javax.naming.*;
import javax.ejb.EJB;

import com.ibm.websphere.samples.daytrader.QuoteDataBean;
import com.ibm.websphere.samples.daytrader.TradeConfig;
import com.ibm.websphere.samples.daytrader.ejb3.TradeSLSBLocal;
import com.ibm.websphere.samples.daytrader.util.Log;

/**
 * 
 * PingServlet2Session2Entity tests key functionality of a servlet call to a
 * stateless SessionEJB, and then to a Entity EJB representing data in a
 * database. This servlet makes use of the Stateless Session EJB {@link Trade},
 * and then uses {@link TradeConfig} to generate a random stock symbol. The
 * stocks price is looked up using the Quote Entity EJB.
 * 
 */
@WebServlet(name = "ejb3.PingServlet2Session2Entity", urlPatterns = {"/ejb3/PingServlet2Session2Entity"})
public class PingServlet2Session2Entity extends HttpServlet {

    private static final long serialVersionUID = -5043457201022265012L;

	private static String initTime;

    private static int hitCount;

    @EJB
    private TradeSLSBLocal tradeSLSBLocal;

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        res.setContentType("text/html");
        java.io.PrintWriter out = res.getWriter();
        String symbol = null;
        QuoteDataBean quoteData = null;
        StringBuffer output = new StringBuffer(100);

        output.append("<html><head><title>PingServlet2Session2Entity</title></head>" + "<body><HR><FONT size=\"+2\" color=\"#000066\">PingServlet2Session2Entity<BR></FONT>" + "<FONT size=\"-1\" color=\"#000066\">" + "PingServlet2Session2Entity tests the common path of a Servlet calling a Session EJB "
                + "which in turn calls an Entity EJB.<BR>");

        try {
            try {
                int iter = TradeConfig.getPrimIterations();
                for (int ii = 0; ii < iter; ii++) {
                    symbol = TradeConfig.rndSymbol();
                    // getQuote will call findQuote which will instaniate the
                    // Quote Entity Bean
                    // and then will return a QuoteObject
                    quoteData = tradeSLSBLocal.getQuote(symbol);
                }
            } catch (Exception ne) {
                Log.error(ne, "PingServlet2Session2Entity.goGet(...): exception getting QuoteData through Trade");
                throw ne;
            }

            output.append("<HR>initTime: " + initTime).append("<BR>Hit Count: " + hitCount++);
            output.append("<HR>Quote Information<BR><BR>" + quoteData.toHTML());
            out.println(output.toString());

        } catch (Exception e) {
            Log.error(e, "PingServlet2Session2Entity.doGet(...): General Exception caught");
            res.sendError(500, "General Exception caught, " + e.toString());
        }
    }

    public String getServletInfo() {
        return "web primitive, tests Servlet to Session to Entity EJB path";

    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        hitCount = 0;
        initTime = new java.util.Date().toString();
        
        if (tradeSLSBLocal == null) {
            Log.error("PingServlet2Session2Entity:init - Injection of tradeSLSBLocal failed - performing JNDI lookup!");
            
            try {
                InitialContext context = new InitialContext();
                tradeSLSBLocal = (TradeSLSBLocal) context.lookup("java:comp/env/ejb/TradeSLSBBean");                
            } catch (Exception ex) {
                Log.error("PingServlet2Session2Entity:init - Lookup of tradeSLSBLocal failed!!!");
                ex.printStackTrace();
            }
        }
    }
}