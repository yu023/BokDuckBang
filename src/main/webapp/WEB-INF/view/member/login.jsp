<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp" flush="false" />

<div class="sub-container">
	<!--visual start -->
	<section class="container inner-container">
		<h1 class="text-title text-sub-title">
			<c:if test="${sessionScope.member eq null}">로그인</c:if>
			<c:if test="${sessionScope.member ne null}">회원인증</c:if>
		</h1>
		<form class="basic-form" action="${root}/loginProcess" method="post">
			<table>
				<tr>
					<th>
						<label for="member_email">이메일</label>
					</th>
					<td>
						<input class="input-default" <c:if test="${sessionScope.member eq null and email ne null}">value="${email}"</c:if> <c:if test="${sessionScope.member ne null}">value="${sessionScope.member.member_email}" readonly</c:if> type="text" name="member_email" required="required" />
					</td>
				</tr>
				<tr>
					<th>
						<label for="member_password">비밀번호</label>
					</th>
					<td>
						<input class="input-default" type="password" name="member_password" required="required" />
					</td>
				</tr>
			</table>
			<div class="mt10 mb10 tar">
				<a class="inblock mr10" href="${root}/select-join-type">회원가입</a>
				<a class="inblock" href="${root}/findIdPw">이메일/비밀번호찾기</a>
			</div>
			<div class="cf mt20">
				<input class="btn-default submitBtn" style="margin: auto;" type="submit" value="로그인"/>
			</div>
		</form>
	</section>
	<!--visual end -->
</div>

<jsp:include page="../include/footer.jsp" flush="false" />
