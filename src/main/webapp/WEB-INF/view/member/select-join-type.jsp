<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp" flush="false" />
<link rel="stylesheet" href="assets/css/search-common-style.css" />

<div class="sub-container">
	<!--visual start -->
	<section class="container inner-container">
		<h1 class="text-title mb100">회원가입</h1>
		<a class="col-md-6 tac" href="${root}/join-lessee">
			<i style="font-size: 100px; color: #4d4e54;" class="fas fa-user-friends inblock"></i>
			<span class="text-sub-title inblock mt15 w100">임차인</span>
		</a>
		<a class="col-md-6 tac" href="${root}/join-lessor">
			<i style="font-size: 100px; color: #4d4e54;" class="fas fa-user-tie inblock"></i>
			<span class="text-sub-title inblock mt15 w100">임대인</span>
		</a>
	</section>
	<!--visual end -->
</div>

<jsp:include page="../include/footer.jsp" flush="false" />