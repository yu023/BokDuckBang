function initMap(){

	
	/*****************************
		setting
	************************************/
	
	//반경 5km 활성화
	$(".option-box .tab-box li").eq(1).find("input").prop("checked",true);
	
	//전세 탭활성화
	$("#sellingType li").eq(0).find("input[type='checkbox']").prop("checked",true);
	$("#range-box li .deposit").hide();
	
	//필터 거리순 활성화
	$("#range-box li input[name='range']").eq(0).prop("checked",true);

	//전체 레이아웃 높이 구성
	var height = $(window).height();
	var header = $('.search-header-area').height() + 120;
	$("#map").css({height : height - header});
	$("#room-list").css({height : height - header});
	

	//map data setting
	var dist = 5;
	var myLatlng = {lat: 37.500625, lng: 127.036411};
	var markers;
	var markerCluster;
	var image = 'assets/images/common/selectedMarker.svg';
	var image2 = 'assets/images/common/selectedMarker2.svg';
			
	var id = "select1";
	var range = ["range1"];
		
	var KeywordArr;
	var keywordMsg;
		
	var roomNum;
	var enterPrevNum = -1;
	var clickPrevNum = -1;
	
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
      map,
      center: myLatlng,
      radius: ( dist * 1000 ) / 2
    });

    //마커 세팅
  	var marker = new google.maps.Marker({
	    position: myLatlng,
	    map: map,
	  });

	var initAjax = {
			room_selling_type : $("#sellingType li input[type='checkbox']:checked").prop("id"),
		  	centerLat : marker.getPosition().lat(),
			centerLng : marker.getPosition().lng(),
		  	distance : dist,
			keyword : KeywordArr,
			range : range
	  }
  
	//첫 화면 function 뿌려주기
	ajaxQuery(initAjax);
	  
	  
	/*****************************
		click event
	************************************/
	
	
    //맵 클릭시 반경과 클러스터 재검색
  	map.addListener('click', function(mapsMouseEvent) {
      markerReset();
	  ajaxQuery(getLatLngFunc(mapsMouseEvent.latLng));
   	});

	//반경 내부의 맵 클릭시 반경과 클러스터 재검색
  	circle.addListener('click', function(mapsMouseEvent) {
      markerReset();
	  ajaxQuery(getLatLngFunc(mapsMouseEvent.latLng));
  	 });

	//탭 클릭시 반경과 클러스터 재검색
	$(".option-box .tab-box li input[type='radio']").on("click",function(){
	
		dist = $(".option-box .tab-box li input[type='radio']:checked").val();

		circle.setRadius(( dist * 1000 ) / 2);
		initAjax.distance = dist;
		ajaxQuery(getLatLngFunc(marker.getPosition()));
		map.fitBounds (circle.getBounds());

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
	
	
	/*****************************
		return lat.lng 
	************************************/
	

	//좌표 구하여 반환해주기
	function getLatLngFunc(latLng){

	  var latLngList = latLng.toString();
	  latLngList = latLngList.substring( 1, latLngList.length - 1 );
	  latLngList = latLngList.split(",");
	  
	  circle.setCenter(latLng);
	  marker.setPosition(latLng);
	  
	  map.setCenter(latLng);
	 
	  initAjax.centerLat = latLngList[0];
	  initAjax.centerLng = latLngList[1];
	  initAjax.distance = dist;
	 
	  return initAjax;
	}
	
		
	
	/*****************************
		ajax search
	************************************/
  
	//좌표값 받아서 주변 매물 서치
	function ajaxQuery(arr){
		$.ajax({
			url : "getCenterLatLng",
			method : "post",
			data : JSON.stringify(arr),
			contentType : "application/json"
		  }).done(function(msg){
			  roomFilter(arr);
			  minmax(arr);
		  })
	}
	
		  
	/*****************************
		cluster
	************************************/

	//ajax로 받아온 주변 매물 데이터 결과값으로 클러스터 생성
    function makeCluster(msg){

		deleteMarkers();
		var locations = [];
		
		for(var i = 0; i < msg.length; i++){
			locations[i] = {lat: msg[i].room_lat, lng: msg[i].room_lng}
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

	//목적지와 가까운 거리순으로 방 목록 생성
	function makeList(msg){
		
		$("#room-list *").remove();
		enterPrevNum = -1;
	    clickPrevNum = -1;

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
			
				var key = keywordArr[j];
				
				if(typeof key != "undefined"){
					var idx = key.indexOf("\/");
					if(idx != -1){
						key[j].replace("\/", " · ")
					}
					keytitle += "<span>#" + key + "</span> ";
				}
			}
			$("#room-list").append('<li class="col-md-6 room' + msg[i].room_number + '"><div class="list-li-wrapper"><a class="block" href="room-detail?num=' + msg[i].room_number + '"><div style="background-image: url(https://d1774jszgerdmk.cloudfront.net/1024/'+ imgUrlArr[0] + ')" class="thumb"></div></a><div class="li-wrap"><div class="table"><p class="tableCell"><a class="block" href="room-detail?num=' + msg[i].room_number + '">'+ title + '</a></p><span class="tableCell tar"><i onclick="like(this)" class="far fa-heart"></i></span></div><p><a class="block" href="room-detail?num=' + msg[i].room_number + '">' + keytitle + '</a></p></div></div></li>');
		}
		
		
		
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
			var hrefArr = href.split("num=");
				
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
			 
		 }).mouseleave(function(){
				clickMarker.setPosition(null);
				map.setCenter(marker.getPosition());
		 })
	}	
	
	function getRoomNumber(targetPostion, i){
	
		var str = targetPostion.toString();
		str = str.substring(1,str.length-1);
		var points = str.split(",");
		var data = {lat: points[0], lng: points[1]};
		$.ajax({
			url : 'get-room-number',
			method : 'post',
			data : JSON.stringify(data),
			contentType : "application/json"
		}).done(function(msg){
		
			var target = [];
			var pin, top;
			var roomArr = msg.room;

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
		  	 map.fitBounds (circle.getBounds());
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
		minMaxReset(initAjax);
		$.ajax({
			url : 'search',
			method : 'post',
			data : JSON.stringify(region),
			contentType : 'application/json'
		}).done(function(msg){
		
			initAjax.centerLat = msg.points.centerLat;
			initAjax.centerLng = msg.points.centerLng;
			initAjax.distance = dist;

			myLatlng.lat = initAjax.centerLat;
			myLatlng.lng = initAjax.centerLng;
			reSetting(myLatlng);
			
			ajaxQuery(initAjax);
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

	function keyFunction(arr){
		KeywordArr = arr.keyword;
		initAjax.keyword = KeywordArr;
		keyFunctionAjax(initAjax);
	}
	
	
	function keyFunctionAjax(initAjax){
		$.ajax({
			url : 'keyword',
			method : 'post',
			data : JSON.stringify(initAjax),
			contentType : 'application/json'
		}).done(function(msg){
			
			keywordMsg = msg;
			
			$("#keyword-box *").remove();
			if(msg.length > 0){
				$("#keyword-box").prev().removeClass("none");
				$("#keyword-wrapper").removeClass("none");
				$(".search-header-area").removeClass("mb40");
				for(var i = 0; i < msg.length; i++){
					$("#keyword-box").append('<li class="inblock pl15"><p class="color222 inblock keywordDelBtn"><span class="pr05">'+msg[i]+'</span><img src="assets/images/common/close-icon.svg" alt="키워드 삭제"/></p></li>');
				}
			}else{
				$("#keyword-wrapper").addClass("none");
				$("#keyword-box").prev().addClass("none");
				$(".search-header-area").addClass("mb40");
			}
			
			roomFilter(initAjax);
		})
	}
	
	$('#keyword-box').on('click', '.keywordDelBtn', function() {
		
		var delKeyword = $(this).find('span').text();
		
		for(var i = 0; i < keywordMsg.length; i++){
			if(keywordMsg[i] == delKeyword){
				keywordMsg.splice(i,1);
			}
		}
		$(this).parent("li").remove();
		
		var keyFunctionArr = {keyword : keywordMsg}
		keyFunction(keyFunctionArr);
		
	})
	
	
	/*****************************
		Modal,Tab
	************************************/
	
	
	$('#sellingType').on('click', '.sellingInput', function() {
		
		var sell = $(this).prop("id");
		
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
		
	})
	
	
	
	function minmax(filter){
		filter.type = 'none';
		console.log('welcome');
		console.log(filter);
		$.ajax({
			url : "minmax",
			method: "post",
			data : JSON.stringify(filter),
			contentType : "application/json"
		}).done(function(msg){
		
			var modal1 = $("#deposit-modal1");
			var modal2 = $("#deposit-modal2");
			
			if(msg.maxRent > 0){
				modalDefault(modal2, msg, false, filter);
			}else{
				modalDefault(modal1, msg, true, filter);
			}
			
		}) //end done
	}
	
	function modalDefault(modal, msg, type, filter){
		if(type){
			var defaultModal = modal.find('.default-range');
			modalCommon(defaultModal, msg.min, msg.max, msg.multi, filter, 100, 'lease');
		}else{
			var defaultModal = modal.find('.default-range');
			modalCommon(defaultModal, msg.min, msg.max, msg.multi, filter, 50, 'deposit');
			var monthModal = modal.find('.monthly-range');
		   	modalCommon(monthModal, msg.minRent, msg.maxRent, msg.rentMulti, filter, 10, 'rent');
		}

	}
	
	function modalCommon(modal, modalMin, modalMax, modalMulti, filter, stepNum, roomType){
		
		modal.find('ul li.min').text(modalMin);
		modal.find('ul li.multi').text(modalMulti);
		modal.find('ul li.max').text(modalMax);
		modal.find('.price-range-result .min').text(modalMin);
		modal.find('.price-range-result .max').text(modalMax);
	
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
				values: [modalMin, modalMax],
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
					
					var rename = ['min' + roomType, 'max' + roomType];
					filter.type = roomType;
					
					filter[rename[0]] = ui.values[0];
					filter[rename[1]] = ui.values[1];
					
					minValue = filter[rename[0]];
					maxValue = filter[rename[1]];
					  
				    modal.find('.min_price').val(minValue);
				    modal.find('.max_price').val(maxValue);
				    modal.find('.price-range-result .min').text(minValue);
					modal.find('.price-range-result .max').text(maxValue);

					roomFilter(filter);
				}
			   }); //end slider-range
		  
		  modal.find('.min_price').val(minValue);
		  modal.find('.max_price').val(maxValue);
		
	   }); //end function
	   
	 }
	
	/*****************************
		filter
	************************************/

		
	$('input[type=checkbox][name=select]').on("click",function() {
	
		id = $("#sellingType li input[type='checkbox']:checked").prop("id");

		if(($('#select1').is(":checked") == true && $('#select2').is(":checked") == true) || ($('#select1').is(":checked") == false && $('#select2').is(":checked") == false)){
			id = "all";
		}
		
	  	initAjax.room_selling_type = id;
		roomFilter(initAjax);
	  	minmax(initAjax);
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
		initAjax.range = range;
		roomFilter(initAjax);
	}

	function roomFilter(filter){
		$.ajax({
			url : "filter",
			method: "post",
			data : JSON.stringify(filter),
			contentType : "application/json"
		}).done(function(msg){
			delete initAjax['minVal'];
			var result = msg.result;
			console.log(result.length);
			if(result.length > 0){
				makeCluster(result);
				makeList(result);
				enterEvent();
			}else if(result.length == 0){
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
			delete filter.minlease 
		}
		if(filter['maxlease']){
			delete filter.maxlease 
		}
		if(filter['mindeposit']){
			delete filter.mindeposit 
		}
		if(filter['maxdeposit']){
			delete filter.maxdeposit 
		}
		if(filter['minrent']){
			delete filter.minrent 
		}
		if(filter['maxrent']){
			delete filter.maxrent 
		}
	}

}