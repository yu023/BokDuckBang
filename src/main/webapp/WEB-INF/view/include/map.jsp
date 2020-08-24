<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

 <script>

 function initMap(){
	/*****************************
		cluster
	************************************/

	//map data setting
	var dist = 5;
	var myLatlng = {lat: 37.500625, lng: 127.036411};
	var initAjax = {
		  	centerLat : myLatlng.lat,
		  	centerLng : myLatlng.lng,
		  	distance : dist
	  }
	var markers;
	var markerCluster;
	var image = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
  
	//첫 화면 function 뿌려주기
	ajaxQuery(initAjax);

	//탭 첫번째 활성화
	$(".option-box .tab-box li").eq(1).find("input").prop("checked",true);

	//전체 레이아웃 높이 구성
	var height = $(window).height();
	var header = height - ($('.search-header-area').height() + 120);
	$("#map").css({height : header});
	$("#room-list").css({height : header});

	//지도 세팅
	var map = new google.maps.Map(document.getElementById('map'), {
	    zoom: 14,	
	    center: myLatlng,
		draggable: true
	  });

 	//반경 세팅
  	var circle = new google.maps.Circle({
      strokeColor: "#FF0000",
      strokeOpacity: 0.7,
      strokeWeight: 2,
      fillColor: "#FF0000",
      fillOpacity: 0.1,
      map,
      center: myLatlng,
      radius: ( dist * 1000 ) / 2
    });

    //마커 세팅
  	var marker = new google.maps.Marker({
	    position: myLatlng,
	    map: map,
	  });

    //맵 클릭시 반경과 클러스터 재검색
  	map.addListener('click', function(mapsMouseEvent) {
	  ajaxQuery(getLatLngFunc(mapsMouseEvent.latLng));
   	});

	//반경 내부의 맵 클릭시 반경과 클러스터 재검색
  	circle.addListener('click', function(mapsMouseEvent) {
	  ajaxQuery(getLatLngFunc(mapsMouseEvent.latLng));
  	 });

	//탭 클릭시 반경과 클러스터 재검색
	$(".option-box .tab-box li input[type='radio']").on("click",function(){
		dist = $(".option-box .tab-box li input[type='radio']:checked").val();

		circle.setRadius(( dist * 1000 ) / 2);
		initAjax.distance = dist;
		ajaxQuery(getLatLngFunc(map.getCenter()));
	})

	// Sets the map on all markers in the array.
	function setMapOnAll(map) {
		if(markers != null){
		  for (let i = 0; i < markers.length; i++) {
		    markers[i].setMap(map);
		  }
		}
	}

	// Removes the markers from the map, but keeps them in the array.
	function clearMarkers() {
	  setMapOnAll(null);
	}

	// Shows any markers currently in the array.
	function showMarkers() {
	  setMapOnAll(map);
	}

	// Deletes all markers in the array by removing references to them.
	function deleteMarkers() {
		if(markerCluster  != null){
			markerCluster.clearMarkers();
		}
	  markers = [];
	}

	//좌표 구하여 반환해주기
	function getLatLngFunc(latLng){

	  var latLngList = latLng.toString();
	  latLngList = latLngList.substring( 1, latLngList.length - 1 );
	  latLngList = latLngList.split(",");
	  
	  circle.setCenter(latLng);
	  marker.setPosition(latLng);
	  
	  map.setCenter(latLng);
	  var lat_lng = {
			  centerLat : latLngList[0],
			  	centerLng : latLngList[1],
			  	distance : dist
		  }
	  return lat_lng;
	}
  
	//좌표값 받아서 주변 매물 서치
	function ajaxQuery(arr){
		$.ajax({
			url : "${root}/getCenterLatLng",
			method : "post",
			data : JSON.stringify(arr),
			contentType : "application/json"
		  }).done(function(msg){
			  makeCluster(msg);
			  makeList(msg);
		  })
	}

	//ajax로 받아온 주변 매물 데이터 결과값으로 클러스터 생성
    function makeCluster(msg){

		deleteMarkers();
		var locations = [];
		
		for(var i = 0; i < msg.length; i++){
			locations[i] = {lat: msg[i].room_lat, lng: msg[i].room_lng}
		}


		markers = locations.map(function(location, i) {
	          return new google.maps.Marker({
	            position: location,
	            icon: image
	          });
	        });
        
	    markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
 
	} 

	//목적지와 가까운 거리순으로 방 목록 생성
	function makeList(msg){
		
		$("#room-list *").remove();

		var locations = [];
		var title;
		
		for(var i = 0; i < msg.length; i++){
			var imgUrlStr = msg[i].room_img_url;
			var imgUrlArr = imgUrlStr.split(",");
			
			if(msg[i].room_selling_type == '월세'){
				title = msg[i].room_money_deposit + " / " + msg[i]. room_money_monthly_rent;
			}else if(msg[i].room_selling_type == '전세'){
				title = msg[i].room_money_lease;
			}else{
				title = "기타";
			}

			var keywords = msg[i].room_keywords;
			var keywordArr = keywords.split(",");
			var keytitle = "";

			for(var j = 0; j < 5; j++){
				var key = keywordArr[j]
				keytitle += "<span>#" + keywordArr[j] + "</span> ";
			}
			
			$("#room-list").append('<li class="col-md-6"><a href="${root}/room-detail?num=' + msg[i].room_number + '"><div style="background-image: url(https://d1774jszgerdmk.cloudfront.net/1024/'+ imgUrlArr[0] + ')" class="thumb"></div><div class="li-wrap"><div class="table"><p class="tableCell">'+ title + '</p><span class="tableCell tar"><i onclick="like(this)" class="far fa-heart"></i></span></div><p>' + keytitle + '</p></div></a></li>');
		}
		
	}

	/*****************************
		location
	************************************/

	var fastRoot = [];
		
	function getFastRoot(){ //상세페이지에서 보여줄 예쩡
		/* $.ajax({
			 url: '${root}/getFastRoot',
		      method: 'post',
		      data: JSON.stringify(myLatlng),
		      dataType: 'json',
		      contentType : "application/json",
			  success : function(data) {

				var encoded_data = data.points;
				var data_length = encoded_data.length;
				encoded_data = encoded_data.substring(1, data_length-1);

				var decoded_latlngs = google.maps.geometry.encoding.decodePath(encoded_data);
				
				for (var i = 0; i < decoded_latlngs.length; i++) {
					fastRoot[i] = {lng : decoded_latlngs[i].lng(), lat: decoded_latlngs[i].lat()};
				} 
				
				var fastRootPolygon = new google.maps.Polyline({
					path: fastRoot,
			          geodesic: true,
			          strokeColor: "#00FFFF",
			          strokeOpacity: 1.0,
			          strokeWeight: 2
				});

				fastRootPolygon.setMap(map); 
		    },
	     	error:function(request,status,error){
	    	     console.log("code = "+ request.status + " message = " + request.responseText + " error = " + error);
	        },
	      	complete : function(data) {
                 console.log("complete")
	         }
		}) */
	}
		

}

    </script> 