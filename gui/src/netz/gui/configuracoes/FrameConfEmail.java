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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import netz.gui.listeners.FrameConfEmailListener;
import br.netz.configuration.model.EmailConfigurationTO;

public class FrameConfEmail extends JFrame {
	
	private static final long serialVersionUID = -4678367259546804702L;
	
	private PanelConfEmail panelConfEmail;
	
	private ArrayList<FrameConfEmailListener> listeners = new ArrayList<FrameConfEmailListener>();
	
	public FrameConfEmail() {
		super("Configura��es de e-mail");
		add(getPanelConfEmail());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}
	
	private PanelConfEmail getPanelConfEmail() {
		if(panelConfEmail==null){
			panelConfEmail = new PanelConfEmail();
		}
		return panelConfEmail;
	}


	private class PanelConfEmail extends JPanel{

		private static final long serialVersionUID = 7609701521578818766L;

		private JLabel informacoesServLabel;
		private JLabel servidorSmtpLabel;
		private JTextField servidorSmtpField;
		private JLabel numPortaLabel;
		private JTextField numPortaField;
		
		private JLabel informRemDestLabel;
		private JLabel endRemetenteLabel;
		private JTextField endRemetenteField;
		private JLabel endDestinoLabel;
		private JTextField endDestinoField;
		
		private JLabel informLogonLabel;
		private JLabel nomeUsuarioLabel;
		private JTextField nomeUsuarioField;
		private JLabel senhaLabel;
		private JPasswordField senhaField;
		
		private JCheckBox sslCheckBox;
		private JCheckBox tlsCheckBox;
		
		private PanelConfEmailButtons panelButtons;
		
		
		private GridBagConstraints informacoesServLabelConstraints;
		private GridBagConstraints servidorSmtpLabelConstraints;
		private GridBagConstraints servidorSmtpFieldConstraints;
		private GridBagConstraints numPortaLabelConstraints;
		private GridBagConstraints numPortaFieldConstraints;
		
		private GridBagConstraints informRemDestLabelConstraints;
		private GridBagConstraints endRemetenteLabelConstraints;
		private GridBagConstraints endRemetenteFieldConstraints;
		private GridBagConstraints endDestinoLabelConstraints;
		private GridBagConstraints endDestinoFieldConstraints;
		
		private GridBagConstraints informLogonLabelConstraints;
		private GridBagConstraints nomeUsuarioLabelConstraints;
		private GridBagConstraints nomeUsuarioFieldConstraints;
		private GridBagConstraints senhaLabelConstraints;
		private GridBagConstraints senhaFieldConstraints;
		
		private GridBagConstraints sslCheckBoxConstraints;
		private GridBagConstraints tlsCheckBoxConstraints;
		
		private GridBagConstraints panelButtonsConstraints;
		
		
		public PanelConfEmail() {
			setLayout(new GridBagLayout());
			initialize();
			setPreferredSize(new Dimension(430,350));
		}

		private void initialize() {
			
			add(getInformacoesServLabel(),getInformacoesServLabelConstraints());
			add(getServidorSmtpLabel(),getServidorSmtpLabelConstraints());
			add(getServidorSmtpField(),getServidorSmtpFieldConstraints());
			add(getNumPortaLabel(),getNumPortaLabelConstraints());
			add(getNumPortaField(),getNumPortaFieldConstraints());
			
			add(getInformRemDestLabel(),getInformRemDestLabelConstraints());
			add(getEndRemetenteLabel(),getEndRemetenteLabelConstraints());
			add(getEndRemetenteField(),getEndRemetenteFieldConstraints());
			add(getEndDestinoLabel(),getEndDestinoLabelConstraints());
			add(getEndDestinoField(),getEndDestinoFieldConstraints());
			
			add(getInformLogonLabel(),getInformLogonLabelConstraints());
			add(getNomeUsuarioLabel(),getNomeUsuarioLabelConstraints());
			add(getNomeUsuarioField(),getNomeUsuarioFieldConstraints());
			add(getSenhaLabel(),getSenhaLabelConstraints());
			add(getSenhaField(),getSenhaFieldConstraints());
			
			add(getSslCheckBox(),getSslCheckBoxConstraints());
			add(getTlsCheckBox(),getTlsCheckBoxConstraints());
			
			add(getPanelButtons(),getPanelButtonsConstraints());
			
		}

