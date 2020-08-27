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

package netz.gui.configuracoes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;

import netz.gui.listeners.PanelConfGeralListener;
import netz.gui.listeners.PanelConfNotificacoesListener;
import netz.gui.listeners.PanelConfiguracoesListener;
import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;

public class PanelConfiguracoes extends JPanel {

	private static final long serialVersionUID = -5644766859498202390L;

	private PanelConfGeral panelConfGeral;
	private PanelConfContatos panelConfContatos;
	private PanelConfNotificacoes panelConfNotificacoes;
	private PanelConfGraficos panelConfGraficos;
	private JPanel emptyPanel;
	
	private GridBagConstraints panelConfGeralConstraints;
	private GridBagConstraints panelConfContatosConstraints;
	private GridBagConstraints panelConfNotificacoesConstraints;
	private GridBagConstraints panelConfGraficosConstraints;
	private GridBagConstraints emptyPanelConstraints;

	private ArrayList<PanelConfiguracoesListener> listeners = new ArrayList<PanelConfiguracoesListener>();
	
	public PanelConfiguracoes() {
		super();
		setPreferredSize(new Dimension(980,680));
		initialize();
	}

	private void initialize() {
		setLayout(new GridBagLayout());
		add(getPanelConfGeral(),getPanelConfGeralConstraints());
		add(getPanelConfContatos(),getPanelConfContatosConstraints());
		add(getPanelConfNotificacoes(),getPanelConfNotificacoesConstraints());
		add(getPanelConfGraficos(),getPanelConfGraficosConstraints());
		add(getEmptyPanel(),getEmptyPanelConstraints());
	}

	private PanelConfGeral getPanelConfGeral() {
		if(panelConfGeral==null){
			panelConfGeral = new PanelConfGeral();
			panelConfGeral.addPanelConfGeralListener(new PanelConfGeralListener(){

				@Override
				public void limparHistorico() {
					for(PanelConfiguracoesListener listener: listeners){
						listener.limparHistorico();
					}
				}
				
			});
		}
		return panelConfGeral;
	}
	private PanelConfContatos getPanelConfContatos() {
		if(panelConfContatos==null){
			panelConfContatos = new PanelConfContatos();
		}
		return panelConfContatos;
	}
	private PanelConfNotificacoes getPanelConfNotificacoes() {
		if(panelConfNotificacoes==null){
			panelConfNotificacoes = new PanelConfNotificacoes();
			panelConfNotificacoes.addPanelConfNotificacoesListener(new PanelConfNotificacoesListener(){

				@Override
				public void configurarEmail() {
					for(PanelConfiguracoesListener listener: listeners){
						listener.configurarEmail();
					}
				}

				@Override
				public void configurarSms() {
					for(PanelConfiguracoesListener listener: listeners){
						listener.configurarSms();
					}
				}
				
			});
		}
		return panelConfNotificacoes;
	}
	private PanelConfGraficos getPanelConfGraficos() {
		if(panelConfGraficos==null){
			panelConfGraficos = new PanelConfGraficos();
		}
		return panelConfGraficos;
	}
	private JPanel getEmptyPanel() {
		if(emptyPanel==null){
			emptyPanel = new JPanel();
			emptyPanel.setPreferredSize(new Dimension(450,300));
		}
		return emptyPanel;
	}

	private GridBagConstraints getPanelConfGeralConstraints() {
		if(panelConfGeralConstraints==null){
			panelConfGeralConstraints = formatoGeral();
			panelConfGeralConstraints.gridx = 0;
			panelConfGeralConstraints.gridy = 0;
		}
		return panelConfGeralConstraints;
	}
	private GridBagConstraints getPanelConfContatosConstraints() {
		if(panelConfContatosConstraints==null){
			panelConfContatosConstraints = formatoGeral();
			panelConfContatosConstraints.gridx = 0;
			panelConfContatosConstraints.gridy = 1;
		}
		return panelConfContatosConstraints;
	}
	private GridBagConstraints getPanelConfNotificacoesConstraints() {
		if(panelConfNotificacoesConstraints==null){
			panelConfNotificacoesConstraints = formatoGeral();
			panelConfNotificacoesConstraints.gridx = 1;
			panelConfNotificacoesConstraints.gridy = 0;
			panelConfNotificacoesConstraints.gridheight = 3;
		}
		return panelConfNotificacoesConstraints;
	}
	private GridBagConstraints getPanelConfGraficosConstraints() {
		if(panelConfGraficosConstraints==null){
			panelConfGraficosConstraints = formatoGeral();
			panelConfGraficosConstraints.gridx = 0;
			panelConfGraficosConstraints.gridy = 2;
		}
		return panelConfGraficosConstraints;
	}
	private GridBagConstraints getEmptyPanelConstraints() {
		if(emptyPanelConstraints==null){
			emptyPanelConstraints = formatoGeral();
			emptyPanelConstraints.gridx = 0;
			emptyPanelConstraints.gridy = 3;
			emptyPanelConstraints.gridwidth = 2;
		}
		return emptyPanelConstraints;
	}
	
