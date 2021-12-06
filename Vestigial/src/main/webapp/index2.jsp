<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>

<html lang="en">
	<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Vestigial</title>

    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/font-awesome.min.css"/>
    <link rel="stylesheet" href="css/animate.css"/>
	<link rel="stylesheet" href="css/style.css" />
	<link rel="stylesheet" href="css/main.css" />
	<link rel="shortcut icon" type="image/jpg" href="images/favicon.png"/>
		

    <script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCZXJBVDf7R4JqmSpopVPoduIGWx1IwpBM"></script>
    <script type="text/javascript" src="js/plugins.js"></script>

		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

	</head>
	<body>
	<div class="svg-wrap">
      <svg width="64" height="64" viewBox="0 0 64 64">
        <path id="arrow-left" d="M26.667 10.667q1.104 0 1.885 0.781t0.781 1.885q0 1.125-0.792 1.896l-14.104 14.104h41.563q1.104 0 1.885 0.781t0.781 1.885-0.781 1.885-1.885 0.781h-41.563l14.104 14.104q0.792 0.771 0.792 1.896 0 1.104-0.781 1.885t-1.885 0.781q-1.125 0-1.896-0.771l-18.667-18.667q-0.771-0.813-0.771-1.896t0.771-1.896l18.667-18.667q0.792-0.771 1.896-0.771z"></path>
      </svg>

      <svg width="64" height="64" viewBox="0 0 64 64">
        <path id="arrow-right" d="M37.333 10.667q1.125 0 1.896 0.771l18.667 18.667q0.771 0.771 0.771 1.896t-0.771 1.896l-18.667 18.667q-0.771 0.771-1.896 0.771-1.146 0-1.906-0.76t-0.76-1.906q0-1.125 0.771-1.896l14.125-14.104h-41.563q-1.104 0-1.885-0.781t-0.781-1.885 0.781-1.885 1.885-0.781h41.563l-14.125-14.104q-0.771-0.771-0.771-1.896 0-1.146 0.76-1.906t1.906-0.76z"></path>
      </svg>
    </div>


    <!-- MAIN CONTENT -->

   <div class="container-fluid">

    <!-- HEADER -->

    <section id="header">

      <!-- NAVIGATION -->
      <nav class="navbar navbar-fixed-top navbar-default bottom">
        <div class="container">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menu">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#header">Vestigial</a>
          </div><!-- /.navbar-header -->

          <div class="collapse navbar-collapse" id="menu">
            <ul class="nav navbar-nav navbar-right">
              <li><a href="#header">Inicio</a></li>
              <li><a href="#about">Información</a></li>              
			  <li><a href="#team">Equipo</a></li>
              <li><a href="#contact">Contacto</a></li>
            </ul>
          </div> <!-- /.navbar-collapse -->
        </div> <!-- /.container -->
      </nav>
      
      		<a href="#service" class="mouse-icon hidden-xs">
			<div class="wheel"></div>
		</a>
      

      <!-- SLIDER -->
      <div class="header-slide">
        <section>
          <div id="loader" class="pageload-overlay" data-opening="M 0,0 0,60 80,60 80,0 z M 80,0 40,30 0,60 40,30 z">
            <svg xmlns="http://www.w3.org/2000/svg" width="100%" height="100%" viewBox="0 0 80 60" preserveAspectRatio="none">
              <path d="M 0,0 0,60 80,60 80,0 Z M 80,0 80,60 0,60 0,0 Z"/>
            </svg>
          </div> <!-- /.pageload-overlay -->
          
          <div class="image-slide bg-fixed">
            <div class="overlay">
              <div class="container">
                <div class="row">
                  <div class="col-md-12">

                    <div class="slider-content">
                      <h1></h1>
                      <p>Texto Sobre Nosotros y la aplicacion</p>
                       <br></br>
                      	        <a href="#about" class="main-stroked-button">Leer más</a>
                  				<a href="Login" class="main-stroked-button">Acceder</a>
                    </div>

                  </div> <!-- /.col-md-12 -->
                </div> <!-- /.row -->
              </div> <!-- /.container -->
            </div> <!-- /.overlay -->
          </div> <!-- /.image-slide -->

          <nav class="nav-slide">
            <a class="prev" href="#prev">
              <span class="icon-wrap">
                <svg class="icon" width="32" height="32" viewBox="0 0 64 64">
                  <use xlink:href="#arrow-left">
                </svg>
              </span>
              <div>
                <span>Foto anterior</span>
                <h3>...</h3>
                <p>...</p>
                <img alt="Previous thumb">
              </div>
            </a>
            <a class="next" href="#next">
              <span class="icon-wrap">
                <svg class="icon" width="32" height="32" viewBox="0 0 64 64">
                  <use xlink:href="#arrow-right">
                </svg>
              </span>
              <div>
                <span>Foto siguiente</span>
                <h3>...</h3>
                <p>...</p>
                <img alt="Next thumb">
              </div>
            </a>
          </nav>
        </section>

        <script type="text/javascript">
        var dataHeader = [
                            {
                              bigImage :"images/slide-1.jpg",
                              title : "Gestione sus contactos",
							  author : "Hokuro Inc."
                            },
                            {
                              bigImage :"images/slide-2.jpg",
                              title : "Visualice sus Eventos",
                              author : "Hokuro Inc."
                            },
                            {
                              bigImage :"images/slide-3.jpg",
                              title : "Organice sus tareas con nosotros",
                              author : "Hokuro Inc."
                            }
                        ],
            loaderSVG = new SVGLoader(document.getElementById('loader'), {speedIn : 600, speedOut : 600, easingIn : mina.easeinout});
            loaderSVG.show()
        </script>

      </div><!-- /.header-slide -->
    </section>

    <!-- HEADER END -->


    <!-- ABOUT -->

    <section id="about" class="dark">
      <header class="title">
        <h2>Mayor <span>Seguridad,</span> Mayor <span>Control.</span></h2>
        <p>Más de 700.000 clientes disfrutan trabajan diariamente con nuestra <strong>aplicación segura</strong> para la gestión de sus eventos y agendas de contacto.</p>
      </header>

      <div class="container">
        <div class="row table-row">
          <div class="col-sm-6 hidden-xs">
            <div class="section-content">
              <div class="big-image" style="background-image:url(images/1.png)"></div>
            </div>
          </div>

          <div class="col-sm-6">
            <div class="section-content">
              <div class="about-content left animated" data-animate="fadeInLeft">
                <div class="about-icon"><i class="fa fa-code"></i></div>
                <div class="about-detail">
                  <h4>Más control</h4>
                  <p>Crea y elimina en cualquier momento tus tarjetas. Así las mantendrás seguras si no las encuentras o utilizas</p>
                </div>
              </div>

              <div class="about-content left animated" data-animate="fadeInLeft">
                <div class="about-icon"><i class="fa fa-desktop"></i></div>
                <div class="about-detail">
                  <h4>Clave dinámica</h4>
                  <p>La clave de acceso cambia automáticamente cada 6 meses, de manera que no es el mismo siempre. Así estarás más protegido.</p>
                </div>
              </div>

              <div class="about-content left animated" data-animate="fadeInLeft">
                <div class="about-icon"><i class="fa fa-cube"></i></div>
                <div class="about-detail">
                  <h4>Diseño fácil</h4>
                  <p>Con el nuevo diseño de nuestra página web no erncontrarás ningún problema a la hora de gestionar tu cuenta, lo que te permitirá reducir enormente los tiempos de uso.</p>
                </div>
              </div>
            </div>
          </div>
        </div> <!-- /.row table-row -->
      </div> <!-- /.container -->
    </section>
	
	
	   


    <!-- TEAM -->

    <section id="team" class="dark">
      <header class="title">
        <h2>Equipo <span>Desarrollo</span></h2>
        <p>Somos un equipo de trabajo que lleva varios años trabajando juntos, y se han juntado para realizar varios proyectos, entre ellos, Vestigial. Aquí podréis encontrar un poco de información de cada uno de los integrantes.</p>
      </header>
      
      <div class="container">
        <div class="row table-row" >
          <div class="col-md-3 col-sm-6 text-center">
            <div class="wrap animated" data-animate="fadeInDown">
              <div class="img-team">
                <img src="images/team-1.png" alt="" class="img-circle">
              </div>

              <h3>Christian</h3>
              <h5>CEO & Backend Developer.</h5>

              <p>Encargado de coordinar y dirigir al equipo en todos los proyectos, se responsabiliza de que el proyecto web se resuelva satisfactoriamente asi
              como del desarrollo del Backend.</p>

              <div class="team-social">
                <ul class="list-inline social-list">
                  <li><a href="https://twitter.com/HokuroInc" class="fa fa-twitter"></a></li>
                  <li><a href="#" class="fa fa-linkedin"></a></li>
                  <li><a href="#" class="fa fa-facebook"></a></li>
                  <li><a href="#" class="fa fa-google-plus"></a></li>
                </ul> 
              </div>                
            </div>
          </div>

          <div class="col-md-3 col-sm-6 text-center">
            <div class="wrap animated" data-animate="fadeInDown"> 
              <div class="img-team">
                <img src="images/team-2.png" alt="" class="img-circle">
              </div>

              <h3>Javi</h3>
              <h5>Diseñador Gráfico & Frontend Developer.</h5>

              <p>Encargado de realizar bocetos, prototipos y diseños del sistema, asi como del desarrollo usando frameworks.</p>

              <div class="team-social">
                <ul class="list-inline social-list">
                  <li><a href="https://twitter.com/HokuroInc" class="fa fa-twitter"></a></li>
                  <li><a href="#" class="fa fa-linkedin"></a></li>
                  <li><a href="#" class="fa fa-facebook"></a></li>
                  <li><a href="#" class="fa fa-google-plus"></a></li>
                </ul> 
              </div>                
            </div>
          </div>

          <div class="col-md-3 col-sm-6 text-center">
            <div class="wrap animated" data-animate="fadeInDown"> 
              <div class="img-team">
                <img src="images/team-5.png" alt="" class="img-circle">
              </div>

              <h3>Rubén</h3>
              <h5>Web Developer & Feature Researcher.</h5>

              <p>Encargado de crear la estructura de la web y investigar sobre nuevas funcionalidades.</p>

              <div class="team-social">
                <ul class="list-inline social-list">
                  <li><a href="https://twitter.com/HokuroInc" class="fa fa-twitter"></a></li>
                  <li><a href="#" class="fa fa-linkedin"></a></li>
                  <li><a href="#" class="fa fa-facebook"></a></li>
                  <li><a href="#" class="fa fa-google-plus"></a></li>
                </ul> 
              </div>                
            </div>
          </div>

        </div>
        
        <div class="row table-row">
                   
          <div class="col-md-3 col-sm-6 text-center">
            <div class="wrap animated" data-animate="fadeInDown"> 
              <div class="img-team">
                <img src="images/team-3.png" alt="" class="img-circle">
              </div>

              <h3>Ángel</h3>
              <h5>Fullstack Developer & Tester.</h5>

              <p>Encargado de servir de nexo entre el Backend y el Frontend y de la realización de tests.</p>

              <div class="team-social">
                <ul class="list-inline social-list">
                  <li><a href="https://twitter.com/HokuroInc" class="fa fa-twitter"></a></li>
                  <li><a href="#" class="fa fa-linkedin"></a></li>
                  <li><a href="#" class="fa fa-facebook"></a></li>
                  <li><a href="#" class="fa fa-google-plus"></a></li>
                </ul> 
              </div>                
            </div>
          </div>

          <div class="col-md-3 col-sm-6 text-center">
            <div class="wrap animated" data-animate="fadeInDown"> 
              <div class="img-team">
                <img src="images/team-4.png" alt="" class="img-circle">
              </div>

              <h3>Juanan</h3>
              <h5>Web Designer and Taiga Lorekeeper.</h5>

              <p>Encargado de codificar el diseño y la imagen de la pagina web asi como de la correcta documentación del proyecto en Taiga.</p>

              <div class="team-social">
                <ul class="list-inline social-list">
                  <li><a href="https://twitter.com/HokuroInc" class="fa fa-twitter"></a></li>
                  <li><a href="#" class="fa fa-linkedin"></a></li>
                  <li><a href="#" class="fa fa-facebook"></a></li>
                  <li><a href="#" class="fa fa-google-plus"></a></li>
                </ul> 
              </div>                
            </div>
          </div>

          <div class="col-md-3 col-sm-6 text-center">
            <div class="wrap animated" data-animate="fadeInDown"> 
              <div class="img-team">
                <img src="images/team-6.png" alt="" class="img-circle">
              </div>

              <h3>Pedro Pablo</h3>
              <h5>Server Manager & Database Developer.</h5>

              <p>Encargado de la configuración del servidor e infraestructuras y del diseño e implementación de la Base de Datos.</p>

              <div class="team-social">
                <ul class="list-inline social-list">
                  <li><a href="https://twitter.com/HokuroInc" class="fa fa-twitter"></a></li>
                  <li><a href="#" class="fa fa-linkedin"></a></li>
                  <li><a href="#" class="fa fa-facebook"></a></li>
                  <li><a href="#" class="fa fa-google-plus"></a></li>
                </ul> 
              </div>                
            </div>
          </div>
        
        
        
        </div>
      </div> <!-- /.container -->
    </section>
	
	   
    <!-- FOOTER CONTACT -->

     <section id="contact" class="dark">
      <header class="title">
        <h2>Contacta con <span>Nosotros</span></h2>
        <p>Podremos ayudarte con tus dudas, consultas operativas, ayuda técnica, etc.</p>
      </header>

      <div class="container">
        <div class="row">
          <div class="col-md-8 animated" data-animate="fadeInLeft">

          </div>

          <div class="col-md-4 animated" data-animate="fadeInRight">
            <address>
                <span><i class="fa fa-map-marker fa-lg"></i> Campus of Rabanales, Córdoba</span>
                <span><i class="fa fa-phone fa-lg"></i> 957 26 26 28</span>
                <span><i class="fa fa-envelope-o fa-lg"></i> <a href="mailto:contact@example.com">hokuroincorporated@gmail.com</a></span>
                <span><i class="fa fa-globe fa-lg"></i> <a href="http://support.example.com">support.hokuro.com</a></span>
            </address>
          </div>
		  
        </div>
      </div>
    </section>

    <section id="footer">
      <div class="container">
        <div class="row">
          <div class="col-md-12 text-center">
            <p>Made BY <i class="fa fa-heart"></i> <a href="https://github.com/Hokuro-Inc">Hokuro Inc</a></p>
            <p><small>Ingeniería de Sistemas Móviles</small></p>
          </div>
        </div>
      </div>
    </section>

   </div><!-- /.container-fluid -->
    
    <!-- SCRIPT -->
    <script type="text/javascript" src="js/main.js"></script>
	</body>
</html>