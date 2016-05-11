package ba.unsa.etf.si.TelefonskeNarudzbe.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import Util.HibernateUtil;
import ba.unsa.etf.si.TelefonskeNarudzbe.DomainModels.*;

public class KuharController {
	
	private int brojNarudzbi;
	public Transaction t;
	

	
	
	
	public boolean promijeniStatusUPreuzeta(int id , long idKuhara){
		Session session = HibernateUtil.getSessionFactory().openSession();
			Narudzba narudzba=dajNarudzbuIzBaze(id);
		Transaction t = session.beginTransaction();
		Long status=dajStatusIzBaze(2);
		Narudzba p = (Narudzba) session.get(Narudzba.class,narudzba.getId());
		if(p == null) {
			t.rollback();
			return false;
		}
		p.setKuhar(idKuhara);
		p.setStatusNarudzbe(status);
	    
		session.update(p);
		t.commit();
		return true;
	}


	public boolean promijeniStatusUSpremna(int id, int idKuhara){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Narudzba narudzba=dajNarudzbuIzBaze(id);
	Transaction t = session.beginTransaction();
	//Status status=dajStatusIzBaze(3);
	Narudzba p = (Narudzba) session.get(Narudzba.class,narudzba.getId());
	if(p == null) {
		t.rollback();
		return false;
	}
	
	p.setStatusNarudzbe((long) 3);
    
	session.update(p);
	t.commit();
	return true;
	
	}
	public Kupac dajKupca(int id){
		return new Kupac();
	}
	
	public boolean provjeriDaLiJePreuzeta(int id){
		Narudzba narudzba=dajNarudzbuIzBaze(id);
		
		if(narudzba.getStatusNarudzbe()==2) return true; else return false;
	}
	public Narudzba dajNarudzbuIzBaze (int idd){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		String id = "%" + idd + "%";
		
		Transaction t = session.beginTransaction();
		
		String hql = "Select Narudzba FROM Narudzba n WHERE str(n.id) like :id";
		Query query = session.createQuery(hql);
		query.setString("id", id);
	
		t.commit();
		
		Narudzba listaNarudzbi =  (Narudzba) query.uniqueResult();
		return listaNarudzbi;
	
	}
	public Long dajStatusIzBaze(int idd){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		String id = "%" + idd + "%";
		
		Transaction t = session.beginTransaction();
		
		String hql = "Select Status FROM Status n WHERE str(n.id) like :id";
		Query query = session.createQuery(hql);
		query.setString("id", id);
	
		t.commit();
		
		Status status =  (Status) query.uniqueResult();
		return (long) 3;
	
	}
	public List<Narudzba> dajSveNarudzbeIzBaze (){
		Session session = HibernateUtil.getSessionFactory().openSession();
		
	  
	                	
	        String hql = "Select new ba.unsa.etf.si.TelefonskeNarudzbe.DomainModels.Narudzba(n.id,n.statusNarudzbe,n.cijena,n.narucioc,"+
	        "n.primaoc,n.vrijemePrijema,n.kuhar,n.vrijemePocetkaPripreme,n.dostavljac,n.vrijemePreuzimanja,"+
	        "n.vrijemeDostave) from Narudzba n";
			Query q = session.createQuery(hql);
		//	q.setString("naziv",naziv);
			List<Narudzba> listaNarudzbi =  (List<Narudzba>) q.list();
		session.close();
			return listaNarudzbi;		
		}
		
	
	
	
	
	


}