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

package br.netz.traffic.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import netz.traffic.logger.NetzLogger;

public class TrafficDAO {

	private Connection connection = null;

	public TrafficDAO() throws TrafficDAOException {
		connection = ConnectionManager.getConnection();
	}

	public void savePacket(PacketTO packet) throws TrafficDAOException {
		if (packet != null) {
			PreparedStatement sttm = null;
			try {
				String sql = "insert into packet(sourceMac,destinationMac,"
						+ "sourceIp,destinationIp,protocol,packetLength,sourceName,"
						+ "destinationName,sourcePort,destinationPort,ack,syn,fin,ackFlag,sequence,capturedate) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				sttm = connection.prepareStatement(sql);
				sttm.setString(1, packet.getSourceMac());
				sttm.setString(2, packet.getDestinationMac());
				sttm.setString(3, packet.getSourceIp());
				sttm.setString(4, packet.getDestinationIp());
				sttm.setString(5, packet.getProtocol());
				sttm.setFloat(6, packet.getPacketLength());
				sttm.setString(7, packet.getSourceName());
				sttm.setString(8, packet.getDestinationName());
				sttm.setInt(9, packet.getSourcePort());
				sttm.setInt(10, packet.getDestinationPort());
				sttm.setFloat(11, packet.getAck());
				sttm.setBoolean(12, packet.getSyn());
				sttm.setBoolean(13, packet.getFin());
				sttm.setBoolean(14, packet.getAckFlag());
				sttm.setFloat(15, packet.getSequence());
				
				if (packet.getCaptureDate() != null) {
					java.sql.Timestamp captureDate = new java.sql.Timestamp(packet
							.getCaptureDate().getTime());
					sttm.setTimestamp(16, captureDate);
				} else {
					sttm.setDate(16, null);
				}

				sttm.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
				NetzLogger.getInstance().error(
						"Erro ao comunicar com o banco de dados - "
								+ e.getMessage());
				throw new TrafficDAOException(
						"Erro ao comunicar com o banco de dados");
			} finally {
				close(sttm);
			}
		}
	}

	public ArrayList<PacketTO> getPackets() throws TrafficDAOException {
		return getPacketsFromIndex(0);
	}

