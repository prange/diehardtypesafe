package dhts;

import fj.F;
import fj.data.Either;
import fj.data.Option;
import fj.data.Validation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BookDao1 {

    //DAO functions
    private static Map<String, Saved<Book>> books = new HashMap<String, Saved<Book>>();

    /**
     * Save tar inn en ulagret bok. Mange lagde også en Unsaved sort for å vise at en bok var ulagret, noe som slett ikke var dumt.
     * Jeg har valgt å ikke bruke Unsaved her for å spare meg implementeringen.
     * Validation er et objekt som enten inneholder en feilmelding eller det objektet man forventer, og brukes istendefor Exceptions.
     * Man kunne også bruke Either<String,Saved<Book>> her, men Validation gir det litt mer betydning.
     *
     * @param book
     * @return
     */
    public static Validation<String, Saved<Book>> save(Book book) {
        try {
            Saved<Book> saved = Saved.markSaved( UUID.randomUUID().toString(), book );
            books.put( saved.id, saved );
            return Validation.success( saved ); //Vi fikk lagret, og vi returnerer det lagrede objektet.
        } catch (Exception e) {
            return Validation.fail( e.getMessage() ); //En feil har oppstått, og vi returnerer feilmeldingen.
        }
    }

    /**
     * Vi må vise at vi bare kan oppdatere bøker som allerede er lagret. Derfor tar vi bare inn Saved Book objekter.
     *
     * @param saved
     */
    public static Validation<String, Saved<Book>> update(Saved<Book> saved) {
        try {
            books.put( saved.id, saved );
            return Validation.success( saved ); //Vi fikk lagret, og vi returnerer det lagrede objektet.
        } catch (Exception e) {
            return Validation.fail( e.getMessage() ); //En feil har oppstått, og vi returnerer feilmeldingen.
        }
    }

    public static Validation<String, Saved<Book>> saveOrUpdate(Either<Book, Saved<Book>> book) {
        return book.either( new F<Book, Validation<String, Saved<Book>>>() {
                                public Validation<String, Saved<Book>> f(Book b) {
                                    return save( b );
                                }
                            }, new F<Saved<Book>, Validation<String, Saved<Book>>>() {
                                public Validation<String, Saved<Book>> f(Saved<Book> bookSaved) {
                                    return update( bookSaved );
                                }
                            }
        );
    }

    /**
     * Her har jeg valgt å bruke option for å vise at det ikke er sikkert man får en bok for en gitt ID. Man kunne bruke Validation for å få
     * med feilmeldinger også.
     *
     * @param id
     * @return
     */
    public static Option<Saved<Book>> load(String id) {
        return Option.fromNull( books.get( id ) );
    }
}
