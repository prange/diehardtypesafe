package dhts;

import fj.F;
import fj.data.Option;

/**
 * Jeg har laget en hjelpeklasse som er en funksjon. Den har metode som tar inn et objekt som argument.
 * Er metoden av samme type som vi har satt i konstruktoren castes den og den returnerer en Some(A), ellers returnerer
 * den en None
 * @param <A>
 */
public class Caster<A> extends F<Object,Option<A>>{

    private final Class<A> type;

    public Caster(Class<A> type) {
        this.type = type;
    }

    /**
     * Statisk factory metode som gjør den enklere å bruke.
     * @param type
     * @param <A>
     * @return
     */

    public static <A> Caster<A> castTo(Class<A> type){
        return new Caster<A>( type );
    }

    @Override
    public Option<A> f(Object o) {
        if (type.isInstance( o ))
            return Option.some( type.cast( o ) );
        else
            return Option.none();
    }
}
