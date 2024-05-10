package entiteti;

import entiteti.Kategorija;
import entiteti.Korisnik;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-01-27T02:03:42")
@StaticMetamodel(Snimak.class)
public class Snimak_ { 

    public static volatile ListAttribute<Snimak, Kategorija> kategorijaList;
    public static volatile SingularAttribute<Snimak, Integer> idSni;
    public static volatile SingularAttribute<Snimak, Korisnik> idKor;
    public static volatile SingularAttribute<Snimak, Date> datumPostavljanja;
    public static volatile SingularAttribute<Snimak, Integer> trajanje;
    public static volatile SingularAttribute<Snimak, Date> vremePostavljanja;
    public static volatile SingularAttribute<Snimak, String> naziv;

}