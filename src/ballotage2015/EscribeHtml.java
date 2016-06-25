/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ballotage2015;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 *
 * @author vaio
 */
public class EscribeHtml {
  String cad="ELECCIONES GENERALES 2015";      
 
  //crea el html nacional 
public void BdToHtml(String nombreArchivo,int idlocalidad) throws SQLException{
     
    Date date = new Date();
    //Caso 1: obtener la hora y salida por pantalla con formato:
    DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
     
     BDPASO2015 bd2=new BDPASO2015();
     
     bd2.abrirConexion();
     
     //datos balotage 
     String votosFPV=bd2.obtenerVotosPresidente(idlocalidad)[0];
     String votosMacri=bd2.obtenerVotosPresidente(idlocalidad)[1];
     System.out.println("votooooooos"+idlocalidad+"--"+votosFPV);
     Long sumaVotosPresidente=Long.parseLong(votosFPV)+Long.parseLong(votosMacri);
     //porcentaje de votos presidente
     BigDecimal porcentajeFPV=this.truncateDecimalPorcentajeMesasEscrutadas(sumaVotosPresidente, Long.parseLong(votosFPV));
     BigDecimal porcentajeMacri=this.truncateDecimalPorcentajeMesasEscrutadas(sumaVotosPresidente, Long.parseLong(votosMacri));
     //fin
     
     
     long MH=bd2.obtenerMesasHabilitadas(idlocalidad);
     long ME=bd2.obtenerMesasEscrutadas(idlocalidad);
     BigDecimal porcentajeMesas=this.truncateDecimalPorcentajeMesasEscrutadas(MH, ME);
     long id1=idlocalidad;
     
     
     String []blanco1=bd2.obtenerVotosPresidente(id1);
     
     
     String blanco=blanco1[4];
     String nulo=blanco1[2];
     String recurrido=blanco1[3];
     //parte de pie de pagina
     long sumaVotos=sumaVotosPresidente+Long.parseLong(blanco)+Long.parseLong(nulo)+Long.parseLong(recurrido);
     BigDecimal porcentaje1=this.truncateDecimalPorcentajeMesasEscrutadas(sumaVotos,sumaVotosPresidente);
     BigDecimal porcentaje2=this.truncateDecimalPorcentajeMesasEscrutadas(sumaVotos,Long.parseLong(blanco));
     BigDecimal porcentaje3=this.truncateDecimalPorcentajeMesasEscrutadas(sumaVotos,Long.parseLong(nulo));
     BigDecimal porcentaje4=this.truncateDecimalPorcentajeMesasEscrutadas(sumaVotos,Long.parseLong(recurrido));
     //fin porcentajes pie de pagina
     
     
     
   long totalelectores=bd2.obtenerElectoresHabilitados(idlocalidad);  
   long totaldevotosgeneral=bd2.obtenerSumaElectoresMesasXlocalidad(idlocalidad);
   long electores=bd2.obtenerSumaVotantesMesasXlocalidad(idlocalidad);
   BigDecimal porcentajeParticipacion=this.truncateDecimalPorcentajeMesasEscrutadas(totaldevotosgeneral,electores);
   
     
     FileWriter filewriter = null;
     String direimagen="test.jpg";
     PrintWriter printw = null;
     try {
     //ResultSetMetaData md = rs.getMetaData();
     //int count = md.getColumnCount();
     filewriter = new FileWriter(nombreArchivo);//declarar el archivo
       printw = new PrintWriter(filewriter);//declarar un impresor
        printw.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
printw.println("<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='es' lang='es'>");
    
//<!-- Mirrored from www.resultados.gob.ar/resultados/dat14/DPR14999A.htm by HTTrack Website Copier/3.x [XR&CO'2014], Mon, 02 Nov 2015 13:52:28 GMT -->
printw.println("<head>");
        printw.println("<title>Elecciones Nacionales Argentinas 2015 - Misiones  - Presidente </title>");

        printw.println("<meta http-equiv='X-UA-Compatible' content='IE=edge' />");
        printw.println("<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1' />");
        printw.println("<meta name='viewport' content='width=device-width, initial-scale=1' />");

        printw.println("<link rel='shortcut icon' href='imagenes/favicon.png' />");
        printw.println("<link rel='stylesheet' type='text/css' href='estilos/normalize_1_1_3.css'/>");
        printw.println("<link rel='stylesheet' type='text/css' href='estilos/estilos.css'/>");
        printw.println("<link rel='stylesheet' type='text/css' href='estilos/print.css'/>");
		printw.println("<link href=\"estilos/bootstrap.min.css\" rel=\"stylesheet\"> ");

        //<!--[if IE 9]>
          //  <link rel='stylesheet' type='text/css' href='../../estilos/estilos_IE9.css'/>
        //<![endif]-->

        //<!--[if lt IE 9]>
        //  <link rel='stylesheet' type='text/css' href='../../estilos/estilos_lt_IE9.css'/>
        //<![endif]-->

        printw.println("<script type='text/javascript' src='javascript/jquery-1.11.1.min.js'></script>");
        printw.println("<script type='text/javascript' src='javascript/modernizr.js'></script>");
        printw.println("<script type='text/javascript' src='javascript/detectizr.js'></script>");
        printw.println("<script type='text/javascript' src='javascript/general.js'></script>");
        printw.println("<script type='text/javascript' src='javascript/mun.js'></script>");
		printw.println("<script src=\"javascript/jquery-1.11.0.js\"></script>");
		printw.println("<script src=\"javascript/bootstrap.min.js\"></script>");
    printw.println("</head>");
    printw.println("<body class='p14 PR'>");

        //<!-- BANNER -->
        printw.println("<div id='banner'>");
            printw.println("<img src='imagenes/banner.png' alt='Logo Elecciones Nacionales Argentinas 2015'/>");
            printw.println("<div id='links'>");
                printw.println("<ul>");
                    printw.println("<li id='imprimir'>");
                        printw.println("<a href='javascript:window.print();'>Imprimir</a>");
                    printw.println("</li>");
                    printw.println("<li id='recargar'>");
                        printw.println("<a href='javascript:window.location.reload(true);'>Recargar</a>");
                    printw.println("</li>");
                printw.println("</ul>");
            printw.println("</div>");
        printw.println("</div>");
        //<!-- FIN BANNER -->

        //<!-- MENU -->
        
		
		
                        printw.println("<div class=\"btn-group\"> ");
                printw.println("<button type=\"button\" class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\"> Municipios <span class=\"caret\"></span></button> ");
                printw.println("<ul class=\"dropdown-menu scrollable-menu\" role=\"menu\" style=\" height: auto;max-height: 350px;overflow-x: hidden;\"> ");
printw.println("<li><a href=\"General76.html\"> MISIONES GENERAL</a></li> ");
printw.println("<li><a href=\"Nacional56.html\"> POSADAS</a></li> ");
printw.println("<li><a href=\"Nacional1.html\"> ALBA POSSE</a></li> ");
printw.println("<li><a href=\"Nacional2.html\"> ALMAFUERTE</a></li> ");
printw.println("<li><a href=\"Nacional3.html\"> APOSTOLES</a></li> ");
printw.println("<li><a href=\"Nacional4.html\"> A.DEL VALLE</a></li> ");
printw.println("<li><a href=\"Nacional5.html\"> A.DEL MEDIO</a></li> ");
printw.println("<li><a href=\"Nacional6.html\"> AZARA</a></li> ");
printw.println("<li><a href=\"Nacional7.html\"> IRIGOYEN</a></li> ");
printw.println("<li><a href=\"Nacional8.html\"> BONPLAND</a></li> ");
printw.println("<li><a href=\"Nacional9.html\"> CAAYARI</a></li> ");
printw.println("<li><a href=\"Nacional10.html\"> CAMPO GRANDE</a></li> ");
printw.println("<li><a href=\"Nacional11.html\"> CAMPO RAMON</a></li> ");
printw.println("<li><a href=\"Nacional12.html\"> CAMPO VIERA</a></li> ");
printw.println("<li><a href=\"Nacional13.html\"> CANDELARIA</a></li> ");
printw.println("<li><a href=\"Nacional14.html\"> CAPIOVI</a></li> ");
printw.println("<li><a href=\"Nacional15.html\"> CARAGUATAY</a></li> ");
printw.println("<li><a href=\"Nacional16.html\"> CERRO AZUL</a></li> ");
printw.println("<li><a href=\"Nacional17.html\"> CERRO CORA</a></li> ");
printw.println("<li><a href=\"Nacional18.html\"> CNIA.ALBERDI</a></li> ");
printw.println("<li><a href=\"Nacional19.html\"> COLONIA AURORA</a></li> ");
printw.println("<li><a href=\"Nacional20.html\"> CNIA.DELICIA</a></li> ");
printw.println("<li><a href=\"Nacional21.html\"> COLONIA POLANA</a></li> ");
printw.println("<li><a href=\"Nacional22.html\"> VICTORIA</a></li> ");
printw.println("<li><a href=\"Nacional23.html\"> WANDA</a></li> ");
printw.println("<li><a href=\"Nacional24.html\"> ANDRESITO</a></li>"); 
printw.println("<li><a href=\"Nacional25.html\"> C.DE LA SIERRA</a></li> ");
printw.println("<li><a href=\"Nacional26.html\"> CORPUS</a></li> ");
printw.println("<li><a href=\"Nacional27.html\"> DOS ARROYOS</a></li> ");
printw.println("<li><a href=\"Nacional28.html\"> DOS DE MAYO</a></li> ");
printw.println("<li><a href=\"Nacional29.html\"> EL ALCAZAR</a></li> ");
printw.println("<li><a href=\"Nacional30.html\"> ELDORADO</a></li> ");
printw.println("<li><a href=\"Nacional31.html\"> EL SOBERBIO</a></li> ");
printw.println("<li><a href=\"Nacional32.html\"> FACHINAL</a></li> ");
printw.println("<li><a href=\"Nacional33.html\"> F.AMEGHINO</a></li> ");
printw.println("<li><a href=\"Nacional34.html\"> GARUHAPE</a></li> ");
printw.println("<li><a href=\"Nacional35.html\"> GARUPA</a></li> ");
printw.println("<li><a href=\"Nacional36.html\"> GRAL.ALVEAR</a></li> ");
printw.println("<li><a href=\"Nacional37.html\"> SAN ANTONIO</a></li> ");
printw.println("<li><a href=\"Nacional38.html\"> GRAL.URQUIZA</a></li> ");
printw.println("<li><a href=\"Nacional39.html\"> GDOR.LOPEZ</a></li> ");
printw.println("<li><a href=\"Nacional40.html\"> GDOR.ROCA</a></li> ");
printw.println("<li><a href=\"Nacional41.html\"> GUARANI</a></li> ");
printw.println("<li><a href=\"Nacional42.html\"> H.IRIGOYEN</a></li> ");
printw.println("<li><a href=\"Nacional43.html\"> ITACARUARE</a></li> ");
printw.println("<li><a href=\"Nacional44.html\"> JARDIN AMERICA</a></li>"); 
printw.println("<li><a href=\"Nacional45.html\"> L.N.ALEM</a></li> ");
printw.println("<li><a href=\"Nacional46.html\"> LIBERTAD</a></li> ");
printw.println("<li><a href=\"Nacional47.html\"> LORETO</a></li> ");
printw.println("<li><a href=\"Nacional48.html\"> LOS HELECHOS</a></li> ");
printw.println("<li><a href=\"Nacional49.html\"> MARTIRES</a></li> ");
printw.println("<li><a href=\"Nacional50.html\"> MOJON GRANDE</a></li>"); 
printw.println("<li><a href=\"Nacional51.html\"> MONTECARLO</a></li> ");
printw.println("<li><a href=\"Nacional52.html\"> 9 DE JULIO</a></li> ");
printw.println("<li><a href=\"Nacional53.html\"> OBERA</a></li> ");
printw.println("<li><a href=\"Nacional54.html\"> O.V.ANDRADE</a></li> ");
printw.println("<li><a href=\"Nacional55.html\"> PANAMBI</a></li> ");
printw.println("<li><a href=\"Nacional57.html\"> PROFUNDIDAD</a></li> ");
printw.println("<li><a href=\"Nacional58.html\"> ESPERANZA</a></li> ");
printw.println("<li><a href=\"Nacional59.html\"> PTO.IGUAZU</a></li> ");
printw.println("<li><a href=\"Nacional60.html\"> PUERTO LEONI</a></li> ");
printw.println("<li><a href=\"Nacional61.html\"> PUERTO PIRAY</a></li> ");
printw.println("<li><a href=\"Nacional62.html\"> PUERTO RICO</a></li> ");
printw.println("<li><a href=\"Nacional63.html\"> R.DE MONTOYA</a></li> ");
printw.println("<li><a href=\"Nacional64.html\"> SAN IGNACIO</a></li> ");
printw.println("<li><a href=\"Nacional65.html\"> SAN JAVIER</a></li> ");
printw.println("<li><a href=\"Nacional66.html\"> SAN JOSE</a></li> ");
printw.println("<li><a href=\"Nacional67.html\"> SAN MARTIN</a></li> ");
printw.println("<li><a href=\"Nacional68.html\"> SAN PEDRO</a></li> ");
printw.println("<li><a href=\"Nacional69.html\"> SAN VICENTE</a></li> ");
printw.println("<li><a href=\"Nacional70.html\"> SANTA ANA</a></li> ");
printw.println("<li><a href=\"Nacional71.html\"> SANTA MARIA</a></li> ");
printw.println("<li><a href=\"Nacional72.html\"> SGO.DE LINIERS</a></li> ");
printw.println("<li><a href=\"Nacional73.html\"> SANTO PIPO</a></li> ");
printw.println("<li><a href=\"Nacional74.html\"> TRES CAPONES</a></li> ");
printw.println("<li><a href=\"Nacional75.html\"> 25 DE MAYO</a></li> ");
printw.println("</ul>");
printw.println("</div>");
                    
        //<!-- FIN MENU -->


        //<!-- PAGINA -->
        printw.println("<div id='pagina'>");

            //<!-- INDICES MUNICIPIOS 
            //<div id='indicesmunicipios'>
              //  <div id='interiorindicesmunicipios'>
                //    <ul class='indices'>
				  //  <li><a class='total' href='DPR99999A.htm'>Total Nacional</a></li>
					//<li class='act'><span id='c14'>+</span><a class='linkmuninojs' href='../localidades.htm'>+</a><a href='DPR14999A.html'>Misiones</a></li> </ul></div>
            //</div>
            //<!-- FIN INDICES MUNICIPIOS -->
             

            //<!-- INDICES ELECCIONES -->
            printw.println("<div id='indiceselecciones'>");
                printw.println("<div id='interiorindiceselecciones'>");
                    printw.println("<ul class='indices'>                        <li class='R'><a href='DDR14999R.html'>Resumen</a></li>");
                        printw.println("<li class='PR act'><a href='DPR14999A.html'>Presidente Misiones General </a></li>                 ;                        </ul>                </div>");
            printw.println("</div>");
            //<!-- FIN INDICES ELECCIONES -->

            //<!-- CONTENIDO -->
            printw.println("<div id='contenido'>");
                printw.println("<div id='interiorcontenido'>");

                    //<!-- CABECERA -->
                    printw.println("<div id='cabecera'>");

                        printw.println("<div id='titulo'>");
                            printw.println("<p>"+bd2.obtenerNombreLocalidad(idlocalidad) +" </p>");
                        printw.println("</div>");

                        printw.println("<div id='subtitulo'>");

                            printw.println("<div id='literalsubtitulo'>");
                                printw.println("<p>Misiones                                </p>");
                            printw.println("</div>");

                            printw.println("<div id='horafecha'>");

                                printw.println("<div id='fechalarga'>");
                                    printw.println("<p>Datos de las "+hourFormat.format(date)+" del "+dateFormat.format(date)+"</p>");
                                printw.println("</div>");

                                printw.println("<div id='fechacorta'>");
                                    printw.println("<p>Datos de las 08:20 del 26/10</p>");
                                printw.println("</div>");

                            printw.println("</div>");

                        printw.println("</div>");

                        printw.println("<div id='participacion'>");
                            printw.println("<p>Participacion sobre escrutado: <span>"+porcentajeParticipacion+"%</span></p>");
                        printw.println("</div>");

                    printw.println("</div>");
                    //<!-- FIN CABECERA -->

                    //<!-- DATOS -->
                    printw.println("<div id='datos'>");

                        printw.println("<div id='datosmesasygraficos'>");

                            printw.println("<div id='datosmesas'>");

                                printw.println("<div id='datosmesashabilitadas'>");
                                    printw.println("<table id='tablamesashabilitadas'>");
                                        printw.println("<colgroup>");
                                            printw.println("<col width='60%' />");
                                            printw.println("<col width='40%' />");
                                        printw.println("</colgroup>");
                                        printw.println("<thead>");
                                            printw.println("<tr>");
                                                printw.println("<th></th>");
                                                printw.println("<th class='literal'>Habilitado</th>");
                                            printw.println("</tr>");
                                        printw.println("</thead>");
                                        printw.println("<tbody>");
                                            printw.println("<tr>");
                                                printw.println("<td class='literal'>Electores</td>");
                                                printw.println("<td>"+totalelectores+"</td>");
                                            printw.println("</tr>");
                                            printw.println("<tr>");
                                                printw.println("<td class='literal'>Mesas</td>");
                                                printw.println("<td>"+MH+"</td>");
                                            printw.println("</tr>");
                                        printw.println("</tbody>");
                                    printw.println("</table>");
                                printw.println("</div>");

                                printw.println("<div id='datosmesasescrutadas'>");
                                    printw.println("<table id='tablamesasescrutadas'>");
                                        printw.println("<colgroup>");
                                            printw.println("<col width='60%' />");
                                            printw.println("<col width='40%' />");
                                        printw.println("</colgroup>");
                                        printw.println("<thead>");
                                            printw.println("<tr>");
                                                printw.println("<th></th>");
                                                printw.println("<th class='literal'>Escrutado</th>");
                                            printw.println("</tr>");
                                        printw.println("</thead>");
                                        printw.println("<tbody>");
                                            printw.println("<tr>");
                                                printw.println("<td class='literal'>Mesas</td>");
                                                printw.println("<td>"+ME+"</td>");
                                            printw.println("</tr>");
                                            printw.println("<tr>");
                                                printw.println("<td class='literal'>% escrutadas</td>");
                                                printw.println("<td>"+porcentajeMesas+"</td>");
                                            printw.println("</tr>");
                                        printw.println("</tbody>");
                                    printw.println("</table>");
                                printw.println("</div>");

                            printw.println("</div>");

                        printw.println("</div>");

                        printw.println("<div id='datosagrupaciones'>");
                            printw.println("<table id='tablaagrupaciones'>");
                                printw.println("<colgroup>");
                                    printw.println("<col width='70%' />");
                                    printw.println("<col width='15%' />");
                                    printw.println("<col width='15%' />");
                                printw.println("</colgroup>");
                                printw.println("<thead>");
                                    printw.println("<tr>");
                                        printw.println("<th class='literal'>Agrupaciones politicas</th>");
                                        printw.println("<th class='literal' colspan='2'>Votos</th>");
                                    printw.println("</tr>");
                                printw.println("</thead>");
                                printw.println("<tbody>");
                                    printw.println("<tr class='r1 agrup'>");
                                        printw.println("<td class='denom'>");
                                            printw.println("<img src='imagenes/DScioli.png'/> ALIANZA FRENTE PARA LA VICTORIA ");
                                            printw.println("<span style='width:"+porcentajeFPV+"%;border-color:#538ed5'></span>");
                                        printw.println("</td>");
                                        printw.println("<td class='vot'>"+votosFPV+"</td>");
                                        printw.println("<td class='pvot'>"+porcentajeFPV+"%</td>");
                                    printw.println("</tr>");
                                    printw.println("<tr class='r1 lista'>");
                                        printw.println("<td class='denom'>SCIOLI, DANIEL OSVALDO - ZANNINI, CARLOS ALBERTO </td>");
                                        printw.println("<td class='vot'></td>");
                                        printw.println("<td class='pvot'></td>");
                                    printw.println("</tr>");
                                    printw.println("<tr class='r2 agrup'>");
                                        printw.println("<td class='denom'>");
                                            printw.println("<img src='imagenes/MMacri.png'/> ALIANZA CAMBIEMOS"); 
                                            printw.println("<span style='width:"+porcentajeMacri+"%;border-color:#ffff00'></span>");
                                        printw.println("</td>");
                                        printw.println("<td class='vot'>"+votosMacri+"</td>");
                                        printw.println("<td class='pvot'>"+porcentajeMacri+"%</td>");
                                    printw.println("</tr>");
                                    printw.println("<tr class='r2 lista'>");
                                        printw.println("<td class='denom'>MACRI, MAURICIO - MICHETTI, MARTA GABRIELA </td>");
                                        printw.println("<td class='vot'></td>");
                                        printw.println("<td class='pvot'></td>");
                                    printw.println("</tr>");
                                    
                                printw.println("</tbody>");
                            printw.println("</table>");
                        printw.println("</div>");

                       printw.println("<div id='datosvotos'>");
                        printw.println("<table id='tablavotos'>");
                                printw.println("<colgroup>");
                                    printw.println("<col width='70%' />");
                                    printw.println("<col width='15%' />");
                                    printw.println("<col width='15%' />");
                                printw.println("</colgroup>");
                                printw.println("<thead>");
                                   printw.println(" <tr>");
                                        printw.println("<th></th>");
                                        printw.println("<th colspan='2' class='literal'>Votos</th>");
                                    printw.println("</tr>");
                                printw.println("</thead>");
                                printw.println("<tbody>");
                                    printw.println("<tr class='afir'>");
                                        printw.println("<td class='literal'>Afirmativos</td>");
                                        printw.println("<td class='vot'>"+sumaVotosPresidente+"</td>");
                                        printw.println("<td class='pvot'>"+porcentaje1+"%</td>");
                                    printw.println("</tr>");
                                    printw.println("<tr class='blan'>");
                                        printw.println("<td class='literal'>En blanco</td>");
                                        printw.println("<td class='vot'>"+blanco+"</td>");
                                        printw.println("<td class='pvot'>"+porcentaje2+"%</td>");
                                    printw.println("</tr>");
                                    printw.println("<tr>");
                                        printw.println("<td class='literal'>Nulos</td>");
                                        printw.println("<td class='vot'>"+nulo+"</td>");
                                        printw.println("<td class='pvot'>"+porcentaje3+"%</td>");
                                    printw.println("</tr>");
                                    printw.println("<tr>");
                                        printw.println("<td class='literal'>Recurridos e impugnados</td>");
                                        printw.println("<td class='vot'>"+recurrido+"</td>");
                                        printw.println("<td class='pvot'>"+porcentaje4+"%</td>");
                                    printw.println("</tr>");
                                    printw.println("<tr class='total'>");
                                        printw.println("<td class='literal'>Total de votos</td>");
                                        printw.println("<td class='vot'>"+sumaVotos+"</td>");
                                        printw.println("<td class='pvot'>100%</td>");
                                    printw.println("</tr>");
                                printw.println("</tbody>");
                            printw.println("</table>");
                        printw.println("</div>");

                   printw.println(" </div>");
                    //<!-- FIN DATOS -->

                    //<!-- PIE -->
                    printw.println("<div id='pie'>");

                        

                            //<!--<p align=\"center\"> -->
                            //<!--<img src='../../imagenes/logo.jpg'  alt='Centro De Cómputos de La Provincia De Misiones'/>-->
                        

                    printw.println("</div>");
                    //<!-- FIN PIE -->

                printw.println("</div>");
            printw.println("</div>");
            //<!-- FIN CONTENIDO -->
        printw.println("</div>");
        //<!-- FIN PAGINA -->

    printw.println("</body>");

//<!-- Mirrored from www.resultados.gob.ar/resultados/dat14/DPR14999A.htm by HTTrack Website Copier/3.x [XR&CO'2014], Mon, 02 Nov 2015 13:52:38 GMT -->
printw.println("</html>");

    
    printw.close();
    
            
     //no debemos olvidar cerrar el archivo para que su lectura sea correcta
     
     printw.close();//cerramos el archivo
     
     bd2.cerrarConexion();
     System.out.println("Generado exitosamente");//si todo sale bien mostramos un mensaje de guardado exitoso
     } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
}
  //crea el html regional

public void BdToHtmlGeneral(String nombreArchivo,int idlocalidad) throws SQLException{
     Date date = new Date();
    //Caso 1: obtener la hora y salida por pantalla con formato:
    DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
     
     
     BDPASO2015 bd2=new BDPASO2015();
     
     bd2.abrirConexion();
     
     //datos balotage 
     String votosFPV=bd2.obtenerVotosPresidenteGeneral()[0];
     String votosMacri=bd2.obtenerVotosPresidenteGeneral()[1];
     Long sumaVotosPresidente=Long.parseLong(votosFPV)+Long.parseLong(votosMacri);
     //porcentaje de votos presidente
     BigDecimal porcentajeFPV=this.truncateDecimalPorcentajeMesasEscrutadas(sumaVotosPresidente, Long.parseLong(votosFPV));
     BigDecimal porcentajeMacri=this.truncateDecimalPorcentajeMesasEscrutadas(sumaVotosPresidente, Long.parseLong(votosMacri));
     //fin
     
     
     long MH=bd2.obtenerMesasHabilitadasGeneral();
     long ME=bd2.obtenerMesasEscrutadasGeneral();
     BigDecimal porcentajeMesas=this.truncateDecimalPorcentajeMesasEscrutadas(MH, ME);
     long id1=idlocalidad;
     
     
     String []blanco1=bd2.obtenerVotosPresidenteGeneral();
     
     
     String blanco=blanco1[4];
     String nulo=blanco1[2];
     String recurrido=blanco1[3];
     //parte de pie de pagina
     long sumaVotos=sumaVotosPresidente+Long.parseLong(blanco)+Long.parseLong(nulo)+Long.parseLong(recurrido);
     BigDecimal porcentaje1=this.truncateDecimalPorcentajeMesasEscrutadas(sumaVotos,sumaVotosPresidente);
     BigDecimal porcentaje2=this.truncateDecimalPorcentajeMesasEscrutadas(sumaVotos,Long.parseLong(blanco));
     BigDecimal porcentaje3=this.truncateDecimalPorcentajeMesasEscrutadas(sumaVotos,Long.parseLong(nulo));
     BigDecimal porcentaje4=this.truncateDecimalPorcentajeMesasEscrutadas(sumaVotos,Long.parseLong(recurrido));
     //fin porcentajes pie de pagina
     
     
     
     long totalElectoresHabilitados=bd2.obtenerElectoresGeneral();
     long totaldevotosgeneral=bd2.obtenerSumaElectoresMesasGeneral();
     long electores=bd2.obtenerSumaVotantesMesas();
   BigDecimal porcentajeParticipacion=this.truncateDecimalPorcentajeMesasEscrutadas(totaldevotosgeneral,electores);
   
     
     FileWriter filewriter = null;
     String direimagen="test.jpg";
     PrintWriter printw = null;
     try {
     //ResultSetMetaData md = rs.getMetaData();
     //int count = md.getColumnCount();
     filewriter = new FileWriter(nombreArchivo);//declarar el archivo
       printw = new PrintWriter(filewriter);//declarar un impresor
        printw.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
printw.println("<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='es' lang='es'>");
    
//<!-- Mirrored from www.resultados.gob.ar/resultados/dat14/DPR14999A.htm by HTTrack Website Copier/3.x [XR&CO'2014], Mon, 02 Nov 2015 13:52:28 GMT -->
printw.println("<head>");
        printw.println("<title>Elecciones Nacionales Argentinas 2015 - Misiones  - Presidente </title>");

        printw.println("<meta http-equiv='X-UA-Compatible' content='IE=edge' />");
        printw.println("<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1' />");
        printw.println("<meta name='viewport' content='width=device-width, initial-scale=1' />");

        printw.println("<link rel='shortcut icon' href='imagenes/favicon.png' />");
        printw.println("<link rel='stylesheet' type='text/css' href='estilos/normalize_1_1_3.css'/>");
        printw.println("<link rel='stylesheet' type='text/css' href='estilos/estilos.css'/>");
        printw.println("<link rel='stylesheet' type='text/css' href='estilos/print.css'/>");
		printw.println("<link href=\"estilos/bootstrap.min.css\" rel=\"stylesheet\"> ");

        //<!--[if IE 9]>
          //  <link rel='stylesheet' type='text/css' href='../../estilos/estilos_IE9.css'/>
        //<![endif]-->

        //<!--[if lt IE 9]>
        //  <link rel='stylesheet' type='text/css' href='../../estilos/estilos_lt_IE9.css'/>
        //<![endif]-->

        printw.println("<script type='text/javascript' src='javascript/jquery-1.11.1.min.js'></script>");
        printw.println("<script type='text/javascript' src='javascript/modernizr.js'></script>");
        printw.println("<script type='text/javascript' src='javascript/detectizr.js'></script>");
        printw.println("<script type='text/javascript' src='javascript/general.js'></script>");
        printw.println("<script type='text/javascript' src='javascript/mun.js'></script>");
		printw.println("<script src=\"javascript/jquery-1.11.0.js\"></script>");
		printw.println("<script src=\"javascript/bootstrap.min.js\"></script>");
    printw.println("</head>");
    printw.println("<body class='p14 PR'>");

        //<!-- BANNER -->
        printw.println("<div id='banner'>");
            printw.println("<img src='imagenes/banner.png' alt='Logo Elecciones Nacionales Argentinas 2015'/>");
            printw.println("<div id='links'>");
                printw.println("<ul>");
                    printw.println("<li id='imprimir'>");
                        printw.println("<a href='javascript:window.print();'>Imprimir</a>");
                    printw.println("</li>");
                    printw.println("<li id='recargar'>");
                        printw.println("<a href='javascript:window.location.reload(true);'>Recargar</a>");
                    printw.println("</li>");
                printw.println("</ul>");
            printw.println("</div>");
        printw.println("</div>");
        //<!-- FIN BANNER -->

        //<!-- MENU -->
        
		
		
                        printw.println("<div class=\"btn-group\"> ");
                printw.println("<button type=\"button\" class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\"> Municipios <span class=\"caret\"></span></button> ");
                printw.println("<ul class=\"dropdown-menu scrollable-menu\" role=\"menu\" style=\" height: auto;max-height: 350px;overflow-x: hidden;\"> ");
printw.println("<li><a href=\"General76.html\"> MISIONES GENERAL</a></li> ");
printw.println("<li><a href=\"Nacional56.html\"> POSADAS</a></li> ");
printw.println("<li><a href=\"Nacional1.html\"> ALBA POSSE</a></li> ");
printw.println("<li><a href=\"Nacional2.html\"> ALMAFUERTE</a></li> ");
printw.println("<li><a href=\"Nacional3.html\"> APOSTOLES</a></li> ");
printw.println("<li><a href=\"Nacional4.html\"> A.DEL VALLE</a></li> ");
printw.println("<li><a href=\"Nacional5.html\"> A.DEL MEDIO</a></li> ");
printw.println("<li><a href=\"Nacional6.html\"> AZARA</a></li> ");
printw.println("<li><a href=\"Nacional7.html\"> IRIGOYEN</a></li> ");
printw.println("<li><a href=\"Nacional8.html\"> BONPLAND</a></li> ");
printw.println("<li><a href=\"Nacional9.html\"> CAAYARI</a></li> ");
printw.println("<li><a href=\"Nacional10.html\"> CAMPO GRANDE</a></li> ");
printw.println("<li><a href=\"Nacional11.html\"> CAMPO RAMON</a></li> ");
printw.println("<li><a href=\"Nacional12.html\"> CAMPO VIERA</a></li> ");
printw.println("<li><a href=\"Nacional13.html\"> CANDELARIA</a></li> ");
printw.println("<li><a href=\"Nacional14.html\"> CAPIOVI</a></li> ");
printw.println("<li><a href=\"Nacional15.html\"> CARAGUATAY</a></li> ");
printw.println("<li><a href=\"Nacional16.html\"> CERRO AZUL</a></li> ");
printw.println("<li><a href=\"Nacional17.html\"> CERRO CORA</a></li> ");
printw.println("<li><a href=\"Nacional18.html\"> CNIA.ALBERDI</a></li> ");
printw.println("<li><a href=\"Nacional19.html\"> COLONIA AURORA</a></li> ");
printw.println("<li><a href=\"Nacional20.html\"> CNIA.DELICIA</a></li> ");
printw.println("<li><a href=\"Nacional21.html\"> COLONIA POLANA</a></li> ");
printw.println("<li><a href=\"Nacional22.html\"> VICTORIA</a></li> ");
printw.println("<li><a href=\"Nacional23.html\"> WANDA</a></li> ");
printw.println("<li><a href=\"Nacional24.html\"> ANDRESITO</a></li>"); 
printw.println("<li><a href=\"Nacional25.html\"> C.DE LA SIERRA</a></li> ");
printw.println("<li><a href=\"Nacional26.html\"> CORPUS</a></li> ");
printw.println("<li><a href=\"Nacional27.html\"> DOS ARROYOS</a></li> ");
printw.println("<li><a href=\"Nacional28.html\"> DOS DE MAYO</a></li> ");
printw.println("<li><a href=\"Nacional29.html\"> EL ALCAZAR</a></li> ");
printw.println("<li><a href=\"Nacional30.html\"> ELDORADO</a></li> ");
printw.println("<li><a href=\"Nacional31.html\"> EL SOBERBIO</a></li> ");
printw.println("<li><a href=\"Nacional32.html\"> FACHINAL</a></li> ");
printw.println("<li><a href=\"Nacional33.html\"> F.AMEGHINO</a></li> ");
printw.println("<li><a href=\"Nacional34.html\"> GARUHAPE</a></li> ");
printw.println("<li><a href=\"Nacional35.html\"> GARUPA</a></li> ");
printw.println("<li><a href=\"Nacional36.html\"> GRAL.ALVEAR</a></li> ");
printw.println("<li><a href=\"Nacional37.html\"> SAN ANTONIO</a></li> ");
printw.println("<li><a href=\"Nacional38.html\"> GRAL.URQUIZA</a></li> ");
printw.println("<li><a href=\"Nacional39.html\"> GDOR.LOPEZ</a></li> ");
printw.println("<li><a href=\"Nacional40.html\"> GDOR.ROCA</a></li> ");
printw.println("<li><a href=\"Nacional41.html\"> GUARANI</a></li> ");
printw.println("<li><a href=\"Nacional42.html\"> H.IRIGOYEN</a></li> ");
printw.println("<li><a href=\"Nacional43.html\"> ITACARUARE</a></li> ");
printw.println("<li><a href=\"Nacional44.html\"> JARDIN AMERICA</a></li>"); 
printw.println("<li><a href=\"Nacional45.html\"> L.N.ALEM</a></li> ");
printw.println("<li><a href=\"Nacional46.html\"> LIBERTAD</a></li> ");
printw.println("<li><a href=\"Nacional47.html\"> LORETO</a></li> ");
printw.println("<li><a href=\"Nacional48.html\"> LOS HELECHOS</a></li> ");
printw.println("<li><a href=\"Nacional49.html\"> MARTIRES</a></li> ");
printw.println("<li><a href=\"Nacional50.html\"> MOJON GRANDE</a></li>"); 
printw.println("<li><a href=\"Nacional51.html\"> MONTECARLO</a></li> ");
printw.println("<li><a href=\"Nacional52.html\"> 9 DE JULIO</a></li> ");
printw.println("<li><a href=\"Nacional53.html\"> OBERA</a></li> ");
printw.println("<li><a href=\"Nacional54.html\"> O.V.ANDRADE</a></li> ");
printw.println("<li><a href=\"Nacional55.html\"> PANAMBI</a></li> ");
printw.println("<li><a href=\"Nacional57.html\"> PROFUNDIDAD</a></li> ");
printw.println("<li><a href=\"Nacional58.html\"> ESPERANZA</a></li> ");
printw.println("<li><a href=\"Nacional59.html\"> PTO.IGUAZU</a></li> ");
printw.println("<li><a href=\"Nacional60.html\"> PUERTO LEONI</a></li> ");
printw.println("<li><a href=\"Nacional61.html\"> PUERTO PIRAY</a></li> ");
printw.println("<li><a href=\"Nacional62.html\"> PUERTO RICO</a></li> ");
printw.println("<li><a href=\"Nacional63.html\"> R.DE MONTOYA</a></li> ");
printw.println("<li><a href=\"Nacional64.html\"> SAN IGNACIO</a></li> ");
printw.println("<li><a href=\"Nacional65.html\"> SAN JAVIER</a></li> ");
printw.println("<li><a href=\"Nacional66.html\"> SAN JOSE</a></li> ");
printw.println("<li><a href=\"Nacional67.html\"> SAN MARTIN</a></li> ");
printw.println("<li><a href=\"Nacional68.html\"> SAN PEDRO</a></li> ");
printw.println("<li><a href=\"Nacional69.html\"> SAN VICENTE</a></li> ");
printw.println("<li><a href=\"Nacional70.html\"> SANTA ANA</a></li> ");
printw.println("<li><a href=\"Nacional71.html\"> SANTA MARIA</a></li> ");
printw.println("<li><a href=\"Nacional72.html\"> SGO.DE LINIERS</a></li> ");
printw.println("<li><a href=\"Nacional73.html\"> SANTO PIPO</a></li> ");
printw.println("<li><a href=\"Nacional74.html\"> TRES CAPONES</a></li> ");
printw.println("<li><a href=\"Nacional75.html\"> 25 DE MAYO</a></li> ");
printw.println("</ul>");
printw.println("</div>");
                    
        //<!-- FIN MENU -->


        //<!-- PAGINA -->
        printw.println("<div id='pagina'>");

            //<!-- INDICES MUNICIPIOS 
            //<div id='indicesmunicipios'>
              //  <div id='interiorindicesmunicipios'>
                //    <ul class='indices'>
				  //  <li><a class='total' href='DPR99999A.htm'>Total Nacional</a></li>
					//<li class='act'><span id='c14'>+</span><a class='linkmuninojs' href='../localidades.htm'>+</a><a href='DPR14999A.html'>Misiones</a></li> </ul></div>
            //</div>
            //<!-- FIN INDICES MUNICIPIOS -->
             

            //<!-- INDICES ELECCIONES -->
            printw.println("<div id='indiceselecciones'>");
                printw.println("<div id='interiorindiceselecciones'>");
                    printw.println("<ul class='indices'>                        <li class='R'><a href='DDR14999R.html'>Resumen</a></li>");
                        printw.println("<li class='PR act'><a href='DPR14999A.html'>Presidente Misiones General </a></li>                 ;                        </ul>                </div>");
            printw.println("</div>");
            //<!-- FIN INDICES ELECCIONES -->

            //<!-- CONTENIDO -->
            printw.println("<div id='contenido'>");
                printw.println("<div id='interiorcontenido'>");

                    //<!-- CABECERA -->
                    printw.println("<div id='cabecera'>");

                        printw.println("<div id='titulo'>");
                            printw.println("<p>MISIONES TOTAL GENERAL </p>");
                        printw.println("</div>");

                        printw.println("<div id='subtitulo'>");

                            printw.println("<div id='literalsubtitulo'>");
                                printw.println("<p>Misiones                                </p>");
                            printw.println("</div>");

                            printw.println("<div id='horafecha'>");

                                printw.println("<div id='fechalarga'>");
                                    printw.println("<p>Datos de las "+hourFormat.format(date)+" del "+dateFormat.format(date)+"</p>");
                                printw.println("</div>");

                                printw.println("<div id='fechacorta'>");
                                    printw.println("<p>Datos de las 08:20 del 26/10</p>");
                                printw.println("</div>");

                            printw.println("</div>");

                        printw.println("</div>");

                        printw.println("<div id='participacion'>");
                            printw.println("<p>Participacion sobre escrutado: <span>"+porcentajeParticipacion+"%</span></p>");
                        printw.println("</div>");

                    printw.println("</div>");
                    //<!-- FIN CABECERA -->

                    //<!-- DATOS -->
                    printw.println("<div id='datos'>");

                        printw.println("<div id='datosmesasygraficos'>");

                            printw.println("<div id='datosmesas'>");

                                printw.println("<div id='datosmesashabilitadas'>");
                                    printw.println("<table id='tablamesashabilitadas'>");
                                        printw.println("<colgroup>");
                                            printw.println("<col width='60%' />");
                                            printw.println("<col width='40%' />");
                                        printw.println("</colgroup>");
                                        printw.println("<thead>");
                                            printw.println("<tr>");
                                                printw.println("<th></th>");
                                                printw.println("<th class='literal'>Habilitado</th>");
                                            printw.println("</tr>");
                                        printw.println("</thead>");
                                        printw.println("<tbody>");
                                            printw.println("<tr>");
                                                printw.println("<td class='literal'>Electores</td>");
                                                printw.println("<td>"+totalElectoresHabilitados+"</td>");
                                            printw.println("</tr>");
                                            printw.println("<tr>");
                                                printw.println("<td class='literal'>Mesas</td>");
                                                printw.println("<td>"+MH+"</td>");
                                            printw.println("</tr>");
                                        printw.println("</tbody>");
                                    printw.println("</table>");
                                printw.println("</div>");

                                printw.println("<div id='datosmesasescrutadas'>");
                                    printw.println("<table id='tablamesasescrutadas'>");
                                        printw.println("<colgroup>");
                                            printw.println("<col width='60%' />");
                                            printw.println("<col width='40%' />");
                                        printw.println("</colgroup>");
                                        printw.println("<thead>");
                                            printw.println("<tr>");
                                                printw.println("<th></th>");
                                                printw.println("<th class='literal'>Escrutado</th>");
                                            printw.println("</tr>");
                                        printw.println("</thead>");
                                        printw.println("<tbody>");
                                            printw.println("<tr>");
                                                printw.println("<td class='literal'>Mesas</td>");
                                                printw.println("<td>"+ME+"</td>");
                                            printw.println("</tr>");
                                            printw.println("<tr>");
                                                printw.println("<td class='literal'>% escrutadas</td>");
                                                printw.println("<td>"+porcentajeMesas+"</td>");
                                            printw.println("</tr>");
                                        printw.println("</tbody>");
                                    printw.println("</table>");
                                printw.println("</div>");

                            printw.println("</div>");

                        printw.println("</div>");

                        printw.println("<div id='datosagrupaciones'>");
                            printw.println("<table id='tablaagrupaciones'>");
                                printw.println("<colgroup>");
                                    printw.println("<col width='70%' />");
                                    printw.println("<col width='15%' />");
                                    printw.println("<col width='15%' />");
                                printw.println("</colgroup>");
                                printw.println("<thead>");
                                    printw.println("<tr>");
                                        printw.println("<th class='literal'>Agrupaciones politicas</th>");
                                        printw.println("<th class='literal' colspan='2'>Votos</th>");
                                    printw.println("</tr>");
                                printw.println("</thead>");
                                printw.println("<tbody>");
                                    printw.println("<tr class='r1 agrup'>");
                                        printw.println("<td class='denom'>");
                                            printw.println("<img src='imagenes/DScioli.png'/> ALIANZA FRENTE PARA LA VICTORIA ");
                                            printw.println("<span style='width:"+porcentajeFPV+"%;border-color:#538ed5'></span>");
                                        printw.println("</td>");
                                        printw.println("<td class='vot'>"+votosFPV+"</td>");
                                        printw.println("<td class='pvot'>"+porcentajeFPV+"%</td>");
                                    printw.println("</tr>");
                                    printw.println("<tr class='r1 lista'>");
                                        printw.println("<td class='denom'>SCIOLI, DANIEL OSVALDO - ZANNINI, CARLOS ALBERTO </td>");
                                        printw.println("<td class='vot'></td>");
                                        printw.println("<td class='pvot'></td>");
                                    printw.println("</tr>");
                                    printw.println("<tr class='r2 agrup'>");
                                        printw.println("<td class='denom'>");
                                            printw.println("<img src='imagenes/MMacri.png'/> ALIANZA CAMBIEMOS"); 
                                            printw.println("<span style='width:"+porcentajeMacri+"%;border-color:#ffff00'></span>");
                                        printw.println("</td>");
                                        printw.println("<td class='vot'>"+votosMacri+"</td>");
                                        printw.println("<td class='pvot'>"+porcentajeMacri+"%</td>");
                                    printw.println("</tr>");
                                    printw.println("<tr class='r2 lista'>");
                                        printw.println("<td class='denom'>MACRI, MAURICIO - MICHETTI, MARTA GABRIELA </td>");
                                        printw.println("<td class='vot'></td>");
                                        printw.println("<td class='pvot'></td>");
                                    printw.println("</tr>");
                                    
                                printw.println("</tbody>");
                            printw.println("</table>");
                        printw.println("</div>");

                       printw.println("<div id='datosvotos'>");
                        printw.println("<table id='tablavotos'>");
                                printw.println("<colgroup>");
                                    printw.println("<col width='70%' />");
                                    printw.println("<col width='15%' />");
                                    printw.println("<col width='15%' />");
                                printw.println("</colgroup>");
                                printw.println("<thead>");
                                   printw.println(" <tr>");
                                        printw.println("<th></th>");
                                        printw.println("<th colspan='2' class='literal'>Votos</th>");
                                    printw.println("</tr>");
                                printw.println("</thead>");
                                printw.println("<tbody>");
                                    printw.println("<tr class='afir'>");
                                        printw.println("<td class='literal'>Afirmativos</td>");
                                        printw.println("<td class='vot'>"+sumaVotosPresidente+"</td>");
                                        printw.println("<td class='pvot'>"+porcentaje1+"%</td>");
                                    printw.println("</tr>");
                                    printw.println("<tr class='blan'>");
                                        printw.println("<td class='literal'>En blanco</td>");
                                        printw.println("<td class='vot'>"+blanco+"</td>");
                                        printw.println("<td class='pvot'>"+porcentaje2+"%</td>");
                                    printw.println("</tr>");
                                    printw.println("<tr>");
                                        printw.println("<td class='literal'>Nulos</td>");
                                        printw.println("<td class='vot'>"+nulo+"</td>");
                                        printw.println("<td class='pvot'>"+porcentaje3+"%</td>");
                                    printw.println("</tr>");
                                    printw.println("<tr>");
                                        printw.println("<td class='literal'>Recurridos e impugnados</td>");
                                        printw.println("<td class='vot'>"+recurrido+"</td>");
                                        printw.println("<td class='pvot'>"+porcentaje4+"%</td>");
                                    printw.println("</tr>");
                                    printw.println("<tr class='total'>");
                                        printw.println("<td class='literal'>Total de votos</td>");
                                        printw.println("<td class='vot'>"+sumaVotos+"</td>");
                                        printw.println("<td class='pvot'>100%</td>");
                                    printw.println("</tr>");
                                printw.println("</tbody>");
                            printw.println("</table>");
                        printw.println("</div>");

                   printw.println(" </div>");
                    //<!-- FIN DATOS -->

                    //<!-- PIE -->
                    printw.println("<div id='pie'>");

                        

                            //<!--<p align=\"center\"> -->
                            //<!--<img src='../../imagenes/logo.jpg'  alt='Centro De Cómputos de La Provincia De Misiones'/>-->
                        

                    printw.println("</div>");
                    //<!-- FIN PIE -->

                printw.println("</div>");
            printw.println("</div>");
            //<!-- FIN CONTENIDO -->
        printw.println("</div>");
        //<!-- FIN PAGINA -->

    printw.println("</body>");

//<!-- Mirrored from www.resultados.gob.ar/resultados/dat14/DPR14999A.htm by HTTrack Website Copier/3.x [XR&CO'2014], Mon, 02 Nov 2015 13:52:38 GMT -->
printw.println("</html>");

    
    printw.close();
    
            
     //no debemos olvidar cerrar el archivo para que su lectura sea correcta
     
     printw.close();//cerramos el archivo
     
     bd2.cerrarConexion();
     System.out.println("Generado exitosamente");//si todo sale bien mostramos un mensaje de guardado exitoso
     } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
}







private  BigDecimal truncateDecimalPorcentajeMesasEscrutadas(Long divisor, Long dividendo) {
        double div1 = (double) divisor;
        double div2 = (double) dividendo;
        Double res = (div2 / div1) * 100;
        BigDecimal aux = new BigDecimal("0");
        if (div1 != 0) {
            aux = new BigDecimal(String.valueOf(res)).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return aux;
    }




}
  

