let client;

$(document).ready(function(){
	initSet();
})

function initSet(){
	client = new WebSocket('ws://localhost:8080/BokDuckBang/room-reserve/websocket');
	reserveBtn.callReserveBtn = false;
	reserveTable.callReserve = false;
	if('' != room_number && '' != memberChk && '1' == memberChk){
		var str = 'lessee:' + room_number;
		client.onopen = function (event) {
		  client.send('str');
		};
		reserveBtn.callReserveBtn = true;
		webSocketProceed('lessee');
	}else{
		$("#").addClass("none");
	}
	if('' != memberChk && '0' == memberChk){
		client.onopen = function (event) {
		  client.send('lessor');
		};
		webSocketProceed('lessor');
	}
}

var reserveBtn = new Vue({
  el: '#reserveBtn',
  data: function() {
    return {
      callReserveBtn: false,
      status: ""
    }
  },
  methods: {
	    reserveRoom: function (member_email, member_name, member_phone, room_author_email, room_number, room_title) {
	      var myJson = {'val':'null','member_email':member_email,'member_name':member_name,'member_phone':member_phone,'room_author_email':room_author_email,'room_number':room_number,'room_title':room_title};
	      client.send(JSON.stringify(myJson));
	      webSocketProceed('firstReserve');
	    }
  }
})

var reserveTable = new Vue({
  el: '#reserveTable',
  data: function() {
    return {
      callReserve: false,
      reserves: []
    }
  },
  methods: {
	    sayYes : function(reserve_num, member_email){
		    var str = {reserve_num : reserve_num, member_email : member_email, val : true};
	    	var strJson = JSON.stringify(str);
	    	client.send(strJson);
		    webSocketProceed('all');
	    },
	    sayNo : function(reserve_num, member_email){
	    	var str = {reserve_num : reserve_num, member_email : member_email, val : false};
	    	var strJson = JSON.stringify(str);
	    	client.send(strJson);
		    webSocketProceed('all');
	    }
  }
})

function webSocketProceed(type){
    client.onmessage = function (event) {
    	if('' != event.data){
	    	var result = stringJsonParser(event.data);
	    	
	    	if('firstReserve' == type){
	    		var myJson = result[0];
	    		reserveBtnStatus(myJson);
	    		pushReserveTable(result);
	    	}
	    	
	    	if('' != memberChk && 'all' == type){
	    		var myJson = result[0];
	    		reserveBtnStatus(myJson);
	    		for(var i = 0; i < reserveTable.reserves.length; i ++){
	    			if(reserveTable.reserves[i].reserve_num == myJson.reserve_num){
	    				reserveTable.reserves[i].reserve_status = myJson.reserve_status;
	    			}
	    		}
	    	}
	
	    	if("" != result && 'lessee' == type){
	    		var lesseeResult = result[0];
	    		reserveBtnStatus(lesseeResult);
	    	}
	
	    	if("" != result && 'lessor' == type){
	    		pushReserveTable(result);
	    	}
    	}
    	
    }
}

function stringJsonParser(str){
	var jsonList = [];
	if(-1 != str.search("/")){
		var strArr = str.split("/");
		strArr = strArr.splice(1, strArr.length);
		for(var i = 0; i < strArr.length; i++){
			var myStr = "{" + strArr[i] + "}";
			var myJson =  JSON.parse(myStr);
			jsonList.push(myJson);
		}
	}else{
		var myJson = JSON.parse(str);
		jsonList.push(myJson)
	}
	return jsonList;
}

function reserveBtnStatus(lesseeResult){
	reserveBtn.status = lesseeResult.reserve_status;
}

function pushReserveTable(result){
	for(var i = (result.length-1); i >= 0 ; i--){
		var lessorResult = result[i];
    	var reserveTableData = {
	    	reserve_num : lessorResult.reserve_num,
	    	member_email : lessorResult.reserve_member_email,
	    	room_number : lessorResult.reserve_room_number,
	    	member_name : lessorResult.reserve_member_name,
	    	member_phone : lessorResult.reserve_member_phone,
	    	room_title : lessorResult.reserve_room_title,
	    	reserve_status : lessorResult.reserve_status,
	    	room_href : '/BokDuckBang/room-detail?num=' + lessorResult.reserve_room_number
    	}
    	reserveTable.callReserve = true;
    	reserveTable.reserves.push(reserveTableData);
	}
}




