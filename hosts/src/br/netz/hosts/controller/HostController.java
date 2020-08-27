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

package br.netz.hosts.controller;

import java.util.Scanner;

import br.netz.configuration.model.ConfigurationDAOException;
import br.netz.hosts.model.HostDAOException;
import br.netz.traffic.model.TrafficDAOException;

public class HostController{
	
	public HostController() throws TrafficDAOException, HostDAOException, ConfigurationDAOException {
		
		new Thread(new HostTrafficAnalisysThread()).start();
		new Thread(new ListHostsAnalisysThread()).start();
	}
	

	public static boolean testHostOnline(String ip) {
		String os = System.getProperty("os.name");
		boolean retorno = false;
		if(os.contains("Windows") || os.contains("Micorsoft")){
			retorno = testHostOnlineWindows(ip);
		}
		else{
			retorno = testHostOnlineLinux(ip);
		}
		return retorno;
	}
	
	public static boolean testHostOnlineWindows(String ip) {
		String resposta = "";
		int fim = 0;
		boolean end = false;
		int count = 0;
		String comando = new String("ping -n 5 -w 600 " + ip);
		try {
			Scanner s = new Scanner(Runtime.getRuntime().exec(
					"cmd /c " + comando).getInputStream());

			while (s.hasNextLine()) {
				resposta = s.nextLine() + "\n";
				fim = resposta.length() - 8;

				for (int j = 0; j <= fim; j++) {
					if (resposta.substring(j, 8 + j).equals("Resposta")) {
						end = true;
						break;
					}
				}
				if (end == true)
					count++;
				else
					resposta = "OFF";
			}
		} catch (Exception e) {
		}

		
		if (count==0) {
			return false;
		} else {
			return true;
		}

	}
	
	public static boolean testHostOnlineLinux(String ip) {
		String resposta = "";
		int fim = 0;
		boolean end = false;
		int count = 0;
		String comando = new String("ping -c 5 -w 600 " + ip);
		try {
			Scanner s = new Scanner(Runtime.getRuntime().exec(comando).getInputStream());

			while (s.hasNextLine()) {
				resposta = s.nextLine() + "\n";
				fim = resposta.length() - 8;

				for (int j = 0; j <= fim; j++) {
					if (resposta.substring(j, 8 + j).equals("Resposta")) {
						end = true;
						break;
					}
				}
				if (end == true)
					count++;
				else
					resposta = "OFF";
			}
		} catch (Exception e) {
		}

		
		if (count==0) {
			return false;
		} else {
			return true;
		}

	}

}
