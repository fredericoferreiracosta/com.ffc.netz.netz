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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

import br.netz.hosts.model.HostTO;

public class SortedListModel extends AbstractListModel {

	private static final long serialVersionUID = -1566070499503157765L;
	private SortedSet<HostTO> model;

	public SortedListModel() {
		model = new TreeSet<HostTO>(new HostComparator());
	}

	public int getSize() {
		return model.size();
	}

	public Object getElementAt(int index) {
		try{
			return model.toArray()[index];
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void add(HostTO element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}

	public void addAll(HostTO elements[]) {
		Collection<HostTO> c = Arrays.asList(elements);
		model.addAll(c);
		fireContentsChanged(this, 0, getSize());
	}

	public void clear() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
	}

	public boolean contains(Object element) {
		return model.contains(element);
	}

	public Object firstElement() {
		return model.first();
	}

	public Iterator<HostTO> iterator() {
		return model.iterator();
	}

	public Object lastElement() {
		return model.last();
	}

	public boolean removeElement(HostTO element) {
		boolean removed = model.remove(element);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		return removed;
	}

	public void updateHostList(HostTO hostTO) {
		for(int i=0;i<model.size();i++){
			HostTO h = (HostTO) model.toArray()[i];
			if(h.getMacAddress().equals(hostTO.getMacAddress())){
				model.remove(h);
				model.add(hostTO);
			}
		}
		fireContentsChanged(this, 0, getSize());
	}

}