		private JLabel getInformacoesServLabel() {
			if(informacoesServLabel==null){
				informacoesServLabel = new JLabel();
				informacoesServLabel.setText("Informa��es do servidor");
				informacoesServLabel.setFont(informacoesServLabel.getFont().deriveFont(
						informacoesServLabel.getFont().getStyle() ^ Font.BOLD));

			}
			return informacoesServLabel;
		}

		private JLabel getServidorSmtpLabel() {
			if(servidorSmtpLabel==null){
				servidorSmtpLabel = new JLabel();
				servidorSmtpLabel.setText("Servidor SMTP");
			}
			return servidorSmtpLabel;
		}

		private JTextField getServidorSmtpField() {
			if(servidorSmtpField==null){
				servidorSmtpField = new JTextField();
			}
			return servidorSmtpField;
		}

		private JLabel getNumPortaLabel() {
			if(numPortaLabel==null){
				numPortaLabel = new JLabel();
				numPortaLabel.setText("N�mero da porta");
			}
			return numPortaLabel;
		}

		private JTextField getNumPortaField() {
			if(numPortaField==null){
				numPortaField = new JTextField();
			}
			return numPortaField;
		}

		private JLabel getInformRemDestLabel() {
			if(informRemDestLabel==null){
				informRemDestLabel = new JLabel();
				informRemDestLabel.setText("Informa��es do remetente e do destinat�rio");
				informRemDestLabel.setFont(informRemDestLabel.getFont().deriveFont(
						informRemDestLabel.getFont().getStyle() ^ Font.BOLD));
			}
			return informRemDestLabel;
		}

		private JLabel getEndRemetenteLabel() {
			if(endRemetenteLabel==null){
				endRemetenteLabel = new JLabel();
				endRemetenteLabel.setText("Endere�o do remetente");
			}
			return endRemetenteLabel;
		}

		private JTextField getEndRemetenteField() {
			if(endRemetenteField==null){
				endRemetenteField = new JTextField();
			}
			return endRemetenteField;
		}

		private JLabel getEndDestinoLabel() {
			if(endDestinoLabel==null){
				endDestinoLabel = new JLabel();
				endDestinoLabel.setText("Endere�o do destinat�rio");

			}
			return endDestinoLabel;
		}

		private JTextField getEndDestinoField() {
			if(endDestinoField==null){
				endDestinoField = new JTextField();
			}
			return endDestinoField;
		}

		private JLabel getInformLogonLabel() {
			if(informLogonLabel==null){
				informLogonLabel = new JLabel();
				informLogonLabel.setText("Informa��es de logon");
				informLogonLabel.setFont(informLogonLabel.getFont().deriveFont(
						informLogonLabel.getFont().getStyle() ^ Font.BOLD));
			}
			return informLogonLabel;
		}

		private JLabel getNomeUsuarioLabel() {
			if(nomeUsuarioLabel==null){
				nomeUsuarioLabel = new JLabel();
				nomeUsuarioLabel.setText("Nome do usu�rio");
			}
			return nomeUsuarioLabel;
		}

		private JTextField getNomeUsuarioField() {
			if(nomeUsuarioField==null){
				nomeUsuarioField = new JTextField();
			}
			return nomeUsuarioField;
		}

		private JLabel getSenhaLabel() {
			if(senhaLabel==null){
				senhaLabel = new JLabel();
				senhaLabel.setText("Senha");
			}
			return senhaLabel;
		}

		private JPasswordField getSenhaField() {
			if(senhaField==null){
				senhaField = new JPasswordField();
			}
			return senhaField;
		}

