package org.georepublic.utils;

public class HostInfo {
	
	public static boolean checkHostInfo( String host, String lookUp[]) {		
		boolean found = false;
		
		for(int i=0;i<lookUp.length;i++) {
			if(host.compareTo(lookUp[i]) == 0){
				found = true;
				break;
			}
		}		
		
		return found;		
	}
}
