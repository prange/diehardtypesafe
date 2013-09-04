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
        Når man jobber med databaser er det vanlig å ha et id felt i objektene som lagres. Det er også vanlig å gjøre en Null-sjekk på id i dao laget for å sjekke om et objekt er nytt eller "gammelt"
        Når man lagrer et tidligere ulagret objekt får man ofte tilbake de samme objektet med id, eller bare en id tilbake.

        Nå skal vi fjerne denne tvedydigheten om et objekt er lagret eller ulagret:

        Lag en sort som represtenter et lagret objekt, og endre metodesignaturene til dao motodene i Books til å reflektere dette.
        Implementer en enkel lagring vha books HashMap<Object,Book> feltet i Books
        Hva skjer når man prøver å hente en bok som ikke finnes i lageret? Reflekterer metodesignaturene dette? (Hint, exception er ikke typesikkert)


        Du kan bruke bookListGenerator for å lage en liste med bøker for å teste.

        4)
        Hva skjer når hvis flere tråder henter samme bok og gjør endringer på den?
        Hva er forventet oppførsel når man gjør endringer? Må man lagre boka igjen for at de skal "sitte"?

         */


        /*
        Bakgrunn:


        5)
        Dao metodene eksponerer delt endrebar tilstand og Books endrer tilstand mellom hvert kall. Dette ønsker vi å unngå.
        Lag en eller flere sorter som represtenterer db-operasjoner og data uten at daoen faktisk endrer tilstand.

         */


        /*
        6)
        Lag en en ny versjon av Books som kjører databaseoperasjoner.
         */




        /*
        Sett det hele sammen:

        Lag først en funksjon som skal sjekke isbn nummeret til en bok, og gjøre alle bokstaver i den til uppercase.

        Skriv så et lite program som ved hjelp av en id laster inne en bok, endrer nummeret på den, lagrer den oppdaterte versjonen og returnerer en indikasjon på om operasjonen var vellykket eller ikke.


         */


    }


}
