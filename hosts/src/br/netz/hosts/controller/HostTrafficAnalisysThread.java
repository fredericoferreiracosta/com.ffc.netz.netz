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

package br.netz.hosts.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import netz.traffic.logger.NetzLogger;
import br.netz.configuration.controller.ConfigurationController;
import br.netz.configuration.model.ConfigurationDAO;
import br.netz.configuration.model.ConfigurationDAOException;
import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;
import br.netz.hosts.model.HostDAO;
import br.netz.hosts.model.HostDAOException;
import br.netz.hosts.model.HostTO;
import br.netz.notification.controller.NetzMailException;
import br.netz.notification.controller.NotificationController;
import br.netz.traffic.model.PacketTO;
import br.netz.traffic.model.TrafficDAO;
import br.netz.traffic.model.TrafficDAOException;

public class HostTrafficAnalisysThread implements Runnable {

	private TrafficDAO trafficDAO;
	private HostDAO hostDAO;
	private ConfigurationDAO configurationDAO;
	private GeneralConfigurationTO configurationTO;
	private EmailConfigurationTO email;
	private SmsConfigurationTO sms;
	private NotificationController notificationController;
	private int lastIndex;

	public HostTrafficAnalisysThread() throws TrafficDAOException,
			HostDAOException, ConfigurationDAOException {
		trafficDAO = new TrafficDAO();
		hostDAO = new HostDAO();
		configurationDAO = new ConfigurationDAO();
		configurationTO = configurationDAO.getGeneralConfiguration();
		email = configurationDAO.getEmailConfiguration();
		sms = configurationDAO.getSmsConfiguration();
		notificationController = new NotificationController();
		lastIndex = configurationTO.getLastHostsIndex();
	}

	@Override
	public void run() {

		while (true) {
			ArrayList<PacketTO> packets = new ArrayList<PacketTO>();
			try {
				packets = trafficDAO.getPacketsBroadcastFromIndex(lastIndex,
						ConfigurationController.range + ".255");
				for (PacketTO packet : packets) {
					String hostMac = packet.getSourceMac();

					if (!ListHosts.getInstance().getOnlineHosts().containsKey(
							hostMac)) {
						int value = hostDAO.hostExists(hostMac);

						if (value == HostDAO.HOST_NONEXISTENT) {
							HostTO host = new HostTO();
							host.setKnown(false);
							host.setMacAddress(packet.getSourceMac());
							host.setIpAddress(packet.getSourceIp());
							host.setHostName(packet.getSourceName());
							host.setViewName(packet.getSourceName());
							hostDAO.saveHost(host);

							boolean online = HostController.testHostOnline(host
									.getIpAddress());
							if (online) {
								ListHosts.getInstance().addHostOnline(host);
								email = configurationDAO.getEmailConfiguration();
								sms = configurationDAO.getSmsConfiguration();

								checkNotify(host);
							}

						} else if (value == HostDAO.HOST_KNOWN) {
							HostTO hostOld = hostDAO.getHost(hostMac);
							HostTO host = hostDAO.getHost(hostMac);
							if (host.getViewName().equals(host.getIpAddress())) {
								host.setViewName(packet.getSourceIp());
							}
							if (host.getHostName().equals(host.getIpAddress())) {
								host.setHostName(packet.getSourceIp());
							}
							host.setIpAddress(packet.getSourceIp());
							hostDAO.updateHost(host);

							boolean online = HostController.testHostOnline(host
									.getIpAddress());
							if (online) {
								ListHosts.getInstance().removeOfflineHost(
										hostOld);
								ListHosts.getInstance().addHostOnline(host);
								email = configurationDAO.getEmailConfiguration();
								sms = configurationDAO.getSmsConfiguration();

								checkNotify(host);
							}

							

						} else if (value == HostDAO.HOST_UNKNOWN) {
							HostTO hostOld = hostDAO.getHost(hostMac);
							HostTO host = hostDAO.getHost(hostMac);
							if (host.getViewName().equals(host.getIpAddress())) {
								host.setViewName(packet.getSourceIp());
							}
							if (host.getHostName().equals(host.getIpAddress())) {
								host.setHostName(packet.getSourceIp());
							}
							host.setIpAddress(packet.getSourceIp());
							hostDAO.updateHost(host);

							boolean online = HostController.testHostOnline(host
									.getIpAddress());
							if (online) {
								ListHosts.getInstance().removeOfflineHost(
										hostOld);
								ListHosts.getInstance().addHostOnline(host);
								email = configurationDAO.getEmailConfiguration();
								sms = configurationDAO.getSmsConfiguration();

								checkNotify(host);
							}
						}
					}
				}
				if (packets.size() > 0) {
					PacketTO pack = packets.get(packets.size() - 1);
					if (pack != null) {
						lastIndex = pack.getId();
						configurationDAO.updateLastHostsIndex(lastIndex);
					}
				}

			} catch (TrafficDAOException e) {
				e.printStackTrace();
				NetzLogger.getInstance().error(
						"TrafficDAOException: " + e.getMessage());
			} catch (HostDAOException e) {
				e.printStackTrace();
				NetzLogger.getInstance().error(
						"HostDAOException: " + e.getMessage());
			} catch (IOException e) {
				NetzLogger.getInstance()
						.error("IOException: " + e.getMessage());
				e.printStackTrace();
			} catch (ConfigurationDAOException e) {
				NetzLogger.getInstance().error(
						"ConfigurationDAOException: " + e.getMessage());
				e.printStackTrace();
			}
			Thread.yield();
		}
	}

