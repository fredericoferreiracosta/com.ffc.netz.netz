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

import java.awt.Color;
import java.awt.Component;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import br.netz.hosts.model.HostTO;

final class EntryListCellRendererOffline extends JPanel implements ListCellRenderer {

	private static final long serialVersionUID = 2120405499033989105L;

	private static final int LIST_CELL_ICON_SIZE = 10;
    
    private JLabel hostLabel;
    private JLabel ipLabel;
    private JLabel imageLabel;
    
    EntryListCellRendererOffline() {
	    hostLabel = new JLabel(" ");
        ipLabel = new JLabel(" ");
        imageLabel = new JLabel();
        imageLabel.setOpaque(true);
        
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageLabel.setBackground(Color.GRAY);
        int imageSize = LIST_CELL_ICON_SIZE + 4;
        imageLabel.setBorder(new CompoundBorder(
                new LineBorder(Color.BLACK, 1),
                new EmptyBorder(1, 1, 1, 1)));
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        GroupLayout.SequentialGroup hg = layout.createSequentialGroup();
        layout.setHorizontalGroup(hg);
        hg.
                addComponent(imageLabel, imageSize, imageSize, imageSize).
                addGroup(layout.createParallelGroup().
                addComponent(hostLabel, 2, 2, Integer.MAX_VALUE).
                addComponent(ipLabel, 2, 2, Integer.MAX_VALUE));
        
        GroupLayout.ParallelGroup vg = layout.createParallelGroup();
        layout.setVerticalGroup(vg);
        vg.
                addComponent(imageLabel, GroupLayout.Alignment.CENTER, imageSize, imageSize, imageSize).
                addGroup(layout.createSequentialGroup().
                addComponent(hostLabel,5,10,Integer.MAX_VALUE).
                addComponent(ipLabel,5,10,Integer.MAX_VALUE));
        
        setOpaque(true);
    }
    
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        
    	 HostTO entry = (HostTO) value;
    	 
    	 hostLabel.setText(entry.getViewName());
    	 ipLabel.setText(entry.getIpAddress());
    	
    	if (isSelected) {
            adjustColors(list.getSelectionBackground(),
                    list.getSelectionForeground(), this, hostLabel, ipLabel);
        } else {
            adjustColors(list.getBackground(),
                    list.getForeground(), this, hostLabel, ipLabel);
        }
        
        
        return this;
    }
    
    private void adjustColors(Color bg, Color fg, Component...components) {
        for (Component c : components) {
            c.setForeground(fg);
            c.setBackground(bg);
        }
    }

}
