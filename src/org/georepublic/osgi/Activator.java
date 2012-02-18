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

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private HttpServiceTracker serviceTracker;

	public void start(BundleContext context) throws Exception {
		serviceTracker = new HttpServiceTracker(context);
		serviceTracker.open();
	}

	public void stop(BundleContext context) throws Exception {
		serviceTracker.close();
		serviceTracker = null;
	}

}
