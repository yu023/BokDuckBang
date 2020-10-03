<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp" flush="false" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<link rel="stylesheet" href="assets/css/room-detail-style.css" />
<link href="https://fonts.googleapis.com/css2?family=Permanent+Marker&display=swap" rel="stylesheet">
<%@ page import="bokduckbang.member.Member" %>
<%@ page import="bokduckbang.member.MemberLessee" %>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<% 
	Member member = (Member)session.getAttribute("member");
	if(null != member && member.getMember_type().equals("1")){
		MemberLessee memberLessee = (MemberLessee) session.getAttribute("memberInfo");
		request.setAttribute("member_phone", memberLessee.getMember_phone());
		request.setAttribute("member_name", memberLessee.getMember_name());
		request.setAttribute("room_lat", memberLessee.getMember_dest_lat());
		request.setAttribute("room_lng", memberLessee.getMember_dest_lng());
	}
%>
<div class="sub-container">
	<section class="container">
		<!-- testemonial Start -->
		<section class="room-detail-box">
			<div class="room-detail-top">
				<c:if test="${sessionScope.member ne null and sessionScope.member.member_type eq 1}">
					<c:if test="${roomLikes}">
						<div class="like">LIKE <i class="fas fa-heart"></i></div>
					</c:if>
					<c:if test="${!roomLikes}">
						<div class="like">LIKE <i class="far fa-heart"></i></div>
					</c:if>
				</c:if>
				<div class="owl-carousel owl-theme" id="room-gallery-carousel">
					<c:if test="${null ne roomUrl}">
						<c:forEach var="room" items="${roomUrl}">
							<div class="home1-testm item">
								<div class="home1-testm-single text-center">
									<div style="background-image: url(https://d1774jszgerdmk.cloudfront.net/1024/${room})" class="thumb"></div>
								</div><!--/.home1-testm-single-->
							</div><!--/.item-->
						</c:forEach>
					</c:if>
					<c:if test="${null ne roomImgList}">
						<c:forEach var="room" items="${roomImgList}">
							<div class="home1-testm item">
								<div class="home1-testm-single text-center">
									<div style="background-image: url('${room}')" class="thumb"></div>
								</div><!--/.home1-testm-single-->
							</div><!--/.item-->
						</c:forEach>
					</c:if>
					
				</div><!--/.visual-->
			</div><!--/.top-->
			<div class="room-detail-bottom count-box">
				<div id="reserveBtn" class="mt40 mb25 tar button">
					<a v-if="status == '' && callReserveBtn == true && '판매중지' != '${roomStatus}'" class="tac keybg" v-on:click="reserveRoom('${sessionScope.member.member_email}','${member_name}','${member_phone}','${room.room_author_email}','${room.room_number}','${room.room_title}');">예약하기</a>
					<a v-if="status == 'YN' && '판매중지' != '${roomStatus}'" class="tac bgGray cursorDefault" >예약대기중</a>
					<a v-if="status == 'Y' && '판매중지' != '${roomStatus}'" class="tac bgGray cursorDefault" >예약완료</a>
					<a v-if="status == 'N' && '판매중지' != '${roomStatus}'" class="tac bgGray cursorDefault" >예약거절</a>
				</div>
				<c:if test="${null != roomStatus}">
					<div class="button tar">
						<button class="tac bgGray cursorDefault" >${roomStatus}</button>
					</div>
				</c:if>
				<div class="detail-script table common-div-padding">
					<dl class="tableCell col-md-7 fn vm">
						<dt>${room.room_title}</dt>
						<dd>${room.room_content}</dd>
					</dl>
					<div class="tableCell col-md-5 fn vm">
						<div
							class="table about_images d-flex flex-row tac align-items-center justify-content-between flex-wrap">
							<div class="tableCell vm milestone d-flex flex-row align-items-start justify-content-start">
								<div class="milestone_content">
									<div class="milestone_counter counter bold keycolor" data-end-value="${room.room_hits}">0</div>
									<span class="milestone_title block">Visitors</span>
								</div>
							</div>
							<div class="tableCell vm milestone d-flex flex-row align-items-start justify-content-start">
								<div class="milestone_content">
									<div class="milestone_counter counter bold keycolor" data-end-value="${room.room_likes}">0</div>
									<span class="milestone_title block">Likes</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="detail-info common-div-padding">
					<h3 class="text-sub-title tac mb30">상세 정보</h3>
					<ul>
						<li class="table">
						<p class="tableCel w50 tal price-title">
							<c:if test="${room.room_selling_type eq '전세'}">
								${room.room_money_lease}
							</c:if>
							<c:if test="${room.room_selling_type eq '월세'}">
								${room.room_money_deposit} / ${room.room_money_monthly_rent}
							</c:if>
						</p>
							<p class="tableCell w50 tar">
								#${room.room_type_str} 
								<c:forEach var="keyword" items="${roomKeyword}" begin="1" end="3">
									#${keyword} 
								</c:forEach>
							</p>
						</li>
						<li class="etc-title">
							<c:if test="${room.room_service eq ''}">
								관리비 (항목 없음) - ${room.room_service_charge}
							</c:if>
							<c:if test="${room.room_service ne ''}">
								관리비 (${room.room_service}) - ${room.room_service_charge}
							</c:if>
							</li>
						<li class="etc-title">주차비 - ${room.room_park_charge}</li>
						<li>
							<table class="common-table w100">
								<colgroup>
									<col width="13%"/>
									<c:if test="${not empty room.room_floor_str}">
										<col width="15%"/>
									</c:if>
									<col width="14%"/>
									<col width="*"/>
									<col width="15%"/>
									<col width="14%"/>
									<col width="14%"/>
								</colgroup>
								<thead>
									<tr>
										<th>평수</th>
										<c:if test="${not empty room.room_floor_str}">
											<th>해당층 
												<c:if test="${not empty room.room_total_floor}">
													/ 건물층
												</c:if>
											</th>
										</c:if>
										<th>엘리베이터</th>
										<th>난방종류</th>
										<th>베란다 / 발코니</th>
										<th>전세자금대출</th>
										<th>입주가능일</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>${room.room_width}</td>
										<c:if test="${not empty room.room_floor_str}">
											<td>${room.room_floor_str} 
												<c:if test="${not empty room.room_total_floor}">
													/ ${room.room_total_floor}
												</c:if>
											</td>
										</c:if>
										<td>${room.room_elevator}</td>
										<td>${room.room_heating}</td>
										<td>${room.room_balcony}</td>
										<td>${room.room_loan}</td>
										<td>${room.room_move_in_date}</td>
									</tr>
								</tbody>
							</table>
						</li>
					</ul>
					<div class="row common-div-padding">
						<div class="col-md-4">
							<div class="card">
								<h3 class="text-sub-title tac mb20">옵션</h3>
								<ul>
									<c:if test="${null eq roomOption || fn:length(roomOption) > 0}">
										<c:forEach var="option" items="${roomOption}">
											<li>${option}</li>
										</c:forEach>
									</c:if>
									<c:if test="${null ne roomOption || fn:length(roomOption) eq 0}">
										<li>해당사항 없음</li>
									</c:if>
								</ul>
							</div>
						</div>
						<div class="col-md-8">
							<div id="map"></div>
							<c:if test="${sessionScope.member.member_type eq 1}">
								<dl class="mb00 mt30">
									<dt class="mb10">주소</dt>
									<dd class="mb20">${room.room_address}</dd>
									<dt class="mb10">가는 길</dt>
								</dl>
								<dl id="route-dl">
									<dd></dd>
								</dl>
							</c:if>
							<c:if test="${sessionScope.member.member_type eq 0}">
								<div class="tar mt30 sellingType">판매상태 : 
									<c:if test="${room.room_status eq 1}">
										<span>판매중</span>
									</c:if>
									<c:if test="${room.room_status eq 0}">
										<span class="colorred">판매중지</span>
									</c:if>
								</div>
								<div class="button tar mt20">
									<a class="bgGray tac" href="${root}/edit-my-room?roomNumber=${room.room_number}">방 수정하기</a>
									<a class="bgGray ml10 mr10 tac" onclick="javascript:deleteMyRoom(${room.room_number});">방 삭제하기</a>
									<c:if test="${room.room_status eq 1}">
										<a class="bgGray tac sellingBtn" onclick="javascript:stopSellingMyRoom(${room.room_number});">방 판매중지하기</a>
									</c:if>
									<c:if test="${room.room_status eq 0}">
										<a class="bgGray tac sellingBtn" onclick="javascript:stopSellingMyRoom(${room.room_number});">방 판매재개하기</a>
									</c:if>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
			
			<div>
				
			</div>
			

		</section><!--/.testimonial-->	
		<!-- testemonial End -->

	</section>
