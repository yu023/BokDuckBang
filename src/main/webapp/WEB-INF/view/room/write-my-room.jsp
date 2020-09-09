<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp" flush="false" />
<script type="text/javascript" src="assets/editor/dist/js/service/HuskyEZCreator.js"></script>
<div class="sub-container">
	<!--visual start -->
	<section class="container inner-container">
		<h1 class="text-title text-sub-title">방 등록하기</h1>
		<form id="fileUploadForm" class="basic-form" onsubmit="return false;" method="post">
			<table>
				<tr class="notNull">
					<th>
						<label for="room_images">방 사진</label>
					</th>
					<td>
						<input id="myInputID" class="input-default" type="file" name="room_image_input" onchange="myFiles()" accept="image/gif, image/jpeg, image/png" multiple required="required"/>
						<div id="preview">
						<c:if test="${roomImgList ne null}">
							<ul class="row mt15">
								<c:forEach var="room" items="${roomImgList}">
									<li class="col-md-3 inblock"><img src="${room}"/></li>
								</c:forEach>
							</ul>
							<p class="mt10" style="font-size: 0.85em;">선택하지 않을 경우 기존의 사진이 유지됩니다.</p>
						</c:if>
						</div>
						<input class="input-default none" type="number" name="room_number" value="0" required="required"/>
					</td>
				</tr>
			</table>
		</form>
		<form id="roomUploadForm" class="basic-form" onsubmit="return(checkRoom())" method="post">
			<table>
				<tr class="notNull">
					<th>
						<label for="member_dest_loc">위치</label>
					</th>
					<td>
						<input class="input-default btnInput" placeholder="주소찾기를 통하여 입력해주세요." type="text" value="${room.room_address}" name="member_dest_loc" readonly required="required"/>
						<div class="inblock formBtn"><a onclick="javascript:postAddr();" href='javascript:void(0);' class="btn-default bgGray bdGray">주소찾기</a></div>
						<input class="input-default mt10 w33 numberOnly" type="text" value="${room.room_dong}" name="room_dong" disabled="disabled" /> 동 &nbsp;&nbsp;
						<input class="input-default mt10 w33 numberOnly" type="text" value="${room.room_ho}" name="room_ho" disabled="disabled" /> 호
						<input class="input-default none" placeholder="시군구동" type="text" value="${room.room_address}" name="room_address" readonly />
						<input class="input-default none" placeholder="건물" type="text" value="${room.room_complex_id}" name="room_complex_id" readonly />
					</td>
				</tr>
				<tr>
					<th class="notNull">
						<label for="room_type">방 분류</label>
					</th>
					<td>
						<ul class="tab-box pr10">
							<li>
								<input checked="checked" class="sellingInput" name="room_type" id="room_type1" value="0" type="radio">
								<label for="room_type1">원룸 / 1.5룸</label>
							</li>
							<li>
								<input class="sellingInput" name="room_type" id="room_type2" value="1" type="radio">
								<label for="room_type2">투룸</label>
							</li>
							<li>
								<input class="sellingInput" name="room_type" id="room_type3" value="2" type="radio">
								<label for="room_type3">쓰리룸</label>
							</li>
							<li>
								<input class="sellingInput" name="room_type" id="room_type4" value="3" type="radio">
								<label for="room_type4">포룸 이상</label>
							</li>
						</ul>
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="room_title">글 제목</label>
					</th>
					<td>
						<input class="input-default" type="text" name="room_title" value="${room.room_title}" required="required" />
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="room_content">글 내용</label>
					</th>
					<td>
						<textarea class="input-default w100" name="ir1" id="ir1">${room.room_content}</textarea>
						<textarea class="none" name="room_content">${room.room_content}</textarea>
					</td>
				</tr>
				<tr class="notNull">
					<th class="">
						<label for="room_keywords">키워드</label>
					</th>
					<td class="">
						<input class="input-default" value="${room.room_keywords}" placeholder="콤마(,)로 구분해주세요. ex. 채광, 반려동물, 단기가능" type="text" name="room_keywords" required="required"/>
					</td>
				</tr>
				<tr>
					<th class="notNull">
						<label for="room_selling_type">전월세</label>
					</th>
					<td>
						<ul class="tab-box pr10">
							<li>
								<input checked="checked" class="sellingInput" name="room_selling_type" id="room_selling_type1" value="전세" type="radio">
								<label for="room_selling_type1">전세</label>
							</li>
							<li>
								<input class="sellingInput" name="room_selling_type" id="room_selling_type2" value="월세" type="radio">
								<label for="room_selling_type2">월세</label>
							</li>
						</ul>
					</td>
				</tr>
				<tr class="room_selling_type room_selling_type1">
					<th class="notNull pb00">
						<label for="room_money_lease">전세금</label>
					</th>
					<td class="pb00">
						<p class="tar" style="font-size: 0.85em;">(단위 : 만원)</p>
							<input class="input-default numberOnly pay_input" value="${room.room_lease_num}" type="text" placeholder="전세금" name="room_money_lease_text" required/>
							<input class="none pay_text_input" value="${room.room_money_lease}" type="text" name="room_money_lease" readOnly/>
							<c:if test="${room.room_lease_num eq null}">
								<input class="none pay_num_input" value="0"  type="number" name="room_lease_num" readOnly/>
							</c:if>
							<c:if test="${room.room_lease_num ne null}">
								<input class="none pay_num_input" value="${room.room_lease_num}"  type="number" name="room_lease_num" readOnly/>
							</c:if>
					</td>
				</tr>
				<tr class="room_selling_type room_selling_type2 none">
					<th class="notNull pb00">
						<label for="">보증금/월세</label>
					</th>
					<td class=" pb00">
						<p class="tar" style="font-size: 0.85em;">(단위 : 만원)</p>
						<input class="input-default w33 numberOnly pay_input" placeholder="보증금" value="${room.room_deposit_num}" type="text" name="room_deposit_num_text" />&nbsp;
						<input class="none pay_text_input" type="text" name="room_money_deposit" value="${room.room_money_deposit}" readOnly/>
						<c:if test="${room.room_deposit_num eq null}">
								<input class="none pay_num_input"value="0"  type="number" name="room_deposit_num" readOnly/>
							</c:if>
							<c:if test="${room.room_deposit_num ne null}">
								<input class="none pay_num_input" value="${room.room_deposit_num}"  type="number" name="room_deposit_num" readOnly/>
							</c:if>
						<input class="none pay_num_input" type="number" name="room_deposit_num" value="${room.room_deposit_num}"  value="0" readOnly/>
						/&nbsp;<input class="input-default w33 numberOnly pay_input" placeholder="월세" value="${room.room_monthly_rent_num}" type="text" name="room_monthly_rent_num_text" />
						<input class="none pay_text_input" type="text" name="room_money_monthly_rent" value="${room.room_money_monthly_rent}" readOnly/>
						<c:if test="${room.room_monthly_rent_num eq null}">
							<input class="none pay_num_input" type="number" name="room_monthly_rent_num" value="0" readOnly/>
						</c:if>
						<c:if test="${room.room_monthly_rent_num ne null}">
							<input class="none pay_num_input" type="number" name="room_monthly_rent_num" value="${room.room_monthly_rent_num}" readOnly/>
						</c:if>
					</td>
				</tr>
				<tr>
					<th class="notNull">
						<label>관리비</label>
					</th>
					<td>
						<input class="input-default mb10 w33 numberOnly" type="text" value="${room.room_service_charge}" placeholder="기본값은 무료" name="room_service_charge"  />&nbsp;만원
						<br/>
						<label class="inblock mr20" for="room_service1"><input class="wAuto mr10" name="room_service" id="room_service1" value="인터넷" type="checkbox">인터넷</label>
						<label class="inblock mr20" for="room_service2"><input class="wAuto mr10" name="room_service" id="room_service2" value="유선 TV" type="checkbox">유선 TV</label>
						<label class="inblock mr20" for="room_service3"><input class="wAuto mr10" name="room_service" id="room_service3" value="수도세" type="checkbox">수도세</label>
						<label class="inblock mr20" for="room_service4"><input class="wAuto mr10" name="room_service" id="room_service4" value="전기세" type="checkbox">전기세</label>
						<label class="inblock mr20" for="room_service5"><input class="wAuto mr10" name="room_service" id="room_service5" value="청소" type="checkbox">청소</label>
						<label class="inblock" for="room_service6"><input class="wAuto mr10" name="room_service" id="room_service6" value="기타" type="checkbox">기타</label>
					</td>
				</tr>
				<tr>
					<th class="notNull">
						<label for="room_park_charge">주차 가능여부</label>
					</th>
					<td>
						<ul class="tab-box pr10">
							<li>
								<input checked="checked" class="sellingInput" name="room_park_charge_radio" id="room_park_charge1" value="불가" type="radio">
								<label for="room_park_charge1">불가</label>
							</li>
							<li>
								<input class="sellingInput" name="room_park_charge_radio" id="room_park_charge2" value="가능(무료)" type="radio">
								<label for="room_park_charge2">가능(무료)</label>
							</li>
							<li>
								<input class="sellingInput" name="room_park_charge_radio" id="room_park_charge3" value="" type="radio">
								<label for="room_park_charge3">가능(유료)</label>
							</li>
						</ul>
						<div class="none mt10"><input class="input-default numberOnly w33" type="text"  value="${room.room_park_charge}" name="room_park_charge_payed"/>&nbsp;만 원</div>
						<input class="input-default none" type="text" name="room_park_charge"   value="${room.room_park_charge}" readOnly/>
					</td>
				</tr>
				<tr class="notNull">
					<th>
						<label for="room_width">전용면적</label>
					</th>
					<td>
						<input class="input-default w33" type="number" name="room_width" step="0.01"  value="${room.room_width}" required="required" />m2
					</td>
				</tr>
				<tr>
					<th class="notNull">
						<label for="">해당층/건물층</label>
					</th>
					<td>
						<input class="input-default w33 numberOnly" placeholder="해당층" type="text" value="${room.room_floor_str}" name="room_floor_str" />층&nbsp;
						/&nbsp;<input class="input-default w33 numberOnly" placeholder="건물층" type="text" name="room_total_floor" value="${room.room_total_floor}" />층
					</td>
				</tr>
				<tr>
					<th class="notNull">
						<label for="room_elevator">엘리베이터</label>
					</th>
					<td>
						<ul class="tab-box pr10">
							<li>
								<input checked="checked" class="sellingInput" name="room_elevator" id="room_elevator1" value="있음" type="radio">
								<label for="room_elevator1">있음</label>
							</li>
							<li>
								<input class="sellingInput" name="room_elevator" id="room_elevator2" value="없음" type="radio">
								<label for="room_elevator2">없음</label>
							</li>
						</ul>
					</td>
				</tr>
				<tr>
					<th class="notNull">
						<label for="room_heating">난방종류</label>
					</th>
					<td>
						<ul class="tab-box pr10">
							<li>
								<input checked="checked" class="sellingInput" name="room_heating" id="room_heating1" value="개별난방" type="radio">
								<label for="room_heating1">개별난방</label>
							</li>
							<li>
								<input class="sellingInput" name="room_heating" id="room_heating2" value="중앙난방" type="radio">
								<label for="room_heating2">중앙난방</label>
							</li>
						</ul>
					</td>
				</tr>
				<tr>
					<th class="notNull">
						<label for="room_balcony">베란다/발코니</label>
					</th>
					<td>
						<ul class="tab-box pr10">
							<li>
								<input checked="checked" class="sellingInput" name="room_balcony" id="room_balcony1" value="있음" type="radio">
								<label for="room_balcony1">있음</label>
							</li>
							<li>
								<input class="sellingInput" name="room_balcony" id="room_balcony2" value="없음" type="radio">
								<label for="room_balcony2">없음</label>
							</li>
						</ul>
					</td>
				</tr>
				<tr>
					<th class="notNull">
						<label for="room_loan">전세자금대출</label>
					</th>
					<td>
						<ul class="tab-box pr10">
							<li>
								<input checked="checked" class="sellingInput" name="room_loan" id="room_loan1" value="가능" type="radio">
								<label for="room_loan1">가능</label>
							</li>
							<li>
								<input class="sellingInput" name="room_loan" id="room_loan2" value="불가능" type="radio">
								<label for="room_loan2">불가능</label>
							</li>
						</ul>
					</td>
				</tr>
				<tr>
					<th class="notNull">
						<label for="room_move_in_date">입주가능일</label>
					</th>
					<td>
						<ul class="tab-box pr10">
							<li>
								<input checked="checked" class="sellingInput" name="room_move_in_date" id="room_move_in_date1" value="즉시입주" type="radio">
								<label for="room_move_in_date1">즉시입주</label>
							</li>
							<li>
								<input class="sellingInput" name="room_move_in_date" id="room_move_in_date2" value="날짜협의" type="radio">
								<label for="room_move_in_date2">날짜협의</label>
							</li>
						</ul>
					</td>
				</tr>
				<tr>
					<th class="notNull">
						<label>옵션</label>
					</th>
					<td>
						<label for="room_option1" class="inblock mr20"><input type="checkbox" id="room_option1" class="wAuto mr10" name="room_option" value="반려동물" />반려동물 가능</label>
						<label for="room_option2" class="inblock mr20"><input type="checkbox" id="room_option2" class="wAuto mr10" name="room_option" value="에어컨" />에어컨</label>
						<label for="room_option3" class="inblock mr20"><input type="checkbox" id="room_option3" class="wAuto mr10" name="room_option" value="인덕션" />인덕션</label>
						<label for="room_option4" class="inblock mr20"><input type="checkbox" id="room_option4" class="wAuto mr10" name="room_option" value="전자레인지" />전자레인지</label>
						<label for="room_option5" class="inblock mr20"><input type="checkbox" id="room_option5" class="wAuto mr10" name="room_option" value="가스레인지" />가스레인지</label>
						<label for="room_option6" class="inblock mr20"><input type="checkbox" id="room_option6" class="wAuto mr10" name="room_option" value="냉장고" />냉장고</label>
						<label for="room_option7" class="inblock mr20"><input type="checkbox" id="room_option7" class="wAuto mr10" name="room_option" value="침대" />침대</label>
						<label for="room_option8" class="inblock mr20"><input type="checkbox" id="room_option8" class="wAuto mr10" name="room_option" value="책상" />책상</label>
						<label for="room_option9" class="inblock mr20"><input type="checkbox" id="room_option9" class="wAuto mr10" name="room_option" value="옷장" />옷장</label>
						<label for="room_option10" class="inblock mr20"><input type="checkbox" id="room_option10" class="wAuto mr10" name="room_option" value="TV" />TV</label>
						<label for="room_option11" class="inblock mr20"><input type="checkbox" id="room_option11" class="wAuto mr10" name="room_option" value="신발장" />신발장</label>
						<label for="room_option12" class="inblock mr20"><input type="checkbox" id="room_option12" class="wAuto mr10" name="room_option" value="세탁기" />세탁기</label>
						<label for="room_option13" class="inblock mr20"><input type="checkbox" id="room_option13" class="wAuto mr10" name="room_option" value="전자도어락" />전자도어락</label>
						<label for="room_option14" class="inblock mr20"><input type="checkbox" id="room_option14" class="wAuto mr10" name="room_option" value="비데" />비데</label>
						<label for="room_option15" class="inblock mr20"><input type="checkbox" id="room_option15" class="wAuto mr10" name="room_option" value="비디오폰" />비디오폰</label>
						<label for="room_option16" class="inblock mr20"><input type="checkbox" id="room_option16" class="wAuto mr10" name="room_option" value="공동현관" />공동현관</label>
						<label for="room_option17" class="inblock mr20"><input type="checkbox" id="room_option17" class="wAuto mr10" name="room_option" value="경비원" />경비원</label>
						<label for="room_option18" class="inblock mr20"><input type="checkbox" id="room_option18" class="wAuto mr10" name="room_option" value="CCTV" />CCTV</label>
						<label for="room_option19" class="inblock mr20"><input type="checkbox" id="room_option19" class="wAuto mr10" name="room_option" value="인터폰" />인터폰</label>
						<label for="room_option20" class="inblock"><input type="checkbox" id="room_option20" class="wAuto mr10" name="room_option" value="화재경보기" />화재경보기</label>
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

<script>
	var room = "${room.room_type}";
	var roomInfo = {};
	if(room != ""){
		roomInfo.room_number = "${room.room_number}",
		roomInfo.room_type = "${room.room_type}",
		roomInfo.room_service = "${room.room_service}",
		roomInfo.room_selling_type = "${room.room_selling_type}",
		roomInfo.room_park_charge = "${room.room_park_charge}",
		roomInfo.room_elevator = "${room.room_elevator}",
		roomInfo.room_heating = "${room.room_heating}",
		roomInfo.room_balcony = "${room.room_balcony}",
		roomInfo.room_loan = "${room.room_loan}",
		roomInfo.room_move_in_date = "${room.room_move_in_date}",
		roomInfo.room_option = "${room.room_option}"
	}
</script>
<c:if test="${room ne null}">
	<script src="assets/js/room/editMyRoom.js"></script>
</c:if>
<script src="assets/js/room/addMyRoom.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<jsp:include page="../include/footer.jsp" flush="false" />