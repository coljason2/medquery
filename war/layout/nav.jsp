<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">橫幅</span> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/index">藥品查詢</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<form class="navbar-form navbar-left" action="query" method="get">
				<div class="form-group">
					<input type="text" name="querystring" placeholder="輸入要查詢藥名或健保碼"
						class="form-control">
				</div>
				<button type="submit" class="btn btn-success">查詢</button>
			</form>
		</div>
	</div>
</nav>