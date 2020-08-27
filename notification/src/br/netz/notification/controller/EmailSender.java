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

import netz.traffic.logger.NetzLogger;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.netz.configuration.model.EmailConfigurationTO;

public class EmailSender {
	
	public void sendEmail(EmailConfigurationTO email) throws EmailException {
		
		if(email==null||emailEmpty(email)){
			System.out.println("As configura��es de e-mail devem ser preenchidas para o envio!");
			NetzLogger.getInstance().info("As configura��es de e-mail devem ser preenchidas para o envio!");
			return;
		}
    		HtmlEmail htmlEmail = new HtmlEmail();

			htmlEmail.setHostName(email.getHostName());
			htmlEmail.addTo(email.getTo());
			htmlEmail.setFrom(email.getFrom());
			htmlEmail.setSubject(email.getSubject());
			htmlEmail.setHtmlMsg(email.getMessage());
			htmlEmail.setTextMsg("Seu servidor de e-mail n�o suporta mensagem HTML");
			htmlEmail.setAuthentication(email.getUser(), email.getPassword());  
			htmlEmail.setSmtpPort(email.getSmtpPort());
			htmlEmail.setSSL(email.isSsl());  
			htmlEmail.setTLS(email.isTls());

			htmlEmail.send();
	}
	public boolean emailEmpty(EmailConfigurationTO email){
		if(email.getHostName()==null)
			return true;
		if(email.getTo()==null)
			return true;
		if(email.getFrom()==null)
			return true;
		if(email.getTo()==null)
			return true;
		if(email.getUser()==null)
			return true;
		if(email.getPassword()==null)
			return true;
		return false;
	}

}
