<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp" flush="false" />

<div class="sub-container">
	<!--visual start -->
	<section class="container inner-container">
		<c:if test="${sessionScope.member eq null}">
			<h1 class="text-title text-sub-title">임대인 회원가입</h1>
		</c:if>
		<c:if test="${sessionScope.member ne null}">
			<h1 class="text-title text-sub-title">임대인 회원정보수정</h1>
		</c:if>
		<form class="basic-form" action="${root}/joinLessor" onsubmit="return(checkLessorMember())" method="post">
			<table>
				<tr>
					<th class="pb00 notNull">
						<label for="member_email">이메일</label>
					</th>
					<td class="pb00">
						<input class="input-default btnInput <c:if test="${sessionScope.member ne null}">w100</c:if>" type="text" name="member_email" <c:if test="${sessionScope.member ne null}">value="${sessionScope.member.member_email}" readonly</c:if> required="required"/>
						<c:if test="${sessionScope.member eq null}">
							<div class="inblock formBtn"><a onclick="javascript:checkId();" href='javascript:void(0);' class="btn-default bgGray bdGray">중복확인</a></div>
						</c:if>
						<p class="msg-box"><c:if test="${sessionScope.member ne null}">변경 불가능한 값입니다.</c:if></p>
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="member_password">비밀번호</label>
					</th>
					<td>
						<input class="input-default" type="password" name="member_password" />
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="member_repassword">비밀번호 확인</label>
					</th>
					<td class="pb00">
						<input class="input-default" type="password" name="member_repassword" required="required"/>
						<p class="msg-box"></p>
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="member_business_license">사업자 번호</label>
					</th>
					<td class="pb00">
						<input class="input-default btnInput numberOnly <c:if test="${sessionScope.memberInfo ne null}">w100</c:if>" type="text" name="member_business_license" <c:if test="${sessionScope.memberInfo ne null}">value="${sessionScope.memberInfo.member_business_license}" readonly</c:if> maxlength="12" required="required"/>
						<c:if test="${sessionScope.memberInfo eq null}"><div class="inblock formBtn"><a onclick="javascript:businessLicense();" href='javascript:void(0);' class="btn-default bgGray bdGray">중복확인</a></div></c:if>
						<p class="msg-box"><c:if test="${sessionScope.memberInfo ne null}">변경 불가능한 값입니다.</c:if></p>
					</td>
				</tr>
				<tr class="notNull">
					<th class="<c:if test="${sessionScope.memberInfo ne null}">pb00</c:if>">
						<label for="member_business_name">상호명</label>
					</th>
					<td class="<c:if test="${sessionScope.memberInfo ne null}">pb00</c:if>">
						<input class="input-default" type="text" name="member_business_name" <c:if test="${sessionScope.memberInfo ne null}">value="${sessionScope.memberInfo.member_business_name}" readonly</c:if> />
						<p class="msg-box"><c:if test="${sessionScope.memberInfo ne null}">변경 불가능한 값입니다.</c:if></p>
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="member_ceo_name">대표자명</label>
					</th>
					<td>
						<input class="input-default" type="text" name="member_ceo_name" <c:if test="${sessionScope.memberInfo ne null}">value="${sessionScope.memberInfo.member_ceo_name}"</c:if>/>
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="member_business_phn_num">대표 번호</label>
					</th>
					<td>
						<input class="input-default numberOnly" type="text" name="member_business_phn_num" <c:if test="${sessionScope.memberInfo ne null}">value="${sessionScope.memberInfo.member_business_phn_num}"</c:if>/>
					</td>
				</tr>
			</table>
			<p class="tar mt10 mb10" class="font-size: 0.8em; color: #ccc;">*는 필수 입력 값입니다.</p>
			<div class="cf mt20">
				<input class="fr btn-default submitBtn" type="submit" value="회원가입"/>
			</div>
			<c:if test="${sessionScope.member ne null}">
				<p class="tar mt20 mb20"><a style="font-size: 0.9em; color: #bbb;"  href="javascript:withdrawal()">회원탈퇴</a></p>
			</c:if>
		</form>
	</section>
	<!--visual end -->
</div>

<script>
	var session = "${sessionScope.memberInfo}";
</script>

<script src="assets/js/member/member.js"></script>
<jsp:include page="../include/footer.jsp" flush="false" />