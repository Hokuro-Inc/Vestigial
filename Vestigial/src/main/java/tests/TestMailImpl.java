package tests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import es.uco.ism.utils.*;


public class TestMailImpl {
	
	public static void main (String[] args ) throws UnsupportedEncodingException, MessagingException, IOException{
		
		Mail mail = new Mail("pedropgarciap@gmail.com", "hola", "pito");
		
		System.out.println("PRUEBA CLASE MAIL");

		String MAIL = "hokuroincorporated@gmail.com";
		String PWD = "adaylachupa";
		mail.sendEmail(MAIL,PWD);
	}
}
