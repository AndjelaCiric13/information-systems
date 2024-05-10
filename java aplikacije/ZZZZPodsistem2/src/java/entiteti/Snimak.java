/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "snimak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Snimak.findAll", query = "SELECT s FROM Snimak s"),
    @NamedQuery(name = "Snimak.findByIdSni", query = "SELECT s FROM Snimak s WHERE s.idSni = :idSni"),
    @NamedQuery(name = "Snimak.findByNaziv", query = "SELECT s FROM Snimak s WHERE s.naziv = :naziv"),
    @NamedQuery(name = "Snimak.findByTrajanje", query = "SELECT s FROM Snimak s WHERE s.trajanje = :trajanje"),
    @NamedQuery(name = "Snimak.findByDatumPostavljanja", query = "SELECT s FROM Snimak s WHERE s.datumPostavljanja = :datumPostavljanja"),
    @NamedQuery(name = "Snimak.findByVremePostavljanja", query = "SELECT s FROM Snimak s WHERE s.vremePostavljanja = :vremePostavljanja")})
public class Snimak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSni")
    private Integer idSni;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumPostavljanja")
    @Temporal(TemporalType.DATE)
    private Date datumPostavljanja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vremePostavljanja")
    @Temporal(TemporalType.TIME)
    private Date vremePostavljanja;
    @ManyToMany(mappedBy = "snimakList")
    private List<Kategorija> kategorijaList;
    @JoinColumn(name = "idKor", referencedColumnName = "idKor")
    @ManyToOne(optional = false)
    private Korisnik idKor;

    public Snimak() {
    }

    public Snimak(Integer idSni) {
        this.idSni = idSni;
    }

    public Snimak(Integer idSni, String naziv, int trajanje, Date datumPostavljanja, Date vremePostavljanja) {
        this.idSni = idSni;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datumPostavljanja = datumPostavljanja;
        this.vremePostavljanja = vremePostavljanja;
    }

    public Integer getIdSni() {
        return idSni;
    }

    public void setIdSni(Integer idSni) {
        this.idSni = idSni;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumPostavljanja() {
        return datumPostavljanja;
    }

    public void setDatumPostavljanja(Date datumPostavljanja) {
        this.datumPostavljanja = datumPostavljanja;
    }

    public Date getVremePostavljanja() {
        return vremePostavljanja;
    }

    public void setVremePostavljanja(Date vremePostavljanja) {
        this.vremePostavljanja = vremePostavljanja;
    }

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }

    public Korisnik getIdKor() {
        return idKor;
    }

    public void setIdKor(Korisnik idKor) {
        this.idKor = idKor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSni != null ? idSni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Snimak)) {
            return false;
        }
        Snimak other = (Snimak) object;
        if ((this.idSni == null && other.idSni != null) || (this.idSni != null && !this.idSni.equals(other.idSni))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd"); Date date = null;
        
        String formattedDate = format.format(this.datumPostavljanja);
        
        format = new SimpleDateFormat("hh:mm:ss"); Date time = null;
        String formattedTime = format.format(this.vremePostavljanja);
        return "Snimak[ idSni=" + idSni + ", vlasnik=" + idKor.getIme() + ", naziv" + naziv + ", trajanje=" + trajanje +
                ", datumPostavljanja=" + formattedDate + ", vremePostavljanja" + formattedTime + " ]";
    }
    
}
