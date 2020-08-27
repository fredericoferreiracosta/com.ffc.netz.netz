/*
	NETZ - Network management support system
    Copyright (C) 2011  Alana de Almeida Brand�o (alana.brandao@yahoo.com.br)
    					Frederico Ferreira Costa (fredericoferreira@live.com)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package br.netz.hosts.model;

public class HostTO {

	private String ipAddress;
	private String hostName;
	private String macAddress;
	private String viewName;
	private boolean known;
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostAddress) {
		this.hostName = hostAddress;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public boolean isKnown() {
		return known;
	}
	public void setKnown(boolean known) {
		this.known = known;
	}
	
	@Override
	public String toString() {
		return "IpAddress: " + getIpAddress() + " - HostName: " + getHostName() + " - MacAddress: " + getMacAddress() + " - ViewName: " + getViewName() + " - Known: " + isKnown();
	}
	
	
}
