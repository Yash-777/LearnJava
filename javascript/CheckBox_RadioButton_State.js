/**
 * Ex Site : http://browsershots.org/
 * 
 * UnCheck All Check Boxes
 */

/* ===== UnCheck All Check Boxes ===== 
 * <input type="checkbox" name="checkBox" >one<br>
 * */
// JQuery > https://stackoverflow.com/a/39326451/5081877
$('input:checkbox').attr('checked',false);

// Javascript
var checkBoxes = document.querySelectorAll('input');
console.dir(checkBoxes);

for(i= 0; i < checkBoxes.length; i++) {
	if(checkBoxes[i].type = "checkBox") {
		checkBoxes[i].checked = false;
	}
}

/* ===== Using map generically querySelectorAll ===== */
var elems = document.querySelectorAll('select option:checked');
var values = Array.prototype.map.call(elems, function(obj) {
	return obj.value;
});

// Radio Button:<input type="radio" id="myRadioID" onclick="javascript:change();"/>
var checked = 0;
function change() {
	if (checked === 0) {
		checked++;
	}else {
		document.getElementById("myRadioID").checked = false;
		checked = 0;
	}
}

// OnClick change images. « https://stackoverflow.com/a/32377469/5081877
/*
<img id="myImage" onclick="changeImage()" src="D:\\Images\\Img1.jpg" width="100" height="100">
<p id="myImageText">10</p>
</image>
*/
function changeImage() {
	var image = document.getElementById('myImage');
	var txt = document.getElementById('myImageText');
	if (image.src.match("Img1")) {
		image.src = "D:\\Images\\Img2.jpg";
		txt.innerHTML = '20';
	} else {
		image.src = "D:\\Images\\Img1.jpg";
		txt.innerHTML = 10;
	}
}

// HTML ELEMENT Empty Check « https://stackoverflow.com/a/33148191/5081877
