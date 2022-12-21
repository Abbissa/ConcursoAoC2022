package Dia20;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dia20 {

    // Lista de Nodos a ordenar y HashSer de elementos ya ordenados
    // Si falla, modificar .equals de Nodo para que compare referencias, no valores

    private static String INPUT = "Dia20\\input.txt";
    private static String EXAMPLE = "Dia20\\example.txt";
    private static int tam;

    public static void main(String[] args) throws FileNotFoundException {
        parte1(EXAMPLE);
        parte1(INPUT);
        parte2(EXAMPLE);
        parte2(INPUT);

    }

    private static void parte1(String file) throws FileNotFoundException {
        Node raiz = leer(file);
        solve(raiz);
        encontrarSol(raiz);
    }

    private static void parte2(String file) throws FileNotFoundException {
        Node raiz = leer2(file);
        for (int i = 0; i < 10; i++) {
            solve(raiz);
        }
        encontrarSol(raiz);
    }

    private static Node leer2(String file) throws FileNotFoundException {

        Node raiz = new Node(0);
        Node node = raiz;
        int i = 0;
        tam = 0;
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            while (sc.hasNext()) {
                long n = sc.nextInt();
                node.n = n * 811_589_153;
                node.order = i;
                Node nodo = new Node(n);
                node.post = nodo;
                nodo.pre = node;
                node = nodo;
                i++;
                tam++;
            }
        }
        node.pre.post = raiz;
        raiz.pre = node.pre;
        return raiz;
    }

    private static void encontrarSol(Node raiz) {
        Node node = encontrar0(raiz);

        long n1 = encontrarN(node, 1000);
        long n2 = encontrarN(node, 2000);
        long n3 = encontrarN(node, 3000);
        System.out.println(n1 + " " + n2 + " " + n3);
        System.out.println(n1 + n2 + n3);
    }

    private static void solve(Node raiz) {

        Node cur = raiz;

        iterar(cur);
        int i = 1;

        while (i != 0) {
            while (cur.order != i) {
                cur = cur.post;
            }
            iterar(cur);
            i = (i + 1) % tam;
        }

    }

    private static long encontrarN(Node node, int i) {

        int add = i % tam;

        Node cur = node;

        while (add != 0) {
            cur = cur.post;
            add--;
        }

        return cur.n;
    }

    private static Node encontrar0(Node raiz) {

        Node node = raiz;

        while (node.n != 0)
            node = node.post;

        return node;
    }

    private static void iterar(Node cur) {
        int add = (int) (cur.n % (tam - 1));

        cur.pre.post = cur.post;
        cur.post.pre = cur.pre;

        Node avance = cur;
        if (add > 0) {
            while (add != 0) {
                avance = avance.post;
                add--;
            }
            cur.post = avance.post;
            cur.pre = avance;
            avance.post.pre = cur;
            avance.post = cur;

        } else if (add < 0) {
            while (add != 0) {
                avance = avance.pre;
                add++;
            }
            cur.post = avance;
            cur.pre = avance.pre;
            avance.pre.post = cur;
            avance.pre = cur;
        } else {
            cur.pre.post = cur;
            cur.post.pre = cur;
        }
    }

    private static Node leer(String file) throws FileNotFoundException {
        Node raiz = new Node(0);
        Node node = raiz;
        int i = 0;
        tam = 0;
        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            while (sc.hasNext()) {
                int n = sc.nextInt();
                node.n = n;
                node.order = i;
                Node nodo = new Node(n);
                node.post = nodo;
                nodo.pre = node;
                node = nodo;
                i++;
                tam++;
            }
        }
        node.pre.post = raiz;
        raiz.pre = node.pre;
        return raiz;
    }

    public static class Node {
        long n;
        Node pre;
        Node post;
        int order;

        public Node(long n) {
            this.n = n;
        }

        @Override
        public String toString() {
            return this.n + "";
        }
    }

}
