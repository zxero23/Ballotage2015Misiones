/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ballotage2015;

/**
 *
 * @author vaio
 */
public class Ballotage2015 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    BDPASO2015 bd =new BDPASO2015();
    EscribeHtml escribe=new EscribeHtml();
    
     for (int i=1;i<=75;i++){
         try{
            escribe.BdToHtml("D://balotaje1//Nacional"+i+".html",i);
         } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     }
     
     try{
            escribe.BdToHtmlGeneral("D://balotaje1//General"+76+".html",76);
         } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    
    
    
    }
}
