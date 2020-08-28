function checkPw(){
	var pw = $("input[name='member_password']");
	var rePw = $("input[name='member_repassword']");
	
	if(pw.val() == rePw.val()){
		return true;
	}else{
		rePw.next('.msg-box').text("패스워드가 일치하지 않습니다.");
		rePw.focus();
		return false;
	}
}

function checkId(){
	var email = $("input[name='member_email']");
	var emailVal = email.val();
	var idCheckData = {"id": emailVal}
	var checkReg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
	email.text(emailVal);
	
	if(checkReg.test(emailVal)){
		console.log("email true")
		$.ajax({
			url : 'idChecker',
			data : JSON.stringify(idCheckData),
			method : 'post',
			contentType : "application/json"
		}).done(function(msg){
			email.next().next('.msg-box').text(msg.chkResult);
		})
		return true;
	}else{
		console.log("email false")
		email.next().next('.msg-box').text("이메일 형식이 아닙니다.");
		email.focus();
		return false;
	}
}
	
function checkMember(){
	if(checkPw() && checkId()){
		var sellVal = $("input[name='select']:checked").prop("id");
		if(typeof sellVal != 'undefined'){
			$("input[name='member_like_room_type']").val(sellVal);
		}
		return true;
	}else{
		alert("id 혹은 pw를 확인해주세요.");
		return false;
	}
}
	
function checkLessorMember(){
	if(checkPw() && checkId() && businessLicense()){
		return true;
	}else{
		alert("id/pw 혹은 사업자번호를 확인해주세요.");
		return false;
	}
}

function postAddr(){
	var postAddr = {};
	new daum.Postcode({
	    oncomplete: function(data) {
	       postAddr.address = data.address;
	       $("input[name='member_dest_loc']").val(postAddr.address);
	    }
	}).open();
}

function businessLicense(){
	var Blicense = $("input[name='member_business_license']");
	if(Blicense.val() != ""){
		var BlicenseMap = {"member_business_license" : Blicense.val()};
		console.log(BlicenseMap);
		$.ajax({
			url : 'businessLicenseChecker',
			method : 'post',
			data : JSON.stringify(BlicenseMap),
			contentType : 'application/json'
		}).done(function(result){
			Blicense.next().next('.msg-box').text(result.chkResult);
		})
	}
}

$(".numberOnly").on('keydown keypress keyup', function(e){
	if(e.keyCode == 45){
		return false;
	}
})

$(".numberOnly").on('keydown keypress keyup paste input', function(){
	var inputTxt = $(this).val();
	
	$(this).val(inputTxt.replace(/[^\d|-]/g, ''));
	
	if(inputTxt.length == 3){
		var myTxt = inputTxt;
		$(this).val(myTxt + "-");
	};
	if(inputTxt.length == 6){
		var myTxt = inputTxt;
		$(this).val(myTxt + "-");
	};
});
