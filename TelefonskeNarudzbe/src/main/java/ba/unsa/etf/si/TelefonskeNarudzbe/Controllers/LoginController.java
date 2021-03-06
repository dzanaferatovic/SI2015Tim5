package ba.unsa.etf.si.TelefonskeNarudzbe.Controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import Util.HibernateUtil;
import ba.unsa.etf.si.TelefonskeNarudzbe.DomainModels.Zaposlenik;

public final class LoginController {
	final static Logger logger = Logger.getLogger(LoginController.class);
	
	private static LoginController instance = null;
	
	private static Session sesija;
	public Session getSesija() {
		return sesija;
	}
	private static Transaction transakcija;
	public Transaction getTransakcija() {
		return transakcija;
	}
	private static Zaposlenik zaposlenik;
	public Zaposlenik getZaposlenik() {
		return zaposlenik;
	}
	private LoginController(String username, String password) throws Exception{
		sesija = HibernateUtil.getSessionFactory().openSession();
		transakcija = sesija.beginTransaction();
		zaposlenik = null;
		zaposlenik = (Zaposlenik) sesija.createCriteria(Zaposlenik.class).add(Restrictions.eq("username", username)).uniqueResult();
		if (zaposlenik != null) {
			if(zaposlenik.getRadnomjesto().getId()>4){
				throw new Exception("Zaposlenik je otpušten");
			}
			pocniLogin(password);
		}
		else{
			throw new IllegalArgumentException("Pogrešno korisničko ime");
		}
	}
	
	public static LoginController getInstance(String username, String password) throws Exception {
		zaposlenik = null;
		instance=null;
			instance = new LoginController(username, password);
		
		return instance;
	}
	
	private static void pocniLogin(String password) throws Exception {
		try {
			if (!zaposlenik.getPassword().equals(kriptujPassword(password)))
				throw new IllegalArgumentException("Pogrešna šifra");
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public Boolean daLiPostoji(){
		return (instance != null);
	}
	
	public void ubijSesiju(){
		//trasaction.commit();
		sesija.close();
	
	}
	 public static String kriptujPassword(String password)
	    {
	        String kriptovani = null;
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            byte[] bytes = md.digest(password.getBytes());
	             StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            //Get complete hashed password in hex format
	            kriptovani = sb.toString();
	        }
	        catch (NoSuchAlgorithmException e) {
	            logger.info(e);
	        }
	        return kriptovani;
	    }
	}
	


