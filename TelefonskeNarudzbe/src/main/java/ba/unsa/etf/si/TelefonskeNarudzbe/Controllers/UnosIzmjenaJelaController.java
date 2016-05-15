package ba.unsa.etf.si.TelefonskeNarudzbe.Controllers;

import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Set;

import Util.HibernateUtil;
import ba.unsa.etf.si.TelefonskeNarudzbe.DomainModels.Jelo;
import ba.unsa.etf.si.TelefonskeNarudzbe.DomainModels.Sastojak;
import ba.unsa.etf.si.TelefonskeNarudzbe.DomainModels.SastojciJeloVeza;
import ba.unsa.etf.si.TelefonskeNarudzbe.DomainModels.Zaposlenik;
import ba.unsa.etf.si.TelefonskeNarudzbe.UserInterface.sef;

public class UnosIzmjenaJelaController {
	final static Logger logger = Logger.getLogger(UnosIzmjenaJelaController.class); 
	public List<Jelo> vratiSvaJela(){
		Session sesija = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = sesija.createCriteria(Jelo.class);
		List<Jelo> r = criteria.list();
		sesija.close();
		return r;
	}
	public static Jelo vratiJelo(String naziv){
		Session sesija = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = sesija.createCriteria(Jelo.class).add(Restrictions.like("naziv", naziv).ignoreCase());
		List<Jelo> r = criteria.list();
		Jelo j=r.get(0);
		sesija.close();
		return j;
	}
public static boolean izmjenaJela(String naziv, String opis, Double cijena, List<Sastojak> listaSastojaka,
			List<Double> listaKolicina) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		Session session2 = HibernateUtil.getSessionFactory().openSession();
		Transaction t2 = session2.beginTransaction();
		int ids =-1;
		if(!sef.dodajNovi) ids=sef.vratiIzabranoJelo();
		try {
			if (session.createCriteria(Jelo.class).add(Restrictions.eq("id", ids))
					.setProjection(Projections.property("id")).uniqueResult() == null) {
				System.out.println("Novo jelo");
				Jelo j = new Jelo();
				j.setNaziv(naziv);
				j.setOpis(opis);
				j.setCijena(cijena);
				session.beginTransaction();
				session.save(j);
				session.getTransaction().commit();
				session2.createCriteria(SastojciJeloVeza.class);
				session2.beginTransaction(); //jel ovceko?
				for (int i = 0; i < listaSastojaka.size(); i++) {
					session2.beginTransaction();
					if (listaKolicina.get(i).equals(0))
						continue;
					SastojciJeloVeza sjv = new SastojciJeloVeza();
					sjv.setJelo(j);
					sjv.setSastojak(listaSastojaka.get(i));
					sjv.setKolicina(listaKolicina.get(i));
					session2.save(sjv);
					session2.getTransaction().commit();
			
				}
			}
			else {
				System.out.println("Postoji jelo");
				Criteria criteria = session.createCriteria(Jelo.class)
						.add(Restrictions.like("id", ids));
				List<Jelo> lista = criteria.list();
				Jelo j = lista.get(0);
				j.setNaziv(naziv);
				j.setOpis(opis);
				j.setCijena(cijena);
				session.update(j);
				t.commit();
				session.close();
				session2.getTransaction().begin();
				int i=0;
			for (Sastojak s: listaSastojaka)
			{
				session2.getTransaction().begin();
					Criteria criteria2 = session2.createCriteria(SastojciJeloVeza.class);
					List<SastojciJeloVeza> l = criteria2.list();
					boolean postoji=false;
					for (SastojciJeloVeza sjv: l)
					{
						if(sjv.getJelo().getId()==j.getId() && sjv.getSastojak().getId()==s.getId())
						{
							sjv.setJelo(j);
							sjv.setSastojak(s);
							sjv.setKolicina(listaKolicina.get(i));
							if(listaKolicina.get(i)==0) session2.delete(sjv);
							else session2.update(sjv);
							postoji=true;//cekaj
							break;//kako mislis nemoj push mijenjala si samo svoje kontroleremerisa ih je mijenjala zbg logrea samo edituj tamo direktno dodaj ove izmjene
						}
					}
					if(listaKolicina.get(i)==0) continue;
					if(!postoji){
							
							session2.createCriteria(SastojciJeloVeza.class);
							SastojciJeloVeza sjv = new SastojciJeloVeza();
							sjv.setJelo(j);
							sjv.setSastojak(s);
							sjv.setKolicina(listaKolicina.get(i));
							session2.save(sjv);	
							
					}
					session2.getTransaction().commit();
					i++;
					
				}
				sef.refreshTabeleJelo();
				session2.close();
				System.out.println("Jelo je izmijenjeno");
				return true;
			}
		} catch (Exception e) {
			logger.info(e); 
			return false;
		}
		return true;

	}
}
