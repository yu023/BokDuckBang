<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp" flush="false" />
<link rel="stylesheet" href="assets/css/search-room-style.css" />
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<div class="sub-container">
	<!--visual start -->
	<section class="">
		<div class="container">
			<table id="reserveTable" class="common-table w100">
				<template v-if="callReserve">
					<colgroup>
						<col width="*"/>
						<col width="250px"/>
					</colgroup>
					<thead>
						<tr>
							<th>예약내용</th>
							<th>예약상태</th>
						</tr>
					</thead>
					<tbody>
					<tr v-for="(reserve, index) in reserves.slice().reverse()">
							<td>
								<a class="tal block pl10" v-bind:href="reserve.room_href">
									{{index}} / {{reserve.member_name}} /  {{reserve.member_phone}} /  {{reserve.room_title}}
								</a>
							</td>
							<td>
								<div class="button answer">
									<a v-if="reserve.reserve_status == 'YN'" v-on:click="sayYes(reserve.reserve_num, reserve.member_email)" class="keybg">예약 승인</a>
									<a v-if="reserve.reserve_status == 'YN'" v-on:click="sayNo(reserve.reserve_num, reserve.member_email)" class="bgGray">예약 거절</a>
									<a v-if="reserve.reserve_status == 'Y'" class="keybg cursorDefault">예약 완료</a>
									<a v-if="reserve.reserve_status == 'N'" class="bgGray cursorDefault">예약 거절</a>
								</div>
							</td>
						</tr>
					</tbody>
				</template>
				<template v-else>
					<colgroup>
						<col width="*"/>
					</colgroup>
					<thead>
						<tr>
							<th>예약내용</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>예약 회원이 없습니다.</td>
						</tr>
					</tbody>
				</template>
			</table>
		</div>
		<!--/.container-->
	</section>
	<!--visual end -->
</div>
<div id="reserveBtn" class="none"></div>
<script>
	var room_number;
	var memberChk = "${sessionScope.member.member_type}" ;
</script>
		
<c:if test="${sessionScope.member.member_type ne null}">
	<script src="assets/js/webSocket/webSocket.js"></script>
</c:if>
<jsp:include page="../include/footer.jsp" flush="false" />