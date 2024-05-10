/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "paket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paket.findAll", query = "SELECT p FROM Paket p"),
    @NamedQuery(name = "Paket.findByIdPak", query = "SELECT p FROM Paket p WHERE p.idPak = :idPak"),
    @NamedQuery(name = "Paket.findByCenaNaMesNivou", query = "SELECT p FROM Paket p WHERE p.cenaNaMesNivou = :cenaNaMesNivou"),
    @NamedQuery(name = "Paket.findByNaziv", query = "SELECT p FROM Paket p WHERE p.naziv = :naziv")})
public class Paket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPak")
    private Integer idPak;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cenaNaMesNivou")
    private int cenaNaMesNivou;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPak")
    private List<Pretplata> pretplataList;

    public Paket() {
    }

    public Paket(Integer idPak) {
        this.idPak = idPak;
    }

    public Paket(Integer idPak, int cenaNaMesNivou, String naziv) {
        this.idPak = idPak;
        this.cenaNaMesNivou = cenaNaMesNivou;
        this.naziv = naziv;
    }

    public Integer getIdPak() {
        return idPak;
    }

    public void setIdPak(Integer idPak) {
        this.idPak = idPak;
    }

    public int getCenaNaMesNivou() {
        return cenaNaMesNivou;
    }

    public void setCenaNaMesNivou(int cenaNaMesNivou) {
        this.cenaNaMesNivou = cenaNaMesNivou;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public List<Pretplata> getPretplataList() {
        return pretplataList;
    }

    public void setPretplataList(List<Pretplata> pretplataList) {
        this.pretplataList = pretplataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPak != null ? idPak.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paket)) {
            return false;
        }
        Paket other = (Paket) object;
        if ((this.idPak == null && other.idPak != null) || (this.idPak != null && !this.idPak.equals(other.idPak))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Paket[ idPak=" + idPak + ", naziv=" + naziv + ", mesecni Iznos=" + this.cenaNaMesNivou +  " ]";
    }
    
}
