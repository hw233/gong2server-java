package com;

import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.xml.bind.DatatypeConverter;

public class test1 {

	public static void main(String[] args) {
		String st = "enSiI5qjJ76cYZjF0ibpOd1HiDHyl4A3pVtwyczszuMa1jZzsvF3igCRUumUsVgCY8CO3+mlxO2JaI9EfluVfAeC3eoAOsks+3fG40gu0+8Yg20cb3vWn5TLRwIUG3/2XJ/Tjxcf5TCMt0Onf2TP3EI2sAbGXf6irvIH25N6V9XVTy3ozTV5onnwiMFMCPMGwFWIFurYVQqOWzp8C5ajD9oGZgxvscbneeQQFTLjcIyDqbmtUyC6D25EySnkqaUYSmDxuXAdl1xNQQdAj537W+t2X4hD1rsLcnjYAwOSDzKY49++lALk5zoMFFEjKOP9mh3PHz3SB6LNTxBq+GSeBw==";
		String salt = "UPABBQ==";
		String url = "https://static.gc.apple.com/public-key/gc-prod-2.cer";
		verify1("G:1131362816", "cn.gamejelly.gong.appstore", 1462436496389l, st, salt, url);

	}

	public static void verify1(String playerIDStr, String bundleIDStr, long timestampLong, String signature,
			String saltStr, String pkUrl) {

		try {

			byte[] playerID = playerIDStr.getBytes("UTF-8");

			byte[] bundleID = bundleIDStr.getBytes("UTF-8");

			long ts = timestampLong;

			final ByteBuffer tsByteBuffer = ByteBuffer.allocate(8);

			tsByteBuffer.order(ByteOrder.BIG_ENDIAN);

			tsByteBuffer.putLong(ts);

			byte[] timestamp = tsByteBuffer.array();

			byte[] salt = DatatypeConverter.parseBase64Binary(saltStr);

			byte[] sigToCheck = DatatypeConverter.parseBase64Binary(signature);

			ByteBuffer dataBuffer = ByteBuffer.allocate(playerID.length + bundleID.length + 8 + salt.length)

			.put(playerID)

			.put(bundleID)

			.put(timestamp)

			.put(salt);

			Certificate cert = CertificateFactory.getInstance("X.509")

			.generateCertificate(
					new URL(pkUrl).openConnection().getInputStream());

			Signature sig = Signature.getInstance("SHA1withRSA");

			sig.initVerify(cert);
			sig.update(dataBuffer.array());

			final boolean verify = sig.verify(sigToCheck);

			System.out.println("signature verifies: " + verify);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
