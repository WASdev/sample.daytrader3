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
package com.ibm.websphere.samples.daytrader.web.prims;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ibm.websphere.samples.daytrader.util.*;



/**
 *
 * PingServletSetContentLength tests fundamental dynamic HTML creation functionality through
 * server side servlet processing.
 *
 */

@WebServlet(name = "PingServletSetContentLength", urlPatterns = {"/servlet/PingServletSetContentLength"})
public class PingServletSetContentLength extends HttpServlet
{

	private static final long serialVersionUID = 8731300373855056661L;

	/**
	 * forwards post requests to the doGet method
	 * Creation date: (02/07/2013 10:52:39 AM)
	 * @param res javax.servlet.http.HttpServletRequest
	 * @param res2 javax.servlet.http.HttpServletResponse
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		doGet(req, res);
	}
	/**
		* this is the main method of the servlet that will service all get requests.
		* @param request HttpServletRequest
		* @param responce HttpServletResponce
		**/
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		try
			{
			res.setContentType("text/html");
			String lengthParam=req.getParameter("contentLength");
			Integer length;
			
			if (lengthParam == null)
			    length = 0;
			else
			    length = Integer.parseInt(lengthParam);
						
			ServletOutputStream out = res.getOutputStream();
						
			// Add characters (a's) to the SOS to equal the requested length
			// 167 is the smallest length possible.
			
			// TODO Improve how to do this?
			int i=0;
            String buffer="";
			
            while (i + 167 < length)
			{
			    buffer = buffer + "a";
			    i++;
			}
                                                
			out.println(
				"<html><head><title>Ping Servlet</title></head>"
					+ "<body><HR><BR><FONT size=\"+2\" color=\"#000066\">Ping Servlet<BR></FONT><FONT size=\"+1\" color=\"#000066\">"
					+ buffer
					+ "</B></body></html>");
		}
		catch (Exception e)
			{
			Log.error(e, "PingServlet.doGet(...): general exception caught");
			res.sendError(500, e.toString());

		}
	}
	/** 
	 * returns a string of information about the servlet
	 * @return info String: contains info about the servlet
	 **/
	public String getServletInfo()
	{
		return "Basic dynamic HTML generation through a servlet, with " +
				"contentLength set by contentLength parameter.";
	}
	/**
	* called when the class is loaded to initialize the servlet
	* @param config ServletConfig:
	**/
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
	}
}