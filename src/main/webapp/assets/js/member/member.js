function checkPw(){
	var pw = $("input[name='member_password']");
	var rePw = $("input[name='member_repassword']");
	
	if(pw.val() == rePw.val()){
		return true;
	}else{
		rePw.next('.msg-box').text("패스워드가 일치하지 않습니다.");
		return false;
	}
}

function checkId(){
	var email = $("input[name='member_email']");
	var emailVal = email.val().toLowerCase();
	var idCheckData = {"id": emailVal}
	
	email.text(emailVal);
	
	if(emailVal.indexOf("@") != -1 && emailVal.indexOf(".") != -1){
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
		email.next().next('.msg-box').text("이메일 형식이 아닙니다.");
		return false;
	}
}
	
function checkMember(){
	if(checkPw() && checkId()){
		return true;
	}else{
		return false;
	}
}

function postAddr(){
	var postAddr = {};
	new daum.Postcode({
	    oncomplete: function(data) {
	       postAddr.address = data.address;
	       $("input[name='member_dest_loc1']").val(postAddr.address);
	       $("input[name='member_dest_loc2']").prop("disabled", false);
	    }
	}).open();
}
