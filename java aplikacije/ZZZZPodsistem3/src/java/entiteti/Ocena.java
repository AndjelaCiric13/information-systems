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
@Table(name = "ocena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocena.findAll", query = "SELECT o FROM Ocena o"),
    @NamedQuery(name = "Ocena.findByIdOce", query = "SELECT o FROM Ocena o WHERE o.idOce = :idOce"),
    @NamedQuery(name = "Ocena.findByOcena", query = "SELECT o FROM Ocena o WHERE o.ocena = :ocena"),
    @NamedQuery(name = "Ocena.findByDatum", query = "SELECT o FROM Ocena o WHERE o.datum = :datum"),
    @NamedQuery(name = "Ocena.findByVreme", query = "SELECT o FROM Ocena o WHERE o.vreme = :vreme")})
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOce")
    private Integer idOce;
    @Column(name = "ocena")
    private Integer ocena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vreme")
    @Temporal(TemporalType.TIME)
    private Date vreme;
    @JoinColumn(name = "idKor", referencedColumnName = "idKor")
    @ManyToOne(optional = false)
    private Korisnik idKor;
    @JoinColumn(name = "idSni", referencedColumnName = "idSni")
    @ManyToOne(optional = false)
    private Snimak idSni;

    public Ocena() {
    }

    public Ocena(Integer idOce) {
        this.idOce = idOce;
    }

    public Ocena(Integer idOce, Date datum, Date vreme) {
        this.idOce = idOce;
        this.datum = datum;
        this.vreme = vreme;
    }

    public Integer getIdOce() {
        return idOce;
    }

    public void setIdOce(Integer idOce) {
        this.idOce = idOce;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
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
        hash += (idOce != null ? idOce.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.idOce == null && other.idOce != null) || (this.idOce != null && !this.idOce.equals(other.idOce))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd"); Date date = null;
        
        String formattedDate = format.format(this.datum);
        
        format = new SimpleDateFormat("hh:mm:ss"); Date time = null;
        String formattedTime = format.format(this.vreme);
        return "Ocena[ idOce=" + idOce + ", od korisnika=" + this.idKor.getIme() + ", za snimak=" + idSni.getNaziv() + ", ocena=" + this.ocena + ", vreme=" +
                formattedDate + "/" + formattedTime + " ]";
    }
    
}
