<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<script src="resources/jquery-3.1.1.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="resources/bootstrap-3.3.2-dist/css/bootstrap.css">
<script src="resources/bootstrap-3.3.2-dist/css/bootstrap.min.js"></script>
</head>
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</P>
	<!-- Standard button -->
	<button type="button" class="btn btn-default">Default</button>

	<!-- Provides extra visual weight and identifies the primary action in a set of buttons -->
	<button type="button" class="btn btn-primary">Primary</button>

	<!-- Indicates a successful or positive action -->
	<button type="button" class="btn btn-success">Success</button>

	<!-- Contextual button for informational alert messages -->
	<button type="button" class="btn btn-info">Info</button>

	<!-- Indicates caution should be taken with this action -->
	<button type="button" class="btn btn-warning">Warning</button>

	<!-- Indicates a dangerous or potentially negative action -->
	<button type="button" class="btn btn-danger">Danger</button>

	<!-- Deemphasize a button by making it look like a link while maintaining button behavior -->
	<button type="button" class="btn btn-link">Link</button>
</body>
</html>
