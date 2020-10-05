function initMap(){
	
	/*****************************
		setting
	************************************/
	
	//반경 5km 활성화
	$(".option-box .tab-box li").eq(1).find("input").prop("checked",true);
	
	//전세 탭활성화
	$("#sellingType li").eq(0).find("input[type='checkbox']").prop("checked",true);
	$("#range-box li .deposit").hide();
	

	//전체 레이아웃 높이 구성
	var height = $(window).height();
	var header = $('.search-header-area').height() + 160;
	$("#map").css({height : height - header});
	$("#room-list-wrapper").css({height : height - header});
	$(".map-wrapper").css({height : height - header});
	

	//map data setting
	var dist = 5;
	var myLatlng = {lat: 37.500625, lng: 127.036411};
	
	if(room_lat != "" && room_lng != ""){
		myLatlng.lat = parseFloat(room_lat);
		myLatlng.lng = parseFloat(room_lng);
	}
	
	
	var markers;
	var markerCluster;
	var image = 'assets/images/common/selectedMarker.svg';
	var image2 = 'assets/images/common/selectedMarker2.svg';
			
	var id = "select1";
	var range = [];
		
	var KeywordArr;
	var keywordMsg;
		
	var roomNum;
	var enterPrevNum = -1;
	var clickPrevNum = -1;
	
	var uriParam = window.location.search;
	var uriPathname = window.location.pathname;
	
	//지도 세팅
	var map = new google.maps.Map(document.getElementById('map'), {
	    zoom: 14,	
	    center: myLatlng,
		draggable: true,
		minZoom: 8,
		maxZoom: 20
	  });
	
 	//반경 세팅
  	var circle = new google.maps.Circle({
      strokeColor: "#FF0000",
      strokeOpacity: 0.7,
      strokeWeight: 2,
      fillColor: "#FF0000",
      fillOpacity: 0.1,
      map : map,
      center: myLatlng,
      radius: ( dist * 1000 ) / 2
    });

    //마커 세팅
  	var marker = new google.maps.Marker({
	    position: myLatlng,
	    map: map,
	  });

	var roomFilters = {
			room_selling_type : member_like_room_type,
		  	centerLat : marker.getPosition().lat(),
			centerLng : marker.getPosition().lng(),
		  	distance : dist,
			keyword : KeywordArr,
			range : range
	  }

	//주소창에 필터링 키워드들 저장
	history.pushState(roomFilters, "BokDuckBang", uriPathname + "?" + encodeURIComponent(JSON.stringify(roomFilters)));
	
	//get 에 parameter 있으면 해당 조건 적용하여 검색
	if(uriParam != "" && typeof uriParam != "undefined" && uriParam != null){
		var decodeURI = decodeURIComponent(uriParam);
		decodeURI = decodeURI.substring(1,decodeURI.length);
		roomFilters = JSON.parse(decodeURI);
		myLatlng.lat = parseFloat(roomFilters.centerLat);
		myLatlng.lng = parseFloat(roomFilters.centerLng);
		reSetting(myLatlng);
		var keywordStr = roomFilters.keyword;
		
		if(keywordStr != "" && typeof keywordStr != "undefined" && keywordStr != null){
			$("#keywordInput").val(roomFilters.keyword);
			keyFunctionAjax(roomFilters);
		}else{
			keywordStr = "";
			keyFunctionAjax(roomFilters);
		}
		
		for(var i = 0; i < roomFilters.range.length; i++){
			$("#"+roomFilters.range[i]).prop("checked", true);
		}
		
		if("" != roomFilters.room_selling_type){
			$("input[name='select']").prop("checked", false);
			$("#" + roomFilters.room_selling_type).prop("checked", true);
		}else{
			roomFilters.room_selling_type = "select1";
		}
		
		if(roomFilters.hasOwnProperty('distance')){
			dist = roomFilters.distance;
			$(".option-box .tab-box li input[value='" + dist + "']").prop("checked", true);
			circleSetting(dist);
		}
		
		if(roomFilters.hasOwnProperty('region') && null != roomFilters.region){
			if(roomFilters.region.hasOwnProperty('keyword') && null != roomFilters.region.keyword){
				$("#seachInput").val(roomFilters.region.keyword);
			}
		}
		
	}else{
		if("" != roomFilters.room_selling_type){
			$("input[name='select']").prop("checked", false);
			$("#" + roomFilters.room_selling_type).prop("checked", true);
		}else{
			roomFilters.room_selling_type = "select1";
		}
		//첫 화면 param 없을 경우 function 뿌려주기
		keyFunctionAjax(roomFilters);
	}
	
	//첫 화면 function 뿌려주기
	modalBox();
	  
	/*****************************
		click event
	************************************/
	
	
    //맵 클릭시 반경과 클러스터 재검색
  	map.addListener('click', function(mapsMouseEvent) {
	  minMaxReset(roomFilters);
      markerReset();
	  searchRoom(resetPointsRoomFilters(mapsMouseEvent.latLng));
   	});

	//반경 내부의 맵 클릭시 반경과 클러스터 재검색
  	circle.addListener('click', function(mapsMouseEvent) {
	  minMaxReset(roomFilters);
      markerReset();
	  searchRoom(resetPointsRoomFilters(mapsMouseEvent.latLng));
  	 });

	//탭 클릭시 반경과 클러스터 재검색
	$(".option-box .tab-box li input[type='radio']").on("click",function(){
		dist = $(".option-box .tab-box li input[type='radio']:checked").val();
		circleSetting(dist);
	})
	
	function circleSetting(dist){
		circle.setRadius(( dist * 1000 ) / 2);
		minMaxReset(roomFilters);
		roomFilters.distance = dist;
		searchRoom(resetPointsRoomFilters(marker.getPosition()));
		map.fitBounds(circle.getBounds());
	}

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
	
	
	/*****************************
		return lat.lng 
	************************************/
	

	//좌표 구하여 반환해주기
	function resetPointsRoomFilters(latLng){

	  var latLngList = latLng.toString();
	  latLngList = latLngList.substring( 1, latLngList.length - 1 );
	  latLngList = latLngList.split(",");
	  
	  circle.setCenter(latLng);
	  marker.setPosition(latLng);
	  
	  map.setCenter(latLng);
	 
	  roomFilters.centerLat = latLngList[0];
	  roomFilters.centerLng = latLngList[1];
	  roomFilters.distance = dist;
	  
	  return roomFilters;
	}
	
		
	
	/*****************************
		ajax search
	************************************/
  
	//좌표값 받아서 주변 매물 서치
	function searchRoom(roomFilters){
		roomFilter(roomFilters);
		minmax(roomFilters);
	}
	
		  
	/*****************************
		cluster
	************************************/

	//ajax로 받아온 주변 매물 데이터 결과값으로 클러스터 생성
    function makeCluster(result){

		deleteMarkers();
		var locations = [];
		
		if(null != result){
			for(var i = 0; i < result.length; i++){
				locations[i] = {lat: result[i].room_lat, lng: result[i].room_lng}
			}
		}


		markers = locations.map(function(location, i) {
	        var etcMarker = new google.maps.Marker({
	            position: location,
	            icon: image
	          });
			google.maps.event.addListener(etcMarker, 'click', function(target) {
			    getRoomNumber(target.latLng, i);
	        });
	        return etcMarker;
		});
	        
	    
	    markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
		
		//marker의 position이 겹쳐 cluster의 최소 단위가 되었을 경우
		google.maps.event.addListener(markerCluster, 'clusterclick', function(cluster) {
			var clusterMarkerArr = cluster.markers_;
			var clusterLength = clusterMarkerArr.length;
			if(clusterLength < 10){
				for(var i = 0; i < clusterLength; i ++){
					if(i == clusterLength-1){
						if(clusterMarkerArr[i].position.toString() != clusterMarkerArr[0].position.toString()){
							return false;
						}
					}else{
						if(clusterMarkerArr[i].position.toString() != clusterMarkerArr[i+1].position.toString()){
							return false;
						}
					}
				}
				getRoomNumber(clusterMarkerArr[0].position, -1);
			}
		});
		
		markers = markerCluster.markers_;
		
	} 
		  
	/*****************************
		make list
	************************************/
		
	var roomList = new Vue({
	  el: '#room-list',
	  data: function() {
	    return {
	      myList: []
	    }
	  }
	})

	//목적지와 가까운 거리순으로 방 목록 생성
	function makeList(result,likeList){
		$("#room-list *").remove();
		var myListLength = roomList.myList.length;
		roomList.myList.splice(0,myListLength);

		enterPrevNum = -1;
	    clickPrevNum = -1;

		var locations = [];
		var title;
		
		var resultData = JSON.stringify(result);
		var likeListData = JSON.stringify(likeList);
		var totalResultData = resultData + likeListData;
		
		$.ajax({
			url : 'get-my-room-image1',
			method : 'post',
			data: totalResultData,
			contentType: 'application/json'
		}).done(function(result){

			for(var i = 0; i < result.length; i++){
				var myJson = result[i];
				var room = {
					room_number : myJson.room_number,
					room_favorit : 'none'
				}
				
				if(memberChk == "1"){
					room.room_favorit = false
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
		})
		
		
	}
			  
	/*****************************
		list mouse enter event
	************************************/

	var clickMarker = new google.maps.Marker({
						map: map,
			            icon: image2,
			            zIndex: 999999999
			          });

	function enterEvent(){
	
		$("#room-list li").mouseenter(function(){
		
			var href = $(this).find("a").attr("href");
			var hrefArr;
			if("undefined" != typeof href){
				hrefArr = href.split("num=");
					
				$.ajax({
				  url: 'room-position?num=' + hrefArr[1],
			      method: 'post',
			      dataType: 'json',
			      contentType : "application/json",
				  success : function(data) {
				  		var pin = {lat: data.myLat, lng: data.myLng};
				  		
					  	map.setCenter(pin);
					  	
						if(enterPrevNum != -1){
							markers[enterPrevNum].setIcon(image);
						}
					  	
					  	for(var i = 0; i < markers.length; i++){
						  	if(markers[i].getPosition().lat() == data.myLat && markers[i].getPosition().lng() == data.myLng){
						  		roomNum = i;
						  	}
					  	}
					  	markers[roomNum].setIcon(image);
						clickMarker.setPosition(pin);
				   },
				   error:function(request,status,error){
				    	console.log("code = "+ request.status + " message = " + request.responseText + " error = " + error);
				   }
				 })
			}
			 
		 }).mouseleave(function(){
				clickMarker.setPosition(null);
				map.setCenter(marker.getPosition());
		 })
	}	
	
	function getRoomNumber(targetPostion, i){
	
		var tpStr = targetPostion.toString();
		tpStr = tpStr.substring(1,tpStr.length-1);
		var points = tpStr.split(",");
		var data = {lat: points[0], lng: points[1]};
		
		$.ajax({
			url : 'get-room-number',
			method : 'post',
			data : JSON.stringify(data),
			contentType : "application/json"
		}).done(function(roomInfo){
		
			var target = [];
			var pin, top;
			var roomArr = roomInfo.room;

			if(roomArr.length == 1){
				target.push($(".room"+ roomArr[0].room_number));
				pin = {lat: roomArr[0].room_lat, lng: roomArr[0].room_lng};
				top = target[0].offset().top + $("#room-list").scrollTop() - header;
				markerFunction();
			}else if(roomArr.length > 1){
				for(var num = 0; num < roomArr.length; num++){
					target.push($(".room"+ roomArr[num].room_number));
					pin = {lat: roomArr[0].room_lat, lng: roomArr[0].room_lng};
					top = $(".room"+ roomArr[0].room_number).offset().top + $("#room-list").scrollTop() - header;
				}
				animateFunction();
			}
			
			function markerFunction(){
				if(clickPrevNum != -1){
					markers[clickPrevNum].setIcon(image);
				}
				
				clickMarker.setPosition(null);
				markers[i].setIcon(image2);
				clickPrevNum = i;

				map.setCenter(pin);
				animateFunction();
			}
			
			function animateFunction(){
				$.each(target, function (index, item) { 
					var itemLength = item.length;
					if(itemLength > 0){
						item.addClass("class2341324235");
					}
				})
				var liItem = $(".class2341324235");
				$("#room-list").stop().animate( { scrollTop : top }, 400,
					function() {
						liItem.stop().animate({
						    opacity: 0.25,
						  }, 250, function() {
						     $("#room-list li .list-li-wrapper").removeClass("border");
						  	 liItem.find('.list-li-wrapper').addClass("border");
						     liItem.animate({
							    opacity: 1,
							  },250)
						  });
					  	 liItem.removeClass("class2341324235");
					}
				) // end animate

			}//end animateFunction
		})
	}
	
	function setDelay(){
		setTimeout(function() {
		  	 map.fitBounds(circle.getBounds());
			 map.setCenter(marker.getPosition());
			 markers[roomNum].setIcon(null);
			 markers[roomNum].setIcon(image);
		}, 3000);
	}
	
		
	 
	/*****************************
		region search
	************************************/
	
	$("#seachBtn").on('click',function(){
		var region = {keyword : $(this).prev().val()};
		schFunction(region);
	})
	$("#seachInput").keyup(function(e){
		if(e.keyCode == 13){
			var region = {keyword : $(this).val()};
			schFunction(region);
		} 
	});
	
	function schFunction(region){
		roomFilters.region = region;
		minMaxReset(roomFilters);
		$.ajax({
			url : 'search',
			method : 'post',
			data : JSON.stringify(region),
			contentType : 'application/json'
		}).done(function(regionSearchResult){
		
			roomFilters.centerLat = regionSearchResult.points.centerLat;
			roomFilters.centerLng = regionSearchResult.points.centerLng;
			roomFilters.distance = dist;

			myLatlng.lat = roomFilters.centerLat;
			myLatlng.lng = roomFilters.centerLng;
			reSetting(myLatlng);
			
			searchRoom(roomFilters);
		})
	}
	
	
			  
	/*****************************
		keyword search
	************************************/
	
	
	$("#keywordBtn").on('click',function(){
		var Keywords = {keyword : $(this).prev().val()};
		keyFunction(Keywords);
	})
	$("#keywordInput").keyup(function(e){
		if(e.keyCode == 13){
			var Keywords = {keyword : $(this).val()};
			keyFunction(Keywords);
		} 
	})

	function keyFunction(Keywords){
		KeywordArr = Keywords.keyword;
		roomFilters.keyword = KeywordArr;
		keyFunctionAjax(roomFilters);
	}
	
	
	function keyFunctionAjax(roomFilters){
		if(typeof roomFilters.keyword == "undefined" || roomFilters.keyword == null){
			roomFilters.keyword = "";
		} 

		$.ajax({
			url : 'keyword',
			method : 'post',
			data : JSON.stringify(roomFilters),
			contentType : 'application/json'
		}).done(function(returnKeywords){
			
			keywordMsg = returnKeywords;
			keywordSetting(keywordMsg)
			
			searchRoom(roomFilters);
		})
	}
	
	function keywordSetting(keywordMsg){
		$("#keyword-box *").remove();
		var keywordMsgArr = "";

		if(typeof keywordMsg === "string"){
			if(keywordMsg.includes(",")){
				keywordMsgArr = keywordMsg.split(",");
			}else{
				keywordMsgArr = [keywordMsg];
			}
		}else if(typeof keywordMsg === "object"){
			keywordMsgArr = keywordMsg;
		}

		if(typeof keywordMsg != "undefined" && keywordMsgArr.length > 0){
			$("#keyword-box").prev().removeClass("none");
			$("#keyword-wrapper").removeClass("none");
			$(".search-header-area").removeClass("mb40");
			for(var i = 0; i < keywordMsgArr.length; i++){
				$("#keyword-box").append('<li class="inblock pl15"><p class="color222 inblock keywordDelBtn"><span class="pr05">'+keywordMsgArr[i]+'</span><img src="assets/images/common/close-icon.svg" alt="키워드 삭제"/></p></li>');
			}
		}else{
			$("#keyword-wrapper").addClass("none");
			$("#keyword-box").prev().addClass("none");
			$(".search-header-area").addClass("mb40");
		}
		
		keywordArr = keywordMsgArr;
	}
	
	$('#keyword-box').on('click', '.keywordDelBtn', function() {
		
		var delKeyword = $(this).find('span').text();
		
		for(var i = 0; i < keywordArr.length; i++){
			if(keywordArr[i] == delKeyword){
				keywordArr.splice(i,1);
			}
		}
		$(this).parent("li").remove();
		
		var keyFunctionArr = {keyword : keywordArr}
		keyFunction(keyFunctionArr);
		
	})
	
	
	/*****************************
		Modal,Tab
	************************************/
	
	
	$('#sellingType').on('click', '.sellingInput', function() {
		
		var sell = $(this).prop("id");
		modalBoxSetting();
		
	})
	
	function modalBox(){
		
		var mySelltype;
		
		if(member_like_room_type != ""){
			mySelltype = member_like_room_type;
		}else{
			mySelltype = roomFilters.room_selling_type
		}
		
		minMaxReset(roomFilters);
		modalBoxSetting();
	}
	
	function modalBoxSetting(){
		if(($('#select1').is(":checked") == true && $('#select2').is(":checked") == true) || ($('#select1').is(":checked") == false && $('#select2').is(":checked") == false)){
			$(".lease-box").addClass("none");
			$(".monthly-box").addClass("none");
		}else if($('#select1').is(":checked") == true){
			$(".monthly-box").addClass("none");
			$(".lease-box").removeClass("none");
		}else if($('#select2').is(":checked") == true){
			$(".monthly-box").removeClass("none");
			$(".lease-box").addClass("none");
		}
	}
	
	
	
	function minmax(roomFilters){
		roomFilters.type = 'none';
	
		$.ajax({
			url : "minmax",
			method: "post",
			data : JSON.stringify(roomFilters),
			contentType : "application/json"
		}).done(function(moneyRangeFilter){
			
			modalBox();
			
			var modal1 = $("#deposit-modal1");
			var modal2 = $("#deposit-modal2");
			
			if(moneyRangeFilter.maxRent > 0){
				modalDefault(modal2, moneyRangeFilter, false, roomFilters);
			}else{
				modalDefault(modal1, moneyRangeFilter, true, roomFilters);
			}
			
		}) //end done
		
	}
	
	function modalDefault(modal, moneyRangeFilter, type, roomFilters){
		if(type){
			var defaultModal = modal.find('.default-range');
			modalCommon(defaultModal, moneyRangeFilter.min, moneyRangeFilter.max, moneyRangeFilter.multi, roomFilters, 100, 'lease');
		}else{
			var defaultModal = modal.find('.default-range');
			modalCommon(defaultModal, moneyRangeFilter.min, moneyRangeFilter.max, moneyRangeFilter.multi, roomFilters, 50, 'deposit');
			var monthModal = modal.find('.monthly-range');
		   	modalCommon(monthModal, moneyRangeFilter.minRent, moneyRangeFilter.maxRent, moneyRangeFilter.rentMulti, roomFilters, 10, 'rent');
		}

	}
	
	function modalCommon(modal, modalMin, modalMax, modalMulti, roomFilters, stepNum, roomType){

		var rename = ['min' + roomType, 'max' + roomType];
		roomFilters.type = roomType;
		minMaxReset(roomFilters);
		
		var setMin, setMax;
					
		modal.find('ul li.min').text(modalMin);
		modal.find('ul li.multi').text(modalMulti);
		modal.find('ul li.max').text(modalMax);
		
		if(roomFilters[rename[0]] > 0){
			modal.find('.price-range-result .min').text(roomFilters[rename[0]]);
			setMin = roomFilters[rename[0]];
		}else{
			modal.find('.price-range-result .min').text(modalMin);
			setMin = modalMin;
		}
		
		if(roomFilters[rename[1]] > 0){
			modal.find('.price-range-result .max').text(roomFilters[rename[1]]);
			setMax = roomFilters[rename[1]];
		}else{
			modal.find('.price-range-result .max').text(modalMax);
			setMax = modalMax;
		}
	
		modal.find('.price-range-block input').on('change', function () {
		  var min_price_range = parseInt(modalMin);
		  var max_price_range = parseInt(modalMax);
		  if (min_price_range > max_price_range) {
			modal.find('.max_price').val(min_price_range);
		  }
	
		  modal.find('.slider-range').slider({
			values: [min_price_range, max_price_range]
		  });
		  
		});
		

		$(function () {
		
		  	var minValue = modalMin;
		  	var maxValue = modalMax;
		  
			  modal.find('.slider-range').slider({
				range: true,
				orientation: "horizontal",
				min: modalMin,
				max: modalMax,
				values: [setMin, setMax],
				step: stepNum,
				slide: function (event, ui) {
				  	if (ui.values[0] == ui.values[1]) {
					  return false;
					}
					if(ui.values[1] >= (modalMax - stepNum)){
					  ui.values[1] = modalMax;
					}
					if(ui.values[0] <= (modalMin + stepNum)){
					  ui.values[0] = modalMin;
					}
					
					roomFilters[rename[0]] = ui.values[0];
					roomFilters[rename[1]] = ui.values[1];
					
					minValue = roomFilters[rename[0]];
					maxValue = roomFilters[rename[1]];
					  
				    modal.find('.min_price').val(minValue);
				    modal.find('.max_price').val(maxValue);
				    modal.find('.price-range-result .min').text(minValue);
					modal.find('.price-range-result .max').text(maxValue);

				}
				
			   }); //end slider-range
			   
		  modal.find('.min_price').val(minValue);
		  modal.find('.max_price').val(maxValue);
		
	   }); //end function
		   
	  
	 }
   $('.slider-range').mouseup(function () {
		roomFilter(roomFilters);
  });
	
	/*****************************
		filter
	************************************/

		
	$('input[type=checkbox][name=select]').on("click",function() {
	
		id = $("#sellingType li input[type='checkbox']:checked").prop("id");

		if(($('#select1').is(":checked") == true && $('#select2').is(":checked") == true) || ($('#select1').is(":checked") == false && $('#select2').is(":checked") == false)){
			id = "all";
		}
		
	  	roomFilters.room_selling_type = id;
		roomFilter(roomFilters);
	  	minmax(roomFilters);
	})
	
	
	$('#range-box li input').on("click",function() {
		range.splice(0,range.length);
		$('#range-box li input:checked').each(function(){
			range.push($(this).prop("id"))
		})
		roomOrder(range);
	})
	
	function roomOrder(rangeArr){
		range = rangeArr;
		roomFilters.range = range;
		roomFilter(roomFilters);
	}

	function roomFilter(roomFilters){
	
		history.replaceState(roomFilters, "BokDuckBang", uriPathname + "?" +  encodeURIComponent(JSON.stringify(roomFilters)));
		var filters = history.state;
		$.ajax({
			url : "filter",
			method: "post",
			data : JSON.stringify(filters),
			contentType : "application/json"
		}).done(function(returnRooms){
			delete roomFilters['minVal'];

			var result = returnRooms.result;
			var likeList = returnRooms.likeList;
			
			if(null != result && result.length > 0){
				makeCluster(result);
				makeList(result,likeList);
				enterEvent();
			}else if(null == result || result.length == 0){
				$("#room-list *").remove();
				$("#room-list").append('<li>검색되는 결과가 없습니다.</li>');
				makeCluster(result);
				enterEvent();
			}
		})
	}
	
	/*****************************
		common
	************************************/
	
	function markerReset(){
		clickPrevNum = -1;
	    clickMarker.setPosition(null);
	}
	
	function reSetting(points){
		marker.setPosition(points);
		circle.setCenter(points);
		map.setCenter(points);
	}	
	
	function minMaxReset(filter){
		if(filter['minlease']){
			delete roomFilters.minlease 
		}
		if(filter['maxlease']){
			delete roomFilters.maxlease 
		}
		if(filter['mindeposit']){
			delete roomFilters.mindeposit 
		}
		if(filter['maxdeposit']){
			delete roomFilters.maxdeposit 
		}
		if(filter['minrent']){
			delete roomFilters.minrent 
		}
		if(filter['maxrent']){
			delete roomFilters.maxrent 
		}
	}
	

}