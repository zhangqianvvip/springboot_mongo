package cn.com.taiji.mongoweb;

import com.mongodb.*;

/**
 * Hello world!
 */
public class TestMongoMR2 {

    public static void main(String[] args) {


                Mongo mongo;

                try {
                    mongo = new Mongo("localhost", 27017);
                    DB db = mongo.getDB("test");
                    DBCollection books = db.getCollection("mapreduceTest");
                   /* DBCollection books = db.getCollection("books");

                    BasicDBObject book = new BasicDBObject();
                    book.put("name", "Understanding JAVA");
                    book.put("pages", 100);
                    books.insert(book);

                    book = new BasicDBObject();
                    book.put("name", "Understanding JSON");
                    book.put("pages", 200);
                    books.insert(book);

                    book = new BasicDBObject();
                    book.put("name", "Understanding XML");
                    book.put("pages", 300);
                    books.insert(book);

                    book = new BasicDBObject();
                    book.put("name", "Understanding Web Services");
                    book.put("pages", 400);
                    books.insert(book);

                    book = new BasicDBObject();
                    book.put("name", "Understanding Axis2");
                    book.put("pages", 150);
                    books.insert(book);*/

                    String map = "function(){ emit(this.location, this.age); }";

                    String reduce = "function( key, values ){ return Array.sum(values); }";


                    MapReduceCommand cmd = new MapReduceCommand(books, map, reduce,
                            null, MapReduceCommand.OutputType.INLINE, null);

                    MapReduceOutput out = books.mapReduce(cmd);

                    for (DBObject o : out.results()) {
                        System.out.println(o.toString());
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

        }
    }

}

