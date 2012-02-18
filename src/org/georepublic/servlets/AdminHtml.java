/* 
 * pgRouting Server
 * Copyright 2012, Georepublic. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.	
 */

package org.georepublic.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.georepublic.properties.*;
import org.georepublic.utils.*;

public class AdminHtml extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, 
		HttpServletResponse resp) throws ServletException, IOException {

		if(!HostInfo.checkHostInfo(req.getRemoteHost(),
				AdminProperties.getHttp_allowed()) ) {
			if(! HostInfo.checkHostInfo(req.getRemoteAddr(),
					AdminProperties.getHttp_allowed()) ) {
				PrintWriter out = resp.getWriter();
				out.write("Not authorized to use this Service["+
						req.getRemoteAddr() +"]");
				return;
			}				 
		}
		
		req.getRequestDispatcher("/resources/html/admin.html").forward(
					req, resp);
		
	}
}