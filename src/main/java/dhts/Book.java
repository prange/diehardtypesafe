package dhts;

import fj.data.Option;

/**
 * For å kjøre boka så null-sikker som mulig bør man gjøre feltene final. Da er man sikker på at de ikke endres tilsiktet.
 * Ved å bruke Option signaliserer man at isbn er optional.
 *
 * Flere lagde også to konstruktorer som faktisk gir kompileringsfeil. Mitt forslag er at man ikke gjør dette fordi det kan forvirre
 * den som overtar. Men det er absolutt et triks som kan være artig å ha med seg.
 *
 * Når feltene er final er det ikke vits å bruke gettere for å hente ut dataene, siden de ikke kan endres. Settere lager kopier av objektet,
 * slik at det blir trådsikkert, og slik at man unngår utilsiktede endringer.
 *
 */
public class Book {

    public final String title;
    public final Option<String> isbn;


    public Book(final String title, final Option<String> isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public Book changeTitle(String title){
        return new Book(title,isbn);
    }

    public Book setISBN(Option<String> isbn){
        return new Book(title,isbn);
    }
}
