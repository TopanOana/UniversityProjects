$(document).ready(function(){
	$("img").hide();
	$(".startGame").click(function(){
		startGame();
	});
});

var imgs = [
	"dog1.jpg",
	"dog2.jpg",
	"dog3.jpg"
];

function startGame(){
	var timesClicked = 0;
	
	$(".startGame").hide();
	
	var interval = setInterval(function(){
		console.log("here?");
		var index = Math.floor(Math.random() * imgs.length);
		var image = imgs[index];
		
		$("img").css({ "left": Math.random() * window.outerWidth,  "top": Math.random() * window.outerHeight})
		$("img").attr("src",image);
		$("img").show();
	}, 1000);
	
	$("img").click(function(){
		$(this).hide();
		timesClicked+=1;
		console.log(timesClicked);
		$(".score").text("Score"+timesClicked);
		if(timesClicked===10){
			$("h1").text("You have pet all the good boys!!!");
			$("img").hide();
			clearInterval(interval);
		}
	});
	
	
	
	
	
	
	
}