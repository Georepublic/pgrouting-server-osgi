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

import org.apache.wink.json4j.JSONObject;
import org.georepublic.db.DBProc;
import org.georepublic.properties.*;
import org.georepublic.utils.*;

public class DrivingDistance extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, 
		HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		
		String json = req.getParameter("json");
		String key  = req.getParameter("key");
		
		String retval = new String();
		DBProc dbProc = new DBProc();

		ProfileProperties prop = dbProc.verifyProfileKey(key);
		
		if( prop == null) {
			out.print( "Profile Key "+key+" was not Verified" );
			out.flush();
			out.close();
			return;
		}

		if( !prop.ispEnabled() ) {
			out.print( "Profile Key "+key+" is not Enabled" );
			out.flush();
			out.close();
			return;
		}
		
		if( !prop.isPgr_dd() ) {
			out.print("DrivingDistance Searches for this Profile is not enabled");
			out.flush();
			out.close();
			return;
		}
		
		if( !prop.ispPublic() ) {
			if( !HostInfo.checkHostInfo(req.getRemoteHost(),prop.getHosts()) ) {
				if(!HostInfo.checkHostInfo(
						req.getRemoteAddr(),prop.getHosts()) ) {
					out.print( "Not authorized to use this Service[ "+
						req.getRemoteAddr() + " ]" );
					out.flush();
					out.close();
					return;
				}				
			}
		}
		
		
		if( json == null ) {
			out.print( "No JSON Request Parameter" );
			out.flush();
			out.close();
			return;
		}
		
		try {
			JSONObject jo = new JSONObject(json);
			String proj   = jo.getString("projection");
			String source = jo.getString("point");
			String mode   = jo.getString("mode");
			double dist   = jo.getDouble("distance");
			
			int epsg = Integer.parseInt(proj.split(":")[1]);
			
			if(epsg != RouteProperties.getSrid()) {
				source = dbProc.transformPoint(source, epsg);
			}
			
			String tmp = source.toUpperCase().replace("POINT(", "");
			String xy[]= tmp.replace(")", "").split(" ");

			retval = dbProc.findDrivingDistance(
					prop.getId(),
					Double.parseDouble(xy[0]), 
					Double.parseDouble(xy[1]), dist, mode,
					prop.isReverse_cost() );
		}
		catch( Exception ex ) {
			retval = "Error has occured";
			ex.printStackTrace();
		}
		
		resp.setContentType("application/json");
		
		out.print( retval );
		out.flush();
		out.close();
	}
}