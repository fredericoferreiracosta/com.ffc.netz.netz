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

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import netz.gui.listeners.PanelUsuariosListener;
import br.netz.hosts.controller.ListHosts;
import br.netz.hosts.listener.HostListener;
import br.netz.hosts.model.HostTO;

public class PanelUsuarios extends JPanel {
	
	private static final long serialVersionUID = 160696458607965617L;

	private PanelUsuariosOnline panelUsuariosOnline;
	private PanelUsuariosOffline panelUsuariosOffline;
	private ListHosts listHosts;
	private ArrayList<PanelUsuariosListener> listeners = new ArrayList<PanelUsuariosListener>();
	private HostTO hostSelected = new HostTO();
	
	public PanelUsuarios() {
		setLayout(new GridLayout(2,1));
		add(getPanelUsuariosOnline());
		add(getPanelUsuariosOffline());
		getListHosts();
	}
		
	private ListHosts getListHosts() {
		listHosts = ListHosts.getInstance();
		listHosts.addHostListener(new HostListener(){

			@Override
			public void addOfflineHost(HostTO host) {
				getPanelUsuariosOffline().addOfflineHost(host);
			}

			@Override
			public void addOnlineHost(HostTO host) {
				getPanelUsuariosOnline().addOnlineHost(host);
			}

			@Override
			public void removeOfflineHost(HostTO host) {
				getPanelUsuariosOffline().removeOfflineHost(host);
			}

			@Override
			public void removeOnlineHost(HostTO host) {
				getPanelUsuariosOnline().removeOnlineHost(host);
			}
			
		});
		return listHosts;
	}

	private PanelUsuariosOnline getPanelUsuariosOnline() {
		if(panelUsuariosOnline==null){
			panelUsuariosOnline = new PanelUsuariosOnline();
		}
		return panelUsuariosOnline;
	}

	private PanelUsuariosOffline getPanelUsuariosOffline() {
		if(panelUsuariosOffline==null){
			panelUsuariosOffline = new PanelUsuariosOffline();
		}
		return panelUsuariosOffline;
	}
	
	private JPopupMenu popupMenu;
	
	protected JPopupMenu getPopupMenu(){
		if(popupMenu==null){
			popupMenu = new JPopupMenu();
			JMenuItem editarItem = new JMenuItem("Editar");
			editarItem.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					for(PanelUsuariosListener listener: listeners){
						listener.editarUsuario(hostSelected);
					}
				}
				
			});
			popupMenu.add(editarItem);
			JMenuItem conhecidoItem = new JMenuItem("Converter para conhecido");
			conhecidoItem.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					for(PanelUsuariosListener listener: listeners){
						listener.adicionarUsuario(hostSelected);
					}
				}
				
			});
			popupMenu.add(conhecidoItem);
		}
		return popupMenu;
	}
	
	public void addPanelUsuariosListener(PanelUsuariosListener listener){
		listeners.add(listener);
	}


	private class PanelUsuariosOffline extends JPanel{
		
		private static final long serialVersionUID = 797877637260933530L;

		private JList hostsList;
		private SortedListModel sourceListModel;

		private JScrollPane scrollPane;

		public PanelUsuariosOffline() {
			super();
			initialize();
			setPreferredSize(new Dimension(360, 200));
			setBorder(new TitledBorder(BorderFactory.createTitledBorder("Offline")));
			
		}

		public void initialize() {
			add(getScrollPane());
		}
		
		private JScrollPane getScrollPane() {
			if (scrollPane == null) {
				scrollPane = new JScrollPane();
				scrollPane.setViewportView(getHostsList());
				scrollPane.setPreferredSize(new Dimension(340, 160));
				scrollPane
						.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			}
			return scrollPane;
		}

		private JList getHostsList() {
			if (hostsList == null) {
				sourceListModel = new SortedListModel();
				hostsList = new JList(sourceListModel);
				hostsList.setCellRenderer(new EntryListCellRendererOffline());
			}
			return hostsList;
		}


		public void addOfflineHost(HostTO host) {
			sourceListModel.add(host);
		}
		
		public void removeOfflineHost(HostTO host) {
			sourceListModel.removeElement(host);
		}

	}
	
	private class PanelUsuariosOnline extends JPanel {

		private static final long serialVersionUID = 797877637260933530L;

		private JList hostsList;
		private SortedListModel sourceListModel;

		private JScrollPane scrollPane;

		public PanelUsuariosOnline() {
			super();
			initialize();
			setPreferredSize(new Dimension(360, 200));
			setBorder(new TitledBorder(BorderFactory.createTitledBorder("Online")));
			setBounds(2, 2, 2, 2);
		}

		public void initialize() {
			add(getScrollPane());
		}

		private JScrollPane getScrollPane() {
			if (scrollPane == null) {
				scrollPane = new JScrollPane();
				scrollPane.setViewportView(getHostsList());
				scrollPane.setPreferredSize(new Dimension(340, 160));
				scrollPane
						.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			}
			return scrollPane;
		}

		private JList getHostsList() {
			if (hostsList == null) {
				sourceListModel = new SortedListModel();
				hostsList = new JList(sourceListModel);
				hostsList.setCellRenderer(new EntryListCellRendererOnline());
				hostsList.addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent arg0) {
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						hostSelected = (HostTO) hostsList.getSelectedValue();
						showPopup(arg0);
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						hostSelected = (HostTO) hostsList.getSelectedValue();
						showPopup(arg0);
					}
					
				});
			}
			return hostsList;
		}

		private void showPopup(MouseEvent e) {
	        if (e.isPopupTrigger()) {
	            getPopupMenu().show(e.getComponent(),
	                       e.getX(), e.getY());
	        }
	    }
		
		public void addOnlineHost(HostTO host) {
			sourceListModel.add(host);
		}
		
		public void removeOnlineHost(HostTO host) {
			sourceListModel.removeElement(host);
		}

		public void updateHostList(HostTO hostTO) {
			sourceListModel.updateHostList(hostTO);
		}

	}

	public void updateHostList(HostTO hostTO) {
		getPanelUsuariosOnline().updateHostList(hostTO);
		repaint();
	}
}