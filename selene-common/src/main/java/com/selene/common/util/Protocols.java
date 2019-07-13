package com.selene.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * IP utilities.
 *
 * @author shaobo shih
 * @version 1.0
 */
public final class Protocols {

	/**
	 * Get the local server IP address
	 * 
	 * @return {@code String} {@code Inet4Address} address
	 */
	public static String ip() {
		try {
			Enumeration<?> en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface i = (NetworkInterface) en.nextElement();
				for (Enumeration<?> en2 = i.getInetAddresses(); en2.hasMoreElements();) {
					InetAddress addr = (InetAddress) en2.nextElement();
					if (!addr.isLoopbackAddress()) {
						if (addr instanceof Inet4Address) {
							return addr.getHostAddress();
						}
					}
				}
			}
		} catch (SocketException e) {
			return null;
		}
		return null;
	}
}