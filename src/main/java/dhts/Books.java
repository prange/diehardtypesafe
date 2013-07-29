package dhts;

import fj.F;
import fj.F2;
import fj.data.List;
import fj.data.Option;
import fj.test.Arbitrary;
import fj.test.Gen;

import java.util.HashMap;
import java.util.Map;

public class Books {

    public static Gen<String> titleGenerator = Arbitrary.arbAlphaNumString.gen;
    public static Gen<String> isbnGenerator = Arbitrary.arbAlphaNumString.gen;
    public static Gen<Option<String>> optionalIsbnGenerator = Arbitrary.arbBoolean.gen.bind( new F<Boolean, Gen<Option<String>>>() {
        @Override
        public Gen<Option<String>> f(Boolean aBoolean) {
            if (aBoolean)
                return isbnGenerator.map( Option.<String>some_() );
            else
                return Gen.elements( Option.<String>none() );
        }
    } );

    public static Gen<Book> bookGenerator = titleGenerator.bind(isbnGenerator,new F2<String, String, Book >() {
        @Override
        public Book f(String s1,String s2) {
            return new Book(s1,s2);
        }
    }.curry() );

    public static Gen<List<Book>> bookListGenerator = Gen.listOf( bookGenerator );

    //DAO functions

    private static Map<Object,Book> books = new HashMap<Object, Book>(  );

    public static Book save(Book book){

    }

    public static void update(Book book){

    }

    public static Book saveOrUpdate(Book book){

    }

    public static Book load(Object id){

    }
}
