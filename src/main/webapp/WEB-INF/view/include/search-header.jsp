<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="assets/css/common-style.css" />
<link rel="stylesheet" href="assets/css/search-common-style.css" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js" type="text/javascript"></script>
	<!-- <script src="assets/plugin/price_range/price_range_script.js" type="text/javascript"></script> -->
<!-- search-header-container start -->

<div class="search-header-area mb40">
	<div class="container">
		<div class="search-container inblock">
			<div class="search-area pr10 cf">
				<input id="seachInput" class="input-default fl" placeholder="지역검색" type="text" name="areaSearch"/>
				<div id="seachBtn" class="i-box inblock fl"><i class="fas fa-search-location"></i></div>
			</div>
			<ul id="sellingType" class="tab-box pr10">
				<li>
					<input class="sellingInput" name="select" id="select1" type="checkbox"/>
					<label for="select1">전세</label>
				</li>
				<li>
					<input class="sellingInput" name="select" id="select2" type="checkbox"/>
					<label for="select2">월세</label>
				</li>
			</ul>
			<div id="modalBox" class="tableCell">
				<div class="deposit-box lease-box rel pr10">
					<div class="rel inblock">
						<button onclick="showModal(this)" data-modal="deposit-modal1" data-name="areaSearch" class="input-default pr20">전세금</button>
						<i class="fas fa-sort abs"></i>
					</div>
				</div>
				<div class="deposit-box monthly-box none">
					<div class="rel inblock">
						<button onclick="showModal(this)" data-modal="deposit-modal2" data-name="areaSearch" class="input-default pr20">보증금 / 월세</button>
						<i class="fas fa-sort abs"></i>
					</div>
				</div>
			</div>
		</div>
		<div id="deposit-modal1" class="abs deposit-modal">
			<div onclick="closeModal(this)" class="close-deposit-modal"></div>
			<div class="deposit-modal-inner">
				<p>드래그하여 가격을 설정해주세요<span style="float:right; font-size: 0.7em;">(단위 : 만원)</span></p>
				<div class="deposit-range default-range">
					<ul class="price-ul cf mb10">
						<li class="min"></li>
						<li class="tac multi"></li>
						<li class="tar max"></li>
					</ul>
					<div class="deposit-bar1">
						<div class="price-range-block">
							<div class="price-filter-range slider-range" name="rangeInput"></div>
							<div class="none deposit-input-bar">
								<input type="number" min=0 max="9900"
									oninput="validity.valid||(value='0');"
									class="price-range-field min_price" />
								<input type="number" min=0
									max="10000" oninput="validity.valid||(value='10000');"
									class="price-range-field max_price" />
							</div>
						</div>
						<p class="mt10 price-range-result"><span class="min"></span> ~ <span class="max"></span></p>
					</div>
				</div>
				<p>
					<input name="fee" id="fee" type="checkbox"/>
					<label for="fee">관리비를 포함하시려면 체크해주세요</label>
				</p>
			</div>
		</div>
		<div id="deposit-modal2" class="abs deposit-modal">
			<div onclick="closeModal(this)" class="close-deposit-modal"></div>
			<div class="deposit-modal-inner">
				<p>드래그하여 가격을 설정해주세요<span style="float:right; font-size: 0.7em;">(단위 : 만원)</span></p>
				<div class="deposit-range default-range">
					<p class="mt10 mb10">보증금</p>
					<ul class="price-ul cf mb10">
						<li class="min"></li>
						<li class="tac multi"></li>
						<li class="tar max"></li>
					</ul>
					<div class="deposit-bar1">
						<div class="price-range-block">
							<div class="price-filter-range slider-range" name="rangeInput"></div>
							<div class="none deposit-input-bar">
								<input type="number" min=0 max="9900"
									oninput="validity.valid||(value='0');"
									class="price-range-field min_price" />
								<input type="number" min=0
									max="10000" oninput="validity.valid||(value='10000');"
									class="price-range-field max_price" />
							</div>
						</div>
						<p class="mt10 price-range-result"><span class="min"></span> ~ <span class="max"></span></p>
					</div>
				</div>
				<div class="deposit-range monthly-range">
					<p class="mt10 mb10">월세</p>
					<ul class="price-ul cf mb10">
						<li class="min"></li>
						<li class="tac multi"></li>
						<li class="tar max"></li>
					</ul>
					<div class="deposit-bar1">
						<div class="price-range-block">
							<div class="price-filter-range slider-range" name="rangeInput"></div>
							<div class="none deposit-input-bar">
								<input type="number" min=0 max="9900"
									oninput="validity.valid||(value='0');"
									class="price-range-field min_price" />
								<input type="number" min=0
									max="10000" oninput="validity.valid||(value='10000');"
									class="price-range-field max_price" />
							</div>
						</div>
						<p class="mt10 price-range-result"><span class="min"></span> ~ <span class="max"></span></p>
					</div>
				</div>
				<p>
					<input name="fee" id="fee" type="checkbox"/>
					<label for="fee">관리비를 포함하시려면 체크해주세요</label>
				</p>
			</div>
		</div>
		<div class="search-container inblock fr">
			<div class="search-area pr10 cf">
				<input id="keywordInput" class="input-default fl" placeholder="키워드검색 (ex. 반려동물, 단기임대)" type="text" name="keywordSearch"/>
				<div id="keywordBtn" class="i-box inblock fl"><i class="fas fa-search-plus"></i></div>
			</div>
			<ul id="range-box" class="range-box cf">
				<!--li>
					<input hidden="hidden" name="range" id="range0" type="checkbox"/>
					<label for="range0">거리순</label>
				</li -->
				<li>
					<input hidden="hidden" name="range" id="range1" type="checkbox"/>
					<label for="range1">인기순</label>
				</li>
				<li>
					<input hidden="hidden" name="range" id="range2" type="checkbox"/>
					<label for="range2">최신순</label>
				</li>
				<li>
					<input hidden="hidden" name="rangeRadio" id="range3" type="radio"/>
					<label for="range3">높은가격순</label>
				</li>
				<li>
					<input hidden="hidden" name="rangeRadio" id="range4" type="radio"/>
					<label for="range4">낮은가격순</label>
				</li>
			</ul>
		</div>
		<div id="keyword-wrapper" class="search-container block none tar" style="height: 40px;">
			<span class="none inblock">검색 키워드 : </span>
			<ul id="keyword-box" class="inblock"></ul>
		</div>
	</div>
</div>
<!-- search-header-container end -->

<script src="assets/js/jquery.js"></script>
<script>
	$(document).ready(function(){
	    $(".tab-box li").first().find("input[type='radio']").prop("checked", true);
	})
</script>
<script>

function showModal(elem){
	const rect = elem.getBoundingClientRect();
	const offsetX = rect.left + window.scrollX;
	const offsetY = rect.top + window.scrollY + $(elem).height() + 7;
	var modal = elem.getAttribute('data-modal');
	console.log(modal);
	$("#"+modal).find(".deposit-modal-inner").css({left: offsetX, top: offsetY});
	$("#"+modal).show();
}


function closeModal(elem){
	$(elem).parents(".deposit-modal").hide();
}



</script>
