package entiteti;

import entiteti.Korisnik;
import entiteti.Snimak;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-01-26T23:27:22")
@StaticMetamodel(Ocena.class)
public class Ocena_ { 

    public static volatile SingularAttribute<Ocena, Integer> idOce;
    public static volatile SingularAttribute<Ocena, Date> datum;
    public static volatile SingularAttribute<Ocena, Date> vreme;
    public static volatile SingularAttribute<Ocena, Korisnik> idKor;
    public static volatile SingularAttribute<Ocena, Snimak> idSni;
    public static volatile SingularAttribute<Ocena, Integer> ocena;

}