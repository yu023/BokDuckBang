$(document).ready(function(){

	var height = $(window).height();
	var header = height - ($('.search-header-area').height() + 120);
	$("#room-list").css({height : header});
		
	getMyRoomList();
})

function getMyRoomList(){
	$.ajax({
		url : 'set-my-room',
		method : 'post',
		contentType : 'application/json'
	}).done(function(map){
		var list = map.roomList;
		if(typeof list == "undefined"){
			$("#room-list *").remove();
			$("#room-list").append('<li>내가 등록한 방이 없습니다.</li>');
		}else{
			makeList(map);
		}
	})
}


function makeList(map){
	
	var result = map.roomList;
	var imgResult = map.roomImgList;
	$("#room-list *").remove();
	enterPrevNum = -1;
    clickPrevNum = -1;
    
	var locations = [];
	var myNum = [];
	var roomList = {};
	var title;
	var defaultNum = 0;
	
	imgSearchCancelLoops:
	for(var i = 0; i < result.length; i++){
		
		if(result[i].room_selling_type == '월세'){
			title = result[i].room_money_deposit + " / " + result[i]. room_money_monthly_rent;
		}else if(result[i].room_selling_type == '전세'){
			title = result[i].room_money_lease;
		}else{
			title = "기타";
		}

		var keywords = result[i].room_keywords;
		var keywordArr = keywords.split(",");
		var keytitle = "";

		for(var j = 0; j < 5; j++){
		
			var key = keywordArr[j];
			
			if(typeof key != "undefined"){
				var idx = key.indexOf("\/");
				if(idx != -1){
					key[j].replace("\/", " · ")
				}
				keytitle += "<span>#" + key + "</span> ";
			}
		}
		
		var imgUrlStr = result[i].room_img_url;
		var imgUrlArr;
		if(typeof imgUrlStr != "undefined" && imgUrlStr != null){
			imgUrlArr = imgUrlStr.split(",");
			$("#room-list").append('<li class="col-md-3 room' + result[i].room_number + '"><div class="list-li-wrapper"><a class="block" href="room-detail?num=' + result[i].room_number + '"><div style="background-image: url(https://d1774jszgerdmk.cloudfront.net/1024/'+ imgUrlArr[0] + ')" class="thumb"></div></a><div class="li-wrap"><div class="table"><p class="tableCell"><a class="block" href="room-detail?num=' + result[i].room_number + '">'+ title + '</a></p></div><p><a class="block" href="room-detail?num=' + result[i].room_number + '">' + keytitle + '</a></p></div></div></li>');
		}else{
			if("undefined" != typeof imgResult){
				for(var j = 0; j < imgResult.length; j++){
					myNum.push(imgResult[j].room_img_number);
					roomList[myNum[j]] = imgResult[j].room_img;
				}
			}
			var setArr = Array.from(new Set(myNum));
			$("#room-list").append('<li class="col-md-3 room' + setArr[defaultNum] + '"><div class="list-li-wrapper"><a class="block" href="room-detail?num=' + setArr[defaultNum] + '"><div class="thumb"><img src="' + roomList[setArr[defaultNum]] + '"/></div></a><div class="li-wrap"><div class="table"><p class="tableCell"><a class="block" href="room-detail?num=' + setArr[defaultNum] + '">'+ title + '</a></p></div><p><a class="block" href="room-detail?num=' + setArr[defaultNum] + '">' + keytitle + '</a></p></div></div></li>');
			defaultNum ++;
		}
		
	
	}
	
	
	
}