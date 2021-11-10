package es.uco.ism.utils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public abstract class PasswordHashing {

	/**
	 * Devuelve la cadena hexadecimal de un array de bytes
	 * 
	 * @param array Array de bytes a convertir
	 * @return Cadena hexadecimal del array
	 */
	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }
        else{
            return hex;
        }
	}
	
	/**
	 * Devuelve el array de bytes de una cadena hexadecimal
	 * 
	 * @param hex Cadena hexadecimal a convertir
	 * @return Array de bytes de la cadena
	 */
	private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        
        for(int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        
        return bytes;
    }
	
	/**
	 * Crea un salt para la contraseña
	 * 
	 * @return Salt para la contraseña
	 */
	public static String createSalt() {
		byte[] salt = new byte[16];
		SecureRandom random;
		
		try {
			random = SecureRandom.getInstance("SHA1PRNG");			
			random.nextBytes(salt);
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return toHex(salt);
	}
	
	/**
	 * Devuelve el hash de una contraseña
	 * 
	 * @param password Contraseña a hashear
	 * @param salt Salt utilizado para hashear la contraseña
	 * @return Hash de la contraseña
	 */
	public static String createHash(String password, String salt) {
		byte[] hash = new byte[0];
		KeySpec spec = new PBEKeySpec(password.toCharArray(), fromHex(salt), 5, 512);
				
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
		} 
		catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			System.out.println(e);
		}
		
		return toHex(hash);
	}
	
}
