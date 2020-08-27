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

package netz.gui.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import netz.gui.listeners.FramePrincipalListener;
import netz.gui.main.FramePrincipal;
import netz.traffic.logger.NetzLogger;
import br.netz.configuration.controller.ConfigurationController;
import br.netz.configuration.model.ConfigurationDAO;
import br.netz.configuration.model.ConfigurationDAOException;
import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;
import br.netz.hosts.controller.HostController;
import br.netz.hosts.controller.ListHosts;
import br.netz.hosts.model.HostDAO;
import br.netz.hosts.model.HostDAOException;
import br.netz.hosts.model.HostTO;
import br.netz.notification.controller.NetzSystemTray;
import br.netz.notification.controller.NotificationController;
import br.netz.traffic.controller.TrafficController;
import br.netz.traffic.listener.TrafficControllerListener;
import br.netz.traffic.model.PacketTO;
import br.netz.traffic.model.TrafficDAO;
import br.netz.traffic.model.TrafficDAOException;

public class GUIController {

	private TrafficController trafficController;
	private FramePrincipal framePrincipal;
	private NotificationController notificationController;
	private ConfigurationDAO configurationDAO;
	private HostDAO hostDAO;
	private ImageIcon icon;
	
	private UnificadaController unificadaController;
	private ConfiguracoesController configuracoesController;

	
	public GUIController() throws ConfigurationDAOException, HostDAOException {
	
		try {
			configureDatabase();

			notificationController = new NotificationController();
			configurationDAO = new ConfigurationDAO();
			hostDAO = new HostDAO();
			unificadaController = new UnificadaController(hostDAO,getFramePrincipal());
			configuracoesController = new ConfiguracoesController(configurationDAO,notificationController,getFramePrincipal());

		} catch (TrafficDAOException e) {
			e.printStackTrace();
		}
		
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		UIManager.put("swing.boldMetal", Boolean.FALSE);
	}

	private FramePrincipal getFramePrincipal() throws TrafficDAOException {
		if (framePrincipal == null) {

			framePrincipal = new FramePrincipal();
			icon = new ImageIcon(getClass().getResource("images/netzIconFrame.png"));
			framePrincipal.setIconImage(icon.getImage());
			framePrincipal
					.addFramePrincipalListener(new FramePrincipalListener() {

						@Override
						public void configurarEmail() {
							configuracoesController.configurarEmail();
						}

						@Override
						public void configurarSms() {
							configuracoesController.configurarSms();
						}

						@Override
						public void salvarConfiguracoesGerais(
								GeneralConfigurationTO generalConfiguration) {
							configuracoesController.salvarConfiguracoesGerais(generalConfiguration);
						}

						@Override
						public void limparHistorico() {
							configuracoesController.limparHistorico();
						}

						@Override
						public void editarUsuario(HostTO hostTO) {
							unificadaController.editarUsuario(hostTO);
						}

						@Override
						public void adicionarUsuario(HostTO hostTO) {
							unificadaController.salvarUsuario(hostTO);
						}

					});
		}
		return framePrincipal;
	}

	
	
	private TrafficController getTrafficController() {
		if (trafficController == null) {
			trafficController = TrafficController.getInstance();
			trafficController
					.addTrafficControllerListener(new TrafficControllerListener() {

						@Override
						public void newPacket(PacketTO packet) {
							try {
								getFramePrincipal().addPacket(packet);
							} catch (TrafficDAOException e) {
								e.printStackTrace();
							}
						}

					});
		}
		return trafficController;
	}
	
	

	

