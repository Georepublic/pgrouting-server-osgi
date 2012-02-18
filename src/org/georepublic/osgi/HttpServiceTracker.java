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

package org.georepublic.osgi;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import org.georepublic.properties.AdminProperties;
import org.georepublic.properties.DBProperties;
import org.georepublic.properties.RouteProperties;
import org.georepublic.servlets.*;

public class HttpServiceTracker extends ServiceTracker {

	public HttpServiceTracker(BundleContext context) {
		super(context, HttpService.class.getName(), null);
	}

	public Object addingService(ServiceReference reference) {
		HttpService httpService = (HttpService) super.addingService(reference);
		if (httpService == null)
			return null;

		//::::::::::::::::::::::::::::::::
		//:: Setting the Property classes
		//::::::::::::::::::::::::::::::::
		DBProperties.setProperties();
		RouteProperties.setProperties();
		AdminProperties.setProperties();
		//::::::::::::::::::::::::::::::::
		
		try {
			httpService.registerResources("/resources",	"/resources", null);
			
			httpService.registerServlet(
					"/routing-service/web/v1.0/admin.html", 
					new AdminHtml(), null, null);
			
			httpService.registerServlet(
					"/routing-service/web/v1.0/admin/key.json", 
					new AdminKey(), null, null);
			
			httpService.registerServlet(
					"/routing-service/web/v1.0/admin/classes.json", 
					new AdminClasses(), null, null);
			
			httpService.registerServlet(
					"/routing-service/web/v1.0/admin/resources.json", 
					new AdminResources(), null, null);
			
			httpService.registerServlet(
					"/routing-service/web/v1.0/admin/profiles.json", 
					new AdminProfiles(), null, null);
			
			httpService.registerServlet(
					"/routing-service/web/v1.0/catch.json", 
					new DrivingDistance(), null, null);
			
			httpService.registerServlet(
					"/routing-service/web/v1.0/route.json", 
					new ShortestPath(), null, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return httpService;
	}

}