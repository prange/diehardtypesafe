package dhts;

import fj.data.List;
import fj.test.Rand;

public class Tasks1 {

    public static void main(String[] args) {

        Book book = Books.bookGenerator.gen( 1, Rand.standard );

        /*1)
        Null er ikke typesikkert, hvordan kan vi øke sannsynligheten for at feltene i Book ikke er null? (Husk, det beste er å la kompilatoren gjøre sjekken for oss så langt det er mulig)
        Gjør endringer på Book for å reflektere dette.
        */

        /*
        2)
        Noen bøker har ikke ISBN nummer, hvordan kan man reflektere dette uten å bruke null? (Husk, det er lurt å bruke typer for semantikk istedenfor kommentarer, det gjør det enklere å forstå koden)
        Endre Book for å reflektere dette.
        */

        /*
        3)
        Når man jobber med databaser er det vanlig å ha et id felt i objektene som lagres. Det er også vanlig å gjøre en Null-sjekk i dao laget for å sjekke om et objekt er nytt eller "gammelt"
        Når man lagrer et tidligere ulagret objekt får man ofte tilbake de samme objektet med id, eller bare en id tilbake.

        Nå skal vi fjerne denne tvedydigheten om et objekt er lagret eller ulagret:
        Lag en sort som represtenter et lagret objekt, og endre metodesignaturene til dao motodene i Books til å reflektere dette.
        Implementer en enkel lagring vha books HashMap<Object,Book> feltet i Books
        */

        /*

        Bakgrunn:
        Immutability


        4.a)
        Hvis du ikke allerede har gjort det: Gjør Book immutable for å forenkle testing.

        4.b)
        Lag en kodesnutt som lagrer bøker og henter dem fram igjen. (Hint, bruk bookListGenerator for å lage en liste med bøker)
        Hvordan lager man id-er for å teste henting av bøker fra lager? Hva skjer når en bok ikke finnes i lageret? Reflekterer metodesignaturene dette? (Hint, exception er ikke typesikkert)


         */


        /*
        Bakgrunn:
        map(), bind()

        5)
        Dao metodene eksponerer delt endrebar tilstand, dvs at de ikke er trådsikre, og Books endrer tilstand mellom hvert kall. Dette ønsker vi å unngå.
        Lag en eller flere sorter som represtenterer db-operasjoner og data uten at daoen faktisk endrer tilstand, med mindre man kjører en execute/commit/performIO eller lignende.
        Endre dao-funksjoner for å reflektere dette.
         */


        /*
        6)
        Man ønsker som regel å sette sammen flere databaseoperasjoner, men bare gjøre ett eneste kall til execute metoden sin.
        Endre sortene slik at de kan settes sammen.
         */



    }


}
