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

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import br.netz.configuration.model.EmailConfigurationTO;
import br.netz.configuration.model.GeneralConfigurationTO;
import br.netz.configuration.model.SmsConfigurationTO;

public class PanelConfContatos extends JPanel{

	private static final long serialVersionUID = 1443915205072720574L;

	private JCheckBox notificacaoCheckBox;
	
	public PanelConfContatos() {
		initialize();
		setBorder(new TitledBorder(BorderFactory.createTitledBorder("Lista de contatos")));
		setPreferredSize(new Dimension(450,100));

	}
	
	public void initialize(){
		add(getNotificacaoCheckBox());
	}
	
	private JCheckBox getNotificacaoCheckBox() {
		if(notificacaoCheckBox==null){
			notificacaoCheckBox = new JCheckBox();
			notificacaoCheckBox.setText("Mostrar notifica��o no System Tray quando o host entrar");
			notificacaoCheckBox.setFont(notificacaoCheckBox.getFont().deriveFont(
					notificacaoCheckBox.getFont().getStyle() & ~Font.BOLD));
		}
		return notificacaoCheckBox;
	}

	public void setConfigurations(SmsConfigurationTO smsConfigurationTO,
			EmailConfigurationTO emailConfigurationTO,
			GeneralConfigurationTO generalConfigurationTO) {
		getNotificacaoCheckBox().setSelected(generalConfigurationTO.isNotifyHostEntry());
	}
	
	public GeneralConfigurationTO getConfigurations(){
		GeneralConfigurationTO generalConfigurationTO = new GeneralConfigurationTO();
		generalConfigurationTO.setNotifyHostEntry(getNotificacaoCheckBox().isSelected());
		return generalConfigurationTO;
	}
}