		private JCheckBox getSslCheckBox() {
			if(sslCheckBox==null){
				sslCheckBox = new JCheckBox("Utilizar SSL");
			}
			return sslCheckBox;
		}

		private JCheckBox getTlsCheckBox() {
			if(tlsCheckBox==null){
				tlsCheckBox = new JCheckBox("Utilizar TLS");
			}
			return tlsCheckBox;
		}

		private PanelConfEmailButtons getPanelButtons() {
			if(panelButtons==null){
				panelButtons = new PanelConfEmailButtons();
			}
			return panelButtons;
		}

	

		private GridBagConstraints getInformacoesServLabelConstraints() {
			if(informacoesServLabelConstraints==null){
				informacoesServLabelConstraints = formatoGeral();
				informacoesServLabelConstraints.gridx = 0;
				informacoesServLabelConstraints.gridy = 0;
			}
			return informacoesServLabelConstraints;
		}

		private GridBagConstraints getServidorSmtpLabelConstraints() {
			if(servidorSmtpLabelConstraints==null){
				servidorSmtpLabelConstraints = formatoGeral();
				servidorSmtpLabelConstraints.gridx = 0;
				servidorSmtpLabelConstraints.gridy = 1;
			}
			return servidorSmtpLabelConstraints;
		}

		private GridBagConstraints getServidorSmtpFieldConstraints() {
			if(servidorSmtpFieldConstraints==null){
				servidorSmtpFieldConstraints = formatoGeral();
				servidorSmtpFieldConstraints.gridx = 1;
				servidorSmtpFieldConstraints.gridy = 1;
				servidorSmtpFieldConstraints.ipadx = 150;
			}
			return servidorSmtpFieldConstraints;
		}

		private GridBagConstraints getNumPortaLabelConstraints() {
			if(numPortaLabelConstraints==null){
				numPortaLabelConstraints = formatoGeral();
				numPortaLabelConstraints.gridx = 0;
				numPortaLabelConstraints.gridy = 2;
				numPortaLabelConstraints.insets = new Insets(1,1,8,1);
			}
			return numPortaLabelConstraints;
		}

		private GridBagConstraints getNumPortaFieldConstraints() {
			if(numPortaFieldConstraints==null){
				numPortaFieldConstraints = formatoGeral();
				numPortaFieldConstraints.gridx = 1;
				numPortaFieldConstraints.gridy = 2;
			}
			return numPortaFieldConstraints;
		}

		private GridBagConstraints getInformRemDestLabelConstraints() {
			if(informRemDestLabelConstraints==null){
				informRemDestLabelConstraints = formatoGeral();
				informRemDestLabelConstraints.gridx = 0;
				informRemDestLabelConstraints.gridy = 3;
			}
			return informRemDestLabelConstraints;
		}

		private GridBagConstraints getEndRemetenteLabelConstraints() {
			if(endRemetenteLabelConstraints==null){
				endRemetenteLabelConstraints = formatoGeral();
				endRemetenteLabelConstraints.gridx = 0;
				endRemetenteLabelConstraints.gridy = 4;
			}
			return endRemetenteLabelConstraints;
		}

		private GridBagConstraints getEndRemetenteFieldConstraints() {
			if(endRemetenteFieldConstraints==null){
				endRemetenteFieldConstraints = formatoGeral();
				endRemetenteFieldConstraints.gridx = 1;
				endRemetenteFieldConstraints.gridy = 4;
			}
			return endRemetenteFieldConstraints;
		}

		private GridBagConstraints getEndDestinoLabelConstraints() {
			if(endDestinoLabelConstraints==null){
				endDestinoLabelConstraints = formatoGeral();
				endDestinoLabelConstraints.gridx = 0;
				endDestinoLabelConstraints.gridy = 5;
				endDestinoLabelConstraints.insets = new Insets(1,1,8,1);
			}
			return endDestinoLabelConstraints;
		}

