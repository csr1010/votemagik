var testModel = Backbone.Model.extend({
	defaults:{
		empIDlabel:"Employee ID",empNamlabel:"Employee Name",eventslabel:"Events",mainevents:["Best Acotr"
		,"BEst actress"
		,"Best Music"
		, "Best director"
		,"Best producer"],warning:"",currentEvent:"",empNames:["chetan","sandeep","sandeep","sandeep","sandeep","sandeep","sandeep","sandeep","sandeep","sandeep","sandeep","sandeep","sandeep","sandeep","sandeep","sandeep","sandeep","sandeep"],eventMap:{}
	},
	
	 initialize: function(){ 
		 
		 this.on("change:mainevents", function(model){
			  var name = model.get("mainevents"); 
			//  $("#mainevents_frm").empty().append()
		 });
		/* this.on("change:warning", function(model){
			  var warning = model.get("warning"); 
			  $("#regAlert").html(warning).parent().show();
		 });*/
		 }
});
var resultsrModel = Backbone.Model.extend({
	resultsJson:{
		  "Best producer": "{ \"serverUsed\" : \"localhost/127.0.0.1:27017\" , \"result\" : [ { \"_id\" : { \"$oid\" : \"51e65f5e35848e650490b6e8\"} , \"marks\" : 156 , \"event\" : \"Best producer\" , \"empid\" : \"666666\" , \"empname\" : \"test login2\"} , { \"_id\" : { \"$oid\" : \"51e65e6c3584ecdf5fa6a939\"} , \"marks\" : 101 , \"event\" : \"Best producer\" , \"empid\" : \"555555\" , \"empname\" : \"test login\"}] , \"ok\" : 1.0}",
	    "BEst actress": "{ \"serverUsed\" : \"localhost/127.0.0.1:27017\" , \"result\" : [ { \"_id\" : { \"$oid\" : \"51e65e1c3584ecdf5fa6a934\"} , \"marks\" : 90 , \"event\" : \"BEst actress\" , \"empid\" : \"533135\" , \"empname\" : \"chetan\"}] , \"ok\" : 1.0}",
	    "Best director": "{ \"serverUsed\" : \"localhost/127.0.0.1:27017\" , \"result\" : [ { \"_id\" : { \"$oid\" : \"51e65e6c3584ecdf5fa6a938\"} , \"marks\" : 167 , \"event\" : \"Best director\" , \"empid\" : \"555555\" , \"empname\" : \"test login\"}] , \"ok\" : 1.0}",
	    "Best Acotr": "{ \"serverUsed\" : \"localhost/127.0.0.1:27017\" , \"result\" : [ { \"_id\" : { \"$oid\" : \"51e65e1c3584ecdf5fa6a933\"} , \"marks\" : 167 , \"event\" : \"Best Acotr\" , \"empid\" : \"533135\" , \"empname\" : \"chetan\"} , { \"_id\" : { \"$oid\" : \"51e65f5e35848e650490b6e6\"} , \"marks\" : 124 , \"event\" : \"Best Acotr\" , \"empid\" : \"666666\" , \"empname\" : \"test login2\"}] , \"ok\" : 1.0}",
	    "Best Music": "{ \"serverUsed\" : \"localhost/127.0.0.1:27017\" , \"result\" : [ { \"_id\" : { \"$oid\" : \"51e65e1c3584ecdf5fa6a935\"} , \"marks\" : 156 , \"event\" : \"Best Music\" , \"empid\" : \"533135\" , \"empname\" : \"chetan\"} , { \"_id\" : { \"$oid\" : \"51e65e6c3584ecdf5fa6a937\"} , \"marks\" : 134 , \"event\" : \"Best Music\" , \"empid\" : \"555555\" , \"empname\" : \"test login\"} , { \"_id\" : { \"$oid\" : \"51e65f5e35848e650490b6e7\"} , \"marks\" : 132 , \"event\" : \"Best Music\" , \"empid\" : \"666666\" , \"empname\" : \"test login2\"}] , \"ok\" : 1.0}"
	},
	timeOut:10,
	urlRoot: '../bbones/ws/CRUD/results',
	initialize: function(){
		/*this.on("change:resultsJson",function(model){
			//alert(model)
			fuck(model)
			});*/
	}
	
	
});

