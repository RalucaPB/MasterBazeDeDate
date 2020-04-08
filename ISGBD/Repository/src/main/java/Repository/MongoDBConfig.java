package Repository;

import Domain.DTO;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MongoDBConfig {

    MongoClient mongoClient;
    DB db;


    public MongoDBConfig() throws UnknownHostException {
        this.mongoClient= new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        this.db= mongoClient.getDB("ISGBD");

    }
    public void createTableMDB(String dbName,String tbName){
        DBCollection collection = db.getCollection(dbName+"_"+tbName);

        System.out.println("haha");
        mongoClient.getDatabaseNames().forEach(System.out::println);

    }
    public void createIndexT(String dbName,String tbName,String index){
        DBCollection collection = db.getCollection(dbName+"_"+tbName+"_"+index);

        System.out.println("haha");
        mongoClient.getDatabaseNames().forEach(System.out::println);

    }
    public void insert(String dbName, String tbName, DTO dto)
    {

        DBCollection collection = db.getCollection(dbName+"_"+tbName);
        BasicDBObject document = new BasicDBObject();
        document.put("key", dto.getKey());
        document.put("value", dto.getValue());
        collection.insert(document);

    }
    public void inserI(String dbName,String tbName,String index,DTO dto){
        DBCollection collection = db.getCollection(dbName+"_"+tbName+"_"+index);
        BasicDBObject document = new BasicDBObject();
        document.put("key", dto.getKey());
        document.put("value",dto.getValue());
        collection.insert(document);
    }
    public void delete(String dbName,String tbName, String key)
    {

        DBCollection collection= db.getCollection(dbName+"_"+tbName);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("key", key);
        collection.remove(searchQuery);
    }
    public void deleteI(String dbName,String tbName,String index, String value)
    {

        DBCollection collection= db.getCollection(dbName+"_"+tbName+"_"+index);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("value", value);
        collection.remove(searchQuery);
    }
    public void updateI(String dbName,String tbName,String index, DTO dto)
    {

        DBCollection collection = db.getCollection(dbName+"_"+tbName+"_"+index);

        collection.update(new BasicDBObject("key", dto.getKey()),
                new BasicDBObject("$set", new BasicDBObject("value", dto.getValue())));

    }
    public void update(String dbName,String tbName, DTO dto)
    {

        DBCollection collection = db.getCollection(dbName+"_"+tbName);

        collection.update(new BasicDBObject("key", dto.getKey()),
                new BasicDBObject("$set", new BasicDBObject("value", dto.getValue())));

    }
    public List<DTO> getDto(String dbName, String tbName)
    {
        List<DTO> list=new ArrayList<>();
        DBCollection collection = db.getCollection(dbName+"_"+tbName);
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject i=dbCursor.next();
            String key= (String) i.get("key");
            String value= (String) i.get("value");
            DTO dto=new DTO(key,value);
            list.add(dto);

        }
        return list;

    }
    public List<DTO> getDtoIndex(String dbName, String tbName,String index)
    {
        List<DTO> list=new ArrayList<>();
        DBCollection collection = db.getCollection(dbName+"_"+tbName+"_"+index);
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject i=dbCursor.next();
            String key= (String) i.get("key");
            String value= (String) i.get("value");
            DTO dto=new DTO(key,value);
            list.add(dto);

        }
        return list;

    }
    public String getValueByKey(String dbName, String tbName,String k)
    {
        List<String> list=new ArrayList<>();
        DBCollection collection = db.getCollection(dbName+"_"+tbName);
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject i=dbCursor.next();
            String key= (String) i.get("key");
            String value= (String) i.get("value");
            if(k.equals(key))
                return value;

        }
        return null;


    }
    public String getValueByKeyI(String dbName, String tbName,String index,String k)
    {
        List<String> list=new ArrayList<>();
        DBCollection collection = db.getCollection(dbName+"_"+tbName+"_"+index);
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject i=dbCursor.next();
            String key= (String) i.get("key");
            String value= (String) i.get("value");
            if(k.equals(key))
                return value;

        }
        return null;


    }
    public String getKeyByValueI(String dbName, String tbName,String index,String v)
    {
        List<String> list=new ArrayList<>();
        DBCollection collection = db.getCollection(dbName+"_"+tbName+"_"+index);
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject i=dbCursor.next();
            String key= (String) i.get("key");
            String value= (String) i.get("value");
            if(value.contains(v))
                return key;

        }
        return null;


    }
    public List<String> getValue(String dbName, String tbName)
    {
        List<String> list=new ArrayList<>();
        DBCollection collection = db.getCollection(dbName+"_"+tbName);
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject i=dbCursor.next();

            String value= (String) i.get("value");
            list.add(value);

        }
        return list;

    }
    public List<String> getKeys(String dbName, String tbName)
    {
        List<String> list=new ArrayList<>();
        DBCollection collection = db.getCollection(dbName+"_"+tbName);
        DBCursor dbCursor = collection.find();
        while (dbCursor.hasNext()) {
            DBObject i=dbCursor.next();

            String key= (String) i.get("key");
            list.add(key);

        }
        return list;

    }

    public boolean findPk(String dbName,String tbName,String name)
    {
        DBCollection collection= db.getCollection(dbName+"_"+tbName);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("key", name);
        DBCursor cursor=collection.find(searchQuery);
        if(cursor.hasNext())return true;
        else return false;

    }
    public boolean findUkI(String dbName,String tbName,String index,String name)
    {
        DBCollection collection= db.getCollection(dbName+"_"+tbName+"_"+index);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("key", name);
        DBCursor cursor=collection.find(searchQuery);
        if(cursor.hasNext())return true;
        else return false;

    }
    public static void main(String args[]) throws UnknownHostException {
        MongoDBConfig mongoDBConfig=new MongoDBConfig();
        //System.out.println(mongoDBConfig.findPk("Muzica","Artist","2"));
        //System.out.println( mongoDBConfig.findPk("Muzica","Artist","30"));

            System.out.println(mongoDBConfig.getValueByKey("Universitate","Student","456534"));

    }
}
