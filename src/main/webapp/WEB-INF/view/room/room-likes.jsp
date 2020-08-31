<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp" flush="false" />
<link rel="stylesheet" href="assets/css/search-room-style.css" />

<div class="sub-container">
	<jsp:include page="../include/search-header.jsp" flush="false" />
	<!--visual start -->
	<section class="map-wrapper">
		<div class="container">
			<ul id="room-list">
			</ul>
		</div>
		<!--/.container-->
	</section>
	<!--visual end -->
</div>


<script src="assets/js/room/likesRoom.js"></script>
<jsp:include page="../include/footer.jsp" flush="false" />