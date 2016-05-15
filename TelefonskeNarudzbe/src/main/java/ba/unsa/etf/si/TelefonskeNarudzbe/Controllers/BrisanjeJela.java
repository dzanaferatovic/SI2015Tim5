package ba.unsa.etf.si.TelefonskeNarudzbe.Controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import Util.HibernateUtil;
import ba.unsa.etf.si.TelefonskeNarudzbe.DomainModels.Jelo;

public class BrisanjeJela {
	final static Logger logger = Logger.getLogger(BrisanjeJela.class);

	public static boolean BrisiJelo(String naziv) {
		try{
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			Criteria criteria = session.createCriteria(Jelo.class).add(Restrictions.like("naziv", naziv).ignoreCase());
			List<Jelo> lista = criteria.list();
			Jelo j = lista.get(0);
			session.delete(j);
			t.commit();
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;		
	}
}