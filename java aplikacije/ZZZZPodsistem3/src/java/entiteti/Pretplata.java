/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "pretplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pretplata.findAll", query = "SELECT p FROM Pretplata p"),
    @NamedQuery(name = "Pretplata.findByIdPre", query = "SELECT p FROM Pretplata p WHERE p.idPre = :idPre"),
    @NamedQuery(name = "Pretplata.findByDatumPocetka", query = "SELECT p FROM Pretplata p WHERE p.datumPocetka = :datumPocetka"),
    @NamedQuery(name = "Pretplata.findByVremePocetka", query = "SELECT p FROM Pretplata p WHERE p.vremePocetka = :vremePocetka"),
    @NamedQuery(name = "Pretplata.findByCena", query = "SELECT p FROM Pretplata p WHERE p.cena = :cena")})
public class Pretplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPre")
    private Integer idPre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumPocetka")
    @Temporal(TemporalType.DATE)
    private Date datumPocetka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vremePocetka")
    @Temporal(TemporalType.TIME)
    private Date vremePocetka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private int cena;
    @JoinColumn(name = "idKor", referencedColumnName = "idKor")
    @ManyToOne(optional = false)
    private Korisnik idKor;
    @JoinColumn(name = "idPak", referencedColumnName = "idPak")
    @ManyToOne(optional = false)
    private Paket idPak;

    public Pretplata() {
    }

    public Pretplata(Integer idPre) {
        this.idPre = idPre;
    }

    public Pretplata(Integer idPre, Date datumPocetka, Date vremePocetka, int cena) {
        this.idPre = idPre;
        this.datumPocetka = datumPocetka;
        this.vremePocetka = vremePocetka;
        this.cena = cena;
    }

    public Integer getIdPre() {
        return idPre;
    }

    public void setIdPre(Integer idPre) {
        this.idPre = idPre;
    }

    public Date getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(Date datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public Date getVremePocetka() {
        return vremePocetka;
    }

    public void setVremePocetka(Date vremePocetka) {
        this.vremePocetka = vremePocetka;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Korisnik getIdKor() {
        return idKor;
    }

    public void setIdKor(Korisnik idKor) {
        this.idKor = idKor;
    }

    public Paket getIdPak() {
        return idPak;
    }

    public void setIdPak(Paket idPak) {
        this.idPak = idPak;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPre != null ? idPre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.idPre == null && other.idPre != null) || (this.idPre != null && !this.idPre.equals(other.idPre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd"); Date date = null;
        
        String formattedDate = format.format(this.datumPocetka);
        
        format = new SimpleDateFormat("hh:mm:ss"); Date time = null;
        String formattedTime = format.format(this.vremePocetka);
        
        return "Pretplata[ idPre=" + idPre + ", korisnik=" + this.idKor.getIme() + ", paket=" + idPak.getNaziv() + ", cena=" +this.cena +
                ", vreme pocetka=" + formattedDate + "/" + formattedTime + " ]";
    }
    
}
