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

import java.util.Date;

public class GeneralConfigurationTO {
	
	public static final int DELETE_ONE_DAY = 0;
	public static final int DELETE_ONE_WEEK = 1;
	public static final int DELETE_ONE_MONTH = 2;
	public static final int DELETE_THREE_MONTHS = 3;
	public static final int DELETE_SIX_MONTHS = 4;
	public static final int DELETE_ONE_YEAR = 5;
	
	private int id;
	private String language;
	private boolean deleteData;
	private String deleteDataTime;
	private boolean notifyHostEntry;
	private String notifyHostEntryMode;
	private boolean highlightUnknownHost;
	private int notifyTrafficLevel;
	private boolean notifyTrafficSms;
	private boolean notifyTrafficEmail;
	private boolean notifyUnknownHostSms;
	private boolean notifyUnknownHostEmail;
	private String notifyMacs;
	private boolean notifyMacsSms;
	private boolean notifyMacsEmail;
	private Date notifyTimeFrom;
	private Date notifyTimeTo;
	private boolean notifyTimeEmail;
	private boolean notifyTimeSms;
	private String graphicUnit;
	private int graphicUpdate;
	private String networkInterface;
	private int lastGraphicIndex;
	private int lastHostsIndex;
	private int lastTCPViewerIndex;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isDeleteData() {
		return deleteData;
	}
	public void setDeleteData(boolean deleteData) {
		this.deleteData = deleteData;
	}
	public String getDeleteDataTime() {
		return deleteDataTime;
	}
	public void setDeleteDataTime(String deleteDataTime) {
		this.deleteDataTime = deleteDataTime;
	}
	public boolean isNotifyHostEntry() {
		return notifyHostEntry;
	}
	public void setNotifyHostEntry(boolean notifyHostEntry) {
		this.notifyHostEntry = notifyHostEntry;
	}
	public String getNotifyHostEntryMode() {
		return notifyHostEntryMode;
	}
	public void setNotifyHostEntryMode(String notifyHostEntryMode) {
		this.notifyHostEntryMode = notifyHostEntryMode;
	}
	public boolean isHighlightUnknownHost() {
		return highlightUnknownHost;
	}
	public void setHighlightUnknownHost(boolean highlightUnknownHost) {
		this.highlightUnknownHost = highlightUnknownHost;
	}
	public int getNotifyTrafficLevel() {
		return notifyTrafficLevel;
	}
	public void setNotifyTrafficLevel(int notifyTrafficLevel) {
		this.notifyTrafficLevel = notifyTrafficLevel;
	}
	public boolean isNotifyTrafficSms() {
		return notifyTrafficSms;
	}
	public void setNotifyTrafficSms(boolean notifyTrafficSms) {
		this.notifyTrafficSms = notifyTrafficSms;
	}
	public boolean isNotifyTrafficEmail() {
		return notifyTrafficEmail;
	}
	public void setNotifyTrafficEmail(boolean notifyTrafficEmail) {
		this.notifyTrafficEmail = notifyTrafficEmail;
	}
	public boolean isNotifyUnknownHostSms() {
		return notifyUnknownHostSms;
	}
	public void setNotifyUnknownHostSms(boolean notifyUnknownHostSms) {
		this.notifyUnknownHostSms = notifyUnknownHostSms;
	}
	public boolean isNotifyUnknownHostEmail() {
		return notifyUnknownHostEmail;
	}
	public void setNotifyUnknownHostEmail(boolean notifyUnknownHostEmail) {
		this.notifyUnknownHostEmail = notifyUnknownHostEmail;
	}
	public String getNotifyMacs() {
		return notifyMacs;
	}
	public void setNotifyMacs(String notifyMacs) {
		this.notifyMacs = notifyMacs;
	}
	public boolean isNotifyMacsSms() {
		return notifyMacsSms;
	}
	public void setNotifyMacsSms(boolean notifyMacsSms) {
		this.notifyMacsSms = notifyMacsSms;
	}
	public boolean isNotifyMacsEmail() {
		return notifyMacsEmail;
	}
	public void setNotifyMacsEmail(boolean notifyMacsEmail) {
		this.notifyMacsEmail = notifyMacsEmail;
	}
	public Date getNotifyTimeFrom() {
		return notifyTimeFrom;
	}
	public void setNotifyTimeFrom(Date notifyTimeFrom) {
		this.notifyTimeFrom = notifyTimeFrom;
	}
	public Date getNotifyTimeTo() {
		return notifyTimeTo;
	}
	public void setNotifyTimeTo(Date notifyTimeTo) {
		this.notifyTimeTo = notifyTimeTo;
	}
	public boolean isNotifyTimeEmail() {
		return notifyTimeEmail;
	}
	public void setNotifyTimeEmail(boolean notifyTimeEmail) {
		this.notifyTimeEmail = notifyTimeEmail;
	}
	public boolean isNotifyTimeSms() {
		return notifyTimeSms;
	}
	public void setNotifyTimeSms(boolean notifyTimeSms) {
		this.notifyTimeSms = notifyTimeSms;
	}
	public String getGraphicUnit() {
		return graphicUnit;
	}
	public void setGraphicUnit(String graphicUnit) {
		this.graphicUnit = graphicUnit;
	}
	public int getGraphicUpdate() {
		return graphicUpdate;
	}
	public void setGraphicUpdate(int graphicUpdate) {
		this.graphicUpdate = graphicUpdate;
	}
	public String getNetworkInterface() {
		return networkInterface;
	}
	public void setNetworkInterface(String networkInterface) {
		this.networkInterface = networkInterface;
	}
	public int getLastGraphicIndex() {
		return lastGraphicIndex;
	}
	public void setLastGraphicIndex(int lastGraphicIndex) {
		this.lastGraphicIndex = lastGraphicIndex;
	}
	public int getLastHostsIndex() {
		return lastHostsIndex;
	}
	public void setLastHostsIndex(int lastHostsIndex) {
		this.lastHostsIndex = lastHostsIndex;
	}
	public int getLastTCPViewerIndex() {
		return lastTCPViewerIndex;
	}
	public void setLastTCPViewerIndex(int lastTCPViewerIndex) {
		this.lastTCPViewerIndex = lastTCPViewerIndex;
	}
	
}