	public void initNetz() throws ConfigurationDAOException {
		try {
			
			ConfigurationDAO configurationDAO = new ConfigurationDAO();
			GeneralConfigurationTO configurationTO = new GeneralConfigurationTO();
			configurationTO = configurationDAO.getGeneralConfiguration();
			
			if(configurationTO.isDeleteData()){
				String temp = configurationTO.getDeleteDataTime();
				int deleteData = 0;
				try{
					deleteData = Integer.parseInt(temp);
				}catch (Exception e) {
				}
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				
				switch (deleteData) {
				case GeneralConfigurationTO.DELETE_ONE_DAY:
					calendar.add(Calendar.DAY_OF_MONTH, -1);
					break;
				case GeneralConfigurationTO.DELETE_ONE_WEEK:
					calendar.add(Calendar.DAY_OF_MONTH, -7);
					break;
				case GeneralConfigurationTO.DELETE_ONE_MONTH:
					calendar.add(Calendar.MONTH, -1);
					break;
				case GeneralConfigurationTO.DELETE_THREE_MONTHS:
					calendar.add(Calendar.MONTH, -3);
					break;
				case GeneralConfigurationTO.DELETE_SIX_MONTHS:
					calendar.add(Calendar.MONTH, -6);
					break;
				case GeneralConfigurationTO.DELETE_ONE_YEAR:
					calendar.add(Calendar.YEAR, -1);
					break;
				default:
					break;
				}
				TrafficDAO trafficDAO = new TrafficDAO();
				trafficDAO.deletePacketsFromDate(calendar.getTime());
			}

			new NetzSystemTray();

			getFramePrincipal();


			InetAddress in = InetAddress.getLocalHost();
			String range = in.getHostAddress();

			int index = range.lastIndexOf(".");
			range = range.substring(0, index);

			ConfigurationController.setRange(range);
			
			HostDAO hostDAO = new HostDAO();
			ArrayList<HostTO> hosts = hostDAO.getAllHosts();
			for(HostTO host:hosts){
				ListHosts.getInstance().addHostOffline(host);
			}

			getTrafficController();

			TrafficController.initSniffer();
			
			new HostController();
			
			getFramePrincipal().setVisible(true);

		} catch (UnknownHostException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"UnknownHostException: " + e.getMessage());
		} catch (TrafficDAOException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"TrafficDAOException: " + e.getMessage());
		} catch (HostDAOException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"HostDAOException: " + e.getMessage());
		}

	}

	public void initJustGui() throws TrafficDAOException {
		getFramePrincipal().setVisible(true);
	}

	private void configureDatabase() throws ConfigurationDAOException,
			TrafficDAOException {
		ConfigurationDAO configurationDAO = new ConfigurationDAO();

		GeneralConfigurationTO generalConfigurationTO = configurationDAO
				.getGeneralConfiguration();
		EmailConfigurationTO emailConfigurationTO = configurationDAO
				.getEmailConfiguration();
		SmsConfigurationTO smsConfigurationTO = configurationDAO
				.getSmsConfiguration();

		if (generalConfigurationTO == null) {
			generalConfigurationTO = new GeneralConfigurationTO();
			generalConfigurationTO.setHighlightUnknownHost(false);
			generalConfigurationTO.setNotifyMacsEmail(false);
			generalConfigurationTO.setDeleteData(false);
			generalConfigurationTO.setNotifyMacsEmail(false);
			generalConfigurationTO.setNotifyMacsSms(false);
			generalConfigurationTO.setNotifyTrafficSms(false);
			generalConfigurationTO.setNotifyTrafficEmail(false);
			generalConfigurationTO.setNotifyUnknownHostEmail(false);
			generalConfigurationTO.setNotifyUnknownHostSms(false);
			generalConfigurationTO.setGraphicUpdate(3);
			configurationDAO.saveGeneralConfiguration(generalConfigurationTO);
		} else {
			generalConfigurationTO = configurationDAO.getGeneralConfiguration();
		}

		if (emailConfigurationTO == null) {
			emailConfigurationTO = new EmailConfigurationTO();
			emailConfigurationTO.setTls(false);
			emailConfigurationTO.setSsl(false);
			configurationDAO.saveEmailConfiguration(emailConfigurationTO);
		} else {
			emailConfigurationTO = configurationDAO.getEmailConfiguration();
		}

		if (smsConfigurationTO == null) {
			smsConfigurationTO = new SmsConfigurationTO();
			configurationDAO.saveSmsConfiguration(smsConfigurationTO);
		} else {
			smsConfigurationTO = configurationDAO.getSmsConfiguration();
		}

		getFramePrincipal().setConfigurations(smsConfigurationTO,
				emailConfigurationTO, generalConfigurationTO);
	}

}
