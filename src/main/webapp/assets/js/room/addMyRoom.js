var editorContent;
var oEditors = [];

$(document).ready(function(){

	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors,
		elPlaceHolder : "ir1",
		sSkinURI : "assets/editor/dist/SmartEditor2Skin.html",
		fCreator : "createSEditor2"
	});
	
	myFileList.imgBoolean = "true";
	myPostAddr();
})

function checkRoom(){
	if(edit() && setParkCharge()){
		if(room == ""){
			myRoom().done(function(roomNum){
				myFileList.roomNumber = parseInt(roomNum);
	       	    myRoomImg();
			})
		}else{
			myRoomUpdate().done(function(roomNum){
				myFileList.roomNumber = parseInt(roomNum);
				if(myFileList.files != "undefined"){
		       	    myRoomImg();
				}
			})
		}
		return false;
	}else{
		alert("모든 항목을 작성하여 주십시오.");
		return false;
	}
	return false;
}

var fileList = new Array();
var myFileList = {};

function myFiles() {

	  var files   = document.querySelector('input[type=file]').files;
	  function readAndPreview(file) {
	    // `file.name` 형태의 확장자 규칙에 주의하세요
	    if ( /\.(jpe?g|png|gif)$/i.test(file.name) ) {
	      var reader = new FileReader();

	      reader.addEventListener("load", function () {
	        var image = new Image();
	        image.height = 100;
	        image.title = file.name;
	        image.src = this.result;
	        fileList.push(this.result);
	        $('#preview').append("<p class='mt05'>" + file.name + "</p>");
	      }, false);
	      reader.readAsDataURL(file);
	    }

	  }

	  if (files) {
	  	delete myFileList.imgBoolean;
	  	fileList = [];
	  	myFileList = {};
	    $('#preview *').empty();
	    [].forEach.call(files, readAndPreview);
	    myFileList.files = fileList;
	  }else{
	  	myFileList.imgBoolean = "true";
	  }
}

$("input[name='room_selling_type']").on("click",function(){
	var room_selling_type_class = $("input[name='room_selling_type']:checked").prop("id");
	$(".room_selling_type").addClass("none");
	$(".room_selling_type").find("input").prop("required",false);
	$("."+room_selling_type_class).removeClass("none");
	$("."+room_selling_type_class).find("input").prop("required",true);
})


function postAddr(){
	var postAddr = {};
	new daum.Postcode({
	    oncomplete: function(data) {
	       postAddr.address = data.address;
	       $("input[name='member_dest_loc']").val(postAddr.address);
	       $("input[name='room_dong']").prop("disabled", false);
	       $("input[name='room_ho']").prop("disabled", false);
	       $("input[name='room_address']").val(data.sido + " " + data.sigungu + " " + data.bname);
	       $("input[name='room_complex_id']").val(data.buildingName);
	    }
	}).open();
}


function edit(){
	oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
	editorContent = document.getElementById("ir1").value;
	if(editorContent != ""){
		$("textarea[name='room_content']").text(editorContent);
		return true;
	}else{
		alert("글 내용을 작성하세요");
		return false;
	}
	
	var rsc = $("input[name='room_service_charge']").val();
	if('' != rsc){
		$("input[name='room_service_charge']").val("없음");
	}else{
		$("input[name='room_service_charge']").val(rsc + "만 원");
	}
}

function myPostAddr(){
	console.log($("input[name='member_dest_loc']").val())
}

function myRoomUpdate(){
	
	var $form = $("#roomUploadForm").serialize();
	return $.ajax({
       method:"POST",
       url: 'update-my-room?num=' + roomInfo.room_number,
       data: $form,
       success: function(roomNum){  
       	   console.log(roomNum);
       }
    });
}

function myRoom(){
	
	var $form = $("#roomUploadForm").serialize();
	return $.ajax({
       method:"POST",
       url: 'add-my-room',
       data: $form,
       success: function(roomNum){  
       	   console.log(roomNum);
       }
    });
}

function myRoomImg(){
    $.ajax({
       url: 'add-my-room-img',
       method:'post',
       data: JSON.stringify(myFileList),
       contentType : 'application/json',
       success: function(result){  
       	   window.location.href = "my-room-list";
       },
	   error:function(request,status,error){
	    	console.log("code = "+ request.status + " message = " + request.responseText + " error = " + error);
	   }
    });
}

$("input.pay_input").change(function(){
	var num = $(this).val();
	var num1 = "";
	var num2 = "";
	if(num.length >= 5){
		num1 = num.substring(0, num.length-4);
		num2 = num.substring(num.length-4, num.length);
		if(num2 == "0000"){
			$(this).next(".pay_text_input").val(num1 + "억 ");
		}else{
			$(this).next(".pay_text_input").val(num1 + "억 " + num2);
		}
	}else{
		$(this).next(".pay_text_input").val(num);
	}
	$(this).next().next(".pay_num_input").val(num);
});

$("input[name='room_park_charge_radio']").change(function(){
	var charge = $("input[name='room_park_charge_radio']:checked");
	if(charge.prop("id") == "room_park_charge3"){
		$("input[name='room_park_charge_payed']").parent("div").removeClass("none");
		$("input[name='room_park_charge_payed']").prop("required",true);
	}else{
		$("input[name='room_park_charge_payed']").parent("div").addClass("none");
		$("input[name='room_park_charge_payed']").prop("required",false);
	}
})

function setParkCharge(){
	var charge = $("input[name='room_park_charge_radio']:checked");
	if(charge.prop("id") == "room_park_charge3"){
		$("input[name='room_park_charge']").val($("input[name='room_park_charge_payed']").val() + "만 원");
	}else{
		$("input[name='room_park_charge']").val(charge.val());
	}
	if($("input[name='room_service_charge']").val() == ""){
		$("input[name='room_service_charge']").val("무료");
	}
	return true;
}