var upDaterModel = Backbone.Model.extend({
	defaults:{
		empMarks : []//,oldMarks:[]
	},
	urlRoot: '../bbones/ws/CRUD/update',
	
});
var registerModel = Backbone.Model.extend({
	defaults:{
		rempID:"Employee ID",rempNam:"Employee Name",mainevents:[]
	},
	urlRoot: '../bbones/ws/CRUD/register',
	
});
var logModel = Backbone.Model.extend({
	defaults:{
		rempID:"Employee ID"
	},
	urlRoot: '../bbones/ws/CRUD/Log',
	
});
var viewModel = Backbone.Model.extend({
	defaults:{
		rempID:"Employee ID"
	},
	idAttribute:"rempID",
	urlRoot: '../bbones/ws/CRUD/voteList/',
	
});
var  tstMdlObj = new testModel();
var  upDaterModelObj = new upDaterModel();
var  viewModelObj = new viewModel();
var  registerModelObj = new registerModel();
var  logModelObj = new logModel();
var resultsObj = new resultsrModel();

var resultsView =  Backbone.View.extend({
	initialize:function(){
		this.render();
	},
	render:function(){
		var  templt= _.template($("#resultsDiv").html(),resultsObj.toJSON());
		this.$el.html(templt);
	}
});
var listView =  Backbone.View.extend({
	initialize:function(){
		this.render();
	},
	render:function(){
		var  templt= _.template($("#voteTiles").html(),tstMdlObj.toJSON());
		this.$el.html(templt);
	},
	events:{
		"click .evClas":"surrRender",
		"click #VoteButton":"updateMarks",
		"keydown .numeric100":"numeric",
		"click #exitButton":"leave"
	},
	leave:function(){
		$("#register").html("Thank you for participation");
	},
	numeric:function(evt){
		try{
			
		 var charCode = (evt.which) ? evt.which : window.event.keyCode;  

	        if (charCode <= 13) 
	        { 
	            return true; 
	        } 
	        else 
	        { 
	            var keyChar = String.fromCharCode(charCode); 
	            var re = /[0-9]/ ;
	            if(re.test(keyChar)){
	            	var $this= Number(evt.target.value);
	    			if(evt.target.textLength ==2 && $this == 10){evt.target.maxLength =3;evt.target.value = 100;}
	    			else if(evt.target.textLength ==2 && $this <100)evt.target.maxLength =2;	
	            }
	            else
	            evt.preventDefault(); 
	        }
		}
		catch(e){}
		},
	updateMarks:function(ev){
		var ritnowEvent = tstMdlObj.get("currentEvent");
	    var temparr = [];var tempArr2 = [];
	    var marksMap = tstMdlObj.get("eventMap");
	    for(var i in marksMap)
	    	{
	    	  for(var k in marksMap[i])
	    		  {
			    		  var empMarks = {
			  					marks:"",
			  					rempid:""
			  			};
	    		  empMarks.marks = marksMap[i][k].marks;
	    		  empMarks.rempid = marksMap[i][k].empid;
	    		  tempArr2.push(empMarks);
	    		  }
	    	}
		for(var i =0;i<document.getElementById("tab_"+ritnowEvent).childNodes[1].childElementCount;i++){
			var empMarks = {
					marks:"",
					rempids:"",
					rempNam:"",
					event:""					
			};
			empMarks.marks = document.getElementById("tab_"+ritnowEvent).childNodes[1].children[i].children[1].children[0].value ?document.getElementById("tab_"+ritnowEvent).childNodes[1].children[i].children[1].children[0].value:0;
			empMarks.rempids = document.getElementById("tab_"+ritnowEvent).childNodes[1].children[i].children[1].children[0].id;
			empMarks.rempNam = document.getElementById("tab_"+ritnowEvent).childNodes[1].children[i].children[0].children[0].innerHTML;
			empMarks.event = ritnowEvent;
			temparr.push(empMarks);
			document.getElementById("tab_"+ritnowEvent).childNodes[1].children[i].children[1].children[0].disabled=true;
		}
		upDaterModelObj.set("empMarks",temparr);
		
		upDaterModelObj.save(null, 
				{ success: function (user,resp) 
			       { 
					 console.log(resp); 
					 $("#voteAlert").html("Voted successfully").parent().removeClass("alert-error").addClass("alert-success").show();
				    } ,
				    error: function(model, error) {
				        console.log(model.toJSON());
				    }
		});
	}
	,
	surrRender:function(ev){
		$(".tabu").hide();
		var $this= ev.target.attributes["id"];
		var event = $this.nodeValue;
		 tstMdlObj.set("currentEvent",event);
		 var t = document.getElementById("tab_"+event);
		 $(t).show();
		 $("#VoteButton").html('<span class="add-on"><i class="icon-ok-sign"></i></span> Done with '+event);
		 $(".evClas").removeClass("active");
		 var d = document.getElementById("li_"+event);
		 d.className = d.className + " active";
	}
	
});
var headView =  Backbone.View.extend({
	initialize:function(){
		this.render();
	},
	render:function(){
		var  templt= _.template($("#hedTiles").html(),{});
		this.$el.html(templt);
	},
	events:{
		"click #Login":"checkLog"
	},
checkLog:function(){
		
		var empidLenth = $.trim($("#log_frm").val()).length;
		if( empidLenth !=6 ){
			$("#regAlert").html("empid = 6 digits ONLY ").parent().show();
		}else{
			logModelObj.set("rempID",$("#log_frm").val());
			logModelObj.save(null,
					{ success: function (user,resp) 
				       {
						if(resp){
						
						viewModelObj.set("rempID",$("#log_frm").val());
						viewModelObj.fetch(
								{ success: function (user,resp) 
							       { 
									 console.log("logged in ,,, enojoy"); 
									 $("#logForm").hide();
									 var events = [];
									 var empIDs =[];
									 for(var event in resp)
										 {
										 var temp={};
										 events.push(event);
										 tstMdlObj.set("eventMap",resp);
										 }
									 tstMdlObj.set("mainevents",events);
									 tstMdlObj.set("currentEvent",events[0]);
									 var  listViewObj = new listView({el:$("#register")});
									 $(".tabu:first").show();
									 $("#VoteButton").html('<span class="add-on"><i class="icon-ok-sign"></i></span> Done with '+events[0]);
								    } ,
								    error: function(model, error) {
								        console.log(model.toJSON());
								    }
						});
						}
						else{
							 $("#regAlert").html("Hey ! you are not registered, or already voted  sorry !!!!").parent().addClass("alert-error").removeClass("alert-success").show();	
						}
					    } ,
					    error: function(model, error) {
					        $("#regAlert").html(error.responseText).parent().addClass("alert-error").removeClass("alert-success").show();
					    }
			});
			
		}
	}
});
var testView =  Backbone.View.extend({
	initialize:function(){
		this.render();
	},
	render:function(){
		var  templt= _.template($("#Registration").html(),tstMdlObj.toJSON());
		this.$el.html(templt);
	},
	events:{
		"click #regButton":"addtoRegList",
		"keypress .numeric":"Numeric",
		"click #del":"deleteData",
		"click #get":"getData"
	},
	
	Numeric:function(evt){
		try{
		 var charCode = (evt.which) ? evt.which : window.event.keyCode;  

	        if (charCode <= 13) 
	        { 
	            return true; 
	        } 
	        else 
	        { 
	            var keyChar = String.fromCharCode(charCode); 
	            var re = /[0-9]/ ;
	            re.test(keyChar)?"": evt.preventDefault(); 
	        }
		}
		catch(e){}
		},
	addtoRegList:function(){
		
		var eventsList = $("#mainevents_frm").val() || [] ;
		var empidLenth = $.trim($("#rempID_frm").val()).length;
		if(eventsList.length > 3 || empidLenth !=6 ){
			$("#regAlert").html("rule 1: empid = 6 digits ONLY <br> rule 2 :Maximum of 3 events can be Registered").parent().show();
		}else{
			$("#register :input:not('button')").each(function(){
				var objName = this.id.split("_")[0] ;
				registerModelObj.set(objName, $(this).val() || []);
			});
			registerModelObj.save(null,
					{ success: function (user,resp) 
				       { 
						$("#regAlert").html(resp).parent().removeClass("alert-error").addClass("alert-success").show();
					    } ,
					    error: function(model, error) {
					        $("#regAlert").html(error.responseText).parent().addClass("alert-error").removeClass("alert-success").show();
					    }
			});
			
		}
	}
	/*getData:function(){
		tstMdlObj.set("id","1");
		tstMdlObj.fetch( 
				{ success: function (user,resp) 
			       { 
					 console.log(JSON.stringify(resp)); 
				    } ,
				    error: function(model, error) {
				        console.log(model.toJSON());
				    }
		});
	},
	deleteData:function(){
		tstMdlObj.set("id","1");
		tstMdlObj.destroy( 
				{ success: function (user,resp) 
			       { 
					 console.log(JSON.stringify(resp)); 
				    } ,
				    error: function(model, error) {
				        console.log(model.toJSON());
				    }
		});
	},
	updateData:function(){
		testDel={
				id:"1",
				name :$("#name").val(),
				paswd :$("#paswd").val()
		
		};
		tstMdlObj.save(testDel, 
				{ success: function (user,resp) 
			       { 
					 console.log(JSON.stringify(resp)); 
				    } ,
				    error: function(model, error) {
				        console.log(model.toJSON());
				    }
		});
	}
	,submitData:function(){
		testDel={
				id:null,
				name :$("#name").val(),
				paswd :$("#paswd").val()
		
		};
		tstMdlObj.set("name",$("#name").val());
		tstMdlObj.set("paswd",$("#paswd").val());
		
		tstMdlObj.save( testDel,
				{ success: function (user,resp) 
			       { 
					 console.log(JSON.stringify(resp)); 
				    } ,
				    error: function(model, error) {
				        console.log(model.toJSON());
				        console.log(error);
				    }
		});
	}*/
	
});

