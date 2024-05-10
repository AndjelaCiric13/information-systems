/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import zzzzpodsistem3.Podsistem3;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "gledanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gledanje.findAll", query = "SELECT g FROM Gledanje g"),
    @NamedQuery(name = "Gledanje.findByIdGle", query = "SELECT g FROM Gledanje g WHERE g.idGle = :idGle"),
    @NamedQuery(name = "Gledanje.findByDatumPocetka", query = "SELECT g FROM Gledanje g WHERE g.datumPocetka = :datumPocetka"),
    @NamedQuery(name = "Gledanje.findByVremePocetka", query = "SELECT g FROM Gledanje g WHERE g.vremePocetka = :vremePocetka"),
    @NamedQuery(name = "Gledanje.findBySekundOdPocetka", query = "SELECT g FROM Gledanje g WHERE g.sekundOdPocetka = :sekundOdPocetka"),
    @NamedQuery(name = "Gledanje.findBySekundiOdgledano", query = "SELECT g FROM Gledanje g WHERE g.sekundiOdgledano = :sekundiOdgledano")})
public class Gledanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGle")
    private Integer idGle;
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
    @Column(name = "sekundOdPocetka")
    private int sekundOdPocetka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sekundiOdgledano")
    private int sekundiOdgledano;
    @JoinColumn(name = "idKor", referencedColumnName = "idKor")
    @ManyToOne(optional = false)
    private Korisnik idKor;
    @JoinColumn(name = "idSni", referencedColumnName = "idSni")
    @ManyToOne(optional = false)
    private Snimak idSni;

    public Gledanje() {
    }

    public Gledanje(Integer idGle) {
        this.idGle = idGle;
    }

    public Gledanje(Integer idGle, Date datumPocetka, Date vremePocetka, int sekundOdPocetka, int sekundiOdgledano) {
        this.idGle = idGle;
        this.datumPocetka = datumPocetka;
        this.vremePocetka = vremePocetka;
        this.sekundOdPocetka = sekundOdPocetka;
        this.sekundiOdgledano = sekundiOdgledano;
    }

    public Integer getIdGle() {
        return idGle;
    }

    public void setIdGle(Integer idGle) {
        this.idGle = idGle;
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

    public int getSekundOdPocetka() {
        return sekundOdPocetka;
    }

    public void setSekundOdPocetka(int sekundOdPocetka) {
        this.sekundOdPocetka = sekundOdPocetka;
    }

    public int getSekundiOdgledano() {
        return sekundiOdgledano;
    }

    public void setSekundiOdgledano(int sekundiOdgledano) {
        this.sekundiOdgledano = sekundiOdgledano;
    }

    public Korisnik getIdKor() {
        return idKor;
    }

    public void setIdKor(Korisnik idKor) {
        this.idKor = idKor;
    }

    public Snimak getIdSni() {
        return idSni;
    }

    public void setIdSni(Snimak idSni) {
        this.idSni = idSni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGle != null ? idGle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gledanje)) {
            return false;
        }
        Gledanje other = (Gledanje) object;
        if ((this.idGle == null && other.idGle != null) || (this.idGle != null && !this.idGle.equals(other.idGle))) {
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
        
        return "Gledanje[ idGle=" + idGle + ", korisnik=" + idKor.getIme() + ", snimak=" + idSni.getNaziv() + 
                ", vreme pocetka gledanja=" + formattedDate + "/" + formattedTime + " ]";
    }
    
}
