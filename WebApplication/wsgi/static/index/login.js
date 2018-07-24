


$(document).ready(function()
{ 

			//checks is session exists

			function log(){
				var formData = $(".login").serializeArray();
				var csrf = "input[name=csrfmiddlewaretoken]" 
				$.ajax({
					type:"POST",
					url: '/Login/login',
					data: {
						formData,
						'csrfmiddlewaretoken' : $("input[name=csrfmiddlewaretoken]").val()
					},

					success: function(response){
						
						$(".alert").append('<div class="alert alert-success alert-dismissable fade in"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Success!</strong> Welcome back</div>')
						setTimeout(function(){
							if(response[0][3]=="Builder"){

								var url="/builder/"+response[0][5]+"/home"
						window.open(url,'_self')

							}else{
	var url="/tennant/"+response[0][4]+"/home"
						window.open(url,'_self')


							}
	
						},1000); 

					},
					error: function(e){
						$(".alert").append('<div class="alert alert-danger alert-dismissable fade in"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Danger!</strong> Incorrect login, Please check login combination.</div>')
						$(".logID").val("")
						$(".logPass").val("")
					}

				})	
				event.preventDefault();
			}
			$(".login").submit(function(){
				log()

			})


		});
