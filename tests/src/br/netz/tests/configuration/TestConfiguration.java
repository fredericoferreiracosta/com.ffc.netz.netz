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

package br.netz.tests.configuration;

import java.util.Date;

import br.netz.configuration.model.ConfigurationDAO;
import br.netz.configuration.model.ConfigurationDAOException;
import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;
import junit.framework.TestCase;

public class TestConfiguration extends TestCase{

	private ConfigurationDAO configurationDAO;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		configurationDAO = new ConfigurationDAO();
	}
	

	public void testEmailConfiguration() throws ConfigurationDAOException{
		
		configurationDAO.deleteEmailConfiguration();
		
		EmailConfigurationTO returnedWithoutId1 = configurationDAO.getEmailConfiguration();
		assertNull(returnedWithoutId1);

		EmailConfigurationTO savedEmailConfiguration = new EmailConfigurationTO();
		savedEmailConfiguration.setFrom("sender@email.com");
		savedEmailConfiguration.setHostName("hostName");
		savedEmailConfiguration.setPassword("123456");
		savedEmailConfiguration.setSmtpPort(123);
		savedEmailConfiguration.setSsl(true);
		savedEmailConfiguration.setTls(false);
		savedEmailConfiguration.setTo("receiver@email.com");
		savedEmailConfiguration.setUser("email.user");
		
		configurationDAO.saveEmailConfiguration(savedEmailConfiguration);
		assertNotNull(savedEmailConfiguration.getId());
		
		EmailConfigurationTO returnedEmailConfiguration = configurationDAO.getEmailConfiguration(savedEmailConfiguration.getId());
		assertEquals(savedEmailConfiguration.getFrom(), returnedEmailConfiguration.getFrom());
		assertEquals(savedEmailConfiguration.getHostName(), returnedEmailConfiguration.getHostName());
		assertEquals(savedEmailConfiguration.getId(), returnedEmailConfiguration.getId());
		assertEquals(savedEmailConfiguration.getPassword(), returnedEmailConfiguration.getPassword());
		assertEquals(savedEmailConfiguration.getSmtpPort(), returnedEmailConfiguration.getSmtpPort());
		assertEquals(savedEmailConfiguration.getTo(), returnedEmailConfiguration.getTo());
		assertEquals(savedEmailConfiguration.getUser(), returnedEmailConfiguration.getUser());

		savedEmailConfiguration.setHostName("updatedHostName");
		
		configurationDAO.updateEmailConfiguration(savedEmailConfiguration);
		
		returnedEmailConfiguration = configurationDAO.getEmailConfiguration(savedEmailConfiguration.getId());
		
		assertEquals(savedEmailConfiguration.getHostName(), returnedEmailConfiguration.getHostName());
		
		EmailConfigurationTO returnedWithoutId2 = configurationDAO.getEmailConfiguration();
		assertNotNull(returnedWithoutId2);
	}
	
	public void testSmsConfiguration() throws ConfigurationDAOException{
		
		configurationDAO.deleteSmsConfiguration();
		
		SmsConfigurationTO returnedWithoutId1 = configurationDAO.getSmsConfiguration();
		assertNull(returnedWithoutId1);

		SmsConfigurationTO savedSmsConfiguration = new SmsConfigurationTO();
		savedSmsConfiguration.setUrl("www.teste.com?user=teste&password=1234");
		
		configurationDAO.saveSmsConfiguration(savedSmsConfiguration);
		
		SmsConfigurationTO returnedSmsConfiguration = configurationDAO.getSmsConfiguration(savedSmsConfiguration.getId());
		
		assertEquals(savedSmsConfiguration.getId(), returnedSmsConfiguration.getId());
		assertEquals(savedSmsConfiguration.getUrl(), returnedSmsConfiguration.getUrl());
		
		savedSmsConfiguration.setUrl("www.novaurl.com?user=teste2&password=1234");
		
		configurationDAO.updateSmsConfiguration(savedSmsConfiguration);
		
		returnedSmsConfiguration = configurationDAO.getSmsConfiguration(savedSmsConfiguration.getId());
		
		assertEquals(savedSmsConfiguration.getUrl(), returnedSmsConfiguration.getUrl());
		
		SmsConfigurationTO returnedWithoutId2 = configurationDAO.getSmsConfiguration();
		assertNotNull(returnedWithoutId2);

	}
	
	public void testGeneralConfiguration() throws ConfigurationDAOException{
		
		configurationDAO.deleteGeneralConfiguration();
		
		GeneralConfigurationTO returnedWithoutId1 = configurationDAO.getGeneralConfiguration();
		assertNull(returnedWithoutId1);
		
		GeneralConfigurationTO returnedWithId1 = configurationDAO.getGeneralConfiguration(1);
		assertNull(returnedWithId1);
		
		GeneralConfigurationTO savedGeneralConfiguration = new GeneralConfigurationTO();
		savedGeneralConfiguration.setDeleteData(true);
		savedGeneralConfiguration.setDeleteDataTime("1 dia");
		savedGeneralConfiguration.setGraphicUnit("Kb");
		savedGeneralConfiguration.setGraphicUpdate(10);
		savedGeneralConfiguration.setHighlightUnknownHost(true);
		savedGeneralConfiguration.setLanguage("pt-BR");
		savedGeneralConfiguration.setNotifyHostEntry(true);
		savedGeneralConfiguration.setNotifyHostEntryMode("1");
		savedGeneralConfiguration.setNotifyMacs("00:00:00:00:00:0f;00:00:00:00:00:0d");
		savedGeneralConfiguration.setNotifyMacsEmail(false);
		savedGeneralConfiguration.setNotifyMacsSms(true);
		savedGeneralConfiguration.setNotifyTimeEmail(false);
		savedGeneralConfiguration.setNotifyTimeFrom(new Date());
		savedGeneralConfiguration.setNotifyTimeSms(true);
		savedGeneralConfiguration.setNotifyTimeTo(new Date());
		savedGeneralConfiguration.setNotifyTrafficEmail(false);
		savedGeneralConfiguration.setNotifyTrafficSms(true);
		savedGeneralConfiguration.setNotifyTrafficLevel(100);
		savedGeneralConfiguration.setNotifyUnknownHostEmail(false);
		savedGeneralConfiguration.setNotifyUnknownHostSms(true);
		savedGeneralConfiguration.setNetworkInterface("eth0");
		
		configurationDAO.saveGeneralConfiguration(savedGeneralConfiguration);
		assertNotNull(savedGeneralConfiguration.getId());
		
		GeneralConfigurationTO returnedWithoutId = configurationDAO.getGeneralConfiguration();
		assertNotNull(returnedWithoutId);

		GeneralConfigurationTO returnedGeneralConfiguration = configurationDAO.getGeneralConfiguration(savedGeneralConfiguration.getId());
		
		assertEquals(savedGeneralConfiguration.getDeleteDataTime(), returnedGeneralConfiguration.getDeleteDataTime());
		assertEquals(savedGeneralConfiguration.getGraphicUnit(), returnedGeneralConfiguration.getGraphicUnit());
		assertEquals(savedGeneralConfiguration.getGraphicUpdate(), returnedGeneralConfiguration.getGraphicUpdate());
		assertEquals(savedGeneralConfiguration.getId(), returnedGeneralConfiguration.getId());
		assertEquals(savedGeneralConfiguration.getLanguage(), returnedGeneralConfiguration.getLanguage());
		assertEquals(savedGeneralConfiguration.getNotifyHostEntryMode(), returnedGeneralConfiguration.getNotifyHostEntryMode());
		assertEquals(savedGeneralConfiguration.getNotifyMacs(), returnedGeneralConfiguration.getNotifyMacs());
		assertEquals(savedGeneralConfiguration.getNotifyTrafficLevel(), returnedGeneralConfiguration.getNotifyTrafficLevel());
		assertEquals(savedGeneralConfiguration.isDeleteData(), returnedGeneralConfiguration.isDeleteData());
		assertEquals(savedGeneralConfiguration.isHighlightUnknownHost(), returnedGeneralConfiguration.isHighlightUnknownHost());
		assertEquals(savedGeneralConfiguration.isNotifyHostEntry(), returnedGeneralConfiguration.isNotifyHostEntry());
		assertEquals(savedGeneralConfiguration.isNotifyMacsEmail(), returnedGeneralConfiguration.isNotifyMacsEmail());
		assertEquals(savedGeneralConfiguration.isNotifyTimeEmail(), returnedGeneralConfiguration.isNotifyTimeEmail());
		assertEquals(savedGeneralConfiguration.isNotifyTimeSms(), returnedGeneralConfiguration.isNotifyTimeSms());
		assertEquals(savedGeneralConfiguration.isNotifyTrafficEmail(), returnedGeneralConfiguration.isNotifyTrafficEmail());
		assertEquals(savedGeneralConfiguration.isNotifyTrafficSms(), returnedGeneralConfiguration.isNotifyTrafficSms());
		assertEquals(savedGeneralConfiguration.isNotifyUnknownHostEmail(), returnedGeneralConfiguration.isNotifyUnknownHostEmail());
		assertEquals(savedGeneralConfiguration.isNotifyUnknownHostSms(), returnedGeneralConfiguration.isNotifyUnknownHostSms());
		assertEquals(savedGeneralConfiguration.getNetworkInterface(), returnedGeneralConfiguration.getNetworkInterface());

		savedGeneralConfiguration.setLanguage("en-US");
		savedGeneralConfiguration.setNotifyMacs("00:00:00:00:00:0a;00:00:00:00:00:0b");
		savedGeneralConfiguration.setNetworkInterface("eth1");

		configurationDAO.updateGeneralConfiguration(savedGeneralConfiguration);
		
		returnedGeneralConfiguration = configurationDAO.getGeneralConfiguration(savedGeneralConfiguration.getId());
		
		assertEquals(savedGeneralConfiguration.getNotifyMacs(), returnedGeneralConfiguration.getNotifyMacs());
		assertEquals(savedGeneralConfiguration.getLanguage(), returnedGeneralConfiguration.getLanguage());
		assertEquals(savedGeneralConfiguration.getNetworkInterface(), returnedGeneralConfiguration.getNetworkInterface());


	}
}
