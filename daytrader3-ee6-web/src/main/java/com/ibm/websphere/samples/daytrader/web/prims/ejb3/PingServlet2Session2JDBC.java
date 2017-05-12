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

import javax.ejb.EJB;


import com.ibm.websphere.samples.daytrader.QuoteDataBean;
import com.ibm.websphere.samples.daytrader.TradeConfig;
import com.ibm.websphere.samples.daytrader.ejb3.DirectSLSBLocal;
import com.ibm.websphere.samples.daytrader.util.Log;

/**
 * 
 * PingServlet2Session2JDBC tests key functionality of a servlet call to a
 * stateless SessionEJB, which then performs a JDBC lookup.
 * 
 */
@WebServlet(name = "ejb3.PingServlet2Session2JDBC", urlPatterns = {"/ejb3/PingServlet2Session2JDBC"})
public class PingServlet2Session2JDBC extends HttpServlet {

    private static final long serialVersionUID = 121225510303225826L;

	private static String initTime;

    private static int hitCount;

    @EJB
    private DirectSLSBLocal directSLSBLocal;

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        res.setContentType("text/html");
        java.io.PrintWriter out = res.getWriter();
        String symbol = null;
        QuoteDataBean quoteData = null;
        StringBuffer output = new StringBuffer(100);

        output.append("<html><head><title>PingServlet2Session2JDBC</title></head>" + "<body><HR><FONT size=\"+2\" color=\"#000066\">PingServlet2Session2JDBC<BR></FONT>" + "<FONT size=\"-1\" color=\"#000066\">" + "PingServlet2Session2JDBC tests the common path of a Servlet calling a Session EJB "
                + "which in turn calls JDBC.<BR>");

        try {

            try {
                int iter = TradeConfig.getPrimIterations();
                for (int ii = 0; ii < iter; ii++) {
                    symbol = TradeConfig.rndSymbol();
                    // getQuote will call findQuote which will instaniate the
                    // Quote Entity Bean
                    // and then will return a QuoteObject
                    quoteData = directSLSBLocal.getQuote(symbol);
                }
            } catch (Exception ne) {
                Log.error(ne, "PingServlet2Session2JDBC.goGet(...): exception getting QuoteData through Trade");
                throw ne;
            }

            output.append("<HR>initTime: " + initTime).append("<BR>Hit Count: " + hitCount++);
            output.append("<HR>Quote Information<BR><BR>" + quoteData.toHTML());
            out.println(output.toString());

        } catch (Exception e) {
            Log.error(e, "PingServlet2Session2JDBC.doGet(...): General Exception caught");
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
    }
}
