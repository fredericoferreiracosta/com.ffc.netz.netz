package br.netz.graphic.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Timer;

import br.netz.configuration.model.ConfigurationDAO;
import br.netz.configuration.model.ConfigurationDAOException;
import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;
import br.netz.graphic.view.GraphicPanel.Graphic;
import br.netz.notification.controller.NetzMailException;
import br.netz.notification.controller.NotificationController;
import br.netz.traffic.model.PacketTO;
import br.netz.traffic.model.TrafficDAO;
import br.netz.traffic.model.TrafficDAOException;

public class DataGenerator extends Timer implements ActionListener {

	private static final long serialVersionUID = -1830062100202668654L;
	private int index;
	private TrafficDAO trafficDAO;
	private ConfigurationDAO configurationDAO;
	private Graphic graphic;
	private EmailConfigurationTO email;
	private SmsConfigurationTO sms;
	private NotificationController notificationController;
	private Date lastNotification;
	
	private float totalSize = 0f;

	public DataGenerator(int interval, Graphic graphic) {
		super(interval, null);
		index = 0;
		this.graphic = graphic;
		addActionListener(this);
		try {
			trafficDAO = new TrafficDAO();
			configurationDAO = new ConfigurationDAO();
			GeneralConfigurationTO config = configurationDAO.getGeneralConfiguration();
			index = config.getLastGraphicIndex();
			email = configurationDAO.getEmailConfiguration();
			sms = configurationDAO.getSmsConfiguration();
			notificationController = new NotificationController();
		} catch (TrafficDAOException e) {
			e.printStackTrace();
		} catch (ConfigurationDAOException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent event) {
		graphic.addTraffic(getSize());
	}

	public float getSize(){
		float size = 0f;
		try {
			ArrayList<PacketTO> packets = trafficDAO.getPacketsFromIndex(index);
			for(PacketTO packet : packets){
				size = size + packet.getPacketLength();
			}
			if(packets.size()>0){
				PacketTO pack =  packets.get(packets.size()-1);
				if(pack !=null){
					index = pack.getId();
					configurationDAO.updateLastGraphicIndex(index);
				}
			}
			notifyTrafficLevel(size / 1048576);
		} catch (TrafficDAOException e) {
			e.printStackTrace();
		} catch (ConfigurationDAOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		totalSize = totalSize + (size / 1048576);
		//System.out.println("TOTAL: " + totalSize + " Rate (): " + size / 1048576);
		return size / 1048576;
	}

	private void notifyTrafficLevel(float size) throws ConfigurationDAOException, IOException {
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		GeneralConfigurationTO configurationTO = configurationDAO.getGeneralConfiguration();
		int levelMB = configurationTO.getNotifyTrafficLevel();
		
		if(levelMB>0){
			Calendar calendar = Calendar.getInstance();
			if(lastNotification!=null){
				calendar.setTime(lastNotification);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
			//se o trafego ultrapassou o esperado e (nao houve notificacao ou agora a um dia maior que a ultima notificacao)
			if(size>levelMB && (lastNotification==null || new Date().after(calendar.getTime()))){
				if (configurationTO.isNotifyUnknownHostEmail()) {
					email
							.setMessage("O nivel de trafego ultrapassou o valor maximo " +
									"configurado de " + levelMB + "MB, atingindo " + size +
									"MB. Data e hora: "
									+ dateformat.format(new Date()));
					email.setSubject("NETZ - Nivel de trafego excedido");
					try {
						notificationController.sendNotificationEmail(email);
					} catch (NetzMailException e) {
						e.printStackTrace();
					}
				}
				if (configurationTO.isNotifyUnknownHostSms()) {
					sms
					.setMessage("O nivel de trafego ultrapassou o valor maximo " +
							"configurado de " + levelMB + "MB, atingindo " + size +
							"MB. Data e hora: "
							+ dateformat.format(new Date()));
					notificationController.sendNotificationSMS(sms);
				}
				lastNotification = new Date();
			}
		}
	}
}

