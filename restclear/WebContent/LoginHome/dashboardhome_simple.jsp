<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>MyTrade</title>

    <!-- Bootstrap Core CSS -->
    <link href="./LoginHome/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="./LoginHome/css/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="./LoginHome/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="./LoginHome/css/startmin.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="./LoginHome/css/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="./LoginHome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    
    <!-- DataTables CSS -->
     <link href="./LoginHome/css/dataTables/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="./LoginHome/css/dataTables/dataTables.responsive.css" rel="stylesheet">
    
  
    
	<!-- my style sheet -->
	 <link href="./LoginHome/css/mycustomcss.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  

</head>
<body>

<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">PivotMaster</a>
        </div>

        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                
        <!-- Top Navigation: Left Menu -->
        <ul class="nav navbar-nav navbar-left navbar-top-links">
            <li id="homeb"><a href="#"><i class="fa fa-home fa-fw"></i> Home</a></li>
        </ul>
		
		<ul class="nav navbar-nav navbar-left navbar-top-links">
            <li id="min15live"><a href="#"><i class="fa fa-download fa-fw"></i> LiveData</a></li>
        </ul>
		
		
		
            <div class="onoffswitch nav navbar-nav navbar-left navbar-top-links">
              <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch" >
              <label class="onoffswitch-label" for="myonoffswitch">
                 <span class="onoffswitch-inner"></span>
                 <span class="onoffswitch-switch"></span>
              </label>
        
        </div>
		
		<ul class="nav navbar-nav  navbar-left navbar-top-links">
            <li><a id="Livesclicktatusval" href="#"><i class="fa fa-hourglass-o fa-fw"></i>LastUpdate: Unknown </a></li>
        </ul> 
		
		

        <!-- Top Navigation: Right Menu -->
        <ul class="nav navbar-right navbar-top-links">
            
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i> Mohan <b class="caret"></b>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                    </li>
                    <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                    </li>
                    <li class="divider"></li>
                    <li><a id="signout" href="#"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                    </li>
                </ul>
            </li>
        </ul>
		


        <!-- Sidebar -->
        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">

                <ul class="nav" id="side-menu">
                    
                    <li>
                        <a id="dashb" href="#" class="active"><i class="fa fa-dashboard fa-fw"></i> Menu</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-sitemap fa-fw"></i> Defined Strategy<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            
                            <li>
                                <a href="#">Investments <span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a id="Inv-st1" href="#">Strategy 1</a>
                                    </li>
									 <li>
                                        <a id="Inv-st2" href="#">Strategy 2</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
						 <ul class="nav nav-second-level">
                            
                            <li>
                                <a href="#">Swing Trading <span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a id="Swi-st1" href="#">Startegy 1</a>
                                    </li>
									 <li>
                                        <a id="Swi-st2" href="#">Startegy 2</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
						 <ul class="nav nav-second-level">
                            
                            <li>
                                <a href="#">Futures/short Term Trading <span class="fa arrow"></span></a>
                                <ul class="nav nav-third-level">
                                    <li>
                                        <a id="Fst-st1" href="#">Startegy 1</a>
                                    </li>
									 <li>
                                        <a id="Fst-st2" href="#">Startegy 2</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    
					
					<li>
                        <a href="#"><i class="fa fa-sitemap fa-fw"></i> Pivots Strategy<span class="fa arrow"></span></a>
                        <ul class="nav nav-third-level">
                                    <li>
                                        <a id="Pv-Daily" href="#">Daily Pivot</a>
                                    </li>
									 <li>
                                        <a id="Pv-Weekly" href="#">Weekly Pivot</a>
                                    </li>
									 <li>
                                        <a id="Pv-Monthly" href="#">Monthly Pivot</a>
                                    </li>
									 <li>
                                        <a id="Pv-yearly" href="#">Yearly Pivot</a>
                                    </li>
                                </ul>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-sitemap fa-fw"></i>Market Indicators Strategy<span class="fa arrow"></span></a>
                        <ul class="nav nav-third-level">
                                    <li>
                                        <a id="MS-settings" href="#">My settings</a>
                                    </li>
									 <li>
                                        <a id="MS-strategy" href="#">My Strategy</a>
                                    </li>
									
                                </ul>
                    </li>
					
                </ul>
                

            </div>
        </div>
    </nav>

    <!-- Page Content -->
    <div id="page-wrapper">
        <div class="container-fluid">

             
<div class="row">
<!--  Tables starts -->
 <div id="content" class="col-lg-15">


</div>

                                <!--  Tables ends -->
                                <!-- notification panel starts -->
                                
 
    </div>

</div>

  <!-- jQuery -->
<script src="./LoginHome/js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="./LoginHome/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="./LoginHome/js/metisMenu.min.js"></script>

 <!-- DataTables JavaScript -->
        <script src="./LoginHome/js/dataTables/jquery.dataTables.min.js"></script>
        <script src="./LoginHome/js/dataTables/dataTables.bootstrap.min.js"></script>
        
<!-- Custom Theme JavaScript -->
<script src="./LoginHome/js/startmin.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/js/bootstrap-multiselect.js"></script>


      
<!-- My customized Javascript --->
<script src="./LoginHome/js/mycustomjs.js"></script>



</body>
</html>
