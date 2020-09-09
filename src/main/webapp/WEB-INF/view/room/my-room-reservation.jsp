<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp" flush="false" />
<link rel="stylesheet" href="assets/css/search-room-style.css" />

<div class="sub-container">
	<!--visual start -->
	<section class="">
		<div class="container">
			<table class="common-table w100">
				<colgroup>
					<col width="150px"/>
					<col width="*"/>
					<col width="250px"/>
				</colgroup>
				<thead>
					<tr>
						<th>예약 번호</th>
						<th>예약내용</th>
						<th>예약상태</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>3</th>
						<td>
							<a class="tal block pl10" href="${root}/room-detail?num=374">
								김아무개 / 01012345678 / 신명스카이뷰 16평형 전세권설정 저렴한 전세매물 입주협의가능
							</a>
						</td>
						<td>
							<div class="button">
								<a class="keybg">예약 승인</a>
								<a class="bgGray">예약 거절</a>
							</div>
						</td>
					</tr>
					<tr>
						<th>2</th>
						<td>
							<a class="tal block pl10" href="${root}/room-detail?num=374">
								김아무개 / 01012345678 / O역삼동O쓰리룸 방3화2 거실대O신축급O특급매물O전세자금 Cㅐ출 가능O
							</a>
						</td>
						<td>
							<div class="button">
								<a class="keybg">예약 승인</a>
								<a class="bgGray">예약 거절</a>
							</div>
						</td>
					</tr>
					<tr>
						<th>1</th>
						<td>
							<a class="tal block pl10" href="${root}/room-detail?num=374">
								김아무개 / 01012345678 / 갓성비 1.5룸 선릉역 도보3분 보면 나갑니다
							</a>
						</td>
						<td>
							<div class="button">
								<a class="keybg">예약 승인</a>
								<a class="bgGray">예약 거절</a>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!--/.container-->
	</section>
	<!--visual end -->
</div>


<script src="assets/js/room/myRoomList.js"></script>
<jsp:include page="../include/footer.jsp" flush="false" />