$(document).ready(function(){
	$(".tab-box-border li").on("click",function(){
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
		
		var idx = $(this).index();
		$(".tab-content li").eq(idx).siblings().addClass("none");
		$(".tab-content li").eq(idx).removeClass("none");
		
		$(".popBox").remove();
		$(".typeForm").removeClass("visibilityHidden");
		$(".tab-content li").find("*").removeClass("visibilityHidden");
	})
})

function checkEmail(){
	var table = $(".emailForm");
	var memberType = $(".typeForm").find("input[name='member_type']:checked").val();
	var member_name = table.find("input[name='member_name']");
	var member_phone = table.find("input[name='member_phone']");
	var checker = {member_type : memberType};
	
	if(member_name.val() == ""){
		alert("이름을 작성하여 주십시오");
		member_name.focus();
	}else if(member_phone.val() == ""){
		alert("핸드폰 번호를 작성하여 주십시오");
		member_phone.focus();
	}else if(member_name.val() != "" && member_phone.val() != ""){
		checker.member_name = member_name.val();
		checker.member_phone = member_phone.val();
		$.ajax({
			url : 'findId',
			method : 'post',
			data : JSON.stringify(checker),
			contentType : 'application/json'
		}).done(function(result){
			if(result == ""){
				alert("가입되지 않은 회원입니다.")
			}else{
				$(".typeForm").addClass("visibilityHidden");
				$(".tab-content li").eq(0).find("*").addClass("visibilityHidden");
				$(".tab-content li").eq(0).append("<p class='abs popBox' style='left: 50%; top: 50%; transform: translate(-50%, -50%);'>회원님의 이메일은 " + result + " 입니다</p>");
			}
		})
	}
	
}

function checkPw(){
	var table = $(".pwForm");
	var memberType = $(".typeForm").find("input[name='member_type']:checked").val();
	var member_name = table.find("input[name='member_name']");
	var email = $("input[name='member_email']");
	var checkReg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
	
	var checker = {member_type : memberType};
	
	if(member_name.val() == ""){
		alert("이름을 작성하여 주십시오");
		member_name.focus();
	}else if(email.val() == ""){
		alert("이메일을 작성하여 주십시오");
		email.focus();
	}else if(!checkReg.test(email.val())){
		alert("이메일 형식을 체크해주세요.");
		email.focus();
	}else if(checkReg.test(email.val())){
		checker.member_name = member_name.val();
		checker.member_email = email.val();
		
		$(".typeForm").addClass("visibilityHidden");
		$(".tab-content li").eq(1).find("*").addClass("visibilityHidden");
		$(".tab-content li").eq(1).append("<p class='abs popBox' style='left: 50%; top: 50%; transform: translate(-50%, -50%);'>비밀번호 전송중입니다.</p>");

		$.ajax({
			url : 'findPw',
			method : 'post',
			data : JSON.stringify(checker),
			contentType : 'application/json'
		}).done(function(result){
			var resultMsg = result.resultMsg;
			$(".popBox").remove();
			$(".tab-content li").eq(1).append("<p class='abs popBox' style='left: 50%; top: 50%; transform: translate(-50%, -50%);'>" + resultMsg + "</p>");
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
	if(inputTxt.length == 8){
		var myTxt = inputTxt;
		$(this).val(myTxt + "-");
	};
});