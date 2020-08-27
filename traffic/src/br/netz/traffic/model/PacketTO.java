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

import java.util.Date;

public class PacketTO {

	private int id;
	private String sourceMac;
	private String destinationMac;
	private String sourceIp;
	private String sourceName;
	private String destinationIp;
	private String destinationName;
	private String protocol;
	private Float packetLength;
	private Integer sourcePort;
	private Integer destinationPort;
	private Long ack;
	private Boolean syn;
	private Boolean fin;
	private Boolean ackFlag;
	private long sequence;
	private Date captureDate;
	
	public Integer getSourcePort() {
		return sourcePort;
	}
	public void setSourcePort(Integer sourcePort) {
		this.sourcePort = sourcePort;
	}
	public Integer getDestinationPort() {
		return destinationPort;
	}
	public void setDestinationPort(Integer destinationPort) {
		this.destinationPort = destinationPort;
	}
	public Long getAck() {
		return ack;
	}
	public void setAck(Long ack) {
		this.ack = ack;
	}
	public Boolean getSyn() {
		return syn;
	}
	public void setSyn(Boolean syn) {
		this.syn = syn;
	}
	public Boolean getFin() {
		return fin;
	}
	public void setFin(Boolean fin) {
		this.fin = fin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSourceMac() {
		return sourceMac;
	}
	public void setSourceMac(String sourceMac) {
		this.sourceMac = sourceMac;
	}
	public String getDestinationMac() {
		return destinationMac;
	}
	public void setDestinationMac(String destinationMac) {
		this.destinationMac = destinationMac;
	}
	public String getSourceIp() {
		return sourceIp;
	}
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}
	public String getDestinationIp() {
		return destinationIp;
	}
	public void setDestinationIp(String destinationIp) {
		this.destinationIp = destinationIp;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public Float getPacketLength() {
		return packetLength;
	}
	public void setPacketLength(Float packetLength) {
		this.packetLength = packetLength;
	}	
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public Boolean getAckFlag() {
		return ackFlag;
	}
	public void setAckFlag(Boolean ackFlag) {
		this.ackFlag = ackFlag;
	}
	public Long getSequence() {
		return sequence;
	}
	public void setSequence(long l) {
		this.sequence = l;
	}
	public Date getCaptureDate() {
		return captureDate;
	}
	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
	}
	
	@Override
	public String toString() {
		return "Id:" + id + ", SourceMac: " + sourceMac + ", DestinationMac: " + destinationMac +
		", SourceIp: " + sourceIp + ", DestinationIp: " + destinationIp + ", Protocol: " + protocol +
		", PacketLength: " + packetLength;
	}
}
