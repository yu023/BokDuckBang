
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
	}).done(function(msg){
		if(msg){
			
		}
	})
}

function stopSellingMyRoom(number){
	$.ajax({
		url : 'stop-selling-my-room?num=' + number,
		method : 'post',
		contentType : 'application/json'
	}).done(function(room){
		if(null != room){
			console.log(room.room_status)
			if(room.room_status == 0){
				$(".sellingType span").text("판매중지").addClass("colorred");
				$(".sellingBtn").text("방 판매재개하기");
			}else if(room.room_status == 1){
				$(".sellingType span").text("판매중").removeClass("colorred");
				$(".sellingBtn").text("방 판매중지하기");
			}
		}
	})
}

function deleteMyRoom(number){
	if (confirm('방을 삭제하시겠습니까?')) {
		$.ajax({
			url : 'room-delete?num=' + number,
			method : 'post',
			contentType : 'application/json'
		}).done(function(result){
			if(result){
				window.location.href = "my-room-list";
			}else{
				alert("방 삭제에 실패하였습니다.");
			}
		})
	}
}

function reserveRoom(number){
	$.ajax({
		url : 'room-reserve?number=' + number,
		method : 'post'
	}).done(function(){
	})
}

$(document).ready(function(){
	$('.room-detail-top').on('click', '.like', function() {
		var link = window.location.search;
		$(this).find('i').toggleClass("far fas");
		addLikes(link);
	})
})