 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ballotage2015;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BanghoCCCPM
 */
public class BDPASO2015 {

    private Connection con;

    public void abrirConexion() {
        try {
          Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://10.10.0.21:5432/balotagedb2015";
            String user = "postgres";
            String pass = "admin";
          
            
            /*String url = "jdbc:postgresql://localhost:5432/ballotaje";
            String user = "postgres";
            String pass = "root";*/
            this.con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void cerrarConexion() {
        try {
            this.con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public String[][] obtenersublemas(int idMunicipio){
        String[] retorno = null;
        String[] retorno2=null;
        
        String a;
        int i=0,j=0;
        List<String> auxLst = new ArrayList<>();
        List<String> auxLst2 = new ArrayList<>();
        System.out.println("----- SubLemas bd final-----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("select l.lista, l.nombre from lemas l left join lemas_municipios lm on l.id = lm.lema_id left join configuraciones con on l.id = con.lema_id and lm.municipios_id = con.municipio_id where con.cod_pla = '2'  and con.municipio_id ="+idMunicipio+" and con.extranjero=false order by posicion"
                );
            while (rs2.next()) {
                //System.out.println(rs2.getString(1) + " - " + rs2.getString(2));
                
                //matriz[i][j]=a.substring(1);
              //  matriz[i][j+1]=rs2.getString(2);
                i=i+1;
                auxLst.add(rs2.getString(1));
                auxLst2.add(rs2.getString(2));
                
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
            retorno2= auxLst2.toArray(new String[auxLst2.size()]);
            // System.out.println(retorno[0]+"-------"+retorno2[0]);
            
        
        } else {
            
            retorno = this.crearVotos(19);
            retorno2= this.crearVotos(19);
        }
        
        String[][] matriz=new String[retorno.length][retorno2.length];
        int aa=0;
          for (i=0;i<retorno.length;i++){
            matriz[i][0]=retorno[i];
            matriz[i][1]=retorno2[i];
            
          } 
        return matriz;
    
        
  }
    
    public String[] obtenerlemadesublema(int idMunicipio){
        String[] retorno = null;
        //String[] retorno2=null;
        
        String a;
        int i=0,j=0;
        List<String> auxLst = new ArrayList<>();
        List<String> auxLst2 = new ArrayList<>();
        //System.out.println("-----cabeza de lema bd final-----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.nombre,l.lista,l.id FROM lemas l INNER JOIN municipios m ON l.id=m.id and m.id="+idMunicipio);
            while (rs2.next()) {
                //System.out.println(rs2.getString(1) + " - " + rs2.getString(2));
                
                //matriz[i][j]=a.substring(1);
              //  matriz[i][j+1]=rs2.getString(2);
                
                auxLst.add(rs2.getString(1));
                auxLst.add(rs2.getString(2));
                
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
            //retorno2= auxLst2.toArray(new String[auxLst2.size()]);
            // System.out.println(retorno[0]+"-------"+retorno2[0]);
            
        
        } else {
            
            //retorno = this.crearVotos(19);
           // retorno2= this.crearVotos(19);
        }
        
        
        
        
        return retorno;
       
        } 
   
    
        
    public int obtenerMesasHabilitadas(int idMunicipio) {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT count(m.id) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE mu.id = " + idMunicipio);
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }

    public int obtenerElectoresHabilitados(int idMunicipio) {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT SUM(m.electores) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE mu.id = " + idMunicipio);
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }

    public int obtenerVotantes(Long idMunicipio) {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT SUM(m.votantes) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE m.cargada = true AND m.publicada = true AND mu.id = " + idMunicipio);
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }
    
    public int obtenerElectores(Long idMunicipio) {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT SUM(m.electores) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE m.cargada = true AND m.publicada = true AND mu.id = " + idMunicipio);
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }

    public int obtenerMesasEscrutadas(int idMunicipio) {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT COUNT(m.id) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true ");
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }

    public String obtenerNombreLocalidad(int idMunicipio) {
        String retorno = null;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT mu.nombre FROM municipios mu WHERE mu.id = " + idMunicipio);
            while (rs.next()) {
                retorno = rs.getString(1);
            }
            stmt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        System.out.println(retorno);
        return retorno;
    }

    public int obtenerMesasHabilitadasGeneral() {
        int retorno = 2480;
        return retorno;
    }

   /* public int obtenerElectoresHabilitadosGeneral() {
        int retorno = 847862;
        return retorno;
    }*/

    public int obtenerMesasEscrutadasGeneral() {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT COUNT(m.id) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE m.cargada = true AND m.publicada = true ");
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }

    //cuatro columnas con los votos x sublemas ordenados por cargos (nacional y general) 
    
    public String[] obtenerVotosPresidente(long idMunicipio) {
        String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.id,l.nombre,sum(pres) FROM votos v INNER JOIN mesas m on m.id=v.mesa_id INNER JOIN lemas l ON l.id=v.lema_id and m.municipio_id="+idMunicipio+" and m.cargada = true AND m.publicada = true GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(3));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
    }
     
    public String[] obtenerVotosParlamentarioNacional(Long idMunicipio) {
        String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PARLAMENTARIO MERCOSUR NACIONAL -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.id,l.nombre,sum(par_nac) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16) AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '1' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(3));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(17);
        }
        return retorno;
    }

