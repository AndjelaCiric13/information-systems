package entiteti;

import entiteti.Korisnik;
import entiteti.Snimak;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-01-26T23:27:22")
@StaticMetamodel(Gledanje.class)
public class Gledanje_ { 

    public static volatile SingularAttribute<Gledanje, Integer> idGle;
    public static volatile SingularAttribute<Gledanje, Date> datumPocetka;
    public static volatile SingularAttribute<Gledanje, Date> vremePocetka;
    public static volatile SingularAttribute<Gledanje, Integer> sekundiOdgledano;
    public static volatile SingularAttribute<Gledanje, Korisnik> idKor;
    public static volatile SingularAttribute<Gledanje, Snimak> idSni;
    public static volatile SingularAttribute<Gledanje, Integer> sekundOdPocetka;

}