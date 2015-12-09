package bones;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.MongoURI;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import classes.Params;

@Path("/CRUD")
public class BoneJava {
	Params paramObject;
	List<Params> paramsList = new ArrayList<Params>();
	Map<String, List<Params>> paramsMap = new HashMap<String, List<Params>>();
	Gson jsonConverter = new Gson();
	Mongo mongo;DBCollection table;DB db ;
	String textUri = "mongodb://chetan1010:chetan1010@ds027155.mongolab.com:27155/votemagik";
	MongoCollection<Document> iterable ;
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/register")
	public String createMethod(String userDetails) {
		System.out.println("create"+userDetails);
		String msg = "";
		paramObject = jsonConverter.fromJson(userDetails, Params.class);
		MongoClientURI uri = new MongoClientURI(textUri);
		MongoClient mongoclinet  = new MongoClient(uri);
		MongoDatabase db= mongoclinet.getDatabase("votemagik");
		/*boolean auth = db.authenticate("chetan1010", "chetan1010".toCharArray());
		System.out.println(auth);*/
		/*table = db.getCollection("eventsList");
		String[] lists= new String[5];
		lists[0]="Best Acotr";
		lists[1]="BEst actress";
		lists[2]="Best Music";
		lists[3]= "Best director";
		lists[4]="Best producer";
		
		
		for(int i =0;i<lists.length;i++)
		{
			Document empEventsMarksDoc = new Document();
			empEventsMarksDoc.put("event", lists[i]);
			empEventsMarksDoc.put("evId",i);
			table.insert(empEventsMarksDoc);
			
		}*/
		iterable =  db.getCollection("empdetails");
		Document empdetailsDoc = new Document();

		empdetailsDoc.put("empid",paramObject.getRempID());
		FindIterable<Document> cursor = iterable.find(empdetailsDoc);
		 
		if(!cursor.iterator().hasNext()) {
			msg = "Registered Successfully";
		
		
		empdetailsDoc.put("empid", paramObject.getRempID());
		empdetailsDoc.put("empname",paramObject.getRempNam());
		iterable.insertOne(empdetailsDoc);
		
		iterable  =  db.getCollection("empEventsMarks");
		
		System.out.println(paramObject.getId().length);
		for(int i =0;i<paramObject.getId().length;i++)
		{
			Document empEventsMarksDoc = new Document();
			empEventsMarksDoc.put("event", paramObject.getId()[i]);
			empEventsMarksDoc.put("empid",paramObject.getRempID());
			empEventsMarksDoc.put("empname",paramObject.getRempNam());
			empEventsMarksDoc.put("marks",0);
			iterable .insertOne(empEventsMarksDoc);
			
		}
		
		}
		else{
			msg = "you are Already registerd";
			
		}
		mongoclinet.close();
		return msg;
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/Log")
	public String logMethod(String userDetails) throws UnknownHostException {
		paramObject = jsonConverter.fromJson(userDetails, Params.class);

		MongoClientURI uri = new MongoClientURI(textUri);
		MongoClient mongoclinet  = new MongoClient(uri);
		MongoDatabase db= mongoclinet.getDatabase("votemagik");
		
		iterable = db.getCollection("Loginalready");
			Document logers = new Document();
			logers.put("empid", paramObject.getRempID());
			FindIterable<Document> cursor1 = iterable.find(logers);
			String msg = "false";
			if (!cursor1.iterator().hasNext()) {
				iterable.insertOne(logers);	
		System.out.println("Log"+userDetails);
		
		
		iterable = db.getCollection("empdetails");
		Document empdetailsDoc = new Document();

		empdetailsDoc.put("empid",paramObject.getRempID());
		FindIterable<Document> cursor = iterable.find(empdetailsDoc);
		 
		if(cursor.iterator().hasNext()) {
			msg =  "true";
		}
			}
			else{
			
			 msg = "false";
			}
		return msg;
		
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/voteList/{empID}")
	public String getVotelistMethod(@PathParam("empID") String empID) throws UnknownHostException {
			
		System.out.println("voteList"+empID);
		
		MongoClientURI uri = new MongoClientURI(textUri);
		MongoClient mongoclinet  = new MongoClient(uri);
		MongoDatabase db= mongoclinet.getDatabase("votemagik");
		
		Map<String,ArrayList<Object>> evenListMap = new HashMap<String, ArrayList<Object>>();
		String[] values= {"Best Acotr"
	     		,"BEst actress"
	     		,"Best Music"
	     		, "Best director"
	     		,"Best producer"};
		String result = null;
		for(int i =0;i<values.length;i++)
		{
			 final ArrayList<Object> eventEmpList = new ArrayList<Object>();
			iterable = db.getCollection("empEventsMarks");
			Document empEventsMarksDoc = new Document();
			empEventsMarksDoc.put("event", values[i]);
			empEventsMarksDoc.put("empid", new Document("$ne", empID));
			FindIterable<Document> cursor = iterable.find(empEventsMarksDoc);
			/*while (cursor.) {
				eventEmpList.add(cursor.first());
			}*/
			cursor.forEach(new Block<Document>() {
			    @Override
			    public void apply(final Document document) {
			        System.out.println(document);
			        eventEmpList.add(document);
			    }
			});
			evenListMap.put( (String) values[i], eventEmpList);
			result = jsonConverter.toJson(evenListMap);
			 
		}
			
			return result;
	}
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		@Path("/update")
		public String updatMethod(String empMarks) throws UnknownHostException {
			paramObject = jsonConverter.fromJson(empMarks, Params.class);
			System.out.println(empMarks);
			String msg = "false";
			MongoClientURI uri = new MongoClientURI(textUri);
			MongoClient mongoclinet  = new MongoClient(uri);
			MongoDatabase db= mongoclinet.getDatabase("votemagik");
			
			int newInt = 0;
			for(int i = 0 ;i<paramObject.getEmpMarks().size();i++){
				iterable = db.getCollection("empEventsMarks");
				Document empEventsMarksDoc = new Document();
				empEventsMarksDoc.put("event",paramObject.getEmpMarks().get(i).getEvent());
				empEventsMarksDoc.put("empid",paramObject.getEmpMarks().get(i).getRempIDs());
				FindIterable<org.bson.Document> cursor = iterable.find(empEventsMarksDoc);
				while(cursor.iterator().hasNext()) {
					System.out.println("inside");
					msg= "true";
					Integer x = (Integer) cursor.iterator().next().get("marks");
					newInt =  x + paramObject.getEmpMarks().get(i).getMarks();

					Document newquery = new Document();
					newquery.put("marks", newInt);
					newquery.put("event",paramObject.getEmpMarks().get(i).getEvent());
					newquery.put("empid",paramObject.getEmpMarks().get(i).getRempIDs());
					newquery.put("empname",paramObject.getEmpMarks().get(i).getRempNam());
					
					UpdateResult x1 = iterable.updateOne(empEventsMarksDoc, newquery);
					System.out.println(x1);
					
				}
				
			}
		
			return msg;
		}
		@GET
		@Path("/results")
		public String results() throws UnknownHostException {
			String[] mainevents= {"Best Acotr"
		     		,"BEst actress"
		     		,"Best Music"
		     		, "Best director"
		     		,"Best producer"};
			MongoClientURI uri = new MongoClientURI(textUri);
			MongoClient mongoclinet  = new MongoClient(uri);
			MongoDatabase db= mongoclinet.getDatabase("votemagik");
		new ArrayList<Object>(); 
		Map<String,String > resultMap = new HashMap<String,String >();
		for(int i = 0 ;i<mainevents.length;i++){
		iterable = db.getCollection("empEventsMarks");
		
		BasicDBObject match = new BasicDBObject("$match", new BasicDBObject("event",mainevents[i]));
		BasicDBObject sort = new BasicDBObject("$sort", new BasicDBObject("marks",-1));
		BasicDBObject limit = new BasicDBObject("$limit", 5);
		
		  AggregateIterable<Document> result = iterable.aggregate(Arrays.asList(
				  match,sort,limit
		    )).allowDiskUse(true);
		  
				resultMap.put(mainevents[i],jsonConverter.toJson(result.iterator()));
			}
			
		
		mongoclinet.close();
		return jsonConverter.toJson(resultMap);
		}

	
	
	/*
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String createMethod(String userDetails) {
		System.out.println("create"+userDetails);
		paramObject = jsonConverter.fromJson(userDetails, Params.class);
		paramsList.add(paramObject);
		paramsMap.put(paramObject.getName(), paramsList);
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db= mongo.getDB("CKLM");
		 iterable = db.getCollection("empdetails");
		 Document document = new Document();
			document.put("id", paramObject.getId());
			document.put("event",paramObject.getName());
			iterable.insert(document);
		
		return jsonConverter.toJson(paramsMap);
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String updateMethod(@PathParam("id") String id,String  userDetails) {
		System.out.println("update"+userDetails);
		paramObject = jsonConverter.fromJson(userDetails, Params.class);
		paramsList.add(paramObject);
		paramsMap.put(paramObject.getId(), paramsList);
		return id;
	}
	@DELETE
	@Path("/{id}")
	public String deleteMethod(@PathParam("id") String id,String  userDetails) {
		System.out.println("delete"+userDetails);
		paramObject = jsonConverter.fromJson(userDetails, Params.class);
		paramsList.add(paramObject);
		paramsMap.put(paramObject.getId(), paramsList);
		return id;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String getMethod(@PathParam("id") String id) {
		System.out.println("GET"+id);
		
		Document searchQuery = new Document();
		searchQuery.put("id", "null");
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db= mongo.getDB("CKLM");
		 iterable = db.getCollection("events");
		FindIterable<org.bson.Document> cursor = iterable.find();
	 
		while (cursor.iterator().hasNext()) {
			System.out.println(cursor.next());
		}
		paramObject = jsonConverter.fromJson(userDetails, Params.class);
		paramsList.add(paramObject);
		paramsMap.put(paramObject.getId(), paramsList);
		return jsonConverter.toJson(paramsMap);
	}*/
}
