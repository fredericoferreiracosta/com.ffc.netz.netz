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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import netz.traffic.logger.NetzLogger;

public class HostDAO {

	public static final int HOST_KNOWN = 0;
	public static final int HOST_UNKNOWN = 1;
	public static final int HOST_NONEXISTENT = 2;
	
	private Connection connection = null;
	
	public HostDAO() throws HostDAOException {
		connection = ConnectionManager.getConnection();
	}
	
	public void saveHost(HostTO hostTO) throws HostDAOException{
		if(hostTO!=null) {
			
			PreparedStatement sttm = null;
			
			try {
				String sql = "insert into host(ipaddress, hostname, macaddress, viewname, known) values (?,?,?,?,?)";
				sttm = connection.prepareStatement(sql);
				
				sttm.setString(1, hostTO.getIpAddress());
				sttm.setString(2, hostTO.getHostName());
				sttm.setString(3, hostTO.getMacAddress());
				sttm.setString(4, hostTO.getViewName());
				sttm.setBoolean(5, hostTO.isKnown());
				
				sttm.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
				NetzLogger.getInstance().error("Error while communicating with database");
				throw new HostDAOException("Error while communicating with database");
			} finally {
				close(sttm);
			}
			
		}
	}
	
	public void updateHost(HostTO hostTO) throws HostDAOException {
		if(hostTO!=null) {
			
			PreparedStatement sttm = null;
			
			try {
				String sql = "update host set ipaddress = ?, hostname = ?, viewname = ?, known = ? where macaddress = ?";
				sttm = connection.prepareStatement(sql);
				
				sttm.setString(1, hostTO.getIpAddress());
				sttm.setString(2, hostTO.getHostName());
				sttm.setString(3, hostTO.getViewName());
				sttm.setBoolean(4, hostTO.isKnown());
				sttm.setString(5, hostTO.getMacAddress());

				sttm.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
				NetzLogger.getInstance().error("Error while communicating with database");
				throw new HostDAOException("Error while communicating with database");
			} finally {
				close(sttm);
			}
		}
	}
	
	public HostTO getHost(String macAddress) throws HostDAOException {
		if(!macAddress.equals(null)) {
			
			PreparedStatement sttm = null;
			ResultSet rs = null;
			HostTO hostTO = null;
			
			try {
				String sql = "select * from host where macAddress = ?";
				sttm = connection.prepareStatement(sql);
				sttm.setString(1, macAddress);
				rs = sttm.executeQuery();
				rs.next();
				
				if(rs!=null) {
					String ipAddress = rs.getString("ipAddress");
					String hostName = rs.getString("hostName");
					String macAddress2 = rs.getString("macAddress");
					String viewName = rs.getString("viewName");
					boolean known = rs.getBoolean("known");
					
					hostTO = new HostTO();
					hostTO.setIpAddress(ipAddress);
					hostTO.setHostName(hostName);
					hostTO.setMacAddress(macAddress2);
					hostTO.setViewName(viewName);
					hostTO.setKnown(known);
					
					return hostTO;
				}
								
			} catch (SQLException e) {
				e.printStackTrace();
				NetzLogger.getInstance().error("Error while communicating with database");
				throw new HostDAOException("Error while communicating with database");
			} finally {
				close(sttm, rs);
			}
		}
		
		return null;
	}
	
	public int hostExists(String macAddress) throws HostDAOException {
		if(!macAddress.equals(null)) {
			
			PreparedStatement sttm = null;
			ResultSet rs = null;
			
			try {
				String sql = "select * from host where macAddress = ?";
				sttm = connection.prepareStatement(sql);
				sttm.setString(1, macAddress);
				rs = sttm.executeQuery();
				
				if(rs.next()) {
					boolean known = rs.getBoolean("known");
					if (known) {
						return HOST_KNOWN;
					} else {
						return HOST_UNKNOWN;
					}
				} else {
					return HOST_NONEXISTENT;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				NetzLogger.getInstance().error("Error while communicating with database");
				throw new HostDAOException("Error while communicating with database");
			}finally {
				close(sttm, rs);
			}
			
		}
		
		return HOST_NONEXISTENT;
	}
	
	public void deleteAllHosts() {
		
		Statement sttm = null;
		
			String sql = "DELETE FROM host";
			
			try {
				sttm = connection.createStatement();
				sttm.executeUpdate(sql);
			} catch (SQLException e) {
				System.out.println("Erro ao tentar apagar hosts do banco.");
				e.printStackTrace();
			}
	}
	
	public ArrayList<HostTO> getAllHosts() {

		PreparedStatement sttm = null;
		ResultSet rs = null;
		ArrayList<HostTO> hosts = new ArrayList<HostTO>();
		
		try {
			String sql = "select * from host";
			sttm = connection.prepareStatement(sql);
			rs = sttm.executeQuery();
			
			while(rs.next()) {
				
				String ipAddress = rs.getString("ipAddress");
				String hostName = rs.getString("hostName");
				String macAddress2 = rs.getString("macAddress");
				String viewName = rs.getString("viewName");
				boolean known = rs.getBoolean("known");
				
				HostTO hostTO = new HostTO();
				hostTO.setIpAddress(ipAddress);
				hostTO.setHostName(hostName);
				hostTO.setMacAddress(macAddress2);
				hostTO.setViewName(viewName);
				hostTO.setKnown(known);
				
				hosts.add(hostTO);
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
		} finally {
			close(sttm, rs);
		}
		return hosts;
	}
	
	private void close(PreparedStatement sttm, ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {}
		}
		if(sttm!=null){
			try {
				sttm.close();
			} catch (SQLException e) {}
		}
	}
	
	private void close(Statement sttm){
		if(sttm!=null){
			try {
				sttm.close();
			} catch (SQLException e) {}
		}
	}

	
}
