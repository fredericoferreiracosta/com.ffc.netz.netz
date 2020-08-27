package br.netz.traffic;

import java.io.EOFException;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.NifSelector;

public class PackageTest
{
    private static final String COUNT_KEY = PackageTest.class.getName() + ".count";
    private static final int COUNT = Integer.getInteger(COUNT_KEY, 5);

    private static final String READ_TIMEOUT_KEY = PackageTest.class.getName() + ".readTimeout";
    private static final int READ_TIMEOUT = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]

    private static final String SNAPLEN_KEY = PackageTest.class.getName() + ".snaplen";
    private static final int SNAPLEN = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]

    public static void
           main(String[] args) throws IOException, PcapNativeException, TimeoutException, NotOpenException {
        PcapNetworkInterface nif = new NifSelector().selectNetworkInterface();

        System.out.println(nif.getName() + "(" + nif.getDescription() + ")");

        PcapHandle handle = nif.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);

        int num = 0;
        while (true)
        {
            Packet packet = handle.getNextPacketEx();
            System.out.println(handle.getTimestampPrecision());
            System.out.println(packet);
            num++;
            if (num >= COUNT) {
                break;
            }
        }

        handle.close();
    }

    private static void printSinglePackage() throws PcapNativeException, UnknownHostException, EOFException, TimeoutException, NotOpenException
    {
        InetAddress addr = InetAddress.getByName("localhost");
        PcapNetworkInterface nif = Pcaps.getDevByAddress(addr);

        int snapLen = 65536;
        PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
        int timeout = 10;
        PcapHandle handle = nif.openLive(snapLen, mode, timeout);

        Packet packet = handle.getNextPacketEx();
        handle.close();

        IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
        Inet4Address srcAddr = ipV4Packet.getHeader().getSrcAddr();
        System.out.println(srcAddr);
    }
}
