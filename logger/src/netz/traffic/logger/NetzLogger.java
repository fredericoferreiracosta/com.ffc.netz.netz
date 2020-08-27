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

package netz.traffic.logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class NetzLogger extends Logger{

	private static Logger instance = null;
	private static Properties logProperties = new Properties();
	private static final String LOG_PROPERTIES_FILE = "resources/log4j.properties";
	
	private NetzLogger(String name) {
		super(name);
	}

	public static Logger getInstance(){
		if(instance==null){
			try {
				logProperties.load(new FileInputStream(LOG_PROPERTIES_FILE));
				PropertyConfigurator.configure(logProperties);
				instance = getLogger("Netz");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
}
