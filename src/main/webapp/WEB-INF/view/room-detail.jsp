<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="include/header.jsp" flush="false" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="assets/css/room-detail-style.css" />
<link href="https://fonts.googleapis.com/css2?family=Permanent+Marker&display=swap" rel="stylesheet">

<div class="sub-container">
	<section class="container">
		<!-- testemonial Start -->
		<section class="room-detail-box">
			<div class="room-detail-top">
				<div onclick="txtLike(this)" class="like">LIKE <i class="far fa-heart"></i></div>
				<div class="owl-carousel owl-theme" id="room-gallery-carousel">
					
					<c:forEach var="room" items="${roomUrl}">
						<div class="home1-testm item">
							<div class="home1-testm-single text-center">
								<div style="background-image: url(https://d1774jszgerdmk.cloudfront.net/1024/${room})" class="thumb"></div>
							</div><!--/.home1-testm-single-->
						</div><!--/.item-->
					</c:forEach>
					
				</div><!--/.visual-->
			</div><!--/.top-->
			
			<div class="room-detail-bottom count-box">
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
							<c:if test="${room.room_selling_type} == '전세'">
								${room.room_money_lease}
							</c:if>
							<c:if test="${room.room_selling_type} == '월세'">
								${room.room_money_deposit} / ${room.room_money_monthly_rent}
							</c:if>
						</p>
							<p class="tableCell w50 tar">
								<c:forEach var="keyword" items="${roomKeyword}" begin="1" end="3">
									#${keyword} 
								</c:forEach>
							</p>
						</li>
						<li class="etc-title">관리비 (${room.room_service}) - ${room.room_service_charge}</li>
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
									<c:forEach var="option" items="${roomOption}">
										<li>${option}</li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<div class="col-md-8">
							<div id="map">map에는 최소한 지하철역, 버스정류장이 들어가야 하고 추가적으로는 은행, 관공서, 편의점</div>
							<dl class="mb00 mt30">
								<dt class="mb10">주소</dt>
								<dd class="mb20">${room.room_address}</dd>
								<dt class="mb10">가는 길</dt>
							</dl>
							<dl id="route-dl">
								<dd>대중교통 1번 탑승 후 10분 어쩌고</dd>
							</dl>
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


<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
<script
      src="https://maps.googleapis.com/maps/api/js?libraries=geometry&callback=initMap&key=${key}"
      defer
></script>

<script>

	var myLatlng = {lat: ${room.room_lat}, lng: ${room.room_lng}};
	var companyLatlng = {lat: 37.516349, lng: 127.019931};
</script>

<script src="assets/js/map/detail-room-map.js"></script>

<script src="assets/plugin/greensock/TweenMax.min.js"></script>
<script src="assets/plugin/greensock/TimelineMax.min.js"></script>
<script src="assets/plugin/scrollmagic/ScrollMagic.min.js"></script>
<script src="assets/plugin/greensock/animation.gsap.min.js"></script>
<script src="assets/plugin/greensock/ScrollToPlugin.min.js"></script>

<script src="assets/js/scroll.js"></script>

<jsp:include page="include/footer.jsp" flush="false" />