<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Welcome to album</title>
<style>

/* table */
.table {
	display: table;
	border-collapse: collapse;
}

/* tr */
.row {
	display: table-row;
	border: solid 1px red;
}

/* td , th */
.cell1 {
	display: table-cell;
	width: 300px;
	border-right: 1px dotted #fff;
	background: #fcd6d6;
}

.cell2 {
	display: table-cell;
	vertical-align: top;
	width: 180px;
	padding-left: 10px;
	border-right: 1px dotted #fff;
	background: #eff8ff;
}

.cell3 {
	display: table-cell;
	vertical-align: middle;
	width: 180px;
	padding-left: 10px;
	background: #effff0;
}
</style>

<script>
    function deleteRow(elm) {
        var currentindex = elm.value;
        var xhttp =  new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var parnode = elm.parentNode.parentNode.parentNode;
                var par_parnode = parnode.parentNode;

                par_parnode.removeChild(parnode);
            }
        };
        xhttp.open("POST", "myalbum", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("filepath=" + elm.name);

    }
</script>
</head>
<body>
	<c:forEach var="single_src" items="${src_lists}">
		<div class="table">
			<div class="row">
				<div class="cell1">
					<img src="${single_src.path}" style = "width:300px;" />
				</div>
				<div class="cell2" >
					<div>
						FileName : ${single_src.filename}
					</div>
					<br>
					<div style="visibility:hidden">
						name: ${single_src.name}
					</div>
					<br>  
					<div>
						Size: ${single_src.size}
					</div>
					<br>
					<div style="visibility:hidden">
						path: ${single_src.path}
					</div>
					<br> 

				</div>
				<div class="cell3">
				 	<input type="button" value="Delete" name="${single_src.name}" onclick="deleteRow(this)">
				</div>				
				<br />
			</div>
		</div>
	</c:forEach>
</body>
</html>