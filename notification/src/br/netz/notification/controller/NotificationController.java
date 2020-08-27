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

package br.netz.notification.controller;

import java.io.IOException;

import org.apache.commons.mail.EmailException;


import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;

public class NotificationController {

	
	public void sendNotificationEmail(EmailConfigurationTO email) throws NetzMailException {
		EmailSender emailSender = new EmailSender();
		try {
			emailSender.sendEmail(email);
		} catch (EmailException e) {
			e.printStackTrace();
			throw new NetzMailException(e.getMessage());
		}
	}
	
	public boolean sendNotificationSMS(SmsConfigurationTO sms) throws IOException {
		SMSSender smsSender = new SMSSender();
		if(smsSender.sendSMS(sms)) {
			System.out.println("SMS enviado com sucesso!!!");
			return true;
		} else {
			System.out.println("Erro ao tentar enviar o SMS!!!");
			return false;
		}
	}
}
