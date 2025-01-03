package ThreadSocketManagerJava;

import java.io.Serializable;

public class Operation implements Serializable {
    private double nombre1;
    private double nombre2;
    private String operateur;
    private double resultat;
    private boolean status = false; // Set to true when the user types "exit" to signal the server to close the socket.
    public Operation(){}
    public Operation(double nb1, double nb2, String op){
        nombre1 = nb1;
        nombre2 = nb2;
        operateur = op;
    }
    public double getNomber1(){
        return (nombre1);
    }
    public double getNomber2(){
        return (nombre2);
    }
    public String getOperateur(){
        return (operateur);
    }
    public boolean getStatus(){
        return (status);
    }
    public void setStatus(){
        status = true;
    }
    public String operateur(){
        return (operateur);
    }
    public double resultat(){
        return (resultat);
    }
    
    public void calculer()
    {
        try {
            if (operateur.equals("+"))
            resultat = nombre1 + nombre2;
            else if (operateur.equals("-"))
            resultat = nombre1 - nombre2; 
            else if (operateur.equals("/"))
            {
                if (nombre2 == 0)
                {
                    System.out.println("cannot devided by \0");  
                    return ; 
                }
                resultat = nombre1 / nombre2; 
            }
            else if (operateur.equals("*"))
            resultat = nombre1 * nombre2; 
            else
                System.out.println("Incorrect operator ");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
    }
    @Override
    public String toString() {
        return "[" + nombre1 + "] " + operateur + " [" + nombre2 + "] = [" + resultat + "]";
    }
}
