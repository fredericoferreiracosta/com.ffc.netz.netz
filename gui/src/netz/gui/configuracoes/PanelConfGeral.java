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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import netz.gui.listeners.PanelConfGeralListener;
import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;

public class PanelConfGeral extends JPanel {

	private static final long serialVersionUID = -6273222844340469496L;

	private JLabel interfaceLabel;
	private JComboBox interfaceComboBox;
	private JCheckBox exclusaoCheckBox;
	private JComboBox exclusaoComboBox;
	private JButton limparButton;
	private JLabel emptyLabel;
	
	private GridBagConstraints interfaceLabelConstraints;
	private GridBagConstraints interfaceComboBoxConstraints;
	private GridBagConstraints exclusaoCheckBoxConstraints;
	private GridBagConstraints exclusaoComboBoxConstraints;
	private GridBagConstraints limparButtonConstraints;
	private GridBagConstraints emptyLabelConstraints;
	private boolean first = true;
	
	private ArrayList<PanelConfGeralListener> listeners = new ArrayList<PanelConfGeralListener>();
	
	public PanelConfGeral() {
		initialize();
		setBorder(new TitledBorder(BorderFactory.createTitledBorder("Geral")));
		setPreferredSize(new Dimension(450,150));
	}
	
	private void initialize(){
		setLayout(new GridBagLayout());
		add(getInterfaceLabel(), getInterfaceLabelConstraints());
		add(getInterfaceComboBox(),getInterfaceComboBoxConstraints());
		add(getExclusaoCheckBox(),getExclusaoCheckBoxConstraints());
		add(getExclusaoComboBox(),getExclusaoComboBoxConstraints());
		add(getLimparButton(),getLimparButtonConstraints());
		add(getEmptyLabel(),getEmptyLabelConstraints());
	}
	