    public String[] obtenerVotosDiputadoNacional(Long idMunicipio) {
        String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- DIPUTADO NACIONAL -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.id,l.nombre,sum(dip_nac) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16) AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '1' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(3));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(17);
        }
        return retorno;
    }

    public String[] obtenerVotosParlamentarioRegional(Long idMunicipio) {
        String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- Parlamentario Mercosur Regional -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.id,l.nombre,sum(par_prov) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN (1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16) AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '1' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(3));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(17);
        }
        return retorno;
    }

    public String[] crearVotos(int tamaño) {
        String[] votos = new String[tamaño];
        for (int i = 0; i < tamaño; i++) {
            votos[i] = "0";
        }
        return votos;
    }
   
    public String[] obtenerVotosPresidenteGeneral() {
        String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.id,l.nombre,sum(pres) FROM votos v INNER JOIN mesas m on m.id=v.mesa_id INNER JOIN lemas l ON l.id=v.lema_id where m.cargada = true AND m.publicada = true GROUP BY l.id,l.nombre ORDER BY l.id ");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(3));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
    }

    public String[] obtenerVotosParlamentarioNacionalGeneral() {
        String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PARLAMENTARIO MERCOSUR NACIONAL -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.id,l.nombre,sum(par_nac) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16) AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '1' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(3));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(17);
        }
        return retorno;
    }

    public String[] obtenerVotosDiputadoNacionalGeneral() {
        String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- DIPUTADO NACIONAL -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery(" SELECT l.id,l.nombre,sum(dip_nac) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16) AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '1' GROUP BY l.id,l.nombre ORDER BY l.id ");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(3));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(17);
        }
        return retorno;
    }

    public String[] obtenerVotosParlamentarioRegionalGeneral() {
        String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- Parlamentario Mercosur Regional -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.id,l.nombre,sum(par_prov) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16) AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '1' GROUP BY l.id,l.nombre ORDER BY l.id ");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(3));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(17);
        }
        return retorno;
    }

    public int obtenerVotantesGenereal() {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT SUM(m.votantes) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE m.cargada = true AND m.publicada = true");
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }
    
    public int obtenerElectoresGenereal() {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT SUM(m.electores) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE m.cargada = true AND m.publicada = true");
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }



 
 //VOTOS DE LA 2DA HOJA 
    
    
    
    
    // consulta y obtiene los votos regionales
 public String[][] obtenerVotosGob(int idMunicipio){
        String[] retorno = null;
        String[] retorno2=null;
        
        String a;
        int i=0,j=0;
        List<String> auxLst = new ArrayList<>();
        List<String> auxLst2 = new ArrayList<>();
        System.out.println("----- SubLemas bd final-----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.lista,sum(gob)  votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id >16 AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.lista,l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
                //System.out.println(rs2.getString(1) + " - " + rs2.getString(2));
                
                //matriz[i][j]=a.substring(1);
              //  matriz[i][j+1]=rs2.getString(2);
                i=i+1;
                auxLst.add(rs2.getString(1));
                auxLst2.add(rs2.getString(2));
                
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
            retorno2= auxLst2.toArray(new String[auxLst2.size()]);
            // System.out.println(retorno[0]+"-------"+retorno2[0]);
            
        
        } else {
            
            retorno = this.crearVotos(19);
            retorno2= this.crearVotos(19);
        }
        
        String[][] matriz=new String[retorno.length][retorno2.length];
        int aa=0;
          for (i=0;i<retorno.length;i++){
            matriz[i][0]=retorno[i];
            matriz[i][1]=retorno2[i];
            
          } 
        return matriz;
    
        
  }
    
    
    public String[] obtenerVotosGob1(long idMunicipio) {
       String[] retorno = null;
       String[] retorno2 = null;
       String[] retorno3 = null;
        List<String> auxLst = new ArrayList<>();
        List<String> auxLst2 = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.lista,sum(gob)  votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id >16 AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.lista,l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
                System.out.println("stringggg.---------"+rs2.getString(1) + " - " + rs2.getString(2));
                auxLst.add(rs2.getString(1));
                auxLst2.add(rs2.getString(2));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
            
        } else {
            retorno = this.crearVotos(19);
        }
        
            
        
        
        return retorno;
   }  
 public String[] obtenerVotosDipProv(long idMunicipio) {
      String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(dip_prov) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id >16 AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(1));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
  }  
 
 
 public String[][] obtenerVotosInten(int idMunicipio){
        String[] retorno = null;
        String[] retorno2=null;
        
        String a;
        int i=0,j=0;
        List<String> auxLst = new ArrayList<>();
        List<String> auxLst2 = new ArrayList<>();
        System.out.println("----- SubLemas bd final-----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.lista,sum(inte)  votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id >16 AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.lista,l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
                //System.out.println(rs2.getString(1) + " - " + rs2.getString(2));
                
                //matriz[i][j]=a.substring(1);
              //  matriz[i][j+1]=rs2.getString(2);
                i=i+1;
                auxLst.add(rs2.getString(1));
                auxLst2.add(rs2.getString(2));
                
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
            retorno2= auxLst2.toArray(new String[auxLst2.size()]);
            // System.out.println(retorno[0]+"-------"+retorno2[0]);
            
        
        } else {
            
            retorno = this.crearVotos(19);
            retorno2= this.crearVotos(19);
        }
        
        String[][] matriz=new String[retorno.length][retorno2.length];
        int aa=0;
          for (i=0;i<retorno.length;i++){
            matriz[i][0]=retorno[i];
            matriz[i][1]=retorno2[i];
            
          } 
        return matriz;
    
        
  }
    
 
 public String[] obtenerVotosInten1(long idMunicipio) {
           String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.lista,sum(inte)  votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id >16 AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.lista,l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(1));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
  }  
 public String[] obtenerVotosConc(long idMunicipio) {
      String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(con) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id >16 AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(1));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
  }  
 public String[] obtenerVotosDef(long idMunicipio) {
     String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(def) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id >16 AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(1));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
    
}
    
  // votos en blanco, nulos , recurridos y totales en una lista de 3 en ese orden
 
 public String[] GobBlancoNuloRecurridoTotales(int idMunicipio){
     
     String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(gob) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN (13,14,15,16) AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(1));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
     
 }
 public String[] DipBlancoNuloRecurridoTotales(int idMunicipio){
     
     String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(dip_prov) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN (13,14,15,16) AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(1));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
     
 }
 public String[] InteBlancoNuloRecurridoTotales(int idMunicipio){
     
     String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(inte) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN (13,14,15,16) AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(1));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
     
 }
 public String[] ConBlancoNuloRecurridoTotales(int idMunicipio){
     
     String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(con) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN (13,14,15,16) AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(1));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
     
 }
 public String[] DefBlancoNuloRecurridoTotales(int idMunicipio){
     
     String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(def) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN (13,14,15,16) AND mu.id ="+idMunicipio+" AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(1));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
     
 }
 
 
