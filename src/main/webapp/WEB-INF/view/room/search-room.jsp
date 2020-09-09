<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="bokduckbang.member.Member" %>
<%@ page import="bokduckbang.member.MemberLessee" %>
<% 
	Member member = (Member)session.getAttribute("member");
	if(null != member && member.getMember_type().equals("1")){
		MemberLessee memberLessee = (MemberLessee) session.getAttribute("memberInfo");
		request.setAttribute("room_lat", memberLessee.getMember_dest_lat());
		request.setAttribute("room_lng", memberLessee.getMember_dest_lng());
	}
%>
<jsp:include page="../include/header.jsp" flush="false" />
<link rel="stylesheet" href="assets/css/search-room-style.css" />

<div class="sub-container">
	<jsp:include page="../include/search-header.jsp" flush="false" />
	<!--visual start -->
	<section class="map-wrapper">
		<div class="container">
			<div class="row">
				<div class="col-md-7 rel pl00 pr00" id="map-wrapper">
					<div class="option-box">
						<ul class="tab-box">
							<li>
								<input name="distace" id="distace1" value="3" type="radio"/>
								<label for="distace1">3km</label>
							</li>
							<li>
								<input name="distace" id="distace2" value="5" type="radio"/>
								<label for="distace2">5km</label>
							</li>
							<li>
								<input name="distace" id="distace3" value="10" type="radio"/>
								<label for="distace3">10km</label>
							</li>
						</ul>
					</div>
					<div class="w100" id="map">
					</div>
				</div>
				<div class="col-md-5">
					<ul class="row" id="room-list">
						
					</ul>
				</div>
			</div>
		</div>
		<!--/.container-->
	</section>
	<!--visual end -->
</div>
<script>
	var memberChk = "${sessionScope.member.member_type}" ;
	var room_lat = "",
		room_lng = "";

	if(memberChk == 1){
		room_lat = "${room_lat}";
		room_lng = "${room_lng}";
	}
</script>

<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
<script
      src="https://maps.googleapis.com/maps/api/js?libraries=geometry&callback=initMap&key=${key}"
      defer
></script>
<script src="https://unpkg.com/@google/markerclustererplus@4.0.1/dist/markerclustererplus.min.js"></script>
<script src="assets/js/room/room.js"></script>
<script src="assets/js/map/sch-map.js"></script>
<script src="assets/plugin/scrollmagic/ScrollMagic.min.js"></script>
<jsp:include page="../include/footer.jsp" flush="false" />