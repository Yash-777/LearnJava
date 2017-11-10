/*
 * document.ready() function is called as soon as DOM is loaded where body.onload() function is called
 * when everything gets loaded on the page that includes DOM, images and all associated resources of the page.
 */

window.onload=function(){
	
}
/*
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js">
</script>
It reduces the load from your server. by distributing over different servers.
	http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js
	http://ajax.microsoft.com/ajax/jquery/jquery-1.9.1.min.js
	http://code.jquery.com/jquery-1.9.1.min.js
 */
// Dollar Sign is nothing but it's an alias for JQuery
$(document).ready(function(){
  console.log('Page loaded Completly.');
});
jQuery(document).ready(function(){
  console.log('Page loaded Completly.');
});
/* jQuery.noConflict
 * jQuery uses the $ sign as a shortcut for jQuery.
 * AngularJS and some other languages also uses same shortcut $() as their global function
 * which creates conflict and one of them might stop working.
	To avoid conflicts jQuery team have implemented the noConflict() method.
	You can also use your own specific character in the place of $ sign in jQuery.
*/
var $j = jQuery.noConflict();
//Use jQuery via jQuery(...)
$j(document).ready(function(){
	$j("div").hide();
});

$('table#productTable').on('click', '.remove-row', function() {
  $(this).closest('tr').remove(); // https://stackoverflow.com/a/11553788/5081877
});
