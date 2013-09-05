package dhts;

import fj.F;
import fj.data.Option;

import java.util.Map;
import java.util.UUID;

public abstract class DaoOp<A> {

    public static <A> Save<A> save(A a) {
        return new Save<>( a );
    }

    /**
     * Her har vi laget en funksjon av save() metoden, slik at vi kan bruke den som
     * en verdi.
     * @param <A>
     * @return
     */
    public static <A> F<A,DaoOp<Saved<A>>> save(){
        return new F<A, DaoOp<Saved<A>>>() {
            @Override
            public Save<A> f(A a) {
                return save(a);
            }
        };
    }

    public static <A> DaoOp<Saved<A>> update(Saved<A> saved){
        return new Update<>(saved);
    }

   public static <A> F<Saved<A>,DaoOp<Saved<A>>> update(){
       return new F<Saved<A>, DaoOp<Saved<A>>>() {
           @Override
           public DaoOp<Saved<A>> f(Saved<A> aSaved) {
               return update( aSaved );
           }
       };
   }


    public static <A> Get<A> get(String id, Class<A> type) {
        return new Get<>( id, type );
    }

    public static <A> DaoOp<Option<A>> collapse(final DaoOp<Option<DaoOp<A>>> operation){
        return new DaoOp<Option<A>>() {
            @Override
            public Option<A> run(final Map<String, Object> store) {
                Option<DaoOp<A>> result = operation.run( store );
                return result.option( Option.<A>none(),new F<DaoOp<A>, Option<A>>() {
                    @Override
                    public Option<A> f(DaoOp<A> aDaoOp) {
                        A a = aDaoOp.run( store );
                        return Option.some( a );
                    }
                } );
            }
        };
    }
    /**
     * Her kan vi endre innholdet til Operasjonsobjektet ved å kjøre "denne" operasjonen, og så putte inn resultet av kjøringen inn i funksjonen som vi mapper med.
     *
     * @param f
     * @param <B>
     * @return
     */
    public <B> DaoOp<B> map(final F<A, B> f) {
        return new DaoOp<B>() {

            @Override
            public B run(Map<String, Object> map) {
                return f.f( DaoOp.this.run( map ) );
            }
        };
    }

    public <B> DaoOp<B> flatMap(final F<A,DaoOp<B>> f){
        return new DaoOp<B>() {
            @Override
            public B run(Map<String, Object> map) {
                A a = DaoOp.this.run( map );
                return f.f(a).run(map);
            }
        };
    }


    /**
     * Denne abstrakte metoden blir implementert av de ulike konkrete operasjonene.
     *
     * @param map
     * @return
     */
    public abstract A run(Map<String, Object> map);

    /**
     * Get er sort som henter objekter ut av store.
     *
     * @param <A>
     */
    static class Get<A> extends DaoOp<Option<Saved<A>>> {

        private final Class<A> type;
        private final String id;

        private Get(String id, Class<A> type) {
            this.type = type;
            this.id = id;
        }

        @Override
        public Option<Saved<A>> run(Map<String, Object> store) {
            //Vi bruker option.fromNull() for å sikre oss at vi ikke får null når vi henter ut et objekt fra store.
            //Deretter binder vi over innholdet i option, og kaster innholdet til vår type. Hvis den er av feil type får vi None.
            //På denne måten unngår vi nullpointerexception hvis store hadde gitt oss null, og vi kan gi klienten noe selv om vi prøver å caste til
            //Bind er det samme som map, men har en smart tilleggsfunksjon: Hvis funksjonen gir oss en None, så blir totalen også None
            //feil type.
            return Option
                    .fromNull( store.get( id ) )
                    .bind( Caster.castTo( type ) )
                    .map( Saved.<A>construct( id ) );

        }
    }

    /**
     * Henter et objekt fra databasen vår.
     *
     * @param <A>
     */
    static class Save<A> extends DaoOp<Saved<A>> {
        public final A value;

        Save(A value) {
            this.value = value;
        }

        @Override
        public Saved<A> run(Map<String, Object> map) {
            String id = UUID.randomUUID().toString();
            Saved<A> saved = Saved.markSaved( id, value );
            map.put( id, saved );
            return saved;
        }
    }

    /**
     * Lagret et ulagret objekt i databasen vår
     *
     * @param <A>
     */
    static class Update<A> extends DaoOp<Saved<A>> {

        public final Saved<A> value;

        Update(Saved<A> value) {
            this.value = value;
        }

        @Override
        public Saved<A> run(Map<String, Object> map) {
            map.put( value.id, value );
            return value;
        }
    }
}
