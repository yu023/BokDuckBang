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
		
var roomList = new Vue({
  el: '#room-list',
  data: function() {
    return {
      myList: []
    }
  }
})

function makeList(result){
	
	$("#room-list *").remove();
	enterPrevNum = -1;
    clickPrevNum = -1;

	var locations = [];
	var title;
	
	for(var i = 0; i < result.length; i++){
		var myJson = result[i];
		var room = {
			room_number : myJson.room_number,
			room_favorit : true
		}
		
		if(myJson.hasOwnProperty('favorite')){
			room.room_favorit = myJson.favorite;
		}
		
		if(myJson.hasOwnProperty('room_img')){
			room.img = myJson.room_img;
		}else{
			var image = myJson.room_img_url;
			if(-1 != image.indexOf(",")){
				var images = image.split(",");
				room.img = "https://d1774jszgerdmk.cloudfront.net/1024/" + images[0];
			}else{
				room.img = "https://d1774jszgerdmk.cloudfront.net/1024/" + image;
			}
		}
		
		if(myJson.room_selling_type == '월세'){
			room.room_title = myJson.room_money_deposit + " / " + myJson.room_money_monthly_rent;
		}else if(myJson.room_selling_type == '전세'){
			room.room_title = myJson.room_money_lease;
		}
		
		var keywords = myJson.room_keywords;
		var keywordArr = keywords.split(",");
		var keytitle = [];
		
		for(var j = 0; j < 5; j++){
			var key = keywordArr[j];
			if(typeof key != "undefined"){
				var idx = key.indexOf("\/");
				if(idx != -1){
					key[j].replace("\/", " · ")
				}
				keytitle.push("#" + key);
			}
		}
		
		room.room_keytitle = keytitle;
		roomList.myList.push(room);
	}
	
	
	
}