</div>
<div id="reserveTable" class="none"></div>

<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
<script
      src="https://maps.googleapis.com/maps/api/js?libraries=geometry&callback=initMap&key=${key}"
      defer
></script>
<script>
	var myLatlng = {lat: ${room.room_lat}, lng: ${room.room_lng}};
	var companyLatlng = {lat: 37.516349, lng: 127.019931};
	var memberChk = "${sessionScope.member.member_type}" ;
	var room_lat, room_lng, room_number;
	if(memberChk == 1){
		room_lat = "${room_lat}";
		companyLatlng.lat = "${room_lat}";
		companyLatlng.lng = "${room_lng}";
	}
	room_number = '${room.room_number}';
</script>

<script src="assets/js/webSocket/webSocket.js"></script>
<script src="assets/js/map/detail-room-map.js"></script>
<script src="assets/js/room/room.js"></script>

<script src="assets/plugin/greensock/TweenMax.min.js"></script>
<script src="assets/plugin/greensock/TimelineMax.min.js"></script>
<script src="assets/plugin/scrollmagic/ScrollMagic.min.js"></script>
<script src="assets/plugin/greensock/animation.gsap.min.js"></script>
<script src="assets/plugin/greensock/ScrollToPlugin.min.js"></script>

<script src="assets/js/scroll.js"></script>

<jsp:include page="../include/footer.jsp" flush="false" />