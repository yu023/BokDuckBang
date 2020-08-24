<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DB 입력폼</title>
<script src="assets/js/jquery.js"></script>
<script src="assets/js/jquery.ajax-cross-origin.min.js"></script>
</head>
<%
	request.setAttribute("root", request.getContextPath());
%>
<body>
		<input id="imgsrc" style="width: 30vw;" type="text" name="imgsrc" placeholder=""/>
		<button onclick="insert()">insert</button>
	<p id="msgBox"></p>
	
	<form action="">
	
	</form>
</body>

<script>
	$(document).ready(function(){
		$("#imgsrc").focus();
	})
	
	var i = 0;
	
	function insert(){

		i++;
	    var static_json = $("#imgsrc").val() + "?callback=?";
		    console.log("insert 탔음!!");

		$.ajax({
	      
	      url: static_json,
	      context: {},
	      data: {
	          format: 'json'
	       },
	      success: function(data){
			console.log(typeof data);
		    $.ajax({
				method : "post",
				url : "${root}/insert",
				data : data,
				contentType: "application/json"
			}).done(function(msg){
				$("#msgBox").text(msg.resultMsg + i);
			})
	      },
	      error : function(xhr, ajaxOptions, thrownError) {
				$("#msgBox").text("오류발생" + i);
	          console.log("error1 : " + xhr.status);
	          console.log("error2 : " + thrownError);
	      },
	      complete : function() {
	    	  console.log("끝");
	      }
	    })
	}

</script>
</html>