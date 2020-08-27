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

package netz.gui.unificada;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.netz.hosts.model.HostTO;

import netz.gui.listeners.FrameUsuarioListener;

public class FrameUsuario extends JFrame {

	private static final long serialVersionUID = -5083342325397485441L;

	private FrameUsuarioFields frameUsuarioFields;
	private FrameUsuarioButtons frameUsuarioButtons;
	private ArrayList<FrameUsuarioListener> listeners = new ArrayList<FrameUsuarioListener>();
	
	public FrameUsuario() {
		setLayout(new BorderLayout());
		add(getFrameUsuarioFields(),BorderLayout.NORTH);
		add(getFrameUsuarioButtons(),BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
	}
	
	
	private FrameUsuarioFields getFrameUsuarioFields() {
		if(frameUsuarioFields==null){
			frameUsuarioFields = new FrameUsuarioFields();
		}
		return frameUsuarioFields;
	}

	private FrameUsuarioButtons getFrameUsuarioButtons() {
		if(frameUsuarioButtons==null){
			frameUsuarioButtons = new FrameUsuarioButtons();
		}
		return frameUsuarioButtons;
	}

	private class FrameUsuarioFields extends JPanel{
		
		private static final long serialVersionUID = -9028434431339552732L;

		private JLabel detalhesLabel;
		private JLabel nomeVisualizacaoLabel;
		private JTextField nomeVisualizacaoTextField;
		private JLabel nomeHostLabel;
		private JTextField nomeHostTextField;
		private JLabel endIpLabel;
		private JTextField endIpTextField;
		private JLabel endMacLabel;
		private JTextField endMacTextField;
		
		private GridBagConstraints detalhesLabelConstraints;
		private GridBagConstraints nomeVisualizacaoLabelConstraints;
		private GridBagConstraints nomeVisualizacaoTextFieldConstraints;
		private GridBagConstraints nomeHostLabelConstraints;
		private GridBagConstraints nomeHostTextFieldConstraints;
		private GridBagConstraints endIpLabelConstraints;
		private GridBagConstraints endIpTextFieldConstraints;
		private GridBagConstraints endMacLabelConstraints;
		private GridBagConstraints endMacTextFieldConstraints;
		
		
		public FrameUsuarioFields() {
			setLayout(new GridBagLayout());
			initialize();
			setPreferredSize(new Dimension(350,160));
		}

		private void initialize() {
			add(getDetalhesLabel(),getDetalhesLabelConstraints());
			add(getNomeVisualizacaoLabel(),getNomeVisualizacaoLabelConstraints());
			add(getNomeVisualizacaoTextField(),getNomeVisualizacaoTextFieldConstraints());
			add(getNomeHostLabel(),getNomeHostLabelConstraints());
			add(getNomeHostTextField(),getNomeHostTextFieldConstraints());
			add(getEndIpLabel(),getEndIpLabelConstraints());
			add(getEndIpTextField(),getEndIpTextFieldConstraints());
			add(getEndMacLabel(),getEndMacLabelConstraints());
			add(getEndMacTextField(),getEndMacTextFieldConstraints());
		}
		
		private JLabel getDetalhesLabel() {
			if(detalhesLabel==null){
				detalhesLabel = new JLabel();
				detalhesLabel.setText("Detalhes do host");
				detalhesLabel.setFont(detalhesLabel.getFont().deriveFont(
						detalhesLabel.getFont().getStyle() ^ Font.BOLD));
			}
			return detalhesLabel;
		}
		private JLabel getNomeVisualizacaoLabel() {
			if(nomeVisualizacaoLabel==null){
				nomeVisualizacaoLabel = new JLabel();
				nomeVisualizacaoLabel.setText("Nome para visualiza��o");
			}
			return nomeVisualizacaoLabel;
		}
		private JTextField getNomeVisualizacaoTextField() {
			if(nomeVisualizacaoTextField==null){
				nomeVisualizacaoTextField = new JTextField();
			}
			return nomeVisualizacaoTextField;
		}
		private JLabel getNomeHostLabel() {
			if(nomeHostLabel==null){
				nomeHostLabel = new JLabel();
				nomeHostLabel.setText("Nome do host");
			}
			return nomeHostLabel;
		}
		private JTextField getNomeHostTextField() {
			if(nomeHostTextField==null){
				nomeHostTextField = new JTextField();
				nomeHostTextField.setEditable(false);
			}
			return nomeHostTextField;
		}
		private JLabel getEndIpLabel() {
			if(endIpLabel==null){
				endIpLabel = new JLabel();
				endIpLabel.setText("Endere�o IP");
			}
			return endIpLabel;
		}
		private JTextField getEndIpTextField() {
			if(endIpTextField==null){
				endIpTextField = new JTextField();
				endIpTextField.setEditable(false);
			}
			return endIpTextField;
		}
		private JLabel getEndMacLabel() {
			if(endMacLabel==null){
				endMacLabel = new JLabel();
				endMacLabel.setText("Endere�o MAC");
			}
			return endMacLabel;
		}
		private JTextField getEndMacTextField() {
			if(endMacTextField==null){
				endMacTextField = new JTextField();
				endMacTextField.setEditable(false);
			}
			return endMacTextField;
		}
		
		
		private GridBagConstraints getDetalhesLabelConstraints() {
			if(detalhesLabelConstraints==null){
				detalhesLabelConstraints = formatoGeral();
				detalhesLabelConstraints.gridx = 0;
				detalhesLabelConstraints.gridy = 0;
				detalhesLabelConstraints.insets = new Insets(1,1,10,1);
				detalhesLabelConstraints.gridwidth = 2;
			}
			return detalhesLabelConstraints;
		}
		private GridBagConstraints getNomeVisualizacaoLabelConstraints() {
			if(nomeVisualizacaoLabelConstraints==null){
				nomeVisualizacaoLabelConstraints = formatoGeral();
				nomeVisualizacaoLabelConstraints.gridx = 0;
				nomeVisualizacaoLabelConstraints.gridy = 1;
			}
			return nomeVisualizacaoLabelConstraints;
		}
		private GridBagConstraints getNomeVisualizacaoTextFieldConstraints() {
			if(nomeVisualizacaoTextFieldConstraints==null){
				nomeVisualizacaoTextFieldConstraints = formatoGeral();
				nomeVisualizacaoTextFieldConstraints.gridx = 1;
				nomeVisualizacaoTextFieldConstraints.gridy = 1;
				nomeVisualizacaoTextFieldConstraints.ipadx = 190;
			}
			return nomeVisualizacaoTextFieldConstraints;
		}
		private GridBagConstraints getNomeHostLabelConstraints() {
			if(nomeHostLabelConstraints==null){
				nomeHostLabelConstraints = formatoGeral();
				nomeHostLabelConstraints.gridx = 0;
				nomeHostLabelConstraints.gridy = 2;
			}
			return nomeHostLabelConstraints;
		}
		private GridBagConstraints getNomeHostTextFieldConstraints() {
			if(nomeHostTextFieldConstraints==null){
				nomeHostTextFieldConstraints = formatoGeral();
				nomeHostTextFieldConstraints.gridx = 1;
				nomeHostTextFieldConstraints.gridy = 2;
			}
			return nomeHostTextFieldConstraints;
		}
		private GridBagConstraints getEndIpLabelConstraints() {
			if(endIpLabelConstraints==null){
				endIpLabelConstraints = formatoGeral();
				endIpLabelConstraints.gridx = 0;
				endIpLabelConstraints.gridy = 3;
			}
			return endIpLabelConstraints;
		}
		private GridBagConstraints getEndIpTextFieldConstraints() {
			if(endIpTextFieldConstraints==null){
				endIpTextFieldConstraints = formatoGeral();
				endIpTextFieldConstraints.gridx = 1;
				endIpTextFieldConstraints.gridy = 3;
			}
			return endIpTextFieldConstraints;
		}
		private GridBagConstraints getEndMacLabelConstraints() {
			if(endMacLabelConstraints==null){
				endMacLabelConstraints = formatoGeral();
				endMacLabelConstraints.gridx = 0;
				endMacLabelConstraints.gridy = 4;
			}
			return endMacLabelConstraints;
		}
		private GridBagConstraints getEndMacTextFieldConstraints() {
			if(endMacTextFieldConstraints==null){
				endMacTextFieldConstraints = formatoGeral();
				endMacTextFieldConstraints.gridx = 1;
				endMacTextFieldConstraints.gridy = 4;
			}
			return endMacTextFieldConstraints;
		}
		
		private GridBagConstraints formatoGeral(){
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(1,1,1,1);
			return gbc;
		}

		public HostTO getDados() {
			HostTO hostTO = new HostTO();
			hostTO.setViewName(getNomeVisualizacaoTextField().getText());
			hostTO.setHostName(getNomeHostTextField().getText());
			hostTO.setIpAddress(getEndIpTextField().getText());
			hostTO.setMacAddress(getEndMacTextField().getText());
			return hostTO;
		}
		
		public void setDados(HostTO hostTO){
			getNomeVisualizacaoTextField().setText(hostTO.getViewName());
			getNomeHostTextField().setText(hostTO.getHostName());
			getEndIpTextField().setText(hostTO.getIpAddress());
			getEndMacTextField().setText(hostTO.getMacAddress());
		}

	}
	
	private class FrameUsuarioButtons extends JPanel{
		
		private static final long serialVersionUID = 8781487595708833720L;
		private JButton salvarButton;
		private JButton cancelarButton;
		
		public FrameUsuarioButtons() {
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
						for(FrameUsuarioListener listener: listeners){
							listener.salvarHost(getFrameUsuarioFields().getDados());
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
	
	public void addFrameUsuarioListener(FrameUsuarioListener listener){
		listeners.add(listener);
	}


	public void setDados(HostTO hostTO) {
		getFrameUsuarioFields().setDados(hostTO);
	}
}
