package com.hadoit.game.engine.guardian.gas;

import org.apache.commons.codec.binary.StringUtils;

import com.hadoit.game.common.lang.GZIP;
import com.hadoit.game.common.lang.codec.Arc4;
import com.hadoit.game.engine.core.rpc.base.codec.factory.MessageEncryptFactory;

/**
 * 加密压缩
 * 
 * @author bezy 2014-2-26
 * 
 */
public class GasClientMessageEncryptFactory implements MessageEncryptFactory {

	private byte[] saltKey;

	public void setSalt(String salt) {
		saltKey = StringUtils.getBytesUtf8(salt);
	}

	@Override
	public byte[] encrypt(byte[] src) {
		byte[] dst = src;
		if (dst == null) {
			return dst;
		}
		dst = GZIP.deflaterCompress(dst);
		if (saltKey != null) {
			dst = new Arc4(saltKey).crypt(dst);
		}
		return dst;
	}

	@Override
	public byte[] decrypt(byte[] src) {
		byte[] dst = src;
		if (dst == null) {
			return dst;
		}
		if (saltKey != null) {
			dst = new Arc4(saltKey).crypt(dst);
		}
		dst = GZIP.inflaterUncompress(dst);
		return dst;
	}

}
