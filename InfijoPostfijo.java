package proyecto2.progra2;
import java.util.Scanner;
import java.util.Stack;
//Esta libreria servirá para otener el resultado de la expresión
import javax.script.*;

/**
 *
 * @author kevin
 * 7690-15-3698
 */

public class InfijoPostfijo {
    
    public static void main(String[] args) {

    //Solicitar datos para evaluar
    System.out.println("*Escribe una expresión algebraica: ");
    Scanner leer = new Scanner(System.in);

    //Depurar la expresion ingresada por teclado
    String expr = Depurar(leer.nextLine());
    //Dato para obtener el resultado de la expresion
    String oper = Resultado(expr);
    String[] arrayInfix = expr.split(" ");

    //Declaración de las pilas quese van a utilizar
    Stack < String > E = new Stack < String > (); //Pila entrada
    Stack < String > P = new Stack < String > (); //Pila temporal para operadores
    Stack < String > S = new Stack < String > (); //Pila salida

    //Añadir el array a la Pila de entrada
    for (int i = arrayInfix.length - 1; i >= 0; i--) {
      E.push(arrayInfix[i]);
    }

    try {
      //Algoritmo para pasar de Infijo a Postfijo
      while (!E.isEmpty()) {
        switch (pref(E.peek())){
          case 1:
            P.push(E.pop());
            break;
          case 3:
          case 4:
            while(pref(P.peek()) >= pref(E.peek())) {
              S.push(P.pop());
            }
            P.push(E.pop());
            break; 
          case 2:
            while(!P.peek().equals("(")) {
              S.push(P.pop());
            }
            P.pop();
            E.pop();
            break; 
          default:
            S.push(E.pop()); 
        } 
      }

      //Eliminacion de caracteres innecesarios en la expresion
      String infix = expr.replace(" ", "");
      String postfix = S.toString().replaceAll("[\\]\\[,]", "");

      //Mostrar resultados
      System.out.println("Expresion Infija: " + infix);
      System.out.println("Expresion Postfija: " + postfix);

    }catch(Exception ex){ 
      System.out.println("Error en la expresión algebraica");
      System.err.println(ex);
    }
  }

  //Depurar expresión algebraica
  private static String Depurar(String s) {
    s = s.replaceAll("\\s+", ""); //Elimina espacios en blanco
    s = "(" + s + ")";
    String simbols = "+-*/()";
    String str = "";
 
    //Deja espacios entre operadores
    for (int i = 0; i < s.length(); i++) {
      if (simbols.contains("" + s.charAt(i))) {
        str += " " + s.charAt(i) + " ";
      }else str += s.charAt(i);
    }
    return str.replaceAll("\\s+", " ").trim();
  }

  //Jerarquia de los operadores
  private static int pref(String op) {
    int prf = 99;
    if (op.equals("^")) prf = 5;
    if (op.equals("*") || op.equals("/")) prf = 4;
    if (op.equals("+") || op.equals("-")) prf = 3;
    if (op.equals(")")) prf = 2;
    if (op.equals("(")) prf = 1;
    return prf;
  }
  
  public static String Resultado(String expr){
      
      //Estos objetos evaluan la expresión ingresada
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine engine = manager.getEngineByName("js");
	  
	  try {
            //Este pequeño código hace todo el trabajo
            Object result = engine.eval(expr);
            String salida = expr+" = "+result;
            System.out.println("Rsultado: " + result);

        } catch(ScriptException se) {
             System.out.println(se);
        }
        return null;
        
        
   }

     
}
