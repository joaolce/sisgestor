package test.criptografia;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class Criptografia {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {   
			String senha = "admin";
			MessageDigest digest = MessageDigest.getInstance("MD5");    
			digest.update(senha.getBytes());    
			BASE64Encoder encoder = new BASE64Encoder ();    
			System.out.println(encoder.encode (digest.digest ()));
		} catch (NoSuchAlgorithmException ns) {   
			ns.printStackTrace (); 
		}    
	}

}
