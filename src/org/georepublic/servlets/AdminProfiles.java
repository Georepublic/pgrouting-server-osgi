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

import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONObject;
import org.georepublic.db.DBProc;
import org.georepublic.properties.*;
import org.georepublic.utils.*;

public class AdminProfiles extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		if(!HostInfo.checkHostInfo(req.getRemoteHost(),
				AdminProperties.getHttp_allowed()) ) {
			if(! HostInfo.checkHostInfo(req.getRemoteAddr(),
					AdminProperties.getHttp_allowed()) ) {
				
				out.write("Not authorized to use this Service["+
						req.getRemoteAddr() +"]");
				return;
			}				 
		}
		
		String json   = req.getParameter("json");
		String retval = new String();
		DBProc dbProc = new DBProc();
		
		if( json == null || json.length() < 5) {
			retval = dbProc.getProfiles(null);
		}
		else {
			String key = new String();
			try {
				JSONObject jo  = new JSONObject(json);
				JSONArray keys = jo.getJSONArray("key");
				
				if( keys != null ) {
					StringBuffer sb = new StringBuffer();
					sb.append("'").append(keys.getString(0)).append("'");
					
					for(int i=1;i<keys.length();i++)
						sb.append(",'").append(keys.getString(1)).append("'");
					
					key = sb.toString();
				}
				retval = dbProc.getProfiles(key);
			}
			catch(Exception e) {
				e.printStackTrace();
				
				try {
					JSONObject jo     = new JSONObject();
					JSONArray  data   = new JSONArray();
					JSONObject status = new JSONObject();
					
					status.put("code"   , new Integer(404));
					status.put("success", false);
					status.put("message","JSON parse error");
					
					jo.put("data"  , data);
					jo.put("status", status);
					
					retval = jo.toString();
				}
				catch( Exception exception ) {;}
			}
		}		 
		
		resp.setContentType("application/json");
		out.print( retval );
		out.flush();
		out.close();
		
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		PrintWriter out = resp.getWriter();
		
		if(!HostInfo.checkHostInfo(req.getRemoteHost(),
				AdminProperties.getHttp_allowed()) ) {
			if(! HostInfo.checkHostInfo(req.getRemoteAddr(),
					AdminProperties.getHttp_allowed()) ) {
				
				out.write("Not authorized to use this Service["+
						req.getRemoteAddr() +"]");
				return;
			}				 
		}
		
		String json   = req.getParameter("json");
		
		if(json == null || json.length() < 10 ) {
			try {
				JSONObject jo     = new JSONObject();
				JSONObject status = new JSONObject();
				
				status.put("code"   , new Integer(404));
				status.put("success", false);
				status.put("message","JSON parse error");
				
				jo.put("status", status);
				
				out.write( jo.toString() );
				return;
			}
			catch( Exception exception ) {;}
		}
		DBProc dbProc = new DBProc();
		resp.setContentType("application/json");
		
		out.print( dbProc.insertProfile(json) );
		out.flush();
		out.close();
	}


	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		if(!HostInfo.checkHostInfo(req.getRemoteHost(),
				AdminProperties.getHttp_allowed()) ) {
			if(! HostInfo.checkHostInfo(req.getRemoteAddr(),
					AdminProperties.getHttp_allowed()) ) {
				
				out.write("Not authorized to use this Service["+
						req.getRemoteAddr() +"]");
				return;
			}				 
		}
		
		String json   = req.getParameter("json");
		
		if(json == null || json.length() < 10 ) {
			try {
				JSONObject jo     = new JSONObject();
				JSONObject status = new JSONObject();
				
				status.put("code"   , new Integer(404));
				status.put("success", false);
				status.put("message","JSON parse error");
				
				jo.put("status", status);
				
				out.write( jo.toString() );
				out.close();
				return;
			}
			catch( Exception exception ) {;}
		}
		DBProc dbProc = new DBProc();
		resp.setContentType("application/json");
		
		out.print( dbProc.insertProfile(json) );
		out.flush();
		out.close();
		
		return;
	}


	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		if(!HostInfo.checkHostInfo(req.getRemoteHost(),
				AdminProperties.getHttp_allowed()) ) {
			if(! HostInfo.checkHostInfo(req.getRemoteAddr(),
					AdminProperties.getHttp_allowed()) ) {
				
				out.write("Not authorized to use this Service["+
						req.getRemoteAddr() +"]");
				return;
			}				 
		}
		
		String json   = req.getParameter("json");
		
		if(json == null || json.length() < 5 ) {
			try {
				JSONObject jo     = new JSONObject();
				JSONObject status = new JSONObject();
				
				status.put("code"   , new Integer(404));
				status.put("success", false);
				status.put("message","JSON parse error");
				
				jo.put("status", status);
				
				out.write( jo.toString() );
				out.close();
				return;
			}
			catch( Exception exception ) {;}
		}
		DBProc dbProc = new DBProc();
		resp.setContentType("application/json");
		
		out.print( dbProc.deleteProfiles(json) );
		out.flush();
		out.close();
		
		return;
	}
	
	
}