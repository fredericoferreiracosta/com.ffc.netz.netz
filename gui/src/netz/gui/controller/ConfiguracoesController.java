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

import java.io.IOException;

import javax.swing.JOptionPane;

import netz.gui.configuracoes.FrameConfEmail;
import netz.gui.configuracoes.FrameConfSms;
import netz.gui.listeners.FrameConfEmailListener;
import netz.gui.listeners.FrameConfSmsListener;
import netz.gui.main.FramePrincipal;
import netz.traffic.logger.NetzLogger;

import br.netz.configuration.model.ConfigurationDAO;
import br.netz.configuration.model.ConfigurationDAOException;
import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;
import br.netz.notification.controller.NetzMailException;
import br.netz.notification.controller.NotificationController;
import br.netz.traffic.model.TrafficDAO;
import br.netz.traffic.model.TrafficDAOException;

public class ConfiguracoesController {

	private ConfigurationDAO configurationDAO;
	private FrameConfEmail frameConfEmail;
	private FrameConfSms frameConfSms;
	private FramePrincipal framePrincipal;
	private NotificationController notificationController;
	
	public ConfiguracoesController(ConfigurationDAO configurationDAO, NotificationController notificationController, FramePrincipal framePrincipal) {
		this.configurationDAO = configurationDAO;
		this.framePrincipal = framePrincipal;
		this.notificationController = notificationController;
	}
	
	public void configurarEmail(){
		EmailConfigurationTO emailConfigurationTO;
		try {
			emailConfigurationTO = configurationDAO
					.getEmailConfiguration();
			getFrameConfEmail().setConfigurations(
					emailConfigurationTO);
			getFrameConfEmail().setVisible(true);
		} catch (ConfigurationDAOException e) {
			JOptionPane.showMessageDialog(null,
					"N�o foi poss�vel recuperar as informa��es de e-mail! - "
							+ e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"ConfigurationDAOException: " + e.getMessage());
		}

	}
	
	public void configurarSms(){
		SmsConfigurationTO smsConfigurationTO;
		try {
			smsConfigurationTO = configurationDAO
					.getSmsConfiguration();
			getFrameConfSms().setConfigurations(
					smsConfigurationTO);
			getFrameConfSms().setVisible(true);
		} catch (ConfigurationDAOException e) {
			JOptionPane.showMessageDialog(null,
					"N�o foi poss�vel recuperar as informa��es de SMS! - "
							+ e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"ConfigurationDAOException: " + e.getMessage());
		}
	}
	
	public void salvarConfiguracoesGerais(GeneralConfigurationTO generalConfiguration) {
			try {
				configurationDAO
						.updateGeneralConfiguration(generalConfiguration);
			} catch (ConfigurationDAOException e) {
				e.printStackTrace();
				NetzLogger.getInstance().error(
						"ConfigurationDAOException: " + e.getMessage());
			}
	}
	
	public void limparHistorico(){
		Object[] options = { "Sim", "N�o" };
		
		int n = JOptionPane.showOptionDialog(null,
				"Deseja relamente remover todo o hist�rico?",
				"Hist�rico",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, 
				options, 
				options[1]);
		if(n==0){
			removerHistorico();
		}
	}
	private void removerHistorico(){
		try {
			TrafficDAO trafficDAO = new TrafficDAO();
			trafficDAO.deleteAllPackets();
			framePrincipal.removerHistorico();
			JOptionPane
					.showMessageDialog(
							null,
							"O hist�rico foi removido com sucesso! ",
							"Hist�rico",
							JOptionPane.INFORMATION_MESSAGE);
		} catch (TrafficDAOException e) {
			JOptionPane
			.showMessageDialog(
					null,
					"Erro ao remover hist�rico! ",
					"Hist�rico",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"TrafficDAOException: " + e.getMessage());
		}
	}
	
	private FrameConfEmail getFrameConfEmail() {
		if (frameConfEmail == null) {
			frameConfEmail = new FrameConfEmail();
			frameConfEmail
					.addFrameConfEmailListener(new FrameConfEmailListener() {

						@Override
						public void enviarEmailTeste(
								EmailConfigurationTO emailConfiguration) {
							ConfiguracoesController.this
									.enviarEmailTeste(emailConfiguration);
						}

						@Override
						public void salvarConfiguracoesEmail(
								EmailConfigurationTO emailConfiguration) {
							ConfiguracoesController.this
									.salvarConfiguracoesEmail(emailConfiguration);
						}

					});
		}
		return frameConfEmail;
	}

	private FrameConfSms getFrameConfSms() {
		if (frameConfSms == null) {
			frameConfSms = new FrameConfSms();
			frameConfSms.addFrameConfSmsListener(new FrameConfSmsListener() {

				@Override
				public void enviarSmsTeste(SmsConfigurationTO smsConfiguration) {
					ConfiguracoesController.this.enviarSmsTeste(smsConfiguration);
				}

				@Override
				public void salvarConfiguracoesSms(
						SmsConfigurationTO smsConfiguration) {
					ConfiguracoesController.this.salvarConfiguracoesSms(smsConfiguration);
				}

			});
		}
		return frameConfSms;
	}

	public void enviarEmailTeste(EmailConfigurationTO emailConfiguration) {
		emailConfiguration.setSubject("E-mail de teste");
		emailConfiguration
				.setMessage("Este � um e-mail de teste enviado pelo Netz. Se voc� recebeu este e-mail significa que as configura��es para o envio est�o corretas.");
		try {
			notificationController.sendNotificationEmail(emailConfiguration);
			JOptionPane.showMessageDialog(null, "Foi enviado um e-mail de teste para sua caixa de entrada, verifique se a mensagem foi recebida, caso n�o tenha, verique novamente as configura��es do servidor.", "E-mail",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (NetzMailException e) {
			JOptionPane.showMessageDialog(null, "Erro ao enviar e-mail! Verifique as configura��es.", "E-mail",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}

	public void enviarSmsTeste(SmsConfigurationTO smsConfiguration) {

		try {
			smsConfiguration
					.setMessage("SMS+de+teste+enviado+pelo+Netz.+As+configuracoes+para+envio+estao+corretas.");
			notificationController.sendNotificationSMS(smsConfiguration);
			JOptionPane.showMessageDialog(null, "Uma mensagem de teste foi enviada para seu celular. Verifique se voc� recebeu o SMS, caso n�o tenha recebido, verifique as configura��es novamente.",
					"Mensagem", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Erro ao enviar mensagem SMS! Verifique as configura��es colocadas e se voc� possui cr�ditos junto ao servidor de gateway.",
							"Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"IOException: " + e.getMessage());
		}
	}

	public void salvarConfiguracoesEmail(EmailConfigurationTO emailConfiguration) {
		try {
			configurationDAO.updateEmailConfiguration(emailConfiguration);
			JOptionPane.showMessageDialog(null,
					"Configura��es salvas com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
			frameConfEmail.dispose();
		} catch (ConfigurationDAOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"ConfigurationDAOException: " + e.getMessage());
		}
	}

	public void salvarConfiguracoesSms(SmsConfigurationTO smsConfiguration) {
		try {
			configurationDAO.updateSmsConfiguration(smsConfiguration);
			JOptionPane.showMessageDialog(null,
					"Configura��es salvas com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
			frameConfSms.dispose();
		} catch (ConfigurationDAOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"ConfigurationDAOException: " + e.getMessage());
		}
	}
}
