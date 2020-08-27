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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import netz.gui.listeners.PanelConfNotificacoesListener;
import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;

public class PanelConfNotificacoes extends JPanel {

	private static final long serialVersionUID = 5009423698287173767L;

	private JLabel notificarNivelTrafegoLabel;
	private JTextField notificarNivelTrafegoField;
	private JCheckBox notificaNivelTrafegoSms;
	private JCheckBox notificaNivelTrafegoEmail;
	
	private JLabel notificarDesconhecidoLabel;
	private JCheckBox notificarDesconhecidoSmsCheckBox;
	private JCheckBox notificarDesconhecidoEmailCheckBox;

	private JLabel notificarMacsLabel;
	private JLabel macsLabel;
	private JTextField macsTextField;
	private JCheckBox notificarMacsSmsCheckBox;
	private JCheckBox notificarMacsEmailCheckBox;

	private JLabel notificarHorarioLabel;
	private JFormattedTextField deTextField;
	private JLabel eLabel;
	private JFormattedTextField ateTextField;
	private JCheckBox notificarHorarioSmsCheckBox;
	private JCheckBox notificarHorarioEmailCheckBox;
	private PanelButtonsConfNotificacoes panelButtonsConfNotificacoes;
	

	private GridBagConstraints notificarNivelTrafegoLabelConstraints;
	private GridBagConstraints notificarNivelTrafegoFieldConstraints;
	private GridBagConstraints notificaNivelTrafegoSmsConstraints;
	private GridBagConstraints notificaNivelTrafegoEmailConstraints;
	
	private GridBagConstraints notificarDesconhecidoLabelConstraints;
	private GridBagConstraints notificarDesconhecidoSmsCheckBoxConstraints;
	private GridBagConstraints notificarDesconhecidoEmailCheckBoxConstraints;

	private GridBagConstraints notificarMacsLabelConstraints;
	private GridBagConstraints macsLabelConstraints;
	private GridBagConstraints macsTextFieldConstraints;
	private GridBagConstraints notificarMacsSmsCheckBoxConstraints;
	private GridBagConstraints notificarMacsEmailCheckBoxConstraints;

	private GridBagConstraints notificarHorarioLabelConstraints;
	private GridBagConstraints deTextFieldConstraints;
	private GridBagConstraints eLabelConstraints;
	private GridBagConstraints ateTextFieldConstraints;
	private GridBagConstraints notificarHorarioSmsCheckBoxConstraints;
	private GridBagConstraints notificarHorarioEmailCheckBoxConstraints;
	private GridBagConstraints panelButtonsConfNotificacoesConstraints;
		
	private ArrayList<PanelConfNotificacoesListener> listeners = new ArrayList<PanelConfNotificacoesListener>();
	
	public PanelConfNotificacoes() {
		initialize();
		setBorder(new TitledBorder(BorderFactory.createTitledBorder("Notifica��es")));
		setPreferredSize(new Dimension(450,330));
	}

	private void initialize(){
		setLayout(new GridBagLayout());
		
		add(getNotificarNivelTrafegoLabel(),getNotificarNivelTrafegoLabelConstraints());
		add(getNotificarNivelTrafegoField(),getNotificarNivelTrafegoFieldConstraints());
		add(getNotificaNivelTrafegoSms(),getNotificaNivelTrafegoSmsConstraints());
		add(getNotificaNivelTrafegoEmail(),getNotificaNivelTrafegoEmailConstraints());
		
		add(getNotificarDesconhecidoLabel(),getNotificarDesconhecidoLabelConstraints());
		add(getNotificarDesconhecidoSmsCheckBox(),getNotificarDesconhecidoSmsCheckBoxConstraints());
		add(getNotificarDesconhecidoEmailCheckBox(),getNotificarDesconhecidoEmailCheckBoxConstraints());
		
		add(getNotificarMacsLabel(),getNotificarMacsLabelConstraints());
		add(getMacsLabel(),getMacsLabelConstraints());
		add(getMacsTextField(),getMacsTextFieldConstraints());
		add(getNotificarMacsSmsCheckBox(),getNotificarMacsSmsCheckBoxConstraints());
		add(getNotificarMacsEmailCheckBox(),getNotificarMacsEmailCheckBoxConstraints());
		
		add(getNotificarHorarioLabel(),getNotificarHorarioLabelConstraints());
		add(getDeTextField(),getDeTextFieldConstraints());
		add(getELabel(),getELabelConstraints());
		add(getAteTextField(),getAteTextFieldConstraints());
		add(getNotificarHorarioSmsCheckBox(),getNotificarHorarioSmsCheckBoxConstraints());
		add(getNotificarHorarioEmailCheckBox(),getNotificarHorarioEmailCheckBoxConstraints());
		
		add(getPanelButtonsConfNotificacoes(),getPanelButtonsConfNotificacoesConstraints());
	}

