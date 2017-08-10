/**
 */
function setStyleOption() {
	htmlEle.style.setProperty( 'outline', "3px solid #45FF17","important");
}
function removeStyleOption( htmlEle ) {
	htmlEle.style.removeProperty("outline");
}

function sendKeys( htmlEle, value ) {
	htmlEle.value = value;

}