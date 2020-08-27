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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import netz.traffic.logger.NetzLogger;

public class ConnectionManager {

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException exception) { }
	}
	
	public static Connection getConnection() throws ConfigurationDAOException {
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/netz", 
					"postgres", 
					"postgres");
		} catch (SQLException exception) {
			exception.printStackTrace();
			NetzLogger.getInstance().info("Could no connect to database");
			throw new ConfigurationDAOException("Could no connect to database", exception);
		}
	
		return connection;
	}

}