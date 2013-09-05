package dhts;

import fj.Effect;
import fj.F;

import java.util.HashMap;
import java.util.Map;

/**
 * Dette er en implementasjon av books som kjører DAO operasjoner. Books bryr seg nå bare om
 * å holde styr på "store", og lar operasjonene selv håndtere lagring og henting.
 */
public class BookDao2 {

    private Map<String, Object> store = new HashMap<>();

    //Utfører operasjonen og returnerer resultatet.
    public <A> A execute(DaoOp<A> op) {
        return op.run( store );
    }

    //En funksjon som utfører operasjonen og returnerer resultatet.
    public <A> F<DaoOp<A>,A> execute(){
        return new F<DaoOp<A>, A>() {
            public A f(DaoOp<A> aDaoOp) {
                return execute(aDaoOp);
            }
        };
    }


}
