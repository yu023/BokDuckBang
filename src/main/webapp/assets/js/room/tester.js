function gogo(){
	var form = $("#fileUpForm")[0];
    var data = new FormData(form);
    $.ajax({
       enctype:"multipart/form-data",
       method:"POST",
       url: 'add-my-room-img',
       processData: false,   
       contentType: false,
       cache: false,
       data: data,
       success: function(result){  
           alert("업로드 성공!!");
       },
	   error:function(request,status,error){
	    	console.log("code = "+ request.status + " message = " + request.responseText + " error = " + error);
	   }
    });
    return false;
}

function myRoom(){

	e.preventDefault();
	
	// Get form
    var form = $('#fileUpForm')[0];

    // Create an FormData object 
    var data = new FormData(form);
   console.log( data.getAll('room_image_input'));
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "add-my-room-img",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
        	console.log(data);
        	alert("complete");
        	return false;
        },
        error: function (e) {
            console.log("ERROR : ", e);
            alert("fail");
            return false;
        }
    });
    
    return false;
}