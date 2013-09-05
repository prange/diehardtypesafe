package dhts;

import fj.F;

public class Saved<A> {

    public final String id;
    public final A value;

    private Saved(String id, A value) {
        this.id = id;
        this.value = value;
    }

    public static <A> F<A, Saved<A>> construct(final String id) {
        return new F<A, Saved<A>>() {
            @Override
            public Saved<A> f(A a) {
                return Saved.markSaved( id, a );
            }
        };
    }

    public static <A> F<Saved<A>, String> getId() {
        return new F<Saved<A>, String>() {
            public String f(Saved<A> saved) {
                return saved.id;
            }
        };
    }



    /**
     * Lager en statisk metode for å lage nye Saved objekter fordi det forenkler generics, og
     * fordi det gir meg mulighet til å lage flere ulike måter instansiere et objekt på med ulike navn.
     *
     * @param id
     * @param value
     * @param <T>
     * @return
     */
    public static <T> Saved<T> markSaved(String id, T value) {
        return new Saved<T>( id, value );
    }

    public static <A, B> F<Saved<A>, Saved<B>> lift(final F<A, B> f) {
        return new F<Saved<A>, Saved<B>>() {
            @Override
            public Saved<B> f(Saved<A> aSaved) {
                return aSaved.map( f );
            }
        };
    }

    /**
     * Map endrer "innholdet" til Saved ved hjelp av et funksjonsobjekt.
     *
     * @param function
     * @param <B>
     * @return
     */
    public <B> Saved<B> map(F<A, B> function) {
        B newValue = function.f( value );
        return new Saved<B>( id, newValue );
    }

}
