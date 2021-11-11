package tests;

import es.uco.ism.utils.PasswordHashing;

public class PasswordHashingTest {

	public static void main(String[] args) {

		System.out.println("Test PasswordHashing");
		
		String password = "myPassword";
		String salt = PasswordHashing.createSalt();
		String passwordHash = PasswordHashing.createHash(password, salt);
				
		System.out.println("Comprobando distintas contraseñas");
		assert !PasswordHashing.createHash("password", salt).equals(passwordHash) : "Error comprobando contraseñas distintas";
		
		System.out.println("Comprobando disinto salt");
		assert !PasswordHashing.createHash(password, PasswordHashing.createSalt()).equals(passwordHash) : "Error comprobando constraseñas con distinto salt";
		
		System.out.println("Comprobando contraseña correcta");
		assert PasswordHashing.createHash(password, salt).equals(passwordHash) : "Error comprobando contraseña correcta";
		
		System.out.println("Fin del test");

	}

}
