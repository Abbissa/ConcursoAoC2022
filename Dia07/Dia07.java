package Dia07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Dia07 {

    public static void main(String[] args) throws FileNotFoundException {
        parte1();
        parte2();

    }



    private static void parte2() throws FileNotFoundException {
        Nodo raiz = new Nodo(null, new HashMap<String, Nodo>(), "/", 0);

        leer(raiz);
        raiz.sumar();
        System.out.println(raiz.buscar(30000000-(70000000-raiz.value)));

    }


    private static void parte1() throws FileNotFoundException {
        Nodo raiz = new Nodo(null, new HashMap<String, Nodo>(), "/", 0);

        leer(raiz);

       System.out.println(raiz.sumar());
    }
    
    private static void leer(Nodo raiz) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileInputStream("Dia07\\input.txt"))) {

            sc.nextLine();
            Nodo cur = raiz;
            String line = sc.nextLine();
            while (sc.hasNext()) {
                boolean ls = true;
                String[] mandato = line.split(" ");
                if (mandato[1].equals("ls")) {

                    while (sc.hasNext()&&ls) {
                        line = sc.nextLine();
                        mandato = line.split(" ");
                        if (line.charAt(0) == '$') {
                            ls = false;
                            continue;
                        }
                        if (mandato[0].equals("dir")) {
                            String name = mandato[1];
                            cur.hijos.put(name, new Nodo(cur, new HashMap<String, Nodo>(), name, 0));
                        } else {
                            cur.hijos.put(mandato[1], new Nodo(cur, null, mandato[1], Long.parseLong(mandato[0])));
                        }
                    }
                } else if (mandato[1].equals("cd")) {

                    if (mandato[2].equals("..")) {
                        cur = cur.padre;
                    } else if (mandato[2].equals("/")) {
                        cur = raiz;
                    } else {
                        cur = cur.hijos.get(mandato[2]);
                    }

                }
                if (sc.hasNext()&&ls)
                    line = sc.nextLine();
            }

        }
    }

    private static class Nodo {

        Nodo padre;
        Map<String, Nodo> hijos;
        long value;

        public Nodo(Nodo padre, Map<String, Nodo> hijos, String key, long value) {
            this.padre = padre;
            this.hijos = hijos;
            this.value = value;
        }

        public long buscar(long l) {
            long res = 0;
            if(this.hijos!=null&&this.value>=l){
                res=this.value;

                for (Nodo nodo : hijos.values()) {

                    long aux= nodo.buscar(l);
                    if(aux!=0&&aux<res)
                        res=aux;
                    
                }

            }
            return res;
        }

        public long sumar() {
            int res=0;
            for (Nodo nodo : hijos.values()) {

                if(nodo.hijos==null){
                    this.value+=nodo.value;
                }else{
                    res += nodo.sumar();
                    this.value+=nodo.value;
                }
                
                
            }
            if(this.value<=100000)
                res+=this.value;
            return res;
        }

    }

}
