<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp" flush="false" />
<script type="text/javascript" src="assets/editor/dist/js/service/HuskyEZCreator.js"></script>
<div class="sub-container">
	<!--visual start -->
	<section class="container inner-container">
		<h1 class="text-title text-sub-title">방 등록하기</h1>
		<form id="fileUpForm" class="basic-form" action="${root}/gogo" onsubmit="return(gogo())" method="post" enctype="multipart/form-data">
			<table>
				<tr class="notNull">
					<th>
						<label for="room_images">방 사진</label>
					</th>
					<td>
						<input class="input-default" type="file" name="room_image_input" multiple required="required"/>
					</td>
				</tr>
			</table>
			<p class="tar mt10 mb10" class="font-size: 0.8em; color: #ccc;">*는 필수 입력 값입니다.</p>
			<div class="cf mt20">
				<input class="fr btn-default submitBtn" type="submit" value="등록하기"/>
			</div>
		</form>
	</section>
	<!--visual end -->
</div>

<script src="assets/js/room/tester.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<jsp:include page="../include/footer.jsp" flush="false" />