<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp" flush="false" />
<link rel="stylesheet" href="assets/css/search-room-style.css" />

<div class="sub-container">
	<!--visual start -->
	<section class="map-wrapper">
		<div class="container">
			<ul id="room-list">
				<li v-for="list in myList" v-bind:class="'col-md-3 room' + list.room_number">
					<div class="list-li-wrapper">
						<a class="block" v-bind:href="list.room_link">
							<div v-bind:style="{ 'background-image': 'url(' + list.img + ')' }" class="thumb"></div>
						</a>
						<div class="li-wrap">
							<div class="table">
								<p class="tableCell">
									<a class="block" v-bind:href="'room-detail?num=' + list.room_number">
										{{list.room_title}}
									</a>
								</p>
								<span v-if="list.room_favorit == true" class="tableCell tar">
									<i onclick="like(this)" class="fas fa-heart"></i>
								</span>
								<span v-if="list.room_favorit == false" class="tableCell tar">
									<i onclick="like(this)" class="far fa-heart"></i>
								</span>
							</div>
							<p>
								<a class="block" v-bind:href="'room-detail?num=' + list.room_number">
									<span v-for="keyword in list.room_keytitle">
										{{keyword}}
									</span>
								</a>
							</p>
						</div>
					</div>
				</li>
			</ul>
		</div>
		<!--/.container-->
	</section>
	<!--visual end -->
</div>

<script src="https://cdn.jsdelivr.net/npm/vue"></script>
<script src="assets/js/room/likesRoom.js"></script>
<jsp:include page="../include/footer.jsp" flush="false" />