		private GridBagConstraints getEndDestinoFieldConstraints() {
			if(endDestinoFieldConstraints==null){
				endDestinoFieldConstraints = formatoGeral();
				endDestinoFieldConstraints.gridx = 1;
				endDestinoFieldConstraints.gridy = 5;
			}
			return endDestinoFieldConstraints;
		}

		private GridBagConstraints getInformLogonLabelConstraints() {
			if(informLogonLabelConstraints==null){
				informLogonLabelConstraints = formatoGeral();
				informLogonLabelConstraints.gridx = 0;
				informLogonLabelConstraints.gridy = 6;
			}
			return informLogonLabelConstraints;
		}

		private GridBagConstraints getNomeUsuarioLabelConstraints() {
			if(nomeUsuarioLabelConstraints==null){
				nomeUsuarioLabelConstraints = formatoGeral();
				nomeUsuarioLabelConstraints.gridx = 0;
				nomeUsuarioLabelConstraints.gridy = 7;
			}
			return nomeUsuarioLabelConstraints;
		}

		private GridBagConstraints getNomeUsuarioFieldConstraints() {
			if(nomeUsuarioFieldConstraints==null){
				nomeUsuarioFieldConstraints = formatoGeral();
				nomeUsuarioFieldConstraints.gridx = 1;
				nomeUsuarioFieldConstraints.gridy = 7;
			}
			return nomeUsuarioFieldConstraints;
		}

		private GridBagConstraints getSenhaLabelConstraints() {
			if(senhaLabelConstraints==null){
				senhaLabelConstraints = formatoGeral();
				senhaLabelConstraints.gridx = 0;
				senhaLabelConstraints.gridy = 8;
				senhaLabelConstraints.insets = new Insets(1,1,8,1);
			}
			return senhaLabelConstraints;
		}

		private GridBagConstraints getSenhaFieldConstraints() {
			if(senhaFieldConstraints==null){
				senhaFieldConstraints = formatoGeral();
				senhaFieldConstraints.gridx = 1;
				senhaFieldConstraints.gridy = 8;
			}
			return senhaFieldConstraints;
		}

		private GridBagConstraints getSslCheckBoxConstraints() {
			if(sslCheckBoxConstraints==null){
				sslCheckBoxConstraints = formatoGeral();
				sslCheckBoxConstraints.gridx = 0;
				sslCheckBoxConstraints.gridy = 9;
			}
			return sslCheckBoxConstraints;
		}

		private GridBagConstraints getTlsCheckBoxConstraints() {
			if(tlsCheckBoxConstraints==null){
				tlsCheckBoxConstraints = formatoGeral();
				tlsCheckBoxConstraints.gridx = 0;
				tlsCheckBoxConstraints.gridy = 10;
				tlsCheckBoxConstraints.insets = new Insets(1,1,8,1);
			}
			return tlsCheckBoxConstraints;
		}
		
		private GridBagConstraints getPanelButtonsConstraints() {
			if(panelButtonsConstraints==null){
				panelButtonsConstraints = formatoGeral();
				panelButtonsConstraints.gridx = 0;
				panelButtonsConstraints.gridy = 11;
				panelButtonsConstraints.gridwidth = 2;
			}
			return panelButtonsConstraints;
		}
		
		private GridBagConstraints formatoGeral(){
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(1,1,1,1);
			return gbc;
		}

		public void setConfigurations(EmailConfigurationTO emailConfigurationTO) {
			getServidorSmtpField().setText(emailConfigurationTO.getHostName());
			getNumPortaField().setText(emailConfigurationTO.getSmtpPort()+"");
			
			getEndRemetenteField().setText(emailConfigurationTO.getFrom());
			getEndDestinoField().setText(emailConfigurationTO.getTo());
			
			getNomeUsuarioField().setText(emailConfigurationTO.getUser());
			getSenhaField().setText(emailConfigurationTO.getPassword());
			
			getSslCheckBox().setSelected(emailConfigurationTO.isSsl());
			getTlsCheckBox().setSelected(emailConfigurationTO.isTls());
		}
		
	}
	
