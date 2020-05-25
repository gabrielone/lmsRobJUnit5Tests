function validateChangePassword(){
	var currentPassword = document.getElementById('currentPassword').value;
	var newPassword = document.getElementById('newPassword').value;
	var reEnterPassword = document.getElementById('reEnterPassword').value;
	var regExp = /[a-zA-Z0-9]/;
	
	if((currentPassword == null || currentPassword == '') || (newPassword == null || newPassword == '')){
		bootbox.alert("Empty password fields are not allowed!!!");
		return false;
	} else if(!regExp.test(currentPassword) || !regExp.test(newPassword)){
		bootbox.alert("Please enter a valid Password!!! Special characters and spaces are not allowed!!!");
		return false;
	}
	
	if(newPassword != reEnterPassword){
		bootbox.alert("New Password and Re-enter password don't match!!!");
		return false;
	}
	
	document.forms[0].action='/user/change-password';
	document.forms[0].method="POST";
	document.forms[0].submit();
}
