function initMap(){

	/*****************************
		cluster
	************************************/
	//map data setting
	var dist = 5;
	var initAjax = {
		  	centerLat : myLatlng.lat,
		  	centerLng : myLatlng.lng,
		  	distance : dist
	  }
	var markers;
	var markerCluster;
	var image = 'assets/images/common/homeMarker.svg';
  
	//첫 화면 function 뿌려주기
	ajaxQuery(initAjax);

	//탭 첫번째 활성화
	$(".option-box .tab-box li").eq(1).find("input").prop("checked",true);

	//전체 레이아웃 높이 구성
	var header = '500px';
	$("#map").css({height : header});

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
	    icon: image
	  });


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
			url : "getCenterLatLng",
			method : "post",
			data : JSON.stringify(arr),
			contentType : "application/json"
		  }).done(function(){
		  	if(typeof room_lat == "string"){
			  	getFastRoot();
		  	}
		  	map.fitBounds (circle.getBounds ());
		  })
	}


	/*****************************
		location
	************************************/

	var fastRoot = [];
		
	function getFastRoot(){ //상세페이지에서 보여줄 예쩡
	
	$("#route-dl *").remove();
	
		$.ajax({
			 url: 'getFastRoot',
		      method: 'post',
		      data: JSON.stringify(companyLatlng),
		      dataType: 'json',
		      contentType : "application/json",
			  success : function(data) {
			  	
			  	var routeArr = data.route;
			  	
			  	for(var i = 0; i < routeArr.length; i++){
				  	$("#route-dl").append("<dd>" + routeArr[i] + "</dd>");
			  	}

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
		}) 
	}
	

}