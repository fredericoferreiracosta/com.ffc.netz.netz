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

package br.netz.configuration.model;

public class ConfigurationDAOException extends Exception {

	private static final long serialVersionUID = -8196985728784817377L;

	public ConfigurationDAOException() {
		super();
	}

	public ConfigurationDAOException(String arg0) {
		super(arg0);
	}

	public ConfigurationDAOException(Throwable arg0) {
		super(arg0);
	}

	public ConfigurationDAOException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
