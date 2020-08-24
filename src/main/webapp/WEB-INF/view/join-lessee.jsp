<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="include/header.jsp" flush="false" />

<div class="sub-container">
	<!--visual start -->
	<section class="container inner-container">
		<h1 class="text-title text-sub-title">임차인 회원가입</h1>
		<form class="basic-form" action="${root}/joinLessee" onsubmit="return(checkMember())" method="post">
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
						<input class="input-default" type="text" name="member_name" required="required"/>
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="member_phone">핸드폰 번호</label>
					</th>
					<td>
						<input class="input-default" type="text" name="member_phone" required="required"/>
					</td>
				</tr>
				<tr>
					<th>
						<label for="member_dest_loc">직장 주소</label>
					</th>
					<td>
						<input class="input-default btnInput" placeholder="주소찾기를 통하여 입력해주세요." disabled="disabled" type="text" name="member_dest_loc1"/>
						<div class="inblock formBtn"><a onclick="javascript:postAddr();" href='javascript:void(0);' class="btn-default bgGray bdGray">주소찾기</a></div>
						<input class="input-default mt05" disabled="disabled" type="text" placeholder="상세주소" name="member_dest_loc2"/>
						<input class="input-default none" type="text" name="member_dest_loc"/>
					</td>
				</tr>
				<tr>
					<th>
						<label for="member_like_area">선호 지역</label>
					</th>
					<td>
						<input class="input-default" type="text" name="member_like_area" />
					</td>
				</tr>
				<tr>
					<th>
						<label for="member_like_room_num">선호하는 타입</label>
					</th>
					<td>
						<ul class="tab-box pr10">
							<li>
								<input class="sellingInput" name="select" id="select1" type="checkbox">
								<label for="select1">전세</label>
							</li>
							<li>
								<input class="sellingInput" name="select" id="select2" type="checkbox">
								<label for="select2">월세</label>
							</li>
						</ul>
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

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script src="assets/js/member/member.js"></script>
<jsp:include page="include/footer.jsp" flush="false" />