<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp" flush="false" />

<div class="sub-container">
	<!--visual start -->
	<section class="container inner-container">
		<c:if test="${sessionScope.member eq null}">
			<h1 class="text-title text-sub-title">임차인 회원가입</h1>
		</c:if>
		<c:if test="${sessionScope.member ne null}">
			<h1 class="text-title text-sub-title">임차인 회원정보수정</h1>
		</c:if>
		<form class="basic-form" action="${root}/joinLessee" onsubmit="return(checkMember())" method="post">
			<table>
				<tr>
					<th class="pb00 notNull">
						<label for="member_email">이메일</label>
					</th>
					<td class="pb00">
						<input class="input-default <c:if test="${sessionScope.member eq null}">btnInput</c:if>" type="text" name="member_email" required="required" <c:if test="${sessionScope.member ne null}">value="${sessionScope.member.member_email}" readonly</c:if>/>
						<c:if test="${sessionScope.member eq null}">
							<div class="inblock formBtn"><a onclick="javascript:checkId();" href='javascript:void(0);' class="btn-default bgGray bdGray">중복확인</a></div>
						</c:if>
						<p class="msg-box"><c:if test="${sessionScope.member ne null}">변경 불가한 값입니다.</c:if></p>
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="member_password">비밀번호</label>
					</th>
					<td>
						<input class="input-default" type="password" name="member_password" required="required"/>
					</td>
				</tr>
				<tr class="notNull">
					<th class="pb00">
						<label for="member_repassword">비밀번호 확인</label>
					</th>
					<td class="pb00">
						<input class="input-default" type="password" name="member_repassword" required="required"/>
						<p class="msg-box"></p>
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="member_name">이름</label>
					</th>
					<td>
						<input class="input-default" type="text" name="member_name" required="required" <c:if test="${sessionScope.memberInfo.member_name ne null}">value="${sessionScope.memberInfo.member_name}"</c:if> />
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="member_phone">핸드폰 번호</label>
					</th>
					<td>
						<input class="input-default" type="text" name="member_phone" required="required" <c:if test="${sessionScope.memberInfo.member_phone ne null}">value="${sessionScope.memberInfo.member_phone}"</c:if>/>
					</td>
				</tr>
				<tr>
					<th>
						<label for="member_dest_loc">직장 주소</label>
					</th>
					<td>
						<input class="input-default btnInput" placeholder="주소찾기를 통하여 입력해주세요." type="text" name="member_dest_loc" readonly <c:if test="${sessionScope.memberInfo.member_dest_loc ne null}">value="${sessionScope.memberInfo.member_dest_loc}"</c:if>/>
						<div class="inblock formBtn"><a onclick="javascript:postAddr();" href='javascript:void(0);' class="btn-default bgGray bdGray">주소찾기</a></div>
					</td>
				</tr>
				<tr>
					<th>
						<label for="member_like_room_num">선호하는 타입</label>
					</th>
					<td>
						<ul class="tab-box pr10">
							<li>
								<input class="sellingInput" name="select" id="select1" type="radio">
								<label for="select1">전세</label>
							</li>
							<li>
								<input class="sellingInput" name="select" id="select2" type="radio">
								<label for="select2">월세</label>
							</li>
						</ul>
						<input class="input-default none" type="text" value="" name="member_like_room_type" readOnly/>
					</td>
				</tr>
			</table>
			<p class="tar mt10 mb10" class="font-size: 0.8em; color: #ccc;">*는 필수 입력 값입니다.</p>
			<div class="cf mt20">
				<c:if test="${sessionScope.member eq null}">
					<input class="fr btn-default submitBtn" type="submit" value="회원가입"/>
				</c:if>
				<c:if test="${sessionScope.member ne null}">
					<input class="fr btn-default submitBtn" type="submit" value="회원정보수정"/>
				</c:if>
			</div>
		</form>
	</section>
	<!--visual end -->
</div>

<script>
	var type = "${sessionScope.memberInfo.member_like_room_type}";
	if(type != ""){
		$("#" + type).prop("checked",true);
	}
</script>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script src="assets/js/member/member.js"></script>
<jsp:include page="../include/footer.jsp" flush="false" />