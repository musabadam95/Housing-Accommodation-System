$(document).ready(function() {

	var pathnamea = window.location.pathname;
	var pathname=pathnamea.split("/");
	$('input:submit').click(function() {
var value=$(this).val();
var csrf = "input[name=csrfmiddlewaretoken]"
	$.ajax({
			type:"POST",
			url:'/'+pathname[2]+'/jobUpdate'
			,

			data: {
			'builderPk': pathname[1],
			'buttonValue': value,
				'comment' : $("#commentBox").val(),
				'csrfmiddlewaretoken' : $("input[name=csrfmiddlewaretoken]").val()
			},

			success : function(data) {
					$(".alert").append('<div class="alert alert-success alert-dismissable fade in"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Success!</strong> Job Updated</div>')
						setTimeout(function(){
								var url="/builder/"+data[0][5]+"/home"
						window.open(url,'_self')
					},3000); 
			}

		})	
	

		event.preventDefault();

});
		
	


})

