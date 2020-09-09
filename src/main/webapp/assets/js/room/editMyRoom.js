$(document).ready(function(){

	var roomTypeNumber = parseInt(roomInfo.room_type) + 1;
	$("#room_type"+roomTypeNumber).prop("checked",true);

	if(roomInfo.room_park_charge == "불가"){
		$("#room_park_charge1").prop("checked",true);
	}else if(roomInfo.room_park_charge == "가능(무료)"){
		$("#room_park_charge2").prop("checked",true);
	}else{
		$("#room_park_charge3").prop("checked",true);
		$("input[name='room_park_charge_payed']").parent("div").removeClass("none");
		$("input[name='room_park_charge_payed']").prop("required",true);
	}
	
	selector(roomInfo.room_selling_type, 'room_selling_type', '전세', '월세');
	selector(roomInfo.room_elevator, 'room_elevator', '있음', '없음');
	selector(roomInfo.room_heating, 'room_heating', '개별난방', '중앙난방');
	selector(roomInfo.room_balcony, 'room_balcony', '있음', '없음');
	selector(roomInfo.room_loan, 'room_loan', '가능', '불가능');
	selector(roomInfo.room_move_in_date, 'room_move_in_date', '즉시입주', '날짜협의');
	sliceOption(roomInfo.room_option, 'room_option');
	sliceOption(roomInfo.room_service, 'room_service');
	
	var room_selling_type_class = $("input[name='room_selling_type']:checked").prop("id");
	$(".room_selling_type").addClass("none");
	$(".room_selling_type").find("input").prop("required",false);
	$("."+room_selling_type_class).removeClass("none");
	$("."+room_selling_type_class).find("input").prop("required",true);
})

function selector(myKeyword, idValue, result1, result2){
	if(myKeyword == result1){
		$("#" + idValue + "1").prop("checked",true);
	}else if(myKeyword == result2){
		$("#" + idValue + "2").prop("checked",true);
	}
}

function sliceOption(option, inputName){

	var inputs = $("input[name='" + inputName + "']");
	var optionList = {};
	var optionKey = [];
	var optionVal = [];
	
	for(var i = 0; i < inputs.length; i ++){
		optionKey.push(inputs[i].id);
	    optionVal[i] = inputs[i].defaultValue;
	    optionList[optionVal[i]] = optionKey[i];
	}

	if(option.search(",") != -1){
		var options = option.split(",");
		
		var dataOption = keyArr(options,optionVal);
		
		$.each(dataOption, function (idx, val) {
			$("#"+optionList[val]).prop("checked",true);
		})

	}else{
		$("#"+optionList[option]).prop("checked",true);
	}
}

function keyArr(options, optionList){
	return $.each(options, function (index, value) {
	   return optionList.filter(elem => elem == value);
	});
}
