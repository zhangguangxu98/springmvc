<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>个人管理系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/adminhome.css">
	<link rel="stylesheet" type="text/css" href="css/adminupanddownloadlotteryresult.css">
	<link rel="stylesheet" type="text/css" href="css/datepicker.css">
	<script type="text/javascript" src="js/adminhome.js"></script>
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		//下载模板
	    function downTemplate(){
	        window.location.href="/springmvc/adminlotteryresult/template.xlsx";
	    }
	</script>
  </head>
  <jsp:include page="/adminhome/adminheader.jsp"/>
  <body>
	   <div class="adminhome21">
             <table>
                    <tr>
                         <td>
                             <div>
                           		<jsp:include page="/adminhome/adminnavigator.jsp"/> 
                             </div>
                     	      </td>
                         <td  class="exceltest">
                             <div class="exceltest1">
                                  <form action="adminuploadlotteryresult.do" method="post" name="uploadform" enctype="multipart/form-data">
                                  <table id="exceltest3">
                                         <tr>
                                              <td class="exceltest4">
                                                  	上传彩票结果:
                                              </td>
                                         </tr>
                                  </table>
                                  <table id=exceltest5> 
                                         <tr>
                                             <td>
                                                  	上传文件:
                                             </td>
                                             <td>
                                                 <input type="file" name="myFile" id="myFile" />
                                             </td>
                                             <td>
                                             	 <input type="button" value="下载模板" onclick="downTemplate();" />
                                             </td>
                                             <td>
                                             	 <font color="red"><c:out value="${error}"></c:out></font>
                                             	 <font color="red"><c:out value="${sucess}"></c:out></font>
                                             </td>
                                         </tr>
                                         <tr>
                                             <td></td>
                                             <td>
                                                 <input type="submit" value="上传" style="width:70px;height:30px"/>
                                                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                 <input type="reset" value="重置" style="width:70px;height:30px"/>
                                             </td>
                                         </tr>
                                   </table>
                                      </form> 
                                     <hr width=100% size=3 color=#5151A2 style="filter: progid:DXImageTransform.Microsoft.Shadow(color #5151A2, direction :145, strength :   15 )">    
                                     <form action="admindownloadlotteryresult.do" method="post">
                                  <table id="exceltest6">
                                         <tr>
                                              <td class="exceltest7">
                                                  	下载彩票结果:
                                              </td>
                                         </tr>
                                  </table>
                                  <table id=exceltest8>
                                         <tr>
                                             <td>
                                                  	下载文件:
                                             </td>
                                             <td>
                                                 <input type="submit" value="下载" style="width:70px;height:30px"/>
                                             </td>
                                             <td>
                                                 <font color="red"><c:out value="${downloaderror}"></c:out></font>
                                             </td>
                                         </tr>
                                   </table>
                                      </form> 
                             </div>
                         </td>
                    </tr>
             </table>
	  </div>
  </body>
  <jsp:include page="/adminhome/adminfooter.jsp"/>
</html>
