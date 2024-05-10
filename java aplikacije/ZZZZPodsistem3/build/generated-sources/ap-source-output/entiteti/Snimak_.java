package entiteti;

import entiteti.Gledanje;
import entiteti.Korisnik;
import entiteti.Ocena;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-01-26T23:27:22")
@StaticMetamodel(Snimak.class)
public class Snimak_ { 

    public static volatile ListAttribute<Snimak, Gledanje> gledanjeList;
    public static volatile SingularAttribute<Snimak, Integer> idSni;
    public static volatile SingularAttribute<Snimak, Korisnik> idKor;
    public static volatile SingularAttribute<Snimak, Date> datumPostavljanja;
    public static volatile SingularAttribute<Snimak, Integer> trajanje;
    public static volatile SingularAttribute<Snimak, Date> vremePostavljanja;
    public static volatile ListAttribute<Snimak, Ocena> ocenaList;
    public static volatile SingularAttribute<Snimak, String> naziv;

}