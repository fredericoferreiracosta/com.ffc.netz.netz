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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;

public class PanelConfGraficos extends JPanel{

	private static final long serialVersionUID = 2589954913062306632L;

	private JLabel atualizacaoLabel;
	private JTextField atualizacaoTextField;
	private boolean changed = false;
	private String text;
	
	public PanelConfGraficos() {
		initialize();
		setBorder(new TitledBorder(BorderFactory.createTitledBorder("Gr�ficos")));
		setPreferredSize(new Dimension(450,50));
	}

	private void initialize() {
		add(getAtualizacaoLabel());
		add(getAtualizacaoTextField());
	}

	private JLabel getAtualizacaoLabel() {
		if(atualizacaoLabel==null){
			atualizacaoLabel = new JLabel();
			atualizacaoLabel.setText("Auto atualiza��o a cada (segundos):");
			atualizacaoLabel.setFont(atualizacaoLabel.getFont().deriveFont(
					atualizacaoLabel.getFont().getStyle() & ~Font.BOLD));
		}
		return atualizacaoLabel;
	}

	private JTextField getAtualizacaoTextField() {
		if(atualizacaoTextField==null){
				atualizacaoTextField = new JTextField();
				atualizacaoTextField.setColumns(5);
				atualizacaoTextField.addKeyListener(new KeyListener(){

					@Override
					public void keyPressed(KeyEvent arg0) {
					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						changed = true;
					}

					@Override
					public void keyTyped(KeyEvent arg0) {
					}
					
				});
				atualizacaoTextField.addFocusListener(new FocusListener(){

					@Override
					public void focusGained(FocusEvent arg0) {
					}

					@Override
					public void focusLost(FocusEvent arg0) {
						text = atualizacaoTextField.getText();

						try{
							int i = Integer.parseInt(text);
							if(i<0){
					            JOptionPane.showMessageDialog(null, "O valor digitado � inv�lido! Informe um n�mero inteiro e maior que zero." ,"Valida��o Netz",JOptionPane.WARNING_MESSAGE);  
								text="3";
								changed = false;
							}
						}catch (Exception e) {
							text="3";
				            JOptionPane.showMessageDialog(null, "O valor digitado � inv�lido! Informe um n�mero inteiro e maior que zero." ,"Valida��o Netz",JOptionPane.WARNING_MESSAGE);  
							changed = false;
						}
						if(changed==true){
							JOptionPane.showMessageDialog(
									null,
									"As altera��es ser�o aplicadas da pr�xima vez que o NETZ for iniciado.",
									"Configura��es",
									JOptionPane.INFORMATION_MESSAGE);
						}
						changed = false;
						atualizacaoTextField.setText(text);
					}
					
				});
		}
		return atualizacaoTextField;
	}

	public void setConfigurations(SmsConfigurationTO smsConfigurationTO,
			EmailConfigurationTO emailConfigurationTO,
			GeneralConfigurationTO generalConfigurationTO) {
		getAtualizacaoTextField().setText(generalConfigurationTO.getGraphicUpdate() + "");
	}
	
	public GeneralConfigurationTO getConfigurations(){
		GeneralConfigurationTO generalConfigurationTO = new GeneralConfigurationTO();
		int update = 3;
		try{
			String num = getAtualizacaoTextField().getText().trim();
			update = Integer.parseInt(num);
		}catch (Exception e) {		}
		generalConfigurationTO.setGraphicUpdate(update);
		return generalConfigurationTO;
	}
	
}