	private JLabel getNotificarNivelTrafegoLabel() {
		if(notificarNivelTrafegoLabel==null){
			notificarNivelTrafegoLabel = new JLabel();
			notificarNivelTrafegoLabel.setText("Notificar quando o n�vel de tr�fego exceder (MB):");
			notificarNivelTrafegoLabel.setFont(notificarNivelTrafegoLabel.getFont().deriveFont(
					notificarNivelTrafegoLabel.getFont().getStyle() & ~Font.BOLD));
		}
		return notificarNivelTrafegoLabel;
	}

	private JTextField getNotificarNivelTrafegoField() {
		if(notificarNivelTrafegoField==null){
			notificarNivelTrafegoField = new JTextField();
			notificarNivelTrafegoField.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent arg0) {
				}

				@Override
				public void focusLost(FocusEvent arg0) {
					String text = notificarNivelTrafegoField.getText();

					try{
						int i = Integer.parseInt(text);
						if(i<0){
				            JOptionPane.showMessageDialog(null, "O valor digitado � inv�lido! Informe um n�mero inteiro e maior que zero." ,"Valida��o Netz",JOptionPane.WARNING_MESSAGE);  
							text="0";
						}
					}catch (Exception e) {
						text="0";
			            JOptionPane.showMessageDialog(null, "O valor digitado � inv�lido! Informe um n�mero inteiro e maior que zero." ,"Valida��o Netz",JOptionPane.WARNING_MESSAGE);  
					}
					notificarNivelTrafegoField.setText(text);
				}
				
			});
		}
		return notificarNivelTrafegoField;
	}

	private JCheckBox getNotificaNivelTrafegoSms() {
		if(notificaNivelTrafegoSms==null){
			notificaNivelTrafegoSms = new JCheckBox("SMS");
			notificaNivelTrafegoSms.setFont(notificaNivelTrafegoSms.getFont().deriveFont(
					notificaNivelTrafegoSms.getFont().getStyle() & ~Font.BOLD));
		}
		return notificaNivelTrafegoSms;
	}

	private JCheckBox getNotificaNivelTrafegoEmail() {
		if(notificaNivelTrafegoEmail==null){
			notificaNivelTrafegoEmail = new JCheckBox("E-mail");
			notificaNivelTrafegoEmail.setFont(notificaNivelTrafegoEmail.getFont().deriveFont(
					notificaNivelTrafegoEmail.getFont().getStyle() & ~Font.BOLD));
		}
		return notificaNivelTrafegoEmail;
	}

	private JLabel getNotificarDesconhecidoLabel() {
		if(notificarDesconhecidoLabel==null){
			notificarDesconhecidoLabel = new JLabel();
			notificarDesconhecidoLabel.setText("Notificar quando host desconhecido entrar:");
			notificarDesconhecidoLabel.setFont(notificarDesconhecidoLabel.getFont().deriveFont(
					notificarDesconhecidoLabel.getFont().getStyle() & ~Font.BOLD));
		}
		return notificarDesconhecidoLabel;
	}

	private JCheckBox getNotificarDesconhecidoSmsCheckBox() {
		if(notificarDesconhecidoSmsCheckBox==null){
			notificarDesconhecidoSmsCheckBox = new JCheckBox("SMS");
			notificarDesconhecidoSmsCheckBox.setFont(notificarDesconhecidoSmsCheckBox.getFont().deriveFont(
					notificarDesconhecidoSmsCheckBox.getFont().getStyle() & ~Font.BOLD));
		}
		return notificarDesconhecidoSmsCheckBox;
	}

	private JCheckBox getNotificarDesconhecidoEmailCheckBox() {
		if(notificarDesconhecidoEmailCheckBox==null){
			notificarDesconhecidoEmailCheckBox = new JCheckBox("E-mail");
			notificarDesconhecidoEmailCheckBox.setFont(notificarDesconhecidoEmailCheckBox.getFont().deriveFont(
					notificarDesconhecidoEmailCheckBox.getFont().getStyle() & ~Font.BOLD));
		}
		return notificarDesconhecidoEmailCheckBox;
	}

	private JLabel getNotificarMacsLabel() {
		if(notificarMacsLabel==null){
			notificarMacsLabel = new JLabel();
			notificarMacsLabel.setText("<html>Notificar quando os seguintes MAC's entrarem (Use ponto e v�rgula </br> para separar v�rios endere�os): </html>");
			notificarMacsLabel.setFont(notificarMacsLabel.getFont().deriveFont(
					notificarMacsLabel.getFont().getStyle() & ~Font.BOLD));
		}
		return notificarMacsLabel;
	}

	private JLabel getMacsLabel() {
		if(macsLabel==null){
			macsLabel = new JLabel();
			macsLabel.setText("MACs");
			macsLabel.setFont(macsLabel.getFont().deriveFont(
					macsLabel.getFont().getStyle() & ~Font.BOLD));
			
		}
		return macsLabel;
	}

	private JTextField getMacsTextField() {
		if(macsTextField==null){
			macsTextField = new JTextField();
		}
		return macsTextField;
	}

	private JCheckBox getNotificarMacsSmsCheckBox() {
		if(notificarMacsSmsCheckBox==null){
			notificarMacsSmsCheckBox = new JCheckBox("SMS");
			notificarMacsSmsCheckBox.setFont(notificarMacsSmsCheckBox.getFont().deriveFont(
					notificarMacsSmsCheckBox.getFont().getStyle() & ~Font.BOLD));
		}
		return notificarMacsSmsCheckBox;
	}

	private JCheckBox getNotificarMacsEmailCheckBox() {
		if(notificarMacsEmailCheckBox==null){
			notificarMacsEmailCheckBox = new JCheckBox("E-mail");
			notificarMacsEmailCheckBox.setFont(notificarMacsEmailCheckBox.getFont().deriveFont(
					notificarMacsEmailCheckBox.getFont().getStyle() & ~Font.BOLD));
		}
		return notificarMacsEmailCheckBox;
	}

	private JLabel getNotificarHorarioLabel() {
		if(notificarHorarioLabel==null){
			notificarHorarioLabel = new JLabel();
			notificarHorarioLabel.setText("Notificar quando algu�m entrar entre (HH:MM):");
			notificarHorarioLabel.setFont(notificarHorarioLabel.getFont().deriveFont(
					notificarHorarioLabel.getFont().getStyle() & ~Font.BOLD));
		}
		return notificarHorarioLabel;
	}
	
	private MaskFormatter getMask(){
		MaskFormatter m_hora = null;  
	    
        try {
			m_hora = new MaskFormatter("##:##");
	        m_hora.setValidCharacters("0123456789");

		} catch (ParseException e) {
			e.printStackTrace();
		}  
	    return m_hora;
	}

	private JFormattedTextField getDeTextField() {
		if(deTextField==null){
			deTextField = new JFormattedTextField(getMask());  
			deTextField.addFocusListener(new java.awt.event.FocusAdapter() {  
			    public void focusLost(java.awt.event.FocusEvent e) {  
			          
			        String pega = "" ;  
			        String hora = null;  
			        String minuto = null ;  
			        int conta_pega = 0;  
			        int conta_hora = 0;  
			        int conta_minuto = 0;  
			          
			        pega = deTextField.getText();  
			        pega = pega.trim();  
			        if ( pega.equals(":") && (getNotificarHorarioSmsCheckBox().isSelected() || getNotificarHorarioEmailCheckBox().isSelected())) {  
			            JOptionPane.showMessageDialog(null, "Informe o hor�rio!" ,"Valida��o Netz",JOptionPane.WARNING_MESSAGE);  
			            deTextField.setValue("");  
			            return;  
			        }  
			        conta_pega = pega.length();  
			        if ( conta_pega < 5 && (getNotificarHorarioSmsCheckBox().isSelected() || getNotificarHorarioEmailCheckBox().isSelected()) ) {  
			            JOptionPane.showMessageDialog(null, "Informe o hor�rio!" ,"Valida��o Netz",JOptionPane.WARNING_MESSAGE);  
			            deTextField.setValue("");  
			            return;  
			        }  
			          
			        if(!pega.equals(":") && conta_pega == 5){
				        hora  = pega.substring(0,2);  
				        minuto  = pega.substring(3,5);  
				        conta_hora = Integer.parseInt(hora);  
				        conta_minuto = Integer.parseInt(minuto);  
				          
				        if(conta_hora > 23) {  
				            JOptionPane.showMessageDialog(null, "A hora digitada � inv�lida! Informe um valor correto" ,"Valida��o Netz",JOptionPane.WARNING_MESSAGE);  
				            deTextField.setValue("");  
				            return;  
				        }  
				        if(conta_minuto > 59) {  
				            JOptionPane.showMessageDialog(null, "A hora digitada � inv�lida! Informe um valor correto" ,"Valida��o Netz",JOptionPane.WARNING_MESSAGE);  
				            deTextField.setValue("");  
				            return;  
				        }  
			        }
			    }  
			});  
		}
		return deTextField;
	}
	
	private JLabel getELabel() {
		if(eLabel==null){
			eLabel = new JLabel();
			eLabel.setText("e:");
			eLabel.setFont(eLabel.getFont().deriveFont(
					eLabel.getFont().getStyle() & ~Font.BOLD));
		}
		return eLabel;
	}

	private JFormattedTextField getAteTextField() {
		if(ateTextField==null){
			ateTextField = new JFormattedTextField(getMask());  
			ateTextField.addFocusListener(new java.awt.event.FocusAdapter() {  
			    public void focusLost(java.awt.event.FocusEvent e) {  
			          
			        String pega = "" ;  
			        String hora = null;  
			        String minuto = null ;  
			        int conta_pega = 0;  
			        int conta_hora = 0;  
			        int conta_minuto = 0;  
			          
			        pega = ateTextField.getText();  
			        pega = pega.trim();  
			        if ( pega.equals(":") && (getNotificarHorarioSmsCheckBox().isSelected() || getNotificarHorarioEmailCheckBox().isSelected())) {  
			            JOptionPane.showMessageDialog(null, "Informe o hor�rio!" ,"Valida��o Netz",JOptionPane.WARNING_MESSAGE);  
			            ateTextField.setValue("");  
			            return;  
			        }  
			        conta_pega = pega.length();  
			        if ( conta_pega < 5 && (getNotificarHorarioSmsCheckBox().isSelected() || getNotificarHorarioEmailCheckBox().isSelected()) ) {  
			            JOptionPane.showMessageDialog(null, "Informe o hor�rio!" ,"Valida��o Netz",JOptionPane.WARNING_MESSAGE);  
			            ateTextField.setValue("");  
			            return;  
			        }  
			          
			        if(!pega.equals(":") && conta_pega == 5){
				        hora  = pega.substring(0,2);  
				        minuto  = pega.substring(3,5);  
				        conta_hora = Integer.parseInt(hora);  
				        conta_minuto = Integer.parseInt(minuto);  
				          
				        if(conta_hora > 23) {  
				            JOptionPane.showMessageDialog(null, "A hora digitada � inv�lida! Informe um valor correto" ,"Valida��o Netz",JOptionPane.WARNING_MESSAGE);  
				            ateTextField.setValue("");  
				            return;  
				        }  
				        if(conta_minuto > 59) {  
				            JOptionPane.showMessageDialog(null, "A hora digitada � inv�lida! Informe um valor correto" ,"Valida��o Netz",JOptionPane.WARNING_MESSAGE);  
				            ateTextField.setValue("");  
				            return;  
				        } 
			        }
			    }  
			});
		}
		return ateTextField;
	}

	private JCheckBox getNotificarHorarioSmsCheckBox() {
		if(notificarHorarioSmsCheckBox==null){
			notificarHorarioSmsCheckBox = new JCheckBox("SMS");
			notificarHorarioSmsCheckBox.setFont(notificarHorarioSmsCheckBox.getFont().deriveFont(
					notificarHorarioSmsCheckBox.getFont().getStyle() & ~Font.BOLD));
		}
		return notificarHorarioSmsCheckBox;
	}

	private JCheckBox getNotificarHorarioEmailCheckBox() {
		if(notificarHorarioEmailCheckBox==null){
			notificarHorarioEmailCheckBox = new JCheckBox("E-mail");
			notificarHorarioEmailCheckBox.setFont(notificarHorarioEmailCheckBox.getFont().deriveFont(
					notificarHorarioEmailCheckBox.getFont().getStyle() & ~Font.BOLD));
		}
		return notificarHorarioEmailCheckBox;
	}

	
	
	private PanelButtonsConfNotificacoes getPanelButtonsConfNotificacoes() {
		if(panelButtonsConfNotificacoes==null){
			panelButtonsConfNotificacoes = new PanelButtonsConfNotificacoes();
		}
		return panelButtonsConfNotificacoes;
	}

	private GridBagConstraints getNotificarNivelTrafegoLabelConstraints() {
		if(notificarNivelTrafegoLabelConstraints==null){
			notificarNivelTrafegoLabelConstraints = formatoGeral();
			notificarNivelTrafegoLabelConstraints.gridx = 0;
			notificarNivelTrafegoLabelConstraints.gridy = 0;
			notificarNivelTrafegoLabelConstraints.gridwidth = 4;
		}
		return notificarNivelTrafegoLabelConstraints;
	}

	private GridBagConstraints getNotificarNivelTrafegoFieldConstraints() {
		if(notificarNivelTrafegoFieldConstraints==null){
			notificarNivelTrafegoFieldConstraints = formatoGeral();
			notificarNivelTrafegoFieldConstraints.gridx = 4;
			notificarNivelTrafegoFieldConstraints.gridy = 0;
			notificarNivelTrafegoFieldConstraints.ipadx = 40;
			notificarNivelTrafegoFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
		}
		return notificarNivelTrafegoFieldConstraints;
	}

	private GridBagConstraints getNotificaNivelTrafegoSmsConstraints() {
		if(notificaNivelTrafegoSmsConstraints==null){
			notificaNivelTrafegoSmsConstraints = formatoGeral();
			notificaNivelTrafegoSmsConstraints.gridx = 0;
			notificaNivelTrafegoSmsConstraints.gridy = 1;
		}
		return notificaNivelTrafegoSmsConstraints;
	}

	private GridBagConstraints getNotificaNivelTrafegoEmailConstraints() {
		if(notificaNivelTrafegoEmailConstraints==null){
			notificaNivelTrafegoEmailConstraints = formatoGeral();
			notificaNivelTrafegoEmailConstraints.gridx = 1;
			notificaNivelTrafegoEmailConstraints.gridy = 1;
		}
		return notificaNivelTrafegoEmailConstraints;
	}

	private GridBagConstraints getNotificarDesconhecidoLabelConstraints() {
		if(notificarDesconhecidoLabelConstraints==null){
			notificarDesconhecidoLabelConstraints = formatoGeral();
			notificarDesconhecidoLabelConstraints.gridx = 0;
			notificarDesconhecidoLabelConstraints.gridy = 2;
			notificarDesconhecidoLabelConstraints.gridwidth = 5;
		}
		return notificarDesconhecidoLabelConstraints;
	}


	private GridBagConstraints getNotificarDesconhecidoSmsCheckBoxConstraints() {
		if(notificarDesconhecidoSmsCheckBoxConstraints==null){
			notificarDesconhecidoSmsCheckBoxConstraints = formatoGeral();
			notificarDesconhecidoSmsCheckBoxConstraints.gridx = 0;
			notificarDesconhecidoSmsCheckBoxConstraints.gridy = 3;
		}
		return notificarDesconhecidoSmsCheckBoxConstraints;
	}

	private GridBagConstraints getNotificarDesconhecidoEmailCheckBoxConstraints() {
		if(notificarDesconhecidoEmailCheckBoxConstraints==null){
			notificarDesconhecidoEmailCheckBoxConstraints = formatoGeral();
			notificarDesconhecidoEmailCheckBoxConstraints.gridx = 1;
			notificarDesconhecidoEmailCheckBoxConstraints.gridy = 3;
		}
		return notificarDesconhecidoEmailCheckBoxConstraints;
	}

	private GridBagConstraints getNotificarMacsLabelConstraints() {
		if(notificarMacsLabelConstraints==null){
			notificarMacsLabelConstraints = formatoGeral();
			notificarMacsLabelConstraints.gridx = 0;
			notificarMacsLabelConstraints.gridy = 4;
			notificarMacsLabelConstraints.gridwidth = 5;
		}
		return notificarMacsLabelConstraints;
	}

	private GridBagConstraints getMacsLabelConstraints() {
		if(macsLabelConstraints==null){
			macsLabelConstraints = formatoGeral();
			macsLabelConstraints.gridx = 0;
			macsLabelConstraints.gridy = 5;
		}
		return macsLabelConstraints;
	}

	private GridBagConstraints getMacsTextFieldConstraints() {
		if(macsTextFieldConstraints==null){
			macsTextFieldConstraints = formatoGeral();
			macsTextFieldConstraints.gridx = 1;
			macsTextFieldConstraints.gridy = 5;
			macsTextFieldConstraints.gridwidth = 4;
		}
		return macsTextFieldConstraints;
	}

	private GridBagConstraints getNotificarMacsSmsCheckBoxConstraints() {
		if(notificarMacsSmsCheckBoxConstraints==null){
			notificarMacsSmsCheckBoxConstraints = formatoGeral();
			notificarMacsSmsCheckBoxConstraints.gridx = 0;
			notificarMacsSmsCheckBoxConstraints.gridy = 6;
		}
		return notificarMacsSmsCheckBoxConstraints;
	}

	private GridBagConstraints getNotificarMacsEmailCheckBoxConstraints() {
		if(notificarMacsEmailCheckBoxConstraints==null){
			notificarMacsEmailCheckBoxConstraints = formatoGeral();
			notificarMacsEmailCheckBoxConstraints.gridx = 1;
			notificarMacsEmailCheckBoxConstraints.gridy = 6;
		}
		return notificarMacsEmailCheckBoxConstraints;
	}

	private GridBagConstraints getNotificarHorarioLabelConstraints() {
		if(notificarHorarioLabelConstraints==null){
			notificarHorarioLabelConstraints = formatoGeral();
			notificarHorarioLabelConstraints.gridx = 0;
			notificarHorarioLabelConstraints.gridy = 7;
			notificarHorarioLabelConstraints.gridwidth = 5;
		}
		return notificarHorarioLabelConstraints;
	}

	private GridBagConstraints getDeTextFieldConstraints() {
		if(deTextFieldConstraints==null){
			deTextFieldConstraints = formatoGeral();
			deTextFieldConstraints.gridx = 0;
			deTextFieldConstraints.gridy = 8;
			deTextFieldConstraints.ipadx = 60;
		}
		return deTextFieldConstraints;
	}

	private GridBagConstraints getELabelConstraints() {
		if(eLabelConstraints==null){
			eLabelConstraints = formatoGeral();
			eLabelConstraints.gridx = 1;
			eLabelConstraints.gridy = 8;
			eLabelConstraints.insets = new Insets(1,20,1,1);
		}
		return eLabelConstraints;
	}

	private GridBagConstraints getAteTextFieldConstraints() {
		if(ateTextFieldConstraints==null){
			ateTextFieldConstraints = formatoGeral();
			ateTextFieldConstraints.gridx = 2;
			ateTextFieldConstraints.gridy = 8;
			ateTextFieldConstraints.ipadx = 60;
		}
		return ateTextFieldConstraints;
	}

	private GridBagConstraints getNotificarHorarioSmsCheckBoxConstraints() {
		if(notificarHorarioSmsCheckBoxConstraints==null){
			notificarHorarioSmsCheckBoxConstraints = formatoGeral();
			notificarHorarioSmsCheckBoxConstraints.gridx = 0;
			notificarHorarioSmsCheckBoxConstraints.gridy = 9;
		}
		return notificarHorarioSmsCheckBoxConstraints;
	}

	private GridBagConstraints getNotificarHorarioEmailCheckBoxConstraints() {
		if(notificarHorarioEmailCheckBoxConstraints==null){
			notificarHorarioEmailCheckBoxConstraints = formatoGeral();
			notificarHorarioEmailCheckBoxConstraints.gridx = 1;
			notificarHorarioEmailCheckBoxConstraints.gridy = 9;
		}
		return notificarHorarioEmailCheckBoxConstraints;
	}

	private GridBagConstraints getPanelButtonsConfNotificacoesConstraints() {
		if(panelButtonsConfNotificacoesConstraints==null){
			panelButtonsConfNotificacoesConstraints = formatoGeral();
			panelButtonsConfNotificacoesConstraints.gridx = 0;
			panelButtonsConfNotificacoesConstraints.gridy = 10;
			panelButtonsConfNotificacoesConstraints.gridwidth = 5;
		}
		return panelButtonsConfNotificacoesConstraints;
	}
	
	private GridBagConstraints formatoGeral(){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(1,1,1,1);
		return gbc;
	}
	
	private class PanelButtonsConfNotificacoes extends JPanel{
		
		private static final long serialVersionUID = 853317511099798677L;
		private JButton configurarEmailButton;
		private JButton configurarSmsButton;
	
		public PanelButtonsConfNotificacoes() {
			super();
			setLayout(new FlowLayout());
			add(getConfigurarEmailButton());
			add(getConfigurarSmsButton());
		}
		
		private JButton getConfigurarEmailButton() {
			if(configurarEmailButton==null){
				configurarEmailButton = new JButton();
				configurarEmailButton.setText("Configurar e-mail");
				configurarEmailButton.setFont(configurarEmailButton.getFont().deriveFont(
						configurarEmailButton.getFont().getStyle() & ~Font.BOLD));
				configurarEmailButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						for(PanelConfNotificacoesListener listener:listeners){
							listener.configurarEmail();
						}
					}
					
				});
			}
			return configurarEmailButton;
		}

		private JButton getConfigurarSmsButton() {
			if(configurarSmsButton==null){
				configurarSmsButton = new JButton();
				configurarSmsButton.setText("Configurar SMS");
				configurarSmsButton.setFont(configurarSmsButton.getFont().deriveFont(
						configurarSmsButton.getFont().getStyle() & ~Font.BOLD));
				configurarSmsButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						for(PanelConfNotificacoesListener listener:listeners){
							listener.configurarSms();
						}
					}
					
				});
			}
			return configurarSmsButton;
		}


		
	}
	
	public void addPanelConfNotificacoesListener(PanelConfNotificacoesListener listener){
		listeners.add(listener);
	}

	public GeneralConfigurationTO getConfigurations(){
		
		GeneralConfigurationTO configurationTO = new GeneralConfigurationTO();
		
		int level = 0;
		try{
			level = Integer.parseInt(getNotificarNivelTrafegoField().getText());
		}
		catch (Exception e) {		}
		
		configurationTO.setNotifyTrafficLevel(level);
		configurationTO.setNotifyTrafficSms(getNotificaNivelTrafegoSms().isSelected());
		configurationTO.setNotifyTrafficEmail(getNotificaNivelTrafegoEmail().isSelected());
		
		configurationTO.setNotifyUnknownHostSms(getNotificarDesconhecidoSmsCheckBox().isSelected());
		configurationTO.setNotifyUnknownHostEmail(getNotificarDesconhecidoEmailCheckBox().isSelected());
		
		configurationTO.setNotifyMacs(getMacsTextField().getText());
		configurationTO.setNotifyMacsSms(getNotificarMacsSmsCheckBox().isSelected());
		configurationTO.setNotifyMacsEmail(getNotificarMacsEmailCheckBox().isSelected());
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm"); 
		
		try {
			configurationTO.setNotifyTimeFrom(format.parse(getDeTextField().getText()));	
			configurationTO.setNotifyTimeTo(format.parse(getAteTextField().getText()));
		} catch (ParseException e) {
			configurationTO.setNotifyTimeFrom(null);
			configurationTO.setNotifyTimeTo(null);
		}
		
		configurationTO.setNotifyTimeSms(getNotificarHorarioSmsCheckBox().isSelected());
		configurationTO.setNotifyTimeEmail(getNotificarHorarioEmailCheckBox().isSelected());
		
		return configurationTO;
		
	}
	
	public void setConfigurations(SmsConfigurationTO smsConfigurationTO,
			EmailConfigurationTO emailConfigurationTO,
			GeneralConfigurationTO generalConfigurationTO) {

		getNotificarNivelTrafegoField().setText(generalConfigurationTO.getNotifyTrafficLevel()+"");
		getNotificaNivelTrafegoSms().setSelected(generalConfigurationTO.isNotifyTrafficSms());
		getNotificaNivelTrafegoEmail().setSelected(generalConfigurationTO.isNotifyTrafficEmail());
		
		getNotificarDesconhecidoSmsCheckBox().setSelected(generalConfigurationTO.isNotifyUnknownHostSms());
		getNotificarDesconhecidoEmailCheckBox().setSelected(generalConfigurationTO.isNotifyUnknownHostEmail());
		
		getMacsTextField().setText(generalConfigurationTO.getNotifyMacs());
		getNotificarMacsSmsCheckBox().setSelected(generalConfigurationTO.isNotifyMacsSms());
		getNotificarMacsEmailCheckBox().setSelected(generalConfigurationTO.isNotifyMacsEmail());
		

		
		if(generalConfigurationTO.getNotifyTimeFrom()!=null){
			SimpleDateFormat format = new SimpleDateFormat("HH:mm"); 
			String hora = format.format(generalConfigurationTO.getNotifyTimeFrom());
			getDeTextField().setText(hora);
		}
		
		if(generalConfigurationTO.getNotifyTimeTo()!=null){
			SimpleDateFormat format = new SimpleDateFormat("HH:mm"); 
			String hora = format.format(generalConfigurationTO.getNotifyTimeTo());
			getAteTextField().setText(hora);
		}
		
		getNotificarHorarioSmsCheckBox().setSelected(generalConfigurationTO.isNotifyTimeSms());
		getNotificarHorarioEmailCheckBox().setSelected(generalConfigurationTO.isNotifyTimeEmail());
		
	}

}
