package entiteti;

import entiteti.Gledanje;
import entiteti.Ocena;
import entiteti.Pretplata;
import entiteti.Snimak;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-01-26T21:48:14")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, String> ime;
    public static volatile ListAttribute<Korisnik, Snimak> snimakList;
    public static volatile ListAttribute<Korisnik, Gledanje> gledanjeList;
    public static volatile SingularAttribute<Korisnik, Integer> idKor;
    public static volatile SingularAttribute<Korisnik, Integer> idMes;
    public static volatile ListAttribute<Korisnik, Pretplata> pretplataList;
    public static volatile ListAttribute<Korisnik, Ocena> ocenaList;
    public static volatile SingularAttribute<Korisnik, String> godiste;
    public static volatile SingularAttribute<Korisnik, Character> pol;
    public static volatile SingularAttribute<Korisnik, String> email;

}