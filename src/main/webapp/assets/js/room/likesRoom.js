$(document).ready(function(){

	var height = $(window).height();
	var header = height - ($('.search-header-area').height() + 120);
	$("#room-list").css({height : header});
		
	getLikesList();
})

function getLikesList(){
	$.ajax({
		url : 'set-room-likes',
		method : 'post',
		contentType : 'application/json'
	}).done(function(map){
		var list = map.roomList;
		if(typeof list == "undefined"){
			$("#room-list *").remove();
			$("#room-list").append('<li>찜한 목록이 없습니다.</li>');
		}else{
			makeList(map.roomList);
		}
	})
}


function like(elem){
	$(elem).toggleClass("far fas");
	var link = $(elem).parent(".tableCell").prev().find('a').attr("href");
	addLikes(link);
}
 

function addLikes(elem){
	var linkSplit = elem.split("num=");
	var number = {likes_room : linkSplit[1]};
	$.ajax({
		url : 'room-likes',
		method : 'post',
		data : JSON.stringify(number),
		contentType : 'application/json'
	}).done(function(){
		getLikesList();
	})
}

function makeList(result){
	
	$("#room-list *").remove();
	enterPrevNum = -1;
    clickPrevNum = -1;

	var locations = [];
	var title;
	
	for(var i = 0; i < result.length; i++){
		var imgUrlStr = result[i].room_img_url;
		var imgUrlArr = imgUrlStr.split(",");
		
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
		
		$("#room-list").append('<li class="col-md-3 room' + result[i].room_number + '"><div class="list-li-wrapper"><a class="block" href="room-detail?num=' + result[i].room_number + '"><div style="background-image: url(https://d1774jszgerdmk.cloudfront.net/1024/'+ imgUrlArr[0] + ')" class="thumb"></div></a><div class="li-wrap"><div class="table"><p class="tableCell"><a class="block" href="room-detail?num=' + result[i].room_number + '">'+ title + '</a></p><span class="tableCell tar"><i onclick="like(this)" class="fas fa-heart"></i></span></div><p><a class="block" href="room-detail?num=' + result[i].room_number + '">' + keytitle + '</a></p></div></div></li>');
	
	}
	
	
	
}