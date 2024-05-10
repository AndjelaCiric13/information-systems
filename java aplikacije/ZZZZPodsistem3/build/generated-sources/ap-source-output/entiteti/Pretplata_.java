package entiteti;

import entiteti.Korisnik;
import entiteti.Paket;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-01-26T23:27:22")
@StaticMetamodel(Pretplata.class)
public class Pretplata_ { 

    public static volatile SingularAttribute<Pretplata, Date> datumPocetka;
    public static volatile SingularAttribute<Pretplata, Date> vremePocetka;
    public static volatile SingularAttribute<Pretplata, Korisnik> idKor;
    public static volatile SingularAttribute<Pretplata, Paket> idPak;
    public static volatile SingularAttribute<Pretplata, Integer> cena;
    public static volatile SingularAttribute<Pretplata, Integer> idPre;

}