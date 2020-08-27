package br.netz.tests.notification;

import java.io.IOException;
import junit.framework.TestCase;
import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;
import br.netz.notification.controller.NetzMailException;
import br.netz.notification.controller.NetzSystemTray;
import br.netz.notification.controller.NotificationController;

public class TestNotification extends TestCase {

		
	public void testEmail() {
		//Enviar email
		EmailConfigurationTO email = new EmailConfigurationTO();
		NotificationController notificationController = new NotificationController();
		
		email.setFrom("fredericoferreira@live.com");
		email.setHostName("smtp.live.com");
		email.setMessage("O computador %TAL% acabou de entrar na rede local");
		email.setPassword("l1752sfred");
		email.setSmtpPort(587);
		email.setSsl(false);
		email.setSubject("Host %TAL% acaba de entrar");
		email.setTls(true);
		email.setTo("alana.brandao@yahoo.com.br");
		email.setUser("fredericoferreira@live.com");
		try {
			notificationController.sendNotificationEmail(email);
		} catch (NetzMailException e) {
			e.printStackTrace();
		}
	}
	
	public void testSMS() throws IOException {
		
		SmsConfigurationTO sms = new SmsConfigurationTO();
		NotificationController notificationController = new NotificationController();
				
		sms.setMessage("Teste+de+SMS+via+aplicação");
		sms.setUrl("http://www.mpgateway.com/v_2_00/smspush/enviasms.aspx?CREDENCIAL=15AFA32F9463E74B5C75FFC2290137468455D7BD&PRINCIPAL_USER=MP&AUX_USER=USUÁRIOAUX&MOBILE=553588811570&SEND_PROJECT=S&MESSAGE=messagen");
		
		assertTrue(notificationController.sendNotificationSMS(sms));
	}
	
	public void testSystemTray() throws InterruptedException {
		new NetzSystemTray();
		Thread.sleep(15000);
	}

}
