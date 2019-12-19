package top.itcat.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPUtil {
    public static int getOpenPort(String[] args, int defaultProt) {
        if (args == null || args.length == 0) {
            return defaultProt;
        }

        return Integer.valueOf(args[0]);
    }

    public static String getLocalIP() throws SocketException {
        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip;

        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();

            if (!"en0".equals(netInterface.getName())) {
                continue;
            }

            Enumeration addresses = netInterface.getInetAddresses();

            while (addresses.hasMoreElements()) {
                ip = (InetAddress) addresses.nextElement();

                if (ip instanceof Inet4Address) {
                    return ip.getHostAddress();
                }
            }
        }

        if (!System.getProperties().getProperty("os.name").startsWith("Mac")) {
            return "10.224.7.15";
        }

        return "localhost";
    }
}
