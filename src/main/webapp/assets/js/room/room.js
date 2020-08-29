
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
	})
}

$(document).ready(function(){
	$('.room-detail-top').on('click', '.like', function() {
		var link = window.location.search;
		$(this).find('i').toggleClass("far fas");
		addLikes(link);
	})
})