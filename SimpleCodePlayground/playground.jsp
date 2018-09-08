<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Code Playground</title>

<style>
.split {
	position: absolute;
	width: 50%;
	height: 100%;
	overflow-x: hidden;
	box-sizing: content-box;
	z-index: 1;
}

iframe {
	width: 100%;
	height: 100%;
	border: 3px dashed pink;
}

.rightPage {
	right: 0px;
}

.leftPage {
	left: 0px;
}

.ddc {
	position: relative;
	border: none;
	height: 100%;
	margin: 5px;
}

textarea {
	background-color: rgba(100, 100, 100, 0.5);
	color: white;
	font-size: 20px; border : none;
	width: 100%;
	height: 100%;
	resize: none;
	border: none;
}

.buttonsGroup {
	margin: 5px;
	background-color:  rgba(94, 150, 255, 1);
}
.custombutton{
	display: inline;
	width: 200px;
	height: 100px;
	padding: 8px 8px 8px 8px;
	font-size: 25px;
}
.custombutton:hover{
	display: inline;
	width: 200px;
	height: 100px;
	padding: 8px 8px 8px 8px;
	font-size: 25px;
	color:  red;
	cursor: pointer;
}
</style>
<script>
	let staticpath = "/tmp_pic/text/demo.html";
	function init() {
		document.getElementById("clearbtn")
				.addEventListener("click", clearText);
		document.getElementById("runbtn").addEventListener("click",
				getTextContent);
		document.getElementById("showcontent").src = staticpath;
	}
	function getTextContent() {
		let txtarea = document.getElementById("mainText");
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				let frameElm = document.getElementById("showcontent");
				frameElm.src = frameElm.src;
				//frameElm.contentWindow.location.reload(); also work.
			}
		};
		xhttp.open("POST", "playground", true);
		xhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		console.log(txtarea.value);

		xhttp.send("textareaValue=" + txtarea.value);

	}
	function clearText() {
		let txtarea = document.getElementById("mainText");
		let originalContent = "<!DOCTYPE html>" + "\n" + "<html>" + "\n"
				+ "<body>" + "\n" + "</body>" + "\n" + "</html>";
		txtarea.value = originalContent;
		let frameElm = document.getElementById("showcontent");
		frameElm.src = frameElm.src;
	}
	document.addEventListener("DOMContentLoaded", init)
</script>
</head>

<body>
	<div class="split leftPage">
		<div class="buttonsGroup">
			<div class ="custombutton" id="runbtn"> 
			Run
			</div> 
			<div class ="custombutton" id="clearbtn">
			Reset
			 </div>
		</div>
		<p>when there is "+" symbol, the text in textarea would error.</p>
		<div class="ddc">
			<textarea id="mainText" spellcheck="false">aaaaaa</textarea>
		</div>
	</div>
	<div class="split rightPage">
		<iframe id="showcontent"> </iframe>
	</div>


</body>

</html>