// votos totales generales regionales
 
 public String[] obtenerVotosGobernadorGeneral() {
        String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.id,l.nombre,sum(gob) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN (17,18,19,20,32,33,36,45,50,54,764) AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2'  GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(3));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
    }
 
 public String[] obtenerVotosDiputadoGeneral() {
        String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT l.id,l.nombre,sum(dip_prov) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN (17,18,19,20,32,33,36,45,50,54,764) AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(3));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
    }

 
 

 
  public String[][] obtenerlemas(){
        String[] retorno = null;
        String[] retorno2=null;
        
        String a;
        int i=0,j=0;
        List<String> auxLst = new ArrayList<>();
        List<String> auxLst2 = new ArrayList<>();
        System.out.println("----- SubLemas bd final-----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("select lista,nombre,id from lemas where id IN (17,18,19,20,32,33,36,45,50,54) "
                );
            while (rs2.next()) {
                //System.out.println(rs2.getString(1) + " - " + rs2.getString(2));
                
                //matriz[i][j]=a.substring(1);
              //  matriz[i][j+1]=rs2.getString(2);
                i=i+1;
                auxLst.add(rs2.getString(1));
                auxLst2.add(rs2.getString(2));
                
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
            retorno2= auxLst2.toArray(new String[auxLst2.size()]);
            // System.out.println(retorno[0]+"-------"+retorno2[0]);
            
        
        } else {
            
            retorno = this.crearVotos(19);
            retorno2= this.crearVotos(19);
        }
        
        String[][] matriz=new String[retorno.length][retorno2.length];
        int aa=0;
          for (i=0;i<retorno.length;i++){
            matriz[i][0]=retorno[i];
            matriz[i][1]=retorno2[i];
            
          } 
        return matriz;
    
        
  }
  
 public String[] GobBlancoNuloRecurridoTotalesGRAL(){
     
     String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(gob) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN (13,14,15,16) AND mu.id >0 AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(1));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
     
 } 
 
 public String[] DipProvBlancoNuloRecurridoTotalesGRAL(){
     
     String[] retorno = null;
        List<String> auxLst = new ArrayList<>();
//        System.out.println("----- PRESIDENTE -----");
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(dip_prov) votos FROM votos v INNER JOIN registros r ON v.id = r.voto_id INNER JOIN lemas l ON r.lema_id = l.id INNER JOIN planillas_registros pr ON r.id = pr.listregistro_id INNER JOIN planillas pl ON pl.id = pr.planilla_id INNER JOIN mesas m ON m.id = pl.mesa_id INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE l.id IN (13,14,15,16) AND mu.id >0 AND m.cargada = true AND m.publicada = true AND pl.cod_pla = '2' GROUP BY l.id,l.nombre ORDER BY l.id");
            while (rs2.next()) {
//                System.out.println(rs2.getString(2) + " - " + rs2.getString(3));
                auxLst.add(rs2.getString(1));
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (!auxLst.isEmpty()) {
            retorno = auxLst.toArray(new String[auxLst.size()]);
        } else {
            retorno = this.crearVotos(19);
        }
        return retorno;
     
 }
 
 
 // para sacar el % de participacion
 
 public int obtenerSumaElectoresMesasGeneral() {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(m.electores) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE m.cargada = true AND m.publicada = true");
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }
 
 public int obtenerSumaVotantesMesas() {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(m.votantes) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE m.cargada = true AND m.publicada = true");
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }
 
 
 public int obtenerSumaElectoresMesasXlocalidad(int idlocalidad) {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(m.electores) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE m.cargada = true AND m.publicada = true and mu.id="+idlocalidad);
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }
 
 public int obtenerSumaVotantesMesasXlocalidad(int idlocalidad) {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT sum(m.votantes) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id WHERE m.cargada = true AND m.publicada = true and mu.id="+idlocalidad);
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }
 
 
 public int obtenerElectoresGeneral() {
        int retorno = 0;
        try {
            Statement comando = con.createStatement();
            ResultSet rs2 = comando.executeQuery("SELECT SUM(m.electores) FROM mesas m INNER JOIN escuelas e ON e.id = m.escuela_id INNER JOIN municipios mu ON mu.id = e.municipio_id");
            while (rs2.next()) {
                retorno = rs2.getInt(1);
            }
            rs2.close();
            comando.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }
 
 
}

