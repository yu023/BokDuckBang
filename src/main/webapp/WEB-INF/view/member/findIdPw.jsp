<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp" flush="false" />

<div class="sub-container">
	<!--visual start -->
	<section class="container inner-container">
		<h1 class="text-title text-sub-title mb25">이메일 / 비밀번호 찾기</h1>
		<ul class="tab-box tab-box-border mb20 mt40">
			<li class="col-md-6 active"><a href="javascript:">이메일 찾기</a></li>
			<li class="col-md-6"><a href="javascript:">비밀번호 찾기</a></li>
		</ul>
		<table class="typeForm basic-form w100">
			<tr>
				<th><label>가입분류</label></th>
				<td>
					<input checked="checked" style="width: auto;" class="sellingInput" name="member_type" id="1" value="1" type="radio"/>
					<label for="1">임차인</label>
					<input style="width: auto;" class="sellingInput ml20" name="member_type" id="0" value="0" type="radio"/>
					<label for="0">임대인</label>
				</td>
			</tr>
		</table>
		<ul class="tab-content">
			<li class="rel">
				<table class="emailForm basic-form w100">
					<tr>
						<th><label for="member_name">이름</label></th>
						<td><input  class="input-default btnInput" type="text" name="member_name"></td>
					</tr>
					<tr>
						<th><label for="member_phone">핸드폰번호</label></th>
						<td><input class="input-default btnInput numberOnly" maxlength="13" type="text" name="member_phone"></td>
					</tr>
				</table>
				<div class="button mt40">
					<a class="bgGray" href="javascript:checkEmail()">이메일 찾기</a>
				</div>
			</li>
			<li class="none rel">
				<table class="pwForm basic-form w100">
					<tr>
						<th><label for="member_name">이름</label></th>
						<td><input class="input-default btnInput" type="text" name="member_name"></td>
					</tr>
					<tr>
						<th><label for="member_email">이메일</label></th>
						<td><input class="input-default btnInput" type="text" name="member_email"></td>
					</tr>
				</table>
				<div class="button mt40">
					<a class="bgGray" href="javascript:checkPw()">비밀번호 찾기</a>
				</div>
			</li>
		</ul>
	</section>
	<!--visual end -->
</div>
<script src="assets/js/member/findIdPw.js"></script>
<jsp:include page="../include/footer.jsp" flush="false" />
