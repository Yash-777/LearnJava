<html>
<head>
  <title>${title}
</head>
<body>
  <h1>${title}</h1>

  <ul>
	<#list countries as country>
		${country_index + 1}. ${country}
	</#list>
  </ul>

</body>
</html>