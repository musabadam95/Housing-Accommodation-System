$(document).ready(function()
{
    var pathnamea = window.location.pathname;
    var pathname=pathnamea.split("/");
			//checks is session exists
			function log(){
				var csrf = "input[name=csrfmiddlewaretoken]" 
				$.ajax({
					type:"POST",
					url: 'sendReport',
					data: {
                        'comment' : $("#commentBox").val(),
						'csrfmiddlewaretoken' : $("input[name=csrfmiddlewaretoken]").val()
					},
				dataType:"html",
					success: function(response){

						$(".alert").append('<div class="alert alert-success alert-dismissable fade in"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Success!</strong> Issue Reported</div>')
							setTimeout(function(){
						var url="/tennant/"+pathname[1]+"/home"
						window.open(url,'_self')
							},3000); 
					},
					error: function(e){
						$(".alert").append('<div class="alert alert-danger alert-dismissable fade in"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Danger!</strong> Error in reporting , Please try again later</div>')						
					},
					

				})	
				event.preventDefault();
			}
			$(".registerform").submit(function(){
				log()

			})


		});
