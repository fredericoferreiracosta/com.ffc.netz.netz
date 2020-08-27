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

package br.netz.configuration.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import netz.traffic.logger.NetzLogger;
public class ConfigurationDAO {
	
	private Connection connection = null;

	public ConfigurationDAO() throws ConfigurationDAOException {
		connection = ConnectionManager.getConnection();
	}

	public void saveSmsConfiguration(SmsConfigurationTO smsConfigurationTO) throws ConfigurationDAOException{
		
		PreparedStatement sttm = null;
		try {
			
			String sql = "insert into smsConfiguration(url) values(?) returning id";
			
			sttm = connection.prepareStatement(sql);
			
			sttm.setString(1, smsConfigurationTO.getUrl());
			
			ResultSet rs = sttm.executeQuery();  
			  
			if(rs.next()){
			    int id = rs.getInt("id"); 
			    smsConfigurationTO.setId(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
			throw new ConfigurationDAOException("Error while communicating with database");
		}finally{
			close(sttm);
		}
	}
	
	public void saveEmailConfiguration(EmailConfigurationTO emailConfigurationTO) throws ConfigurationDAOException{
		
		PreparedStatement sttm = null;
		try {
			
			String sql = "insert into emailconfiguration(hostname,emailto,emailfrom,emailuser,password,smtpport,ssl,tls) values(?,?,?,?,?,?,?,?) returning id";
			
			sttm = connection.prepareStatement(sql);
			
			sttm.setString(1, emailConfigurationTO.getHostName());
			sttm.setString(2, emailConfigurationTO.getTo());
			sttm.setString(3, emailConfigurationTO.getFrom());
			sttm.setString(4, emailConfigurationTO.getUser());
			sttm.setString(5, emailConfigurationTO.getPassword());
			sttm.setInt(6, emailConfigurationTO.getSmtpPort());
			sttm.setBoolean(7, emailConfigurationTO.isSsl());
			sttm.setBoolean(8, emailConfigurationTO.isTls());
			
			ResultSet rs = sttm.executeQuery();  
			  
			if(rs.next()){
			    int id = rs.getInt("id"); 
			    emailConfigurationTO.setId(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
			throw new ConfigurationDAOException("Error while communicating with database");
		}finally{
			close(sttm);
		}
	}
	
	public void saveGeneralConfiguration(GeneralConfigurationTO configurationTO) throws ConfigurationDAOException{
		PreparedStatement sttm = null;
		try {
			
			String sql = "insert into generalConfiguration(language,deleteData,deleteDataTime, " +
					"notifyHostEntry,notifyHostEntryMode,highlightUnknownHost,notifyTrafficLevel," +
					"notifyTrafficSms,notifyTrafficEmail,notifyUnknownHostSms,notifyUnknownHostEmail," +
					"notifyMacs,notifyMacsSms,notifyMacsEmail,notifyTimeFrom,notifyTimeTo,notifyTimeEmail,notifyTimeSms," +
					"graphicUnit,graphicUpdate,networkInterface,lastgraphicindex,lasthostsindex,lasttcpviewerindex) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) returning id";
			
			sttm = connection.prepareStatement(sql);
			
			sttm.setString(1, configurationTO.getLanguage());
			sttm.setBoolean(2, configurationTO.isDeleteData());
			sttm.setString(3, configurationTO.getDeleteDataTime());
			sttm.setBoolean(4, configurationTO.isNotifyHostEntry());
			sttm.setString(5, configurationTO.getNotifyHostEntryMode());
			sttm.setBoolean(6, configurationTO.isHighlightUnknownHost());
			sttm.setInt(7, configurationTO.getNotifyTrafficLevel());
			sttm.setBoolean(8, configurationTO.isNotifyTrafficSms());
			sttm.setBoolean(9, configurationTO.isNotifyTrafficEmail());
			sttm.setBoolean(10, configurationTO.isNotifyUnknownHostSms());
			sttm.setBoolean(11, configurationTO.isNotifyUnknownHostEmail());
			sttm.setString(12, configurationTO.getNotifyMacs());
			sttm.setBoolean(13, configurationTO.isNotifyMacsSms());
			sttm.setBoolean(14, configurationTO.isNotifyMacsEmail());
			if(configurationTO.getNotifyTimeFrom()!=null){
				try{
					java.sql.Timestamp notifyTimeFrom = new java.sql.Timestamp(configurationTO.getNotifyTimeFrom().getTime()); 
					sttm.setTimestamp(15, notifyTimeFrom);
				}catch (Exception e) {
					sttm.setTimestamp(15, null);
				}
			}
			else{
				sttm.setTimestamp(15, null);
			}
			if(configurationTO.getNotifyTimeTo()!=null){
				try{
				java.sql.Timestamp notifyTimeTo = new java.sql.Timestamp(configurationTO.getNotifyTimeTo().getTime()); 
				sttm.setTimestamp(16, notifyTimeTo);
				}catch (Exception e) {
					sttm.setTimestamp(16, null);
				}
			}
			else{
				sttm.setTimestamp(16, null);
			}
			sttm.setBoolean(17, configurationTO.isNotifyTimeEmail());
			sttm.setBoolean(18, configurationTO.isNotifyTimeSms());
			sttm.setString(19, configurationTO.getGraphicUnit());
			sttm.setInt(20, configurationTO.getGraphicUpdate());
			sttm.setString(21, configurationTO.getNetworkInterface());
			sttm.setInt(22, configurationTO.getLastGraphicIndex());
			sttm.setInt(23, configurationTO.getLastHostsIndex());
			sttm.setInt(24, configurationTO.getLastTCPViewerIndex());

			ResultSet rs = sttm.executeQuery();  
			  
			if(rs.next()){
			    int id = rs.getInt("id"); 
			    configurationTO.setId(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
			throw new ConfigurationDAOException("Error while communicating with database");
		}finally{
			close(sttm);
		}
	}
	
	public void updateSmsConfiguration(SmsConfigurationTO smsConfigurationTO) throws ConfigurationDAOException{
		PreparedStatement sttm = null;
		try {
			
			if(smsConfigurationTO.getId()==0){
				SmsConfigurationTO temp = getSmsConfiguration();
				smsConfigurationTO.setId(temp.getId());
			}
			
			String sql = "update smsConfiguration set url=? where id = ?";
			
			sttm = connection.prepareStatement(sql);
			
			sttm.setString(1, smsConfigurationTO.getUrl());
			sttm.setInt(2, smsConfigurationTO.getId());
			
			sttm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
			throw new ConfigurationDAOException("Error while communicating with database");
		}finally{
			close(sttm);
		}
	}
	
	public void updateEmailConfiguration(EmailConfigurationTO emailConfigurationTO) throws ConfigurationDAOException{
		PreparedStatement sttm = null;
		try {
			
			if(emailConfigurationTO.getId()==0){
				EmailConfigurationTO temp = getEmailConfiguration();
				emailConfigurationTO.setId(temp.getId());
			}
			
			String sql = "update emailConfiguration set hostName=?,emailto=?,emailfrom=?,emailuser=?,password=?,smtpPort=?,ssl=?,tls=? where id=?";
			
			sttm = connection.prepareStatement(sql);
			
			sttm.setString(1, emailConfigurationTO.getHostName());
			sttm.setString(2, emailConfigurationTO.getTo());
			sttm.setString(3, emailConfigurationTO.getFrom());
			sttm.setString(4, emailConfigurationTO.getUser());
			sttm.setString(5, emailConfigurationTO.getPassword());
			sttm.setInt(6, emailConfigurationTO.getSmtpPort());
			sttm.setBoolean(7, emailConfigurationTO.isSsl());
			sttm.setBoolean(8, emailConfigurationTO.isTls());
			sttm.setInt(9, emailConfigurationTO.getId());
			
			sttm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
			throw new ConfigurationDAOException("Error while communicating with database");
		}finally{
			close(sttm);
		}
	}
	
	public void updateLastGraphicIndex(int index) throws ConfigurationDAOException{
		
		PreparedStatement sttm = null;
		try {
			
			GeneralConfigurationTO configurationTO = getGeneralConfiguration();
			
			String sql = "update generalConfiguration set lastgraphicindex=? where id=?";
			
			sttm = connection.prepareStatement(sql);
			
			sttm.setInt(1, index);
			sttm.setInt(2, configurationTO.getId());
			
			sttm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
			throw new ConfigurationDAOException("Error while communicating with database");
		}finally{
			close(sttm);
		}
	}
	
	public void updateLastHostsIndex(int index) throws ConfigurationDAOException{
		
		PreparedStatement sttm = null;
		try {
			
			GeneralConfigurationTO configurationTO = getGeneralConfiguration();
			
			String sql = "update generalConfiguration set lasthostsindex=? where id=?";
			
			sttm = connection.prepareStatement(sql);
			
			sttm.setInt(1, index);
			sttm.setInt(2, configurationTO.getId());
			
			sttm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
			throw new ConfigurationDAOException("Error while communicating with database");
		}finally{
			close(sttm);
		}
	}
	
	public void updateLastTCPViewerIndex(int index) throws ConfigurationDAOException{
		
		PreparedStatement sttm = null;
		try {
			
			GeneralConfigurationTO configurationTO = getGeneralConfiguration();
			
			String sql = "update generalConfiguration set lasttcpviewerindex=? where id=?";
			
			sttm = connection.prepareStatement(sql);
			
			sttm.setInt(1, index);
			sttm.setInt(2, configurationTO.getId());
			
			sttm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
			throw new ConfigurationDAOException("Error while communicating with database");
		}finally{
			close(sttm);
		}
	}
	
	public void updateGeneralConfiguration(GeneralConfigurationTO configurationTO) throws ConfigurationDAOException{
		PreparedStatement sttm = null;
		try {
			
			if(configurationTO.getId()==0){
				GeneralConfigurationTO temp = getGeneralConfiguration();
				configurationTO.setId(temp.getId());
			}
			
			String sql = "update generalConfiguration set language=?,deleteData=?,deleteDataTime=?, " +
					"notifyHostEntry=?,notifyHostEntryMode=?,highlightUnknownHost=?,notifyTrafficLevel=?," +
					"notifyTrafficSms=?,notifyTrafficEmail=?,notifyUnknownHostSms=?,notifyUnknownHostEmail=?," +
					"notifyMacs=?,notifyMacsSms=?,notifyMacsEmail=?,notifyTimeFrom=?,notifyTimeTo=?,notifyTimeEmail=?,notifyTimeSms=?," +
					"graphicUnit=?,graphicUpdate=?,networkInterface=? where id=?";
			
			sttm = connection.prepareStatement(sql);
			
			sttm.setString(1, configurationTO.getLanguage());
			sttm.setBoolean(2, configurationTO.isDeleteData());
			sttm.setString(3, configurationTO.getDeleteDataTime());
			sttm.setBoolean(4, configurationTO.isNotifyHostEntry());
			sttm.setString(5, configurationTO.getNotifyHostEntryMode());
			sttm.setBoolean(6, configurationTO.isHighlightUnknownHost());
			sttm.setInt(7, configurationTO.getNotifyTrafficLevel());
			sttm.setBoolean(8, configurationTO.isNotifyTrafficSms());
			sttm.setBoolean(9, configurationTO.isNotifyTrafficEmail());
			sttm.setBoolean(10, configurationTO.isNotifyUnknownHostSms());
			sttm.setBoolean(11, configurationTO.isNotifyUnknownHostEmail());
			sttm.setString(12, configurationTO.getNotifyMacs());
			sttm.setBoolean(13, configurationTO.isNotifyMacsSms());
			sttm.setBoolean(14, configurationTO.isNotifyMacsEmail());
			
			java.sql.Timestamp notifyTimeFrom = null;
			if(configurationTO.getNotifyTimeFrom()!=null){
				try{
				notifyTimeFrom = new java.sql.Timestamp(configurationTO.getNotifyTimeFrom().getTime()); 
				}
				catch (Exception e) {
					sttm.setTimestamp(15, null);
				}
			}
			sttm.setTimestamp(15, notifyTimeFrom);

			java.sql.Timestamp notifyTimeTo = null;
			if(configurationTO.getNotifyTimeTo()!=null){
				try{
				notifyTimeTo = new java.sql.Timestamp(configurationTO.getNotifyTimeTo().getTime()); 
				sttm.setTimestamp(16, notifyTimeTo);
				}catch (Exception e) {
					sttm.setTimestamp(16, null);
				}
			}
			sttm.setTimestamp(16, notifyTimeTo);
			
			sttm.setBoolean(17, configurationTO.isNotifyTimeEmail());
			sttm.setBoolean(18, configurationTO.isNotifyTimeSms());
			sttm.setString(19, configurationTO.getGraphicUnit());
			sttm.setInt(20, configurationTO.getGraphicUpdate());
			sttm.setString(21, configurationTO.getNetworkInterface());
			sttm.setInt(22, configurationTO.getId());
			
			sttm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
			throw new ConfigurationDAOException("Error while communicating with database");
		}finally{
			close(sttm);
		}
	}
	
	public SmsConfigurationTO getSmsConfiguration() throws ConfigurationDAOException{
		return getSmsConfiguration(0);
	}
	
	public SmsConfigurationTO getSmsConfiguration(int index) throws ConfigurationDAOException{
		PreparedStatement sttm = null;
		ResultSet rs = null;
		try {
		
			if(index == 0){
				String sql = "select * from smsConfiguration";
				sttm = connection.prepareStatement(sql);
			}
			else{
				String sql = "select * from smsConfiguration where id = ?";
				sttm = connection.prepareStatement(sql);
				sttm.setInt(1, index);
			}
			
			rs = sttm.executeQuery();
			//rs.next();

			while(rs.next()){
				int id = rs.getInt("id");
				String url = rs.getString("url");
				
				SmsConfigurationTO smsConfigurationTO = new SmsConfigurationTO();
				smsConfigurationTO.setId(id);
				smsConfigurationTO.setUrl(url);
				return smsConfigurationTO;
			}
			return null;
		
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
			throw new ConfigurationDAOException("Error while communicating with database");
		}finally{
			close(sttm, rs);
		}
	}
	
	
	public EmailConfigurationTO getEmailConfiguration() throws ConfigurationDAOException{
		return getEmailConfiguration(0);
	}
	
	public EmailConfigurationTO getEmailConfiguration(int index) throws ConfigurationDAOException{
		PreparedStatement sttm = null;
		ResultSet rs = null;
		try {
		
			if(index==0){
				String sql = "select * from emailConfiguration";
				sttm = connection.prepareStatement(sql);
			}
			else{
				String sql = "select * from emailConfiguration where id = ?";
				sttm = connection.prepareStatement(sql);
				sttm.setInt(1, index);
			}

			rs = sttm.executeQuery();
			//rs.next();
			
			while(rs.next()){
				String from = rs.getString("emailfrom");
				String hostName = rs.getString("hostName");
				int id = rs.getInt("id");
				String password = rs.getString("password");
				int smtpPort = rs.getInt("smtpPort");
				boolean ssl = rs.getBoolean("ssl");
				boolean tls = rs.getBoolean("tls");
				String to = rs.getString("emailto");
				String user = rs.getString("emailuser");
				
				EmailConfigurationTO emailConfigurationTO = new EmailConfigurationTO();
				emailConfigurationTO.setFrom(from);
				emailConfigurationTO.setHostName(hostName);
				emailConfigurationTO.setId(id);
				emailConfigurationTO.setPassword(password);
				emailConfigurationTO.setSmtpPort(smtpPort);
				emailConfigurationTO.setSsl(ssl);
				emailConfigurationTO.setTls(tls);
				emailConfigurationTO.setTo(to);
				emailConfigurationTO.setUser(user);
				
				return emailConfigurationTO;
			}
			return null;
		
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
			throw new ConfigurationDAOException("Error while communicating with database");
		}finally{
			close(sttm, rs);
		}
	}
	
	public GeneralConfigurationTO getGeneralConfiguration() throws ConfigurationDAOException{
		return getGeneralConfiguration(0);
	}
	
	
	public GeneralConfigurationTO getGeneralConfiguration(int index) throws ConfigurationDAOException{
		PreparedStatement sttm = null;
		ResultSet rs = null;
		try {
		
			if(index==0){
				String sql = "select * from generalConfiguration limit 1";
				sttm = connection.prepareStatement(sql);
			}
			else{
				String sql = "select * from generalConfiguration where id = ?";
				sttm = connection.prepareStatement(sql);
				sttm.setInt(1, index);
			}
			
			rs = sttm.executeQuery();
			//rs.next();
			
			while(rs.next()){
				boolean deleteData = rs.getBoolean("deleteData");
				String deleteDataTime = rs.getString("deleteDataTime");
				String graphicUnit = rs.getString("graphicUnit");
				int graphicUpdate = rs.getInt("graphicUpdate");
				boolean highlightUnknownHost = rs.getBoolean("highlightUnknownHost");
				int id = rs.getInt("id");
				String language = rs.getString("language");
				boolean notifyHostEntry = rs.getBoolean("notifyHostEntry");
				String notifyHostEntryMode = rs.getString("notifyHostEntryMode");
				String notifyMacs = rs.getString("notifyMacs");
				boolean notifyMacsEmail = rs.getBoolean("notifyMacsEmail");
				boolean notifyMacsSms = rs.getBoolean("notifyMacsSms");
				boolean notifyTimeEmail = rs.getBoolean("notifyTimeEmail");
				boolean notifyTimeSms = rs.getBoolean("notifyTimeSms");
				java.util.Date notifyTimeFrom = rs.getTimestamp("notifyTimeFrom");
				java.util.Date notifyTimeTo = rs.getTimestamp("notifyTimeTo");
				boolean notifyTrafficEmail = rs.getBoolean("notifyTrafficEmail");
				int notifyTrafficLevel = rs.getInt("notifyTrafficLevel");
				boolean notifyTrafficSms = rs.getBoolean("notifyTrafficSms");
				boolean notifyUnknownHostEmail = rs.getBoolean("notifyUnknownHostEmail");
				boolean notifyUnknownHostSms = rs.getBoolean("notifyUnknownHostSms");
				String networkInterface = rs.getString("networkInterface");
				int lastGraphicIndex = rs.getInt("lastGraphicIndex");
				int lastHostsIndex = rs.getInt("lastHostsIndex");
				int lastTCPViewerIndex = rs.getInt("lastTCPViewerIndex");

				
				GeneralConfigurationTO configurationTO = new GeneralConfigurationTO();
				configurationTO.setDeleteData(deleteData);
				configurationTO.setDeleteDataTime(deleteDataTime);
				configurationTO.setGraphicUnit(graphicUnit);
				configurationTO.setGraphicUpdate(graphicUpdate);
				configurationTO.setHighlightUnknownHost(highlightUnknownHost);
				configurationTO.setId(id);
				configurationTO.setLanguage(language);
				configurationTO.setNotifyHostEntry(notifyHostEntry);
				configurationTO.setNotifyHostEntryMode(notifyHostEntryMode);
				configurationTO.setNotifyMacs(notifyMacs);
				configurationTO.setNotifyMacsEmail(notifyMacsEmail);
				configurationTO.setNotifyMacsSms(notifyMacsSms);
				configurationTO.setNotifyTimeEmail(notifyTimeEmail);
				configurationTO.setNotifyTimeSms(notifyTimeSms);
				configurationTO.setNotifyTimeFrom(notifyTimeFrom);
				configurationTO.setNotifyTimeTo(notifyTimeTo);
				configurationTO.setNotifyTrafficEmail(notifyTrafficEmail);
				configurationTO.setNotifyTrafficLevel(notifyTrafficLevel);
				configurationTO.setNotifyTrafficSms(notifyTrafficSms);
				configurationTO.setNotifyUnknownHostEmail(notifyUnknownHostEmail);
				configurationTO.setNotifyUnknownHostSms(notifyUnknownHostSms);
				configurationTO.setNetworkInterface(networkInterface);
				configurationTO.setLastGraphicIndex(lastGraphicIndex);
				configurationTO.setLastHostsIndex(lastHostsIndex);
				configurationTO.setLastTCPViewerIndex(lastTCPViewerIndex);

				return configurationTO;
			}
			return null;
		
		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error("Error while communicating with database");
			throw new ConfigurationDAOException("Error while communicating with database");
		}finally{
			close(sttm, rs);
		}
	}
	
	public void deleteGeneralConfiguration() {
		
		Statement sttm = null;
		
			String sql = "DELETE FROM generalconfiguration";
			
			try {
				sttm = connection.createStatement();
				sttm.executeUpdate(sql);
			} catch (SQLException e) {
				System.out.println("Erro ao tentar apagar hosts do banco.");
				e.printStackTrace();
			}
	}
	
	public void deleteEmailConfiguration() {
		
		Statement sttm = null;
		
			String sql = "DELETE FROM emailconfiguration";
			
			try {
				sttm = connection.createStatement();
				sttm.executeUpdate(sql);
			} catch (SQLException e) {
				System.out.println("Erro ao tentar apagar hosts do banco.");
				e.printStackTrace();
			}
	}
	
	public void deleteSmsConfiguration() {
		
		Statement sttm = null;
		
			String sql = "DELETE FROM smsconfiguration";
			
			try {
				sttm = connection.createStatement();
				sttm.executeUpdate(sql);
			} catch (SQLException e) {
				System.out.println("Erro ao tentar apagar hosts do banco.");
				e.printStackTrace();
			}
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
