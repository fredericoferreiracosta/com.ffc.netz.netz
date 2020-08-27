/*
	NETZ - Network management support system
    Copyright (C) 2011  Alana de Almeida Brandão (alana.brandao@yahoo.com.br)
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

package br.netz.traffic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import jpcap.packet.ARPPacket;
//import jpcap.packet.DatalinkPacket;
//import jpcap.packet.EthernetPacket;
//import jpcap.packet.ICMPPacket;
//import jpcap.packet.IPPacket;
//import jpcap.packet.Packet;
//import jpcap.packet.TCPPacket;
//import jpcap.packet.UDPPacket;
import netz.traffic.logger.NetzLogger;
import br.netz.traffic.listener.PacketListener;
import br.netz.traffic.model.PacketTO;
import br.netz.traffic.model.TrafficDAO;
import br.netz.traffic.model.TrafficDAOException;

public class TrafficStorage implements Runnable {

    private TrafficDAO trafficDAO = null;

    private ArrayList<PacketListener> listeners = new ArrayList<PacketListener>();

    @Override
    public void run() {

        ExecutorService storing = Executors.newFixedThreadPool(10);

        try {
            trafficDAO = new TrafficDAO();
        } catch (TrafficDAOException e1) {
            e1.printStackTrace();
        }
        while(true) {

            try {
                for(final String packet: TrafficController.getInstance().getPackets()) {
                    if (packet!=null) {
                        if (packet instanceof String) {
                            if (packet instanceof String) {
                                storing.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        String packettcp = packet;
                                        storeTCPPacket(packettcp, packet);
                                    }
                                });
                            } else if (packet instanceof String) {
                                storing.execute(new Runnable(){
                                    @Override
                                    public void run() {
                                        String packeticmp = packet;
                                        storeICMPPacket(packeticmp, packet);
                                    }
                                });
                            } else if (packet instanceof String) {
                                storing.execute(new Runnable(){
                                    @Override
                                    public void run() {
                                        String packetudp = packet;
                                        storeUDPPacket(packetudp, packet);
                                    }
                                });
                            }
                        } else if (packet instanceof String) {
                            storing.execute(new Runnable() {
                                @Override
                                public void run() {
                                    storeARPPacket(packet);
                                }
                            });
                        } else {
                            System.out.println("Protocolo do pacote desconhecido");
                            storing.execute(new Runnable() {
                                @Override
                                public void run() {
                                    storePacket(packet);
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
            }
            TrafficController.getInstance().clearPackets();
        }
    }

    public void addPacketListener(PacketListener packetListener){
        listeners.add(packetListener);
    }

    public void storeICMPPacket(String packeticmp, String packet) {

        NetzLogger.getInstance().info("Packet received, storing it now");
        PacketTO packetTO = new PacketTO();

//        packetTO.setSourceIp(packeticmp.src_ip.getHostAddress());
//        packetTO.setSourceName(packeticmp.src_ip.getHostName());
//        packetTO.setDestinationIp(packeticmp.dst_ip.getHostAddress());
//        packetTO.setDestinationName(packeticmp.dst_ip.getHostName());
//        packetTO.setPacketLength((float)packet.len);
//        packetTO.setProtocol("ICMP");
//        packetTO.setAck(0l);
//        packetTO.setSourcePort(0);
//        packetTO.setDestinationPort(0);
//        packetTO.setFin(false);
//        packetTO.setSyn(false);
//        packetTO.setAckFlag(false);
//        packetTO.setSequence(0);
//        packetTO.setCaptureDate(new Date());

//        DatalinkPacket datalink = packet.datalink;
//        EthernetPacket packetEthernet = (EthernetPacket) datalink;

//        packetTO.setSourceMac(packetEthernet.getSourceAddress());
//        packetTO.setDestinationMac(packetEthernet.getDestinationAddress());

        try {
            trafficDAO.savePacket(packetTO);
            for(PacketListener listener : listeners){
                listener.newPacket(packetTO);
            }
        } catch (TrafficDAOException e) {
            NetzLogger.getInstance().error("Could not save the packet");
            e.printStackTrace();
        }
    }

    public void storeUDPPacket(String packetudp, String packet) {

        NetzLogger.getInstance().info("Packet received, storing it now");
        PacketTO packetTO = new PacketTO();

//        packetTO.setSourceIp(packetudp.src_ip.getHostAddress());
//        packetTO.setSourceName(packetudp.src_ip.getHostName());
//        packetTO.setDestinationIp(packetudp.dst_ip.getHostAddress());
//        packetTO.setDestinationName(packetudp.dst_ip.getHostName());
//        packetTO.setPacketLength((float)packet.len);
//        packetTO.setProtocol("UDP");
//        packetTO.setAck(0l);
//        packetTO.setSourcePort(packetudp.src_port);
//        packetTO.setDestinationPort(packetudp.dst_port);
//        packetTO.setFin(false);
//        packetTO.setSyn(false);
//        packetTO.setAckFlag(false);
//        packetTO.setSequence(0);
//        packetTO.setCaptureDate(new Date());
//
//        DatalinkPacket datalink = packet.datalink;
//        EthernetPacket packetEthernet = (EthernetPacket) datalink;
//
//        packetTO.setSourceMac(packetEthernet.getSourceAddress());
//        packetTO.setDestinationMac(packetEthernet.getDestinationAddress());

        try {
            trafficDAO.savePacket(packetTO);
            for(PacketListener listener : listeners){
                listener.newPacket(packetTO);
            }
        } catch (TrafficDAOException e) {
            NetzLogger.getInstance().error("Could not save the packet");
            e.printStackTrace();
        }
    }

    public void storeTCPPacket(String packettcp, String packet) {

        NetzLogger.getInstance().info("Packet received, storing it now");
        PacketTO packetTO = new PacketTO();

//        packetTO.setSourceIp(packettcp.src_ip.getHostAddress());
//        packetTO.setSourceName(packettcp.src_ip.getHostName());
//        packetTO.setDestinationIp(packettcp.dst_ip.getHostAddress());
//        packetTO.setDestinationName(packettcp.dst_ip.getHostName());
//        packetTO.setPacketLength((float)packet.len);
//        packetTO.setProtocol("TCP");
//        packetTO.setAck(packettcp.ack_num);
//        packetTO.setSourcePort(packettcp.src_port);
//        packetTO.setDestinationPort(packettcp.dst_port);
//        packetTO.setFin(packettcp.fin);
//        packetTO.setSyn(packettcp.syn);
//        packetTO.setAckFlag(packettcp.ack);
//        packetTO.setSequence(packettcp.sequence);
//        packetTO.setCaptureDate(new Date());

//        DatalinkPacket datalink = packet.datalink;
//        EthernetPacket packetEthernet = (EthernetPacket) datalink;
//
//        packetTO.setSourceMac(packetEthernet.getSourceAddress());
//        packetTO.setDestinationMac(packetEthernet.getDestinationAddress());

        try {
            trafficDAO.savePacket(packetTO);
            for(PacketListener listener : listeners){
                listener.newPacket(packetTO);
            }
        } catch (TrafficDAOException e) {
            NetzLogger.getInstance().error("Could not save the packet");
            e.printStackTrace();
        }
    }

    public void storeARPPacket(String packet) {
        int i, j;
        String sourceIp;
        String destinationIp;

        //pega sourceIp
//        i = packet.toString().indexOf("/");
//        j = packet.toString().indexOf(")");
//        sourceIp = packet.toString().substring(i+1, j);
//
//        //pega destinationIp
//        i = packet.toString().indexOf(">");
//        destinationIp = packet.toString().substring(i, packet.toString().length()-1);
//        i = packet.toString().indexOf("/");
//        j = packet.toString().indexOf(")");
//        destinationIp = packet.toString().substring(i+1, j);
//
//        DatalinkPacket datalink = packet.datalink;
//        EthernetPacket packetEthernet = (EthernetPacket) datalink;
//
        PacketTO packetTO = new PacketTO();
//
//        packetTO.setSourceIp(sourceIp);
//        packetTO.setSourceName("no name");
//        packetTO.setDestinationIp(destinationIp);
//        packetTO.setDestinationName("no name");
//        packetTO.setPacketLength((float)packet.len);
//        packetTO.setProtocol("ARP/RARP");
//        packetTO.setAck(0l);
//        packetTO.setSourcePort(0);
//        packetTO.setDestinationPort(0);
//        packetTO.setFin(false);
//        packetTO.setSyn(false);
//        packetTO.setAckFlag(false);
//        packetTO.setSequence(0);
//        packetTO.setCaptureDate(new Date());
//
//        packetTO.setSourceMac(packetEthernet.getSourceAddress());
//        packetTO.setDestinationMac(packetEthernet.getDestinationAddress());

        try {
            trafficDAO.savePacket(packetTO);
            for(PacketListener listener : listeners){
                listener.newPacket(packetTO);
            }
        } catch (TrafficDAOException e) {
            NetzLogger.getInstance().error("Could not save the packet");
            e.printStackTrace();
        }
    }

    public void storePacket(String packet) {
        int i, j;
        String sourceIp;
        String destinationIp;

//        i = packet.toString().indexOf("/");
//        j = packet.toString().indexOf(")");
//        sourceIp = packet.toString().substring(i+1, j);
//
//        i = packet.toString().indexOf(">");
//        destinationIp = packet.toString().substring(i, packet.toString().length()-1);
//        i = packet.toString().indexOf("/");
//        j = packet.toString().indexOf(")");
//        destinationIp = packet.toString().substring(i+1, j);
//
//        DatalinkPacket datalink = packet.datalink;
//        EthernetPacket packetEthernet = (EthernetPacket) datalink;

        PacketTO packetTO = new PacketTO();

//        packetTO.setSourceIp(sourceIp);
//        packetTO.setSourceName("no name");
//        packetTO.setDestinationIp(destinationIp);
//        packetTO.setDestinationName("no name");
//        packetTO.setPacketLength((float)packet.len);
//        packetTO.setProtocol("Unknow");
//        packetTO.setAck(0l);
//        packetTO.setSourcePort(0);
//        packetTO.setDestinationPort(0);
//        packetTO.setFin(false);
//        packetTO.setSyn(false);
//        packetTO.setAckFlag(false);
//        packetTO.setSequence(0);
//        packetTO.setCaptureDate(new Date());
//
//        packetTO.setSourceMac(packetEthernet.getSourceAddress());
//        packetTO.setDestinationMac(packetEthernet.getDestinationAddress());

        try {
            trafficDAO.savePacket(packetTO);
            for(PacketListener listener : listeners){
                listener.newPacket(packetTO);
            }
        } catch (TrafficDAOException e) {
            NetzLogger.getInstance().error("Could not save the packet");
            e.printStackTrace();
        }
    }

}