<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/bootstrap.min.css" media="screen" />
<link rel="stylesheet" href="/toastr/toastr.min.css" media="screen" />
<title>SKU Entry Form</title>
<style>
.name {
	font-size: 25px;
	font-weight: bold;
	color: #fff;
	padding-top: 12px;
}
</style>

</head>
<body>

	<div class="container">
		<div class="page-header" id="banner">
			<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="" href="#"><span class="name pull-left">LOGO</span></a>
				</div>
			</div>
			</nav>
		</div>
		<div class="row">
			<hr />
		</div>

		<div class="row">
			<div class="col-lg-6 col-md-6 col-sm-12">
				<form enctype="multipart/form-data" id="frm">
					<h4 style="color: rgb(24, 188, 156); font-weight: bold;">SKU
						Entry Form</h4>
					<div class="form-group">
						<label for="vendor_name">Select Vendor</label>
						<div class="dropdown">
							<select name="vendor" id="vendor">
								<option value=""></option>
								<c:forEach items="${vendors}" var="vendor">
									<option value="${vendor.key}">${vendor.value}</option>
								</c:forEach>
							</select>
						</div>

						<div class="form-group">
							<label for="product_sku">Enter Product SKU</label> <input
								type="text" class="form-control" id="skustr" name="skustr" />
						</div>

						<p class="text-center">OR</p>
						<div class="form-group">
							<label for="upload-file-input">Upload CSV</label> <input
								type="file" name="files" accept=".csv" id="upload" />
						</div>


						<hr style="border-top: 2px dashed #ecf0f1;" />

						<div class="form-group" id="divskutypes">
							<label for="sku_type">SKU Types to Create</label>
							<div class="checkbox">
								<label><input type="checkbox" value="Hotline"
									name="skutypes">Hotline</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" value="HD Replacement"
									name="skutypes">HD Replacement</label>
							</div>
						</div>

					</div>

					<button type="button" class="btn btn-primary" id="btn_submit">START</button>
					<br /> <br />
				</form>
			</div>
		</div>
	</div>

	<div style="margin-bottom: -20px; border-radius: 0;"
		th:fragment="footer" class="navbar navbar-default "
		xmlns:th="http://www.w3.org/1999/xhtml">
		<p class="navbar-text pull-left">
			&copy;
			<script>
				var date = new Date();
				document.write(date.getFullYear() + " ")
			</script>
			<a href="#" target="_blank">Company Name</a>
		</p>
	</div>


	<script src="/jquery.js" type="text/javascript"></script>
	<script src="toastr/toastr.min.js" type="text/javascript"></script>


	<script type="text/javascript">
		var validationmessage = '';

		$(document).ready(
				function() {

					$('input[type=checkbox]').bind(
							'change',
							function() {
								if (getcheckedboxes('divskutypes').indexOf(
										'HD Replacement') > -1) {
									$('input[type=checkbox]').prop('checked',
											'checked');
								}
							});

					$("#btn_submit").click(function(event) {
						if (validateform()) {
							submitForm();
						} else {
							toastr['error'](validationmessage);
							validationmessage = '';
						}

					});
				});

		function validateform() {
			var isvalidated = true;
			if ($('#vendor').val() == '') {
				validationmessage = '<p>Please select a vendor</p>';
				isvalidated = false;
			}

			var uploadedFile = $("#upload")[0].files.length;

			if ($('#skustr').val() == '' && uploadedFile == 0) {
				validationmessage += '<p>Please enter sku or choose a sku file</p>';
				isvalidated = false;
			}

			if ($('#skustr').val() != '' && uploadedFile == 1) {
				validationmessage += '<p>You have entered sku and selected file also, please choose only one</p>';
				isvalidated = false;
			}

			var skutypes = getcheckedboxes('divskutypes');
			if (skutypes == '') {
				validationmessage += '<p>Please select sku types</p>';
				isvalidated = false;
			}

			return isvalidated;
		}

		function submitForm() {
			var form = $('#frm')[0];
			var data = new FormData(form);

			$.ajax({
				url : '/api/create',
				type : 'POST',
				enctype : "multipart/form-data",
				data : data,
				processData : false,
				contentType : false,
				success : function(result) {
					window.location = '/download/' + result;
				},
				error : function(e) {
					toastr['error'](e.responseText);
				}
			});
		}

		function getcheckedboxes(parentelementid) {
			var selected = [];
			$('#' + parentelementid + ' input:checked').each(function() {
				selected.push($(this).attr('value'));
			});
			return selected;
		}
	</script>
</body>
</html>