$(function(){
	var  testViewObj = new testView({el:$("#register")});
	var  headViewObj = new headView({el:$("#header")});
	
	var deaddate =new Date(2013, 9-1, 20);
	var currentdate = new Date();
	var timeOute ;
	timeOute = Math.floor(Math.random()*10*2)*1000;
	if(currentdate.getDate() >=  deaddate.getDate())
		{
		
		   $("#register :input").attr("disabled","disabled");
		   $("#regButton").text("Registrations Closed");
		   $("#Login").html("<span class=add-on><i class=icon-ok></i></span> start Voting");
		   var int=self.setInterval(function(){
			   resultsObj.fetch( 
						{ success: function (user,resp) 
					       {
							//timeOute = Math.floor(Math.random()*10*2)*1000;
							resultsObj.set("resultsJson",resp);
							resultsObj.set("timeOut",timeOute/1000);
							var  resultsObjs = new resultsView({el:$("#results")});
						    } ,
						    error: function(model, error) {
						        console.log(model.toJSON());
						    }
				});
		   },timeOute);
		  
		}
	else{
		$("#logForm :input").attr("disabled","disabled");
		$("#Login").text("Voting begins on 2013 / 9 / 20");
		
	}
	$("#ctTimer").countdown({until:  new Date(2013, 9-1, 20),format: 'yowdHMS'}).removeClass("hasCountdown ");
	var options = {
			html:true,
			trigger:"hover",
			content:"Use CNTRL+mouse left button to select more that one event <br> or <br>simply drag using mouse left button"
	};
	$("#mainevents_frm").popover(options);
});