	private void checkNotify(HostTO hostTO) throws IOException {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			configurationTO = configurationDAO.getGeneralConfiguration();
		} catch (ConfigurationDAOException e) {
			e.printStackTrace();
		}

		if (configurationTO.isNotifyTimeEmail()
				|| configurationTO.isNotifyTimeSms()) {
			if(horaMaior(new Date(),configurationTO.getNotifyTimeFrom()) &&
					horaMenor(new Date(),configurationTO.getNotifyTimeTo())){
				if (configurationTO.isNotifyTimeEmail()) {
					email.setMessage("O dispositivo " + hostTO.getHostName()
							+ " (" + hostTO.getMacAddress()
							+ ") acaba de fazer logon na rede. Data e hora: "
							+ dateformat.format(new Date()));
					email.setSubject("NETZ - Dispositivo logou em hor�rio monitorado");
					try {
						notificationController.sendNotificationEmail(email);
					} catch (NetzMailException e) {
						e.printStackTrace();
						NetzLogger.getInstance().error("Error sending e-mail! - " + e.getMessage());
					}
				}
				if (configurationTO.isNotifyTimeSms()) {
					sms.setMessage("O dispositivo " + hostTO.getHostName()
							+ " (" + hostTO.getMacAddress()
							+ ") acaba de fazer logon na rede. Data e hora: "
							+ dateformat.format(new Date()));
					notificationController.sendNotificationSMS(sms);
				}
				return;
			}
		}

		if (!hostTO.isKnown()) {
			if (configurationTO.isNotifyUnknownHostEmail()) {
				email
						.setMessage("O dispositivo "
								+ hostTO.getHostName()
								+ "("
								+ hostTO.getMacAddress()
								+ ") de status desconhecido acaba de entrar na rede. Data e hora: "
								+ dateformat.format(new Date()));
				email.setSubject("NETZ - Novo dispositivo desconhecido");
				try {
					notificationController.sendNotificationEmail(email);
				} catch (NetzMailException e) {
					e.printStackTrace();
					NetzLogger.getInstance().error("Error sending e-mail! - " + e.getMessage());
				}
				return;
			}
			if (configurationTO.isNotifyUnknownHostSms()) {
				sms
						.setMessage("O dispositivo "
								+ hostTO.getHostName()
								+ "("
								+ hostTO.getMacAddress()
								+ ") de status desconhecido acaba de entrar na rede. Data e hora: "
								+ dateformat.format(new Date()));
				notificationController.sendNotificationSMS(sms);
				return;
			}
		}
		if (configurationTO.isNotifyMacsEmail()
				|| configurationTO.isNotifyMacsSms()) {
			String macsList[] = configurationTO.getNotifyMacs().split(";");

			for (int i = 0; i < macsList.length; i++) {
				
				if (hostTO.getMacAddress().equals(macsList[i])) {
					if (configurationTO.isNotifyMacsEmail()) {
						email
								.setMessage("O dispositivo "
										+ hostTO.getHostName()
										+ "("
										+ hostTO.getMacAddress()
										+ ") listado nas notifica��es de MAC acaba de entrar na rede. Data e hora: "
										+ dateformat.format(new Date()));
						email.setSubject("NETZ - Monitoramento de dispositivo (s)");
						try {
							notificationController.sendNotificationEmail(email);
						} catch (NetzMailException e) {
							e.printStackTrace();
							NetzLogger.getInstance().error("Error sending e-mail! - " + e.getMessage());
						}
					}
					if (configurationTO.isNotifyMacsSms()) {
						sms
								.setMessage("O dispositivo "
										+ hostTO.getHostName()
										+ "("
										+ hostTO.getMacAddress()
										+ ") listado nas notificacoes de MAC acaba de entrar na rede. Data e hora: "
										+ dateformat.format(new Date()));
						notificationController.sendNotificationSMS(sms);
					}
					return;
				}
			}
		}

	}

	private boolean horaMaior(Date d1, Date d2) {
		d1 = formatHora(d1);
		d2 = formatHora(d2);
		if (d1.getTime() > d2.getTime()) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean horaMenor(Date d1, Date d2) {
		d1 = formatHora(d1);
		d2 = formatHora(d2);
		if (d1.getTime() < d2.getTime()) {
			return true;
		} else {
			return false;
		}
	}
	
	private Date formatHora(Date date){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm"); 
		String hora = format.format(date);
		
		Date formatedDate;
		try {
			formatedDate = format.parse(hora);	
		} catch (ParseException e) {
			formatedDate = null;
		}
		return formatedDate;
	}
}
