package bones;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

import classes.Params;

@Path("/CRUD")
public class BoneJava {
	Params paramObject;
	List<Params> paramsList = new ArrayList<Params>();
	Map<String, List<Params>> paramsMap = new HashMap<String, List<Params>>();
	Gson jsonConverter = new Gson();
	Mongo mongo;DBCollection table;DB db ;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/register")
	public String createMethod(String userDetails) {
		System.out.println("create"+userDetails);
		String msg = "";
		paramObject = jsonConverter.fromJson(userDetails, Params.class);
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db= mongo.getDB("CKLM");
		/*table = db.getCollection("eventsList");
		String[] lists= new String[5];
		lists[0]="Best Acotr";
		lists[1]="BEst actress";
		lists[2]="Best Music";
		lists[3]= "Best director";
		lists[4]="Best producer";
		
		
		for(int i =0;i<lists.length;i++)
		{
			BasicDBObject empEventsMarksDoc = new BasicDBObject();
			empEventsMarksDoc.put("event", lists[i]);
			empEventsMarksDoc.put("evId",i);
			table.insert(empEventsMarksDoc);
			
		}*/
		table = db.getCollection("empdetails");
		BasicDBObject empdetailsDoc = new BasicDBObject();

		empdetailsDoc.put("empid",paramObject.getRempID());
		DBCursor cursor = table.find(empdetailsDoc);
		 
		if(!cursor.hasNext()) {
			msg = "Registered Successfully";
		
		
		empdetailsDoc.put("empid", paramObject.getRempID());
		empdetailsDoc.put("empname",paramObject.getRempNam());
		table.insert(empdetailsDoc);
		
		table = db.getCollection("empEventsMarks");
		
		System.out.println(paramObject.getId().length);
		for(int i =0;i<paramObject.getId().length;i++)
		{
			BasicDBObject empEventsMarksDoc = new BasicDBObject();
			empEventsMarksDoc.put("event", paramObject.getId()[i]);
			empEventsMarksDoc.put("empid",paramObject.getRempID());
			empEventsMarksDoc.put("empname",paramObject.getRempNam());
			empEventsMarksDoc.put("marks",0);
			table.insert(empEventsMarksDoc);
			
		}
		
		}
		else{
			msg = "you are Already registerd";
			
		}
		mongo.close();
		return msg;
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/Log")
	public String logMethod(String userDetails) {
		paramObject = jsonConverter.fromJson(userDetails, Params.class);

		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db= mongo.getDB("CKLM");
		table = db.getCollection("Loginalready");
			BasicDBObject logers = new BasicDBObject();
			logers.put("empid", paramObject.getRempID());
			DBCursor cursor1 = table.find(logers);
			String msg = "false";
			if (!cursor1.hasNext()) {
				table.insert(logers);	
		System.out.println("Log"+userDetails);
		
		
		table = db.getCollection("empdetails");
		BasicDBObject empdetailsDoc = new BasicDBObject();

		empdetailsDoc.put("empid",paramObject.getRempID());
		DBCursor cursor = table.find(empdetailsDoc);
		 
		if(cursor.hasNext()) {
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
	public String getVotelistMethod(@PathParam("empID") String empID) {
			
		System.out.println("voteList"+empID);
		String msg = "false";
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String,ArrayList<Object>> evenListMap = new HashMap<String, ArrayList<Object>>();
		db= mongo.getDB("CKLM");
		table = db.getCollection("eventsList");
		BasicDBObject eventsDoc = new BasicDBObject();
		eventsDoc.put("event", "Best Acotr");
		@SuppressWarnings("rawtypes")
		List values  = table.distinct("event");
		String result = null;
		for(int i =0;i<values.size();i++)
		{
			ArrayList<Object> eventEmpList = new ArrayList<Object>();
			table = db.getCollection("empEventsMarks");
			BasicDBObject empEventsMarksDoc = new BasicDBObject();
			empEventsMarksDoc.put("event", values.get(i));
			empEventsMarksDoc.put("empid", new BasicDBObject("$ne", empID));
			DBCursor cursor = table.find(empEventsMarksDoc);
			while (cursor.hasNext()) {
				eventEmpList.add(cursor.next());
			}
			evenListMap.put( (String) values.get(i), eventEmpList);
			result = jsonConverter.toJson(evenListMap);
			 
		}
			
			return result;
	}
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		@Path("/update")
		public String updatMethod(String empMarks) {
			paramObject = jsonConverter.fromJson(empMarks, Params.class);
			System.out.println(empMarks);
			String msg = "false";
			try {
				mongo = new Mongo("localhost", 27017);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db= mongo.getDB("CKLM");
			
			int newInt = 0;
			for(int i = 0 ;i<paramObject.getEmpMarks().size();i++){
				table = db.getCollection("empEventsMarks");
				BasicDBObject empEventsMarksDoc = new BasicDBObject();
				empEventsMarksDoc.put("event",paramObject.getEmpMarks().get(i).getEvent());
				empEventsMarksDoc.put("empid",paramObject.getEmpMarks().get(i).getRempIDs());
				DBCursor cursor = table.find(empEventsMarksDoc);
				while(cursor.hasNext()) {
					System.out.println("inside");
					msg= "true";
					Integer x = (Integer) cursor.next().get("marks");
					newInt =  x + paramObject.getEmpMarks().get(i).getMarks();

					BasicDBObject newquery = new BasicDBObject();
					newquery.put("marks", newInt);
					newquery.put("event",paramObject.getEmpMarks().get(i).getEvent());
					newquery.put("empid",paramObject.getEmpMarks().get(i).getRempIDs());
					newquery.put("empname",paramObject.getEmpMarks().get(i).getRempNam());
					
					WriteResult x1 = table.update(empEventsMarksDoc, newquery);
					System.out.println(x1);
					
				}
				
			}
		
			return msg;
		}
		@GET
		@Path("/results")
		public String results() {
			String[] mainevents= {"Best Acotr"
		     		,"BEst actress"
		     		,"Best Music"
		     		, "Best director"
		     		,"Best producer"};
		String msg = "false";
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db= mongo.getDB("CKLM");
		ArrayList<Object> resultsList = new ArrayList<Object>(); 
		Map<String,String > resultMap = new HashMap<String,String >();
		int newInt = 0;
		for(int i = 0 ;i<mainevents.length;i++){
		table = db.getCollection("empEventsMarks");
		BasicDBObject cmdBody = new BasicDBObject("aggregate", "empEventsMarks"); 
		 ArrayList<BasicDBObject> pipeline = new ArrayList<BasicDBObject>(); 
		  
		
		BasicDBObject match = new BasicDBObject("$match", new BasicDBObject("event",mainevents[i]));
		BasicDBObject sort = new BasicDBObject("$sort", new BasicDBObject("marks",-1));
		BasicDBObject limit = new BasicDBObject("$limit", 5);
		pipeline.add(match);
		pipeline.add(sort);
		pipeline.add(limit);
		cmdBody.put("pipeline", pipeline); 
		 CommandResult res = table.getDB().command(cmdBody);
						System.out.println(res);
				resultMap.put(mainevents[i],jsonConverter.toJson(res));
			}
			
		
		mongo.close();
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
		 table = db.getCollection("empdetails");
		 BasicDBObject document = new BasicDBObject();
			document.put("id", paramObject.getId());
			document.put("event",paramObject.getName());
			table.insert(document);
		
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
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", "null");
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db= mongo.getDB("CKLM");
		 table = db.getCollection("events");
		DBCursor cursor = table.find();
	 
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		paramObject = jsonConverter.fromJson(userDetails, Params.class);
		paramsList.add(paramObject);
		paramsMap.put(paramObject.getId(), paramsList);
		return jsonConverter.toJson(paramsMap);
	}*/
}