	public ArrayList<PacketTO> getPacketsFromIndex(int index)
			throws TrafficDAOException {
		Statement sttm = null;
		PreparedStatement psttm = null;
		ResultSet rs = null;
		try {

			ArrayList<PacketTO> result = new ArrayList<PacketTO>();
			String sql = null;
			if (index == 0) {
				sql = "select * from packet";
				sttm = connection.createStatement();
				rs = sttm.executeQuery(sql);
			} else {
				sql = "select * from packet where id > ?";
				psttm = connection.prepareStatement(sql);
				psttm.setInt(1, index);
				rs = psttm.executeQuery();
			}

			while (rs.next()) {

				int id = rs.getInt("id");
				String sourceMac = rs.getString("sourceMac");
				String destinationMac = rs.getString("destinationMac");
				String sourceIp = rs.getString("sourceIp");
				String destinationIp = rs.getString("destinationIp");
				String protocol = rs.getString("protocol");
				Float packetLength = rs.getFloat("packetLength");
				String sourceName = rs.getString("sourceName");
				String destinationName = rs.getString("destinationName");
				Integer sourcePort = rs.getInt("sourcePort");
				Integer destinationPort = rs.getInt("destinationPort");
				Long ack = rs.getLong("ack");
				Boolean syn = rs.getBoolean("syn");
				Boolean fin = rs.getBoolean("fin");
				Boolean ackFlag = rs.getBoolean("ackFlag");
				Long sequence = rs.getLong("sequence");
				Date captureDate = rs.getDate("captureDate");

				PacketTO packet = new PacketTO();
				packet.setId(id);
				packet.setSourceMac(sourceMac);
				packet.setDestinationMac(destinationMac);
				packet.setSourceIp(sourceIp);
				packet.setDestinationIp(destinationIp);
				packet.setPacketLength(packetLength);
				packet.setProtocol(protocol);
				packet.setDestinationName(destinationName);
				packet.setSourceName(sourceName);
				packet.setSourcePort(sourcePort);
				packet.setDestinationPort(destinationPort);
				packet.setAck(ack);
				packet.setSyn(syn);
				packet.setFin(fin);
				packet.setAckFlag(ackFlag);
				packet.setSequence(sequence);
				packet.setCaptureDate(captureDate);

				result.add(packet);
			}
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"Erro ao comunicar com o banco de dados");
			throw new TrafficDAOException(
					"Erro ao comunicar com o banco de dados");
		} finally {
			close(sttm, rs);
			close(psttm, rs);
		}
	}

	public ArrayList<PacketTO> getPacketsBroadcastFromIndex(int index,
			String address) throws TrafficDAOException {
		PreparedStatement psttm = null;
		ResultSet rs = null;
		try {
			ArrayList<PacketTO> result = new ArrayList<PacketTO>();

			String sql = "select * from packet where id > ? "
					+ "AND ( destinationIp = ? OR destinationIp = '239.255.255.250' "
					+ "OR destinationIp = '224.0.0.252' OR destinationIp = '224.0.0.1' )";
			psttm = connection.prepareStatement(sql);
			psttm.setInt(1, index);
			psttm.setString(2, address);
			rs = psttm.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String sourceMac = rs.getString("sourceMac");
				String destinationMac = rs.getString("destinationMac");
				String sourceIp = rs.getString("sourceIp");
				String destinationIp = rs.getString("destinationIp");
				String protocol = rs.getString("protocol");
				Float packetLength = rs.getFloat("packetLength");
				String sourceName = rs.getString("sourceName");
				String destinationName = rs.getString("destinationName");
				Integer sourcePort = rs.getInt("sourcePort");
				Integer destinationPort = rs.getInt("destinationPort");
				Long ack = rs.getLong("ack");
				Boolean syn = rs.getBoolean("syn");
				Boolean fin = rs.getBoolean("fin");
				Boolean ackFlag = rs.getBoolean("ackFlag");
				Long sequence = rs.getLong("sequence");
				Date captureDate = rs.getDate("captureDate");

				PacketTO packet = new PacketTO();
				packet.setId(id);
				packet.setSourceMac(sourceMac);
				packet.setDestinationMac(destinationMac);
				packet.setSourceIp(sourceIp);
				packet.setDestinationIp(destinationIp);
				packet.setPacketLength(packetLength);
				packet.setProtocol(protocol);
				packet.setDestinationName(destinationName);
				packet.setSourceName(sourceName);
				packet.setSourcePort(sourcePort);
				packet.setDestinationPort(destinationPort);
				packet.setAck(ack);
				packet.setSyn(syn);
				packet.setFin(fin);
				packet.setAckFlag(ackFlag);
				packet.setSequence(sequence);
				packet.setCaptureDate(captureDate);

				result.add(packet);
			}
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"Erro ao comunicar com o banco de dados - "
							+ e.getMessage());
			throw new TrafficDAOException(
					"Erro ao comunicar com o banco de dados");
		} finally {
			close(psttm, rs);
		}
	}

	public ArrayList<PacketTO> getPacketsFromProtocol(int index, String protocol)
			throws TrafficDAOException {
		PreparedStatement psttm = null;
		ResultSet rs = null;
		try {

			ArrayList<PacketTO> result = new ArrayList<PacketTO>();

			String sql = "select * from packet where id > ? AND protocol = ?";
			psttm = connection.prepareStatement(sql);
			psttm.setInt(1, index);
			psttm.setString(2, protocol);
			rs = psttm.executeQuery();

			while (rs.next()) {

				int id = rs.getInt("id");
				String sourceMac = rs.getString("sourceMac");
				String destinationMac = rs.getString("destinationMac");
				String sourceIp = rs.getString("sourceIp");
				String destinationIp = rs.getString("destinationIp");
				String protocolreturned = rs.getString("protocol");
				Float packetLength = rs.getFloat("packetLength");
				String sourceName = rs.getString("sourceName");
				String destinationName = rs.getString("destinationName");
				Integer sourcePort = rs.getInt("sourcePort");
				Integer destinationPort = rs.getInt("destinationPort");
				Long ack = rs.getLong("ack");
				Boolean syn = rs.getBoolean("syn");
				Boolean fin = rs.getBoolean("fin");
				Boolean ackFlag = rs.getBoolean("ackFlag");
				Long sequence = rs.getLong("sequence");
				Date captureDate = rs.getDate("captureDate");

				PacketTO packet = new PacketTO();
				packet.setId(id);
				packet.setSourceMac(sourceMac);
				packet.setDestinationMac(destinationMac);
				packet.setSourceIp(sourceIp);
				packet.setDestinationIp(destinationIp);
				packet.setPacketLength(packetLength);
				packet.setProtocol(protocolreturned);
				packet.setDestinationName(destinationName);
				packet.setSourceName(sourceName);
				packet.setSourcePort(sourcePort);
				packet.setDestinationPort(destinationPort);
				packet.setAck(ack);
				packet.setSyn(syn);
				packet.setFin(fin);
				packet.setAckFlag(ackFlag);
				packet.setSequence(sequence);
				packet.setCaptureDate(captureDate);

				result.add(packet);
			}
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			NetzLogger.getInstance().error(
					"Erro ao comunicar com o banco de dados");
			throw new TrafficDAOException(
					"Erro ao comunicar com o banco de dados");
		} finally {
			close(psttm, rs);
		}
	}

	public void deleteAllPackets() {

		Statement sttm = null;

		String sql = "DELETE FROM packet";

		try {
			sttm = connection.createStatement();
			sttm.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Erro ao tentar apagar hosts do banco.");
			e.printStackTrace();
		}
	}

	public void deletePacketsFromDate(Date date) {
		PreparedStatement sttm = null;

		String sql = "DELETE FROM packet where capturedate < ?";

		try {
			sttm = connection.prepareStatement(sql);
			
			if(date!=null){
				java.sql.Timestamp deleteDate = new java.sql.Timestamp(date.getTime()); 
				sttm.setTimestamp(1, deleteDate);
			}
			else{
				sttm.setTimestamp(1, null);
			}
			sttm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Erro ao tentar apagar hosts do banco.");
			e.printStackTrace();
		}
	}

	private void close(Statement sttm, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (sttm != null) {
			try {
				sttm.close();
			} catch (SQLException e) {
			}
		}
	}

	private void close(PreparedStatement sttm, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (sttm != null) {
			try {
				sttm.close();
			} catch (SQLException e) {
			}
		}
	}

	private void close(Statement sttm) {
		if (sttm != null) {
			try {
				sttm.close();
			} catch (SQLException e) {
			}
		}
	}
}
