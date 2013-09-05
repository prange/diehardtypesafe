package dhts;

import fj.F;
import fj.data.List;
import fj.data.Option;
import fj.test.Rand;

public class Program {

      /*
        Sett det hele sammen:

        Lag først en funksjon som skal sjekke isbn nummeret til en bok, og gjøre alle bokstaver i den til uppercase.

        Skriv så et lite program som ved hjelp av en id laster inne en bok, endrer nummeret på den, lagrer den oppdaterte versjonen og returnerer en indikasjon på om operasjonen var vellykket eller ikke.


         */

    /**
     * Dette er en funksjon som tar inn en string og gjør den om til uppercase
     */
    public static F<String, String> toUpperCase = new F<String, String>() {
        @Override
        public String f(String s) {
            return s.toUpperCase();
        }
    };
    /**
     * Dette er en funksjon som tar inn en bok, og som returnerer en ny bok der isbn nummeret er uppercase.
     */
    public static F<Book, Book> isbnToUpprCase = new F<Book, Book>() {
        @Override
        public Book f(Book book) {
            return new Book( book.title, book.isbn.map( toUpperCase ) );
        }
    };

    public void run() {
        //Først må vi ha en DAO
        BookDao2 dao =
                new BookDao2();


        //Så lager vi noen bøker som vi kan teste med
        List<Book> books =
                Books.bookListGenerator.gen( 1, Rand.standard );


        //Så lager vi ett Save objekt for hver bok ved hjelp av DaoOp.save() metoden.
        List<DaoOp<Saved<Book>>> savers =
                books.map(DaoOp.<Book>save());


        //Så mapper vi over en funksjon som utfører databaseoperasjonen.
        //Denne ene linjen utfører alle lagringene
        List<Saved<Book>> savedBooks =
                savers.map( dao.<Saved<Book>>execute() );


        //Vi henter ut id'ene, siden vi trenger de til etterpå.
        List<String> ids =
                savedBooks.map(Saved.<Book>getId());


        //Så går vi gjennom listen med bøker
        for (String bookId:  ids) {


            //Først lager vi getteren som henter ut boka
            DaoOp.Get<Book> getter =
                    DaoOp.get( bookId, Book.class );


            //Så putter vi en funksjon inn i getteren for å endre ISBN-nummeret til boka
            DaoOp<Option<Saved<Book>>> updateBook =
                    getter
                            .map( Saved
                                    .lift( isbnToUpprCase )
                                    .mapOption() );


            //Så tar vi boka som vi kanskje kommer til å hente fra databasen, og lagrer den
            DaoOp<Option<DaoOp<Saved<Book>>>> updateAndStoreBook =
                    updateBook
                            .map( DaoOp.<Book>update().mapOption() );



            //Det resulterer i en operasjon som kanskje henter en bok, som deretter kanskje fører til en
            //operasjon som lagrer en lagret bok. Det blir litt komplisert, derfor
            //har vi laget en liten hjelpefunksjon som heter collapse(), som
            //forenkler litt ved at det gjør det hele om til en operasjon som kanskje fører til et lagret resultat
            DaoOp<Option<Saved<Book>>> simplifiedUpdateAndStoreBook =
                    DaoOp.collapse( updateAndStoreBook );


            //Men hittil har vi bare et objekt som gjør alt det der, vi har enda ikke utført noe som helst!
            //Men det gjør vi her:
            //Vi laster altså *kanskje* inn boka som er lagret (hvis id'en stemmer), endrer isbn nummer til store bokstaver
            //(hvis vi faktisk har hentet en bok), og lagrer endringen (igjen, hvis det var en bok å lagre)
            dao.execute( simplifiedUpdateAndStoreBook );

        }

    }
}
