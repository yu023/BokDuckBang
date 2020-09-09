<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp" flush="false" />
<link rel="stylesheet" href="assets/css/search-room-style.css" />

<div class="sub-container">
	<!--visual start -->
	<section class="map-wrapper">
		<div class="container">
			<div class="button mb25 cf mt05">
				<a class="keybg fr" href="${root}/write-my-room">방 등록하기</a>
			</div>
			<ul id="room-list">
			</ul>
		</div>
		<!--/.container-->
	</section>
	<!--visual end -->
</div>


<script src="assets/js/room/myRoomList.js"></script>
<jsp:include page="../include/footer.jsp" flush="false" />