	private class PanelConfEmailButtons extends JPanel{
		
		private static final long serialVersionUID = 8757785695907403278L;

		private PanelConfEmailButtons1 panel1;
		private PanelConfEmailButtons2 panel2;
		
		public PanelConfEmailButtons() {
			setLayout(new GridLayout(2,1));
			add(getPanel1());
			add(getPanel2());
		}
		
		private PanelConfEmailButtons1 getPanel1() {
			if(panel1==null){
				panel1 = new PanelConfEmailButtons1();
			}
			return panel1;
		}

		private PanelConfEmailButtons2 getPanel2() {
			if(panel2==null){
				panel2 = new PanelConfEmailButtons2();
			}
			return panel2;
		}

		private class PanelConfEmailButtons1 extends JPanel{

			private static final long serialVersionUID = 1520885503708282072L;
			
			private JButton emailTesteButton;
			
			public PanelConfEmailButtons1() {
				add(getEmailTesteButton());
			}

			private JButton getEmailTesteButton() {
				if(emailTesteButton==null){
					emailTesteButton = new JButton();
					emailTesteButton.setText("Enviar e-mail de teste");
					emailTesteButton.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							for(FrameConfEmailListener listener : listeners){
								listener.enviarEmailTeste(getConfiguracoesEmail());
							}
						}
						
					});
				}
				return emailTesteButton;
			}

		}
		
		private class PanelConfEmailButtons2 extends JPanel{
			
			private static final long serialVersionUID = -2695598946015786339L;

			private JButton salvarButton;
			private JButton cancelarButton;
			
			
			public PanelConfEmailButtons2() {
				add(getSalvarButton());
				add(getCancelarButton());
			}
			
			private JButton getSalvarButton() {
				if(salvarButton==null){
					salvarButton = new JButton();
					salvarButton.setText("Salvar");
					salvarButton.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							for(FrameConfEmailListener listener : listeners){
								listener.salvarConfiguracoesEmail(getConfiguracoesEmail());
							}
						}
						
					});
				}
				return salvarButton;
			}

			private JButton getCancelarButton() {
				if(cancelarButton==null){
					cancelarButton = new JButton();
					cancelarButton.setText("Cancelar");
					cancelarButton.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							dispose();
						}
						
					});
				}
				return cancelarButton;
			}
		}
		
	}
	
	public EmailConfigurationTO getConfiguracoesEmail(){
	
		String smtp = getPanelConfEmail().getServidorSmtpField().getText();
		String p = getPanelConfEmail().getNumPortaField().getText();
		int porta = 0;
		try {
			porta = Integer.parseInt(p);
		} catch (Exception e) {
		}
		String endRemetente = getPanelConfEmail().getEndRemetenteField().getText();
		String endDestino = getPanelConfEmail().getEndDestinoField().getText();
		String usuario = getPanelConfEmail().getNomeUsuarioField().getText();
		char[] pass =  getPanelConfEmail().getSenhaField().getPassword();
		String senha = String.valueOf(pass);
		boolean ssl = getPanelConfEmail().getSslCheckBox().isSelected();
		boolean tls = getPanelConfEmail().getTlsCheckBox().isSelected();
		
		EmailConfigurationTO emailConfiguration = new EmailConfigurationTO();
		emailConfiguration.setHostName(smtp);
		emailConfiguration.setSmtpPort(porta);
		emailConfiguration.setFrom(endRemetente);
		emailConfiguration.setTo(endDestino);
		emailConfiguration.setUser(usuario);
		emailConfiguration.setPassword(senha);
		emailConfiguration.setSsl(ssl);
		emailConfiguration.setTls(tls);
		
		return emailConfiguration;
	}
	
	public void setConfigurations(EmailConfigurationTO emailConfigurationTO) {
		getPanelConfEmail().setConfigurations(emailConfigurationTO);
	}
	
	public void addFrameConfEmailListener(FrameConfEmailListener listener){
		listeners.add(listener);
	}

	
}