	private GridBagConstraints formatoGeral(){
		GridBagConstraints gbc = new GridBagConstraints();
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(1,1,1,1);
		return gbc;
	}
	
	public void addPanelConfiguracoesListener(PanelConfiguracoesListener listener){
		listeners.add(listener);
	}

	public void setConfigurations(SmsConfigurationTO smsConfigurationTO,
			EmailConfigurationTO emailConfigurationTO,
			GeneralConfigurationTO generalConfigurationTO) {
		getPanelConfGeral().setConfigurations(smsConfigurationTO,emailConfigurationTO,generalConfigurationTO);
		getPanelConfContatos().setConfigurations(smsConfigurationTO,emailConfigurationTO,generalConfigurationTO);;
		getPanelConfGraficos().setConfigurations(smsConfigurationTO,emailConfigurationTO,generalConfigurationTO);
		getPanelConfNotificacoes().setConfigurations(smsConfigurationTO,emailConfigurationTO,generalConfigurationTO);
	}

	public GeneralConfigurationTO getConfiguracoesGerais() {
		
		GeneralConfigurationTO confGeral = getPanelConfGeral().getConfigurations();
		GeneralConfigurationTO confContatos = getPanelConfContatos().getConfigurations();
		GeneralConfigurationTO confGraficos = getPanelConfGraficos().getConfigurations();
		GeneralConfigurationTO confNotificacoes = getPanelConfNotificacoes().getConfigurations();
		
		GeneralConfigurationTO generalConfiguration = new GeneralConfigurationTO();
		
		generalConfiguration.setNetworkInterface(confGeral.getNetworkInterface());
		generalConfiguration.setDeleteData(confGeral.isDeleteData());
		generalConfiguration.setDeleteDataTime(confGeral.getDeleteDataTime());
		
		generalConfiguration.setNotifyHostEntry(confContatos.isNotifyHostEntry());
		generalConfiguration.setHighlightUnknownHost(confContatos.isHighlightUnknownHost());
		
		generalConfiguration.setGraphicUpdate(confGraficos.getGraphicUpdate());

		generalConfiguration.setNotifyTrafficLevel(confNotificacoes.getNotifyTrafficLevel());
		generalConfiguration.setNotifyTrafficSms(confNotificacoes.isNotifyTrafficSms());
		generalConfiguration.setNotifyTrafficEmail(confNotificacoes.isNotifyTrafficEmail());
		generalConfiguration.setNotifyUnknownHostSms(confNotificacoes.isNotifyUnknownHostSms());
		generalConfiguration.setNotifyUnknownHostEmail(confNotificacoes.isNotifyUnknownHostEmail());
		generalConfiguration.setNotifyMacs(confNotificacoes.getNotifyMacs());
		generalConfiguration.setNotifyMacsSms(confNotificacoes.isNotifyMacsSms());
		generalConfiguration.setNotifyMacsEmail(confNotificacoes.isNotifyMacsEmail());
		generalConfiguration.setNotifyTimeFrom(confNotificacoes.getNotifyTimeFrom());	
		generalConfiguration.setNotifyTimeTo(confNotificacoes.getNotifyTimeTo());
		generalConfiguration.setNotifyTimeSms(confNotificacoes.isNotifyTimeSms());
		generalConfiguration.setNotifyTimeEmail(confNotificacoes.isNotifyTimeEmail());
		
		return generalConfiguration;
	}
}
