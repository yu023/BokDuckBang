<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp" flush="false" />

<div class="sub-container">
	<!--visual start -->
	<section class="container inner-container">
		<h1 class="text-title text-sub-title">임대인 회원가입</h1>
		<form class="basic-form" action="${root}/joinLessor" onsubmit="return(checkLessorMember())" method="post">
			<table>
				<tr>
					<th class="pb00 notNull">
						<label for="member_email">이메일</label>
					</th>
					<td class="pb00">
						<input class="input-default btnInput" type="text" name="member_email" required="required"/>
						<div class="inblock formBtn"><a onclick="javascript:checkId();" href='javascript:void(0);' class="btn-default bgGray bdGray">중복확인</a></div>
						<p class="msg-box"></p>
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
						<input class="input-default btnInput numberOnly" type="text" name="member_business_license" maxlength="12" required="required"/>
						<div class="inblock formBtn"><a onclick="javascript:businessLicense();" href='javascript:void(0);' class="btn-default bgGray bdGray">중복확인</a></div>
						<p class="msg-box"></p>
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="member_business_name">상호명</label>
					</th>
					<td>
						<input class="input-default" type="text" name="member_business_name" />
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="member_ceo_name">대표자명</label>
					</th>
					<td>
						<input class="input-default" type="text" name="member_ceo_name" />
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="member_business_phn_num">대표 번호</label>
					</th>
					<td>
						<input class="input-default" type="text" name="member_business_phn_num" />
					</td>
				</tr>
			</table>
			<p class="tar mt10 mb10" class="font-size: 0.8em; color: #ccc;">*는 필수 입력 값입니다.</p>
			<div class="cf mt20">
				<input class="fr btn-default submitBtn" type="submit" value="회원가입"/>
			</div>
		</form>
	</section>
	<!--visual end -->
</div>

<script src="assets/js/member/member.js"></script>
<jsp:include page="../include/footer.jsp" flush="false" />