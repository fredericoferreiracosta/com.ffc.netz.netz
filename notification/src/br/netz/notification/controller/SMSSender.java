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
import java.net.URL;

import netz.traffic.logger.NetzLogger;

import br.netz.configuration.model.SmsConfigurationTO;

public class SMSSender {

	public boolean sendSMS(SmsConfigurationTO sms) throws IOException {
		String urlComplete = sms.getUrl();
		
		sms.setMessage(sms.getMessage().replaceAll(" ", "+"));
		if(sms!=null || sms.getMessage().length()>0){
			if (urlComplete.contains("messagenetz")) {
				StringBuffer sb = new StringBuffer(urlComplete);
				sb.replace(sb.indexOf("messagenetz"),
						sb.indexOf("messagenetz") + 11, sms.getMessage());
				urlComplete = sb.toString();
			    
			    sms.setMessage(sms.getMessage().replaceAll(" ", "+"));
			    
				URL url = new URL(urlComplete);
				url.openStream();
				return true;
			} else {
				System.out
						.println("A URL informada n�o contem o par�metro 'messagenetz'." +
								"Este par�metro deve ser informado para identificar onde " +
								"deve ficar o texto na URL");
				NetzLogger.getInstance().error("A URL informada n�o contem o par�metro 'messagenetz'." +
						"Este par�metro deve ser informado para identificar onde " +
						"deve ficar o texto na URL");
				return false;
			}
		}else{
			System.out.println("As configura��es de SMS devem ser preenchidas para o envio!");
			NetzLogger.getInstance().info("As configura��es de SMS devem ser preenchidas para o envio!");
			return false;
		}
	}
}
