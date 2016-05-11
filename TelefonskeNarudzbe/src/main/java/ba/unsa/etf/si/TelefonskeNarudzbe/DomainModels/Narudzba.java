package ba.unsa.etf.si.TelefonskeNarudzbe.DomainModels;

import java.util.Date;
import java.util.List;

public class Narudzba implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	//private List<Jelo> listaJela;
	private Long statusNarudzbe;
	private double cijena;
	private Long narucioc;
	private Long primaoc;
	private Date vrijemePrijema;
	private Long kuhar;
	private Date vrijemePocetkaPripreme;
	private Long dostavljac;
	private Date vrijemePreuzimanja;
	private Date vrijemeDostave;


	public Narudzba(Long id, Long statusNarudzbe, double cijena, Long narucioc, Long primaoc, Date vrijemePrijema,
			Long kuhar, Date vrijemePocetkaPripreme, Long dostavljac, Date vrijemePreuzimanja, Date vrijemeDostave) {
		super();
		this.id= id;
		//private List<Jelo> listaJela;
		this.statusNarudzbe= statusNarudzbe;
		this.cijena= cijena;
		this.narucioc= narucioc;
		this.primaoc=primaoc;
		this.vrijemePrijema=vrijemePrijema;
		this.kuhar=kuhar;
		this.vrijemePocetkaPripreme= vrijemePocetkaPripreme;
		this.dostavljac= dostavljac;
		this.vrijemePreuzimanja= vrijemePreuzimanja;
		this.vrijemeDostave= vrijemeDostave;

	}
	public Long getStatusNarudzbe() {
		return statusNarudzbe;
	}

	public void setStatusNarudzbe(Long statusNarudzbe) {
		this.statusNarudzbe = statusNarudzbe;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public Long getNarucioc() {
		return narucioc;
	}

	public void setNarucioc(Long narucioc) {
		this.narucioc = narucioc;
	}

	public Long getPrimaoc() {
		return primaoc;
	}

	public void setPrimaoc(Long primaoc) {
		this.primaoc = primaoc;
	}

	public Date getVrijemePrijema() {
		return vrijemePrijema;
	}

	public void setVrijemePrijema(Date vrijemePrijema) {
		this.vrijemePrijema = vrijemePrijema;
	}

	public Long getKuhar() {
		return kuhar;
	}

	public void setKuhar(long idKuhara) {
		this.kuhar = idKuhara;
	}

	public Date getVrijemePocetkaPripreme() {
		return vrijemePocetkaPripreme;
	}

	public void setVrijemePocetkaPripreme(Date vrijemePocetkaPripreme) {
		this.vrijemePocetkaPripreme = vrijemePocetkaPripreme;
	}
	
	public Long getDostavljac() {
		return dostavljac;
	}

	public void setDostavljac(Long dostavljac) {
		this.dostavljac = dostavljac;
	}

	public Date getVrijemePreuzimanja() {
		return vrijemePreuzimanja;
	}

	public void setVrijemePreuzimanja(Date vrijemePreuzimanja) {
		this.vrijemePreuzimanja = vrijemePreuzimanja;
	}

	public Date getVrijemeDostave() {
		return vrijemeDostave;
	}

	public void setVrijemeDostave(Date vrijemeDostave) {
		this.vrijemeDostave = vrijemeDostave;
	}
	public Narudzba()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
