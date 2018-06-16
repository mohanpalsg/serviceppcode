<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import = "java.io.*,java.util.*" %>

<script type="text/javascript">



        function OthertechFormChange() {
          	
        	
        	
        	
        	$.post(
                    "InvestStrategyTrend", 
                    {
                     
                     
                    	diffselected : $("#highvalue").val()
					
                     
                    },
                    function(result) {
                    	 $('#content').html(result);
                });
        	 	
        	
        	  
        } 
        
        

        
        
        
        $('#dataTables-techlive15').DataTable({
            responsive: true
    });
        

        
     	 
</script>




<br>
<br>
<br>
<div class="panel panel-primary">
                            <div class="panel-heading">
                                <p>Technical Indicators Live - Trend Strategy</p>
                            </div>
                             <div class="panel-body">
							 
							 
							 
							  <div class="dataTable_wrapper col-lg-10">
<table class="table table-striped table-bordered table-hover "  id="dataTables-techlive15">
       
<thead>

<tr>
       <td>Symbol</td>
       <td>LTP</td>
       <td>Support</td>
       <td>Diff-%</td>
       <td>Resistance</td>
       <td>TrendDirection</td>
       <td>Returns-%</td>
       
     
      
    </tr>
 </thead>
 <tbody>
<c:forEach var="entry" items="${stocklist}" >
      
 <tr>
 
<td>${entry.getStocksymbol()}</td>
<td>${entry.getLtp()}</td>
<td>${entry.getSupport()}</td>
<td>${entry.getDiffpercent()}</td>
<td>${entry.getResistance()}</td>
<td>${entry.getTrendstatus()}</td>
<td>${entry.getPotential()}</td>


</tr>
    
</c:forEach>
</tbody>   

</table>
   </div>
   
    <div class="dataTable_wrapper col-lg-2">
	  <div class="panel panel-primary">
	  
	   <div class="panel-heading">
                                <p>Applied Filters</p>
                            </div>
							 <div class="panel-body">
							 
							 <div class="panel panel-info">
							  
						 
							 <div class="panel panel-info">
                            <div class="panel-heading">
                                <p>Range Filters</p>
                            </div>
                             <div class="panel-body">          
         

Price Diff<input id="diffslider" type="range" value="<c:out value="${diffselected}"/>" min="0" max="200" step="20"  onchange="OthertechFormChange()" oninput="highvalue.value =diffslider.value">
<output name="diffoname" id="highvalue"><c:out value="${diffselected}"/></output> 
<br>





</div>
</div>
</div>
</div>
</div>
</div>
</div>