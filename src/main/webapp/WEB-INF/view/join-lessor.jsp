<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="include/header.jsp" flush="false" />

<div class="sub-container">
	<!--visual start -->
	<section class="container inner-container">
		<h1 class="text-title text-sub-title">임대인 회원가입</h1>
		<form class="basic-form" action="${root}/joinLessor" method="post">
			<table>
				<tr>
					<th>
						<label for="member_email">이메일</label>
					</th>
					<td>
						<input class="input-default btnInput" type="text" name="member_email" />
						<div class="inblock formBtn"><button class="btn-default bgGray bdGray">중복확인</button></div>
					</td>
				</tr>
				<tr>
					<th>
						<label for="member_password">비밀번호</label>
					</th>
					<td>
						<input class="input-default" type="password" name="member_password" />
					</td>
				</tr>
				<tr>
					<th>
						<label for="member_repassword">비밀번호 확인</label>
					</th>
					<td>
						<input class="input-default" type="password" name="member_repassword" />
					</td>
				</tr>
				<tr>
					<th>
						<label for="member_business_license">사업자 번호</label>
					</th>
					<td>
						<input class="input-default btnInput" type="text" name="member_business_license" />
						<div class="inblock formBtn"><button class="btn-default bgGray bdGray">중복확인</button></div>
					</td>
				</tr>
				<tr>
					<th>
						<label for="member_business_name">상호명</label>
					</th>
					<td>
						<input class="input-default" type="text" name="member_business_name" />
					</td>
				</tr>
				<tr>
					<th>
						<label for="member_ceo_name">대표자명</label>
					</th>
					<td>
						<input class="input-default" type="text" name="member_ceo_name" />
					</td>
				</tr>
				<tr>
					<th>
						<label for="member_business_phn_num">대표 번호</label>
					</th>
					<td>
						<input class="input-default" type="text" name="member_business_phn_num" />
					</td>
				</tr>
			</table>
			<div class="cf mt20">
				<input class="fr btn-default submitBtn" type="submit" value="회원가입"/>
			</div>
		</form>
	</section>
	<!--visual end -->
</div>

<jsp:include page="include/footer.jsp" flush="false" />