	private JLabel getInterfaceLabel() {
		if(interfaceLabel==null){
			interfaceLabel = new JLabel();
			interfaceLabel.setText("Interface de captura: ");
			interfaceLabel.setFont(interfaceLabel.getFont().deriveFont(
					interfaceLabel.getFont().getStyle() & ~Font.BOLD));
		}
		return interfaceLabel;
	}
	private JComboBox getInterfaceComboBox() {
		if(interfaceComboBox==null){
			NetworkInterface[] devices = JpcapCaptor.getDeviceList();
			ArrayList<String> devNames = new ArrayList<String>();
			for(NetworkInterface dev : devices){
				devNames.add(dev.description);
			}
			interfaceComboBox = new JComboBox(devNames.toArray());
			interfaceComboBox.setFont(interfaceComboBox.getFont().deriveFont(
					interfaceComboBox.getFont().getStyle() & ~Font.BOLD));
			interfaceComboBox.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					if(!first){
					JOptionPane.showMessageDialog(
							null,
							"As altera��es ser�o aplicadas da pr�xima vez que o NETZ for iniciado.",
							"Configura��es",
							JOptionPane.INFORMATION_MESSAGE);
					}
					first = false;
				}
				
			});
		}
		return interfaceComboBox;
	}
	private JCheckBox getExclusaoCheckBox() {
		if(exclusaoCheckBox==null){
			exclusaoCheckBox = new JCheckBox();
			exclusaoCheckBox.setText("Exclus�o autom�tica de hist�rico mais antigo que:");
			exclusaoCheckBox.setFont(exclusaoCheckBox.getFont().deriveFont(
					exclusaoCheckBox.getFont().getStyle() & ~Font.BOLD));
		}
		return exclusaoCheckBox;
	}
	private JComboBox getExclusaoComboBox() {
		if(exclusaoComboBox==null){
			exclusaoComboBox = new JComboBox(new String[]{"1 dia","1 semana","1 m�s","3 meses","6 meses","1 ano"});
			exclusaoComboBox.setFont(exclusaoComboBox.getFont().deriveFont(
					exclusaoComboBox.getFont().getStyle() & ~Font.BOLD));
		}
		return exclusaoComboBox;
	}
	private JButton getLimparButton() {
		if(limparButton==null){
			limparButton = new JButton();
			limparButton.setText("Limpar Hist�rico");
			limparButton.setFont(limparButton.getFont().deriveFont(
					limparButton.getFont().getStyle() & ~Font.BOLD));
			limparButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					for(PanelConfGeralListener listener: listeners){
						listener.limparHistorico();
					}
				}
				
			});
		}
		return limparButton;
	}
	
	private JLabel getEmptyLabel() {
		if(emptyLabel==null){
			emptyLabel = new JLabel();
		}
		return emptyLabel;
	}


	private GridBagConstraints getInterfaceLabelConstraints() {
		if(interfaceLabelConstraints==null){
			interfaceLabelConstraints = formatoGeral();
			interfaceLabelConstraints.gridx = 0;
			interfaceLabelConstraints.gridy = 0;
			interfaceLabelConstraints.gridwidth = 1;
		}
		return interfaceLabelConstraints;
	}
	private GridBagConstraints getInterfaceComboBoxConstraints() {
		if(interfaceComboBoxConstraints==null){
			interfaceComboBoxConstraints = formatoGeral();
			interfaceComboBoxConstraints.gridx = 1;
			interfaceComboBoxConstraints.gridy = 0;
			interfaceComboBoxConstraints.gridwidth = 4;
		}
		return interfaceComboBoxConstraints;
	}
	private GridBagConstraints getExclusaoCheckBoxConstraints() {
		if(exclusaoCheckBoxConstraints==null){
			exclusaoCheckBoxConstraints = formatoGeral();
			exclusaoCheckBoxConstraints.gridx = 0;
			exclusaoCheckBoxConstraints.gridy = 1;
			exclusaoCheckBoxConstraints.gridwidth = 5;
		}
		return exclusaoCheckBoxConstraints;
	}
	private GridBagConstraints getExclusaoComboBoxConstraints() {
		if(exclusaoComboBoxConstraints==null){
			exclusaoComboBoxConstraints = formatoGeral();
			exclusaoComboBoxConstraints.gridx = 0;
			exclusaoComboBoxConstraints.gridy = 2;
			exclusaoComboBoxConstraints.gridwidth = 2;
			exclusaoComboBoxConstraints.ipadx = 150;
		}
		return exclusaoComboBoxConstraints;
	}
	private GridBagConstraints getLimparButtonConstraints() {
		if(limparButtonConstraints==null){
			limparButtonConstraints = formatoGeral();
			limparButtonConstraints.gridx = 2;
			limparButtonConstraints.gridy = 2;
			limparButtonConstraints.gridwidth = 1;
			limparButtonConstraints.fill = GridBagConstraints.NONE;
		}
		return limparButtonConstraints;
	}
	
	private GridBagConstraints getEmptyLabelConstraints() {
		if(emptyLabelConstraints==null){
			emptyLabelConstraints = formatoGeral();
			emptyLabelConstraints.gridx = 0;
			emptyLabelConstraints.gridy = 3;
			emptyLabelConstraints.ipady = 60;
		}
		return emptyLabelConstraints;
	}
	
	private GridBagConstraints formatoGeral(){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(1,1,1,1);
		return gbc;
	}

	public void setConfigurations(SmsConfigurationTO smsConfigurationTO,
			EmailConfigurationTO emailConfigurationTO,
			GeneralConfigurationTO generalConfigurationTO) {
		getInterfaceComboBox().setSelectedItem(generalConfigurationTO.getNetworkInterface());
		getExclusaoCheckBox().setSelected(generalConfigurationTO.isDeleteData());
		int excItem = 0;
		try{
			excItem = Integer.parseInt(generalConfigurationTO.getDeleteDataTime());
		}catch (Exception e) {		}
		 
		getExclusaoComboBox().setSelectedIndex(excItem);
	}
	
	public GeneralConfigurationTO getConfigurations(){
		GeneralConfigurationTO generalConfigurationTO = new GeneralConfigurationTO();
		generalConfigurationTO.setNetworkInterface(getInterfaceComboBox().getSelectedItem()+"");
		generalConfigurationTO.setDeleteData(getExclusaoCheckBox().isSelected());
		generalConfigurationTO.setDeleteDataTime(getExclusaoComboBox().getSelectedIndex()+"");
		return generalConfigurationTO;
	}
	
	public void addPanelConfGeralListener(PanelConfGeralListener listener){
		listeners.add(listener);
	}
	
}
